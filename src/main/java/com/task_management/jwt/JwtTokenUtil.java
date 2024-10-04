package com.task_management.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenUtil {

    private final String SECRET_KEY = Base64.getEncoder().encodeToString(
            Keys.secretKeyFor(SignatureAlgorithm.HS256).getEncoded()
    ); // Kendi gizli anahtarınızı burada belirtin

    // Token'dan kullanıcı adını almak için kullanılır
    //validateToken fonksiyonunda kullanılır.
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Bu genel bir yardımcı metottur. Bir token'dan herhangi bir claim (iddia) almak için kullanılır.
    // Token içindeki belirli bir bilgiyi almak istediğinizde (extractUsername, extractExpiration gibi).
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Bu metot, JWT'nin tamamını çözer ve içindeki claims'leri (iddiaları) çıkartır. Yani token'in içinde saklanan tüm bilgileri (subject, expiration gibi) elde eder.
    // Nerede Kullanılır: extractClaim metodu aracılığıyla çeşitli bilgileri almak için kullanılır.
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    // Bu metot, token'in geçerlilik süresini almak için kullanılır. Token'in ne zaman süresinin dolacağını kontrol eder.
    // Nerede Kullanılır: isTokenExpired fonksiyonunda kullanılır, ayrıca token süresi dolduğunda bunu kullanıcıya bildirmek veya token'in geçersiz olduğunu anlamak için kullanılır.
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Bu metot, token'in süresinin dolup dolmadığını kontrol eder. Token'in geçerlilik süresine bakar ve eğer süresi dolmuşsa true, dolmamışsa false döner.
    // Nerede Kullanılır: validateToken fonksiyonu içinde token geçerliliğini kontrol etmek için kullanılır.
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Kullanıcı detaylarından token oluşturmak
    // Nerede Kullanılır: Kullanıcı giriş yaptıktan sonra, ona JWT token oluşturup döndürmek için kullanılır. Örneğin, login API'sinde kullanıcının başarıyla giriş yapması sonrasında bu metot ile token oluşturur.
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername());
    }

    // Bu metot, bir JWT token oluşturur. Kullanıcının detaylarını (kullanıcı adı) alır ve bu bilgilerle token oluşturur.
    // claims ile JWT token'i oluşturur. Token'in süresi (10 saat) de burada belirtilir.
    // Nerede Kullanılır: generateToken metodunda, yani kullanıcıya token döndürülürken kullanılır.
    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 saatlik geçerlilik süresi
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    // Bu metot, bir JWT token'in geçerli olup olmadığını kontrol eder. Token'daki kullanıcı adı ile UserDetails'daki kullanıcı adı eşleşiyor mu ve token süresi dolmuş mu diye bakar.
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}

