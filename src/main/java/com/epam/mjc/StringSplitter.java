package com.epam.mjc;

import java.util.*;

public class StringSplitter {
    /**
     * Splits given string applying all delimiters to it. Keeps order of result substrings as in source string.
     *
     * @param source source string
     * @param delimiters collection of delimiter strings
     * @return List of substrings
     */
    public List<String> splitByDelimiters(String source, Collection<String> delimiters) {
        if (delimiters == null || delimiters.isEmpty()) {
            throw new IllegalArgumentException("Delimiters list is empty");
        }

        if (source == null) {
            throw new IllegalArgumentException("Source string not specified");
        }

        List<String> list = new ArrayList<>();
        int fromIndex = 0;
        int nextIndex;
        String nextDelimiter;
        do {
            nextDelimiter = findNextDelimiter(source, fromIndex, delimiters);
            if (nextDelimiter == null) {
                nextIndex = source.length();
            } else {
                nextIndex = source.indexOf(nextDelimiter, fromIndex);
            }
            if (fromIndex != nextIndex) {
                list.add(source.substring(fromIndex, nextIndex));
            }
            if (nextDelimiter != null) {
                fromIndex = nextIndex + nextDelimiter.length();
            }
        } while (nextDelimiter != null);

        return list;
    }

    private String findNextDelimiter(String source, int fromIndex, Collection<String> delimiters) {
        int nextIndex = -1;
        String nextDelimiter = null;
        for (String delimiter: delimiters) {
            int index = source.indexOf(delimiter, fromIndex);
            if (index > -1 && (nextIndex == -1 || index < nextIndex)) {
                nextIndex = index;
                nextDelimiter = delimiter;
            }
        }

        return nextDelimiter;
    }
}
