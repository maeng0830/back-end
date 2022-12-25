package com.project.devgram.oauth2.token;

import com.project.devgram.oauth2.redis.RedisService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.BasicJsonParser;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Duration;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class TokenService {


    @Value("${jwt.secretKey}")
    private String secretKey;

    private final RedisService redisService;

    private final long accessTokenValidTime = Duration.ofMinutes(30).toMillis();
    private final long refreshTokenValidTime = Duration.ofDays(1).toSeconds();

    @PostConstruct // 빈등록 후 초기화를 시켜주는 어노테이션
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }


    public Token generateToken(String username, String role) {


        String[] tokenCheck = {"ATK", "RTK"};

        String token = typoToken(username, role, tokenCheck[0], accessTokenValidTime);
        String refreshToken = typoToken(username, role, tokenCheck[1], refreshTokenValidTime);

        redisService.createRefresh(username, refreshToken, tokenCheck[1], refreshTokenValidTime);
        return Token.builder()
                .token(token)
                .refreshToken(refreshToken)
                .build();
    }


    private String typoToken(String username, String role, String type, long period) {


        Claims claims = Jwts.claims()
                .setId(username)
                .setSubject(type);
        claims.put("role", role);

        return Jwts.builder()
                .setClaims(claims)
                .setHeaderParam("jwt", "jwt")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + period))
                .signWith(getSignKey(secretKey), SignatureAlgorithm.HS256)
                .compact();


    }


    public boolean validateToken(String token) {
        return this.getClaims(token) != null;
    }

    private Jws<Claims> getClaims(String token) {

        try {
            return Jwts.parserBuilder()
                    .setSigningKey(secretKey.getBytes())
                    .build()
                    .parseClaimsJws(token);

        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
            throw ex;
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
            throw ex;
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
            throw ex;
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
            throw ex;
        } catch (NullPointerException ex) {
            log.error("JWT RefreshToken is empty");
            throw ex;
        }

    }

    public String getUsername(final String token) throws JwtException {

        final String payloadJWT = token.split("\\.")[1];
        Base64.Decoder decoder = Base64.getUrlDecoder();

        final String payload = new String(decoder.decode(payloadJWT));
        BasicJsonParser jsonParser = new BasicJsonParser();
        Map<String, Object> jsonArray = jsonParser.parseMap(payload);

        if (!jsonArray.containsKey("jti") || !jsonArray.get("sub").toString().equals("ATK")) {

            throw new JwtException("INVALIDATED_TOKEN_ERROR");
        }
        log.info("토큰 파싱 확인: " + jsonArray.get("jti").toString());

        return jsonArray.get("jti").toString();

    }

    private Key getSignKey(String secretKey) {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String getTokenCheck(String token) {

        return Jwts.parserBuilder().setSigningKey(secretKey.getBytes())
                .build().parseClaimsJws(token).getBody().getSubject();
    }

    public String getTokenGrant(String token) {

        return (String) Jwts.parserBuilder().setSigningKey(secretKey.getBytes())
                .build().parseClaimsJws(token).getBody().get("role");
    }


    public boolean getListCheck(String accessToken) {

        return redisService.getBlackToken(accessToken);
    }
}
