package application.service;

import application.model.User;
import application.repository.TokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

/**
 * Service class for JWT operations.
 */
@Service
public class JwtService {

  private final String SECRET_KEY =
      "4bb6d1dfbafb64a681139d1586b6f1160d18159afd57c8c79136d7490630407c";
  private final TokenRepository tokenRepository;

  public JwtService(TokenRepository tokenRepository) {
    this.tokenRepository = tokenRepository;
  }

  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  /**
   * Checks if a JWT token is valid for the given user.
   *
   * @param token for authentication
   * @param user object
   * @return True if the token is valid for the user, false otherwise
   */
  public boolean isValid(String token, UserDetails user) {
    String username = extractUsername(token);

    boolean validToken =
        tokenRepository.findByToken(token).map(t -> !t.isLoggedOut()).orElse(false);

    return (username.equals(user.getUsername())) && !isTokenExpired(token) && validToken;
  }

  private boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  private Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  public <T> T extractClaim(String token, Function<Claims, T> resolver) {
    Claims claims = extractAllClaims(token);
    return resolver.apply(claims);
  }

  private Claims extractAllClaims(String token) {
    return Jwts.parser().verifyWith(getSigninKey()).build().parseSignedClaims(token).getPayload();
  }

  /**
   * Generates a JWT token for the given user.
   *
   * @param user that needs token
   * @return the generated token
   */
  public String generateToken(User user) {
    String token =
        Jwts.builder().subject(user.getUsername()).issuedAt(new Date(System.currentTimeMillis()))
            .expiration(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000))
            .signWith(getSigninKey()).compact();

    return token;
  }

  private SecretKey getSigninKey() {
    byte[] keyBytes = Decoders.BASE64URL.decode(SECRET_KEY);
    return Keys.hmacShaKeyFor(keyBytes);
  }

}
