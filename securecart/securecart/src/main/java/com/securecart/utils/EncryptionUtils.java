package com.securecart.utils;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import com.securecart.exception.ProductCustomException;

public class EncryptionUtils {
	private static final String AES_ALGORITHM = "AES";
	private static final String SECRET_KEY = "enviconnect";

	public static String encrypt(String password) {
		try {

			SecretKeySpec secretKey = new SecretKeySpec(getPaddedKey(), AES_ALGORITHM);
			Cipher cipher = Cipher.getInstance(AES_ALGORITHM);

			cipher.init(Cipher.ENCRYPT_MODE, secretKey);

			byte[] encryptedBytes = cipher.doFinal(password.getBytes(StandardCharsets.UTF_8));

			return Base64.getEncoder().encodeToString(encryptedBytes);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ProductCustomException(e.getMessage());
		}
	}

	public static String decrypt(String encryptedText) {
		try {

			SecretKeySpec secretKey = new SecretKeySpec(getPaddedKey(), AES_ALGORITHM);

			Cipher cipher = Cipher.getInstance(AES_ALGORITHM);

			cipher.init(Cipher.DECRYPT_MODE, secretKey);

			byte[] encryptedBytes = Base64.getDecoder().decode(encryptedText);

			byte[] decryptedBytes = cipher.doFinal(encryptedBytes);

			return new String(decryptedBytes, StandardCharsets.UTF_8);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ProductCustomException(e.getMessage());
		}
	}

	public static byte[] getPaddedKey() {
		byte[] keyBytes = SECRET_KEY.getBytes(StandardCharsets.UTF_8);
		byte[] paddedKey = new byte[16]; // AES-128 requires a 16-byte key
		int length = Math.min(keyBytes.length, paddedKey.length);
		System.arraycopy(keyBytes, 0, paddedKey, 0, length);
		return paddedKey;
	}

}
