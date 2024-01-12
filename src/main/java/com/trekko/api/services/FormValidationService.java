package com.trekko.api.services;

import java.util.List;
import java.util.Set;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trekko.api.models.Form;

public class FormValidationService {
    private static final String BOOLEAN_FIELD_TYPE = "boolean";
    private static final String NUMBER_FIELD_TYPE = "number";
    private static final String SELECT_FIELD_TYPE = "select";

    private final ObjectMapper objectMapper = new ObjectMapper();

    public boolean validateFormData(final String formTemplateJson, final String submittedDataJson) {
        try {
            final Form formTemplate = this.objectMapper.readValue(formTemplateJson, Form.class);
            final JsonNode submittedData = this.objectMapper.readTree(submittedDataJson);

            return this.validateFields(formTemplate.getFields(), submittedData);
        } catch (final JsonProcessingException e) {
            return false;
        }
    }

    private boolean validateFields(final List<Form.FormField> templateFields, final JsonNode submittedData) {
        for (final Form.FormField templateField : templateFields) {
            final JsonNode fieldValue = submittedData.get(templateField.getKey());

            if (!this.isFieldValueValid(templateField, fieldValue)) {
                return false;
            }
        }

        if (this.hasExtraFields(templateFields, submittedData)) {
            return false;
        }

        return true;
    }

    private boolean isFieldValueValid(final Form.FormField templateField, final JsonNode fieldValue) {
        if (fieldValue == null || fieldValue.isNull()) {
            return !templateField.isRequired();
        }

        switch (templateField.getType()) {
            case BOOLEAN_FIELD_TYPE:
                return fieldValue.isBoolean();
            case NUMBER_FIELD_TYPE:
                return fieldValue.isNumber();
            case SELECT_FIELD_TYPE:
                return this.isValidDropdownValue(templateField.getOptions(), fieldValue.asText());
            default:
                return true;
        }
    }

    private boolean isValidDropdownValue(final List<Form.FormFieldOption> options, final String submittedValue) {
        return options.stream().anyMatch(option -> option.getKey().equals(submittedValue));
    }

    private boolean hasExtraFields(final List<Form.FormField> templateFields, final JsonNode submittedData) {
        final Set<String> templateFieldKeys = templateFields.stream().map(Form.FormField::getKey)
                .collect(Collectors.toSet());
        final Set<String> submittedFieldKeys = StreamSupport.stream(Spliterators.spliteratorUnknownSize(
                submittedData.fieldNames(), Spliterator.ORDERED), false)
                .collect(Collectors.toSet());

        return !submittedFieldKeys.equals(templateFieldKeys);
    }
}
