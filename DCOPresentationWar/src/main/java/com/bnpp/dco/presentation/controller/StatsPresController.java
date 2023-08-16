package com.bnpp.dco.presentation.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bnpp.dco.common.constant.Constants;
import com.bnpp.dco.common.dto.CountryDto;
import com.bnpp.dco.common.dto.StatisticsDto;
import com.bnpp.dco.common.dto.UserDto;
import com.bnpp.dco.common.exception.DCOException;
import com.bnpp.dco.common.utils.cvs.CSVList;
import com.bnpp.dco.common.utils.cvs.CountryCSV;
import com.bnpp.dco.common.utils.cvs.DocumentCSV;
import com.bnpp.dco.common.utils.cvs.EntityFormCSV;
import com.bnpp.dco.common.utils.cvs.EntityUsersCSV;
import com.bnpp.dco.common.utils.cvs.UserCSV;
import com.bnpp.dco.presentation.form.StatsForm;
import com.bnpp.dco.presentation.utils.BusinessHelper;
import com.bnpp.dco.presentation.utils.PropertiesHelper;
import com.bnpp.dco.presentation.utils.UserHelper;
import com.bnpp.dco.presentation.utils.constants.WebConstants;

@Controller
public class StatsPresController extends GenericController {

    @Autowired
    private StatsForm statsForm;

    @ModelAttribute("statsForm")
    public StatsForm getStatsForm() {
        return this.statsForm;
    }

    @Autowired
    private PropertiesHelper propertiesHelper;

    @RequestMapping(value = "statsLoad", method = RequestMethod.GET)
    public final String doStatsLoad() {
        getStatsForm().setTypeStat(Constants.VAR_ZERO);
        return WebConstants.STATS_LOAD;
    }

    /**
     * Allow to download the checked stats in statsForm, in the stats Page.
     * @param statsForm
     * @param response
     */
    @RequestMapping(value = "statsCSV", method = RequestMethod.POST)
    public final String doStatsLoad(@ModelAttribute("statsForm") final StatsForm statsForm,
            final HttpServletResponse response) {

        String ret = WebConstants.STATS_LOAD;
        if (statsForm.getTypeStat() == Constants.VAR_ZERO) {
            addError(this.propertiesHelper.getMessage("page.stats.no.choice"));
        } else {
            try {
                getStats(statsForm, response);
                ret = "redirect:" + ret;
            } catch (final DCOException e) {
                addError(this.propertiesHelper.getMessage("page.stats.get.stats.error"));
            }
        }
        return ret;
    }

    /**
     * Get the specified stats from the biz, and put it in the form.
     * @param statsForm
     * @throws DCOException
     */
    private void getStats(final StatsForm statsForm, final HttpServletResponse response) throws DCOException {

        String csvText = StringUtils.EMPTY;
        String fileName = StringUtils.EMPTY;
        try {

            switch (statsForm.getTypeStat()) {
            case 1:
                final List<StatisticsDto> list1 = getStatsDocuments();
                csvText = generateCSVstatsDocuments(list1);
                fileName = "document.csv";
                break;
            case 2:
                final Map<String, List<UserDto>> map2 = usersByEntity();
                csvText = generateCSVstatsUserByEntity(map2);
                fileName = "userByEntity.csv";
                break;
            case 3:
                final Map<UserDto, Integer> map3 = getStatsUsers(Constants.STATS_TYPE_CONNECTION);
                csvText = generateCSVstatsUser(map3);
                fileName = "connection.csv";
                break;
            case 4:
                final Map<UserDto, Integer> map4 = getStatsUsers(Constants.STATS_TYPE_SEARCH);
                csvText = generateCSVstatsUser(map4);
                fileName = "searchDocByUser.csv";
                break;
            case 5:
                final Map<Object, Integer> map5 = getStatsFormCountry();
                csvText = generateCSVstatsCountry(map5);
                fileName = "formByCountry.csv";
                break;
            case 6:
                final Map<String, Integer> map6 = getStatsEntitiesForm();
                csvText = generateCSVstatsEntity(map6);
                fileName = "formByEntity.csv";
                break;
            case 7:
                final Map<Object, Integer> map7 = getStatsCountry(Constants.STATS_TYPE_PRINT_COUNTRY);
                csvText = generateCSVstatsCountry(map7);
                fileName = "printedDocumentsByCountry.csv";
                break;
            default:
                addError(this.propertiesHelper.getMessage("page.proceding"));
            }

            if (StringUtils.isNotBlank(csvText)) {
                statsForm.setCsv(csvText);
                statsForm.setFileName(fileName);
            } else {
                addWarning(this.propertiesHelper.getMessage("page.stats.csv.nothing.to.generate"));
            }

        } catch (IllegalArgumentException | IllegalAccessException e) {
            addError(this.propertiesHelper.getMessage("page.stats.csv.generate.error"));
        }
    }

