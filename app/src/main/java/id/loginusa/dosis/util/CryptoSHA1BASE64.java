package id.loginusa.dosis.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by mfachmirizal on 11-May-16.
 */

    public final class CryptoSHA1BASE64 {
        public static String hash(String plaintext) throws Exception {
            MessageDigest md = null;

            try {
                md = MessageDigest.getInstance("SHA"); // SHA-1 generator instance
            } catch (NoSuchAlgorithmException e) {
                throw new Exception(e.getMessage());
            }

            try {
                md.update(plaintext.getBytes("UTF-8")); // Message summary
                // generation
            } catch (UnsupportedEncodingException e) {
                throw new Exception(e.getMessage());
            }

            byte raw[] = md.digest(); // Message summary reception
            try {
                String hash = new String(org.apache.commons.codec.binary.Base64.encodeBase64(raw), "UTF-8");
                return hash;
            } catch (UnsupportedEncodingException use) {
                throw new Exception(use);
            }
        }
    }
