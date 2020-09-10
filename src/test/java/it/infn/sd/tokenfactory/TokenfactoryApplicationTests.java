package it.infn.sd.tokenfactory;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
@WithAnonymousUser
class TokenfactoryApplicationTests {

	@Autowired
	MockMvc mvc;

    @Autowired
    TokenFactoryProperties properties;

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

}
