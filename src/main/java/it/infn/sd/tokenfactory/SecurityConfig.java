package it.infn.sd.tokenfactory;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {


  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.anonymous().and().cors().and().csrf().disable();

    http.authorizeRequests()
      .mvcMatchers("/jwk")
      .permitAll()
      .mvcMatchers("/.well-known/openid-configuration")
      .permitAll()
      .mvcMatchers("/api/token")
      .permitAll();
  }

}
