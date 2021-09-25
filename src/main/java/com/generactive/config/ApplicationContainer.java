package com.generactive.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class ApplicationContainer {
    public static final ApplicationContext context =
            new AnnotationConfigApplicationContext(SpringConfiguration.class);
}
