package de.deutscherv.gq0500.springbff;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;

import java.util.function.Supplier;

public class SpaCsrfTokenRequestHandler extends CsrfTokenRequestAttributeHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, Supplier<CsrfToken> csrfToken) {
        // erzwingen, dass das Token-Repository das Cookie IMMER setzt,
        super.handle(request, response, csrfToken);
    }

    @Override
    public String resolveCsrfTokenValue(HttpServletRequest request, CsrfToken csrfToken) {
        // Angular schickt den Token im Header "X-XSRF-TOKEN"
        String headerValue = request.getHeader(csrfToken.getHeaderName());
        return (headerValue != null) ? headerValue : super.resolveCsrfTokenValue(request, csrfToken);
    }
}
