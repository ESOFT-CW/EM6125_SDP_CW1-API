package lk.esoft.EM6125_SDP_CW1API;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class Em6125SdpCw1ApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(Em6125SdpCw1ApiApplication.class, args);
	}
	@Bean
	public WebMvcConfigurer configure() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.
						addMapping("/**").
						allowedOrigins("http://localhost:4200");
			}

		};
	}
	@Bean
	public PasswordEncoder passwordEncoder(){
		return NoOpPasswordEncoder.getInstance();
	}
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
}
