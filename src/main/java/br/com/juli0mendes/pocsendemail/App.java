package br.com.juli0mendes.pocsendemail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.juli0mendes.pocsendemail.core.EmailCore;

@SpringBootApplication
public class App implements CommandLineRunner {
	
	private static final Logger log = LoggerFactory.getLogger(App.class);
	
	private final EmailCore core;
	
	@Autowired
	public App(EmailCore core) {
		super();
		this.core = core;
	}

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		log.info("sending email");
		
		try {
			this.core.sendEmail();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		log.info("sent");		
	}

}
