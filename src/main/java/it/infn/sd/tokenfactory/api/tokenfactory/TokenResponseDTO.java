package it.infn.sd.tokenfactory.api.tokenfactory;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TokenResponseDTO {

  private final String accessToken;
}
