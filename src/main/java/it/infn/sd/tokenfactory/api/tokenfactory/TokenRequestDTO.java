package it.infn.sd.tokenfactory.api.tokenfactory;

import java.time.Instant;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.OptBoolean;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class TokenRequestDTO {

  @JsonFormat(shape = Shape.STRING, lenient = OptBoolean.TRUE,
      pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
  private Instant nbf;

  @JsonFormat(shape = Shape.STRING, lenient = OptBoolean.TRUE,
      pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
  private Instant exp;

  private String keyId;

  private String subject;
  private String issuer;

  private List<String> audiences;

  private Map<String, String> otherClaims;

}
