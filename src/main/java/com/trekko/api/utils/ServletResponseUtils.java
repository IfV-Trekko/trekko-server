package com.trekko.api.utils;

import java.io.IOException;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletResponse;

public final class ServletResponseUtils {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private ServletResponseUtils() {
    }

    public static void appendDto(final HttpServletResponse httpResponse, final Object dtoResponse,
            final HttpStatus status) throws IOException {
        httpResponse.setStatus(status.value());
        httpResponse.setContentType("application/json");
        httpResponse.setCharacterEncoding("UTF-8");
        httpResponse.getWriter().write(OBJECT_MAPPER.writeValueAsString(dtoResponse));
    }
}
