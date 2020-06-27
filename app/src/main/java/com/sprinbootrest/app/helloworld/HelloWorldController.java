package com.sprinbootrest.app.helloworld;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
public class HelloWorldController {

    @Autowired
    private MessageSource messageSource;

    @GetMapping("/hello-world")
    public String helloWorld(){

        return "Hello World";
    }

    @GetMapping("/hello-world-bean")
    public HelloWorldBean helloWorldBean(){

        return new HelloWorldBean("Hello World");
    }

    @GetMapping("/hello-world/{name}")
    public HelloWorldBean helloWorldBean(@PathVariable String name){

        return new HelloWorldBean(String.format("Hello World, %s",name));
    }

    @GetMapping("/hello-world-internationalized")
    public String helloWorldInternationalized(){

        return messageSource.getMessage("good.morning.message",null, LocaleContextHolder.getLocale());
    }
}
