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
package it.infn.sd.tokenfactory.api.metadata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nimbusds.jose.jwk.JWKSet;

import it.infn.sd.tokenfactory.TokenFactoryProperties;

@RestController
public class MetadataEndpointController {

  public static final String OPENID_WELL_KNOWN_URL = "/.well-known/openid-configuration";
  public static final String JWK_URL = "/jwk";

  @Autowired
  TokenFactoryProperties properties;

  @Autowired
  JWKSet localKeyset;

  @GetMapping(path = OPENID_WELL_KNOWN_URL, produces = MediaType.APPLICATION_JSON_VALUE)
  public MetadataDTO getMetadata() {
    return MetadataDTO.builder()
      .issuer(properties.getIssuer())
      .jwksUri(String.format("%s%s", properties.getIssuer(), JWK_URL))
      .build();
  }

  @GetMapping(path = JWK_URL, produces = MediaType.APPLICATION_JSON_VALUE)
  public String getJwk() {
    return localKeyset.toPublicJWKSet().toString();
  }

}
