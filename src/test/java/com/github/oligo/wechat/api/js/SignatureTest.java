/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.oligo.wechat.api.js;

import org.junit.AfterClass;
import org.junit.Test;

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
        Signature instance = new Signature("sM4AOVdWfPE4DxkXGEs8VMCPGGVi4C3VM0P37wVUCFvkVAy_90u5h9nbSlYy3-Sl-HhTdfl2fzFy1AOcHKP7qg", 
            "http://mp.weixin.qq.com");
        String expResult = "f36a5b44ddd2d5f23c8fed43ad7ec25656ecc14a";
        String result = instance.sign();
        System.out.println(result);
        //assertEquals(expResult, result);
    }
    
}
