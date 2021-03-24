package com.stackroute.runners;

import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;

public class CustomizationBean2  implements WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> {

    public void customize(ConfigurableServletWebServerFactory factory) {
        factory.setPort(8346);
        factory.setContextPath("");
    }
}
