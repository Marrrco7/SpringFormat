package com.example.s30019tpo08.model;

import com.google.googlejavaformat.java.Formatter;
import com.google.googlejavaformat.java.FormatterException;
import org.springframework.stereotype.Service;

@Service
public class CodeFormatterService {

    public String format(String input) throws FormatterException {
        return new Formatter().formatSource(input);
    }
}
