package com.jeramtough.randl2;

import com.jeramtough.jtlog.facade.L;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.context.WebApplicationContext;

@SpringBootApplication
public class Randl2Application {

	public static void main(String[] args) {
		SpringApplication.run(Randl2Application.class, args);
		L.info("swagger: http://127.0.0.1:8088/randl2/swagger-ui.html");
	}

}
