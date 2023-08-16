package com.bnpp.dco.business.controller;

import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;
import javax.persistence.Query;

import com.bnpp.dco.business.constant.DatabaseConstants;
import com.bnpp.dco.business.dto.ThirdParty;
import com.bnpp.dco.business.dto.converter.ConverterThirdParty;
import com.bnpp.dco.business.utils.PropertiesHelper;
import com.bnpp.dco.common.dto.ThirdPartyDto;
import com.bnpp.dco.common.exception.DCOException;

public class ThirdPartyController extends GenericController {

    public List<ThirdPartyDto> getThirdPartyByUserLogin(final String login) throws DCOException {

        final List<ThirdPartyDto> thirdParty = new ArrayList<ThirdPartyDto>();

        final Query q = getEntityManager().createQuery(
                PropertiesHelper.getMessage(DatabaseConstants.THIRD_PARTY_LIST));
        q.setParameter("login", login);

        final List<?> listResult = q.getResultList();

        for (final Object object : listResult) {
            if (object instanceof ThirdParty) {
                final ThirdParty acc = (ThirdParty) object;
                thirdParty.add(ConverterThirdParty.convertDbToDto(acc, true, true));
            }
        }

        return thirdParty;
    }

    public ThirdPartyDto getThirdParty(final Integer id) throws DCOException {

        ThirdPartyDto thirdParty = new ThirdPartyDto();
        final Query q = getEntityManager().createQuery(
                PropertiesHelper.getMessage(DatabaseConstants.THIRD_PARTY_LIST_ID));
        q.setParameter("id", id);
        final List<?> listResult = q.getResultList();
        if (listResult.get(0) != null && listResult.get(0) instanceof ThirdParty) {
            thirdParty = ConverterThirdParty.convertDbToDto((ThirdParty) listResult.get(0), true, true);
        }

        return thirdParty;
    }

    public ThirdPartyDto saveThirdParty(final ThirdPartyDto thirdPartyDto) throws DCOException, NamingException {
        ThirdParty thirdParty = null;

        if (thirdPartyDto != null) {

            thirdParty = ConverterThirdParty.convertDtoToDb(thirdPartyDto, true, true);

            if (thirdParty.getAddressByIdHomeAdress() != null) {
                getEntityManager().merge(thirdParty.getAddressByIdHomeAdress());
            }

            getEntityManager().merge(thirdParty);
        }

        return ConverterThirdParty.convertDbToDto(thirdParty, true, true);
    }

    public void deleteThirdParty(final Integer id) {
        getEntityManager().remove(getEntityManager().find(ThirdParty.class, id));
    }
}
