package com.tienda.tiendajwt.configuration;

import com.tienda.tiendajwt.service.LoginDetailsService;
import com.tienda.tiendajwt.util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private LoginDetailsService loginService;

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Obt. el token del Header del Request
        final String header = request.getHeader("Authorization");
        String jwtToken = null;

        String userName = null;

        // Validando si el token no es null.
        if (header != null) {
            // Reemplazamos el auth header.
            jwtToken = header.replace("Bearer ", "");
            System.out.println(jwtToken);

            try {
                 userName = jwtUtil.getUserNameFromToken(jwtToken);

            } catch (IllegalArgumentException e) {
                System.out.println("Unable to get JWT token");
            } catch (ExpiredJwtException e) {
                System.out.println("Jwt Token is expired");
            }
        }

        // Validamos si el subject no es null.
        if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            //Asociar el token valido al usuario y poder hacer el llamado al metodo para iniciar session.
            System.out.println(jwtUtil.getUserNameFromToken(jwtToken));
            UserDetails usuario = loginService.loadUserByUsername(userName);

            if(jwtUtil.validateToken(jwtToken, usuario)){
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());

                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}
