package com.lgcns.hrm.cv.security;

import com.lgcns.hrm.cv.common.constants.ErrorCodes;
import com.lgcns.hrm.cv.common.constants.TokenType;
import com.lgcns.hrm.cv.common.exception.PlatformException;
import com.lgcns.hrm.cv.common.utils.JsonUtil;
import com.lgcns.hrm.cv.common.utils.ObjectUtil;
import com.lgcns.hrm.cv.entity.User;
import com.lgcns.hrm.cv.entity.Group;
import com.lgcns.hrm.cv.entity.Token;
import com.lgcns.hrm.cv.model.vo.AuthenticationVo;
import com.lgcns.hrm.cv.model.vo.UserGroupsPermissionVo;
import com.lgcns.hrm.cv.model.vo.LoginVo;
import com.lgcns.hrm.cv.repository.UserRepository;
import com.lgcns.hrm.cv.repository.GroupRepository;
import com.lgcns.hrm.cv.repository.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;

/**
 * @author pigx
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final GroupRepository groupRepository;

    @Value("${hrm-env.security.jwt.expiration}")
    private long jwtExpiration;

    public AuthenticationVo authenticate(LoginVo request) {
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Can not find user with email: " + request.getEmail()));

        if (!user.isActive()) {
            user.setActive(true);
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.getGroups().add(groupRepository.findByName("USER"));
            userRepository.saveAndFlush(user);
        }

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return new AuthenticationVo()
                .setAccessToken(jwtToken)
                .setRefreshToken(refreshToken);
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = new Token()
                .setUser(user)
                .setExpiredDate(new Date(System.currentTimeMillis() + jwtExpiration))
                .setToken(jwtToken)
                .setTokenType(TokenType.BEARER)
                .setRevoked(false);
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokensByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail != null) {
            var user = userRepository.findByEmail(userEmail)
                    .orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user.getEmail())) {
                var accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                var authResponse = new AuthenticationVo()
                        .setAccessToken(accessToken)
                        .setRefreshToken(refreshToken);
                JsonUtil.getInstance().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }

    public UserGroupsPermissionVo getGroupAndPermissionForUser() {
        var authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();
        if (ObjectUtil.isEmpty(authentication)) {
            throw new PlatformException(ErrorCodes.FORBIDDEN);
        }
        var email = authentication.getName();
        var user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Email not found"));
        var groups = user.getGroups().stream().map(Group::getName).toList();
        var permissions = user.getGroups().stream().flatMap(group -> group.getPermissions().stream()).map(permission -> permission.getComponentName() + ":" + permission.getAction()).toList();
        return new UserGroupsPermissionVo().setPermissions(permissions)
                .setGroups(groups)
                .setId(user.getId())
                .setEmail(user.getEmail())
                .setName(user.getName());
    }
}
