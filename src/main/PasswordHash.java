package main;

public class PasswordHash {
	
	private String hash;
	private String salt;

	public PasswordHash(String hash, String salt) {
		this.hash = hash;
		this.salt = salt;
	}

	public String getHash() {
		return this.hash;
	}

	public String getSalt() {
		return this.salt;
	}

	@Override
	public String toString() {
		return "PasswordHash [hash=" + hash + ", salt=" + salt + "]";
	}
	
}