    /**
     * Method to upload the csv file stored in the form.
     * @param statsForm
     * @param response
     */
    @RequestMapping(value = "getCSV", method = RequestMethod.GET)
    public final void getCSV(final HttpServletResponse response) {

        try {

            final String csv = this.statsForm.getCsv();
            final String fileName = this.statsForm.getFileName();

            this.statsForm.setCsv("");
            this.statsForm.setFileName("");

            downloadCSV(csv, fileName, response);
        } catch (DCOException | IOException e) {
            addError(this.propertiesHelper.getMessage("page.stats.get.stats.download.error"));
        }

    }

    /**
     * Generate the csv for type 3 / 4
     */
    private String generateCSVstatsUser(final Map<UserDto, Integer> map) throws IllegalArgumentException,
            IllegalAccessException {

        final CSVList<UserCSV> userCSVs = new CSVList<UserCSV>();
        if (map != null && map.size() > 0) {

            final UserCSV userCSVInit = new UserCSV();
            userCSVInit.setNameColumn1Str(this.propertiesHelper.getMessage("csv.user.column.user"));
            userCSVInit.setNameColumn2Int(this.propertiesHelper.getMessage("csv.user.column.number"));
            userCSVs.add(userCSVInit);

            for (final Entry<UserDto, Integer> entry : map.entrySet()) {
                final UserDto user = entry.getKey();
                final Integer number = entry.getValue();
                final UserCSV userCSV = new UserCSV();
                userCSV.setNameColumn1Str(user.getLogin());
                userCSV.setNameColumn2Int(Integer.toString(number.intValue()));
                userCSVs.add(userCSV);
            }
        }
        return userCSVs.toCSV();
    }

    /**
     * Generate the csv for type 2
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     */
    private String generateCSVstatsUserByEntity(final Map<String, List<UserDto>> map)
            throws IllegalArgumentException, IllegalAccessException {

        final CSVList<EntityUsersCSV> entitiesCSVs = new CSVList<EntityUsersCSV>();
        if (map != null && map.size() > 0) {

            final EntityUsersCSV entityUsersCSVInit = new EntityUsersCSV();
            entityUsersCSVInit.setNameColumn1Str(this.propertiesHelper.getMessage("csv.entityUser.colum.entity"));
            entityUsersCSVInit.setNameColumn2Str(this.propertiesHelper.getMessage("csv.entityUser.colum.list"));
            entitiesCSVs.add(entityUsersCSVInit);

            for (final Entry<String, List<UserDto>> entry : map.entrySet()) {
                final String entity = entry.getKey();
                final List<UserDto> list = entry.getValue();
                final EntityUsersCSV entityCSV = new EntityUsersCSV();
                entityCSV.setNameColumn1Str(entity);

                final StringBuilder st = new StringBuilder();
                int index = 0;
                for (final UserDto user : list) {
                    if (index > 0) {
                        st.append(" - ");
                    }
                    if (user != null) {
                        st.append(user.getLogin());
                    }
                    index++;
                }
                entityCSV.setNameColumn2Str(st.toString());
                entitiesCSVs.add(entityCSV);
            }
        }
        return entitiesCSVs.toCSV();

    }

