package com.example.carSale;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class CarSaleApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarSaleApplication.class, args);

		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);

		Car carForFamily = context.getBean("classicCar", Car.class);
		Car carForStudent = context.getBean("sportCar", Car.class);
		Car carForPlayboy = context.getBean("convertibleCar",Car.class);
		Car carForBigFamily = context.getBean("minivanCar",Car.class);
		Car carForFarmer = context.getBean("suvCar",Car.class);

		System.out.println(carForFamily.readyForSale());
		System.out.println(carForStudent.readyForSale());
		System.out.println(carForPlayboy.readyForSale());
		System.out.println(carForBigFamily.readyForSale());
		System.out.println(carForFarmer.readyForSale());


	}


}
