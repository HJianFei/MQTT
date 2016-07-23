package utils;

import java.security.Key;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
  
  
/**  
 * des���ܽ���   
 * @author  
 *   
 */  
public class DesEncrypt {  
  
    Key key;  
  
    public DesEncrypt(String str) {  
        setKey(str);// �����ܳ�  
    }  
  
    public DesEncrypt() {  
        setKey("abc123.*abc123.*abc123.*abc123.*");  
    }  
  
    /**  
     * ���ݲ�������KEY  
     */  
    public void setKey(String strKey) {  
        try { 
        	//�Ա�DES
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");  
            this.key  = keyFactory.generateSecret(new DESedeKeySpec(strKey.getBytes("UTF8")));  
        } catch (Exception e) {  
            throw new RuntimeException(  
                    "Error initializing SqlMap class. Cause: " + e);  
        }  
    }  
  
      
    /**  
     * ����String��������,String�������  
     */  
    public String encrypt(String strMing) {  
        byte[] byteMi = null;  
        byte[] byteMing = null;  
        String strMi = "";  
        BASE64Encoder base64en = new BASE64Encoder();  
        try {  
            byteMing = strMing.getBytes("UTF8");  
            byteMi = this.getEncCode(byteMing);  
            strMi = base64en.encode(byteMi);  
        } catch (Exception e) {  
            throw new RuntimeException(  
                    "Error initializing SqlMap class. Cause: " + e);  
        } finally {  
            base64en = null;  
            byteMing = null;  
            byteMi = null;  
        }  
        return strMi;  
    }  
  
    /**  
     * ���� ��String��������,String�������  
     *   
     * @param strMi  
     * @return  
     */  
    public String decrypt(String strMi) {  
        BASE64Decoder base64De = new BASE64Decoder();  
        byte[] byteMing = null;  
        byte[] byteMi = null;  
        String strMing = "";  
        try {  
            byteMi = base64De.decodeBuffer(strMi);  
            byteMing = this.getDesCode(byteMi);  
            strMing = new String(byteMing, "UTF8");  
        } catch (Exception e) {  
            throw new RuntimeException(  
                    "Error initializing SqlMap class. Cause: " + e);  
        } finally {  
            base64De = null;  
            byteMing = null;  
            byteMi = null;  
        }  
        return strMing;  
    }  
  
    /**  
     * ������byte[]��������,byte[]�������  
     *   
     * @param byteS  
     * @return  
     */  
    private byte[] getEncCode(byte[] byteS) {  
        byte[] byteFina = null;  
        Cipher cipher;  
        try {//�Ա�DES 
            cipher = Cipher.getInstance("DESede");  
            cipher.init(Cipher.ENCRYPT_MODE, key,SecureRandom.getInstance("SHA1PRNG"));  
            byteFina = cipher.doFinal(byteS);  
        } catch (Exception e) {  
            throw new RuntimeException(  
                    "Error initializing SqlMap class. Cause: " + e);  
        } finally {  
            cipher = null;  
        }  
        return byteFina;  
    }  
  
    /**  
     * ������byte[]��������,��byte[]�������  
     *   
     * @param byteD  
     * @return  
     */  
    private byte[] getDesCode(byte[] byteD) {  
        Cipher cipher;  
        byte[] byteFina = null;  
        try {//�Ա�DES
            cipher = Cipher.getInstance("DESede");  
            cipher.init(Cipher.DECRYPT_MODE, key,SecureRandom.getInstance("SHA1PRNG"));  
            byteFina = cipher.doFinal(byteD);  
        } catch (Exception e) {  
            throw new RuntimeException(  
                    "Error initializing SqlMap class. Cause: " + e);  
        } finally {  
            cipher = null;  
        }  
        return byteFina;  
    }  
//  
//      
//  
//    public static void main(String args[])  {  
//        DesEncrypt des = new DesEncrypt();  
//  
//        String str1 = "1234";  
//        // DES����  
//        String str2 = des.encrypt(str1);  
//        DesEncrypt des1 = new DesEncrypt();  
//        String deStr = des1.decrypt(str2);  
//        System.out.println("����:" + str2);  
//        // DES����  
//        System.out.println("����:" + deStr);
//       
//    }  
  
}  
