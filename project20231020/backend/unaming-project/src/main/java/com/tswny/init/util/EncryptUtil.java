package com.tswny.init.util;

import com.tswny.init.service.PersonService;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Base64Utils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;

public class EncryptUtil {
    private final static Logger log = LoggerFactory.getLogger(EncryptUtil.class);
    private static final String PRIMARY_KEY = "A88k5452y98k73uLIy6pqVbzR48OBJDv";
    /**
     * 算法定义
     */
    private static final String AES_ALGORITHM = "AES";
    /**
     * 指定填充方式
     */
    private static final String CIPHER_CBC_PADDING = "AES/CBC/PKCS7Padding";
    /**
     * 偏移量(CBC中使用，增强加密算法强度)
     */
    private static final byte[] IV_SEED_ARRAY = {0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};


    /**
     * AES加密  SHA256/AES/CBC/PKCS7Padding 偏移量16个0 base64编码
     * @param content
     * @return
     */
    public static String encrypt(String content) {
        byte[] encryptedBytes = new byte[0];

        try {
            byte[] keyBytes = sha256Key(PRIMARY_KEY);

            Cipher cipher = initCipher(Cipher.ENCRYPT_MODE, keyBytes);
            encryptedBytes = cipher.doFinal(content.getBytes());

        } catch (Exception e) {
            log.info("encrypt exception:" + e.getMessage());
        }

        return Base64Utils.encodeToString(encryptedBytes);
    }

    /**
     * AES解密
     */
    public static String decrypt(String encryptString)
    {
        byte[] encryptBytes = Base64Utils.decodeFromString(encryptString);
        byte[] decryptedBytes = new byte[0];

        try {
            byte[] keyBytes = sha256Key(PRIMARY_KEY);
            Cipher cipher = initCipher(Cipher.DECRYPT_MODE, keyBytes);
            decryptedBytes = cipher.doFinal(encryptBytes);

        } catch (Exception e) {
            log.info("decrypt exception:" + e.getMessage());
        }

        return new String(decryptedBytes);
    }


    public static String base64Encrypt(String content) {
        return Base64Utils.encodeToString(content.getBytes());
    }

    public static String base64Decrypt(String encryptContent) {
        return new String(Base64Utils.decodeFromString(encryptContent));
    }



    /**
     * sha256对key进行摘要
     * @param key
     * @return
     * @throws Exception
     */
    private static byte[] sha256Key(String key) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] keyBytes = md.digest(key.getBytes());
        return keyBytes;
    }

    private static Cipher initCipher(int mode, byte[] shaKeyBytes) throws Exception {
        //设置加密算法，生成秘钥
        SecretKeySpec sKeySpec = new SecretKeySpec(shaKeyBytes, AES_ALGORITHM);

        Security.addProvider(new BouncyCastleProvider());

        // "算法/模式/补码方式"
        Cipher cipher = Cipher.getInstance(CIPHER_CBC_PADDING);

        //偏移量
        IvParameterSpec zeroIv = new IvParameterSpec(IV_SEED_ARRAY);

        //选择加密
        cipher.init(mode, sKeySpec, zeroIv);

        return cipher;
    }

    /**
     * 加密
     * @param shaKeyBytes
     * @param content
     * @return
     * @throws Exception
     */
    private static byte[] cipher(int mode, byte[] shaKeyBytes, String content) throws Exception {

        //设置加密算法，生成秘钥
        SecretKeySpec sKeySpec = new SecretKeySpec(shaKeyBytes, AES_ALGORITHM);

        Security.addProvider(new BouncyCastleProvider());

        // "算法/模式/补码方式"
        Cipher cipher = Cipher.getInstance(CIPHER_CBC_PADDING);

        //偏移量
        IvParameterSpec zeroIv = new IvParameterSpec(IV_SEED_ARRAY);

        //选择加密
        cipher.init(Cipher.ENCRYPT_MODE, sKeySpec, zeroIv);

        //根据待加密内容生成字节数组
        return cipher.doFinal(content.getBytes());
    }

    public static void main(String[] args) {
        String encryptString = EncryptUtil.encrypt("123456");
        String encryptStringBase64 = EncryptUtil.base64Encrypt("123456");
        System.out.println("encryptString: " + encryptString);
        System.out.println("encryptStringBase64: " + encryptStringBase64);
        String decryptString = EncryptUtil.decrypt(encryptString);
        String decryptStringBase64 = EncryptUtil.base64Decrypt(encryptStringBase64);
        System.out.println("decryptString: " + decryptString);
        System.out.println("decryptStringBase64: " + decryptStringBase64);
    }


}
