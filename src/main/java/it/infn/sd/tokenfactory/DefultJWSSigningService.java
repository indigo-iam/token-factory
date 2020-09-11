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
package it.infn.sd.tokenfactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.ECDSASigner;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.jwk.ECKey;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jwt.SignedJWT;

@Service
public class DefultJWSSigningService implements JWTSigningService {

  private final TokenFactoryProperties properties;
  private final Map<String, JWSSigner> signers = new HashMap<>();
  private final Map<String, JWSAlgorithm> algos = new HashMap<>();

  private final JWSAlgorithm RSA_DEFAULT_ALGO = JWSAlgorithm.RS256;
  private final JWSAlgorithm EC_DEFAULT_ALGO = JWSAlgorithm.ES256;

  @Autowired
  public DefultJWSSigningService(TokenFactoryProperties properties, JWKSet keySet)
      throws JOSEException {
    this.properties = properties;


    for (JWK key : keySet.getKeys()) {
      if (key instanceof RSAKey && key.isPrivate()) {

        RSAKey rsaKey = (RSAKey) key;

        RSASSASigner signer = new RSASSASigner(rsaKey);
        signers.put(rsaKey.getKeyID(), signer);
        algos.put(rsaKey.getKeyID(),
            Optional.ofNullable(JWSAlgorithm.parse(rsaKey.getAlgorithm().getName()))
              .orElse(RSA_DEFAULT_ALGO));

      } else if (key instanceof ECKey && key.isPrivate()) {

        ECKey ecKey = (ECKey) key;
        ECDSASigner signer = new ECDSASigner(ecKey);
        signers.put(ecKey.getKeyID(), signer);
        algos.put(ecKey.getKeyID(),
            Optional.ofNullable(JWSAlgorithm.parse(ecKey.getAlgorithm().getName()))
              .orElse(EC_DEFAULT_ALGO));

      }
    }
  }

  @Override
  public JWSSigner getDefaultSigner() {
    return signers.get(properties.getDefaultKeyId());
  }

  @Override
  public JWSSigner getSignerForKey(String keyId) {
    return signers.get(keyId);
  }


  @Override
  public void signJwt(SignedJWT jwt) throws JOSEException {
    jwt.sign(getDefaultSigner());
  }

  @Override
  public void signJwt(SignedJWT jwt, String keyId) throws JOSEException {
    jwt.sign(getSignerForKey(keyId));
  }

  @Override
  public JWSAlgorithm getSigningAlgorithmForKey(String keyId) {
    return algos.get(keyId);
  }

  @Override
  public JWSAlgorithm getDefaultSigningAlgorithm() {

    return algos.get(properties.getDefaultKeyId());
  }

}
