package com.trekko.api.utils;

import java.io.IOException;

import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;

public final class FormUtils {
    private static final String FORM_TEMPLATE_PATH = "json/form_template.json";

    private FormUtils() {
    }

    public static String loadFormTemplate() throws IOException {
        return new String(FileCopyUtils.copyToByteArray(new ClassPathResource(FORM_TEMPLATE_PATH).getInputStream()));
    }
}
