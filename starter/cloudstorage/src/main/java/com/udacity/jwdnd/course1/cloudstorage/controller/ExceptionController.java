package com.udacity.jwdnd.course1.cloudstorage.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ExceptionController implements HandlerExceptionResolver {

	private static Logger logger = LoggerFactory.getLogger(ExceptionController.class);
	
	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		logger.info("In ExceptionController.resolveException");
		ModelAndView modelAndView = new ModelAndView("error.html");
		if (ex instanceof MaxUploadSizeExceededException) {
			logger.info("\t====> maxsize exceed!");
			modelAndView.addObject("error_message", "It is not possible to upload the file. The file size is greater than current limit.");	
		}
		return modelAndView;
	}

}
