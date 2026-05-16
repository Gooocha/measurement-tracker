package org.example.measurementtrackerclean.config;
import jakarta.servlet.*;import jakarta.servlet.http.*;import lombok.RequiredArgsConstructor;import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;import org.springframework.security.core.context.SecurityContextHolder;import org.springframework.security.core.userdetails.UserDetailsService;import org.springframework.stereotype.Component;import org.springframework.web.filter.OncePerRequestFilter;import java.io.IOException;
@Component @RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
 private final JwtService jwt; private final UserDetailsService uds;
 @Override protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws ServletException, IOException {String h=req.getHeader("Authorization"); if(h!=null&&h.startsWith("Bearer ")){String t=h.substring(7); try{String u=jwt.username(t); var ud=uds.loadUserByUsername(u); SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(ud,null,ud.getAuthorities()));}catch(Exception ignored){}} chain.doFilter(req,res);} }
