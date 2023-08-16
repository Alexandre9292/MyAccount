package com.bnpp.dco.presentation.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bnpp.dco.common.constant.Constants;
import com.bnpp.dco.common.dto.UserDto;
import com.bnpp.dco.common.exception.DCOException;
import com.bnpp.dco.presentation.bean.UserSession;
import com.bnpp.dco.presentation.utils.BusinessHelper;

@Service("userDetailsService")
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    /** Logger. */
    private final Logger LOG = LoggerFactory.getLogger(UserDetailsService.class);

    @Override
    public UserDetails loadUserByUsername(final String login) throws UsernameNotFoundException {
        UserSession result = null;
        try {
            final UserDto user = (UserDto) BusinessHelper.call(Constants.CONTROLLER_USER,
                    Constants.CONTROLLER_USER_GET_BY_LOGIN, new Object[] {login});
            result = new UserSession(user);
        } catch (final DCOException e) {
            final String message = new StringBuilder("The user \"").append(login)
                    .append("\" unsuccessfully tried to connect").toString();
            this.LOG.error(message);
            throw new UsernameNotFoundException(message, e);
        }
        return result;
    }
}
