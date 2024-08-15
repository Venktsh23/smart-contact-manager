package com.scm.scm.helper;

import com.scm.scm.config.OAuthAuthenticationSuccessHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.security.Principal;

public class Helper {

    public static String getEmailOfLoggedInUser(Authentication authentication) {
        Logger logger = LoggerFactory.getLogger(Helper.class);
//        DefaultOAuth2User user =(DefaultOAuth2User)authentication.getPrincipal();
//        AuthenticationPrincipal principal = (AuthenticationPrincipal) authentication.getPrincipal();
        if (authentication instanceof OAuth2AuthenticationToken) {
            OAuth2AuthenticationToken authToken = (OAuth2AuthenticationToken) authentication;
            String clientId = authToken.getAuthorizedClientRegistrationId();

            OAuth2User oauth2User = (OAuth2User) authToken.getPrincipal();
            String username = "";
            //if google
            if (clientId.equalsIgnoreCase("google")) {
                String email = oauth2User.getAttribute("email");
//                logger.info(email);

                return email;
            }

        } else {
            return authentication.getName();
        }
        return "";
    }
}
