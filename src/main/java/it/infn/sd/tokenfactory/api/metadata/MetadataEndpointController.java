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
