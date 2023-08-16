package com.bnpp.dco.presentation.controller.newsite;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bnpp.dco.common.constant.Constants;
import com.bnpp.dco.common.dto.AddressDto;
import com.bnpp.dco.common.dto.EntityDto;
import com.bnpp.dco.common.dto.RepresentativeDto;
import com.bnpp.dco.common.exception.DCOException;
import com.bnpp.dco.common.utils.LocaleUtil;
import com.bnpp.dco.presentation.controller.GenericController;
import com.bnpp.dco.presentation.form.newsite.NewCompanyForm;
import com.bnpp.dco.presentation.utils.BusinessHelper;
import com.bnpp.dco.presentation.utils.PropertiesHelper;
import com.bnpp.dco.presentation.utils.constants.WebConstants;

@Controller
public class NewCompanyController extends GenericController {
	
	private static final Logger LOG = LoggerFactory.getLogger(NewCompanyController.class);
	
	@Autowired
    private PropertiesHelper propertiesHelper;
	
	@Autowired
    private NewCompanyForm newCompanyForm;
    
    ///////////////////////////////////////////////////////////////////////////////////////////////
    
    ///////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * @return the newCompanyForm
	 */
    @ModelAttribute("newCompanyForm")
	public NewCompanyForm getNewCompanyForm() {
		return newCompanyForm;
	}
}
