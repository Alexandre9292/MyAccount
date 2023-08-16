package com.bnpp.dco.business.dto.converter;

import com.bnpp.dco.business.dto.Address;
import com.bnpp.dco.common.dto.AddressDto;

public final class ConverterAddress {
    public static AddressDto convertDbToDto(final Address db) {
        AddressDto result = null;
        if (db != null) {
            result = new AddressDto();
            result.setId(db.getId());
            result.setFieldOne(db.getFieldOne());
            result.setFieldTwo(db.getFieldTwo());
            result.setFieldThree(db.getFieldThree());
            result.setFieldFour(db.getFieldFour());
            result.setFieldFive(db.getFieldFive());
            result.setFieldSix(db.getFieldSix());
            result.setFieldSeven(db.getFieldSeven());
        }
        return result;
    }

    public static Address convertDtoToDb(final AddressDto dto) {

        Address address = null;

        if (dto != null) {
            address = new Address();
            address.setId(dto.getId());
            address.setFieldOne(dto.getFieldOne());
            address.setFieldTwo(dto.getFieldTwo());
            address.setFieldThree(dto.getFieldThree());
            address.setFieldFour(dto.getFieldFour());
            address.setFieldFive(dto.getFieldFive());
            address.setFieldSix(dto.getFieldSix());
            address.setFieldSeven(dto.getFieldSeven());
        }

        return address;
    }

    /**
     * Private constructor to protect Utility Class.
     */
    private ConverterAddress() {
    }

}
