package application.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/aircraft").setViewName("aircraft");
        registry.addViewController("/airport").setViewName("airport");
        registry.addViewController("/compare").setViewName("compare");
        registry.addViewController("/editAircraft").setViewName("editAircraft");
        registry.addViewController("/editAirport").setViewName("editAirport");
        registry.addViewController("/login").setViewName("login");
    }

}
