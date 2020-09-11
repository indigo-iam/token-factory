package it.infn.sd.tokenfactory.api.metadata;

import static it.infn.sd.tokenfactory.api.metadata.MetadataEndpointController.OPENID_WELL_KNOWN_URL;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RootController {

  @GetMapping(path = {"/"})
  public String getRoot() {
    return String.format("redirect:%s", OPENID_WELL_KNOWN_URL);
  }

}
