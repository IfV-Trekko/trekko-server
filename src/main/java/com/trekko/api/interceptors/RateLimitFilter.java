package com.trekko.api.interceptors;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletResponse;
import com.trekko.api.dtos.ErrorResponseDto;
import com.trekko.api.utils.ResponseReason;
import com.trekko.api.utils.ServletResponseUtils;

import org.springframework.http.HttpStatus;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.time.Duration;

public class RateLimitFilter extends GenericFilterBean {
    private static final int REQUESTS_PER_WINDOW = 1000;
    private static final int WINDOW_SIZE = 1;

    private final Bucket bucket;

    public RateLimitFilter() {
        final Bandwidth limit = Bandwidth.classic(REQUESTS_PER_WINDOW,
                Refill.greedy(REQUESTS_PER_WINDOW, Duration.ofMinutes(WINDOW_SIZE)));
        this.bucket = Bucket.builder().addLimit(limit).build();
    }

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain)
            throws IOException, ServletException {
        if (this.bucket.tryConsume(1)) {
            chain.doFilter(request, response);
        } else {
            final var httpResponse = (HttpServletResponse) response;
            final var errorResponse = new ErrorResponseDto(ResponseReason.FAILED_TOO_MANY_REQUESTS);
            ServletResponseUtils.appendDto(httpResponse, errorResponse, HttpStatus.TOO_MANY_REQUESTS);
        }
    }
}
