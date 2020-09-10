package it.infn.sd.tokenfactory.api.tokenfactory;

@FunctionalInterface
public interface TokenFactoryService {
  TokenResponseDTO createToken(TokenRequestDTO request);
}
