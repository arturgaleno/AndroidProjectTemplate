package br.com.app.template.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {
	/**
	 * Generate an MD5 entry for given entry
	 * @param entry
	 * @return An MD5 string
	 */
	public static String generate(String entry) {

		
		MessageDigest md = null;
		StringBuffer sb = null;
		try {
			md = MessageDigest.getInstance("MD5");
		
        md.update(entry.getBytes());
 
        byte byteData[] = md.digest();
 
        //convert the byte to hex format method 1
        sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
         sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
        return sb.toString();
	}
}
