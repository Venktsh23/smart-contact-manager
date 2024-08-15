package com.scm.scm.controllers;

import com.scm.scm.entities.User;
import com.scm.scm.helper.Helper;
import com.scm.scm.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    //common method to get logged in user
    @RequestMapping(value = "/dashboard")
    public String dashboard() {
        return "user/dashboard";
    }

    @RequestMapping(value = "/profile")
    public String profile(Model model, Authentication authentication) {

        return "user/profile";
    }

}
