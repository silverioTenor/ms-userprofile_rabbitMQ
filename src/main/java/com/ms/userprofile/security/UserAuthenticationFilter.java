package com.ms.userprofile.security;

import com.ms.userprofile.models.UserDetailsImplModel;
import com.ms.userprofile.models.UserProfileModel;
import com.ms.userprofile.repositories.UserProfileRepository;
import com.ms.userprofile.services.JwtTokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

import static com.ms.userprofile.utils.HttpConstants.ENDPOINTS_WITH_NOT_REQUIRED_AUTH;

@Component
public class UserAuthenticationFilter extends OncePerRequestFilter {

    final JwtTokenService jwtTokenService;
    final UserProfileRepository userProfileRepository;

    public UserAuthenticationFilter(JwtTokenService jwtTokenService, UserProfileRepository userProfileRepository) {
        this.jwtTokenService = jwtTokenService;
        this.userProfileRepository = userProfileRepository;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        if (verifyPublicEndpoints(request)) {
            String token = recoveryToken(request);

            if (Objects.nonNull(token)) {
                String subject = jwtTokenService.getSubject(token);
                UserProfileModel userProfileModel = userProfileRepository.findByEmail(subject).get();
                UserDetailsImplModel userDetailsModel = new UserDetailsImplModel(userProfileModel);
                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        userDetailsModel.getUsername(),
                        null,
                        userDetailsModel.getAuthorities()
                );

                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                throw new RuntimeException("Token not found.");
            }
        }

        filterChain.doFilter(request, response);
    }

    private boolean verifyPublicEndpoints(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        return Arrays.asList(ENDPOINTS_WITH_NOT_REQUIRED_AUTH).contains(requestURI);
    }

    private String recoveryToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (Objects.nonNull(authorizationHeader)) {
            return authorizationHeader.replace("Bearer ", "");
        }

        return null;
    }
}
