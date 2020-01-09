package com.example.demoproject.controller	;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.example.demoproject.bean.HelloBean;

@RestController
public class HelloController {
	
	@Autowired
	private MessageSource messageSource;
	
	@GetMapping(path = "/hello-world")
	public String helloMethod() {
		return "Hello World";
	}
	
	@GetMapping(path = "/hello-bean")
	public HelloBean helloBean() {
		return new HelloBean("Hello 1");
	}
	
	@GetMapping(path="/hello-bean-id/user/{name}")
	public HelloBean helloID(@PathVariable String name) {
		return new HelloBean(String.format("Hello %s", name));
	}
	
	@GetMapping(path = "/hello-world-i18n")
	public String helloI18n(@RequestHeader(name="Accept-Language", required=false) Locale locale) {
		return messageSource.getMessage("good.morning.message", null, LocaleContextHolder.getLocale());
	}

}
