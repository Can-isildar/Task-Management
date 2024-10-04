package com.task_management.jwt;

import com.task_management.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Service
public class JwtRequestFilter extends OncePerRequestFilter {


    private final UserService userService;

    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    @Lazy
    public JwtRequestFilter(UserService userService, JwtTokenUtil jwtTokenUtil) {
        this.userService = userService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    // Bu metod, her HTTP isteği geldiğinde çalışır ve JWT token doğrulama işlemini gerçekleştirir.
    //    doFilterInternal(): Bu metot, gelen her HTTP isteği üzerinde çalışır. Her istek geldiğinde JWT doğrulama işlemi yapılır.
    //    HttpServletRequest request: Gelen isteği temsil eder. Bu istekteki Authorization başlığından JWT token alınır.
    //    HttpServletResponse response: Yanıtı temsil eder.
    //    FilterChain filterChain: Filtre zincirini temsil eder. Doğrulama işlemi tamamlandıktan sonra, isteği zincirin bir sonraki filtresine iletmek için kullanılır.
    @Override
    protected void doFilterInternal(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain) throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization");

        String jwt = null;
        String username = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            username = jwtTokenUtil.extractUsername(jwt);
        }

    // UsernamePasswordAuthenticationToken: Bu nesne, Spring Security'de bir kullanıcının kimlik doğrulaması yapılmış olduğunu gösterir.
    // Kullanıcı bilgileri (userDetails), yetkileri (rolleri) ve kimlik doğrulaması için kullanılır.
    // SecurityContextHolder: Kullanıcı doğrulandıktan sonra, bu doğrulama bilgisi (authentication) SecurityContextHolder içine yerleştirilir. Bu, Spring Security'nin bu kullanıcının kimlik doğrulaması yapıldığını anlamasını sağlar ve sonraki işlemlerde kullanıcıya yetkilerini verir.

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userService.loadUserByUsername(username);
            if (jwtTokenUtil.validateToken(jwt, userDetails)) {

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
