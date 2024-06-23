package com.scm.scm.services.impl;

import com.scm.scm.entities.Contact;
import com.scm.scm.entities.User;
import com.scm.scm.helper.ResourceNotFoundException;
import com.scm.scm.repository.ContactRepository;
import com.scm.scm.services.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
public class ContactServiceImple implements ContactService {


    @Autowired
    private ContactRepository contactRepository;
    @Override
    public Contact save(Contact contact) {
        contact.setId(UUID.randomUUID().toString());
        return contactRepository.save(contact);
    }

    @Override
    public Contact update(Contact contact) {
        Contact contact1 =new Contact();
        contact1.setId(contact.getId());

        contact1.setName(contact.getName());
        contact1.setEmail(contact.getEmail());
        contact1.setAddress(contact.getAddress());
        contact1.setDescription(contact.getDescription());
        contact1.setPhoneNumber(contact.getPhoneNumber());
        contact1.setWebsiteLink(contact.getWebsiteLink());
        contact1.setLinkedInLink(contact.getLinkedInLink());
        contact1.setUser(contact.getUser());
        return contact1;
    }

    @Override
    public List<Contact> getAll() {
        return contactRepository.findAll();
    }

    @Override
    public Contact getById(String id) {
        return contactRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Not found!!"));
    }

    @Override
    public void delete(String id) {
        Contact contact=contactRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Not Found"));
        contactRepository.delete(contact);

    }

    @Override
    public Page<Contact> searchByName(String name,int size,int page,String sortBy,String order) {
        Sort sort=order.equals("desc")?Sort.by(sortBy).descending() :Sort.by(sortBy).ascending();
        var pageable=  PageRequest.of(page,size);

        return contactRepository.findByNameContaining(name,pageable);
    }

    @Override
    public Page<Contact> searchByEmail(String email,int page,int size,String sortBy,String order) {
        Sort sort=order.equals("desc")?Sort.by(sortBy).descending() :Sort.by(sortBy).ascending();
        var pageable=  PageRequest.of(page,size);
        return  contactRepository.findByEmailContaining(email,pageable);
    }

    @Override
    public Page<Contact> searchByPhoneNumber(String phoneNumber,int page,int size,String sortBy,String order) {
        Sort sort=order.equals("desc")?Sort.by(sortBy).descending() :Sort.by(sortBy).ascending();
        var pageable=  PageRequest.of(page,size);
        return  contactRepository.findByPhoneNumberContaining(phoneNumber,pageable);
    }

    @Override
    public List<Contact> getByUserId(String userId) {

        return contactRepository.findByUserId(userId);
    }

//

    @Override
    public Page<Contact> getByUser(User user, int page,int size,String sortBy,String direction) {
        Sort sort=direction.equals("desc")?Sort.by(sortBy).descending() :Sort.by(sortBy).ascending();
        var pageable=  PageRequest.of(page,size);

        return  contactRepository.findByUser(user,pageable);
    }


//    @Override
//    public List<Contact> getByUser(User user) {
////        Sort sort=direction.equals("desc")?Sort.by(sortBy).descending() :Sort.by(sortBy).ascending();
////        var pageable=  PageRequest.of(page,size);
//
//        return contactRepository.findByUser(user);
//    }
}
