package com.trekko.api.dtos;

public class OnboardingTextDto {
    private final String text;

    public OnboardingTextDto(final String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }
}
