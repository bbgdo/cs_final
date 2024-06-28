package com.warehouse.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PasswordUtilTest {
    /*
    * for those tests I used online md5 hash generator
    * https://www.md5hashgenerator.com/
    * */
    @Test
    void testHashPassword() {
        String password = "password";
        String md5hash = "5f4dcc3b5aa765d61d8327deb882cf99"; // md5 hash of "password"

        String hashedPassword = PasswordUtil.hashPassword(password);

        assertEquals(md5hash, hashedPassword);
    }

    @Test
    void testHashPasswordSpecialCharactersAndNumbers() {
        String password = "p@55w0rd";
        String md5hash = "39f13d60b3f6fbe0ba1636b0a9283c50"; // md5 hash of "p@55w0rd"

        String hashedPassword = PasswordUtil.hashPassword(password);

        assertEquals(md5hash, hashedPassword);
    }
}