package com.aluracursos.challenges.literalura;

import com.aluracursos.challenges.literalura.menu.MenuHandler;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(LiteraluraApplication.class, args);
	}

	@Override
	public void run(String... args) {
		MenuHandler menuHandler = new MenuHandler();
		menuHandler.iniciarMenu();
	}
}
