package com.project.devgram.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Aspect
public class AopBinding {

    @Pointcut("execution(* com.project.devgram..*.UserController.*(..))")
    public void UserConExecution() {}

/*

    @Around(value="UserConExecution()")
    public Object validationCheck(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        log.info("aop execution");
        String type = proceedingJoinPoint.getSignature().getDeclaringTypeName();
        String method = proceedingJoinPoint.getSignature().getName();
        Object[] args = proceedingJoinPoint.getArgs();

        System.out.println("type: " + type);
        System.out.println("method = " + method);

        for (Object arg : args
        ) {
            if (arg instanceof AopBinding) {

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
*/

}
