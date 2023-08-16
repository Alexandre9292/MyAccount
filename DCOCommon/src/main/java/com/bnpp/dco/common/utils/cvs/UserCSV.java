package com.bnpp.dco.common.utils.cvs;

public class UserCSV {

    @CSVColumnHeader(name = "")
    private String nameColumn1Str;

    @CSVColumnHeader(name = "")
    private String nameColumn2Int;

    private String anotherFieldToNotExport;

    public UserCSV() {
    }

    public String getanotherFieldToNotExport() {
        return this.anotherFieldToNotExport;
    }

    public String getNameColumn1Str() {
        return this.nameColumn1Str;
    }

    public void setNameColumn1Str(final String nameColumn1Str) {
        this.nameColumn1Str = nameColumn1Str;
    }

    public String getNameColumn2Int() {
        return this.nameColumn2Int;
    }

    public void setNameColumn2Int(final String nameColumn2Int) {
        this.nameColumn2Int = nameColumn2Int;
    }

    public void setanotherFieldToNotExport(final String anotherFieldToNotExport) {
        this.anotherFieldToNotExport = anotherFieldToNotExport;
    }

}
