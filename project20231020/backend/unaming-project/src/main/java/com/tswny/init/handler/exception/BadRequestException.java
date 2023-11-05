package com.tswny.init.handler.exception;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class BadRequestException extends AbstractThrowableProblem {
    private static final long serialVersionUID = 1L;

    public BadRequestException(String path, String title) {
        super(URI.create(ErrorConstants.PROBLEM_BASE_URL + path), title, Status.BAD_REQUEST);
    }

}
