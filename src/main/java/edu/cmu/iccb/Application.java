package edu.cmu.iccb;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

@SpringBootApplication
@EnableOAuth2Sso
public class Application extends WebSecurityConfigurerAdapter {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

//    @Bean
//    CommandLineRunner init() {
//		return new CommandLineRunner() {
//			@Override
//			public void run(String... args) throws Exception {}
//	    };
//    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
      http
        .antMatcher("/**")
        .authorizeRequests()
          .antMatchers("/", "/login**", "/github/success**", "/css**", "/js**", "/fonts**")
          //.antMatchers("/", "/login**", "/css**", "/js**", "/fonts**")
          .permitAll()
        .anyRequest()
          .authenticated();
      
      http.csrf().disable();
    }
}

