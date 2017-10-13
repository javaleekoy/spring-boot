package com.peramdy.annotation;

import com.peramdy.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * Created by peramdy on 2017/10/13.
 */
public class UserIdResolver implements HandlerMethodArgumentResolver {

    @Autowired
    private StudentService studentService;


    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return false;
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {

        UserId userId = methodParameter.getParameterAnnotation(UserId.class);


        return null;
    }
}
