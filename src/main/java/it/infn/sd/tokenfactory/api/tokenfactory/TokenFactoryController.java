/**
 * Copyright (c) Istituto Nazionale di Fisica Nucleare (INFN). 2016-2020
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
