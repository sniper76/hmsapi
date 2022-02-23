package com.skg.hms.framework.auth;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.skg.hms.framework.dto.UserDto;
import com.skg.hms.framework.exception.JwtAllException;
import com.skg.hms.framework.properties.JwtProperties;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtUtils {

	@Autowired
	private JwtProperties jwtProperties;
	
	public String createToken(UserDto userDto) {
		return createToken(userDto, null);
	}
	
	public String createToken(UserDto userDto, String expTerm) {
		log.info("createToken jwtProperties : {}", jwtProperties.toString());
		if(expTerm == null) {
			expTerm = this.jwtProperties.getExpiredTime();
		}
		long expired = Long.parseLong(expTerm) * 86400000L;
		
		String jwtString = Jwts.builder()
				.setHeaderParam("typ", "JWT")
				.setSubject("HMS Token")
				.setExpiration(new Date(System.currentTimeMillis() + expired))
				.claim("user", userDto)
				.signWith(SignatureAlgorithm.HS256, this.jwtProperties.getKey().getBytes())
				.compact();
		return jwtString;
	}
	
	public Claims verifyToken(String token) {
		Claims jws = null;
		try {
			jws = (Claims) Jwts.parser()
					.setSigningKey(this.jwtProperties.getKey().getBytes())
					.parseClaimsJws(token)
					.getBody();
		}
		catch (ExpiredJwtException e) {
			throw new JwtAllException("Token 사용기간이 만료되었습니다.");
		}
		catch (MalformedJwtException e) {
			throw new JwtAllException("Token 형식이 맞지않습니다.");
		}
		catch (Exception e) {
			throw new JwtAllException(e.getLocalizedMessage());
		}
		return jws;
	}
	
	@SuppressWarnings("unchecked")
	public String checkRefreshToken(Claims claims) {
		Date expDate = claims.getExpiration();
		Date nowDate = new Date(System.currentTimeMillis());
		String resToken = "";
		long exp = expDate.getTime() - nowDate.getTime();
		exp /= 86400000L;
		
		if (exp < 30L) {
			Map<String, Object> userMap = (Map<String, Object>) claims.get("user", HashMap.class);
			ObjectMapper om = new ObjectMapper();
			UserDto userDto = (UserDto)om.convertValue(userMap, UserDto.class);
			resToken = createToken(userDto);
		}
		return resToken;
	}
}
