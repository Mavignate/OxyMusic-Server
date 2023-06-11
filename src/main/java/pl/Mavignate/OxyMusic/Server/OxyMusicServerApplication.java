package pl.Mavignate.OxyMusic.Server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.IOException;

@SpringBootApplication
public class OxyMusicServerApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(OxyMusicServerApplication.class, args);
	}

}
