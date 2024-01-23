package com.trekko.api.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Form {

    @JsonProperty("$schema")
    private String schema;

    private List<FormField> fields;

    public List<FormField> getFields() {
        return this.fields;
    }

    public static class FormField {
        private String title;
        private String key;
        private String type;
        private boolean required;
        private String regex;
        private List<FormFieldOption> options;

        public String getTitle() {
            return this.title;
        }

        public String getKey() {
            return this.key;
        }

        public String getType() {
            return this.type;
        }

        public boolean isRequired() {
            return this.required;
        }

        public String getRegex() {
            return this.regex;
        }

        public List<FormFieldOption> getOptions() {
            return this.options;
        }
    }

    public static class FormFieldOption {
        private String title;
        private String key;

        public String getTitle() {
            return this.title;
        }

        public String getKey() {
            return this.key;
        }
    }
}
