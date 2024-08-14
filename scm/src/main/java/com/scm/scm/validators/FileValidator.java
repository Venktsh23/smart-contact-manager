package com.scm.scm.validators;


import org.springframework.web.multipart.MultipartFile;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public class FileValidator implements ConstraintValidator<ValidFile, MultipartFile> {

    private static final long MAX_FILE_SIZE = 1024 * 1024 * 2; // 2MB

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {

        if (file == null || file.isEmpty()) {


            return true;

        }

        // file size

        System.out.println("file size: " + file.getSize());

        if (file.getSize() > MAX_FILE_SIZE) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("File size should be less than 2MB").addConstraintViolation();
            return false;
        }


//        String contentType = file.getContentType();
//        if (!"image/jpeg".equals(contentType) && !"image/png".equals(contentType)) {
//            context.disableDefaultConstraintViolation();
//            context.buildConstraintViolationWithTemplate("Only JPEG and PNG files are allowed!!").addConstraintViolation();
//            return false;
//        }


        return true;
    }

}