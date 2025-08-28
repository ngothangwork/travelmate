package dev.thangngo.travelmate.common;

import com.github.slugify.Slugify;

public class SlugUtil {
    private static final Slugify slugify = Slugify.builder()
            .customReplacement("đ", "d")
            .customReplacement("Đ", "D")
            .build();

    public static String toSlug(String input) {
        return slugify.slugify(input);
    }
}
