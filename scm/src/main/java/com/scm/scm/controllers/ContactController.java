package com.scm.scm.controllers;

import com.scm.scm.entities.Contact;
import com.scm.scm.entities.User;
import com.scm.scm.forms.ContactForm;
import com.scm.scm.helper.Helper;
import com.scm.scm.services.ContactService;
import com.scm.scm.services.ImageService;
import com.scm.scm.services.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/user/contacts")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @Autowired
    private UserService userService;

    @Autowired
    private ImageService imageService;

    @RequestMapping("/add")
    public String addContactView(){
        return "/user/add_contact";
    }

    @RequestMapping(value="/save-contact",method = RequestMethod.POST)
    public String addContactView(@Valid @ModelAttribute("contactForm") ContactForm contactForm, Model model, Authentication authentication, BindingResult bindingResult ){
        String fileURL= imageService.uploadService(contactForm.getImage());
        Contact contact = new Contact();
        String username= Helper.getEmailOfLoggedInUser(authentication);
        //get user by email of authentication
        User user= userService.getUserByEmail(username);

        contact.setName(contactForm.getName());
        contact.setEmail(contactForm.getEmail());
        contact.setUser(user);
        contact.setAddress(contactForm.getAddress());
        contact.setDescription(contactForm.getDescription());
        contact.setPhoneNumber(contactForm.getPhoneNumber());
        contact.setWebsiteLink(contactForm.getWebsiteLink());
        contact.setLinkedInLink(contactForm.getLinkedInLink());
        contact.setPicture(fileURL);


        contactService.save(contact);
        System.out.println("contact has been saved");
        System.out.println(contact);

        System.out.println(contactForm);
        return "/user/add_contact";
    }

    @RequestMapping()
    public String contactView(
            @RequestParam(value = "page",defaultValue = "0") int page,
            @RequestParam(value = "size",defaultValue = "1") int size,
            @RequestParam(value = "sortBy",defaultValue = "name") String sortBy,
            @RequestParam(value = "direction",defaultValue = "asc") String direction,
            Model model,Authentication authentication){
        String userEmail=Helper.getEmailOfLoggedInUser(authentication);
        User user=userService.getUserByEmail(userEmail);
        Page<Contact> contacts=contactService.getByUser(user,page,size,sortBy,direction);
//         List<Contact> contacts=contactService.getByUser(user);


        model.addAttribute("contacts",contacts);

        return "/user/contacts";

    }
    @RequestMapping("/search")
    public String search(
            @RequestParam(value = "field") String field,
            @RequestParam(value = "keyword",defaultValue = "") String keyword,
            @RequestParam(value = "page",defaultValue = "0") int page,
            @RequestParam(value = "size",defaultValue = "1") int size,
            @RequestParam(value = "sortBy",defaultValue = "name") String sortBy,
            @RequestParam(value = "direction",defaultValue = "asc") String order,
            Model model,Authentication authentication
    ){
        Page<Contact> contacts;

        if(keyword.isEmpty()){
            return "/user/contacts";
        }

        if(field.equalsIgnoreCase("name")){
          contacts= contactService.searchByName(keyword,page,size,sortBy,order);
        } else if (field.equalsIgnoreCase("email")) {
          contacts= contactService.searchByEmail(keyword,page,size,sortBy,order);
        } else if (field.equalsIgnoreCase("phone")) {
           contacts= contactService.searchByPhoneNumber(keyword,page,size,sortBy,order);
        }else{
            return "/user/contacts";
        }


        model.addAttribute("contacts" ,contacts)  ;
        return "/user/search";

    }


}
