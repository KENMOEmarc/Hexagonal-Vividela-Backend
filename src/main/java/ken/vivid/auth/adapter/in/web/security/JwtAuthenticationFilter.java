package ken.vivid.auth.adapter.in.web.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ken.vivid.auth.application.port.out.TokenBlacklistPort;
import ken.vivid.auth.application.port.out.TokenGeneratorPort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final TokenGeneratorPort tokenGeneratorPort;
    private final TokenBlacklistPort tokenBlacklistPort;
    private final UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        extractToken(request).ifPresent(token -> {
            if (tokenBlacklistPort.isRevoked(token)) {
                chain2(); // token explicitly disconnected
                return;
            }
            tokenGeneratorPort.validateAndExtractEmail(token).ifPresent(email -> {
                UserDetails userDetails = userDetailsService.loadUserByUsername(email);
                var authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            });
        });

        chain.doFilter(request, response);
    }

    private void chain2() {
        // no-op : placeholder explicite pour la lisibilité du cas "token révoqué"
    }

    private Optional<String> extractToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return Optional.of(header.substring(7));
        }
        return Optional.empty();
    }
}

