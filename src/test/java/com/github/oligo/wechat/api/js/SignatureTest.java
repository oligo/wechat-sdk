/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.oligo.wechat.api.js;

import org.junit.AfterClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Rogers
 */
public class SignatureTest {
    
    public SignatureTest() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of getSignConfig method, of class Signature.
     */
    @Test
    public void testSign() {
        System.out.println("getSignConfig");
        Signature signature = new Signature();
        signature.setJsApiTicket("sM4AOVdWfPE4DxkXGEs8VMCPGGVi4C3VM0P37wVUCFvkVAy_90u5h9nbSlYy3-Sl-HhTdfl2fzFy1AOcHKP7qg");
        signature.setNonceStr("Wm3WZYTPz0wzccnW");
        signature.setTimestamp(1414587457);
        signature.setUrl("http://mp.weixin.qq.com?params=value");
        String expResult = "0f9de62fce790f9a083d5c99e95740ceb90c27ed";
        String result = signature.sign();
        System.out.println(result);
        assertEquals(expResult, result);
    }
    
}
