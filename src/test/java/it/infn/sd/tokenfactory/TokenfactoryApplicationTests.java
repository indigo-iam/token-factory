package it.infn.sd.tokenfactory;


import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.JWTParser;

import it.infn.sd.tokenfactory.api.tokenfactory.TokenResponseDTO;

@SpringBootTest
@AutoConfigureMockMvc
@WithAnonymousUser
class TokenfactoryApplicationTests {

  @Autowired
  private MockMvc mvc;

  @Autowired
  private ObjectMapper mapper;

  @Autowired
  private TokenFactoryProperties properties;

  @Test
  void rootRedirectsToMetadata() throws Exception {
    mvc.perform(MockMvcRequestBuilders.get("/"))
      .andExpect(status().is3xxRedirection())
      .andExpect(MockMvcResultMatchers.redirectedUrl("/.well-known/openid-configuration"));
  }

  @Test
  void metadataAccessibleWithoutAuth() throws Exception {
    mvc.perform(MockMvcRequestBuilders.get("/.well-known/openid-configuration"))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.issuer", is(properties.getIssuer())));
  }

  @Test
  void keysAccessibleWithoutAuth() throws Exception {
    mvc.perform(MockMvcRequestBuilders.get("/jwk"))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.keys").exists());
  }

  @Test
  void apiRequireAuthenticatedClient() throws Exception {
    mvc.perform(MockMvcRequestBuilders.get("/api/token")).andExpect(status().isUnauthorized());
  }


  @Test
  @WithMockUser
  void apiWorksWithAuthenticatedClient() throws Exception {

    String tokenResponse =
        mvc.perform(post("/api/token").content("{}").contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.access_token").exists())
          .andReturn()
          .getResponse()
          .getContentAsString();

    TokenResponseDTO response = mapper.readValue(tokenResponse, TokenResponseDTO.class);
    JWT jwt = JWTParser.parse(response.getAccessToken());

    JWTClaimsSet claims = jwt.getJWTClaimsSet();
    
    assertThat(claims.getIssuer(), is(properties.getIssuer()));
    assertThat(claims.getSubject(), notNullValue());

  }

  @Test
  @WithMockUser
  void apiCanParseDate() throws Exception {

    String req =
        "{\"nbf\":\"2020-09-10T20:14:41+02:00\", \"exp\": \"2020-09-10T20:23:26+02:00\"}";

    String tokenResponse =
        mvc.perform(post("/api/token").content(req).contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.access_token").exists())
          .andReturn()
          .getResponse()
          .getContentAsString();

    TokenResponseDTO response = mapper.readValue(tokenResponse, TokenResponseDTO.class);
    JWT jwt = JWTParser.parse(response.getAccessToken());

    JWTClaimsSet claims = jwt.getJWTClaimsSet();
    
    assertThat(claims.getIssuer(), is(properties.getIssuer()));
    assertThat(claims.getSubject(), notNullValue());
    assertThat(claims.getNotBeforeTime(), notNullValue());
    assertThat(claims.getExpirationTime(), notNullValue());

  }



}
