package com.tswny.init;

import com.tswny.init.webservice.AirlineWebService;
import com.tswny.init.webservice.TraditionalTranslateWebService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class UnNamingProjectApplication {

	public static void main(String[] args) {

		ConfigurableApplicationContext context = SpringApplication.run(UnNamingProjectApplication.class, args);
		TraditionalTranslateWebService traditionalTranslateWebService = (TraditionalTranslateWebService)context.getBean("traditionalTranslateWebService");
		System.out.println(traditionalTranslateWebService.getTraditionalString("龙"));
		System.out.println(traditionalTranslateWebService.getSimplifiedString("龍"));
		AirlineWebService airlineWebService = (AirlineWebService)context.getBean("airlineWebService");
		Object result = airlineWebService.getAirlineTime("北京", "上海", "2024-01-31");
		System.out.println(result);
	}

}
