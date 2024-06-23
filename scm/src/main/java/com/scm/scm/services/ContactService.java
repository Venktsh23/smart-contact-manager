package com.scm.scm.services;

import com.scm.scm.entities.Contact;
import com.scm.scm.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ContactService {

    Contact save(Contact contact);

    Contact update(Contact contact);

    List<Contact> getAll();

    Contact getById(String id);

    void delete(String id);

    Page<Contact> searchByName(String name,int page,int size,String sortBy,String order);

    Page<Contact> searchByEmail( String email,int page,int size,String sortBy,String order);

    Page<Contact> searchByPhoneNumber(String phoneNumber,int page,int size,String sortBy,String order);


    List<Contact> getByUserId(String userId);

    Page<Contact> getByUser(User user,  int page,int size,String sortBy,String direction);
//     List<Contact> getByUser(User user);





}

