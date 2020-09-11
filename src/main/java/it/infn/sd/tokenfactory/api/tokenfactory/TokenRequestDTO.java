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
