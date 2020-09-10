package it.infn.sd.tokenfactory;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.URL;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Configuration
@ConfigurationProperties("tokenfactory")
@Validated
public class TokenFactoryProperties {

  @NotBlank(message = "Please define an issuer")
  @URL(message = "The issuer must be a valid URL")
  String issuer;


  @NotBlank(message = "Please define a JSON keyset resource")
  String jsonKeyset;

  public String getIssuer() {
    return issuer;
  }

  public void setIssuer(String issuer) {
    this.issuer = issuer;
  }

  public String getJsonKeyset() {
    return jsonKeyset;
  }

  public void setJsonKeyset(String jsonKeyset) {
    this.jsonKeyset = jsonKeyset;
  }
}
