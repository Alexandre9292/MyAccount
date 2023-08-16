package com.bnpp.dco.common.utils.cvs;

public class EntityUsersCSV {

    @CSVColumnHeader(name = "")
    private String nameColumn1Str;

    @CSVColumnHeader(name = "")
    private String nameColumn2Str;

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

}
