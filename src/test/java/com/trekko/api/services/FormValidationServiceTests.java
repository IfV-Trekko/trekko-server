package com.trekko.api.services;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FormValidationServiceTests {

        private FormValidationService formValidationService;

        @BeforeEach
        public void setUp() {
                this.formValidationService = new FormValidationService();
        }

        @Test
        public void testValidFormSubmission() {
                String formTemplateJson = "{\n" + //
                                "    \"fields\": [\n" + //
                                "        {\n" + //
                                "            \"title\": \"Alter\",\n" + //
                                "            \"key\": \"age\",\n" + //
                                "            \"type\": \"number\"\n" + //
                                "        }\n" + //
                                "    ]\n" + //
                                "}";
                String submittedDataJson = "{\n" + //
                                "    \"age\": 12\n" + //
                                "}";
                assertTrue(formValidationService.validateFormData(formTemplateJson, submittedDataJson));
        }

        @Test
        public void testInvalidFormSubmissionWithInvalidKeys() {
                String formTemplateJson = "{\n" + //
                                "    \"fields\": [\n" + //
                                "        {\n" + //
                                "            \"title\": \"Alter\",\n" + //
                                "            \"key\": \"age\",\n" + //
                                "            \"type\": \"number\"\n" + //
                                "        }\n" + //
                                "    ]\n" + //
                                "}";
                String submittedDataJson = "{\n" + //
                                "    \"age\": 12,\n" + //
                                "    \"name\": \"Max\"\n" + //
                                "}";
                assertFalse(formValidationService.validateFormData(formTemplateJson, submittedDataJson));
        }

        @Test
        public void testInvalidFormSubmissionWithMissingKeys() {
                String formTemplateJson = "{\n" + //
                                "    \"fields\": [\n" + //
                                "        {\n" + //
                                "            \"title\": \"Alter\",\n" + //
                                "            \"key\": \"age\",\n" + //
                                "            \"type\": \"number\"\n" + //
                                "        },\n" + //
                                "        {\n" + //
                                "            \"title\": \"Name\",\n" + //
                                "            \"key\": \"name\",\n" + //
                                "type\": \"text\"\n" + //
                                "        }\n" + //
                                "    ]\n" + //
                                "}";
                String submittedDataJson = "{\n" + //
                                "    \"age\": 12\n" + //
                                "}";
                assertFalse(formValidationService.validateFormData(formTemplateJson, submittedDataJson));
        }

        @Test
        public void testValidFormSubmissionWithMissingKeysAndStrictFalse() {
                String formTemplateJson = "{\n" + //
                                "    \"fields\": [\n" + //
                                "        {\n" + //
                                "            \"title\": \"Alter\",\n" + //
                                "            \"key\": \"age\",\n" + //
                                "            \"type\": \"number\"\n" + //
                                "        },\n" + //
                                "        {\n" + //
                                "            \"title\": \"Name\",\n" + //
                                "            \"key\": \"name\",\n" + //
                                "            \"type\": \"text\"\n" + //
                                "        }\n" + //
                                "    ]\n" + //
                                "}";
                String submittedDataJson = "{\n" + //
                                "    \"age\": 12\n" + //
                                "}";
                assertTrue(formValidationService.validateFormData(formTemplateJson, submittedDataJson, false));
        }

        @Test
        public void testInvalidFormSubmissionWithInvalidTypes() {
                String formTemplateJson = "{\n" + //
                                "    \"fields\": [\n" + //
                                "        {\n" + //
                                "            \"title\": \"Alter\",\n" + //
                                "            \"key\": \"age\",\n" + //
                                "            \"type\": \"number\"\n" + //
                                "        }\n" + //
                                "    ]\n" + //
                                "}";
                String submittedDataJson = "{\n" + //
                                "    \"age\": \"12\"\n" + //
                                "}";
                assertFalse(formValidationService.validateFormData(formTemplateJson, submittedDataJson));
        }

        @Test
        public void testInvalidFormSubmissionWithMissingRequiredField() {
                String formTemplateJson = "{\n" + //
                                "    \"fields\": [\n" + //
                                "        {\n" + //
                                "            \"title\": \"Alter\",\n" + //
                                "            \"key\": \"age\",\n" + //
                                "            \"type\": \"number\",\n" + //
                                "            \"required\": true\n" + //
                                "        }\n" + //
                                "    ]\n" + //
                                "}";
                String submittedDataJson = "{}";
                assertFalse(formValidationService.validateFormData(formTemplateJson, submittedDataJson));
        }

        @Test
        public void testValidFormSubmissionWithMultipleFields() {
                String formTemplateJson = "{\n" + //
                                "    \"fields\": [\n" + //
                                "        {\n" + //
                                "            \"title\": \"Alter\",\n" + //
                                "            \"key\": \"age\",\n" + //
                                "            \"type\": \"number\"\n" + //
                                "        },\n" + //
                                "        {\n" + //
                                "            \"title\": \"Name\",\n" + //
                                "            \"key\": \"name\",\n" + //
                                "            \"type\": \"text\"\n" + //
                                "        }\n" + //
                                "    ]\n" + //
                                "}";
                String submittedDataJson = "{\n" + //
                                "    \"age\": 12,\n" + //
                                "    \"name\": \"Max\"\n" + //
                                "}";
                assertTrue(formValidationService.validateFormData(formTemplateJson, submittedDataJson));
        }

        @Test
        public void testInvalidFormSubmissionWithMultipleFields() {
                String formTemplateJson = "{\n" + //
                                "    \"fields\": [\n" + //
                                "        {\n" + //
                                "            \"title\": \"Alter\",\n" + //
                                "            \"key\": \"age\",\n" + //
                                "            \"type\": \"number\"\n" + //
                                "        },\n" + //
                                "        {\n" + //
                                "            \"title\": \"Name\",\n" + //
                                "            \"key\": \"name\",\n" + //
                                "            \"type\": \"text\"\n" + //
                                "        }\n" + //
                                "    ]\n" + //
                                "}";
                String submittedDataJson = "{\n" + //
                                "    \"age\": \"Invalid Age\"\n" + //
                                "    \"name\": \"Max\"\n" + //
                                "}";
                assertFalse(formValidationService.validateFormData(formTemplateJson, submittedDataJson));
        }

        @Test
        public void testValidFormSubmissionWithMultipleFieldsAndStrictFalse() {
                String formTemplateJson = "{\n" + //
                                "    \"fields\": [\n" + //
                                "        {\n" + //
                                "            \"title\": \"Alter\",\n" + //
                                "            \"key\": \"age\",\n" + //
                                "            \"type\": \"number\"\n" + //
                                "        },\n" + //
                                "        {\n" + //
                                "            \"title\": \"Name\",\n" + //
                                "            \"key\": \"name\",\n" + //
                                "            \"type\": \"text\"\n" + //
                                "        }\n" + //
                                "    ]\n" + //
                                "}";
                String submittedDataJson = "{\n" + //
                                "    \"age\": 12,\n" + //
                                "    \"name\": \"Max\"\n" + //
                                "}";
                assertTrue(formValidationService.validateFormData(formTemplateJson, submittedDataJson, false));
        }

        @Test
        public void testSubmissionWithInvalidJson() {
                String formTemplateJson = "{\n" + //
                                "    \"fields\": [\n" + //
                                "        {\n" + //
                                "            \"title\": \"Alter\",\n" + //
                                "            \"key\": \"age\",\n" + //
                                "            \"type\": \"number\"\n" + //
                                "        }\n" + //
                                "    ]\n" + //
                                "}";
                String submittedDataJson = "Invalid{{{";
                assertFalse(formValidationService.validateFormData(formTemplateJson, submittedDataJson));
        }
}
