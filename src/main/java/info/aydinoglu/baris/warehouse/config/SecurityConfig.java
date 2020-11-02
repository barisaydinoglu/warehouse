package info.aydinoglu.baris.warehouse.config;

import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private static final String ROLE_ADMIN = "ADMIN";
  private static final String ROLE_USER = "USER";

  @Override
  protected void configure(final HttpSecurity http) throws Exception {
    http
        .csrf().disable()
        .authorizeRequests()
        .antMatchers(HttpMethod.DELETE).hasRole(ROLE_ADMIN)
        .antMatchers(HttpMethod.POST).hasRole(ROLE_ADMIN)
        .anyRequest().authenticated()
        .and()
        .formLogin();
  }

  @Autowired
  public void configureGlobal(final AuthenticationManagerBuilder auth) throws Exception {
    val passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    auth.inMemoryAuthentication()
        .withUser("user").password(passwordEncoder.encode("userPassword")).roles(ROLE_USER)
        .and()
        .withUser("admin").password(passwordEncoder.encode("adminPassword"))
        .roles(ROLE_USER, ROLE_ADMIN);
  }
}