    /**
     * Generate the csv for type 5
     */
    private String generateCSVstatsCountry(final Map<Object, Integer> map) throws IllegalArgumentException,
            IllegalAccessException {

        final CSVList<CountryCSV> countriesCSVs = new CSVList<CountryCSV>();
        if (map != null && map.size() > 0) {

            final CountryCSV countryCSVInit = new CountryCSV();
            countryCSVInit.setNameColumn1Str(this.propertiesHelper.getMessage("csv.country.column.country"));
            countryCSVInit.setNameColumn2Int(this.propertiesHelper.getMessage("csv.country.column.number"));
            countriesCSVs.add(countryCSVInit);

            final Locale locale = UserHelper.getUserInSession().getPreferences().getLocale();

            for (final Entry<Object, Integer> entry : map.entrySet()) {

                final Integer number = entry.getValue();
                final CountryCSV countryCSV = new CountryCSV();

                if (entry.getKey() instanceof CountryDto) {
                    final CountryDto country = (CountryDto) entry.getKey();
                    countryCSV.setNameColumn1Str(country.getLocale().getDisplayCountry(locale));
                } else if (entry.getKey() instanceof String) {
                    final String countryLocale = (String) entry.getKey();
                    final Locale l = new Locale("", countryLocale);
                    countryCSV.setNameColumn1Str(l.getDisplayCountry(locale));
                }
                countryCSV.setNameColumn2Int(Integer.toString(number.intValue()));
                countriesCSVs.add(countryCSV);
            }
        }
        return countriesCSVs.toCSV();
    }

    /**
     * Generate the csv for type 6
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     */
    private String generateCSVstatsEntity(final Map<String, Integer> map) throws IllegalArgumentException,
            IllegalAccessException {

        final CSVList<EntityFormCSV> entitiesCSVs = new CSVList<EntityFormCSV>();
        if (map != null && map.size() > 0) {

            final EntityFormCSV entityCSVInit = new EntityFormCSV();
            entityCSVInit.setNameColumn1Str(this.propertiesHelper.getMessage("csv.entity.column.entity"));
            entityCSVInit.setNameColumn2Int(this.propertiesHelper.getMessage("csv.entity.column.number"));
            entitiesCSVs.add(entityCSVInit);

            for (final Entry<String, Integer> entry : map.entrySet()) {
                final EntityFormCSV entityCSV = new EntityFormCSV();
                entityCSV.setNameColumn1Str(entry.getKey());
                entityCSV.setNameColumn2Int(Integer.toString(entry.getValue().intValue()));
                entitiesCSVs.add(entityCSV);
            }
        }
        return entitiesCSVs.toCSV();

    }

    /**
     * Return the list in parameters, matches with the statistic of documents (type 1).
     * @param list
     * @return the CSV file for the document stats.
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     */
    private String generateCSVstatsDocuments(final List<StatisticsDto> list) throws IllegalArgumentException,
            IllegalAccessException {

        final CSVList<DocumentCSV> documentsCSVs = new CSVList<DocumentCSV>();
        if (list != null && list.size() > 0) {

            final DocumentCSV documentCSVInit = new DocumentCSV();
            documentCSVInit.setNameColumn1Str(this.propertiesHelper.getMessage("csv.document.column.country"));
            documentCSVInit.setNameColumn2Str(this.propertiesHelper.getMessage("csv.document.column.language"));
            documentCSVInit.setNameColumn3Str(this.propertiesHelper.getMessage("csv.document.column.legal"));
            documentCSVInit.setNameColumn4Str(this.propertiesHelper.getMessage("csv.document.column.type"));
            documentCSVInit.setNameColumn2Int(this.propertiesHelper.getMessage("csv.document.column.number"));
            documentsCSVs.add(documentCSVInit);

            final Locale locale = UserHelper.getUserInSession().getPreferences().getLocale();

            for (final StatisticsDto stat : list) {

                final DocumentCSV documentCSV = new DocumentCSV();

                if (stat.getCountry() != null) {
                    documentCSV.setNameColumn1Str(stat.getCountry().getLocale().getDisplayCountry(locale));
                } else {
                    documentCSV.setNameColumn1Str("*");
                }

                if (stat.getLanguage() != null) {
                    documentCSV.setNameColumn2Str(stat.getLanguage().getLocale().getDisplayLanguage(locale));
                } else {
                    documentCSV.setNameColumn2Str("*");
                }

                if (stat.getLegalEntity() != null) {
                    documentCSV.setNameColumn3Str(stat.getLegalEntity().getLabel());
                } else {
                    documentCSV.setNameColumn3Str("*");
                }

                if (stat.getDocumentType() != null) {
                    documentCSV.setNameColumn4Str(stat.getDocumentType().getLabel());
                } else {
                    documentCSV.setNameColumn4Str("*");
                }

                documentCSV.setNameColumn2Int(Integer.toString(stat.getNumber().intValue()));
                documentsCSVs.add(documentCSV);
            }
        }
        return documentsCSVs.toCSV();

    }

