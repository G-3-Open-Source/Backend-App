package nutrismart.recommendations;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class NutriSmartRecommendationsApplication {

	public static void main(String[] args) {
		SpringApplication.run(NutriSmartRecommendationsApplication.class, args);
	}
}
