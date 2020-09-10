package it.infn.sd.tokenfactory.api.tokenfactory;

import org.springframework.security.core.Authentication;

@FunctionalInterface
public interface TokenFactoryService {
  TokenResponseDTO createToken(Authentication authentication, TokenRequestDTO request);
}
