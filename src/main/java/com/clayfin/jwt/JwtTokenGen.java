package com.clayfin.jwt;

import java.io.Serializable;
import java.util.Date;
import java.util.function.Function;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.clayfin.entity.MyUserDetails;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Component
public class JwtTokenGen implements Serializable{
	
	long tokenValidity = 10*60*60;
	@Value("${jwt.secretMsg}")
	private String msg;
	
	
	
	public String getUsernameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}
	
	public String getRoleFromToken(String token) {
		
		return (String) getClaimFromToken(token,Claims->Claims.get("roles"));
	}
	
	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}
	
	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(msg).parseClaimsJws(token).getBody();
	}
	
	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}
	
	private Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}
	
	
	public String generateToken(MyUserDetails user) {
        
        return Jwts.builder()
                .setIssuer( "my Application" )
                .setSubject(user.getUsername())
                .claim("roles", user.getAuthorities())
                .setIssuedAt(new Date())
                .setExpiration(generateExpirationDate())
                .signWith( io.jsonwebtoken.SignatureAlgorithm.HS512, msg )
                .compact();
  }
	
	private Date generateExpirationDate() {
		
		return new Date(new Date().getTime() + tokenValidity * 1000);
	}

//	public String generateJwtToken(MyUserDetails myUserDetails) {
//		Map<String, Object> claims = new HashMap<>();
//		return Jwts.builder().setClaims(claims).setSubject(myUserDetails.getUsername()).setIssuedAt(new Date(System.currentTimeMillis()+ tokenValidity * 1000))
//				.signWith(SignatureAlgorithm.HS512, msg).compact();
//		
//	}
	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = getUsernameFromToken(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
	
	public String getToken(HttpServletRequest request) {
		String authHeader = getAuthHeaderFromHeader(request);
		if(authHeader!=null && authHeader.startsWith("Bearer ")) {
			return authHeader.substring(7);
		}
		return null;
	}
	
	public String getAuthHeaderFromHeader(HttpServletRequest request) {
		return request.getHeader("Authorization");
	}
	
	
		
}
