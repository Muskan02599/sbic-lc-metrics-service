package com.inno;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
@Service
public class TemplateService {
	 
	@Autowired TemplateEngine templateEngine ;
	public String generateTemplate(String templateName, Map<String, Object> dataMap) {
		Context context = new Context();
		context.setVariables(dataMap);
		return templateEngine.process(templateName, context);
	}
}