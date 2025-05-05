package com.example.s30019tpo08.controller;

import com.example.s30019tpo08.model.CodeFormatterService;
import com.example.s30019tpo08.model.CodeSubmission;
import com.example.s30019tpo08.model.FormattedCode;
import com.example.s30019tpo08.model.StorageService;
import com.google.googlejavaformat.java.FormatterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
public class TextController {

    @Autowired
    private CodeFormatterService formatterService;

    @Autowired
    private StorageService storageService;


    @GetMapping("/")
    public String showForm(Model model) {
        model.addAttribute("submission", new CodeSubmission());
        return "text";
    }

    @PostMapping("/format")
    public String formatCode(@ModelAttribute CodeSubmission submission, Model model) {
        try {
            String formatted = formatterService.format(submission.getCode());

            FormattedCode data = new FormattedCode(
                    submission.getCode(),
                    formatted,
                    submission.getDurationInSeconds()
            );
            storageService.save(submission.getTextId(), data);

            model.addAttribute("originalCode", submission.getCode());
            model.addAttribute("formattedCode", formatted);
            model.addAttribute("textId", submission.getTextId());
            model.addAttribute("duration", submission.getDurationInSeconds());

            return "formatted";
        } catch (FormatterException | IOException e) {
            model.addAttribute("error", "Formatting failed: " + e.getMessage());
            return "error";
        }
    }

    @GetMapping("/retrieve/{id}")
    public String retrieveFormattedCode(@PathVariable String id, Model model) {
        try {
            FormattedCode code = storageService.load(id);
            if (code == null) {
                model.addAttribute("error", "Code not found or expired.");
                return "error";
            }

            model.addAttribute("originalCode", code.getOriginalCode());
            model.addAttribute("formattedCode", code.getFormattedCode());
            return "formatted";
        } catch (Exception e) {
            model.addAttribute("error", "Could not retrieve code: " + e.getMessage());
            return "error";
        }
    }


}
