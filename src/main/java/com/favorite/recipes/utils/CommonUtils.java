package com.favorite.recipes.utils;

import java.util.UUID;

public class CommonUtils {
    

    public static final String generateUUID() throws Exception {
        
          return UUID.randomUUID().toString();
    }
}
