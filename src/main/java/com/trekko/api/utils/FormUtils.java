package com.trekko.api.utils;

import java.io.IOException;

import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;

/**
 * Utility class for form-related functionalities.
 */
public final class FormUtils {
    private static final String FORM_TEMPLATE_PATH = "json/form_template.json";

    private FormUtils() {
    }

    /**
     * Loads the form template JSON from the resources directory located in
     * classpath.
     *
     * @return The form template JSON as a string.
     * @throws IOException If the template file cannot be read.
     */
    public static String loadFormTemplate() throws IOException {
        return new String(FileCopyUtils.copyToByteArray(new ClassPathResource(FORM_TEMPLATE_PATH).getInputStream()));
    }
}
