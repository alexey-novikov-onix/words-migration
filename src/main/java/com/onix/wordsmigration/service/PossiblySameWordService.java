package com.onix.wordsmigration.service;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public final class PossiblySameWordService {

    public boolean isSame(final String search, final List<String> words) {
        for (String word : words) {
            String s = search.toLowerCase();
            final String[] parts = word.toLowerCase().split("\\d");

            for (String part : parts) {
                s = s.replaceFirst(part, "");
            }

            if (s.length() < 3) {
                return true;
            }
        }

        return false;
    }

}
