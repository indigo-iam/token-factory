package it.infn.sd.tokenfactory.api.tokenfactory;

public class TokenCreationError extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public TokenCreationError(String message, Throwable cause) {
    super(message, cause);
  }


}
