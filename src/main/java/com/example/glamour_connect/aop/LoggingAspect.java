package com.example.glamour_connect.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    @Around("@annotation(logExecution)")
    public Object logMethod(ProceedingJoinPoint joinPoint, LogExecution logExecution) throws Throwable {
        String methodName = joinPoint.getSignature().toShortString();
        System.out.println("Starting method: " + methodName);

        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long end = System.currentTimeMillis();

        System.out.println("Finished method: " + methodName + " in " + (end - start) + "ms");
        return result;
    }
}