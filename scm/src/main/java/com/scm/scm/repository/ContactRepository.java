package com.scm.scm.repository;

import com.scm.scm.entities.Contact;
import com.scm.scm.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<Contact,String> {
    Page<Contact> findByUser(User user, Pageable pageable);
//         List<Contact> findByUser(User user);


    @Query("SELECT c FROM Contact c WHERE c.user.id = :userId")
    List<Contact> findByUserId(@Param("userId") String userId);

    Page<Contact> findByNameContaining(String name,Pageable pageable);

    Page<Contact> findByEmailContaining(String email,Pageable pageable);

    Page<Contact> findByPhoneNumberContaining(String phone,Pageable pageable);



}
