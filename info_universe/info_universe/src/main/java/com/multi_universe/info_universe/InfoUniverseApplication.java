package com.multi_universe.info_universe;

import com.multi_universe.info_universe.RepositorioDB.AsteroidesAvistadosPorDiaRepository;
import com.multi_universe.info_universe.RepositorioDB.FotosMarteRepository;
import com.multi_universe.info_universe.RepositorioDB.ImagenDelDiaRepository;
import com.multi_universe.info_universe.RepositorioDB.ImagenTierraSatelitalRepository;
import com.multi_universe.info_universe.Main.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class InfoUniverseApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(InfoUniverseApplication.class, args);
	}

	@Autowired
	private ImagenDelDiaRepository imagenDelDiaRepository;
	@Autowired
	private FotosMarteRepository fotosMarteRepository;
	@Autowired
	private AsteroidesAvistadosPorDiaRepository asteroidesAvistadosPorDiaRepository;
	@Autowired
	private ImagenTierraSatelitalRepository imagenTierraSatelitalRepository;

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new
				Principal(imagenTierraSatelitalRepository,imagenDelDiaRepository,fotosMarteRepository,asteroidesAvistadosPorDiaRepository);
		principal.menuPrincipal();
	}
}

