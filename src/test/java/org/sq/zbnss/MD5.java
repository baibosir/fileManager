package org.sq.zbnss;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.junit.jupiter.api.Test;

import java.util.Objects;

public class MD5 {

    @Test
    public void testMD5(){
        String hashName = "md5";
        String password = "123456";
        ByteSource c = ByteSource.Util.bytes("PUBOOT");
        Object res = new SimpleHash(hashName,password,c,2);
        System.out.println(res);
    }
}