    /**
     * Allow to downLoad a csv doc.
     * @param response
     * @param csvDoc
     * @throws DCOException
     * @throws IOException
     */
    private void downloadCSV(final String text, final String fileName, final HttpServletResponse response)
            throws DCOException, IOException {

        response.setHeader("Content-Type", "application/force-download");
        response.setHeader("Cache-Control", "public");
        response.setHeader("Pragma", "private");
        response.setHeader("Expires", "0");
        final OutputStream os = response.getOutputStream();
        if (os != null && text != null) {
            response.setContentLength(text.length());
            response.setHeader("Content-Disposition", "attachment;filename=\"" + fileName + "\"");
            os.write(text.getBytes(), 0, text.length());
            os.close();
        }
    }

    /**
     * Get the user Stats (type 3 / 4).
     * @param type
     * @throws DCOException
     */
    private Map<UserDto, Integer> getStatsUsers(final Integer type) throws DCOException {
        return (Map<UserDto, Integer>) BusinessHelper.call(Constants.CONTROLLER_STATISTICS,
                Constants.CONTROLLER_STATS_GET_STATS_USERS, new Object[] {type});
    }

    /**
     * Get the countries Stats: number of printed doc by country (type 7).
     * @param type
     * @throws DCOException
     */
    private Map<Object, Integer> getStatsCountry(final int type) throws DCOException {
        return (Map<Object, Integer>) BusinessHelper.call(Constants.CONTROLLER_STATISTICS,
                Constants.CONTROLLER_STATS_GET_STATS_PRINT, new Object[] {type});
    }

    /**
     * Get the countries Stats: number of form by country (type 5)
     * @param type
     * @throws DCOException
     */
    private Map<Object, Integer> getStatsFormCountry() throws DCOException {
        return (Map<Object, Integer>) BusinessHelper.call(Constants.CONTROLLER_STATISTICS,
                Constants.CONTROLLER_STATS_GET_STATS_FORM_COUNTRY, new Object[] {});
    }

    /**
     * Get the entity Form Stats (type 6).
     * @param type
     * @throws DCOException
     */
    private Map<String, Integer> getStatsEntitiesForm() throws DCOException {
        return (Map<String, Integer>) BusinessHelper.call(Constants.CONTROLLER_STATISTICS,
                Constants.CONTROLLER_STATS_GET_STATS_FORM_ENTITY, new Object[] {});
    }

    /**
     * Get the documents Stats (type 1).
     * @param type
     * @throws DCOException
     */
    private List<StatisticsDto> getStatsDocuments() throws DCOException {
        return (List<StatisticsDto>) BusinessHelper.call(Constants.CONTROLLER_STATISTICS,
                Constants.CONTROLLER_STATS_GET_STATS_DOCUMENTS, new Object[] {});
    }

    /**
     * Get the user by entity Stats (type 2).
     * @param type
     * @throws DCOException
     */
    private Map<String, List<UserDto>> usersByEntity() throws DCOException {
        return (Map<String, List<UserDto>>) BusinessHelper.call(Constants.CONTROLLER_STATISTICS,
                Constants.CONTROLLER_STATS_GET_USER_BY_LEGAL_ENTITY, new Object[] {});
    }
}
