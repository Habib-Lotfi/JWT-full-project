package habib.Login.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JWTProvider {

    private final SecretKey  key = Keys.hmacShaKeyFor(JWTConstant.JWT_SECRET.getBytes());

    public String generateToken(Authentication auth) {

        String jwt = Jwts.builder().setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() - 86400000))
                .claim("email", auth.getName())
                .signWith(key).compact();

        return jwt;
    }

    public String getEmailFromJwtToken(String jwt){

        jwt = jwt.substring(7);
        Claims claims = (Claims) Jwts.parser().verifyWith(key).build().parseSignedClaims(jwt).getBody();

        String email = String.valueOf(claims.get("email"));


        return email;
    }
}