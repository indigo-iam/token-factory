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
