package it.infn.sd.tokenfactory.api.metadata;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MetadataDTO {

  private final String issuer;
  private final String jwk;
  private final String authorizationEndpoint;
  private final String tokenEndpoint;
  private final String userinfoEndpoint;
  private final String jwksUri;
  private final List<String> responseTypesSupported;
  private final List<String> subjectTypesSupported;
  private final List<String> idTokenSigningAlgValuesSupported;
  private final List<String> scopesSupported;
  private final List<String> tokenEndpointAuthMethodsSupported;
  private final List<String> claimsSupported;
  private final List<String> codeChallengeMethodsSupported;
  private final List<String> grantTypesSupported;

}
