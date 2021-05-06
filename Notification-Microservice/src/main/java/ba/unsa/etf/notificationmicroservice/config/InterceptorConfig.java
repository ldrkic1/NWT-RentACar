package ba.unsa.etf.notificationmicroservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class InterceptorConfig extends WebMvcConfigurerAdapter {
    @Bean
    public NotificationServiceInterceptor notificationServiceInterceptor(){
        return new NotificationServiceInterceptor();
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(notificationServiceInterceptor()).addPathPatterns("/**");
    }
}