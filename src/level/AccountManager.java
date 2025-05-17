package level;

import java.nio.file.*;
import java.security.*;
import java.util.Base64;
import java.io.*;
import java.util.Properties;

public class AccountManager {

    public static int registerAccount(String username, char[] passwordChar) {
        String password=new String(passwordChar);
        if (!isValidUsername(username)) {
            return -1;
        }

        // 修改路径为 user/[username]/data
        Path userDir = Paths.get("user", username, "data");
        try {
            // 创建目录（父目录 user/[username] 也会被自动创建）
            Files.createDirectories(userDir);

            // 生成盐和加密密码（逻辑不变）
            SecureRandom random = new SecureRandom();
            byte[] salt = new byte[16];
            random.nextBytes(salt);
            String hashedPassword = hashPassword(password, salt);

            // 存储文件路径调整为 user/[username]/data/userinfo.properties
            Path dataFile = userDir.resolve("userinfo.properties");
            try (BufferedWriter writer = Files.newBufferedWriter(dataFile, StandardOpenOption.CREATE)) {
                writer.write("username=" + username);
                writer.newLine();
                writer.write("salt=" + Base64.getEncoder().encodeToString(salt));
                writer.newLine();
                writer.write("password=" + hashedPassword);
            }

            return 0;
        } catch (IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
            return 1;
        }
    }

    // 检查密码是否匹配（需与 registerAccount 的加密逻辑一致）
    public static int checkPassword(String username, char[] passwordChar) {
        String password=new String(passwordChar);
        if (!isValidUsername(username)) {
            return -1;//密码不规范
        }

        // 修改路径为 user/[username]/data/userinfo.properties
        Path dataFile = Paths.get("user", username, "data", "userinfo.properties");
        if (!Files.exists(dataFile)) {
            return 1;//未注册或本地数据缺失
        }

        try (InputStream input = Files.newInputStream(dataFile)) {
            Properties prop = new Properties();
            prop.load(input);

            String storedSalt = prop.getProperty("salt");
            String storedHash = prop.getProperty("password");
            if (storedSalt == null || storedHash == null) {
                return 1;//本地数据缺失
            }

            byte[] salt = Base64.getDecoder().decode(storedSalt);
            String calculatedHash = hashPassword(password, salt);

            if(MessageDigest.isEqual(storedHash.getBytes(), calculatedHash.getBytes()))
            {
                return 0;//密码正确
            }else {
                return 2;//密码错误
            }

        } catch (IOException | IllegalArgumentException | NoSuchAlgorithmException e) {
            return 3;//未知错误
        }
    }

    // 校验用户名是否合法
    private static boolean isValidUsername(String username) {
        return username != null && username.matches("^[a-zA-Z0-9_-]{3,20}$");//使用正则match检验，合法字符包含 a-z A-Z 0-9 _ - 字符长度需在3-20个
    }

    // 密码哈希方法
    private static String hashPassword(String password, byte[] salt) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(salt);
        byte[] hashedBytes = md.digest(password.getBytes());
        return Base64.getEncoder().encodeToString(hashedBytes);
    }
}