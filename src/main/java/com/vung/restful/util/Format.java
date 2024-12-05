package com.vung.restful.util;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.vung.restful.domain.RestResponse;
import com.vung.restful.util.CustomAnnotition.APImessage;

import jakarta.servlet.http.HttpServletResponse;

@ControllerAdvice
public class Format implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }
    @Override
    public Object beforeBodyWrite( 
        Object body,
        MethodParameter returnType,                            
        MediaType selectedContentType,
        Class selectedConverterType,                            
        ServerHttpRequest request,
        ServerHttpResponse response) {
                
                HttpServletResponse servletResponse = ((ServletServerHttpResponse) response).getServletResponse();
                int status = servletResponse.getStatus();
                RestResponse<Object> restResponse = new RestResponse<Object>();
                restResponse.setStatusCode(status);
                // Check if the status code represents an error
                if (status >= 400) {
                    return body;
                } else {
                //case success
                    restResponse.setData(body);
                    APImessage message = returnType.getMethodAnnotation(APImessage.class);
                    restResponse.setMessage(message!= null ? message.value() : "call success" );
                    
                }
                return restResponse;
    }

}