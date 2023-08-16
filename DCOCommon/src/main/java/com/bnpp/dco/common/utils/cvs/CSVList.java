package com.bnpp.dco.common.utils.cvs;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class CSVList<E> extends ArrayList<E> implements ICSVList {
    /** Serial UID. */
    private static final long serialVersionUID = 1L;

    @Override
    public String toCSV() throws IllegalArgumentException, IllegalAccessException {

        final List<Field> csvFields = new ArrayList<Field>();
        final StringBuilder csv = new StringBuilder();

        for (int i = 0; i < this.size(); i++) {

            final E element = this.get(i);

            if (i == 0) {
                final Field[] fields = element.getClass().getDeclaredFields();

                for (final Field field : fields) {
                    final CSVColumnHeader csvColumnHeader = field.getAnnotation(CSVColumnHeader.class);
                    if (csvColumnHeader == null) {
                        continue;
                    }
                    csv.append(CSV_SEPARATOR);
                    csvFields.add(field);
                }
                csv.append(CSV_LR);
            }

            for (final Field field : csvFields) {
                field.setAccessible(true);
                csv.append(field.get(element)).append(CSV_SEPARATOR);
            }
            csv.append(CSV_LR);
        }
        return csv.toString();
    }
}
