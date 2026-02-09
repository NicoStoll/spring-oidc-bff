package de.deutscherv.gq0500.springbff.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class DataController {

    @Autowired
    private ContentService contentService;

    @GetMapping("/data")
    @PreAuthorize("hasRole('ROLE_user')")
    public String getData(
            @RegisteredOAuth2AuthorizedClient("keycloak")OAuth2AuthorizedClient authorizedClient,
            Authentication auth
    ) {

        System.out.println(auth.getAuthorities());
        //TOKEN: this is the access token from the Servers memory
        System.out.println(authorizedClient.getAccessToken().getTokenValue());

        return "Hello from Spring Boot Backend!";
    }

    @GetMapping("/messages")
    @PreAuthorize("hasRole('ROLE_user')")
    public ResponseEntity<List<ContentDto>> getMessages() {
        return ResponseEntity.ok(contentService.getMessages());
    }

    @PostMapping("/messages")
    @PreAuthorize("hasRole('ROLE_admin')")
    public ResponseEntity<ContentDto> addMessage(@RequestBody ContentDto contentDto) {
        return ResponseEntity.ok(contentService.createMessage(contentDto));
    }

}
