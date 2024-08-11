//package Config.jwt;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import org.springframework.stereotype.Component;
//
//import java.util.Date;
//import java.util.concurrent.TimeUnit;
//
//@Component
//public class JwtTokenUtil {
//    private String secret = "hieu123";
//
//    private Long expiration = TimeUnit.MINUTES.toMillis(3);
//
//    public String generateToken(String username, Long userId, String appName) {
//        Date now = new Date();
//        Date expiryDate = new Date(now.getTime() + expiration);
//
//        return Jwts.builder()
//                .claim("username", username)
//                .claim("userid", userId)
//                .claim("appname", appName)
//                .setIssuedAt(now)
//                .setExpiration(expiryDate)
//                .signWith(SignatureAlgorithm.HS512, secret)
//                .compact();
//    }
//
//    public String getUsernameFromToken(String token) {
//        Claims claims = Jwts.parser()
//                .setSigningKey(secret)
//                .parseClaimsJws(token)
//                .getBody();
//
//        return claims.get("username", String.class);
//    }
//
//    public Long getUserIdFromToken(String token) {
//        Claims claims = Jwts.parser()
//                .setSigningKey(secret)
//                .parseClaimsJws(token)
//                .getBody();
//
//        return claims.get("userid", Long.class);
//    }
//
//    public String getAppNameFromToken(String token) {
//        Claims claims = Jwts.parser()
//                .setSigningKey(secret)
//                .parseClaimsJws(token)
//                .getBody();
//
//        return claims.get("appname", String.class);
//    }
//
//    public boolean validateToken(String token) {
//        try {
//            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
//            return true;
//        } catch (Exception e) {
//            return false;
//        }
//    }
//}
