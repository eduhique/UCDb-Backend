package psoft.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import psoft.backend.model.TokenFilter;

@SpringBootApplication
public class BackendApplication {

    @Bean
    public FilterRegistrationBean corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration().applyPermitDefaultValues();
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
        bean.setOrder(0);
        return bean;
    }

    @Bean
    public FilterRegistrationBean filterJwt(){
        FilterRegistrationBean filterRb = new FilterRegistrationBean();
        filterRb.setFilter(new TokenFilter());
        filterRb.addUrlPatterns("/v1/users/all");
        filterRb.addUrlPatterns("/v1/users/getuser");
        filterRb.addUrlPatterns("/v1/perfil/disciplina");
        filterRb.addUrlPatterns("/v1/perfil/disciplina/id");
        filterRb.addUrlPatterns("/v1/perfil/");
        filterRb.addUrlPatterns("/v1/perfil/all");
        filterRb.addUrlPatterns("/v1/perfil/comentario");
        filterRb.addUrlPatterns("/v1/perfil/comentario/resposta");
        filterRb.addUrlPatterns("/v1/perfil/comentario/all");
        filterRb.addUrlPatterns("/v1/perfil/like");
        filterRb.addUrlPatterns("/v1/perfil/ranking/like");
        filterRb.addUrlPatterns("/v1/perfil/ranking/comentario");
        return filterRb;
    }

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

}
