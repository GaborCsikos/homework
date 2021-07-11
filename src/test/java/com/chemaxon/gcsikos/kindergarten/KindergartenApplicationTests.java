package com.chemaxon.gcsikos.kindergarten;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class KindergartenApplicationTests {

	@Autowired
	private PopularitySolver popularitySolver;

	@Test
	public void basicTest() {
		//GIVEN
		InputStream inputStream =  getClass().getClassLoader().getResourceAsStream("basic.txt");

		//WHEN
		String result = popularitySolver.solve(inputStream);

		//THEN
		assertEquals("Popular by vote number:2 name(s):[Szabina]", result);
	}

	@Test
	public void comesBackTestCase() {
		//GIVEN
		InputStream inputStream =  getClass().getClassLoader().getResourceAsStream("comes_back.txt");

		//WHEN
		String result = popularitySolver.solve(inputStream);

		//THEN
		assertEquals("Popular by vote number:2 name(s):[Szabina]", result);
	}

	@Test
	public void sameResultTestCase() {
		//GIVEN
		InputStream inputStream =  getClass().getClassLoader().getResourceAsStream("same_result.txt");

		//WHEN
		String result = popularitySolver.solve(inputStream);

		//THEN
		assertEquals("Popular by vote number:1 name(s):[Beatrix, Gabor]", result);
	}

	@Test
	public void saysHimOrHerSelf() {
		//GIVEN
		InputStream inputStream =  getClass().getClassLoader().getResourceAsStream("says_him_herself.txt");

		//WHEN
		String result = popularitySolver.solve(inputStream);

		//THEN
		assertEquals("Popular by vote number:2 name(s):[Szabina]", result);
	}

}
