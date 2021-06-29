package com.example.newCalculator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class NewCalculatorApplicationTests {

	private Calculator calculator;

	@BeforeEach
	public void setUp () {
		calculator = new Calculator();
	}

	@Test
	@DisplayName("test sum")
	void test1() {
		Integer first = 3 ;
		Integer second = 7 ;
		Integer expected = 10;

		assert calculator.sum(first, second).equals(expected);
	}
	@Test
	@DisplayName("test subtraction")
	void test2() {
		Integer first = 20 ;
		Integer second = 7 ;
		Integer expected = 13;

		assert calculator.substraction(first, second).equals(expected);
	}
	@Test
	@DisplayName("test multiply")
	void test3() {
		Integer first = 3 ;
		Integer second = 7 ;
		Integer expected = 21;

		assert calculator.multiply(first, second).equals(expected);
	}
	@Test
	@DisplayName("test division")
	void test4() {
		Integer first = 30 ;
		Integer second = 3 ;
		Integer expected = 10;

		assert calculator.division(first, second).equals(expected);
	}

	@Test
	@DisplayName("test exponentiation")
	void test5() {
		Integer first = 2 ;
		Integer second = 10 ;
		Integer expected = 1024;
		System.out.println(calculator.exponentiation(first, second));

		assert calculator.exponentiation(first, second).equals(expected);
	}

	@Test
	@DisplayName("test root extraction")
	void test6() {
		Double first = 27.0 ;
		Double second = 3.0 ;
		Double expected = 3.0;

		assert calculator.rootExtraction(first, second).equals(expected);
	}

	@Test
	@DisplayName("test root extraction")
	void test7() {
		Double first = 27.0 ;
		Double base = 3.0;
		Double expected = 3.0;

		assert calculator.logarithmExtraction(first,base).equals(expected);
	}

}
