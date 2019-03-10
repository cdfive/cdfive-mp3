package com.cdfive.mp3.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author cdfive
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
//        registry.addResourceHandler("/2017/mp3/**").addResourceLocations("file:E:/mp3/");
        registry.addResourceHandler("/2017/mp3/**").addResourceLocations("/opt/mp3/");

        super.addResourceHandlers(registry);
    }
}