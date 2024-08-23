package com.tswny.init.handler.exception;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.net.URI;

public class UnAuthorizedException extends AbstractThrowableProblem {

    private static final long serialVersionUID = 1L;

    public UnAuthorizedException() {
        super(URI.create(ErrorConstants.PROBLEM_BASE_URL + "/unauthorized"), "unauthorized", Status.UNAUTHORIZED);
    }
}
