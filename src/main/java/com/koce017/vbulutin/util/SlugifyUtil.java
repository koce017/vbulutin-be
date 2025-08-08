package com.koce017.vbulutin.util;

import com.github.slugify.Slugify;

import java.util.UUID;

public class SlugifyUtil {

    public static String slugify(String title) {
        return Slugify.builder().lowerCase(true).build()
                .slugify(title) + "-" + UUID.randomUUID().toString().substring(0, 8);
    }

}
