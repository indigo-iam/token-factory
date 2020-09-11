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
package it.infn.sd.tokenfactory.api;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import it.infn.sd.tokenfactory.api.tokenfactory.TokenErrorDTO;

@RestControllerAdvice
public class ErrorHandlingAdvice {

  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  @ExceptionHandler({HttpRequestMethodNotSupportedException.class,
      HttpMessageNotReadableException.class})
  public TokenErrorDTO badRequests(HttpServletRequest req, Exception ex) {
    TokenErrorDTO dto = new TokenErrorDTO();
    dto.setError("bad_request");
    dto.setErrorDescription(ex.getMessage());
    return dto;
  }
}
