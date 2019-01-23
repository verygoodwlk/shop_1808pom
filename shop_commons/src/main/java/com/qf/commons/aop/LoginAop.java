package com.qf.commons.aop;

import com.qf.entity.User;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLEncoder;

/**
 * 登录验证的切面类
 *
 * @Author ken
 * @Date 2019/1/22
 * @Version 1.0
 */
@Aspect
public class LoginAop {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 切面方法
     *
     * 切点表达式 - 当前的切面作用于哪些目标方法
     * @return
     */
    @Around("@annotation(IsLogin)")
    public Object isLogin(ProceedingJoinPoint proceedingJoinPoint){

        //判断当前用户是否登录
        //获得HttpServletRequest
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();

        //通过request获得cookie
        //什么情况下会报空指针？
        String token = null;
        Cookie[] cookies = request.getCookies();
        if(cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("login_token")) {
                    token = cookie.getValue();
                    break;
                }
            }
        }

        User user = null;

        //根据token，从redis中获得取用信息
        if(token != null){
            user = (User) redisTemplate.opsForValue().get(token);
        }

        if(user == null){
            //未登录

            //需要强制跳转到登录页
            //无需跳转到登录页，目标方法的user对象为null就可以了
            //获得@IsLogin注解，判断是否需要强制跳转
            MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
            //获得目标方法对应的Method对象
            Method method = signature.getMethod();
            //从目标方法上获得注解
            IsLogin islogin = method.getAnnotation(IsLogin.class);
            //获得注解上的方法返回值
            boolean flag = islogin.tologin();
            if(flag){

                //获得当前的url
                String returnUrl = request.getRequestURL() + "?" + request.getQueryString();
                try {
                    returnUrl = URLEncoder.encode(returnUrl, "utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                //强制跳转到登录页
                return "redirect:http://localhost:8086/sso/tologin?returnUrl=" + returnUrl;
            }

        }


        //中间运行目标方法,同时让目标方法中的一个User形参，变成当前登录用户的信息
        //获得原来的参数列表
        Object[] args = proceedingJoinPoint.getArgs();
        for (int i = 0; i < args.length; i++) {
            if(args[i] != null && args[i].getClass() == User.class){
                args[i] = user;
            }
        }


        Object result = null;
        try {
            //按照新的参数列表运行目标方法
            result = proceedingJoinPoint.proceed(args);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }


        return result;
    }

}
