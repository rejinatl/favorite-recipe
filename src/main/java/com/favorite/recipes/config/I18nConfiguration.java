package com.favorite.recipes.config;


import java.util.Locale;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@Configuration
public class I18nConfiguration {
    
    @Bean
    public ResourceBundleMessageSource messageSource() {
      var resourceBundleMessageSource = new ResourceBundleMessageSource();
      resourceBundleMessageSource.setBasenames("messages");
      resourceBundleMessageSource.setUseCodeAsDefaultMessage(true);
      resourceBundleMessageSource.setDefaultLocale(Locale.US);
      resourceBundleMessageSource.setDefaultEncoding("UTF-8");
      return resourceBundleMessageSource;
    }

    @Bean
    public LocaleResolver localeResolver() {
      SessionLocaleResolver sessionLocaleResolver = new SessionLocaleResolver();
      sessionLocaleResolver.setDefaultLocale(Locale.US);
      return sessionLocaleResolver;
    }
}

