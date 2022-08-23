package ru.czl.lpo.server;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import ru.czl.lpo.server.config.WebConfig;

import javax.servlet.*;

public class ApplicationInitializer implements WebApplicationInitializer {

    private final static String DISPATCHER = "dispatcher";

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext(); //создание контекста
        ctx.register(WebConfig.class);// рег веб конфиг
        servletContext.addListener(new ContextLoaderListener(ctx));

        ServletRegistration.Dynamic servlet = servletContext.addServlet(DISPATCHER, new DispatcherServlet(ctx)); // добавление в диспетчер севрлетов  - сервлетконтекст
        servlet.addMapping("/"); // привязка к опред URL
        servlet.setLoadOnStartup(1); // порядок инициализации данного сервлета
    }
}
