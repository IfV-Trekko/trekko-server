package com.trekko.api.controllers;

import java.io.IOException;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trekko.api.utils.FormUtils;

@RestController
@RequestMapping("/form")
public class FormController {

    @GetMapping
    public ResponseEntity<?> getForm() throws IOException {
        final String formTemplateJson = FormUtils.loadFormTemplate();
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(formTemplateJson);
    }
}
