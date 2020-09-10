package it.infn.sd.tokenfactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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
  private final JWSAlgorithm defaultJWSAlgo;

  private final JWSAlgorithm rsaAlgo = JWSAlgorithm.RS256;
  private final JWSAlgorithm ecAlgo = JWSAlgorithm.ES256;

  @Autowired
  public DefultJWSSigningService(TokenFactoryProperties properties, JWKSet keySet)
      throws JOSEException {
    this.properties = properties;


    for (JWK key : keySet.getKeys()) {
      if (key instanceof RSAKey && key.isPrivate()) {

        RSASSASigner signer = new RSASSASigner((RSAKey) key);
        signers.put(key.getKeyID(), signer);

      } else if (key instanceof ECKey && key.isPrivate()) {

        ECDSASigner signer = new ECDSASigner((ECKey) key);
        signers.put(key.getKeyID(), signer);
      }
    }

    defaultJWSAlgo = JWSAlgorithm.RS256;
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
  public JWSAlgorithm getDefaultSigningAlgorithm() {
    return defaultJWSAlgo;
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
    JWSSigner signer = signers.get(keyId);

    if (Objects.isNull(signer)) {
      return null;
    }

    if (signer instanceof RSASSASigner) {
      return rsaAlgo;
    }

    return ecAlgo;
  }

}
