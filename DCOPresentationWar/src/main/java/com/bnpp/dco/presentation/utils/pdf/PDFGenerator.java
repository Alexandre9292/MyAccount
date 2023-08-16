package com.bnpp.dco.presentation.utils.pdf;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Component;

import com.bnpp.dco.common.constant.Constants;
import com.bnpp.dco.common.dto.AjaxDto;
import com.bnpp.dco.common.dto.RulesDto;
import com.bnpp.dco.common.exception.DCOException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

@Component
public class PDFGenerator {

    public static final float MARGIN_LEFT = 36;
    public static final float MARGIN_RIGHT = 36;
    public static final float MARGIN_TOP = 70;
    public static final float MARGIN_BOTTOM = MARGIN_TOP;

    public static final String STR_SPACE = " ";

    public static final Color TAB_HEADER = new Color(44, 100, 74);
    public static final Color COL_RED = new Color(150, 54, 52);
    public static final Color COL_BLUE = new Color(0, 110, 210);
    public static final Color COL_GRAY = new Color(192, 192, 192);

    public static final String FONTNAME = "Arial";

    private static final int ARIAL_MEDIUM_FONT_SIZE = 8;
    private static final int ARIAL_HIGH_FONT_SIZE = 10;
    private static final int PADDING_BOTTOM = 3;
    private static final int TAB_WIDTH_PERCENT = 100;
    private static final int TAB_WIDTH_COL1_SIZE = 40;
    private static final int TAB_WIDTH_COL2_SIZE = 60;
    private static final int TAB_WIDTH_COL1_GLOSSARY = 30;
    private static final int TAB_WIDTH_COL2_GLOSSARY = 70;
    private static final int BORDER_BOLD_FACTOR = 4;

    private static final float TAB_BORDER_WIDTH = 0.8f;

    private static final Font ARIAL = FontFactory.getFont(FONTNAME, ARIAL_MEDIUM_FONT_SIZE);
    private static final Font ARIAL_BOLD = FontFactory.getFont(FONTNAME, ARIAL_MEDIUM_FONT_SIZE, Font.BOLD);
    private static final Font ARIAL_WHITE = FontFactory.getFont(FONTNAME, ARIAL_MEDIUM_FONT_SIZE, Color.WHITE);
    private static final Font ARIAL_ITALIC = FontFactory.getFont(FONTNAME, ARIAL_MEDIUM_FONT_SIZE, Font.ITALIC);
    private static final Font ARIAL_TITLE = FontFactory.getFont(FONTNAME, ARIAL_HIGH_FONT_SIZE, Font.BOLD,
            TAB_HEADER);
    private static final Font ARIAL_MAINTITLE = FontFactory.getFont(FONTNAME, ARIAL_HIGH_FONT_SIZE, Font.BOLD,
            TAB_HEADER);

    @Autowired
    private PDFPropertiesHelper propertiesHelper;

    private Color colorCol1 = null;

    private boolean noBorderTopBottom = false;
    private boolean dataToRight = false;

    private boolean noBorderCol1 = false;
    private boolean noBorder = false;

    /***************************************************************************************************************
     ******************************************** _____AORF____ ****************************************************
     **************************************************************************************************************/
    /**
     * Creates a PDF.
     * @throws DocumentException
     * @throws IOException
     * @throws DCOException
     */
    public ByteArrayOutputStream createPdf(final PDFBean bean) throws IOException, DocumentException, DCOException {
        final long time = System.currentTimeMillis();
        // Setting the top and bottom margins to 70px
        final Document document = new Document(PageSize.A4, MARGIN_LEFT, MARGIN_RIGHT, MARGIN_TOP, MARGIN_BOTTOM);
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final PdfWriter writer = PdfWriter.getInstance(document, baos);
        final SimpleDateFormat dateFormat = new SimpleDateFormat(this.propertiesHelper.getMessage(
                "pdf.footer.message.center", bean.getLanguage()), bean.getLanguage());
        final PDFGeneratorEventListener event = new PDFGeneratorEventListener(bean,
                this.propertiesHelper.getMessage("pdf.footer.message.left", bean.getLanguage()), bean.getCountry()
                        .getDisplayCountry(bean.getLanguage()) + dateFormat.format(new Date(time)),
                this.propertiesHelper.getMessage("pdf.footer.message.right", bean.getLanguage()));
        writer.setPageEvent(event);
        // Generating document
        document.open();

        final Phrase para = new Phrase(this.propertiesHelper.getMessage("pdf.account.country.bank.maintitle",
                bean.getLanguage()), ARIAL_MAINTITLE);
        // add title
        final PdfPCell mainTitleCell = new PdfPCell(para);
        mainTitleCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        mainTitleCell.setBorder(Rectangle.NO_BORDER);
        final PdfPTable mainTitleTable = new PdfPTable(1);
        mainTitleTable.setWidthPercentage(TAB_WIDTH_PERCENT);
        mainTitleTable.addCell(mainTitleCell);
        document.add(mainTitleTable);

        // Country of Account and Bank!!!
        document.add(new Phrase(STR_SPACE));
        addTitle(document, this.propertiesHelper.getMessage("pdf.account.country.bank.title", bean.getLanguage()),
                true, true);
        addAccountCountryBankInfo(document, bean.getLanguage(), bean.getEntity());

        // Add Customer Information (referred to Entity)
        addTitle(document, this.propertiesHelper.getMessage("pdf.customer.title", bean.getLanguage()), true, true);
        addCustomerInfo(document, bean.getLanguage(), bean.getEntity(), bean.getCountry());
        document.add(new Phrase(STR_SPACE));

        // Add Contact Details

        addTitle(document, this.propertiesHelper.getMessage("pdf.contact.title", bean.getLanguage()), false, true);
        addContactDetails(document, bean.getLanguage(), bean.getEntity());
        document.add(new Phrase(STR_SPACE));
        
        // Add branch Address and Account Request ( all if more...)
        // to go through new page!
        if (bean.getAccounts() != null) {
//            for (final PDFBeanAccount account : bean.getAccounts()) {
//                // Add Subsidiary details IN CASE it has been filled for the current account
//                if (StringUtils.isNotBlank(account.getCommercialRegister())
//                        && StringUtils.isNotBlank(account.getVatNumber())
//                        && StringUtils.isNotBlank(account.getBranchName())
//                        && StringUtils.isNotBlank(account.getAddress().getLine1())
//                        && StringUtils.isNotBlank(account.getAddress().getLine2())
//                        && StringUtils.isNotBlank(account.getAddress().getLine3())
//                        && StringUtils.isNotBlank(account.getAddress().getLine4())) {
//                    addAccountSubsidiary(document, bean.getLanguage(), account);
//                    document.newPage();
//                    break;
//                }
//            }
            
            final PdfPTable table = addTitle(document,
                    this.propertiesHelper.getMessage("pdf.account.title", bean.getLanguage()), false, false);

            final String intro = this.propertiesHelper.getMessage("pdf.account.request.intro" + "_"
                    + bean.getCountry().getCountry(), bean.getLanguage());
            if (StringUtils.isNotBlank(intro)) {
                final PdfPCell cell1 = new PdfPCell(new Phrase(intro, ARIAL));
                cell1.setBorder(Rectangle.NO_BORDER);
                cell1.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
                table.addCell(cell1);

                if (bean.getCountry().getCountry().equals("PL")) {
                    final PdfPCell cell2 = new PdfPCell();
                    cell2.setCellEvent(new PdfCheckboxCellEvent("account_request_pl"));
                    cell2.setMinimumHeight(PdfCheckboxCellEvent.CHECKBOX_SIZE);
                    cell2.setFixedHeight(PdfCheckboxCellEvent.CHECKBOX_SIZE);
                    cell2.setPadding(2);
                    cell2.setBorder(Rectangle.NO_BORDER);
                    table.addCell(cell2);
                }
                final PdfPCell cell3 = new PdfPCell(new Phrase(STR_SPACE, ARIAL));
                cell3.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell3);
            }

            document.newPage();
            int accountCounter = 1;
            for (final PDFBeanAccount account : bean.getAccounts()) {
                addAccount(document, bean.getLanguage(), bean.getCountry(), account, accountCounter,
                        accountCounter == 1 ? table : null);
                document.add(new Phrase(STR_SPACE));
                accountCounter++;
            }
            
            final PdfPTable table3 = new PdfPTable(3);
            //notes
            table3.addCell(createSpanTwoCell(this.propertiesHelper.getMessage("pdf.account.label.note", bean.getLanguage()),
                    Rectangle.NO_BORDER, null));
            table3.addCell(createSpanTwoCell(STR_SPACE, Rectangle.NO_BORDER, null));
            table3.addCell(createSpanTwoCell(this.propertiesHelper.getMessage("pdf.account.label.note2", bean.getLanguage()),
                    Rectangle.NO_BORDER, null));
            table3.addCell(createSpanTwoCell(STR_SPACE, Rectangle.NO_BORDER, null));
            
            document.add(table3);
        }

        // Add Account Agreements and supporting Documents
        document.newPage();
        addTitle(document, this.propertiesHelper.getMessage("pdf.account.agreements.title", bean.getLanguage()),
                true, true);
        addAccountAgreements(document, bean.getLanguage(), bean.getCountry(), bean.getEntity()
                .getRegistrationCountry(), bean.getXbasV2());
        document.add(new Phrase(STR_SPACE));

        // Add Instructions of Bank
        addTitle(document, this.propertiesHelper.getMessage("pdf.bank.instructions.title", bean.getLanguage()),
                false, true);
        addInstructionsBank(document, bean.getLanguage());
        document.add(new Phrase(STR_SPACE));

        // PRINT ONLY IF THE SIGNATORIES DECLARATION HAS NOT BEEN DESIGNED IN A DISTINCT LEGAL ACT(AS IN UK,
        // IRELAND and SPAIN)
        // Add Authorized Signatories
        
        if(bean.getAccounts().get(0).getStrategyDocument().equals("Yes")){
        	addTitle(
                    document,
                    this.propertiesHelper.getMessage("pdf.signature.card.authorized.signatories.title",
                            bean.getLanguage()), false, true);
            addAuthorizedSignatoriesSignatureCard(document, bean.getLanguage(), bean.getDateFormat(),
                    bean.getAccounts(), bean.getCountry(), bean.getSignatureCard());
    	}else{
    		addTitle(document, this.propertiesHelper.getMessage("pdf.account.signatories.title", bean.getLanguage()),
                    false, true);
            addAuthorizedSignatories(document, bean.getLanguage(), bean.getDateFormat(), bean.getAccounts(),
                    bean.getAuthorizedSignatories());
            document.add(new Phrase(STR_SPACE));
    	}
        
        
        // Add Customer declarations: start a new page (layout form structured better)
        document.newPage();
        addTitle(document,
                this.propertiesHelper.getMessage("pdf.customer.declarations.title", bean.getLanguage()), false,
                true);
        addCustomerDeclarations(document, bean.getLanguage(), bean);
        document.add(new Phrase(STR_SPACE));

        // Add Personal Data: a shortcut to open a new page if the current page is almost full-filled!
        addTitle(document,
                this.propertiesHelper.getMessage("pdf.customer.personal.data.title", bean.getLanguage()), true,
                true);
        addPersonalData(document, bean.getLanguage(), bean);
        document.add(new Phrase(STR_SPACE));

        document.newPage();

        // Add Glossary
        addTitle(document, this.propertiesHelper.getMessage("pdf.bank.glossary.title", bean.getLanguage()), false,
                true);
        addGlossary(document, bean.getLanguage(), bean);
        addCustomerRepresentatives(document, bean.getLanguage(), bean);

       // document.newPage();
        
//        for(byte[] doc : docs){
//        	document.add(doc);
//        }
        
