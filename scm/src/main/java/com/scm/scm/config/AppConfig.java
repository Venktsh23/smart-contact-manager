package com.scm.scm.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class AppConfig {

    @Bean
    public Cloudinary cloudinary(){
        return  new Cloudinary(
               ObjectUtils.asMap(
                       "cloud_name","CLOUD_NAME",
                       "api_key","USER_API_KEY",
                       "api_secret","USER_SECRET_KEY"
               ));


    }
//     System.out.println(
//             cloudinary.uploader().upload("https://cloudinary-devs.github.io/cld-docs-assets/assets/images/coffee_cup.jpg", params1));
//        );

}
