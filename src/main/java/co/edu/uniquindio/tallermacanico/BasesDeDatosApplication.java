package co.edu.uniquindio.tallermacanico;

import co.edu.uniquindio.tallermacanico.config.TestConexion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BasesDeDatosApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(BasesDeDatosApplication.class, args);
	}
    @Autowired
    private TestConexion testConexion;
    @Override
    public void run(String... args) throws Exception {
        testConexion.probarConexion();
    }
}
