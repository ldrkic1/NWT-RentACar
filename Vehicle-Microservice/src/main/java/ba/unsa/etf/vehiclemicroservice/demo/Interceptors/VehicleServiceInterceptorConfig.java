package ba.unsa.etf.vehiclemicroservice.demo.Interceptors;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class VehicleServiceInterceptorConfig extends WebMvcConfigurerAdapter {
    @Bean
    public VehicleServiceInterceptor vehicleServiceInterceptor(){
        return new VehicleServiceInterceptor();
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(vehicleServiceInterceptor()).addPathPatterns("/**");
    }
}
