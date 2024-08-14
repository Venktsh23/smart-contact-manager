package com.scm.scm.forms;

import com.scm.scm.validators.ValidFile;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.*;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;


@Builder
@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContactForm {
    @Id
    private String id;
    @NotBlank(message = "username is required")
    @Size(min = 3, message = "Min 3 characters is required")
    private String name;
    @Email(message = "Invalid Email Address")
    private String email;
    @Size(min = 10,max=10, message = "Invalid mobile number")
    private String phoneNumber;
    @NotBlank(message = "adress is required")
    private String address;
//    @NotBlank(message = "description is required")
    private String description;
    private boolean favorite=false;
    private String websiteLink;
    private String linkedInLink;


    @ToString.Exclude
    @ValidFile(message = "Please upload a valid image file under 2MB")
    private MultipartFile image;
}
