package main;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;

public class Main {

	public static void main(String[] args) {

		try {
			System.out.println("Portal: " + SaltHash.createHash(args[0]));
			
			PasswordHashHelper passwordHashHelper = new PasswordHashHelper();
			System.out.println("Credenciamento: " + passwordHashHelper.hashPassword(args[0]));
		} catch (UnsupportedEncodingException | GeneralSecurityException e) {
			e.printStackTrace();
		}
	}

}
