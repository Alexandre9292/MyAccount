package com.bnpp.dco.common.utils.cvs;

public class DocumentCSV {

    @CSVColumnHeader(name = "")
    private String nameColumn1Str;

    @CSVColumnHeader(name = "")
    private String nameColumn2Str;

    @CSVColumnHeader(name = "")
    private String nameColumn3Str;

    @CSVColumnHeader(name = "")
    private String nameColumn4Str;

    @CSVColumnHeader(name = "")
    private String nameColumn2Int;

    public String getNameColumn1Str() {
        return this.nameColumn1Str;
    }

    public void setNameColumn1Str(final String nameColumn1Str) {
        this.nameColumn1Str = nameColumn1Str;
    }

    public String getNameColumn2Str() {
        return this.nameColumn2Str;
    }

    public void setNameColumn2Str(final String nameColumn2Str) {
        this.nameColumn2Str = nameColumn2Str;
    }

    public String getNameColumn3Str() {
        return this.nameColumn3Str;
    }

    public void setNameColumn3Str(final String nameColumn3Str) {
        this.nameColumn3Str = nameColumn3Str;
    }

    public String getNameColumn4Str() {
        return this.nameColumn4Str;
    }

    public void setNameColumn4Str(final String nameColumn4Str) {
        this.nameColumn4Str = nameColumn4Str;
    }

    public String getNameColumn2Int() {
        return this.nameColumn2Int;
    }

    public void setNameColumn2Int(final String nameColumn2Int) {
        this.nameColumn2Int = nameColumn2Int;
    }

}
