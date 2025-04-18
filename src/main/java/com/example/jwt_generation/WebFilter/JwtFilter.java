package com.example.jwt_generation.WebFilter;

import com.example.jwt_generation.Service.JwtService;
import com.example.jwt_generation.Service.MyUserDetailsImp;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;



@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final ApplicationContext context;

    public JwtFilter(JwtService jwtService, ApplicationContext context) {
        this.jwtService = jwtService;
        this.context = context;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String header = request.getHeader("Authorization");
        String username = null;
        String token  = null;

        if(header!=null && header.startsWith("Bearer ")){

            token = header.substring(7);
            username = jwtService.getUsername(token);
            System.out.println("filter done: "+username);
        }

        if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null){

            System.out.println("entry point one1");

            UserDetails userDetails = context.getBean(MyUserDetailsImp.class).loadUserByUsername(username);


            if(jwtService.validateToken(token,userDetails)) {

                System.out.println("entry point 2");
                UsernamePasswordAuthenticationToken filter2Token =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                filter2Token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(filter2Token);
                System.out.println(SecurityContextHolder.getContext().getAuthentication().getName());
            }
        }

        filterChain.doFilter(request,response);

    }
}
