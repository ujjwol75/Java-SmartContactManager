package com.smart.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

//import org.springframework.security.config.annotation.web.configuration.webSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class MyConfig{
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf().disable()
			.authorizeHttpRequests()
			.requestMatchers("/admin/**")
			.hasRole("ADMIN")
			.requestMatchers("/user/**")
			.hasRole("USER")
			.requestMatchers("/**")
			.permitAll()
			.and()
			.formLogin()
			.loginPage("/signin")
			.loginProcessingUrl("/dologin")
			.defaultSuccessUrl("/user/index")
			.failureUrl("/login-fail");
		
		http.authenticationProvider(daoAuthenticationProvider());
		return http.build();
	}

	@Bean
	public UserDetailsService getUserDetailsService() {
		return new UserDetailServiceImpl();
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration configuration) throws Exception{
		return configuration.getAuthenticationManager();
	}
	
	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(this.getUserDetailsService());
		provider.setPasswordEncoder(passwordEncoder());
		
		return provider;
	}
	
	
	
}
