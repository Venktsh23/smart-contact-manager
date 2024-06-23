package com.scm.scm.controllers;

import com.scm.scm.entities.User;
import com.scm.scm.forms.UserForm;
import com.scm.scm.services.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PageController {
    @Autowired
    private UserService userService;


    @RequestMapping("/")
    public String index(){
        return "redirect:/home";
    }

    @RequestMapping("/home")
    public String home(){
        return "home";
    }
    @RequestMapping("/base")
    public String base(){
        return "base";
    }
    @RequestMapping("/contact")
    public String contact(){
        return "contact";
    }

    @RequestMapping("/about")
    public String about(){
        return "about";
   }

    @RequestMapping("/service")
    public String service(){
        return "service";
    }
    @RequestMapping("/login")
    public String login(){
        return "login";
    }
    @RequestMapping("/register")
    public String register(){
        return "register";
    }
    @RequestMapping(value = "/do-register", method = RequestMethod.POST)
    public String processRegister(@Valid @ModelAttribute UserForm userForm, BindingResult bindingResult, HttpSession httpSession){
        //fetch form data
        //user form
        System.out.println(userForm);
        if(bindingResult.hasErrors()){
            return "register";
        }

//        User user1 = User.builder().
//                name(userForm.getName()).
//                email(userForm.getEmail()).
//                password(userForm.getPassword()).
//                about(userForm.getAbout()).
//                phoneNumber(userForm.getPhone()).
//        profilePic("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQv_oL1l60gN7zHc_fMS11OeFR-mLDi3DgjNg&s").
//        build();
        User user2 = new User();
        user2.setName(userForm.getName());
        user2.setEmail(userForm.getEmail());
        user2.setPassword(userForm.getPassword());
        user2.setAbout(userForm.getAbout());
        user2.setPhoneNumber(userForm.getPhone());
        user2.setProfilePic("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQv_oL1l60gN7zHc_fMS11OeFR-mLDi3DgjNg&s");

       User user= userService.saveUser(user2);
        System.out.println(user+" users data");
//        httpSession.setAttribute("message1","registration successfull!!");

        //validate form data
        //validate user form
        //save to database
        //display success message
        //redirect to login page


        return "redirect:/register";
    }
}
