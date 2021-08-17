package doancnpm.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import doancnpm.security.jwt.AuthEntryPointJwt;
import doancnpm.security.jwt.AuthTokenFilter;
import doancnpm.security.services.UserDetailsServiceImpl;



@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
		// securedEnabled = true,
		// jsr250Enabled = true,
		prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	UserDetailsServiceImpl userDetailsService;

	@Autowired
	private AuthEntryPointJwt unauthorizedHandler;

	@Bean
	public AuthTokenFilter authenticationJwtTokenFilter() {
		return new AuthTokenFilter();
	}

	@Override
	public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable()
			.exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
			.authorizeRequests()
			.antMatchers("/api/auth/**").permitAll()
			.antMatchers("/api/downloadFile/**").permitAll()
			.antMatchers("/post/**").permitAll()
			.antMatchers("/tutor/**").permitAll()
			.antMatchers("/comment/**").permitAll()
			.antMatchers("/student/**").permitAll()
			.antMatchers("/invitation/**").permitAll()
			.antMatchers("/suggestion/**").permitAll()
			.antMatchers("/api/tutor/search").permitAll()
			.antMatchers("/rating/**").permitAll()
			.antMatchers("/swagger-ui.html", "/configuration/**", "/swagger-resources/**", "/v2/api-docs/**","/webjars/**").permitAll()
			//.antMatchers("/post/**").permitAll()
			//.antMatchers("/api/uploadFile/**").permitAll()
			//.antMatchers("/api/test/**").permitAll()
			//.antMatchers("/api/tutor/**").permitAll()
			//.antMatchers("/api/user/**").permitAll()
			//.antMatchers("/api/message/**").permitAll()
			.anyRequest().authenticated();

		http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
	//	http.csrf().disable();
	}
}
