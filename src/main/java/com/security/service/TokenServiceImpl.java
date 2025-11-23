package com.security.service;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;

@Service
public class TokenServiceImpl implements TokenService {

	@Override
	public String generateToken(String username) {

		PrivateKey privatekey = null;
		try {
			privatekey = getPrivateKey();
		} catch (Exception e) {
			e.printStackTrace();
		}

		DateTime iat = DateTime.now();
		DateTime exp = iat.plusHours(1);

		return Jwts.builder().subject(username).claims(buildClaim()).issuedAt(iat.toDate()).expiration(exp.toDate())
				.signWith(privatekey).compact();

	}

	private Map<String, List<String>> buildClaim() {

		Map<String, List<String>> claimsData = new HashMap<>();

		claimsData.put("ROLE",List.of("ADMIN"));

		return claimsData;

	}

	private PrivateKey getPrivateKey() throws Exception {
		ClassPathResource resource = new ClassPathResource("keys/private.pem");

		String key = new String(resource.getInputStream().readAllBytes());
		key = key.replace("-----BEGIN PRIVATE KEY-----", "").replace("-----END PRIVATE KEY-----", "").replaceAll("\\s",
				"");

		byte[] decoded = Base64.getDecoder().decode(key);

		return KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
	}

}
