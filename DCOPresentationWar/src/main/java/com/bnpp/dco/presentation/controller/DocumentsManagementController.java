package com.bnpp.dco.presentation.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.bnpp.dco.common.constant.Constants;
import com.bnpp.dco.common.dto.AjaxDto;
import com.bnpp.dco.common.dto.CountryDto;
import com.bnpp.dco.common.dto.DocumentDto;
import com.bnpp.dco.common.dto.DocumentTypeDto;
import com.bnpp.dco.common.dto.LanguageDto;
import com.bnpp.dco.common.dto.LegalEntityDto;
import com.bnpp.dco.common.exception.DCOException;
import com.bnpp.dco.presentation.form.DocumentFilterForm;
import com.bnpp.dco.presentation.form.DocumentForm;
import com.bnpp.dco.presentation.form.DocumentsCheckedForm;
import com.bnpp.dco.presentation.utils.BusinessHelper;
import com.bnpp.dco.presentation.utils.PropertiesHelper;
import com.bnpp.dco.presentation.utils.UserHelper;
import com.bnpp.dco.presentation.utils.constants.WebConstants;

@Controller
public class DocumentsManagementController extends GenericController {

    /** Logger. */
    private static final Logger LOG = LoggerFactory.getLogger(DocumentsManagementController.class);

    @Autowired
    private PropertiesHelper propertiesHelper;

    @Autowired
    private DocumentFilterForm documentFilterForm;

    @Autowired
    private DocumentsCheckedForm documentsCheckedForm;

    @Autowired
    private DocumentForm documentForm;

    @ModelAttribute("documentFilterForm")
    public DocumentFilterForm getDocumentFilterForm() {
        return this.documentFilterForm;
    }

    @ModelAttribute("documentsCheckedForm")
    public DocumentsCheckedForm getDocumentsCheckedForm() {
        return this.documentsCheckedForm;
    }

    @ModelAttribute("documentForm")
    public DocumentForm getDocumentForm() {
        return this.documentForm;
    }

    /**
     * Method to put form (with all the documents) in the session
     * @param model
     * @return
     * @throws InterruptedException
     */
    @RequestMapping(value = "documentsLoad", method = RequestMethod.GET)
    public final String getDocuments() throws InterruptedException {
        try {
            addDropDownFieldsIntoModel();
        } catch (final DCOException e) {
            addError(this.propertiesHelper.getMessage("page.unload.model"));
        }

        // Reset the country's filter (case where the user had logged out before re logging)
        getDocumentFilterForm().setCountry(Constants.VAR_ZERO);
        getDocumentFilterForm().setDocumentList(null);

        if (UserHelper.getUserInSession().getLocaleWorkingCountry() != null) {
            final Iterator<CountryDto> it = getDocumentFilterForm().getCountryList().iterator();

            while (it.hasNext()) {
                final CountryDto country = it.next();
                if (country.getLocale() != null
                        && country.getLocale().getCountry()
                                .equals(UserHelper.getUserInSession().getLocaleWorkingCountry().getCountry())) {
                    getDocumentFilterForm().setCountry(country.getId());
                    break;
                }
            }
        }

        return WebConstants.DOCUMENTS;
    }

    /**
     * Method to fetch from the database the filtered documents.
     * @param form
     * @return the matched documents with the filter.
     */
    @RequestMapping(value = "/findDocument", method = RequestMethod.POST)
    public final String findDocuments(@ModelAttribute("documentFilterForm") final DocumentFilterForm form) {

        incrementUserStat();
        incrementDocStat(getCountryFromForm(form.getCountry()), getLegalEntityFromForm(form.getLegalEntity()),
                getLanguageFromForm(form.getLanguage()), getDocumentTypeFromForm(form.getDocumentType()));

        Boolean resident = null;
        if (UserHelper.getUserInSession() != null && UserHelper.getUserInSession().getLocaleEntity() != null) {
            final CountryDto selectedCountry = getCountryFromForm(getDocumentFilterForm().getCountry());
            if (selectedCountry != null) {
                resident = selectedCountry.getLocale().getCountry()
                        .equals(UserHelper.getUserInSession().getLocaleEntity().getCountry());
            }
        }

        return loadModel(form.getCountry(), form.getLanguage(), form.getDocumentType(), form.getLegalEntity(),
                form.getDocumentTitle(), Constants.VAR_NEG_ONE, resident, WebConstants.DOCUMENTS);
    }

