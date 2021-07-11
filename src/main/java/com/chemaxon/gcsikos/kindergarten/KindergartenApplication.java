package com.chemaxon.gcsikos.kindergarten;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

@SpringBootApplication
public class KindergartenApplication implements CommandLineRunner {

	@Autowired
	private PopularitySolver popularitySolver;

	@Value("${paper.file.path}")
	private String file;

	public static void main(String[] args) {
		SpringApplication.run(KindergartenApplication.class, args);
	}

	@Override
	public void run(String... args) {
		InputStream inputStream =  getClass().getClassLoader().getResourceAsStream(file);
		System.out.println(popularitySolver.solve(inputStream));
	}
}
