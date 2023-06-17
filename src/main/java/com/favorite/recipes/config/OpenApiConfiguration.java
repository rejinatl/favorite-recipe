package com.favorite.recipes.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@OpenAPIDefinition(info = @Info(title = "Favorite Recipes", version = "v1.0", description = "Manage Recipe", license = @License(name = "License", url = "javascript:void(0)"), contact = @Contact(url = "javascript:void(0)", name = "Favorite Recipes", email = "contact@favoriterecipes.com")))
@Configuration
public class OpenApiConfiguration {

}
