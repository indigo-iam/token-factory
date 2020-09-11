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

import static java.util.Objects.isNull;
import static java.util.Optional.ofNullable;

import java.time.Clock;
import java.util.Date;
import java.util.UUID;
import java.util.function.Supplier;

import org.apache.logging.log4j.util.Strings;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import it.infn.sd.tokenfactory.JWTSigningService;
import it.infn.sd.tokenfactory.TokenFactoryProperties;

@Service
public class DefaultTokenFactoryService implements TokenFactoryService {

  final Clock clock;

  final TokenFactoryProperties properties;
  final JWTSigningService signingService;

  public DefaultTokenFactoryService(Clock clock, TokenFactoryProperties properties,
      JWTSigningService signer) {
    this.properties = properties;
    this.clock = clock;
    this.signingService = signer;
  }

  private Supplier<String> randomUUID() {
    return () -> UUID.randomUUID().toString();
  }

  @Override
  public TokenResponseDTO createToken(Authentication authentication, TokenRequestDTO request) {

    JWTClaimsSet.Builder claims = new JWTClaimsSet.Builder();

    String issuer = ofNullable(request.getIssuer()).orElseGet(properties::getIssuer);
    if (!issuer.isEmpty()) {
      claims.issuer(issuer);
    }

    String subject = ofNullable(request.getSubject()).orElseGet(randomUUID());

    if (!subject.isEmpty()) {
      claims.subject(subject);
    }

    if (!isNull(request.getAudiences()) && !request.getAudiences().isEmpty()) {
      claims.audience(request.getAudiences());
    }

    if (!isNull(request.getOtherClaims())) {
      request.getOtherClaims().forEach((k, v) -> claims.claim(k, v));
    }


    if (!isNull(request.getNbf())) {
      claims.notBeforeTime(Date.from(request.getNbf()));
    }

    if (!isNull(request.getExp())) {
      claims.expirationTime(Date.from(request.getExp()));
    }

    String keyId = properties.getDefaultKeyId();

    if (Strings.isNotBlank(request.getKeyId())) {
      keyId = request.getKeyId();
    }

    JWSHeader header =
        new JWSHeader.Builder(signingService.getSigningAlgorithmForKey(keyId))
      .keyID(keyId)
      .build();

    SignedJWT signedJwt = new SignedJWT(header, claims.build());

    try {
      signingService.signJwt(signedJwt, keyId);
      return new TokenResponseDTO(signedJwt.serialize());
    } catch (JOSEException e) {
      throw new TokenCreationError(e.getMessage(), e);
    }
  }
}
