package it.infn.sd.tokenfactory.api.tokenfactory;

import java.time.Instant;
import java.util.Map;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class TokenRequestDTO {

  private Instant startTime;
  private Instant endTime;

  private String keyIdentifier;

  private Map<String, String> claims;

}
