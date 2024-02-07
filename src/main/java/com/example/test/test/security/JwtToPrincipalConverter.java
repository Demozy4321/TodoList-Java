package com.example.test.test.security;

import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JwtToPrincipalConverter {

    public UserPrincipal convert(DecodedJWT jwt) {
        return new UserPrincipal(
                Long.valueOf(jwt.getSubject()),
                jwt.getClaim("email").asString(),
                extractAuthoritiesFromClaim(jwt)
        );
    }

    private List<SimpleGrantedAuthority> extractAuthoritiesFromClaim(DecodedJWT jwt) {
        var claim = jwt.getClaim("roles");

        if (claim.isNull() || claim.isMissing())
        {
            return List.of();
        }

        return claim.asList(SimpleGrantedAuthority.class);
    }
}
