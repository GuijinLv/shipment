package com.sap.iot.cd.roche.shipment.config;

import javax.naming.NamingException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jndi.JndiTemplate;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.sap.core.connectivity.api.configuration.ConnectivityConfiguration;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {
	@Bean
	public JndiTemplate jndiTemplate(){
		return new JndiTemplate();
	}
	
	@Bean
	public ConnectivityConfiguration connectivityConfiguration(JndiTemplate template) throws NamingException {
		ConnectivityConfiguration config = template.lookup("java:comp/env/connectivityConfiguration", ConnectivityConfiguration.class);
	    return config;
	}
}
