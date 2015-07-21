/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.rogers1b.wechat.message;

import java.security.MessageDigest;
import java.util.Arrays;
import org.apache.commons.codec.binary.Hex;

/**
 * Validate wechat message
 * 
 * @author Rogers
 */
public class MessageValidator {
    private String token;
    private String timeStamp;
    private String nonce;
    private String signature;
    
    public MessageValidator(String token, String timeStamp, 
            String nonce, String signature){
        this.token = token;
        this.timeStamp = timeStamp;
        this.nonce = nonce;
        this.signature = signature;
    }
    
    public String calculateSignature(){
        String[] array = new String[]{token, timeStamp, nonce};
        Arrays.sort(array);
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA1");
            byte[] result = digest.digest((array[0] + array[1] + array[2]).getBytes());
            return Hex.encodeHexString(result);
        }catch(Exception e){
            throw new RuntimeException(e);
        }

    }
    
    public boolean validate(){
        return signature.equals(calculateSignature());
    }
}
