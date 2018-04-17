package com.demo.util;

import java.security.MessageDigest;

import com.demo.common.IopConst;
import com.demo.common.IopException;
import org.apache.commons.codec.binary.Hex;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;


public class StrToBase64
{

    final BASE64Encoder encoder = new BASE64Encoder();
    final BASE64Decoder decoder = new BASE64Decoder();
    //字符串加密成base64码
    public  String strToBase64(String str) throws IopException
    {
      
        byte[] textByte = null;
        String encodedBase64Text=IopConst.STRING_EMPTY;
        String Base64Text=IopConst.STRING_EMPTY;
        try
        {
            textByte = str.getBytes("UTF-8");
            // 编码
            encodedBase64Text = encoder.encode(textByte);
            Base64Text=encodedBase64Text.replaceAll("\t|\r|\n", "");
           
        }
        catch (Exception e)
        {

            StringBuffer sb = new StringBuffer(IopConst.STRING_EMPTY);
            sb.append("StrToBase64 strToBase64: ");
            sb.append("format is fail");
            throw new IopException(sb.toString());
        }
        
        return Base64Text;

    }
    
    //base64码解密成字符串
    public String base64ToStr(String str) throws IopException
    {
           
        String decodeBase64Text=IopConst.STRING_EMPTY;
        try
        {
            decodeBase64Text = new String(decoder.decodeBuffer(str), "UTF-8");
        }
        catch (Exception e)
        {
            StringBuffer sb = new StringBuffer(IopConst.STRING_EMPTY);
            sb.append("StrToBase64 base64ToStr: ");
            sb.append("format is fail");
            throw new IopException(sb.toString());
        }      
    
        return decodeBase64Text;

    }
    
    
    
    /***
     *  利用Apache的工具类实现SHA-256加密
     * @param str 加密后的报文
     * @return
     */
    public  String getSHA256Str(String str){
        MessageDigest messageDigest;
        String encdeStr = "";
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] hash = messageDigest.digest(str.getBytes("UTF-8"));
            encdeStr = Hex.encodeHexString(hash);//导包应该没问题吧？
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encdeStr;
    }
    
    
    public static void main(String[] args) throws IopException
    {
        //String str=getSHA256Str("我 123");
//        StrToBase64 toBase64=new StrToBase64();
//        String str=toBase64.strToBase64("92f9e879eb5441519b263xcsafasfsafasg4a1bed46498654964qweadsa96");
        StrToBase64 to=new StrToBase64();
        //appid+timestamp+userid+token+appkey
        //appID + userID + appKey + token + timesTamp
        //String st="1102018-03-23 19:22:3822wuhan110427bcd22-cbbc-4e6f-8f43-741571234fa8wuhan110";
        String st="11022wuhan110wuhan110d07c8140-3b5d-4479-b2c1-76f1987df4622018-03-23 19:22:38";
        String parametersToSHA256 = to.getSHA256Str(st);
        String parametersToBase64 = to.strToBase64(parametersToSHA256);
        
//        Pattern p = Pattern.compile("\t|\r|\n");  
//        Matcher m = p.matcher(string);  
//        string = m.replaceAll("");  
//        string = string.replaceAll(" +", " ");  
        //String  str1=str.replaceAll("\r|\n", "");
        System.out.println("parametersToSHA256:"+parametersToSHA256);
        System.out.println("parametersToBase64:"+parametersToBase64);
    }
    
    
    
    
    
    
    
    
    
    

}
