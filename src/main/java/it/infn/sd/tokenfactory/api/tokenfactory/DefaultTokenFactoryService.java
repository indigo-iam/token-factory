package it.infn.sd.tokenfactory.api.tokenfactory;

import java.time.Clock;

import org.springframework.stereotype.Service;

import com.nimbusds.jose.jwk.JWKSet;

@Service
public class DefaultTokenFactoryService implements TokenFactoryService {

  final Clock clock;
  final JWKSet keySet;

  public DefaultTokenFactoryService(Clock clock, JWKSet keys) {
    this.clock = clock;
    this.keySet = keys;
  }

  @Override
  public TokenResponseDTO createToken(TokenRequestDTO request) {
    return null;
  }

}
