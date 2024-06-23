package com.scm.scm.services.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import com.scm.scm.helper.AppConstants;
import com.scm.scm.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    private Cloudinary cloudinary;

    @Override
    public String uploadService(MultipartFile image)  {

        String fileName= UUID.randomUUID().toString();
        try {
            byte[] data=new byte[image.getInputStream().available()];
            image.getInputStream().read(data);
            cloudinary.uploader().upload(
                    data, ObjectUtils.asMap(
                            "public_id",fileName
                    ));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return this.getUrlFromPublicId(fileName);


    }

    @Override
    public String getUrlFromPublicId(String publicId) {
        return cloudinary.url().transformation(
                new Transformation<>()
                        .width(AppConstants.CONTACT_IMAGE_WIDTH)
                        .height(AppConstants.CONTACT_IMAGE_HEIGHT)
                        .crop(AppConstants.CONTACT_IMAGE_CROP)
        ).generate(publicId);
    }
}
