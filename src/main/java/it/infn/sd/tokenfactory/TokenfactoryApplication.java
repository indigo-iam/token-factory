package it.infn.sd.tokenfactory;

import java.io.IOException;
import java.text.ParseException;
import java.time.Clock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import com.nimbusds.jose.jwk.JWKSet;

@SpringBootApplication
public class TokenfactoryApplication {

  public static void main(String[] args) {
    SpringApplication.run(TokenfactoryApplication.class, args);
  }

  @Bean
  JWKSet localKeyset(ResourceLoader resourceLoader, TokenFactoryProperties properties)
      throws IOException, ParseException {

    Resource keysetResource = resourceLoader.getResource(properties.getJsonKeyset());
    return JWKSet.load(keysetResource.getInputStream());
  }


  @Bean
  Clock clock() {
    return Clock.systemDefaultZone();
  }
}
