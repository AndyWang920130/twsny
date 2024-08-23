package com.tswny.init.aop.web;

import com.tswny.init.config.CustomFileHandlerConfiguration;
import com.tswny.init.domain.AbstractAuditingEntity;
import com.tswny.init.util.constants.CommonConstants;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;

import java.util.Arrays;

@Aspect
public class WebAspect {
    private final Logger log = LoggerFactory.getLogger(WebAspect.class);

    private final Environment env;

    public WebAspect(Environment env) {
        this.env = env;
    }

    /**
     * Pointcut that matches all repositories, services and Web REST endpoints.
     */
    @Pointcut("within(@org.springframework.stereotype.Repository *)" +
            " || within(@org.springframework.stereotype.Service *)" +
            " || within(@org.springframework.web.bind.annotation.RestController *)")
    public void springBeanPointcut() {
        // Method is empty as this is just a Pointcut, the implementations are in the advices.
    }

    /**
     * PostMapping,PutMapping切点
     */
    @Pointcut("within(@org.springframework.web.bind.annotation.PostMapping *)" +
            " || within(@org.springframework.web.bind.annotation.PutMapping *)")
    public void requestMapPointcut() {
        // Method is empty as this is just a Pointcut, the implementations are in the advices.
    }


    /**
     * 日志 切面 自定义注解  切到任意方法
     */
    @Pointcut("within(com.tswny.init.repository..*)"+
            " || within(com.tswny.init..service..*)"+
            " || within(com.tswny.init.web.rest..*)")
    public void applicationPackagePointcut() {
        // Method is empty as this is just a Pointcut, the implementations are in the advices.
    }


    /**
     * Retrieves the {@link Logger} associated to the given {@link JoinPoint}.
     *
     * @param joinPoint join point we want the logger for.
     * @return {@link Logger} associated to the given {@link JoinPoint}.
     */
    private Logger logger(JoinPoint joinPoint) {
        return LoggerFactory.getLogger(joinPoint.getSignature().getDeclaringTypeName());
    }


    /**
     * Advice that logs methods throwing exceptions.
     *
     * @param joinPoint join point for advice.
     * @param e exception.
     */
    @AfterThrowing(pointcut = "applicationPackagePointcut()", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
        if (env.acceptsProfiles(Profiles.of(CommonConstants.SPRING_PROFILE_DEVELOPMENT))) {
            logger(joinPoint)
                    .error(
                            "Exception in {}() with cause = \'{}\' and exception = \'{}\'",
                            joinPoint.getSignature().getName(),
                            e.getCause() != null ? e.getCause() : "NULL",
                            e.getMessage(),
                            e
                    );
        } else {
            logger(joinPoint)
                    .error(
                            "Exception in {}() with cause = {}",
                            joinPoint.getSignature().getName(),
                            e.getCause() != null ? e.getCause() : "NULL"
                    );
        }
    }

    /**
     * Advice that logs when a method is entered and exited.
     *
     * @param joinPoint join point for advice.
     * @return result.
     * @throws Throwable throws {@link IllegalArgumentException}.
     */
    @Around("applicationPackagePointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Logger log = logger(joinPoint);
        if (log.isDebugEnabled()) {
            log.debug("Enter: {}() with argument[s] = {}", joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));
        }
        try {
            Object result = joinPoint.proceed();
            if (log.isDebugEnabled()) {
                log.debug("Exit: {}() with result = {}", joinPoint.getSignature().getName(), result);
            }
            return result;
        } catch (IllegalArgumentException e) {
            log.error("Illegal argument: {} in {}()", Arrays.toString(joinPoint.getArgs()), joinPoint.getSignature().getName());
            throw e;
        }
    }

    @After("requestMapPointcut()")
    public void requestMapAfter(JoinPoint joinPoint) {
        log.warn("requestMapAfter execute");
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            if (arg instanceof AbstractAuditingEntity) {
                AbstractAuditingEntity requestBody = (AbstractAuditingEntity) arg;
                System.out.println("Request body: " + requestBody);
            }
        }
    }
}
