package org.example.measurementtrackerclean.config;
import io.jsonwebtoken.Jwts;import io.jsonwebtoken.security.Keys;import org.springframework.beans.factory.annotation.Value;import org.springframework.stereotype.Service;
import javax.crypto.SecretKey;import java.nio.charset.StandardCharsets;import java.util.Date;
@Service
public class JwtService {
 @Value("${app.jwt.secret:01234567890123456789012345678901}") private String secret;
 public String generate(String username){SecretKey k=Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)); return Jwts.builder().subject(username).issuedAt(new Date()).expiration(new Date(System.currentTimeMillis()+86400000)).signWith(k).compact();}
 public String username(String token){SecretKey k=Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)); return Jwts.parser().verifyWith(k).build().parseSignedClaims(token).getPayload().getSubject();}
}
