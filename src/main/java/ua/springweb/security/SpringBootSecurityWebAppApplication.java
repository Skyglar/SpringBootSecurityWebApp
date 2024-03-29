package ua.springweb.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.security.crypto.password.PasswordEncoder;

import ua.springweb.security.entity.UserEntity;
import ua.springweb.security.entity.enumeration.Role;
import ua.springweb.security.repository.UserRepository;

@SpringBootApplication
public class SpringBootSecurityWebAppApplication 
		extends SpringBootServletInitializer {
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(SpringBootSecurityWebAppApplication.class);
	}

	public static void main(String[] args) {
		ConfigurableApplicationContext context =
				SpringApplication.run(SpringBootSecurityWebAppApplication.class, args);
		addAdmin(context);
	}
	
	static void addAdmin(ConfigurableApplicationContext context) {
		String email = "admin@gmail.com";
		String password = "123";
		
		UserRepository userRepository = context.getBean(UserRepository.class);
		UserEntity user = userRepository.findUserEmail(email);
		
		if(user == null) {
			PasswordEncoder passwordEncoder = context.getBean(PasswordEncoder.class);
			
			user = new UserEntity();
			user.setEmail(email);
			user.setPassword(passwordEncoder.encode(password));
			user.setRole(Role.ROLE_ADMIN);
			
			userRepository.save(user);
		}
	}
}
