package application.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/compare").setViewName("compare");
        registry.addViewController("/editAircraft").setViewName("editAircraft");
        registry.addViewController("/editAirport").setViewName("editAirport");
        registry.addViewController("/editUser").setViewName("editUser");
        registry.addViewController("/login").setViewName("login");
    }

}
