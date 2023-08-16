package com.bnpp.dco.common.dto;

import java.util.List;

public class LegalEntityDto implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    private int id;
    private String label;
    private List<CountryDto> countries;
    private AddressDto address;

    public LegalEntityDto() {
    }

    public LegalEntityDto(final int id) {
        super();
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public String getLabel() {
        return this.label;
    }

    public void setLabel(final String label) {
        this.label = label;
    }

    public List<CountryDto> getCountries() {
        return this.countries;
    }

    public void setCountries(final List<CountryDto> countries) {
        this.countries = countries;
    }

    /**
     * @return the address
     */
    public AddressDto getAddress() {
        return this.address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(final AddressDto address) {
        this.address = address;
    }
}
