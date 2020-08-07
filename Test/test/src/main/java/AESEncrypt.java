import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class AESEncrypt {
    private static String sKey = "e2b8b25fad71409e";
    private static String ivParameter = "9e69e212bb37b587";
    //加密
    public static String encrypt(String data) {
        String ivString = ivParameter;
        //偏移量
        byte[] iv = ivString.getBytes();
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            int blockSize = cipher.getBlockSize();
            byte[] dataBytes = data.getBytes();
            int length = dataBytes.length;
            //计算需填充长度
            if (length % blockSize != 0) {
                length = length + (blockSize - (length % blockSize));
            }
            byte[] plaintext = new byte[length];
            //填充
            System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);
            byte[] raw = sKey.getBytes("ASCII");
            SecretKeySpec keySpec = new SecretKeySpec(raw, "AES");
            //设置偏移量参数
            IvParameterSpec ivSpec = new IvParameterSpec(iv);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
            byte[] encryped = cipher.doFinal(plaintext);

            return  new String(Base64.getEncoder().encode(encryped));//此处使用BASE64做转码。
 
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
 
    //解密
    public static String desEncrypt(String data) {
 
        String ivString = ivParameter;
        byte[] iv = ivString.getBytes();
 
        try {
            byte[] encryp = Base64.getDecoder().decode(data);//先用base64解密
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            byte[] raw = sKey.getBytes("ASCII");
            SecretKeySpec keySpec = new SecretKeySpec(raw, "AES");
            IvParameterSpec ivSpec = new IvParameterSpec(iv);
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
            byte[] original = cipher.doFinal(encryp);
            return new String(original);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    //测试
    public static void main(String[] args) {
        String data = "SDalxbAS6i8K0keD+OS5gg==";
        String desencrypt = desEncrypt(data);
        System.out.println("解密后:"+desencrypt);
    }
}