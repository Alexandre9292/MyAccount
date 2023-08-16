package com.bnpp.dco.presentation.utils.pdf;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bnpp.dco.common.exception.DCOException;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfTemplate;
import com.lowagie.text.pdf.PdfWriter;

public class PDFGeneratorEventListener extends PdfPageEventHelper {

    /** Logger. */
    private static final Logger LOG = LoggerFactory.getLogger(PDFGeneratorEventListener.class);

    private static final float TEXT_WIDTH = PageSize.A4.getWidth() - PDFGenerator.MARGIN_LEFT
            - PDFGenerator.MARGIN_RIGHT;
    private static final float FOOTER_WIDTH_LEFT = 355;
    private static final float FOOTER_WIDTH_CENTER = 290;
    private static final float FOOTER_WIDTH_RIGHT = 290;
    private static final float FOOTER_WIDTH_TOTAL = 20;
    private static final float FOOTER_HEIGHT = 9;
    private static final float LOGO_HEIGHT = 29;

    private static final String LOGO_PATH_FOLDER = "pdf/";
    private static final String LOGO_NAME_PREFIX = "logo";
    private static final String LOGO_NAME_SEPARATOR = "_";
    private static final String LOGO_NAME_EXTENSION = ".jpg";
    private static final String LOGO_NAME_DEFAULT = "logo.jpg";

    private static final Font ARIAL_FOOTER = FontFactory.getFont(PDFGenerator.FONTNAME, 7);

    private Image logo;
    private String footerMessageLeft;
    private String footerMessageCenter;
    private String footerMessageRight;
    private PdfTemplate total;

    public PDFGeneratorEventListener(final PDFBean bean, final String footerMessageLeft,
            final String footerMessageCenter, final String footerMessageRight) throws DCOException {
        try {
            InputStream isLogo = null;
            // Computing the logo name (without extension)
            final StringBuilder sbLogoNameSearch = new StringBuilder(LOGO_NAME_PREFIX);
            if (bean.getCountry() != null) {
                final String countryCode = bean.getCountry().getCountry();
                if (StringUtils.isNotBlank(countryCode)) {
                    sbLogoNameSearch.append(LOGO_NAME_SEPARATOR).append(countryCode);
                }
            }
            sbLogoNameSearch.append(LOGO_NAME_EXTENSION);
            final String logoNameSearch = sbLogoNameSearch.toString();
            // Finding the logo name in the directory
            isLogo = getClass().getClassLoader().getResourceAsStream(
                    new StringBuilder(LOGO_PATH_FOLDER).append(logoNameSearch).toString());
            if (isLogo == null) {
                // If the specific logo could not be found, using the default logo
                isLogo = getClass().getClassLoader().getResourceAsStream(
                        new StringBuilder(LOGO_PATH_FOLDER).append(LOGO_NAME_DEFAULT).toString());
                if (isLogo == null) {
                    // Neither the custom nor the default logos could be found
                    final String message = "PDF: Unable to find a valid logo to put in the PDF";
                    LOG.error(message);
                    throw new DCOException(message);
                }
            }
            // At this point, we have a logo name
            final byte[] logoBytes = IOUtils.toByteArray(isLogo);
            this.logo = Image.getInstance(logoBytes);
            this.logo.scaleToFit(TEXT_WIDTH, LOGO_HEIGHT);
            this.footerMessageLeft = footerMessageLeft;
            this.footerMessageCenter = footerMessageCenter;
            this.footerMessageRight = footerMessageRight;
        } catch (final BadElementException | IOException e) {
            final String message = "PDF: Unable to initialize the PDF generator";
            LOG.error(message, e);
            throw new DCOException(message, e);
        }
    }

    /**
     * Creates the PdfTemplate that will hold the total number of pages.
     * @see com.itextpdf.text.pdf.PdfPageEventHelper#onOpenDocument(com.itextpdf.text.pdf.PdfWriter,
     *      com.itextpdf.text.Document)
     */
    @Override
    public void onOpenDocument(final PdfWriter writer, final Document document) {
        this.total = writer.getDirectContent().createTemplate(FOOTER_WIDTH_TOTAL, FOOTER_HEIGHT);
    }

    @Override
    public void onStartPage(final PdfWriter writer, final Document document) {
        final PdfPTable tbl = new PdfPTable(1);
        tbl.getDefaultCell().setBorder(Rectangle.NO_BORDER);
        tbl.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        tbl.addCell(this.logo);
        tbl.setTotalWidth(this.logo.getScaledWidth());
        final float x = document.leftMargin();
        // align bottom between page edge and page margin
        final float y = (float) (document.top() + tbl.getTotalHeight() * 1.5);
        // write the table
        tbl.writeSelectedRows(0, -1, x, y, writer.getDirectContent());
    }

    @Override
    public void onEndPage(final PdfWriter writer, final Document document) {
        try {
            final PdfPTable table = new PdfPTable(4);
            table.setWidths(new float[] {FOOTER_WIDTH_LEFT, FOOTER_WIDTH_CENTER, FOOTER_WIDTH_RIGHT,
                    FOOTER_WIDTH_TOTAL});
            table.getDefaultCell().setBorder(Rectangle.NO_BORDER);
            table.getDefaultCell().setPadding(0);
            table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.getDefaultCell().setFixedHeight(FOOTER_HEIGHT);

            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(new Phrase(this.footerMessageLeft, ARIAL_FOOTER));

            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(new Phrase(this.footerMessageCenter, ARIAL_FOOTER));

            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(new Phrase(String.format(this.footerMessageRight, writer.getPageNumber()), ARIAL_FOOTER));

            // Adding total page number
            final PdfPCell cell = new PdfPCell(Image.getInstance(this.total));
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setPadding(0);
            cell.setFixedHeight(FOOTER_HEIGHT);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(cell);

            table.setTotalWidth(TEXT_WIDTH);
            final float x = document.leftMargin();
            final float y = (float) (document.bottom() - table.getTotalHeight() * 0.5);
            table.writeSelectedRows(0, -1, x, y, writer.getDirectContent());
        } catch (final DocumentException e) {
            LOG.error("PDF: Error while writing the footer", e);
        }
    }

    /**
     * Fills out the total number of pages before the document is closed.
     * @see com.itextpdf.text.pdf.PdfPageEventHelper#onCloseDocument(com.itextpdf.text.pdf.PdfWriter,
     *      com.itextpdf.text.Document)
     */
    @Override
    public void onCloseDocument(final PdfWriter writer, final Document document) {
        ColumnText.showTextAligned(this.total, Element.ALIGN_LEFT,
                new Phrase(String.valueOf(writer.getPageNumber() - 1), ARIAL_FOOTER), 2, 1, 0);
    }
}