package com.loci;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.loci.services.NetworkService;

@SuppressWarnings("unused")
@SpringBootApplication
public class LociBackApplication {
	
	
	static Boolean isWeightedGraph = true;
		
	public final static String  tab[] = {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","3b","7b"};
	
	@Autowired
	static NetworkService networkService = new NetworkService();
	
	public static void main(String[] args) throws IOException {
		SpringApplication.run(LociBackApplication.class, args);
		networkService.createAllTheNetwork(tab, isWeightedGraph);

	}
}
