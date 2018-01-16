package com.cs.mvc.interceptor;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

/**
 * 异常处理实现类（Controller、Service、Dao层Exception）
 * 
 * @author vincent
 * 
 */
public class ExceptionResolver implements HandlerExceptionResolver {
	private final static Logger logger = LoggerFactory
			.getLogger(ExceptionResolver.class);

	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {

		// 取得Exception字符串
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ex.printStackTrace(new PrintStream(baos));
		String exception = baos.toString();
		// 记录错误日志
		logger.error(exception);
		// 跳转至500页面
		ModelAndView mav = new ModelAndView("/error/500");
		mav.addObject("ex", exception);
		return mav;
	}
}
