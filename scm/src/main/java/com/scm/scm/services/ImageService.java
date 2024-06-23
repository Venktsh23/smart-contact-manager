package com.scm.scm.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {

  public  String uploadService(MultipartFile image);

  public String getUrlFromPublicId(String publicId);

}
