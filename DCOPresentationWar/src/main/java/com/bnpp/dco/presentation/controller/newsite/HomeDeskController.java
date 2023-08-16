package com.bnpp.dco.presentation.controller.newsite;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bnpp.dco.presentation.controller.GenericController;
import com.bnpp.dco.presentation.form.newsite.HomeDeskForm;
import com.bnpp.dco.presentation.utils.PropertiesHelper;
import com.bnpp.dco.presentation.utils.constants.WebConstants;

@Controller
public class HomeDeskController extends GenericController {
	
	private static final Logger LOG = LoggerFactory.getLogger(HomeDeskController.class);
	
	@Autowired
	private PropertiesHelper propertiesHelper;
	
	@Autowired
	private HomeDeskForm homeDeskForm;

	/**
	 * @return the homeDeskForm
	 */
	public HomeDeskForm getHomeDeskForm() {
		return homeDeskForm;
	}
	
	/**
	 * Chargement de la page d'accueil Desk
	 * 
	 * @return the page to forward to
	 */
	@RequestMapping(value = "homeDeskLoad")
	public final String homeDeskLoad() {
		return WebConstants.NEW + WebConstants.HOME_DESK;
	}

}
