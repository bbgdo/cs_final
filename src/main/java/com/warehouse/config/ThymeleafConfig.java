package com.warehouse.config;

import lombok.Getter;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

public class ThymeleafConfig {
    @Getter
    private static final TemplateEngine templateEngine;

    static {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("/templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode("HTML5");
        templateResolver.setCharacterEncoding("UTF-8");
        //templateResolver.setCacheable(false); // Set to true in production

        templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);
    }
}
