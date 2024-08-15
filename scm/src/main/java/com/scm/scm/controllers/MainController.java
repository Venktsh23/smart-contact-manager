package com.scm.scm.controllers;

import com.scm.scm.entities.User;
import com.scm.scm.helper.Helper;
import com.scm.scm.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class MainController {

    @Autowired
    private UserService userService;

    @ModelAttribute
    public void addLoggedInUserInformation(Model model, Authentication authentication) {
        if (authentication == null) {
            return;
        }
        //get email from authentication by helper method
        String username = Helper.getEmailOfLoggedInUser(authentication);
        //get user by email of authentication
        User user = userService.getUserByEmail(username);
        if (user == null) {
            model.addAttribute("loggedInUser", null);
        } else {
            model.addAttribute("loggedInUser", user);
        }
    }
}
