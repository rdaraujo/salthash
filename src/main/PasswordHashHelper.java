package main;

import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class PasswordHashHelper {

	public static final String hex = "0123456789ABCDEF";
	
	private String salt = "mX^.5D?P";
	private int iterations = 3;
	private String algorithm = "PBKDF2WithHmacSHA1";
	
	public PasswordHash hashPassword(String password) throws GeneralSecurityException {
		String hash = this.createPasswordKey(password.toCharArray(), this.salt.getBytes(), this.iterations);
		return new PasswordHash(hash, salt);
	}
	
	public String createPasswordKey(char[] password, byte[] salt, int iterations) throws NoSuchAlgorithmException, InvalidKeySpecException {
		PBEKeySpec passwordKeySpec = new PBEKeySpec(password, salt, iterations, 256);
		SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(algorithm);
		SecretKey passwordKey = secretKeyFactory.generateSecret(passwordKeySpec);
		passwordKeySpec.clearPassword();
		return this.bin2hex(passwordKey.getEncoded());
	}

	public String bin2hex(final byte[] b) {
		if (b == null) {
			return "";
		}
		StringBuffer sb = new StringBuffer(2 * b.length);
		for (int i = 0; i < b.length; i++) {
			int v = (256 + b[i]) % 256;
			sb.append(hex.charAt((v / 16) & 15));
			sb.append(hex.charAt((v % 16) & 15));
		}
		return sb.toString();
	}

}