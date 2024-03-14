package com.trekko.api.interceptors;

import io.github.bucket4j.Bucket;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

class RateLimitFilterTests {

    @Mock
    private Bucket bucket;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain chain;

    @InjectMocks
    private RateLimitFilter rateLimitFilter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void whenBucketAllows_thenProceedWithFilterChain() throws Exception {
        when(bucket.tryConsume(1)).thenReturn(true);

        rateLimitFilter.doFilter(request, response, chain);

        verify(chain, times(1)).doFilter(request, response);
    }
}
