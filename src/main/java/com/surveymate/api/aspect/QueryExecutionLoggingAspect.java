package com.surveymate.api.aspect;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Log4j2
@Aspect
@Component
public class QueryExecutionLoggingAspect {

    @Around("execution(* com.surveymate..repository..*(..))")
    public Object logRepositoryMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        return logExecution(joinPoint, "Repository");
    }

    @Around("execution(* com.querydsl.jpa.impl.JPAQueryFactory.*(..))")
    public Object logQueryDSLMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        return logExecution(joinPoint, "QueryDSL");
    }


    private Object logExecution(ProceedingJoinPoint joinPoint, String source) throws Throwable {
        String methodName = joinPoint.getSignature().toShortString();
        Object[] methodArgs = joinPoint.getArgs();

        log.info("[{}] Method called: {}", source, methodName);
        log.debug("[{}] Arguments: {}", source, methodArgs);

        long startTime = System.currentTimeMillis();

        try {
            Object result = joinPoint.proceed();

            long elapsedTime = System.currentTimeMillis() - startTime;
            log.info("[{}] Method executed: {} | Time taken: {} ms", source, methodName, elapsedTime);

            return result;
        } catch (Exception e) {
            log.error("[{}] Exception in method: {}", source, methodName, e);
            throw e;
        }
    }

}