    /**
     * Method to load the filter fields in the document Page
     * @throws DCOException
     */
    private void addDropDownFieldsIntoModel() throws DCOException {

        getDocumentFilterForm().setLanguageList(
                (List<LanguageDto>) BusinessHelper.call(Constants.CONTROLLER_LANGAGE, Constants.CONTROLLER_LIST,
                        new Object[] {}));

        getDocumentFilterForm().setCountryList(
                (List<CountryDto>) BusinessHelper.call(Constants.CONTROLLER_COUNTRY, Constants.CONTROLLER_LIST,
                        new Object[] {}));

        getDocumentFilterForm().setDocumentTypeList(
                (List<DocumentTypeDto>) BusinessHelper.call(Constants.CONTROLLER_DOCUMENT_TYPE,
                        Constants.CONTROLLER_LIST, new Object[] {}));

        getDocumentFilterForm().setLegalEntityList(
                (List<LegalEntityDto>) BusinessHelper.call(Constants.CONTROLLER_LEGAL_ENTITY,
                        Constants.CONTROLLER_LIST, new Object[] {Constants.VAR_NEG_ONE}));

    }

    /**
     * Method to call the biz to fetch the filtered documents.
     */
    private String loadModel(final Integer country, final Integer language, final Integer documentType,
            final Integer legalEntity, final String documentTitle, final Integer id, final Boolean resident,
            final String returnPage) {

        try {
            if (country != 0) {
                int docCommonSupportingId = 0;
                if (getDocumentFilterForm().getDocumentTypeList() != null) {
                    for (final DocumentTypeDto documentTypeDto : getDocumentFilterForm().getDocumentTypeList()) {
                        if (Constants.PARAM_TYPE_DOC_COMMON_SUPPORTING.equals(documentTypeDto.getLabel())) {
                            docCommonSupportingId = documentTypeDto.getId();
                            break;
                        }
                    }
                }

                final List<DocumentDto> listDocumentDto = (List<DocumentDto>) BusinessHelper.call(
                        Constants.CONTROLLER_DOCUMENT, Constants.CONTROLLER_LIST, new Object[] {country, language,
                                documentType, legalEntity, documentTitle, new Integer[] {id}, resident, false, null});
                if (UserHelper.getUserInSession() != null
                        && UserHelper.getUserInSession().getLocaleEntity() != null && resident != null
                        && !resident) {
                    final Integer countryid = getCountryIdFromName(UserHelper.getUserInSession().getLocaleEntity()
                            .getCountry());
                    listDocumentDto.addAll((List<DocumentDto>) BusinessHelper.call(Constants.CONTROLLER_DOCUMENT,
                            Constants.CONTROLLER_LIST, new Object[] {countryid, language, docCommonSupportingId,
                                    legalEntity, documentTitle, new Integer[] {id}, null, false, null}));
                }
                // Filter documents if user is a client, by xbasV2
                List<DocumentDto> documentsDto = new ArrayList<>();
                if(UserHelper.getUserInSession().getProfile().equals(Constants.PROFILE_CLIENT)){
	                for (DocumentDto documentDto : listDocumentDto){
	                	if(documentDto.getXbasV2().equals(UserHelper.getUserInSession().isXbasV2())){
	                		documentsDto.add(documentDto);
	                	}
	                }
                } else {
                	documentsDto.addAll(listDocumentDto);
                }
                getDocumentFilterForm().setDocumentList(documentsDto);
            } else {
                // reset the country's filter!
                getDocumentFilterForm().setDocumentList(null);
                addError(this.propertiesHelper.getMessage("page.document.country.filter.warning"));
            }
        } catch (final DCOException e) {
            addError(this.propertiesHelper.getMessage("page.unload.model"));
        }
        return returnPage;
    }

    private boolean newloadDocumentList(final Integer country, final Integer language, final Integer documentType,
            final Integer legalEntity, final String documentTitle, final Integer id, final Boolean resident) {

        try {
            int docCommonSupportingId = 0;
            if (getDocumentFilterForm().getDocumentTypeList() != null) {
                for (final DocumentTypeDto documentTypeDto : getDocumentFilterForm().getDocumentTypeList()) {
                    if (Constants.PARAM_TYPE_DOC_COMMON_SUPPORTING.equals(documentTypeDto.getLabel())) {
                        docCommonSupportingId = documentTypeDto.getId();
                        break;
                    }
                }
            }
            final List<DocumentDto> listDocumentDto = (List<DocumentDto>) BusinessHelper.call(
                    Constants.CONTROLLER_DOCUMENT, Constants.CONTROLLER_LIST, new Object[] {country, language,
                            documentType, legalEntity, documentTitle, new Integer[] {id}, null, false, null});
            if (UserHelper.getUserInSession() != null && UserHelper.getUserInSession().getLocaleEntity() != null
                    && resident != null && !resident) {
                final Integer countryid = getCountryIdFromName(UserHelper.getUserInSession().getLocaleEntity()
                        .getCountry());
                listDocumentDto.addAll((List<DocumentDto>) BusinessHelper.call(Constants.CONTROLLER_DOCUMENT,
                        Constants.CONTROLLER_LIST, new Object[] {countryid, language, docCommonSupportingId,
                                legalEntity, documentTitle, new Integer[] {id}, true, false, null}));
                listDocumentDto.addAll((List<DocumentDto>) BusinessHelper.call(Constants.CONTROLLER_DOCUMENT,
                        Constants.CONTROLLER_LIST, new Object[] {countryid, language, docCommonSupportingId,
                                legalEntity, documentTitle, new Integer[] {id}, false, false, null}));
            }
            getDocumentFilterForm().setDocumentList(listDocumentDto);
            return true;
        } catch (final DCOException e) {
            addError(this.propertiesHelper.getMessage("page.unload.model"));
        }
        return false;
    }

