package org.ash.demos.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
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
                .allowedOrigins("http://localhost:8080")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowCredentials(true) // 允许携带Cookie
                .maxAge(3600);
    }

//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        //登录和注册接口不拦截
//        registry.addInterceptor(loginInterceptor).excludePathPatterns("/user/login","/user/register","/doc.html","/webjars/**","/v3/**","/upload");
//    }
}

