package it.infn.sd.tokenfactory.api.metadata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nimbusds.jose.jwk.JWKSet;

import it.infn.sd.tokenfactory.TokenFactoryProperties;

@RestController
public class MetadataEndpointController {

  @Autowired
  TokenFactoryProperties properties;

  @Autowired
  JWKSet localKeyset;

  @GetMapping(path = "/.well-known/openid-configuration", produces = MediaType.APPLICATION_JSON_VALUE)
  public MetadataDTO getMetadata() {
    return MetadataDTO.builder()
      .issuer(properties.getIssuer())
      .jwksUri(String.format("%s/jwk", properties.getIssuer()))
      .build();
  }

  @GetMapping(path = "/jwk", produces = MediaType.APPLICATION_JSON_VALUE)
  public String getJwk() {
    return localKeyset.toPublicJWKSet().toString();
  }

}