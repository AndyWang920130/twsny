package com.tswny.init.handler.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.NativeWebRequest;
import org.zalando.problem.*;
import org.zalando.problem.spring.web.advice.ProblemHandling;
import org.zalando.problem.spring.web.advice.security.SecurityAdviceTrait;
import org.zalando.problem.violations.ConstraintViolationProblem;

import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@ControllerAdvice
public class ExceptionHandling implements ProblemHandling, SecurityAdviceTrait {
    /**
     * 是否将堆栈中的错误信息返回
     *
     * @return
     */
    @Override
    public boolean isCausalChainsEnabled() {
        return true;
    }


    @Override
    public void log(Throwable throwable, Problem problem, NativeWebRequest request, HttpStatus status) {
        ProblemHandling.super.log(throwable, problem, request, status);
        //  TODO 记录异常请求信息
        System.out.println("###############ExceptionHandler############");
    }

    /**
     * Post-process Problem payload to add the message key for front-end if needed
     */
    @Override
    public ResponseEntity<Problem> process(@Nullable ResponseEntity<Problem> entity, NativeWebRequest request) {
        if (entity == null || entity.getBody() == null) {
            return entity;
        }
        Problem problem = entity.getBody();
        if (!(problem instanceof ConstraintViolationProblem || problem instanceof DefaultProblem)) {
            return entity;
        }
        ProblemBuilder builder = Problem.builder()
                .withType(Problem.DEFAULT_TYPE.equals(problem.getType()) ? ErrorConstants.DEFAULT_TYPE : problem.getType())
                .withStatus(problem.getStatus())
                .withTitle(problem.getTitle())
                .with("path", request.getNativeRequest(HttpServletRequest.class).getRequestURI());

        if (problem instanceof ConstraintViolationProblem) {
            builder
                    .with("violations", ((ConstraintViolationProblem) problem).getViolations())
                    .with("message", ErrorConstants.ERR_VALIDATION);
            return new ResponseEntity<>(builder.build(), entity.getHeaders(), entity.getStatusCode());
        } else {
            builder
                    .withCause(((DefaultProblem) problem).getCause())
                    .withDetail(problem.getDetail())
                    .withInstance(problem.getInstance());
            problem.getParameters().forEach(builder::with);
            if (!problem.getParameters().containsKey("message") && problem.getStatus() != null) {
                builder.with("message", "error.http." + problem.getStatus().getStatusCode());
            }
            return new ResponseEntity<>(builder.build(), entity.getHeaders(), entity.getStatusCode());
        }
    }

    /**
     * 处理自定义业务异常
     *
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Problem> handleBadRequestExceptionException(BadRequestException ex, NativeWebRequest request) {
        StatusType statusType = ex.getStatus();
        return ResponseEntity.status(statusType.getStatusCode()).body(Problem.builder()
                .withDetail(ex.getTitle())
                .withStatus(statusType)
                .withTitle(ex.getTitle())
                .build());
    }
}
