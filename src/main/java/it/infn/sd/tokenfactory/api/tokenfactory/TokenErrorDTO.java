package it.infn.sd.tokenfactory.api.tokenfactory;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class TokenErrorDTO {

  String error;
  String errorDescription;

}
