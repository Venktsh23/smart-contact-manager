package com.scm.scm.config;

import com.scm.scm.entities.Providers;
import com.scm.scm.entities.User;
import com.scm.scm.helper.AppConstants;
import com.scm.scm.repository.UserRepo;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Component
public class OAuthAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    UserRepo userRepo;
    Logger logger = LoggerFactory.getLogger(OAuthAuthenticationSuccessHandler.class);

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        logger.info("OauthenticationSuccessHandler");
        DefaultOAuth2User user = (DefaultOAuth2User) authentication.getPrincipal();
//        new DefaultRedirectStrategy().sendRedirect(request,response,"/user/profile");
//        logging
//        logger.info(user.getName());
//        user.getAttributes().forEach((key,value)->{
//            logger.info("{} -> {}",key,value);
//        });
//        logger.info(user.getAuthorities().toString());


        String email = user.getAttribute("email").toString();
        String name = user.getAttribute("name").toString();
        String picture = user.getAttribute("picture").toString();


        User user1 = new User();
        user1.setEmail(email);
        user1.setName(name);
        user1.setProfilePic(picture);
        user1.setPassword("password");
        user1.setUserId(UUID.randomUUID().toString());
        user1.setProvider(Providers.GOOGLE);
        user1.setEnabled(true);
        user1.setEmailVerified(true);
        user1.setProviderUserId(user.getName());
        user1.setRolelist(List.of(AppConstants.ROLE_USER));
        user1.setAbout("This account is created using google...");

        User user2 = userRepo.findByEmail(email).orElse(null);
        if (user2 == null) {
            userRepo.save(user1);
            logger.info("user saved" + email);
        }
        new DefaultRedirectStrategy().sendRedirect(request, response, "/user/profile");

    }
}
