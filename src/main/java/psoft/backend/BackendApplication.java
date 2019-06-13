package psoft.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import psoft.backend.model.TokenFilter;

@SpringBootApplication
public class BackendApplication {

    @Bean
    public FilterRegistrationBean filterJwt(){
        FilterRegistrationBean filterRb = new FilterRegistrationBean();
        filterRb.setFilter(new TokenFilter());
        filterRb.addUrlPatterns("/v1/users/all");
        return filterRb;
    }

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

}
