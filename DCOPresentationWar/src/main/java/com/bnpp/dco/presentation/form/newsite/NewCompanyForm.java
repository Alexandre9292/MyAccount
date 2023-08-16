package com.bnpp.dco.presentation.form.newsite;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.bnpp.dco.common.constant.Constants;
import com.bnpp.dco.common.dto.AjaxDto;
import com.bnpp.dco.common.dto.CountryDto;
import com.bnpp.dco.common.dto.EntityDto;
import com.bnpp.dco.common.dto.LanguageDto;
import com.bnpp.dco.common.dto.ParamFuncDto;
import com.bnpp.dco.common.dto.RepresentativeDto;
import com.bnpp.dco.common.exception.DCOException;
import com.bnpp.dco.presentation.utils.BusinessHelper;
import com.bnpp.dco.presentation.utils.UserHelper;

@Component("newCompanyForm")
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class NewCompanyForm implements Serializable {

	/** Serial UID */
	private static final long serialVersionUID = -609246808668009183L;
	
	/** Logger (named on purpose after the form). */
    private static final Logger LOG = LoggerFactory.getLogger(NewCompanyForm.class);
	
    /** Generic */
    private List<LanguageDto> prefLangList;
    
	/** ENTITY */
    private EntityDto newEntity;
    
    /** REPRESETATIVE */
    private RepresentativeDto representative;
    private List<RepresentativeDto> representatives;
    private Integer nbRepresentatives;
    
    /** Lists */
    private List<ParamFuncDto> legalStatusList = new ArrayList<ParamFuncDto>();
    private Map<String, List<ParamFuncDto>> mapLegalStatus = new HashMap<String, List<ParamFuncDto>>();
    private List<CountryDto> countries;
    private List<Locale> countryList;
    private List<AjaxDto> countriesSort;
       
    
    /**
     * Initialitation des données du formulaire
     */
    public void init(EntityDto entity) {    

    	newEntity = entity;
    	
        try {
	        // Liste des pays
	        countryList = new ArrayList<Locale>();
	        final Locale[] allLocales = Locale.getAvailableLocales();
	        final List<String> existingLocales = new ArrayList<String>();
	        for (final Locale l : allLocales) {
	            if (!existingLocales.contains(l.getCountry()) && !l.getCountry().equals(Constants.EMPTY_FIELD)) {
	                existingLocales.add(l.getCountry());
	                countryList.add(new Locale("", l.getCountry())); 
	            }
	        }
	        
			// Liste des pays de comptes
			final List<CountryDto> countryDtoList = (List<CountryDto>) BusinessHelper.call(Constants.CONTROLLER_COUNTRY, Constants.CONTROLLER_LIST, null);
			setListCountries(countryDtoList);
			
			// Representatives
			representatives = newEntity.getRepresentativesList();
			if (representatives != null) {
				nbRepresentatives = representatives.size();
			} else {
				nbRepresentatives = 0;
			}        

	        // Liste des statuts
	    	setPrefLangList( (List<LanguageDto>) BusinessHelper.call(Constants.CONTROLLER_LANGAGE,
                    Constants.CONTROLLER_LANG_LIST_INTERFACE, null));
            final List<String> countriesLocaleList = new ArrayList<String>();
            for (CountryDto country : countryDtoList) {
            	countriesLocaleList.add(country.getLocale().getCountry());
            }
            mapLegalStatus = setMapParamFuncDto(Constants.PARAM_TYPE_LEGAL_STATUS, countriesLocaleList);
            setLegalStatusList(mapLegalStatus.get(Locale.FRANCE.getCountry()));
	        /* --------- */
	        
		} catch (final DCOException e) {
			LOG.error("Error while initializing legal countries for account openings");
		}
    }
    
    /**
     * build a map for different type of paramFuncDto
     * @param paramDtoType type of paramFuncDto to map
     * @param list Countries for the map
     * @return map of paramfuncDto
     * @throws DCOException
     */
    private Map<String, List<ParamFuncDto>> setMapParamFuncDto(final int paramDtoType, final List<String> list)
            throws DCOException {
        final Map<String, List<ParamFuncDto>> mappingPFDT = new HashMap<String, List<ParamFuncDto>>();
        if (getPrefLangList() != null && !getPrefLangList().isEmpty()) {
            int mapCount = 0;
            for (final LanguageDto lang : getPrefLangList()) {
                Map<String, List<ParamFuncDto>> mappingPFDTTemp = (Map<String, List<ParamFuncDto>>) BusinessHelper
                        .call(Constants.CONTROLLER_PARAM, Constants.CONTROLLER_PARAM_LOAD_PARAMS_MAP_LANGUAGE,
                                new Object[] {paramDtoType, list, lang.getId()});

                if (mappingPFDTTemp.entrySet() != null) {
                    mapCount++;
                    for (final Entry<String, List<ParamFuncDto>> entry : mappingPFDTTemp.entrySet()) {
                        if (mapCount == 1) {
                            mappingPFDT.put(entry.getKey(), entry.getValue());
                        } else {
                            boolean found = false;
                            for (final Entry<String, List<ParamFuncDto>> entrySaved : mappingPFDT.entrySet()) {
                                final List<ParamFuncDto> paramsList = new ArrayList<ParamFuncDto>();
                                int index = 0;
                                for (final ParamFuncDto param : entrySaved.getValue()) {
                                    if (index < entry.getValue().size()
                                            && entry.getKey().equals(entrySaved.getKey())) {
                                        param.setValue(entry.getValue().get(index).getValue());
                                        paramsList.add(param);
                                        index++;
                                        found = true;
                                    }
                                }
                                if (found) {
                                    entrySaved.setValue(paramsList);
                                    break;
                                }
                            }
                        }
                    }
                }
                mappingPFDTTemp = null;
                
                
                if (UserHelper.getUserInSession().getPreferences() != null && lang.getId().compareTo(UserHelper.getUserInSession().getPreferences().getLanguageId()) == 0) {
                    break;
                } else if (UserHelper.getUserInSession().getPreferences() == null && lang.getId().compareTo(6) == 0) {
                	// Default english for first connexions
                	break;
                }
            }
        }
        return mappingPFDT;
    }
    
    /** 
	 * set list of contries
	 */
	public void setListCountries(final List<CountryDto> countries) {
        if (countries != null) {
            final List<AjaxDto> ajaxList = new ArrayList<AjaxDto>();
            final Locale locale;
            if (UserHelper.getUserInSession() != null && UserHelper.getUserInSession().getPreferences() != null
                    && UserHelper.getUserInSession().getPreferences().getLocale() != null) {
                locale = UserHelper.getUserInSession().getPreferences().getLocale();
            } else {
                locale = new Locale("en");
            }
            for (final CountryDto c : countries) {
                final AjaxDto a = new AjaxDto(c.getLocale().getDisplayCountry(locale), c.getLocale().getCountry());
                ajaxList.add(a);
            }
            Collections.sort(ajaxList, new AjaxDto.OrderByLabel());
            setCountriesSort(ajaxList);
        }
        this.countries = countries;
    }
    

	/**
	 * @return the newEntity
	 */
	public EntityDto getNewEntity() {
		return newEntity;
	}

	/**
	 * @param newEntity the newEntity to set
	 */
	public void setNewEntity(EntityDto newEntity) {
		this.newEntity = newEntity;
	}

	/**
	 * @return the legalStatusList
	 */
	public List<ParamFuncDto> getLegalStatusList() {
		return legalStatusList;
	}

	/**
	 * @param legalStatusList the legalStatusList to set
	 */
	public void setLegalStatusList(List<ParamFuncDto> legalStatusList) {
		this.legalStatusList = legalStatusList;
	}

	/**
	 * @return the countryList
	 */
	public List<Locale> getCountryList() {
		return countryList;
	}

	/**
	 * @param countryList the countryList to set
	 */
	public void setCountryList(List<Locale> countryList) {
		this.countryList = countryList;
	}

	/**
	 * @return the countriesSort
	 */
	public List<AjaxDto> getCountriesSort() {
		return countriesSort;
	}

	/**
	 * @param countriesSort the countriesSort to set
	 */
	public void setCountriesSort(List<AjaxDto> countriesSort) {
		this.countriesSort = countriesSort;
	}

	/**
	 * @return the countries
	 */
	public List<CountryDto> getCountries() {
		return countries;
	}

	/**
	 * @param countries the countries to set
	 */
	public void setCountries(List<CountryDto> countries) {
		this.countries = countries;
	}

	/**
	 * @return the prefLangList
	 */
	public List<LanguageDto> getPrefLangList() {
		return prefLangList;
	}

	/**
	 * @param prefLangList the prefLangList to set
	 */
	public void setPrefLangList(List<LanguageDto> prefLangList) {
		this.prefLangList = prefLangList;
	}

	/**
	 * @return the representative
	 */
	public RepresentativeDto getRepresentative() {
		return representative;
	}

	/**
	 * @param representative the representative to set
	 */
	public void setRepresentative(RepresentativeDto representative) {
		this.representative = representative;
	}

	/**
	 * @return the representatives
	 */
	public List<RepresentativeDto> getRepresentatives() {
		return representatives;
	}

	/**
	 * @param representatives the representatives to set
	 */
	public void setRepresentatives(List<RepresentativeDto> representatives) {
		this.representatives = representatives;
	}

	/**
	 * @return the nbRepresentatives
	 */
	public Integer getNbRepresentatives() {
		return nbRepresentatives;
	}

	/**
	 * @param nbRepresentatives the nbRepresentatives to set
	 */
	public void setNbRepresentatives(Integer nbRepresentatives) {
		this.nbRepresentatives = nbRepresentatives;
	}

	/**
	 * @return the mapLegalStatus
	 */
	public Map<String, List<ParamFuncDto>> getMapLegalStatus() {
		return mapLegalStatus;
	}

	/**
	 * @param mapLegalStatus the mapLegalStatus to set
	 */
	public void setMapLegalStatus(Map<String, List<ParamFuncDto>> mapLegalStatus) {
		this.mapLegalStatus = mapLegalStatus;
	}
}
