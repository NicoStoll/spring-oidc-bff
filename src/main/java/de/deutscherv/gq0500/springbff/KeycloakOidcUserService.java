package de.deutscherv.gq0500.springbff;

import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
public class KeycloakOidcUserService extends OidcUserService {

    @Value("${spring.security.oauth2.client.registration.keycloak.client-id}")
    private String clientId;

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        OidcUser oidcUser = super.loadUser(userRequest);

        // Extract Access Token
        String accessTokenValue = userRequest.getAccessToken().getTokenValue();
        Set<GrantedAuthority> mappedAuthorities = new HashSet<>(oidcUser.getAuthorities());

        try {
            // Parse roles from the Access Token JWT
            JWT jwt = JWTParser.parse(accessTokenValue);
            Map<String, Object> claims = jwt.getJWTClaimsSet().getClaims();

            // Extract "resource_access" (Client Roles)
            if (claims.get("resource_access") instanceof Map<?, ?> resourceAccess) {
                if (resourceAccess.get(clientId) instanceof Map<?, ?> clientAccess) {

                    if (clientAccess.get("roles") instanceof Collection<?> roles) {
                        roles.forEach(role ->
                                mappedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + role))
                        );
                    }
                }
            }

            // Extract "realm_access" (Realm Roles)
            if (claims.get("realm_access") instanceof Map<?, ?> realmAccess) {
                if (realmAccess.get("roles") instanceof Collection<?> roles) {
                    roles.forEach(role ->
                            mappedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + role))
                    );
                }
            }
        } catch (Exception e) {
            throw new OAuth2AuthenticationException("Failed to parse access token roles");
        }

        return new DefaultOidcUser(mappedAuthorities, oidcUser.getIdToken(), oidcUser.getUserInfo());
    }
}
