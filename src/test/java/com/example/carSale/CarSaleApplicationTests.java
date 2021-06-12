package com.example.carSale;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


@SpringBootTest
class CarSaleApplicationTests {
	AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);

	@Test
	void test1() {
		Car testCar = context.getBean("convertibleCar",Car.class);
		String result = testCar.readyForSale();

		Assertions.assertEquals(result, "Car Ready In : Convertible");
	}
	@Test
	void test2() {
		Car testCar = context.getBean("minivanCar",Car.class);
		String result = testCar.readyForSale();

		Assertions.assertEquals(result, "Car Ready In : Minivan");
	}
	@Test
	void test3() {
		Car testCar = context.getBean("suvCar",Car.class);
		String result = testCar.readyForSale();

		Assertions.assertEquals(result, "Car Ready In : SUV");
	}


}
