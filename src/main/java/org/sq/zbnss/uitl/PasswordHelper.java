package org.sq.zbnss.uitl;



import lombok.experimental.UtilityClass;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.sq.zbnss.entity.User;

/**
 * 密码加密工具类
 *
 */
@UtilityClass
public class PasswordHelper {
    private static final RandomNumberGenerator RANDOM_NUMBER_GENERATOR = new SecureRandomNumberGenerator();
    private static final String ALGORITHM_NAME = "md5";
    private static final int HASH_ITERATIONS = 2;

    public static void encryptPassword(User user) {
        user.setSalt(RANDOM_NUMBER_GENERATOR.nextBytes().toHex());
        String newPassword = new SimpleHash(ALGORITHM_NAME, user.getPassword(), ByteSource.Util.bytes(user.getCredentialsSalt()), HASH_ITERATIONS).toHex();
        user.setPassword(newPassword);
    }

    public static String getPassword(User user) {
        return new SimpleHash(ALGORITHM_NAME, user.getPassword(), ByteSource.Util.bytes(user.getCredentialsSalt()), HASH_ITERATIONS).toHex();
    }

    public static void main(String[] args) {
        User user = new User();
        user.setUsername("root");
        user.setPassword("root@1234");
        encryptPassword(user);
        System.out.println(user);
        System.out.println(getPassword(user));
    }
}
