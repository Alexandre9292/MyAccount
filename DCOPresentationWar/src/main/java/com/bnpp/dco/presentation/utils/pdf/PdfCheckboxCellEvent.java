package com.bnpp.dco.presentation.utils.pdf;

import java.awt.Color;

import com.lowagie.text.ExceptionConverter;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPCellEvent;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.RadioCheckField;

class PdfCheckboxCellEvent implements PdfPCellEvent {

    public static final float CHECKBOX_SIZE = 10;

    private final String name;

    public PdfCheckboxCellEvent(final String name) {
        this.name = name;
    }

    @Override
    public void cellLayout(final PdfPCell cell, final Rectangle position, final PdfContentByte[] canvases) {
        final PdfWriter writer = canvases[0].getPdfWriter();
        final float x = position.getLeft();
        final float y = (position.getTop() + position.getBottom()) / 2;
        final Rectangle rect = new Rectangle(x, y - CHECKBOX_SIZE / 2, x + CHECKBOX_SIZE, y + CHECKBOX_SIZE / 2);
        final RadioCheckField checkbox = new RadioCheckField(writer, rect, this.name, "Yes");
        checkbox.setCheckType(RadioCheckField.TYPE_CROSS);
        checkbox.setBackgroundColor(Color.WHITE);
        checkbox.setBorderColor(PDFGenerator.COL_RED);
        try {
            writer.addAnnotation(checkbox.getCheckField());
        } catch (final Exception e) {
            throw new ExceptionConverter(e);
        }
    }
}