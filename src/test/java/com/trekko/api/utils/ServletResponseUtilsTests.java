package com.trekko.api.utils;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import jakarta.servlet.http.HttpServletResponse;

import java.io.PrintWriter;
import java.io.StringWriter;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ServletResponseUtilsTests {

    @Test
    void appendDtoWritesCorrectResponse() throws Exception {
        HttpServletResponse mockResponse = mock(HttpServletResponse.class);
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);

        when(mockResponse.getWriter()).thenReturn(writer);

        Object dtoResponse = new Object() {
            @SuppressWarnings("unused")
            public final String field = "value";
        };

        ServletResponseUtils.appendDto(mockResponse, dtoResponse, HttpStatus.OK);

        writer.flush(); // Important to ensure the writer's buffer is flushed to the StringWriter

        verify(mockResponse).setStatus(HttpStatus.OK.value());
        verify(mockResponse).setContentType("application/json");
        verify(mockResponse).setCharacterEncoding("UTF-8");

        String expectedJson = "{\"field\":\"value\"}";
        assertEquals(expectedJson, stringWriter.toString());
    }
}
