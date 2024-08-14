package com.scm.scm.config;

import com.scm.scm.services.impl.SecurityCustomUserDetailsService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;

@Configuration
public class SecurityConfig {

//
//    private InMemoryUserDetailsManager inMemoryUserDetailsManager;
//
//    @Bean
//    public UserDetailsService userDetailsService(){
////
////        UserDetails  userDetails = User
////                .withUsername("admin")
////                .password("admin123")
////                .roles("ADMIN","USER")
////                .build();
//
//        UserDetails  userDetails = User
//                .withDefaultPasswordEncoder()
//                .username("admin")
//                .password("admin123")
//                .roles("ADMIN","USER")
//                .build();
//        var inMemoryUserDetailsManager = new InMemoryUserDetailsManager(userDetails);
//        return inMemoryUserDetailsManager;
//    }

    @Autowired
    private SecurityCustomUserDetailsService userDetailsService;

    @Autowired
    private OAuthAuthenticationSuccessHandler oAuthAuthenticationSuccessHandler;


//    AuthenticationProvider spring security
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return  authenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.authorizeHttpRequests(authorize->{
            authorize.requestMatchers("user/**").authenticated();
            authorize.anyRequest().permitAll();
                });

        httpSecurity.formLogin(formlogin ->{
            formlogin.loginPage("/login");
                   formlogin.loginProcessingUrl("/authenticate");
                   formlogin.successForwardUrl("/user/dashboard");
//                   formlogin.failureForwardUrl("/login?error=true");
                   formlogin.usernameParameter("email");
                   formlogin.passwordParameter("password");
                   formlogin.failureHandler(new AuthenticationFailureHandler() {
                       @Override
                       public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
                            new DefaultRedirectStrategy().sendRedirect(request,response,"/login");
                       }
                   });
        });
        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        httpSecurity.logout(formlogout->
        {
            formlogout.logoutUrl("/logout");
            formlogout.logoutSuccessUrl("/login?logout=true");
        });

        httpSecurity.oauth2Login(oauth2->{
                    oauth2.loginPage("/login");
                    oauth2.successHandler(oAuthAuthenticationSuccessHandler);
                });
        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){

        return  new BCryptPasswordEncoder();
    }


}
