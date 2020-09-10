package it.infn.sd.tokenfactory;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jwt.SignedJWT;

public interface JWTSigningService {

  JWSSigner getDefaultSigner();

  JWSSigner getSignerForKey(String keyId);

  public JWSAlgorithm getDefaultSigningAlgorithm();

  public JWSAlgorithm getSigningAlgorithmForKey(String keyId);

  void signJwt(SignedJWT jwt) throws JOSEException;

  public void signJwt(SignedJWT jwt, String keyId) throws JOSEException;

}
