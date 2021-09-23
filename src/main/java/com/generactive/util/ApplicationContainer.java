package com.generactive.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class ApplicationContainer {
    public static final ApplicationContext context =
            new ClassPathXmlApplicationContext("applicationContext.xml");
}
