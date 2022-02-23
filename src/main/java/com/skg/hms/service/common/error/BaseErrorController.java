package com.skg.hms.service.common.error;

import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class BaseErrorController implements ErrorController {

	@RequestMapping(value = "/error")
	public String errorHandler(HttpServletResponse response) {
		return "/error/errorPage";
	}
	@RequestMapping(value = "/")
	public String index(HttpServletResponse response) {
		return "index";
	}
//	@Override
	public String getErrorPath() {
		return "/error";
	}
}
