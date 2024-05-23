package com.example.chatify.models;

import java.util.Arrays;
import java.util.List;

public class Languages {
    private String language_name;

    public Languages(String language_name) {
        this.language_name = language_name;
    }

    public String getName() {
        return language_name;
    }


    public static List<String> getLanguages() {
        return Arrays.asList("English", "Tagalog", "Bisaya", "Korean", "Chinese", "Japanese", "Thai",
                "Vietnamese", "Russian", "Spanish", "Greek", "Swedish", "Malay", "Polish", "German");
    }
}