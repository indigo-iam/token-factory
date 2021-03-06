/**
 * Copyright (c) Istituto Nazionale di Fisica Nucleare (INFN). 2016-2020
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package it.infn.sd.tokenfactory;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public InMemoryUserDetailsManager udm(TokenFactoryProperties properties,
      PasswordEncoder encoder) {

    Set<UserDetails> users = properties.getClients().stream().map(c -> {
      return User.withUsername(c.getUsername())
        .password(encoder.encode(c.getPassword()))
        .roles("USER")
        .build();
    }).collect(Collectors.toSet());

    return new InMemoryUserDetailsManager(users);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.anonymous().and().cors().and().csrf().disable();

    http.authorizeRequests()
      .mvcMatchers("/jwk")
      .permitAll()
      .mvcMatchers("/.well-known/openid-configuration")
      .permitAll();

    http.requestMatchers()
      .mvcMatchers("/api/**")
      .and()
      .httpBasic()
      .and()
      .authorizeRequests()
      .anyRequest()
      .fullyAuthenticated();

  }

}
