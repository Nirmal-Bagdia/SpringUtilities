package com.aaroo.security.config;

import java.math.BigInteger;
import java.security.Key;
import java.security.MessageDigest;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class AESCipher {

	private static final String ALGO = "AES";
	private static final byte[] keyValue = new byte[] { 'T', 'h', 'e', 'B', 'e', 's', 't', 'A', 'a', 'r', 't', 'e', 'k',
			'K', 'e', 'y' };

	/**
	 * Encrypt password
	 * 
	 * @param Data
	 * @return
	 * @throws Exception
	 */
	public static String encrypt(String Data) throws Exception {
		Key key = generateKey();
		Cipher c = Cipher.getInstance(ALGO);
		c.init(Cipher.ENCRYPT_MODE, key);
		byte[] encVal = c.doFinal(Data.getBytes());
		String encryptedValue = Base64.getEncoder().encodeToString(encVal);
		return encryptedValue;
	}

	/**
	 * Decrypt password
	 * 
	 * @param encryptedData
	 * @return
	 * @throws Exception
	 */
	public static String decrypt(String encryptedData) throws Exception {
		Key key = generateKey();
		Cipher c = Cipher.getInstance(ALGO);
		c.init(Cipher.DECRYPT_MODE, key);
		byte[] decordedValue = Base64.getDecoder().decode(encryptedData);
		byte[] decValue = c.doFinal(decordedValue);
		String decryptedValue = new String(decValue);
		return decryptedValue;
	}

	private static Key generateKey() throws Exception {
		Key key = new SecretKeySpec(keyValue, ALGO);
		return key;
	}

	/**
	 * Encrypt password by using MD5
	 */
	public static String ConvertToMD5(String password) {
		String encryptPaswword = "";
		try {
			byte[] bytePass = password.getBytes("UTF-8");

			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] md5Pass = md.digest(bytePass);

			BigInteger bigInt = new BigInteger(1, md5Pass);
			String hashtext = bigInt.toString(16);

			while (hashtext.length() < 32) {
				hashtext = "0" + hashtext;
			}
			encryptPaswword = hashtext;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
		return encryptPaswword;
	}

	public static void main(String args[]) throws Exception {
		System.out.println("account==" + AESCipher.encrypt("Admin@123"));
	}
}
