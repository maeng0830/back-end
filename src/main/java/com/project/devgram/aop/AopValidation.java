package com.project.devgram.aop;

import com.project.devgram.dto.CommonDto;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
@Aspect
public class AopValidation {

    @Pointcut("execution(* com.project.devgram..*.UserController.*(..)) " + "||"
            +   " execution(* com.project.devgram..*.TokenController.*(..))" )
    public void UserConExecution() {}

    @Around(value="UserConExecution()")
    public Object validationCheck(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        log.info("aop execution");
        String type = proceedingJoinPoint.getSignature().getDeclaringTypeName();
        String method = proceedingJoinPoint.getSignature().getName();
        Object[] args = proceedingJoinPoint.getArgs();

        log.info("type: " + type);
        log.info("method: " + method);

        for (Object arg : args
        ) {
            if (arg instanceof BindingResult) {

                BindingResult bindingResult = (BindingResult) arg;
                log.info("bindingResult {}", bindingResult );

                if (bindingResult.hasErrors()) {
                    Map<String, String> errorMap = new HashMap<>();

                    for (FieldError error : bindingResult.getFieldErrors()) {
                        errorMap.put(error.getField(), error.getDefaultMessage());
                        log.warn(type+"."+method+"() => 필드 : "+error.getField() + ", 메시지 : "+error.getDefaultMessage());
                    }

                    return new CommonDto<>(HttpStatus.BAD_REQUEST.value(),errorMap);
                }
            }

        }
        return proceedingJoinPoint.proceed();
    }

}
