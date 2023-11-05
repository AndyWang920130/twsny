package com.tswny.init.util;

import com.tswny.init.constants.CommonConstants;
import com.tswny.init.service.PersonService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.bouncycastle.jcajce.BCFKSLoadStoreParameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JwtTokenUtil {
    private final Logger log = LoggerFactory.getLogger(JwtTokenUtil.class);

    private static final String AUTHORITIES_KEY = "auth";
    private static final String PRIMARY_KEY = "HfkjksFKLJISJFKLFKWJFQFIQWIOFJQOFFQGGSDGFFJIQOEUFIEJFIOQWEFHFQOK5FKOIQWUFFEFE423FIQEOFJHUEWHFKASKDLQWJIFSJDJKFHJIJWO";

    private SecretKey key;
    private final static JwtTokenUtil instance = new JwtTokenUtil();

    private JwtTokenUtil() {
        byte[] keyBytes = PRIMARY_KEY.getBytes(StandardCharsets.UTF_8);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public static JwtTokenUtil getInstance() {
        return instance;
    }

    public String createToken(Authentication authentication, boolean rememberMe) {
        String authorities = authentication.getPrincipal() + ";" + Instant.now();
        long now = (new Date()).getTime();
        Date validity;
        if (rememberMe) {
            validity = new Date(now + CommonConstants.ONE_DAY_MILLISECONDS);
        } else {
            validity = new Date(now + CommonConstants.ONE_WEEK_MILLISECONDS);
        }

        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(validity)
                .compact();
    }


    public boolean validateToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(authToken);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            log.error("Invalid JWT token trace.", e);
        }
        return false;
    }

    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        String[] split = claims.get(AUTHORITIES_KEY).toString().split(";");
        String login = split[0];
        List<GrantedAuthority> authorities = new ArrayList<>();
        User principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }
}
