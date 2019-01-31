package org.anirudh.marketplace;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@SpringBootApplication
@EnableScheduling
public class MarketplaceApplication {

	@Autowired
	static EntityManagerFactory emf;
	public static void main(String[] args) {
		SpringApplication.run(MarketplaceApplication.class, args);
		emf = Persistence.createEntityManagerFactory("marketplacePersistence");
	}

}

