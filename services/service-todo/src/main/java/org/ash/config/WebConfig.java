package org.ash.config;

import org.ash.interceptors.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
//    private final LoginInterceptor loginInterceptor;
//    @Autowired
//    public WebConfig(LoginInterceptor loginInterceptor) {
//        this.loginInterceptor = loginInterceptor;
//    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS");
    }

//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        //登录和注册接口不拦截
//        registry.addInterceptor(loginInterceptor).excludePathPatterns("/user/login","/user/register","/doc.html","/webjars/**","/v3/**","/upload");
//    }
}

