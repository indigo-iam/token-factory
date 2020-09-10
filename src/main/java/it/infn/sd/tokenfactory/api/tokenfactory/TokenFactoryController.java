package it.infn.sd.tokenfactory.api.tokenfactory;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
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
  public TokenResponseDTO getAccessToken(@RequestBody TokenRequestDTO request,
      Authentication authentication) {
    return service.createToken(authentication, request);
  }

  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  @ExceptionHandler(TokenCreationError.class)
  public TokenErrorDTO accountNotFoundError(HttpServletRequest req, Exception ex) {
    TokenErrorDTO dto = new TokenErrorDTO();
    dto.setError("token_creation_error");
    dto.setErrorDescription(ex.getMessage());
    return dto;
  }


}
