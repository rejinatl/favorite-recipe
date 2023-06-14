package com.favorite.recipes.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@OpenAPIDefinition(info = @Info(title = "Favorite Recipes", version = "v1.0", description = "Recipes API", license = @License(name = "Favorite Recipes", url = "https://www.favoriterecipes.com/"), contact = @Contact(url = "https://www.favoriterecipes.com/", name = "Favorite Recipes", email = "hr@favoriterecipes.com")))
@Configuration
public class OpenApiConfiguration {

}