    /**
     * Method to download the document (if severzal: download a zip file).
     * @param response
     * @param docs
     * @throws DCOException
     */
    private void downloadDocuments(final HttpServletResponse response, final List<DocumentDto> docs)
            throws DCOException {

        response.setHeader("Content-Type", "application/force-download");
        response.setHeader("Cache-Control", "public");
        response.setHeader("Pragma", "private");
        response.setHeader("Expires", "0");

        OutputStream os = null;
        try {
            os = response.getOutputStream();
            if (docs.size() == 1) {
                // 1 document => returning the document
                final DocumentDto d = docs.get(0);
                response.setContentLength(d.getData().length);
                response.setHeader("Content-Disposition", "attachment;filename=\"" + d.getTitle() + "\"");
                os.write(d.getData(), 0, d.getData().length);
            } else {
                // Multiple documents => Zipping and returning the zip
                final Map<String, Integer> mapNames = new HashMap<String, Integer>();
                final ByteArrayOutputStream baos = new ByteArrayOutputStream();
                final ZipOutputStream zos = new ZipOutputStream(baos);
                for (final DocumentDto d : docs) {
                    // Computing the file name to avoid duplicates
                    Integer nbOccurences = mapNames.get(d.getTitle());
                    if (nbOccurences == null) {
                        nbOccurences = 0;
                    } else {
                        nbOccurences += 1;
                    }
                    mapNames.put(d.getTitle(), nbOccurences);
                    final StringBuilder sbFileName = new StringBuilder();
                    if (nbOccurences == 0) {
                        sbFileName.append(d.getTitle());
                    } else {
                        final int positLastDot = d.getTitle().lastIndexOf(".");
                        if (positLastDot == -1) {
                            sbFileName.append(d.getTitle()).append("-").append(nbOccurences);
                        } else {
                            sbFileName.append(d.getTitle().substring(0, positLastDot)).append("-")
                                    .append(nbOccurences).append(d.getTitle().substring(positLastDot));
                        }
                    }
                    final ZipEntry ze = new ZipEntry(sbFileName.toString());
                    zos.putNextEntry(ze);
                    zos.write(d.getData());
                }
                response.setHeader("Content-Disposition",
                        "attachment;filename=\"" + this.propertiesHelper.getMessage("page.document.archive.name")
                                + ".zip\"");
                zos.closeEntry();
                zos.close();
                // Writing the content to the output stream
                IOUtils.copy(new ByteArrayInputStream(baos.toByteArray()), os);
            }
        } catch (final FileNotFoundException e) {
            final String message = e.getMessage();
            LOG.error(message);
            throw new DCOException(message, Constants.EXCEPTION_FILE_NOT_FOUND);
        } catch (final IOException e) {
            final String message = e.getMessage();
            LOG.error(message);
            throw new DCOException(e.getMessage(), Constants.EXCEPTION_IO);
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (final IOException e) {
                    final String message = "Error while closing the stream after sending document download response => Proceeding";
                    LOG.error(message);
                    throw new DCOException(message, e, Constants.EXCEPTION_IO);
                }
            }
        }
    }

    /**
     * @param model
     * @return the upload document page
     * @throws DCOException
     */
    @RequestMapping(value = "/addDocument", method = RequestMethod.GET)
    public String addDocument() throws DCOException {
        getDocumentForm().setCountry(Constants.VAR_NEG_ONE);
        getDocumentForm().setLanguage(Constants.VAR_NEG_ONE);
        getDocumentForm().setDocumentType(Constants.VAR_ONE);
        getDocumentForm().setLegalEntity(Constants.VAR_NEG_ONE);

        getDocumentForm().setConfirm(false);
        getDocumentForm().setMatch(false);
        getDocumentForm().setMatchAll(false);
        getDocumentForm().setResident(false);

        return WebConstants.ADD_DOCUMENTS;
    }

    /**
     * Method to upload a confirmed document (it's called when the user have to confirm the recording of a document
     * that already have similar parameters with another).
     * @return the main document page
     * @throws InterruptedException
     * @throws IOException
     */
    @RequestMapping(value = "/doAddDocumentConfirm", method = RequestMethod.GET)
    public String doAddDocumentConfirm() {

        try {
            if (getDocumentForm().getDocumentToSave() != null) {
                BusinessHelper.call(Constants.CONTROLLER_DOCUMENT, Constants.CONTROLLER_DOCUMENTS_SAVE,
                        new Object[] {getDocumentForm().getDocumentToSave()});
                addConfirm(this.propertiesHelper.getMessage("page.document.save.sucess"));
            } else {
                addError(this.propertiesHelper.getMessage("page.document.save.error"));
            }
        } catch (final DCOException e) {
            if (e.getCode() == Constants.EXCEPTION_RECORDING_DATA) {
                addError(this.propertiesHelper.getMessage("recording.data.error"));
            } else if (e.getCode() == Constants.EXCEPTION_ROLLBACK) {
                addError(this.propertiesHelper.getMessage("recording.data.error"));
                addError(this.propertiesHelper.getMessage("canceling.record.data.error"));
            }
        }
        if (getDocumentFilterForm().getCountry() == 0) {
            getDocumentFilterForm().setDocumentList(null);
            return WebConstants.DOCUMENTS;
        }
        return loadModel(getDocumentFilterForm().getCountry(), Constants.VAR_NEG_ONE, Constants.VAR_NEG_ONE,
                Constants.VAR_NEG_ONE, Constants.EMPTY_FIELD, Constants.VAR_NEG_ONE, false, WebConstants.DOCUMENTS);
    }

    /**
     * Method to upload a new document in the database
     * @param response
     * @param model
     * @return the document page
     * @throws DCOException
     * @throws InterruptedException
     * @throws IOException
     */
    @RequestMapping(value = "/doAddDocument", method = RequestMethod.POST)
    public String doAddDocument(@ModelAttribute("documentForm") final DocumentForm form,
            final BindingResult result, final Model model) {
        final MultipartFile multipartFile = form.getFile();

        if (checkFileContentType(multipartFile)) {
            String fileName;
            if (multipartFile != null && !multipartFile.isEmpty()) {
                OutputStream os = null;
                try {
                    if (multipartFile.getBytes().length < Constants.MAX_SIZE_UPLOAD_DOCUMENT) {
                        fileName = multipartFile.getOriginalFilename();

                        final InputStream is = multipartFile.getInputStream();
                        os = new FileOutputStream(fileName);
                        final byte[] buffer = new byte[(int) multipartFile.getSize()];
                        int readBytes;

                        while ((readBytes = is.read(buffer, 0, (int) multipartFile.getSize())) != -1) {
                            os.write(buffer, 0, readBytes);
                        }

                        is.close();
                        os.close();

                        // In a document: country, language and legalEntity can be null, except the documentType.

                        final CountryDto countryDto = getCountryFromForm(form.getCountry());
                        final LanguageDto languageDto = getLanguageFromForm(form.getLanguage());
                        final LegalEntityDto legalEntityDto = getLegalEntityFromForm(form.getLegalEntity());
                        final DocumentTypeDto documentTypeDto = getDocumentTypeFromForm(form.getDocumentType());

                        if (documentTypeDto != null) {
                            final DocumentDto documentDto = new DocumentDto(documentTypeDto, buffer, languageDto,
                                    countryDto, legalEntityDto, fileName, form.isResident());
                            documentDto.setXbasV2(form.isXbasV2());
                            form.setDocumentToSave(documentDto);

                            /****** RG *******/
                            // It's used to check if a document with the same parameters already exists
                            if (BooleanUtils.isFalse(form.isConfirm())) {
                                final String match = matchDocument(documentDto);

                                if (!match.equals(Constants.NO_MATCH)) {
                                    if (match.equals(Constants.MATCH_ALL)) {
                                        form.setMatchAll(true);
                                    } else if (match.equals(Constants.MATCH_TITLE)) {
                                        form.setMatch(true);
                                    }
                                    return WebConstants.ADD_DOCUMENTS;
                                }
                            }
                            /***************/

                            try {
                                BusinessHelper.call(Constants.CONTROLLER_DOCUMENT,
                                        Constants.CONTROLLER_DOCUMENTS_SAVE, new Object[] {documentDto});
                                addConfirm(this.propertiesHelper.getMessage("page.document.save.sucess"));
                            } catch (final DCOException e) {
                                if (e.getCode() == Constants.EXCEPTION_RECORDING_DATA) {
                                    addError(this.propertiesHelper.getMessage("recording.data.error"));
                                } else if (e.getCode() == Constants.EXCEPTION_ROLLBACK) {
                                    addError(this.propertiesHelper.getMessage("recording.data.error"));
                                    addError(this.propertiesHelper.getMessage("canceling.record.data.error"));
                                }
                            }

                        } else {
                            addError(this.propertiesHelper.getMessage("page.document.type.document.error"));
                            return WebConstants.ADD_DOCUMENTS;
                        }
                    } else {
                        addError(this.propertiesHelper.getMessage("page.document.multifile.size.error"));
                        return WebConstants.ADD_DOCUMENTS;
                    }
                } catch (final IOException e) {
                    LOG.error(e.getMessage());
                    addError(this.propertiesHelper.getMessage("page.document.download.error"));
                } finally {
                    if (os != null) {
                        try {
                            os.close();
                        } catch (final IOException e) {
                            LOG.error(e.getMessage());
                            addError(this.propertiesHelper.getMessage("page.document.download.error"));
                        }
                    }
                }
            } else {
                addError(this.propertiesHelper.getMessage("page.document.multifile.error"));
                return WebConstants.ADD_DOCUMENTS;
            }
        } else {
            addError(this.propertiesHelper.getMessage("page.document.file.not.valid"));
            return WebConstants.ADD_DOCUMENTS;
        }
        if (getDocumentFilterForm().getCountry() == 0) {
            getDocumentFilterForm().setDocumentList(null);
            return WebConstants.DOCUMENTS;
        }
        return loadModel(getDocumentFilterForm().getCountry(), Constants.VAR_NEG_ONE, Constants.VAR_NEG_ONE,
                Constants.VAR_NEG_ONE, Constants.EMPTY_FIELD, Constants.VAR_NEG_ONE, false, WebConstants.DOCUMENTS);
    }

    /**
     * @param documentDto
     * @return a string that indicated the type of similarity between the document documentDto and the matching
     *         document (if exists) in the form list.
     */
    private String matchDocument(final DocumentDto documentDto) {

        boolean countryMatch = false, languageMatch = false, legalEntityMatch = false, typeMatch = false, match = false;

        if (!newloadDocumentList(Constants.VAR_NEG_ONE, Constants.VAR_NEG_ONE, Constants.VAR_NEG_ONE,
                Constants.VAR_NEG_ONE, Constants.EMPTY_FIELD, Constants.VAR_NEG_ONE, false)) {
            return Constants.NO_MATCH;
        }
        final Iterator<DocumentDto> it = getDocumentFilterForm().getDocumentList().iterator();

        DocumentDto docToMatch = null;

        while (it.hasNext() && !(countryMatch && languageMatch && legalEntityMatch && typeMatch)) {
            docToMatch = it.next();

            countryMatch = false;
            languageMatch = false;
            legalEntityMatch = false;
            typeMatch = false;

            // Matching if the titles are the same.
            if (docToMatch.getTitle().equals(documentDto.getTitle()) && docToMatch.getXbasV2().equals(documentDto.getXbasV2()) && docToMatch.getResident().equals(documentDto.getResident())) {
                // Matching the countries.
                // All the country are represented when getCountry == null, so any country match to all
                // the
                // country (Same way for language...).
                if (docToMatch.getCountry() == null || documentDto.getCountry() == null) {
                    countryMatch = true;
                } else if (docToMatch.getCountry() != null && documentDto.getCountry() != null
                        && docToMatch.getCountry().getId().compareTo(documentDto.getCountry().getId()) == 0) {
                    countryMatch = true;
                }

                // Matching the Languages.
                if (docToMatch.getLanguage() == null || documentDto.getLanguage() == null) {
                    languageMatch = true;
                } else if (docToMatch.getLanguage() != null && documentDto.getLanguage() != null
                        && docToMatch.getLanguage().getId().compareTo(documentDto.getLanguage().getId()) == 0) {
                    languageMatch = true;
                }

                // Matching the Legal Entity.
                if (docToMatch.getLegalEntity() == null || documentDto.getLegalEntity() == null) {
                    legalEntityMatch = true;
                } else if (docToMatch.getLegalEntity() != null && documentDto.getLegalEntity() != null
                        && docToMatch.getLegalEntity().getId() == documentDto.getLegalEntity().getId()) {
                    legalEntityMatch = true;
                }

                // Matching the documents's type.
                if (docToMatch.getDocumentType().getId().compareTo(documentDto.getDocumentType().getId()) == 0) {
                    typeMatch = true;
                }

                // Same title
                match = true;
            }
        }

        String ret;

        if (countryMatch && languageMatch && legalEntityMatch && typeMatch) {
            // Same title and all options are the same, compared to another existing doc.
            ret = Constants.MATCH_ALL;
        } else if (match) {
            // Just same title
            ret = Constants.MATCH_TITLE;
        } else {
            ret = Constants.NO_MATCH;
        }

        return ret;

    }

    /**
     * Cancelling operation when user want to modify an existing document in the modifyDocument page.
     * @param form
     * @return
     * @throws InterruptedException
     * @throws IOException
     */
    @RequestMapping(value = {"/doCancelling"}, method = RequestMethod.GET)
    public String doCancelling(@ModelAttribute("documentFilterForm") final DocumentFilterForm form)
            throws InterruptedException, IOException {
        return findDocuments(form);
    }

    /**
     * If one checkBox is checked, this method load the new page with the attributs of the document's id referenced
     * by the checkBox.
     * @param id: document to modify
     * @return the modify document page
     * @throws InterruptedException
     */
    @RequestMapping(value = "/modifyDocument", method = RequestMethod.GET)
    public final String modifyDocument(final Integer id, final Model model) throws InterruptedException {

        final Iterator<DocumentDto> itDoc = getDocumentFilterForm().getDocumentList().iterator();
        boolean find = false;
        DocumentDto document = null;

        while (itDoc.hasNext() && !find) {
            final DocumentDto doc = itDoc.next();
            if (doc.getId().compareTo(id) == 0) {
                document = doc;
                find = true;
            }
        }

        if (document.getCountry() != null) {
            getDocumentForm().setCountry(document.getCountry().getId());
        } else {
            getDocumentForm().setCountry(Constants.VAR_NEG_ONE);
        }

        if (document.getLanguage() != null) {
            getDocumentForm().setLanguage(document.getLanguage().getId());
        } else {
            getDocumentForm().setLanguage(Constants.VAR_NEG_ONE);
        }

        if (document.getDocumentType() != null) {
            getDocumentForm().setDocumentType(document.getDocumentType().getId());
        }
        getDocumentForm().setDocumentTitle(document.getTitle());
        getDocumentForm().setDocumentTitleWithoutExt(document.getTitleWithOutExtension());
        getDocumentForm().setDocumentExtension(document.getTitleExtension());
        getDocumentForm().setId(id);
        if (document.getResident() != null) {
            getDocumentForm().setResident(document.getResident());
        }
        if (document.getXbasV2() != null) {
            getDocumentForm().setXbasV2(document.getXbasV2());
        }
        
        return WebConstants.MODIFY_DOCUMENTS;
    }

    /**
     * Method to modify an existing document in the database
     * @param id: document to modify
     * @return the document page
     * @throws InterruptedException final Integer country, final Integer language, final Integer documentType,
     *             final String documentTitle, final Integer id
     */
    @RequestMapping(value = "/doModifyDocument", method = RequestMethod.POST)
    public final String doModifyDocument(@Valid final DocumentForm form, final Model model)
            throws InterruptedException {
        boolean error = false;
        try {

            final Iterator<DocumentDto> itDoc = getDocumentFilterForm().getDocumentList().iterator();
            boolean find = false;
            DocumentDto document = null;

            while (itDoc.hasNext() && !find) {
                final DocumentDto doc = itDoc.next();
                if (doc.getId().compareTo(form.getId()) == 0) {
                    document = doc;
                    find = true;
                }
            }

            if (document != null) {

                document.setCountry(getCountryFromForm(form.getCountry()));
                document.setDocumentType(getDocumentTypeFromForm(form.getDocumentType()));
                document.setLanguage(getLanguageFromForm(form.getLanguage()));
                document.setLegalEntity(getLegalEntityFromForm(form.getLegalEntity()));
                final StringBuilder sbTitle = new StringBuilder(form.getDocumentTitleWithoutExt());
                sbTitle.append(".");
                sbTitle.append(form.getDocumentExtension());
                document.setTitle(sbTitle.toString());
                document.setTitleWithOutExtension(form.getDocumentTitleWithoutExt());
                document.setTitleExtension(form.getDocumentExtension());
                document.setResident(form.isResident());

                if (Constants.DOC_TITLE_MAX_LENGTH < document.getTitle().length()) {
                    addError(this.propertiesHelper.getMessage("page.document.title.too.long"));
                    error = true;
                }

                if (!error) {
                    BusinessHelper.call(Constants.CONTROLLER_DOCUMENT, Constants.CONTROLLER_DOCUMENTS_MODIFY,
                            new Object[] {document});
                }
            }
        } catch (final DCOException e) {
            LOG.error("Error occurs while modifying Document", e.getMessage());
        }
        if (getDocumentFilterForm().getCountry() == 0) {
            getDocumentFilterForm().setDocumentList(null);
            return WebConstants.DOCUMENTS;
        }
        return loadModel(getDocumentFilterForm().getCountry(), Constants.VAR_NEG_ONE, Constants.VAR_NEG_ONE,
                Constants.VAR_NEG_ONE, Constants.EMPTY_FIELD, Constants.VAR_NEG_ONE, false, WebConstants.DOCUMENTS);
    }

    /**
     * Method called to delete one or several documents
     * @param model
     * @return the document page
     * @throws InterruptedException
     * @throws IOException
     */
    @RequestMapping(value = "actionDocumentsChecked", method = RequestMethod.POST, params = "deleteDocumentsButton")
    public final String deleteDocumentsChecked(@Valid final DocumentsCheckedForm form, final Model model,
            final HttpServletResponse response) throws InterruptedException, IOException {
        if (form.getDocumentsChecked().length == 0) {
            addWarning(this.propertiesHelper.getMessage("page.document.delete.empty.selection"));
        } else {
            for (final Integer id : form.getDocumentsChecked()) {
                try {
                    BusinessHelper.call(Constants.CONTROLLER_DOCUMENT, Constants.CONTROLLER_DOCUMENTS_DELETE,
                            new Object[] {id});
                } catch (final DCOException e) {
                    LOG.error("Error occurs while deleting Document", e.getMessage());
                }
            }
        }

        return loadModel(Constants.VAR_NEG_ONE, Constants.VAR_NEG_ONE, Constants.VAR_NEG_ONE,
                Constants.VAR_NEG_ONE, Constants.EMPTY_FIELD, Constants.VAR_NEG_ONE, false, WebConstants.DOCUMENTS);
    }

    /**
     * This method was called to fetch the corresponding entity in function of the chosen country in the filter in
     * the document page. This method doesn't work anymore since we switch select to select2.
     * @param country id
     * @return entity List
     * @throws DCOException
     */
    @Deprecated
    @RequestMapping(value = "fetchLegalEntityFromCountry", method = RequestMethod.GET)
    public final @ResponseBody
    List<LegalEntityDto> getLegalEntityFromCountry(final Integer id) throws DCOException {

        final List<LegalEntityDto> list = (List<LegalEntityDto>) BusinessHelper.call(
                Constants.CONTROLLER_LEGAL_ENTITY, Constants.CONTROLLER_LIST, new Object[] {id});

        return list;

    }

    /**
     * This method was called to fetch the corresponding country in function of the chosen entity in the filter in
     * the document page. This method doesn't work anymore since we switch select to select2.
     * @param legal entity id
     * @return country List
     * @throws DCOException
     */
    @Deprecated
    @RequestMapping(value = "fetchCountryFromLegalEntity", method = RequestMethod.GET)
    public final @ResponseBody
    List<AjaxDto> getCountryFromLegalEntity(final Integer id) throws DCOException {

        final List<CountryDto> list = (List<CountryDto>) BusinessHelper.call(Constants.CONTROLLER_COUNTRY,
                Constants.CONTROLLER_COUNTRY_FILTER_LEGAL_ENTITY, new Object[] {id});

        final List<AjaxDto> ajaxList = new ArrayList<AjaxDto>();

        final Locale locale = UserHelper.getUserInSession().getPreferences().getLocale();

        for (final CountryDto c : list) {
            final AjaxDto a = new AjaxDto(c.getId(), c.getLocale().getDisplayCountry(locale));
            ajaxList.add(a);
        }
        Collections.sort(ajaxList, new AjaxDto.OrderByLabel());
        return ajaxList;

    }

    @RequestMapping(value = "/returnDocumentError", method = RequestMethod.GET)
    public String getUploadForm() {
        return WebConstants.ADD_DOCUMENTS;
    }

    /**
     * Allow to increment the user researches document stats.
     */
    private void incrementUserStat() {
        try {
            BusinessHelper.call(Constants.CONTROLLER_USER, Constants.CONTROLLER_USER_INCREMENT_STATS_SEARCH_DOC,
                    new Object[] {UserHelper.getUserInSession().getLogin()});
        } catch (final DCOException e) {
            LOG.error(this.propertiesHelper.getMessage("page.stats.record.error"));
            addError(this.propertiesHelper.getMessage("page.stats.record.error"));
        }
    }

    /**
     * Allow to increment the document researches stats by country / legalEntity / language / typeDocument.
     */
    private void incrementDocStat(final CountryDto country, final LegalEntityDto legal,
            final LanguageDto language, final DocumentTypeDto type) {
        try {
            BusinessHelper.call(Constants.CONTROLLER_STATISTICS, Constants.CONTROLLER_STATS_INCREMENT_DOC_STATS,
                    new Object[] {country, legal, language, type});
        } catch (final DCOException e) {
            LOG.error(this.propertiesHelper.getMessage("page.stats.record.error"));
            addError(this.propertiesHelper.getMessage("page.stats.record.error"));
        }
    }

    /*
     * Get the country from the form.
     */
    private CountryDto getCountryFromForm(final int countryId) {
        CountryDto countryDto = null;
        if (countryId != -1) {
            final Iterator<CountryDto> itCountry = getDocumentFilterForm().getCountryList().iterator();
            while (itCountry.hasNext()) {
                countryDto = itCountry.next();
                if (countryDto.getId().compareTo(countryId) == 0) {
                    break;
                }
            }
        }
        return countryDto;
    }

    /*
     * Get the country id from the name.
     */
    private int getCountryIdFromName(final String countryName) {
        CountryDto countryDto = null;
        if (countryName != null) {
            final Iterator<CountryDto> itCountry = getDocumentFilterForm().getCountryList().iterator();
            while (itCountry.hasNext()) {
                countryDto = itCountry.next();
                if (countryDto.getLocale().getCountry().equals(countryName)) {
                    break;
                }
            }
        }
        return countryDto != null ? countryDto.getId() : Constants.VAR_NEG_ONE;
    }

    /*
     * Get the Language from the form.
     */
    private LanguageDto getLanguageFromForm(final int languageId) {
        LanguageDto languageDto = null;
        if (languageId != -1) {
            final Iterator<LanguageDto> itLanguage = getDocumentFilterForm().getLanguageList().iterator();
            while (itLanguage.hasNext()) {
                languageDto = itLanguage.next();
                if (languageDto.getId().compareTo(languageId) == 0) {
                    break;
                }
            }
        }
        return languageDto;
    }

    /*
     * Get the Legal Entity from the form.
     */
    private LegalEntityDto getLegalEntityFromForm(final int legal) {
        LegalEntityDto legalEntityDto = null;
        if (legal != -1) {
            final Iterator<LegalEntityDto> itLegalEntity = getDocumentFilterForm().getLegalEntityList().iterator();
            while (itLegalEntity.hasNext()) {
                legalEntityDto = itLegalEntity.next();
                if (legalEntityDto.getId() == legal) {
                    break;
                }
            }
        }
        return legalEntityDto;
    }

    /*
     * Get the document Type from the form.
     */
    private DocumentTypeDto getDocumentTypeFromForm(final int type) {
        DocumentTypeDto documentTypeDto = null;
        if (getDocumentFilterForm().getDocumentTypeList().size() > Constants.VAR_ZERO) {
            final Iterator<DocumentTypeDto> itType = getDocumentFilterForm().getDocumentTypeList().iterator();
            while (itType.hasNext()) {
                documentTypeDto = itType.next();
                if (documentTypeDto.getId().compareTo(type) == 0) {
                    break;
                } else {
                    documentTypeDto = null;
                }
            }
        }
        return documentTypeDto;
    }

    @RequestMapping(value = "/downloadDocumentsChecked", method = RequestMethod.GET)
    public String getDocumentsChecked(final String id, final HttpServletResponse response, final Model model)
            throws DCOException {
        String result = WebConstants.DOCUMENTS;
        if (StringUtils.isNotBlank(id)) {
            final String[] listIdsString = id.split(";");
            if (listIdsString == null || listIdsString.length == 0) {
                LOG.error("Invalid list of document id for download");
                addError(this.propertiesHelper.getMessage("page.document.download.error"));
            } else {
                try {
                    final Integer[] ids = new Integer[listIdsString.length];
                    for (int i = 0; i < listIdsString.length; i++) {
                        ids[i] = Integer.valueOf(listIdsString[i]);
                    }
                    final List<DocumentDto> documentList = (List<DocumentDto>) BusinessHelper.call(
                            Constants.CONTROLLER_DOCUMENT, Constants.CONTROLLER_LIST, new Object[] {
                                    Constants.VAR_NEG_ONE, Constants.VAR_NEG_ONE, Constants.VAR_NEG_ONE,
                                    Constants.VAR_NEG_ONE, Constants.EMPTY_FIELD, ids, null, true, null});
                    downloadDocuments(response, documentList);
                    result = null;
                } catch (final NumberFormatException e) {
                    LOG.error("Invalid document id for download", e);
                } catch (final DCOException e) {
                    if (e.getCode() == Constants.EXCEPTION_FILE_NOT_FOUND) {
                        addError(this.propertiesHelper.getMessage("page.document.file.error"));
                    } else if (e.getCode() == Constants.EXCEPTION_IO) {
                        addError(this.propertiesHelper.getMessage("page.document.download.error"));
                    }
                }
            }
        } else {
            LOG.error("Empty list of document id for download");
            addError(this.propertiesHelper.getMessage("page.document.download.error"));
        }
        return result;
    }

    /**
     * Check if the file content type id PDF or Word or Excel or Powerpoint file
     * @param multipartFile
     */
    private boolean checkFileContentType(final MultipartFile multipartFile) {
        final String fileName = multipartFile.getOriginalFilename();
        final String extensionOfFileName = multipartFile.getOriginalFilename().substring(
                fileName.indexOf(".") + 1, fileName.length());
        if (extensionOfFileName != null
                && (extensionOfFileName.equalsIgnoreCase("pdf") || extensionOfFileName.equalsIgnoreCase("doc")
                        || extensionOfFileName.equalsIgnoreCase("docx")
                        || extensionOfFileName.equalsIgnoreCase("ppt")
                        || extensionOfFileName.equalsIgnoreCase("pptx")
                        || extensionOfFileName.equalsIgnoreCase("xls") || extensionOfFileName
                            .equalsIgnoreCase("xlsx"))) {
            return true;
        } else {
            LOG.info("File type {} is not correct", extensionOfFileName == null ? "null" : extensionOfFileName);
            return false;
        }
    }
}
