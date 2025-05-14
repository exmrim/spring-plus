package org.example.expert.aop;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.example.expert.aop.entity.ActionType;
import org.example.expert.aop.entity.Log;
import org.example.expert.aop.entity.ResultType;
import org.example.expert.aop.repository.LogRepository;
import org.example.expert.aop.service.LogService;
import org.example.expert.domain.user.dto.request.UserRoleChangeRequest;
import org.example.expert.domain.user.entity.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class AdminAccessLoggingAspect {

    private final HttpServletRequest request;
    private final LogService logService;

    //Lv1-5. 코드 개선 퀴즈 - AOP의 이해
    @Before("execution(* org.example.expert.domain.user.controller.UserAdminController.changeUserRole(..))")
    public void logBeforeChangeUserRole(JoinPoint joinPoint) {
        String userId = String.valueOf(request.getAttribute("userId"));
        String requestUrl = request.getRequestURI();
        LocalDateTime requestTime = LocalDateTime.now();

        log.info("Admin Access Log - User ID: {}, Request Time: {}, Request URL: {}, Method: {}",
                userId, requestTime, requestUrl, joinPoint.getSignature().getName());
    }
    //Lv3-11.Transaction 심화
    @AfterReturning(pointcut = "execution(* org.example.expert.domain.user.service.UserAdminService.changeUserRole(..))", returning = "result")
    public void logAfterReturningChangeUserRole(JoinPoint joinPoint, Object result) {
        Log log = LogContext.get();
        if(log != null) {
            log.setResult(ResultType.SUCCESS);
            logService.save(log);
            LogContext.clear();
        }
    }

    //Lv3-11.Transaction 심화
    @AfterThrowing(pointcut = "execution(* org.example.expert.domain.user.service.UserAdminService.changeUserRole(..))", throwing = "exception")
    public void logAfterThrowingChangeUserRole(JoinPoint joinPoint, Throwable exception) {
        Log log = LogContext.get();
        if(log != null) {
            log.setResult(ResultType.FAIL);
            logService.save(log);
            LogContext.clear();
        }

    }
}
