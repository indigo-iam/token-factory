package it.infn.sd.tokenfactory.api.tokenfactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenFactoryController {

  private final TokenFactoryService service;

  @Autowired
  public TokenFactoryController(TokenFactoryService service) {
    this.service = service;
  }


  @PostMapping(path = "/api/token", consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public TokenResponseDTO getAccessToken(@RequestBody TokenRequestDTO request) {
    return service.createToken(request);
  }



}
