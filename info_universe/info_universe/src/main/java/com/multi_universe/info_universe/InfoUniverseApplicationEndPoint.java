package com.multi_universe.info_universe;

import com.multi_universe.info_universe.Main.Principal;
import com.multi_universe.info_universe.RepositorioDB.AsteroidesAvistadosPorDiaRepository;
import com.multi_universe.info_universe.RepositorioDB.FotosMarteRepository;
import com.multi_universe.info_universe.RepositorioDB.ImagenDelDiaRepository;
import com.multi_universe.info_universe.RepositorioDB.ImagenTierraSatelitalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class InfoUniverseApplicationEndPoint {
	public static void main(String[] args){
		SpringApplication.run(InfoUniverseApplicationEndPoint.class,args);
	}
}

