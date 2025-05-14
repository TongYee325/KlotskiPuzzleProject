package level;

import java.nio.file.*;
import java.security.*;
import java.util.Base64;
import java.io.*;
import java.util.Properties;


//采用哈希加盐的加密方法

public class AccountManager {

    public static int registerAccount(String username, char[] passwordChar) {
        String password=new String(passwordChar);
        if (!isValidUsername(username)) {
            return -1;
        }

        // 修改路径为 user/[username]/data
        Path userLocation = Paths.get("user", username, "data");

        try {
            // 创建目录
            Files.createDirectories(userLocation);

            // 随机生成密码加密所需的盐值
            SecureRandom random = new SecureRandom();//使用加密安全的随机方法
            byte[] salt = new byte[16];
            random.nextBytes(salt);//填充至salt中
            String hashedPassword = hashPassword(password, salt);

            // 存储文件路径调整为 user/[username]/data/userinfo.properties
            Path dataFile = userLocation.resolve("userinfo.properties");
            try (BufferedWriter writer = Files.newBufferedWriter(dataFile, StandardOpenOption.CREATE)) {
                writer.write("username=" + username);
                writer.newLine();
                //用 Base64 编码转换为字符串存储
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

    //检查密码是否正确
    public static int checkPassword(String username, char[] passwordChar) {
        String password=new String(passwordChar);
        if (!isValidUsername(username)) {
            return -1;//输入的用户名不合法
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
            //将存储的盐值与用户输入的密码进行计算，计算出来哈希值再与先前存储的哈希值比较
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

    // 检查用户名是否合法
    private static boolean isValidUsername(String username) {
        //使用match方法检验输入的用户名是否合法，合法字符包含 a-z A-Z 0-9 _ - 字符长度需在3-20个
        return username != null && username.matches("^[a-zA-Z0-9_-]{3,20}$");
    }

    // 密码哈希方法
    private static String hashPassword(String password, byte[] salt) throws NoSuchAlgorithmException {
        //在环境不支持加密算法时，丢出NoSuchAlgorithmException异常
        //使用SHA-256哈希加密方法
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        //添加盐值
        md.update(salt);
        byte[] hashedBytes = md.digest(password.getBytes());
        //返回 用 Base64 编码的加密后的密码
        return Base64.getEncoder().encodeToString(hashedBytes);
    }
}