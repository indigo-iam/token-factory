package it.infn.sd.tokenfactory;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.URL;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Configuration
@ConfigurationProperties("tokenfactory")
@Validated
public class TokenFactoryProperties {

  @Valid
  public static class ClientProperties {

    String username;
    String password;

    public String getUsername() {
      return username;
    }

    public void setUsername(String username) {
      this.username = username;
    }

    public String getPassword() {
      return password;
    }

    public void setPassword(String password) {
      this.password = password;
    }

  }

  @NotBlank(message = "Please define an issuer")
  @URL(message = "The issuer must be a valid URL")
  String issuer;


  @NotBlank(message = "Please define a JSON keyset resource")
  String jsonKeyset;

  @NotBlank(message = "Please define a default key id")
  String defaultKeyId;

  @Size(min = 1, message = "Please define credentials for at least a client")
  List<ClientProperties> clients = new ArrayList<>();


  public String getIssuer() {
    return issuer;
  }

  public void setIssuer(String issuer) {
    this.issuer = issuer;
  }

  public String getJsonKeyset() {
    return jsonKeyset;
  }

  public String getDefaultKeyId() {
    return defaultKeyId;
  }

  public void setDefaultKeyId(String defaultKeyId) {
    this.defaultKeyId = defaultKeyId;
  }

  public void setJsonKeyset(String jsonKeyset) {
    this.jsonKeyset = jsonKeyset;
  }

  public List<ClientProperties> getClients() {
    return clients;
  }

  public void setClients(List<ClientProperties> clients) {
    this.clients = clients;
  }
}
