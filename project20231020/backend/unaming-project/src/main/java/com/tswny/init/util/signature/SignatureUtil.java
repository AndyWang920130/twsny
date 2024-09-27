package com.tswny.init.util.signature;

import com.tswny.init.util.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SignatureUtil {
    private final static Logger log = LoggerFactory.getLogger(SignatureUtil.class);

    public static String sha1(String str) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e) {
            log.error("sha1 error: {}", e.getMessage());
        }
        md.update(str.getBytes());
        byte[] digest = md.digest();
        String result = new BigInteger(1, digest).toString(16).toUpperCase();
        return result;
    }
}
