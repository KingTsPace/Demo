package com.demo.util;


import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import com.demo.common.IopConst;
import com.demo.common.IopException;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import sun.misc.BASE64Decoder;




/**
 * @author zWX454479 AES加密
 */
public class AESTool
{
    private static volatile AESTool aesTool;

//    private Key key;

    private static final String ENCRYPT = "encrypt";

    private static final String DECRYPT = "decrypt";

//    private AESTool()
//    {
//        setAesKey();
//    }

    public static AESTool instance()
    {
        if (aesTool == null)
        {
            synchronized (AESTool.class)
            {
                if (aesTool == null)
                {
                    aesTool = new AESTool();
                }
            }
        }
        return aesTool;
    }

//    private void setAesKey()
//    {
//        try
//        {
//            KeyGenerator kgen = KeyGenerator.getInstance("AES");
//            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
//            secureRandom.setSeed(IopConst.AESKEY.getBytes());
//            kgen.init(128, secureRandom);
//            key = kgen.generateKey();
//        }
//        catch (Exception e)
//        {
//            throw new RuntimeException("Error initializing SqlMap class. Cause: " + e);
//        }
//    }

    /**
     * 加密
     * 
     * @param content
     *            需要加密的内容
     * @param   password
     *            加密密码
     * @return
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws UnsupportedEncodingException
     * @throws InvalidKeyException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     */
    public byte[] encryptOrDecrypt(String content, String type) throws Exception
    {
        byte[] enCodeFormat = IopConst.AESKEY.getBytes("utf-8");
        SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");// 创建密码器
        byte[] byteContent = null;
        if (ENCRYPT.equals(type))
        {
            byteContent = content.getBytes("utf-8");
            cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
        }
        else if(DECRYPT.equals(type))
        {
            byteContent = new BASE64Decoder().decodeBuffer(content);
            cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
        }
        else
        {
            throw new IopException("encryptOrDecrypt error param");
        }
        byte[] result = cipher.doFinal(byteContent);
        return result; // 加密
    }

    public String encryptStr(String content) throws Exception
    {
        byte[] encryptResult = encryptOrDecrypt(content, ENCRYPT);
        return Base64.encode(encryptResult);
    }

    public String decryptStr(String content) throws Exception
    {
        byte[] decryptResult = encryptOrDecrypt(content, DECRYPT);
        return new String(decryptResult); 
    }
    
}
