package com.lgcns.hrm.cv.security;

import com.lgcns.hrm.cv.annotation.ConditionOnJwtEnabled;
import com.lgcns.hrm.cv.repository.TokenRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Date;

/**
 * @author pigx
 */
@Component
@RequiredArgsConstructor
@ConditionOnJwtEnabled
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    private final TokenRepository tokenRepository;

    protected final String API_KEY_HEADER = "X-API-KEY";
    protected final String VALID_API_KEY = "asf5558-hdhf41-dh55hd";
    protected final String[] API_PATH = {
            "/hrm/api/candidate",
            "/hrm/api/candidate/emails",
            "/hrm/api/candidate/upload-cv",
            "/hrm/api/sys-param/type/APPLY_POSITION",
            "/hrm/api/sys-param/type/CV_TYPE"
    };

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        if (request.getHeader(API_KEY_HEADER) != null && VALID_API_KEY.equals(request.getHeader(API_KEY_HEADER))) {
            String uri = request.getRequestURI();
            for (String path : API_PATH) {
                if (path.equals(uri)) {
                    SecurityContext context = SecurityContextHolder.getContext();
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            null,
                            null,
                            AuthorityUtils.NO_AUTHORITIES
                    );
                    authToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request)
                    );
                    context.setAuthentication(authToken);
                    SecurityContextHolder.setContext(context);
                    filterChain.doFilter(request, response);
                    return;
                }
            }
        }

        final var authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        jwt = authHeader.substring(7);
        userEmail = jwtService.extractUsername(jwt);
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            var userDetails = this.userDetailsService.loadUserByUsername(userEmail);
            var isTokenValid = tokenRepository.findByToken(jwt)
                    .map(t -> !(t.getExpiredDate().after(new Date(System.currentTimeMillis()))) && !t.isRevoked())
                    .orElse(false);
            if (jwtService.isTokenValid(jwt, userDetails.getUsername()) && Boolean.TRUE.equals(isTokenValid)) {
                SecurityContext context = SecurityContextHolder.getContext();
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                context.setAuthentication(authToken);
                SecurityContextHolder.setContext(context);
            }
        }
        filterChain.doFilter(request, response);
    }
}