        document.close();
        return baos;
    }

    /**
     * Create a suffix or root of pdf file name.
     * @param bean
     * @return String filename "suffix"
     * @throws IOException
     * @throws DCOException
     */
    public String getFilename(final PDFBean bean) throws IOException, DCOException {
        final String filename = this.propertiesHelper.getMessage("pdf.bank.form.filename.suffix",
                bean.getLanguage());
        return filename;
    }

    /**
     * Creates a table for an account.
     * @param document the Document to write to
     * @throws DocumentException
     */
    private void addAccountCountryBankInfo(final Document document, final Locale locale, final PDFBeanEntity entity)
            throws DocumentException {
        final Locale country = entity.getCountry();
        // Creating the data table
        final PdfPTable table = new PdfPTable(2);
        // Filling the table
        table.setWidthPercentage(TAB_WIDTH_PERCENT);
        table.setWidths(new float[] {TAB_WIDTH_COL1_SIZE, TAB_WIDTH_COL2_SIZE});

        // Add country of account
        // background color of columns
        this.colorCol1 = COL_GRAY;
        add2ColsRow(table, this.propertiesHelper.getMessage("pdf.account.country.bank.location", locale),
                country.getDisplayCountry(locale));

        add2ColsRow(table, this.propertiesHelper.getMessage("pdf.account.country.bank.legal.entity", locale),
                entity.getLegalEntity() == null ? "" : entity.getLegalEntity().getLabel());
        if (entity.getLegalEntity() != null && entity.getLegalEntity().getAddress() != null) {

            add2ColsRow(table,
                    this.propertiesHelper.getMessage("pdf.account.country.bank.legal.entity.address", locale),
                    entity.getLegalEntity().getAddress().getFieldOne()
                            + entity.getLegalEntity().getAddress().getFieldTwo()
                            + entity.getLegalEntity().getAddress().getFieldThree()
                            + entity.getLegalEntity().getAddress().getFieldFour()
                            + entity.getLegalEntity().getAddress().getFieldFive()
                            + entity.getLegalEntity().getAddress().getFieldSix()
                            + entity.getLegalEntity().getAddress().getFieldSeven());
        } else {
            add2ColsRow(table,
                    this.propertiesHelper.getMessage("pdf.account.country.bank.legal.entity.address", locale),
                    null);
        }
        // Wrapping the data table in a cell
        final PdfPCell tableCell = new PdfPCell(table);

        // Finally adding the title(null) and the data table to the document
        document.add(createFieldset(null, tableCell));
        document.add(new Phrase(STR_SPACE));

        // Note about the Country of account and bank
        addParagraph(document,
                new Phrase(this.propertiesHelper.getMessage("pdf.account.country.bank.legal.entity.note", locale),
                        ARIAL));
    }

    /**
     * Creates a table for Customer information.
     * @param document the Document to write to
     * @param country
     * @throws DocumentException
     */
    private void addCustomerInfo(final Document document, final Locale locale, final PDFBeanEntity entity,
            final Locale country) throws DocumentException {

        // Creating the data table
        final PdfPTable table = new PdfPTable(2);
        // Filling the table
        table.setWidthPercentage(TAB_WIDTH_PERCENT);
        table.setWidths(new float[] {TAB_WIDTH_COL1_SIZE, TAB_WIDTH_COL2_SIZE});
        // background color of columns
        this.colorCol1 = COL_GRAY;
        add2ColsRow(table, this.propertiesHelper.getMessage("pdf.customer.name", locale), entity.getName());
        // Customer office address
        addAddress(table, this.propertiesHelper.getMessage("pdf.customer.office.address", locale),
                entity.getAddressHeadQuarters());
        // Legal form
        add2ColsRow(table, this.propertiesHelper.getMessage("pdf.company.legal.form", locale),
                entity.getLegalForm());
        // Registration number
        add2ColsRow(table, this.propertiesHelper.getMessage("pdf.company.registration.number", locale),
                entity.getRegistrationNb());
        // VAT registration number
        add2ColsRow(table, this.propertiesHelper.getMessage("pdf.company.tax.information", locale),
                entity.getTaxInfo());
        // Country of fiscal Residence
        add2ColsRow(table, this.propertiesHelper.getMessage("pdf.company.registration.country", locale), entity
                .getRegistrationCountry().getDisplayCountry(locale));
        // Specific to poland
        if (country.getCountry().equalsIgnoreCase("PL")) {
            add2ColsRow(table, this.propertiesHelper.getMessage("pdf.company.specific.label_PL", locale), null);
        }
        // Wrapping the data table in a cell
        final PdfPCell tableCell = new PdfPCell(table);
        tableCell.setBorder(Rectangle.BOX);
        // Finally adding the title and the data table to the document
        document.add(createFieldset(null, tableCell));
    }

    /**
     * Creates a table for Customer information.
     * @param document the Document to write to
     * @throws DocumentException
     */
    private void addContactDetails(final Document document, final Locale locale, final PDFBeanEntity entity)
            throws DocumentException {

        // Creating the data table
        final PdfPTable table = new PdfPTable(2);
        // Filling the table
        table.setWidthPercentage(TAB_WIDTH_PERCENT);
        table.setWidths(new float[] {TAB_WIDTH_COL1_SIZE, TAB_WIDTH_COL2_SIZE});

        // background color of columns
        this.colorCol1 = null;
        // Postal address
//        addAddress(table, this.propertiesHelper.getMessage("pdf.contact.address.postal", locale),
//                entity.getAddressPostal());
        if (entity.getContact1() == null && entity.getContact2() == null) {
           // table.addCell(createSpanTwoCell(STR_SPACE, Rectangle.BOX, null));

            add2ColsRow(table, this.propertiesHelper.getMessage("pdf.contact.person.lastname", locale), "");
            add2ColsRow(table, this.propertiesHelper.getMessage("pdf.contact.person.firstname", locale), "");
//          add2ColsRow(table, this.propertiesHelper.getMessage("pdf.contact.person.position", locale), "");
            add2ColsRow(table, this.propertiesHelper.getMessage("pdf.contact.telephone", locale), "");
//            add2ColsRow(table, this.propertiesHelper.getMessage("pdf.contact.fax", locale), "");
            add2ColsRow(table, this.propertiesHelper.getMessage("pdf.contact.email", locale), "");
        } else {
            if (entity.getContact1() != null) {
//                final PdfPCell coll = new PdfPCell(new Phrase(STR_SPACE, ARIAL));
//                coll.setColspan(2);
//                table.addCell(coll);
                add2ColsRow(table, this.propertiesHelper.getMessage("pdf.contact.person.lastname", locale), entity
                        .getContact1().getName());
                add2ColsRow(table, this.propertiesHelper.getMessage("pdf.contact.person.firstname", locale),
                        entity.getContact1().getFirstname());
//                add2ColsRow(table, this.propertiesHelper.getMessage("pdf.contact.person.position", locale), entity
//                        .getContact1().getPositionName());
                add2ColsRow(table, this.propertiesHelper.getMessage("pdf.contact.telephone", locale), entity
                        .getContact1().getTel());
//                add2ColsRow(table, this.propertiesHelper.getMessage("pdf.contact.fax", locale), entity
//                        .getContact1().getFax());
                add2ColsRow(table, this.propertiesHelper.getMessage("pdf.contact.email", locale), entity
                        .getContact1().getMail());
            }
            if (entity.getContact2() != null && !entity.getContact2().getName().equals("")) {
                final PdfPCell coll = new PdfPCell(new Phrase(STR_SPACE, ARIAL));
                coll.setColspan(2);
                table.addCell(coll);
                add2ColsRow(table, this.propertiesHelper.getMessage("pdf.contact.person.lastname", locale), entity
                        .getContact2().getName());
                add2ColsRow(table, this.propertiesHelper.getMessage("pdf.contact.person.firstname", locale),
                        entity.getContact2().getFirstname());
//                add2ColsRow(table, this.propertiesHelper.getMessage("pdf.contact.person.position", locale), entity
//                        .getContact2().getPositionName());
                add2ColsRow(table, this.propertiesHelper.getMessage("pdf.contact.telephone", locale), entity
                        .getContact2().getTel());
//                add2ColsRow(table, this.propertiesHelper.getMessage("pdf.contact.fax", locale), entity
//                        .getContact2().getFax());
                add2ColsRow(table, this.propertiesHelper.getMessage("pdf.contact.email", locale), entity
                        .getContact2().getMail());
            }
        }

        // Wrapping the data table in a cell
        final PdfPCell tableCell = new PdfPCell(table);
        tableCell.setBorder(Rectangle.BOX);

        // Finally adding the title and the data table to the document
        document.add(createFieldset(null, tableCell));
    }

    /**
     * Creates a table for Account Subsidiary information.
     * @param document the Document to write to
     * @throws DocumentException
     */
    private void addAccountSubsidiary(final Document document, final Locale locale, final PDFBeanAccount account)
            throws DocumentException {

        // Creating the data table
        final PdfPTable table = new PdfPTable(2);
        // Filling the table
        table.setWidthPercentage(TAB_WIDTH_PERCENT);
        table.setWidths(new float[] {TAB_WIDTH_COL1_SIZE, TAB_WIDTH_COL2_SIZE});

        // background color of columns
        this.colorCol1 = null;
        // Branch address
        addAddress(table, this.propertiesHelper.getMessage("pdf.account.subsidiary.address.branch", locale),
                account.getAddress());
        add2ColsRow(table, this.propertiesHelper.getMessage("pdf.account.subsidiary.registration.number", locale),
                account.getCommercialRegister());
        add2ColsRow(table, this.propertiesHelper.getMessage("pdf.account.subsidiary.tax.information", locale),
                account.getVatNumber());

        // Wrapping the data table in a cell
        final PdfPCell tableCell = new PdfPCell(table);
        tableCell.setBorder(Rectangle.NO_BORDER);

        // Finally adding the title and the data table to the document
        document.add(createFieldset(null, tableCell));
    }

    /**
     * Creates a table for Account information.
     * @param document the Document to write to
     * @param locale the locale language reference
     * @param table content the title of the section
     * @throws DocumentException
     * @throws IOException
     */
    private void addAccount(final Document document, final Locale locale, final Locale country,
            final PDFBeanAccount account, final int nb, final PdfPTable table) throws DocumentException,
            IOException {
        this.colorCol1 = COL_GRAY;

        final PdfPTable table1 = new PdfPTable(2);
        table1.setWidthPercentage(TAB_WIDTH_PERCENT);
        table1.setWidths(new float[] {TAB_WIDTH_COL1_SIZE, TAB_WIDTH_COL2_SIZE});

        final PdfPCell row1 = createSubTitleCell(this.propertiesHelper.getMessage("pdf.account.label", locale)+ " " + account.getReference() + " - " + account.getName() + this.propertiesHelper.getMessage("pdf.account.label2", locale));
        row1.setColspan(2);
        table1.addCell(row1);
        
        add2ColsRow(table1, this.propertiesHelper.getMessage("pdf.account.label.currency.iso", locale),
                account.getCurrency());

        add2ColsRow(table1, this.propertiesHelper.getMessage("pdf.account.label.frequency", locale),
                account.getStatementPeriodicity());
        
        add2ColsRow(table1, this.propertiesHelper.getMessage("pdf.account.label.channel", locale),
                account.getChannel());

        // Add the following table for ITALY country ONLY: STATEMENT
        if (country.getCountry().equals("IT")) {
            final PdfPCell coll1 = new PdfPCell(new Phrase(this.propertiesHelper.getMessage(
                    "pdf.account.label.opening.purpose", locale), ARIAL));
            coll1.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
            coll1.setPaddingBottom(PADDING_BOTTOM);
            coll1.setBackgroundColor(COL_GRAY);
            coll1.setBorder(Rectangle.BOX);
            table1.addCell(coll1);

            table1.addCell(createOpeningPuposeTableIT(locale, nb));
        }

        // Add Language of communication field IF REQUIRED: STATEMENT
        if (account.getComLangEnabled()) {
            add2ColsRow(table1,
                    this.propertiesHelper.getMessage("pdf.account.label.communication.language", locale),
                    account.getCommunicationLanguage() != null ? account.getCommunicationLanguage().getLocale()
                            .getDisplayLanguage(country) : "");
        }

        table1.addCell(createSpanTwoCell(STR_SPACE, Rectangle.NO_BORDER, null));


       // Signing rules
        final PdfPCell cellRules = new PdfPCell(new Phrase(this.propertiesHelper.getMessage("pdf.account.agreements.additional.rules.title",
                locale), ARIAL_BOLD));
        cellRules.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
        cellRules.setPaddingBottom(PADDING_BOTTOM);
        cellRules.setBorder(Rectangle.NO_BORDER);
        cellRules.setColspan(2);
        final PdfPTable table2 = new PdfPTable(5);
        if(account.getStrategyDocument().equals("Yes")){
        	table1.addCell(cellRules);
        	//table1.addCell(createSpanTwoCell(STR_SPACE, Rectangle.NO_BORDER, null));
        	table1.addCell(createSpanTwoCell("Refer to Global POA / Customer's Power of Attorney ",
                    Rectangle.NO_BORDER, null));
        }else{
        	
            if(account.getRulesList() != null && !account.getRulesList().isEmpty()){
            	
        		table1.addCell(cellRules);
        		table1.addCell(createSpanTwoCell(STR_SPACE, Rectangle.NO_BORDER, null));
    	        table2.setWidthPercentage(TAB_WIDTH_PERCENT);
    	        table2.setWidths(new float[] {25,25,10,20,20});
    	        add5ColsRow(table2, this.propertiesHelper.getMessage("pdf.account.signing.rules.col1",locale),
    	        		this.propertiesHelper.getMessage("pdf.account.signing.rules.col2",locale), 
    	        		this.propertiesHelper.getMessage("pdf.account.signing.rules.col3",locale), 
    	        		this.propertiesHelper.getMessage("pdf.account.signing.rules.col4",locale), 
    	        		this.propertiesHelper.getMessage("pdf.account.signing.rules.col5",locale),
    	        		true);
    	        
    	        for(PDFBeanRules rules : account.getRulesList()){
    	        	if(rules.getAmountMin() != null && rules.getAmountMax() != null){
	    	        	if((rules.getAmountMin()%1==0.0) && (rules.getAmountMax()%1==0.0)){
		    	        	add5ColsRow(table2, 
		    	        			rules.getSignatory2() != null ? rules.getSignatory2().getFirstName() + " " + rules.getSignatory2().getLastName() 
		    	    	        			: (rules.getCollege2() !=null ? rules.getCollege2().getName() : "N/A" ),
		    	        			rules.getSignatory() != null ? rules.getSignatory().getFirstName() + " " + rules.getSignatory().getLastName() 
		    	    	        			: (rules.getCollege() !=null ? rules.getCollege().getName() : "N/A" ),
		    	    	        	rules.getSignatory2() == null && rules.getCollege2() ==null ? "Individually" : "Jointly", 
		    	    	        	rules.getField() != null &&  rules.getField() != ""? rules.getTypeOperation() + " " + rules.getField() : rules.getTypeOperation(), 
		    	            		rules.getAmountMax() == null && rules.getAmountMin() == null ? "No limit" 
		    	            				: rules.getAmountMax() == null && rules.getAmountMin() != null ? "Above : " + Math.round(rules.getAmountMin()) + " " + account.getCurrency()
		    	            				:rules.getAmountMax() != null && rules.getAmountMin() == null ? "Below : " + Math.round(rules.getAmountMax()) + " " + account.getCurrency()
		    	            				:"Between : "+ Math.round(rules.getAmountMin()) + " " + account.getCurrency() + " and " + Math.round(rules.getAmountMax())  + " " + account.getCurrency(),
		    	            				false);
	    	        	}
	    	        	else if((rules.getAmountMin()%1==0.0)){
		    	        	add5ColsRow(table2, 
		    	        			rules.getSignatory2() != null ? rules.getSignatory2().getFirstName() + " " + rules.getSignatory2().getLastName() 
		    	    	        			: (rules.getCollege2() !=null ? rules.getCollege2().getName() : "N/A" ),
		    	        			rules.getSignatory() != null ? rules.getSignatory().getFirstName() + " " + rules.getSignatory().getLastName() 
		    	    	        			: (rules.getCollege() !=null ? rules.getCollege().getName() : "N/A" ),
		    	    	        	rules.getSignatory2() == null && rules.getCollege2() ==null ? "Individually" : "Jointly", 
		    	    	        	rules.getField() != null &&  rules.getField() != ""? rules.getTypeOperation() + " " + rules.getField() : rules.getTypeOperation(), 
		    	            		rules.getAmountMax() == null && rules.getAmountMin() == null ? "No limit" 
		    	            				: rules.getAmountMax() == null && rules.getAmountMin() != null ? "Above : " + Math.round(rules.getAmountMin()) + " " + account.getCurrency()
		    	            				:rules.getAmountMax() != null && rules.getAmountMin() == null ? "Below : " + rules.getAmountMax() + " " + account.getCurrency()
		    	            				:"Between : "+ Math.round(rules.getAmountMin()) + " " + account.getCurrency()  + " and " + rules.getAmountMax() + " " + account.getCurrency() ,
		    	            				false);
	    	        	}
	    	        	else if((rules.getAmountMax()%1==0.0)){
		    	        	add5ColsRow(table2, 
		    	        			rules.getSignatory2() != null ? rules.getSignatory2().getFirstName() + " " + rules.getSignatory2().getLastName() 
		    	    	        			: (rules.getCollege2() !=null ? rules.getCollege2().getName() : "N/A" ),
		    	        			rules.getSignatory() != null ? rules.getSignatory().getFirstName() + " " + rules.getSignatory().getLastName() 
		    	    	        			: (rules.getCollege() !=null ? rules.getCollege().getName() : "N/A" ),
		    	    	        	rules.getSignatory2() == null && rules.getCollege2() ==null ? "Individually" : "Jointly", 
		    	    	        	rules.getField() != null &&  rules.getField() != ""? rules.getTypeOperation() + " " + rules.getField() : rules.getTypeOperation(), 
		    	            		rules.getAmountMax() == null && rules.getAmountMin() == null ? "No limit" 
		    	            				: rules.getAmountMax() == null && rules.getAmountMin() != null ? "Above : " + rules.getAmountMin() + " " + account.getCurrency()
		    	            				:rules.getAmountMax() != null && rules.getAmountMin() == null ? "Below : " + Math.round(rules.getAmountMax()) + " " + account.getCurrency()
		    	            				:"Between : "+ rules.getAmountMin() + " " + account.getCurrency()  + " and " + Math.round(rules.getAmountMax()) + " " + account.getCurrency() ,
		    	            				false);
	    	        	}
	    	        	else{
	    	        		add5ColsRow(table2, 
		    	        			rules.getSignatory2() != null ? rules.getSignatory2().getFirstName() + " " + rules.getSignatory2().getLastName() 
		    	    	        			: (rules.getCollege2() !=null ? rules.getCollege2().getName() : "N/A" ),
		    	        			rules.getSignatory() != null ? rules.getSignatory().getFirstName() + " " + rules.getSignatory().getLastName() 
		    	    	        			: (rules.getCollege() !=null ? rules.getCollege().getName() : "N/A" ),
		    	    	        	rules.getSignatory2() == null && rules.getCollege2() ==null ? "Individually" : "Jointly", 
		    	    	        	rules.getField() != null &&  rules.getField() != ""? rules.getTypeOperation() + " " + rules.getField() : rules.getTypeOperation(), 
		    	            		rules.getAmountMax() == null && rules.getAmountMin() == null ? "No limit" 
		    	            				: rules.getAmountMax() == null && rules.getAmountMin() != null ? "Above : " + rules.getAmountMin() + " " + account.getCurrency()
		    	            				:rules.getAmountMax() != null && rules.getAmountMin() == null ? "Below : " + rules.getAmountMax() + " " + account.getCurrency() 
		    	            				:"Between : "+ rules.getAmountMin() + " " + account.getCurrency()  + " and " + rules.getAmountMax() + " " + account.getCurrency() ,
		    	            				false);
	    	        		
	    	        	}
    	        	}
    	        	else if(rules.getAmountMin() != null){
	    	        	if((rules.getAmountMin()%1==0.0)){
		    	        	add5ColsRow(table2, 
		    	        			rules.getSignatory2() != null ? rules.getSignatory2().getFirstName() + " " + rules.getSignatory2().getLastName() 
		    	    	        			: (rules.getCollege2() !=null ? rules.getCollege2().getName() : "N/A" ),
		    	        			rules.getSignatory() != null ? rules.getSignatory().getFirstName() + " " + rules.getSignatory().getLastName() 
		    	    	        			: (rules.getCollege() !=null ? rules.getCollege().getName() : "N/A" ),
		    	    	        	rules.getSignatory2() == null && rules.getCollege2() ==null ? "Individually" : "Jointly", 
		    	    	        	rules.getField() != null &&  rules.getField() != ""? rules.getTypeOperation() + " " + rules.getField() : rules.getTypeOperation(), 
		    	            		rules.getAmountMax() == null && rules.getAmountMin() == null ? "No limit" 
		    	            				: rules.getAmountMax() == null && rules.getAmountMin() != null ? "Above : " + Math.round(rules.getAmountMin()) + " " + account.getCurrency()
		    	            				:rules.getAmountMax() != null && rules.getAmountMin() == null ? "Below : " + rules.getAmountMax() + " " + account.getCurrency()
		    	            				:"Between : "+ Math.round(rules.getAmountMin()) + " " + account.getCurrency()  + " and " + rules.getAmountMax() + " " + account.getCurrency() ,
		    	            				false);
	    	        	}
	    	        	else{
	    	        		add5ColsRow(table2, 
		    	        			rules.getSignatory2() != null ? rules.getSignatory2().getFirstName() + " " + rules.getSignatory2().getLastName() 
		    	    	        			: (rules.getCollege2() !=null ? rules.getCollege2().getName() : "N/A" ),
		    	        			rules.getSignatory() != null ? rules.getSignatory().getFirstName() + " " + rules.getSignatory().getLastName() 
		    	    	        			: (rules.getCollege() !=null ? rules.getCollege().getName() : "N/A" ),
		    	    	        	rules.getSignatory2() == null && rules.getCollege2() ==null ? "Individually" : "Jointly", 
		    	    	        	rules.getField() != null &&  rules.getField() != ""? rules.getTypeOperation() + " " + rules.getField() : rules.getTypeOperation(), 
		    	            		rules.getAmountMax() == null && rules.getAmountMin() == null ? "No limit" 
		    	            				: rules.getAmountMax() == null && rules.getAmountMin() != null ? "Above : " + rules.getAmountMin() + " " + account.getCurrency()
		    	            				:rules.getAmountMax() != null && rules.getAmountMin() == null ? "Below : " + rules.getAmountMax() + " " + account.getCurrency() 
		    	            				:"Between : "+ rules.getAmountMin() + " " + account.getCurrency()  + " and " + rules.getAmountMax() + " " + account.getCurrency() ,
		    	            				false);
	    	        		
	    	        	}
    	        	}
    	        	else if(rules.getAmountMax() != null){
	    	        	if((rules.getAmountMax()%1==0.0)){
		    	        	add5ColsRow(table2, 
		    	        			rules.getSignatory2() != null ? rules.getSignatory2().getFirstName() + " " + rules.getSignatory2().getLastName() 
		    	    	        			: (rules.getCollege2() !=null ? rules.getCollege2().getName() : "N/A" ),
		    	        			rules.getSignatory() != null ? rules.getSignatory().getFirstName() + " " + rules.getSignatory().getLastName() 
		    	    	        			: (rules.getCollege() !=null ? rules.getCollege().getName() : "N/A" ),
		    	    	        	rules.getSignatory2() == null && rules.getCollege2() ==null ? "Individually" : "Jointly", 
		    	    	        	rules.getField() != null &&  rules.getField() != ""? rules.getTypeOperation() + " " + rules.getField() : rules.getTypeOperation(), 
		    	            		rules.getAmountMax() == null && rules.getAmountMin() == null ? "No limit" 
		    	            				: rules.getAmountMax() == null && rules.getAmountMin() != null ? "Above : " + rules.getAmountMin() + " " + account.getCurrency()
		    	            				:rules.getAmountMax() != null && rules.getAmountMin() == null ? "Below : " + Math.round(rules.getAmountMax()) + " " + account.getCurrency() 
		    	            				:"Between : "+ rules.getAmountMin() + " " + account.getCurrency()  + " and " + Math.round(rules.getAmountMax()) + " " + account.getCurrency() ,
		    	            				false);
	    	        	}
	    	        	else{
	    	        		add5ColsRow(table2, 
		    	        			rules.getSignatory2() != null ? rules.getSignatory2().getFirstName() + " " + rules.getSignatory2().getLastName() 
		    	    	        			: (rules.getCollege2() !=null ? rules.getCollege2().getName() : "N/A" ),
		    	        			rules.getSignatory() != null ? rules.getSignatory().getFirstName() + " " + rules.getSignatory().getLastName() 
		    	    	        			: (rules.getCollege() !=null ? rules.getCollege().getName() : "N/A" ),
		    	    	        	rules.getSignatory2() == null && rules.getCollege2() ==null ? "Individually" : "Jointly", 
		    	    	        	rules.getField() != null &&  rules.getField() != ""? rules.getTypeOperation() + " " + rules.getField() : rules.getTypeOperation(), 
		    	            		rules.getAmountMax() == null && rules.getAmountMin() == null ? "No limit" 
		    	            				: rules.getAmountMax() == null && rules.getAmountMin() != null ? "Above : " + rules.getAmountMin() + " " + account.getCurrency()
		    	            				:rules.getAmountMax() != null && rules.getAmountMin() == null ? "Below : " + rules.getAmountMax() + " " + account.getCurrency() 
		    	            				:"Between : "+ rules.getAmountMin() + " " + account.getCurrency()  + " and " + rules.getAmountMax() + " " + account.getCurrency() ,
		    	            				false);
	    	        		
	    	        	}
    	        	}
    	        	else{
    	        		add5ColsRow(table2, 
	    	        			rules.getSignatory2() != null ? rules.getSignatory2().getFirstName() + " " + rules.getSignatory2().getLastName() 
	    	    	        			: (rules.getCollege2() !=null ? rules.getCollege2().getName() : "N/A" ),
	    	        			rules.getSignatory() != null ? rules.getSignatory().getFirstName() + " " + rules.getSignatory().getLastName() 
	    	    	        			: (rules.getCollege() !=null ? rules.getCollege().getName() : "N/A" ),
	    	    	        	rules.getSignatory2() == null && rules.getCollege2() ==null ? "Individually" : "Jointly", 
	    	    	        	rules.getField() != null &&  rules.getField() != ""? rules.getTypeOperation() + " " + rules.getField() : rules.getTypeOperation(), 
	    	            		rules.getAmountMax() == null && rules.getAmountMin() == null ? "No limit" 
	    	            				: rules.getAmountMax() == null && rules.getAmountMin() != null ? "Above : " + rules.getAmountMin() + " " + account.getCurrency()
	    	            				:rules.getAmountMax() != null && rules.getAmountMin() == null ? "Below : " + rules.getAmountMax() + " " + account.getCurrency() 
	    	            				:"Between : "+ rules.getAmountMin() + " " + account.getCurrency()  + " and " + rules.getAmountMax() + " " + account.getCurrency() ,
	    	            				false);
    	        		
    	        	}
    	        }
            }
        }
        
        if (table != null && nb == 1) {
            final PdfPCell cell = new PdfPCell(table1);
            final PdfPCell cell2 = new PdfPCell(table2);
            cell.setBorder(Rectangle.NO_BORDER);
            cell2.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell);
            table.addCell(cell2);
            document.add(table);
        } else {
            document.add(table1);
            document.add(table2);
        }

    }

    /**
     * Creates a table for Account Agreements and supporting documents.
     * @param document the Document to write to
     * @param entityCountry
     * @throws DocumentException
     */
    private void addAccountAgreements(final Document document, final Locale locale, final Locale country,
            final Locale entityCountry, final Boolean xbasV2) throws DocumentException {
        final String localCountry = country.getCountry();
        String localCountryRef = "_" + localCountry;
        String localXbasV2Ref;
        if(xbasV2) {
        	localXbasV2Ref = "_xbasV2_" + localCountry;
        } else {
        	localXbasV2Ref = "_" + localCountry;
        }
        // background color of columns
        this.colorCol1 = COL_GRAY;

        final PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(TAB_WIDTH_PERCENT);
        table.setWidths(new float[] {TAB_WIDTH_COL1_SIZE, TAB_WIDTH_COL2_SIZE});
        if (country.getCountry().equalsIgnoreCase(entityCountry.getCountry())) {
            add2ColsRow(table, this.propertiesHelper.getMessage("pdf.account.agreements.label", locale),
                    this.propertiesHelper.getMessage("pdf.account.agreements.data" + localXbasV2Ref, locale));
        } else {
            add2ColsRow(table, this.propertiesHelper.getMessage("pdf.account.agreements.label", locale),
                    this.propertiesHelper.getMessage("pdf.account.agreements.non.resident.data" + localXbasV2Ref,
                            locale));
        }
        document.add(table);
        document.add(new Phrase(STR_SPACE));

        if (localCountry.equals("ES")) {
            final PdfPTable table1 = new PdfPTable(2);
            table1.setWidthPercentage(TAB_WIDTH_PERCENT);
            table1.setWidths(new float[] {TAB_WIDTH_COL1_SIZE, TAB_WIDTH_COL2_SIZE});

            add2ColsRow(table1, this.propertiesHelper.getMessage(
                    "pdf.account.agreements.additional.documents.label_ES", locale),
                    this.propertiesHelper
                            .getMessage("pdf.account.agreements.additional.documents.data_ES", locale));

            document.add(table1);
            document.add(new Phrase(STR_SPACE));
        } else if (localCountry.equals("NO")) {
            final PdfPTable table1 = new PdfPTable(2);
            table1.setWidthPercentage(TAB_WIDTH_PERCENT);
            table1.setWidths(new float[] {TAB_WIDTH_COL1_SIZE, TAB_WIDTH_COL2_SIZE});

            final PdfPTable table10 = new PdfPTable(new float[] {47, 3});
            table10.setWidthPercentage(TAB_WIDTH_COL1_SIZE);

            final PdfPCell cell101 = new PdfPCell(new Phrase(this.propertiesHelper.getMessage(
                    "pdf.account.agreements.additional.documents.label_NO", locale), ARIAL));
            cell101.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
            cell101.setPaddingBottom(PADDING_BOTTOM);
            cell101.setBorder(Rectangle.NO_BORDER);
            cell101.setBackgroundColor(COL_GRAY);
            table10.addCell(cell101);

            final PdfPCell cell102 = new PdfPCell();
            cell102.setCellEvent(new PdfCheckboxCellEvent("additional_documents_NO"));
            cell102.setBorder(Rectangle.NO_BORDER);
            cell102.setBackgroundColor(COL_GRAY);
            cell102.setPadding(2);
            table10.addCell(cell102);

            final PdfPCell coll1 = new PdfPCell(table10);
            coll1.setBackgroundColor(COL_GRAY);
            coll1.setBorder(Rectangle.BOX);
            table1.addCell(coll1);

            final PdfPCell coll2 = new PdfPCell(new Phrase(this.propertiesHelper.getMessage(
                    "pdf.account.agreements.additional.documents.data_NO", locale), ARIAL));
            coll2.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
            coll2.setPaddingBottom(PADDING_BOTTOM);
            coll2.setBorder(Rectangle.BOX);
            table1.addCell(coll2);

            document.add(table1);
            document.add(new Phrase(STR_SPACE));
        }

        final String text = this.propertiesHelper.getMessage("pdf.account.agreements.additional.documents.note1"
                + localCountryRef, locale);
        final String text1 = this.propertiesHelper.getMessage("pdf.account.agreements.additional.documents.note2"
                + localCountryRef, locale);
        if (StringUtils.isNotBlank(text) || StringUtils.isNotBlank(text1)) {
            final Paragraph paragraph = new Paragraph();
            paragraph.setLeading(0, 1);
            paragraph.add(new Phrase(text, ARIAL_BOLD));
            paragraph.setLeading(0, 1);
            paragraph.add(new Phrase(text1, ARIAL));
            paragraph.setAlignment(Element.ALIGN_JUSTIFIED);
            paragraph.setLeading(0, 1);

            document.add(paragraph);
            document.add(new Phrase(STR_SPACE));
        }

        final PdfPTable table2 = new PdfPTable(2);
        table2.setWidthPercentage(TAB_WIDTH_PERCENT);
        table2.setWidths(new float[] {TAB_WIDTH_COL1_SIZE, TAB_WIDTH_COL2_SIZE});
        if (country.getCountry().equalsIgnoreCase(entityCountry.getCountry())) {
            add2ColsRow(
                    table2,
                    this.propertiesHelper.getMessage("pdf.account.agreements.supporting.documents.label", locale),
                    this.propertiesHelper.getMessage("pdf.account.agreements.supporting.documents.data"
                            + localCountryRef, locale));
        } else {
            add2ColsRow(table2, this.propertiesHelper.getMessage(
                    "pdf.account.agreements.supporting.documents.label", locale),
                    this.propertiesHelper.getMessage(
                            "pdf.account.agreements.supporting.documents.non.resident.data" + localCountryRef,
                            locale));
        }

        document.add(table2);
        document.add(new Phrase(STR_SPACE));
    }

    /**
     * Creates a table for Account Agreements and supporting documents.
     * @param document the Document to write to
     * @throws DocumentException
     */
    private void addInstructionsBank(final Document document, final Locale locale) throws DocumentException {

        // Add paragraph's title
        final PdfPCell titleCell = createSubTitleCell(this.propertiesHelper.getMessage(
                "pdf.bank.instructions.title.note", locale));

        // Add paragraph's text and background color
        final PdfPCell cell = new PdfPCell(new Phrase(this.propertiesHelper.getMessage(
                "pdf.bank.instructions.data", locale), ARIAL));
        cell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
        cell.setPaddingBottom(PADDING_BOTTOM);
        cell.setBackgroundColor(COL_GRAY);
        cell.setBorder(Rectangle.BOX);

        document.add(createFieldset(titleCell, cell));
        document.add(new Phrase(STR_SPACE));
    }

    /**
     * Creates a table for Authorized Signatories.
     * @param document the Document to write to
     * @param signatoriesExist
     * @throws DocumentException
     */
    private void addAuthorizedSignatories(final Document document, final Locale locale,
            final SimpleDateFormat dateFormat, final List<PDFBeanAccount> accounts, final Boolean signatoriesExist)
            throws DocumentException {
        if (!signatoriesExist) {
            document.add(new Phrase(this.propertiesHelper.getMessage("pdf.account.signatories.otherwise", locale),
                    ARIAL));
            document.add(new Phrase(STR_SPACE));
            return;
        }
        // Add paragraph's title
        final PdfPCell titleCell = createSubTitleCell(this.propertiesHelper.getMessage(
                "pdf.account.signatories.block.label", locale));

        // Add paragraph's text and background color
        final Paragraph paraText = new Paragraph(this.propertiesHelper.getMessage(
                "pdf.account.signatories.block.data1", locale), ARIAL);
        paraText.add(new Phrase(this.propertiesHelper.getMessage("pdf.account.signatories.block.data2", locale),
                ARIAL));
        paraText.add(new Phrase(this.propertiesHelper.getMessage("pdf.account.signatories.block.data3", locale),
                ARIAL));
        paraText.add(new Phrase(this.propertiesHelper.getMessage("pdf.account.signatories.block.data4", locale),
                ARIAL));

        final PdfPCell cell = new PdfPCell(paraText);
        cell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
        cell.setPaddingBottom(PADDING_BOTTOM);
        cell.setBackgroundColor(COL_GRAY);
        cell.setBorder(Rectangle.BOX);

        document.add(createFieldset(titleCell, cell));
        document.add(new Phrase("\n"));
        
        final PdfPCell cell3 = createSubTitleCell(this.propertiesHelper.getMessage(
              "pdf.signature.card.authorized.signatories.block.label", locale));

      final Paragraph text2 = new Paragraph(this.propertiesHelper.getMessage(
              "pdf.signature.card.authorized.signatories.block.data1", locale), ARIAL);
      text2.add(new Phrase(this.propertiesHelper.getMessage(
              "pdf.signature.card.authorized.signatories.block.data2", locale), ARIAL));
      text2.add(new Phrase(this.propertiesHelper.getMessage(
              "pdf.signature.card.authorized.signatories.block.data3", locale), ARIAL));
      text2.add(new Phrase(this.propertiesHelper.getMessage(
              "pdf.signature.card.authorized.signatories.block.data4", locale), ARIAL));
      text2.add(new Phrase(this.propertiesHelper.getMessage(
              "pdf.signature.card.authorized.signatories.block.data5", locale), ARIAL));
      text2.add(new Phrase(this.propertiesHelper.getMessage(
              "pdf.signature.card.authorized.signatories.block.data6", locale), ARIAL));
//      text2.add(new Phrase(this.propertiesHelper.getMessage(
//              "pdf.signature.card.authorized.signatories.block.data7", locale), ARIAL));
//      text2.add(new Phrase(this.propertiesHelper.getMessage(
//              "pdf.signature.card.authorized.signatories.block.data8", locale), ARIAL));
//      text2.add(new Phrase(this.propertiesHelper.getMessage(
//              "pdf.signature.card.authorized.signatories.block.data9", locale), ARIAL));
//      text2.add(new Phrase(this.propertiesHelper.getMessage(
//              "pdf.signature.card.authorized.signatories.block.data10", locale), ARIAL));

      final PdfPCell cell4 = new PdfPCell(text2);
      cell4.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
      cell4.setPaddingBottom(PADDING_BOTTOM);
      cell4.setBackgroundColor(COL_GRAY);
      cell4.setBorder(Rectangle.BOX);

      document.add(createFieldset(cell3, cell4));
        
//        final String text = this.propertiesHelper.getMessage("pdf.account.agreements.additional.documents.note1"
//                , locale);
        
        //Groups
        document.add(new Phrase(STR_SPACE));
        final Paragraph paragraph = new Paragraph();
        paragraph.setLeading(0, 1);
        paragraph.add(new Phrase(this.propertiesHelper.getMessage("pdf.account.agreements.additional.group.title",
                locale), ARIAL_BOLD));
        
        boolean hasGroup = false;
        final List<PDFBeanCollege> collegeList = new ArrayList<PDFBeanCollege>();
	    for (final PDFBeanAccount beanAccount : accounts) {
	    	if(beanAccount.getCollegeList() != null && !beanAccount.getCollegeList().isEmpty()){
	    		if(!hasGroup){
	    			document.add(paragraph);
	    			hasGroup = true;
	    		}
	    		document.add(new Phrase("\n"));
	    		document.add(new Phrase(STR_SPACE));
	    		document.add(new Phrase(beanAccount.getReference() + " - " + beanAccount.getName(), ARIAL));
			    for (final PDFBeanCollege college : beanAccount.getCollegeList()) {
					if (collegeList.contains(college)) {
						continue;
				    } else {
				    	collegeList.add(college);
				    }
					
					boolean collegeUse = false;
					if(beanAccount.getRulesList() != null && !beanAccount.getRulesList().isEmpty()){
						for(PDFBeanRules r : beanAccount.getRulesList()){
							if(r.getCollege() != null && r.getCollege().equals(college)){
								collegeUse = true;
							}
							if(r.getCollege2() != null && r.getCollege2().equals(college)){
								collegeUse = true;
							}
						}
					}
					
					if(collegeUse){
						document.add(new Phrase(STR_SPACE));
						
						final PdfPCell titleCellGroup = createSubTitleCell(college.getName());
				        titleCellGroup.setBackgroundColor(COL_GRAY);
				        
						final PdfPTable mainTable = new PdfPTable(1);
				        mainTable.setWidthPercentage(TAB_WIDTH_PERCENT);
				        // Adding the title
				        if (titleCellGroup != null) {
				            mainTable.addCell(titleCellGroup);
				        }
				        
				        for (final PDFBeanSignatory signatory : college.getSignatoriesList()) {
				        	Paragraph paraTextGroup = new Paragraph();
					        paraTextGroup.add(new Phrase(signatory.getFirstName() + " " + signatory.getLastName(),
					                ARIAL));
					        
					        final PdfPCell cellGroup = new PdfPCell(paraTextGroup);
					        cellGroup.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
					        cellGroup.setPaddingBottom(PADDING_BOTTOM);
					        cellGroup.setBorder(Rectangle.BOX);
					        
					     // Adding the data table
					        mainTable.addCell(cellGroup);
				        }
		
				     // Wrapping the main table in a "fieldset cell"
				        final PdfPCell fieldsetCell = new PdfPCell(mainTable);
				        // Wrapping the "fieldset cell" in a "fieldset table"
				        final PdfPTable fieldsetTable = new PdfPTable(1);
				        fieldsetTable.setWidthPercentage(TAB_WIDTH_PERCENT);
				        fieldsetTable.addCell(fieldsetCell);
		
				        document.add(fieldsetTable);
					}
			    }
	    	}
	    }

	    //Signataires
	    document.newPage();
        final Paragraph paragraphSignatory = new Paragraph();
        paragraphSignatory.setLeading(0, 1);
        paragraphSignatory.add(new Phrase(this.propertiesHelper.getMessage("pdf.account.agreements.additional.signatory.title",
                locale), ARIAL_BOLD));
        
        document.add(paragraphSignatory);
	    document.add(new Phrase(STR_SPACE));
        
        final List<PDFBeanSignatory> usedTList = new ArrayList<PDFBeanSignatory>();
        List<String> signatoriesResume = new ArrayList<String>();
        for (final PDFBeanAccount beanAccount : accounts) {
            for (final PDFBeanSignatory signatory : beanAccount.getAccountSignatoryList()) {
            	boolean toInsert;
            	
            	String signatoryResume = signatory.getLastName() + " " + signatory.getFirstName() + " " + dateFormat.format(signatory.getBirthDate());
            	if (signatoriesResume.contains(signatoryResume)) {
            		toInsert = false;
            	} else {
            		toInsert = true;
            		signatoriesResume.add(signatoryResume);
            	}
            	
            	if (toInsert) {	            	
	                if (usedTList.contains(signatory)) {
	                    continue;
	                } else {
	                    usedTList.add(signatory);
	                }
	//                if (!signatory.getFormAOR()) {
	//                    continue;
	//                }
	                
	                boolean signatoryUse = false;
	                boolean signatoryGroupUse = false;
					if(beanAccount.getRulesList() != null && !beanAccount.getRulesList().isEmpty()){
						for(PDFBeanRules r : beanAccount.getRulesList()){
							if(r.getSignatory() != null && r.getSignatory().equals(signatory)){
								signatoryUse = true;
							}
							if(r.getSignatory2() != null && r.getSignatory2().equals(signatory)){
								signatoryUse = true;
							}
							if(r.getCollege() != null && r.getCollege().getSignatoriesList() != null && !r.getCollege().getSignatoriesList().isEmpty()){
								for(PDFBeanSignatory s :  r.getCollege().getSignatoriesList()){
									if(s.getId().equals(signatory.getId())){
										signatoryGroupUse = true;
									}
								}
							}
							if(r.getCollege2() != null && r.getCollege2().getSignatoriesList() != null && !r.getCollege2().getSignatoriesList().isEmpty()){
								for(PDFBeanSignatory s :  r.getCollege2().getSignatoriesList()){
									if(s.getId().equals(signatory.getId())){
										signatoryGroupUse = true;
									}
								}
							}							
						}
					}
					
					if(signatoryUse || signatoryGroupUse){
	                
		                document.add(new Phrase(STR_SPACE));
		                final PdfPTable table = new PdfPTable(2);
		                // Filling the table
		                table.setWidthPercentage(TAB_WIDTH_PERCENT);
		                table.setWidths(new float[] {TAB_WIDTH_COL1_SIZE, TAB_WIDTH_COL2_SIZE});
		
		                // background color of columns
		                this.colorCol1 = COL_GRAY;
		                add2ColsRow(table, this.propertiesHelper.getMessage("pdf.account.signatories.lastname", locale),
		                		signatory.getLastName());
		                add2ColsRow(table, this.propertiesHelper.getMessage("pdf.account.signatories.firstname", locale),
		                		signatory.getFirstName());
		                add2ColsRow(table, this.propertiesHelper.getMessage("pdf.account.signatories.position", locale),
		                		signatory.getPosition());
		                add2ColsRow(table, this.propertiesHelper.getMessage("pdf.account.signatories.dob", locale),
		                		signatory.getBirthDate() == null ? "" : dateFormat.format(signatory.getBirthDate()));
		                add2ColsRow(table, this.propertiesHelper.getMessage("pdf.account.signatories.pob", locale),
		                		signatory.getBirthPlace());
		                if (signatory.getCitizenships() != null && !signatory.getCitizenships().isEmpty()) {
		                    final StringBuilder stringBuilder = new StringBuilder();
		                    for (final Locale locale2 : signatory.getCitizenships()) {
		                        if (stringBuilder.length() == 0) {
		                            stringBuilder.append(locale2.getDisplayCountry(locale));
		                        } else {
		                            stringBuilder.append(", " + locale2.getDisplayCountry(locale));
		                        }
		                    }
		                    add2ColsRow(table,
		                            this.propertiesHelper.getMessage("pdf.account.signatories.citizenships", locale),
		                            stringBuilder.toString());
		                }else if(signatory.getNationality() != null){ 
		                	add2ColsRow(table,
		                            this.propertiesHelper.getMessage("pdf.account.signatories.citizenships", locale),
		                            (new Locale("",signatory.getNationality())).getDisplayCountry());
		                }else {
		                    add2ColsRow(table,
		                            this.propertiesHelper.getMessage("pdf.account.signatories.citizenships", locale), null);
		                }                		
		                add2ColsRow(table, this.propertiesHelper.getMessage("pdf.account.signatories.telephone", locale),
		                		signatory.getTelephone());
		//                add2ColsRow(table, this.propertiesHelper.getMessage("pdf.account.signatories.fax", locale),
		//                		signatory.getFax());
		                add2ColsRow(table, this.propertiesHelper.getMessage("pdf.account.signatories.email", locale),
		                		signatory.getEmail());
		
		                addAddress(table, this.propertiesHelper.getMessage("pdf.account.signatories.address", locale),
		                		signatory.getAddressHome());
		
		//                add2ColsRow(table, this.propertiesHelper.getMessage("pdf.account.signatories.label1", locale),
		//                        this.propertiesHelper.getMessage("pdf.account.signatories.data1", locale));
		                this.dataToRight = true;
		                // DTODO check statement thirdParty.getActingIndivOrJoin() before print it(WHICH one?) need more
		                // details...
		
		//                PDFBeanActingDetails actingIndiv = null, actingJoint = null, actingOther = null;
		//                if (signatory.getPdfBeanActingDetails() != null) {
		//                    for (final PDFBeanActingDetails details : signatory.getPdfBeanActingDetails()) {
		//                        if (!details.getPowerCode().equals(Constants.PARAM_CODE_ACC_THIRD_PWR_FR1)) {
		//                            continue;
		//                        }
		//                        if (details.getActingIndivflag()) {
		//                            actingIndiv = details;
		//                        } else if (details.getActingJointlyflag()) {
		//                            actingJoint = details;
		//                        } else {
		//                            actingOther = details;
		//                        }
		//                    }
		//                }
		//                if (actingIndiv == null) {
		//                    add2ColsRow(table, this.propertiesHelper.getMessage("pdf.account.signatories.label2", locale),
		//                            this.propertiesHelper.getMessage("pdf.account.signatories.data22", locale));
		//                } else {
		//                	
		//                    add2ColsRow(table, this.propertiesHelper.getMessage("pdf.account.signatories.label2", locale),
		//                    		actingIndiv.getDeviseAmountLimit() != null ? this.propertiesHelper.getMessage("pdf.account.signatories.data21", locale)
		//                                    + actingIndiv.getAmountLimit() + " " + actingIndiv.getDeviseAmountLimit()
		//                                    : this.propertiesHelper.getMessage("pdf.account.signatories.data21", locale)
		//                                    + actingIndiv.getAmountLimit());
		//                	
		//                }
		//                if (actingJoint == null && actingOther == null) {
		//                    add2ColsRow(table, this.propertiesHelper.getMessage("pdf.account.signatories.label3", locale),
		//                            this.propertiesHelper.getMessage("pdf.account.signatories.data33", locale));
		//                } else {
		//                    String textOther = "";
		//                    if (actingOther != null) {
		//                    	
		//                        textOther = actingOther.getDeviseAmountLimit() != null ? this.propertiesHelper.getMessage("pdf.account.signatories.data3", locale)
		//                                + actingOther.getAmountLimit() + " " + actingOther.getDeviseAmountLimit()
		//                                + this.propertiesHelper.getMessage("pdf.account.signatories.data32", locale)
		//                                : this.propertiesHelper.getMessage("pdf.account.signatories.data3", locale)
		//                                + actingOther.getAmountLimit()
		//                                + this.propertiesHelper.getMessage("pdf.account.signatories.data32", locale);
		//                    	
		//                    }
		//                    if (actingJoint == null) {
		//                        add2ColsRow(table,
		//                                this.propertiesHelper.getMessage("pdf.account.signatories.label3", locale),
		//                                textOther);
		//                    } else {
		//                        final StringBuilder builder = new StringBuilder();
		//                        if (actingJoint.getJointThidParty() != null) {
		//                            boolean f = false;
		//                            for (final AjaxDto tp : actingJoint.getJointThidParty()) {
		//                                if (f) {
		//                                    builder.append(this.propertiesHelper.getMessage(
		//                                            "pdf.account.signatories.separator", locale));
		//                                } else {
		//                                    f = true;
		//                                }
		//                                builder.append(tp.getLabel() + " " + tp.getLogin());
		//                            }
		//                            if (f) {
		//                                builder.append(this.propertiesHelper.getMessage("pdf.account.signatories.end",
		//                                        locale));
		//                            }
		//                        }
		//                        if (textOther.isEmpty()) {
		//                            add2ColsRow(
		//                                    table,
		//                                    this.propertiesHelper.getMessage("pdf.account.signatories.label3", locale),
		//                                    actingJoint.getDeviseAmountLimit() != null ? this.propertiesHelper.getMessage("pdf.account.signatories.data3", locale)
		//                                            + actingJoint.getAmountLimit() + " " + actingJoint.getDeviseAmountLimit()
		//                                            + this.propertiesHelper.getMessage("pdf.account.signatories.data31",
		//                                                    locale) + builder.toString()
		//                                                    : this.propertiesHelper.getMessage("pdf.account.signatories.data3", locale)
		//                                                    + actingJoint.getAmountLimit()
		//                                                    + this.propertiesHelper.getMessage("pdf.account.signatories.data31",
		//                                                            locale) + builder.toString());
		//                        } else {
		//                            this.noBorderTopBottom = true;
		//                            add2ColsRow(
		//                                    table,
		//                                    this.propertiesHelper.getMessage("pdf.account.signatories.label3", locale),
		//                                    actingJoint.getDeviseAmountLimit() != null ? this.propertiesHelper.getMessage("pdf.account.signatories.data3", locale)
		//                                            + actingJoint.getAmountLimit() + " " + actingJoint.getDeviseAmountLimit()
		//                                            + this.propertiesHelper.getMessage("pdf.account.signatories.data31",
		//                                                    locale) + builder.toString()
		//                                                    : this.propertiesHelper.getMessage("pdf.account.signatories.data3", locale)
		//                                                    + actingJoint.getAmountLimit()
		//                                                    + this.propertiesHelper.getMessage("pdf.account.signatories.data31",
		//                                                            locale) + builder.toString());
		//                            add2ColsRow(table, null, textOther);
		//                            this.noBorderTopBottom = false;
		//                        }
		//                    }
		//                }
		//                this.dataToRight = false;
		//                this.noBorderCol1 = false;
		//
		//                add2ColsRow(table, this.propertiesHelper.getMessage("pdf.account.signatories.label4", locale),
		//                        this.propertiesHelper.getMessage("pdf.account.signatories.data4", locale));
		
		                final PdfPCell col1 = new PdfPCell(new Phrase(this.propertiesHelper.getMessage(
		                        "pdf.account.signatories.label5", locale), ARIAL));
		                col1.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
		                col1.setPaddingBottom(PADDING_BOTTOM);
		                col1.setBackgroundColor(COL_GRAY);
		                final PdfPCell col2 = new PdfPCell(new Phrase(this.propertiesHelper.getMessage(
		                        "pdf.account.signatories.data5", locale), ARIAL_ITALIC));
		                col2.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
		                col2.setBorderWidth(col1.getBorderWidth() * BORDER_BOLD_FACTOR);
		                col2.setPaddingBottom(PADDING_BOTTOM);
		                col2.setBorderColor(COL_RED);
		
		                table.addCell(col1);
		                table.addCell(col2);
		                // Wrapping the data table in a cell
		                final PdfPCell tableCell = new PdfPCell(table);
		                tableCell.setBorder(Rectangle.BOX);
		
		                // Finally adding the title and the data table to the document
		                document.add(createFieldset(null, tableCell));
					}
	            }
            }
        }
    }

    /**
     * Creates a table for Customer Declarations.
     * @param document the Document to write to
     * @throws DocumentException
     */
    private void addCustomerDeclarations(final Document document, final Locale locale, final PDFBean localBean)
            throws DocumentException {
        final String country = localBean.getCountry().getCountry();
        final String countryRef = "_" + country;

        final Paragraph paragraph = new Paragraph();
        paragraph.setLeading(0, 1);
        paragraph.add(new Phrase(this.propertiesHelper.getMessage("pdf.customer.declarations.comma1" + countryRef,
                locale), ARIAL_BOLD));
        paragraph.add(new Phrase(this.propertiesHelper.getMessage("pdf.customer.declarations.comma2" + countryRef,
                locale), ARIAL));
        paragraph.add(new Phrase(this.propertiesHelper.getMessage("pdf.customer.declarations.comma3" + countryRef,
                locale), ARIAL));
        paragraph.add(new Phrase(this.propertiesHelper.getMessage("pdf.customer.declarations.comma4" + countryRef,
                locale), ARIAL));
        paragraph.add(new Phrase(this.propertiesHelper.getMessage("pdf.customer.declarations.comma5" + countryRef,
                locale), ARIAL));
        paragraph.add(new Phrase(this.propertiesHelper.getMessage("pdf.customer.declarations.comma6" + countryRef,
                locale), ARIAL));

        // PRINT ONLY IF THE SIGNATORIES DECLARATION HAS NOT BEEN DESIGNED IN A DISTINCT LEGAL ACT
        if (!localBean.getSignatureCard()) {
            paragraph.add(new Phrase(this.propertiesHelper.getMessage("pdf.customer.declarations.comma7", locale),
                    ARIAL));
            paragraph.add(new Phrase(this.propertiesHelper.getMessage("pdf.customer.declarations.comma8", locale),
                    ARIAL));
            paragraph.add(new Phrase(this.propertiesHelper.getMessage("pdf.customer.declarations.comma9", locale),
                    ARIAL));
            paragraph.add(new Phrase(
                    this.propertiesHelper.getMessage("pdf.customer.declarations.comma10", locale), ARIAL));
        }
        paragraph.add(new Phrase(this.propertiesHelper.getMessage("pdf.customer.declarations.comma11", locale),
                ARIAL));
        paragraph.setAlignment(Element.ALIGN_JUSTIFIED);
        document.add(paragraph);
    }

    /**
     * Creates a table for Personal Data.
     * @param document the Document to write to
     * @throws DocumentException
     */
    private void addPersonalData(final Document document, final Locale locale, final PDFBean localBean)
            throws DocumentException {
        final String countryRef = "_" + localBean.getCountry().getCountry();
        // Print the followings paragraph for the countries area BASED (EEA or non-EEA)? Now specific to country
        final Paragraph paragraph = new Paragraph();
        paragraph.setLeading(0, 1);
        paragraph.add(new Phrase(this.propertiesHelper.getMessage("pdf.customer.personal.data.para1" + countryRef,
                locale), ARIAL));
        paragraph.add(new Phrase(this.propertiesHelper.getMessage("pdf.customer.personal.data.para2" + countryRef,
                locale), ARIAL));
        paragraph.add(new Phrase(this.propertiesHelper.getMessage("pdf.customer.personal.data.para3" + countryRef,
                locale), ARIAL));
        paragraph.setAlignment(Element.ALIGN_JUSTIFIED);
        document.add(paragraph);
    }

    /**
     * Creates a table for Glossary.
     * @param document the Document to write to
     * @throws DocumentException
     */
    private void addGlossary(final Document document, final Locale locale, final PDFBean localBean)
            throws DocumentException {
        final String country = localBean.getCountry().getCountry();
        final String countryRef = "_" + country;

        final PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(TAB_WIDTH_PERCENT);
        table.setWidths(new float[] {TAB_WIDTH_COL1_GLOSSARY, TAB_WIDTH_COL2_GLOSSARY});
        this.colorCol1 = null;
        this.noBorder = true;
        final String text = this.propertiesHelper.getMessage("pdf.bank.glossary.warning" + countryRef, locale);
        if (StringUtils.isNotBlank(text)) {
            final PdfPCell cell = new PdfPCell(new Phrase(text, ARIAL));
            cell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
            cell.setPaddingBottom(PADDING_BOTTOM);
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setColspan(2);
            table.addCell(cell);
            add2ColsRow(table, STR_SPACE, STR_SPACE);
        }
        add2ColsRow(table, this.propertiesHelper.getMessage("pdf.bank.glossary.label1", locale),
                this.propertiesHelper.getMessage("pdf.bank.glossary.data1", locale));
        add2ColsRow(table, STR_SPACE, STR_SPACE);
        add2ColsRow(table, this.propertiesHelper.getMessage("pdf.bank.glossary.label2", locale),
                this.propertiesHelper.getMessage("pdf.bank.glossary.data2", locale));
        add2ColsRow(table, STR_SPACE, STR_SPACE);
        add2ColsRow(table, this.propertiesHelper.getMessage("pdf.bank.glossary.label3", locale),
                this.propertiesHelper.getMessage("pdf.bank.glossary.data3", locale));
        add2ColsRow(table, STR_SPACE, STR_SPACE);
        add2ColsRow(table, this.propertiesHelper.getMessage("pdf.bank.glossary.label4", locale),
                this.propertiesHelper.getMessage("pdf.bank.glossary.data4", locale));
        add2ColsRow(table, STR_SPACE, STR_SPACE);
        add2ColsRow(table, this.propertiesHelper.getMessage("pdf.bank.glossary.label5", locale),
                this.propertiesHelper.getMessage("pdf.bank.glossary.data5", locale));
        add2ColsRow(table, STR_SPACE, STR_SPACE);
        add2ColsRow(table, this.propertiesHelper.getMessage("pdf.bank.glossary.label6", locale),
                this.propertiesHelper.getMessage("pdf.bank.glossary.data6", locale));
        add2ColsRow(table, STR_SPACE, STR_SPACE);
        add2ColsRow(table, this.propertiesHelper.getMessage("pdf.bank.glossary.label7", locale),
                this.propertiesHelper.getMessage("pdf.bank.glossary.data7", locale));
        add2ColsRow(table, STR_SPACE, STR_SPACE);
        add2ColsRow(table, this.propertiesHelper.getMessage("pdf.bank.glossary.label8", locale),
                this.propertiesHelper.getMessage("pdf.bank.glossary.data8", locale));
        add2ColsRow(table, STR_SPACE, STR_SPACE);
        add2ColsRow(table, this.propertiesHelper.getMessage("pdf.bank.glossary.label9", locale),
                this.propertiesHelper.getMessage("pdf.bank.glossary.data9", locale));
        add2ColsRow(table, STR_SPACE, STR_SPACE);
        add2ColsRow(table, this.propertiesHelper.getMessage("pdf.bank.glossary.label10", locale),
                this.propertiesHelper.getMessage("pdf.bank.glossary.data10", locale));
        add2ColsRow(table, STR_SPACE, STR_SPACE);
        add2ColsRow(table, this.propertiesHelper.getMessage("pdf.bank.glossary.label11", locale),
                this.propertiesHelper.getMessage("pdf.bank.glossary.data11", locale));
        add2ColsRow(table, STR_SPACE, STR_SPACE);
        add2ColsRow(table, this.propertiesHelper.getMessage("pdf.bank.glossary.label12", locale),
                this.propertiesHelper.getMessage("pdf.bank.glossary.data12", locale));
        add2ColsRow(table, STR_SPACE, STR_SPACE);
        add2ColsRow(table, this.propertiesHelper.getMessage("pdf.bank.glossary.label13", locale),
                this.propertiesHelper.getMessage("pdf.bank.glossary.data13", locale));
        add2ColsRow(table, STR_SPACE, STR_SPACE);

        document.add(table);

        final PdfPTable table2 = new PdfPTable(2);
        table2.setWidthPercentage(TAB_WIDTH_PERCENT);
        table2.setWidths(new float[] {TAB_WIDTH_COL1_SIZE, TAB_WIDTH_COL2_SIZE});

        this.noBorder = true;
        this.dataToRight = true;
        add2ColsRow(table2, STR_SPACE, STR_SPACE);
        add2ColsRow(table2, this.propertiesHelper.getMessage("pdf.bank.glossary.place.label", locale),
                this.propertiesHelper.getMessage("pdf.bank.glossary.place.value", locale));
        add2ColsRow(table2, STR_SPACE, STR_SPACE);
        add2ColsRow(table2, this.propertiesHelper.getMessage("pdf.bank.glossary.date.label", locale),
                this.propertiesHelper.getMessage("pdf.bank.glossary.date.value", locale));
        add2ColsRow(table2, STR_SPACE, STR_SPACE);
        this.dataToRight = false;
        this.noBorder = false;

        document.add(table2);
    }

    /**
     * print ALL legal representatives details
     * @param document
     * @param locale
     * @param localBean
     * @throws DocumentException
     */
    private void addCustomerRepresentatives(final Document document, final Locale locale, final PDFBean localBean)
            throws DocumentException {
        final String country = localBean.getCountry().getCountry();
        final String countryRef = "_" + country;

        final PdfPTable table1 = new PdfPTable(1);
        table1.setWidthPercentage(TAB_WIDTH_PERCENT);

        final PdfPCell cell1 = createSubTitleCell(this.propertiesHelper.getMessage(
                "pdf.bank.customer.representatives.title", locale) + localBean.getEntity().getName());
        table1.addCell(cell1);
        table1.addCell(createSpanTwoCell(STR_SPACE, Rectangle.NO_BORDER, null));

        final PdfPCell col1 = new PdfPCell(new Phrase(this.propertiesHelper.getMessage(
                "pdf.bank.customer.representatives.label3", locale), ARIAL));
        col1.setBackgroundColor(COL_GRAY);
        col1.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
        col1.setPaddingBottom(PADDING_BOTTOM);

        final PdfPCell col2 = new PdfPCell(new Phrase(this.propertiesHelper.getMessage(
                "pdf.bank.customer.representatives.data3", locale), ARIAL_ITALIC));
        col2.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
        col2.setBorderWidth(col1.getBorderWidth() * BORDER_BOLD_FACTOR);
        col2.setPaddingBottom(PADDING_BOTTOM);
        col2.setBorderColor(COL_RED);
        if (localBean.getEntity().getRepresentativeList() != null && !localBean.getEntity().getRepresentativeList().isEmpty()) {
            document.add(table1);
            printCustomerRepresentatives(document, locale, localBean, col1, col2);
        }

        // Special terms and conditions for ITALY ONLY
        if (country.equals("IT")) {
            final PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(TAB_WIDTH_PERCENT);
            table.setWidths(new float[] {TAB_WIDTH_COL1_GLOSSARY, TAB_WIDTH_COL2_GLOSSARY});
            this.colorCol1 = null;
            this.noBorder = true;
            final Paragraph textIt = new Paragraph(localBean.getXbasV2() ? this.propertiesHelper.getMessage("pdf.bank.specific.terms1_xBasV2"
                    + countryRef, locale) : this.propertiesHelper.getMessage("pdf.bank.specific.terms1"
                            + countryRef, locale), ARIAL);
            textIt.add(new Phrase(localBean.getXbasV2() ? this.propertiesHelper
            		.getMessage("pdf.bank.specific.terms2_xBasV2" + countryRef, locale) : this.propertiesHelper
            		.getMessage("pdf.bank.specific.terms2" + countryRef, locale), ARIAL_BOLD));
            textIt.add(new Phrase(localBean.getXbasV2() ? this.propertiesHelper
            		.getMessage("pdf.bank.specific.terms3_xBasV2" + countryRef, locale) : this.propertiesHelper
            		.getMessage("pdf.bank.specific.terms3" + countryRef, locale), ARIAL));
            textIt.add(new Phrase(localBean.getXbasV2() ? this.propertiesHelper
            		.getMessage("pdf.bank.specific.terms4_xBasV2" + countryRef, locale) : this.propertiesHelper
            		.getMessage("pdf.bank.specific.terms4" + countryRef, locale), ARIAL_BOLD));
            textIt.add(new Phrase(localBean.getXbasV2() ? this.propertiesHelper
            		.getMessage("pdf.bank.specific.terms5_xBasV2" + countryRef, locale) : this.propertiesHelper
            		.getMessage("pdf.bank.specific.terms5" + countryRef, locale), ARIAL));

            if (!textIt.isEmpty()) {
                final PdfPCell cell10 = new PdfPCell(textIt);
                cell10.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
                cell10.setPaddingBottom(PADDING_BOTTOM);
                cell10.setBorder(Rectangle.BOX);
                cell10.setColspan(2);

                table.addCell(cell10);
                add2ColsRow(table, STR_SPACE, STR_SPACE);
            }
            document.add(table);

            final PdfPTable table2 = new PdfPTable(2);
            table2.setWidthPercentage(TAB_WIDTH_PERCENT);
            table2.setWidths(new float[] {TAB_WIDTH_COL1_SIZE, TAB_WIDTH_COL2_SIZE});

            this.noBorder = true;
            this.dataToRight = true;
            add2ColsRow(table2, STR_SPACE, STR_SPACE);
            add2ColsRow(table2, this.propertiesHelper.getMessage("pdf.bank.glossary.place.label", locale),
                    this.propertiesHelper.getMessage("pdf.bank.glossary.place.value", locale));
            add2ColsRow(table2, STR_SPACE, STR_SPACE);
            add2ColsRow(table2, this.propertiesHelper.getMessage("pdf.bank.glossary.date.label", locale),
                    this.propertiesHelper.getMessage("pdf.bank.glossary.date.value", locale));
            add2ColsRow(table2, STR_SPACE, STR_SPACE);
            this.dataToRight = false;
            this.noBorder = false;

            document.add(table2);
            if (localBean.getEntity().getRepresentativeList() != null
                    && !localBean.getEntity().getRepresentativeList().isEmpty()) {
                document.add(table1);
                printCustomerRepresentatives(document, locale, localBean, col1, col2);
            }
        }
        this.colorCol1 = null;
    }

    /**
     * @param document
     * @param locale
     * @param localBean
     * @param col1
     * @param col2
     * @throws DocumentException
     */
    private void printCustomerRepresentatives(final Document document, final Locale locale,
            final PDFBean localBean, final PdfPCell col1, final PdfPCell col2) throws DocumentException {
        this.colorCol1 = COL_GRAY;
        for (final PDFBeanRepresentative representative : localBean.getEntity().getRepresentativeList()) {
            final PdfPTable table = new PdfPTable(2);
            table.setWidths(new float[] {TAB_WIDTH_COL1_SIZE, TAB_WIDTH_COL2_SIZE});
            table.setWidthPercentage(TAB_WIDTH_PERCENT);
            table.setKeepTogether(true);
            table.setSpacingBefore(0);
            table.setSpacingAfter(0);

            add2ColsRow(table,
                    this.propertiesHelper.getMessage("pdf.bank.customer.representatives.label1", locale),
                    representative.getName());
            add2ColsRow(table,
                    this.propertiesHelper.getMessage("pdf.bank.customer.representatives.label2", locale),
                    representative.getFirstname());
            table.addCell(col1);
            table.addCell(col2);
            table.addCell(createSpanTwoCell(STR_SPACE, Rectangle.NO_BORDER, null));

            document.add(table);
        }
    }

    /**
     * Handle the creation of the purpose cell for account from Italy.
     * @param locale
     * @param accountCounter
     * @return table
     * @throws DocumentException
     */
    private PdfPTable createOpeningPuposeTableIT(final Locale locale, final int accountCounter)
            throws DocumentException {
        final PdfPTable table = new PdfPTable(new float[] {1, 25});
        table.setWidthPercentage(TAB_WIDTH_COL2_SIZE);

        addLineInnerTable(table, "opening_purpose_IT1_" + accountCounter,
                this.propertiesHelper.getMessage("pdf.account.data.opening.purpose.IT1", locale));
        addLineInnerTable(table, "opening_purpose_IT2_" + accountCounter,
                this.propertiesHelper.getMessage("pdf.account.data.opening.purpose.IT2", locale));
        addLineInnerTable(table, "opening_purpose_IT3_" + accountCounter,
                this.propertiesHelper.getMessage("pdf.account.data.opening.purpose.IT3", locale));
        addLineInnerTable(table, "opening_purpose_IT4_" + accountCounter,
                this.propertiesHelper.getMessage("pdf.account.data.opening.purpose.IT4", locale));
        return table;

    }

    /***************************************************************************************************************
     ************************************ _____SIGNATURE CARD____ **************************************************
     **************************************************************************************************************/

    /**
     * Creates a table for Authorized Signatories.
     * @param document the Document to write to
     * @param country
     * @param signatureCardExist
     * @throws DocumentException
     */
    private void addAuthorizedSignatoriesSignatureCard(final Document document, final Locale locale,
            final SimpleDateFormat dateFormat, final List<PDFBeanAccount> accounts, final Locale country,
            final Boolean signatureCardExist) throws DocumentException {

    	final PdfPCell cell1 = createSubTitleCell(this.propertiesHelper.getMessage(
                "pdf.signature.card.authorized.signatories.block.label", locale));
		
		final Paragraph text1 = new Paragraph(this.propertiesHelper.getMessage(
                "pdf.signature.card.authorized.signatories.note.label.data1", locale), ARIAL);
        text1.add(new Phrase(this.propertiesHelper.getMessage(
                "pdf.signature.card.authorized.signatories.note.label.data2", locale), ARIAL));
        text1.add(new Phrase(this.propertiesHelper.getMessage(
                "pdf.signature.card.authorized.signatories.note.label.data3", locale), ARIAL));
        text1.add(new Phrase(this.propertiesHelper.getMessage(
                "pdf.signature.card.authorized.signatories.note.label.data4", locale), ARIAL));
        text1.add(new Phrase(this.propertiesHelper.getMessage(
                "pdf.signature.card.authorized.signatories.note.label.data5", locale), ARIAL));
        text1.add(new Phrase(this.propertiesHelper.getMessage(
                "pdf.signature.card.authorized.signatories.note.label.data6", locale), ARIAL));
        
        final PdfPCell cell2 = new PdfPCell(text1);
        cell2.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
        cell2.setPaddingBottom(PADDING_BOTTOM);
        cell2.setBackgroundColor(COL_GRAY);
        cell2.setBorder(Rectangle.BOX);
        document.add(createFieldset(cell1, cell2));
       // document.add(new Phrase(STR_SPACE));
    	
//        final PdfPCell cell1 = createSubTitleCell(this.propertiesHelper.getMessage(
//                "pdf.signature.card.authorized.signatories.note.label", locale));
//
//        final Paragraph text1 = new Paragraph(this.propertiesHelper.getMessage(
//                "pdf.signature.card.authorized.signatories.note.data1", locale), ARIAL);
//        text1.add(new Phrase(this.propertiesHelper.getMessage(
//                "pdf.signature.card.authorized.signatories.note.data2", locale), ARIAL));
//        text1.add(new Phrase(this.propertiesHelper.getMessage(
//                "pdf.signature.card.authorized.signatories.note.data3", locale), ARIAL));
//
//        final PdfPCell cell2 = new PdfPCell(text1);
//        cell2.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
//        cell2.setPaddingBottom(PADDING_BOTTOM);
//        cell2.setBackgroundColor(COL_GRAY);
//        cell2.setBorder(Rectangle.BOX);
//        document.add(createFieldset(cell1, cell2));
//        document.add(new Phrase(STR_SPACE));
//
//        if (!signatureCardExist) {
//            document.add(new Phrase(this.propertiesHelper.getMessage(
//                    "pdf.signature.card.authorized.signatories.otherwise", locale), ARIAL));
//            document.add(new Phrase(STR_SPACE));
//            return;
//        }
//
//        final PdfPCell cell3 = createSubTitleCell(this.propertiesHelper.getMessage(
//                "pdf.signature.card.authorized.signatories.block.label", locale));
//
//        final Paragraph text2 = new Paragraph(this.propertiesHelper.getMessage(
//                "pdf.signature.card.authorized.signatories.block.data1", locale), ARIAL);
//        text2.add(new Phrase(this.propertiesHelper.getMessage(
//                "pdf.signature.card.authorized.signatories.block.data2", locale), ARIAL));
//        text2.add(new Phrase(this.propertiesHelper.getMessage(
//                "pdf.signature.card.authorized.signatories.block.data3", locale), ARIAL));
//        text2.add(new Phrase(this.propertiesHelper.getMessage(
//                "pdf.signature.card.authorized.signatories.block.data4", locale), ARIAL));
//        text2.add(new Phrase(this.propertiesHelper.getMessage(
//                "pdf.signature.card.authorized.signatories.block.data5", locale), ARIAL));
////        text2.add(new Phrase(this.propertiesHelper.getMessage(
////                "pdf.signature.card.authorized.signatories.block.data6", locale), ARIAL));
////        text2.add(new Phrase(this.propertiesHelper.getMessage(
////                "pdf.signature.card.authorized.signatories.block.data7", locale), ARIAL));
////        text2.add(new Phrase(this.propertiesHelper.getMessage(
////                "pdf.signature.card.authorized.signatories.block.data8", locale), ARIAL));
////        text2.add(new Phrase(this.propertiesHelper.getMessage(
////                "pdf.signature.card.authorized.signatories.block.data9", locale), ARIAL));
////        text2.add(new Phrase(this.propertiesHelper.getMessage(
////                "pdf.signature.card.authorized.signatories.block.data10", locale), ARIAL));
//
//        final PdfPCell cell4 = new PdfPCell(text2);
//        cell4.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
//        cell4.setPaddingBottom(PADDING_BOTTOM);
//        cell4.setBackgroundColor(COL_GRAY);
//        cell4.setBorder(Rectangle.BOX);
//
//        document.add(createFieldset(cell3, cell4));

//        final List<PDFBeanThirdParty> usedTList = new ArrayList<PDFBeanThirdParty>();
//        int authorizedSignatoriesCounter = 0;
//        for (final PDFBeanAccount beanAccount : accounts) {
//            for (final PDFBeanThirdParty thirdParty : beanAccount.getAccountThirdPartyList()) {
//                if (usedTList.contains(thirdParty)) {
//                    continue;
//                } else {
//                    usedTList.add(thirdParty);
//                }
//                if (!thirdParty.getSignatureCard()) {
//                    continue;
//                }
//                document.add(new Phrase(STR_SPACE));
//
//                final PdfPTable table = new PdfPTable(2);
//                // Filling the table
//                table.setWidthPercentage(TAB_WIDTH_PERCENT);
//                table.setWidths(new float[] {TAB_WIDTH_COL1_SIZE, TAB_WIDTH_COL2_SIZE});
//
//                // background color of columns
//                this.colorCol1 = COL_GRAY;
//                add2ColsRow(table, this.propertiesHelper.getMessage(
//                        "pdf.signature.card.authorized.signatories.lastname", locale), thirdParty.getLastName());
//                add2ColsRow(table, this.propertiesHelper.getMessage(
//                        "pdf.signature.card.authorized.signatories.firstname", locale), thirdParty.getFirstName());
//                add2ColsRow(table, this.propertiesHelper.getMessage(
//                        "pdf.signature.card.authorized.signatories.position", locale), thirdParty.getPosition());
////                add2ColsRow(table,
////                        this.propertiesHelper.getMessage("pdf.signature.card.authorized.signatories.dob", locale),
////                        thirdParty.getBirthDate() == null ? "" : dateFormat.format(thirdParty.getBirthDate()));
//                add2ColsRow(table,
//                        this.propertiesHelper.getMessage("pdf.signature.card.authorized.signatories.pob", locale),
//                        thirdParty.getBirthPlace());
//                if (thirdParty.getCitizenships() != null) {
//                    final StringBuilder stringBuilder = new StringBuilder();
//                    for (final Locale locale2 : thirdParty.getCitizenships()) {
//                        if (stringBuilder.length() == 0) {
//                            stringBuilder.append(locale2.getDisplayCountry(locale));
//                        } else {
//                            stringBuilder.append(", " + locale2.getDisplayCountry(locale));
//                        }
//                    }
//                    add2ColsRow(table, this.propertiesHelper.getMessage(
//                            "pdf.signature.card.authorized.signatories.citizenships", locale),
//                            stringBuilder.toString());
//                } else {
//                    add2ColsRow(table, this.propertiesHelper.getMessage(
//                            "pdf.signature.card.authorized.signatories.citizenships", locale), null);
//                }
//                add2ColsRow(table, this.propertiesHelper.getMessage(
//                        "pdf.signature.card.authorized.signatories.telephone", locale), thirdParty.getTelephone());
//                add2ColsRow(table,
//                        this.propertiesHelper.getMessage("pdf.signature.card.authorized.signatories.fax", locale),
//                        thirdParty.getFax());
//                add2ColsRow(table, this.propertiesHelper.getMessage(
//                        "pdf.signature.card.authorized.signatories.email", locale), thirdParty.getEmail());
//
//                addAddress(table, this.propertiesHelper.getMessage(
//                        "pdf.signature.card.authorized.signatories.address", locale), thirdParty.getAddressHome());
//
//                PDFBeanActingDetails actingIndiv = null, actingJoint = null, actingOther = null, actingDate = null, actingDateES = null;
//                if (thirdParty.getPdfBeanActingDetails() != null) {
//                    for (final PDFBeanActingDetails details : thirdParty.getPdfBeanActingDetails()) {
//                        switch (details.getPowerCode()) {
//                        case Constants.PARAM_CODE_ACC_THIRD_PWR_FR1:
//                            continue;
//                        case Constants.PARAM_CODE_ACC_THIRD_PWR_FR2:
//                            break;
//                        case Constants.PARAM_CODE_ACC_THIRD_PWR_ES:
//                            actingDateES = details;
//                            break;
//                        default:
//                            actingDate = details;
//                            break;
//                        }
//                        if (details.getActingIndivflag()) {
//                            actingIndiv = details;
//                        } else if (details.getActingJointlyflag()) {
//                            actingJoint = details;
//                        } else {
//                            actingOther = details;
//                        }
//                    }
//                }
////                if (actingDate != null) {
////                    table.addCell(createSpanTwoCell(this.propertiesHelper.getMessage(
////                            "pdf.signature.card.authorized.signatories.label1", locale), Rectangle.BOX, COL_GRAY));
////                    switch (actingDate.getPowerCode()){
////                    case Constants.PARAM_CODE_ACC_THIRD_PWR_ES :
////                    	add2ColsRow(
////                                table,
////                                this.propertiesHelper.getMessage("pdf.signature.card.authorized.signatories.label2_2",
////                                        locale),
////                                actingDate.getBoardResolutionDate() != null ? dateFormat.format(actingDate
////                                        .getBoardResolutionDate()) : "");
////                    	break;
////                    case Constants.PARAM_CODE_ACC_THIRD_PWR_PT :
////                    	add2ColsRow(
////                                table,
////                                this.propertiesHelper.getMessage("pdf.signature.card.authorized.signatories.label2_1",
////                                        locale),
////                                actingDate.getBoardResolutionDate() != null ? dateFormat.format(actingDate
////                                        .getBoardResolutionDate()) : "");
////                    	break;
////                    case Constants.PARAM_CODE_ACC_THIRD_PWR_GB :
////                    	add2ColsRow(
////                                table,
////                                this.propertiesHelper.getMessage("pdf.signature.card.authorized.signatories.label2",
////                                        locale),
////                                actingDate.getBoardResolutionDate() != null ? dateFormat.format(actingDate
////                                        .getBoardResolutionDate()) : "");
////                    	break;
////                    }
////                }
////                if (actingDateES != null) {
////                    table.addCell(createSpanTwoCell(this.propertiesHelper.getMessage(
////                            "pdf.signature.card.authorized.signatories.label3", locale), Rectangle.BOX, COL_GRAY));
////
////                    add2ColsRow(
////                            table,
////                            this.propertiesHelper.getMessage("pdf.signature.card.authorized.signatories.label4",
////                                    locale),
////                            actingDateES.getBoardResolutionDate() != null ? dateFormat.format(actingDateES
////                                    .getBoardResolutionDate()) : "");
////                    add2ColsRow(table, this.propertiesHelper.getMessage(
////                            "pdf.signature.card.authorized.signatories.label5", locale),
////                            actingDateES.getPublicDeedReference());
////                }
//                table.addCell(createSpanTwoCell(STR_SPACE, Rectangle.BOX, COL_GRAY));
//
//                add2ColsRow(table, this.propertiesHelper.getMessage(
//                        "pdf.signature.card.authorized.signatories.label6", locale),
//                        this.propertiesHelper
//                                .getMessage("pdf.signature.card.authorized.signatories.data6", locale));
//                this.dataToRight = true;
//                add2ColsRow(
//                        table,
//                        this.propertiesHelper.getMessage("pdf.signature.card.authorized.signatories.label7",
//                                locale),
//                        (actingIndiv != null && actingIndiv.getDeviseAmountLimit() != null) ? this.propertiesHelper.getMessage(
//                                "pdf.signature.card.authorized.signatories.data71", locale)
//                                + actingIndiv.getAmountLimit() + " " + actingIndiv.getDeviseAmountLimit(): this.propertiesHelper.getMessage(
//                                "pdf.signature.card.authorized.signatories.data72", locale));
//                if (actingOther == null && actingJoint == null) {
//                    add2ColsRow(table, this.propertiesHelper.getMessage(
//                            "pdf.signature.card.authorized.signatories.label8", locale),
//                            this.propertiesHelper.getMessage("pdf.signature.card.authorized.signatories.data84",
//                                    locale));
//                } else { 
//                		final String textOther = (actingOther != null && actingOther.getDeviseAmountLimit() != null) ? this.propertiesHelper.getMessage(
//                            "pdf.signature.card.authorized.signatories.data81", locale)
//                            + actingOther.getAmountLimit() + " " + actingOther.getDeviseAmountLimit()
//                            + this.propertiesHelper.getMessage("pdf.signature.card.authorized.signatories.data83",
//                                    locale) : "";
//                    if (actingJoint == null) {
//                        add2ColsRow(table, this.propertiesHelper.getMessage(
//                                "pdf.signature.card.authorized.signatories.label8", locale), textOther);
//                    } else {
//                        final StringBuilder bTextJoint = new StringBuilder();
//                        if (actingJoint.getJointThidParty() != null) {
//                            boolean flag = false;
//                            for (final AjaxDto tp : actingJoint.getJointThidParty()) {
//                                if (flag) {
//                                    bTextJoint.append(this.propertiesHelper.getMessage(
//                                            "pdf.signature.card.authorized.signatories.separator", locale));
//                                } else {
//                                    flag = true;
//                                }
//                                bTextJoint.append(tp.getLabel() + " " + tp.getLogin());
//                            }
//                            if (flag) {
//                                bTextJoint.append(this.propertiesHelper.getMessage(
//                                        "pdf.signature.card.authorized.signatories.end", locale));
//                            }
//                        }
//                        final String textJoint = bTextJoint.toString();
//                        add2ColsRow(
//                                table,
//                                this.propertiesHelper.getMessage(
//                                        "pdf.signature.card.authorized.signatories.label8", locale),
//                                actingJoint.getDeviseAmountLimit() != null ? this.propertiesHelper.getMessage(
//                                        "pdf.signature.card.authorized.signatories.data81", locale)
//                                        + actingJoint.getAmountLimit() + " " + actingJoint.getDeviseAmountLimit()
//                                        + this.propertiesHelper.getMessage(
//                                                "pdf.signature.card.authorized.signatories.data82", locale)
//                                        + textJoint + (StringUtils.isNotEmpty(textOther) ? "\n" + textOther : "")
//                                        : this.propertiesHelper.getMessage(
//                                                "pdf.signature.card.authorized.signatories.data81", locale)
//                                                + actingJoint.getAmountLimit()
//                                                + this.propertiesHelper.getMessage(
//                                                        "pdf.signature.card.authorized.signatories.data82", locale)
//                                                + textJoint + (StringUtils.isNotEmpty(textOther) ? "\n" + textOther : ""));
//                    }
//                }
//                this.dataToRight = false;
//                this.noBorderCol1 = false;
//
//                final PdfPCell coll71 = new PdfPCell(new Phrase(this.propertiesHelper.getMessage(
//                        "pdf.signature.card.authorized.signatories.label9", locale), ARIAL));
//                coll71.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
//                coll71.setPaddingBottom(PADDING_BOTTOM);
//                coll71.setBackgroundColor(COL_GRAY);
//                table.addCell(coll71);
//
//                final PdfPTable coll72 = createASSCInnerTable(locale, authorizedSignatoriesCounter);
//                table.addCell(coll72);
//                authorizedSignatoriesCounter++;
//
//                add2ColsRow(table, this.propertiesHelper.getMessage(
//                        "pdf.signature.card.authorized.signatories.label10", locale),
//                        country.getDisplayCountry(locale));
//
//                final PdfPCell coll81 = new PdfPCell(new Phrase(this.propertiesHelper.getMessage(
//                        "pdf.signature.card.authorized.signatories.label11", locale), ARIAL));
//                coll81.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
//                coll81.setPaddingBottom(PADDING_BOTTOM);
//                coll81.setBackgroundColor(COL_GRAY);
//                final PdfPCell coll82 = new PdfPCell(new Phrase(this.propertiesHelper.getMessage(
//                        "pdf.signature.card.authorized.signatories.data11", locale), ARIAL_ITALIC));
//                coll82.setBorderWidth(coll82.getBorderWidth() * BORDER_BOLD_FACTOR);
//                coll82.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
//                coll82.setPaddingBottom(PADDING_BOTTOM);
//                coll82.setBorderColor(COL_RED);
//                table.addCell(coll81);
//                table.addCell(coll82);
//
//                final PdfPCell masterCell = new PdfPCell(table);
//                masterCell.setBorder(Rectangle.BOX);
//                // Finally adding the title and the data table to the document
//                document.add(createFieldset(null, masterCell));
//            }
//        }
    }

    private PdfPTable createASSCInnerTable(final Locale locale, final int counter) throws DocumentException {
        final PdfPTable table = new PdfPTable(new float[] {1, 25});
        table.setWidthPercentage(TAB_WIDTH_COL2_SIZE);

        addLineInnerTable(table, "authorized_signatories_data91_" + counter,
                this.propertiesHelper.getMessage("pdf.signature.card.authorized.signatories.data91", locale));
        addLineInnerTable(table, "authorized_signatories_data92_" + counter,
                this.propertiesHelper.getMessage("pdf.signature.card.authorized.signatories.data92", locale));
        addLineInnerTable(table, "authorized_signatories_data93_" + counter,
                this.propertiesHelper.getMessage("pdf.signature.card.authorized.signatories.data93", locale));

        return table;
    }

    /***************************************************************************************************************
     ***************************************** _____COMMON____ *****************************************************
     **************************************************************************************************************/

    /**
     * Utility method to add a new line of two columns (and setBackgroundColor too if required).
     * @param table the PdfTable to use
     * @param col1 the text to put in column 1
     * @param col2 the text to put in column 2
     */
    private void add2ColsRow(final PdfPTable table, final String col1, final String col2) throws DocumentException {
        PdfPCell cell1;
        cell1 = new PdfPCell(new Phrase(col1 != null ? col1 : "", ARIAL));
        // or set border of this column only (col1)
        if (this.noBorderTopBottom || this.noBorderCol1) {
            cell1.setBorder(Rectangle.LEFT);
            cell1.setBorder(Rectangle.RIGHT);
        } else if (this.noBorder) {
            cell1.setBorder(Rectangle.NO_BORDER);
        } else {
            cell1.setBorder(Rectangle.BOX);
        }
        // alignment text's settings (col1)
        if (this.dataToRight) {
            cell1.setHorizontalAlignment(Element.ALIGN_RIGHT);
        } else {
            cell1.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
        }
        cell1.setPaddingBottom(PADDING_BOTTOM);
        if (this.colorCol1 != null) {
            cell1.setBackgroundColor(this.colorCol1);
        }
        table.addCell(cell1);

        PdfPCell cell2;
        cell2 = new PdfPCell(new Phrase(col2 != null ? col2 : "", ARIAL));
        // or also set border of this column only (col2)
        if (this.noBorderTopBottom) {
            cell2.setBorder(Rectangle.LEFT);
            cell2.setBorder(Rectangle.RIGHT);
        } else if (this.noBorder) {
            cell2.setBorder(Rectangle.NO_BORDER);
        } else {
            cell2.setBorder(Rectangle.BOX);
        }
        cell2.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
        cell2.setPaddingBottom(PADDING_BOTTOM);
        table.addCell(cell2);
    }
    
    private void add5ColsRow(final PdfPTable table, final String col1, final String col2, final String col3, final String col4, final String col5, boolean first) throws DocumentException {
        PdfPCell cell1;
        cell1 = new PdfPCell(new Phrase(col1 != null ? col1 : "", ARIAL));
        // or set border of this column only (col1)
        if (this.noBorderTopBottom || this.noBorderCol1) {
            cell1.setBorder(Rectangle.LEFT);
            cell1.setBorder(Rectangle.RIGHT);
        } else if (this.noBorder) {
            cell1.setBorder(Rectangle.NO_BORDER);
        } else {
            cell1.setBorder(Rectangle.BOX);
        }
        // alignment text's settings (col1)
        if (this.dataToRight) {
            cell1.setHorizontalAlignment(Element.ALIGN_RIGHT);
        } else {
            cell1.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
        }
        cell1.setPaddingBottom(PADDING_BOTTOM);
        if (first) {
            cell1.setBackgroundColor(this.colorCol1);
        }
        table.addCell(cell1);

        PdfPCell cell2;
        cell2 = new PdfPCell(new Phrase(col2 != null ? col2 : "", ARIAL));
        // or also set border of this column only (col2)
        if (this.noBorderTopBottom) {
            cell2.setBorder(Rectangle.LEFT);
            cell2.setBorder(Rectangle.RIGHT);
        } else if (this.noBorder) {
            cell2.setBorder(Rectangle.NO_BORDER);
        } else {
            cell2.setBorder(Rectangle.BOX);
        }
        cell2.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
        cell2.setPaddingBottom(PADDING_BOTTOM);
        if (first) {
            cell2.setBackgroundColor(this.colorCol1);
        }
        table.addCell(cell2);
        
        PdfPCell cell3;
        cell3 = new PdfPCell(new Phrase(col3 != null ? col3 : "", ARIAL));
        // or also set border of this column only (col2)
        if (this.noBorderTopBottom) {
        	cell3.setBorder(Rectangle.LEFT);
        	cell3.setBorder(Rectangle.RIGHT);
        } else if (this.noBorder) {
        	cell3.setBorder(Rectangle.NO_BORDER);
        } else {
        	cell3.setBorder(Rectangle.BOX);
        }
        cell3.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
        cell3.setPaddingBottom(PADDING_BOTTOM);
        if (first) {
            cell3.setBackgroundColor(this.colorCol1);
        }
        table.addCell(cell3);
        
        PdfPCell cell4;
        cell4 = new PdfPCell(new Phrase(col4 != null ? col4 : "", ARIAL));
        // or also set border of this column only (col2)
        if (this.noBorderTopBottom) {
        	cell4.setBorder(Rectangle.LEFT);
        	cell4.setBorder(Rectangle.RIGHT);
        } else if (this.noBorder) {
        	cell4.setBorder(Rectangle.NO_BORDER);
        } else {
        	cell4.setBorder(Rectangle.BOX);
        }
        cell4.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
        cell4.setPaddingBottom(PADDING_BOTTOM);
        if (first) {
            cell4.setBackgroundColor(this.colorCol1);
        }
        table.addCell(cell4);
        
        PdfPCell cell5;
        cell5 = new PdfPCell(new Phrase(col5 != null ? col5 : "", ARIAL));
        // or also set border of this column only (col2)
        if (this.noBorderTopBottom) {
        	cell5.setBorder(Rectangle.LEFT);
        	cell5.setBorder(Rectangle.RIGHT);
        } else if (this.noBorder) {
        	cell5.setBorder(Rectangle.NO_BORDER);
        } else {
        	cell5.setBorder(Rectangle.BOX);
        }
        cell5.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
        cell5.setPaddingBottom(PADDING_BOTTOM);
        if (first) {
            cell5.setBackgroundColor(this.colorCol1);
        }
        table.addCell(cell5);
    }


    /**
     * @param table
     * @param name
     * @param locale
     * @throws DocumentException
     */
    private void addLineInnerTable(final PdfPTable table, final String name, final String text)
            throws DocumentException {
        final PdfPCell cell1 = new PdfPCell();
        cell1.setCellEvent(new PdfCheckboxCellEvent(name));
        cell1.setBorder(Rectangle.NO_BORDER);
        cell1.setPadding(2);
        table.addCell(cell1);

        final PdfPCell cellx02 = new PdfPCell(new Phrase(text, ARIAL));
        cellx02.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
        cellx02.setPaddingBottom(PADDING_BOTTOM);
        cellx02.setBorder(Rectangle.NO_BORDER);
        table.addCell(cellx02);
    }

    /**
     * Utility method to create a fieldset holding a title and a body content.
     * @param title the title object to set (not null if defined)
     * @param tableCell the table content to set
     * @return a PdfPtable representing the fieldset
     */
    private static PdfPTable createFieldset(final PdfPCell title, final PdfPCell tableCell) {
        final PdfPTable mainTable = new PdfPTable(1);
        mainTable.setWidthPercentage(TAB_WIDTH_PERCENT);
        // Adding the title
        if (title != null) {
            mainTable.addCell(title);
        }
        // Adding the data table
        mainTable.addCell(tableCell);
        // Wrapping the main table in a "fieldset cell"
        final PdfPCell fieldsetCell = new PdfPCell(mainTable);
        // Wrapping the "fieldset cell" in a "fieldset table"
        final PdfPTable fieldsetTable = new PdfPTable(1);
        fieldsetTable.setWidthPercentage(TAB_WIDTH_PERCENT);
        fieldsetTable.addCell(fieldsetCell);
        return fieldsetTable;
    }

    /**
     * add title to a section
     * @param document
     * @param titleText
     * @param spaceBefore
     * @param printTable print the table or return it to be print after
     * @return table containing the title
     */
    private PdfPTable addTitle(final Document document, final String titleText, final boolean spaceBefore,
            final boolean printTable) throws DocumentException {

        final PdfPTable table = new PdfPTable(1);
        table.setWidthPercentage(TAB_WIDTH_PERCENT);
        table.setKeepTogether(true);

        if (spaceBefore) {
            final PdfPCell cell0 = new PdfPCell(new Phrase(STR_SPACE));
            cell0.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell0);
        }

        final PdfPCell cell1 = new PdfPCell(new Phrase(titleText, ARIAL_TITLE));
        cell1.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell1);

        final PdfPCell cell2 = new PdfPCell(new Phrase(STR_SPACE));
        cell2.setBorderWidthTop(TAB_BORDER_WIDTH);
        cell2.setBorderColorTop(TAB_HEADER);
        cell2.setBorder(Rectangle.TOP);
        table.addCell(cell2);
        if (printTable) {
            document.add(table);
        }
        return table;
    }

    private PdfPCell createSubTitleCell(final String subTitleText) {
        final PdfPCell cell = new PdfPCell(new Phrase(subTitleText, ARIAL_WHITE));
        cell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
        cell.setPaddingBottom(PADDING_BOTTOM);
        cell.setBackgroundColor(TAB_HEADER);
        cell.setBorder(Rectangle.BOX);
        return cell;
    }

    /**
     * write an address to a given table
     * @param table
     * @param addressLabel
     * @param bean
     * @throws DocumentException
     */
    private void addAddress(final PdfPTable table, final String addressLabel, final PDFBeanAddress bean)
            throws DocumentException {
        if (bean == null) {
            add2ColsRow(table, addressLabel, null);
        } else {
            this.noBorderTopBottom = true;
            add2ColsRow(table, addressLabel, bean.getLine1());
            if (StringUtils.isNotBlank(bean.getLine2())) {
                add2ColsRow(table, null, bean.getLine2());
            }
            if (StringUtils.isNotBlank(bean.getLine3())) {
                add2ColsRow(table, null, bean.getLine3());
            }
            if (StringUtils.isNotBlank(bean.getLine4())) {
                add2ColsRow(table, null, (new Locale("",bean.getLine4())).getDisplayCountry());
            }
            if (StringUtils.isNotBlank(bean.getLine5())) {
                add2ColsRow(table, null, bean.getLine5());
            }
            if (StringUtils.isNotBlank(bean.getLine6())) {
                add2ColsRow(table, null, bean.getLine6());
            }
            if (StringUtils.isNotBlank(bean.getLine7())) {
                add2ColsRow(table, null, bean.getLine7());
            }
            this.noBorderTopBottom = false;
        }
    }

    private void addParagraph(final Document document, final Phrase phrase) throws DocumentException {
        final Paragraph paragraph = new Paragraph(phrase);
        paragraph.setLeading(0, 1);
        paragraph.setAlignment(Element.ALIGN_JUSTIFIED);
        document.add(paragraph);
    }

    /**
     * Create a cell of span 2
     * @param text text for the cell
     * @param border type or border
     * @param backgroundColor background color
     * @return the cell create
     */
    private PdfPCell createSpanTwoCell(final String text, final int border, final Color backgroundColor) {
        final PdfPCell cell = new PdfPCell(new Phrase(text, ARIAL));
        cell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
        cell.setPaddingBottom(PADDING_BOTTOM);
        if (backgroundColor != null) {
            cell.setBackgroundColor(backgroundColor);
        }
        cell.setBorder(border);
        cell.setColspan(2);
        return cell;
    }
}
