package main;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Objects;

public class SaltHash {
	
	public static PasswordHash createHash(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		byte[] bSalt = createRandomSalt();
		byte[] bDigest = getHash(password, bSalt);

		String sDigest = byteToBase64(bDigest);
		String sSalt = byteToBase64(bSalt);

		return new PasswordHash(sDigest, sSalt);
	}

	public static boolean validate(String password, String digest, String salt) throws IOException, NoSuchAlgorithmException {
		byte[] bSalt = base64ToByte(salt);
		byte[] proposedDigest = getHash(password, bSalt);

		String sDigested = byteToBase64(proposedDigest);

		return Objects.equals(digest, sDigested);
	}

	private static byte[] getHash(String password, byte[] salt) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		digest.reset();
		digest.update(salt);
		byte[] input = digest.digest(password.getBytes("UTF-8"));
		for (int i = 0; i < 1000; i++) {
			digest.reset();
			input = digest.digest(input);
		}
		return input;
	}

	private static byte[] createRandomSalt() throws NoSuchAlgorithmException {
		byte[] salt = new byte[8];
		SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
		random.nextBytes(salt);
		return salt;
	}

	private static byte[] base64ToByte(String data) throws IOException {
		byte[] message = data.getBytes(StandardCharsets.UTF_8);
		return Base64.getDecoder().decode(message);
	}

	private static String byteToBase64(byte[] data) {
		return Base64.getEncoder().encodeToString(data);
	}
}