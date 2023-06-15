package com.favorite.recipes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class FavoriteRecipeApplication {

	public static void main(String[] args) {
		SpringApplication.run(FavoriteRecipeApplication.class, args);
	}

}
