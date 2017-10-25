package com.peramdy.annotation;

import com.peramdy.service.StudentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * Created by peramdy on
 *
 * @author peramdy
 * @date 2017/10/13
 */
public class UserIdResolver implements HandlerMethodArgumentResolver {

    @Autowired
    private StudentService studentService;


    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        if (methodParameter.hasParameterAnnotation(UserId.class)) {
            return true;
        }

        if (methodParameter.getParameterAnnotation(UserId.class) != null) {
            return true;
        }

        return false;
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter,
                                  ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest nativeWebRequest,
                                  WebDataBinderFactory webDataBinderFactory) throws Exception {

        UserId userId = methodParameter.getParameterAnnotation(UserId.class);
        if (userId == null) {
            return null;
        }

        String auth = nativeWebRequest.getHeader("auth-token");
        if (StringUtils.isBlank(auth)) {
            return null;
        }
//        StudentService studentService1 = new StudentServiceImpl();
        int stuId = studentService.queryUserIdByToken(auth);
        return stuId;
    }


}
