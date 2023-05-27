package lk.premier.league.lpl;

import lk.premier.league.lpl.service.api.ScoreService;
import lk.premier.league.lpl.service.impl.ScoreServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LplApplication {

	public static void main(String[] args){
		SpringApplication.run(LplApplication.class, args);
	}
}
