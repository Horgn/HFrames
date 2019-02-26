package com.hframe.basic.util;

import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;


/**
 * 安全工具类
 * 
 * @author horgn黄小锤
 * @date 2018年12月17日
 * @version 1.0
 */
public class SecurityUtils {

	/**
	 * MD5 加密工具类<br>
	 * @author Horgn黄小锤
	 * @date 2019年2月18日 下午3:56:04
	 * @version V1.0
	 */
	public static class MD5 {

		/**
		 * 生成MD5
		 * @author Horgn黄小锤
		 * @param message
		 * @return
		 * @date 2018-03-19 11:56:26
		 */
		public static String getMD5(String message) {

			String md5 = "";
			try {
				MessageDigest md = MessageDigest.getInstance("MD5"); // 创建一个md5算法对象

				byte[] messageByte = message.getBytes("UTF-8");
				byte[] md5Byte = md.digest(messageByte); // 获得MD5字节数组,16*8=128位
				md5 = bytesToHex(md5Byte); // 转换为16进制字符串

			} catch (Exception e) {
				e.printStackTrace();
			}

			return md5;
		}

		/**
		 * 二进制转十六进制
		 * @author Horgn黄小锤
		 * @param bytes
		 * @return
		 * @date 2018-03-19 11:56:34
		 */
		public static String bytesToHex(byte[] bytes) {

			StringBuffer hexStr = new StringBuffer();
			int num;

			for (int i = 0; i < bytes.length; i++) {
				num = bytes[i];
				if (num < 0) {
					num += 256;
				}
				if (num < 16) {
					hexStr.append("0");
				}
				hexStr.append(Integer.toHexString(num));
			}
			return hexStr.toString().toUpperCase();
		}
		
	}
	// ==============================================================================
	// ==============================================================================
	// ==============================================================================

	

	/**
	 * 64 位密钥加密工具类
	 * @author Horgn黄小锤
	 * @date 2019年2月18日 下午3:54:42
	 * @version V1.0
	 */
	public static class Crypto {

		public static Key DEFAULT_KEY = null;
		public static final String DEFAULT_SECRET_KEY = "BCA331A47F4DDA75CCAA1B0DEF60847586B295112E"
																				+ "F624764E53690CC37E21D002FB2777E0E2B2FAA0B91C"
																				+ "E661DC093C7AD8BF31418FBD93EFB15210BC570C4A";//勿随便更改
		public static final String Ctypto_Type = "AES";//勿随便更改

		static {
			DEFAULT_KEY = obtainKey(DEFAULT_SECRET_KEY);
		}

		/**
		 * 获得key
		 * 
		 * @author Horgn黄小锤
		 * @date 2019年2月18日 下午3:59:49
		 * @param key
		 * @return
		 */
		private static Key obtainKey(String key) {
			if (key == null) {
				return DEFAULT_KEY;
			}
			KeyGenerator generator = null;
			try {
				
				generator = KeyGenerator.getInstance(Ctypto_Type);
				SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
				random.setSeed(key.getBytes());
				generator.init(128,random);
				
			} catch (NoSuchAlgorithmException e) {
				throw new RuntimeException("获取加密密钥失败");
			}
//			generator.init(new SecureRandom(key.getBytes()));
			Key key1 = generator.generateKey();
			generator = null;
			return key1;
		}

		/**
		 * 默认密钥加密<br>
		 * @author Horgn黄小锤
		 * @date 2019年2月18日 下午3:59:56
		 * @param str 待加密明文
		 * @return
		 */
		public static String encode(String str) {
			return encode(null, str);
		}

		/**
		 * 自定义密钥加密<br>
		 * @author Horgn黄小锤
		 * @date 2019年2月18日 下午4:00:16
		 * @param key 密钥字符串
		 * @param str 待加密明文
		 * @return
		 */
		public static String encode(String key, String str) {
			// 非 16 进制数据
			//return  org.apache.commons.codec.binary.Base64.encodeBase64URLSafeString(obtainEncode(key, str.getBytes()));
			// 16 进制数据
			return Hex.encodeHexString(obtainEncode(key, str.getBytes())).toUpperCase();
		}

		/**
		 * 默认密钥解密<br>
		 * @author Horgn黄小锤
		 * @date 2019年2月18日 下午4:00:24
		 * @param str 待解密密文
		 * @return
		 */
		public static String decode(String str) {
			return decode(null, str);
		}

		/**
		 * 自定义密钥解密<br>
		 * @author Horgn黄小锤
		 * @date 2019年2月18日 下午4:00:30
		 * @param key 密文对应密钥
		 * @param str 待解密密文
		 * @return
		 */
		public static String decode(String key, String str) {
			// 非 16 进制数据
//			return new String(obtainDecode(key, org.apache.commons.codec.binary.Base64.decodeBase64(str)));
			// 16 进制的数据
			 try {
				 return new String(obtainDecode(key, Hex.decodeHex(str.toCharArray())));
			 } catch (DecoderException e) {
				 throw new RuntimeException("密文解密失败");
			 }
		}

		/**
		 * 加密<br>
		 * 以byte[]明文输入,byte[]密文输出
		 * @author Horgn黄小锤
		 * @date 2019年2月18日 下午4:00:39
		 * @param key
		 * @param str
		 * @return
		 */
		private static byte[] obtainEncode(String key, byte[] str) {
			byte[] byteFina = null;
			Cipher cipher;
			try {
				Key key1 = obtainKey(key);
				cipher = Cipher.getInstance(Ctypto_Type);
				cipher.init(Cipher.ENCRYPT_MODE, key1);
				byteFina = cipher.doFinal(str);
			} catch (Exception e) {
				throw new RuntimeException("明文加密失败");
			} finally {
				cipher = null;
			}
			return byteFina;
		}

		@Override
		public int hashCode() {
			return super.hashCode();
		}

		/**
		 * 解密<br>
		 * 以byte[]密文输入,以byte[]明文输出
		 * 
		 * @author Horgn黄小锤
		 * @date 2019年2月18日 下午4:00:48
		 * @param key
		 * @param str
		 * @return
		 */
		private static byte[] obtainDecode(String key, byte[] str) {
			Cipher cipher;
			byte[] byteFina = null;
			try {
				Key key1 = obtainKey(key);
				cipher = Cipher.getInstance(Ctypto_Type);
				cipher.init(Cipher.DECRYPT_MODE, key1);
				byteFina = cipher.doFinal(str);
			} catch (Exception e) {
				throw new RuntimeException("密文解密失败");
			} finally {
				cipher = null;
			}
			return byteFina;
		}
		
		public static void main(String[] args) {
//			String content = "Horgnframe";
			String content = "root";
			
			String pwd = encode(content);
			System.out.println(pwd);
			System.out.println(pwd.length());
			
			String decode = decode( pwd);
			System.out.println(content);
			System.out.println(decode);
			System.out.println(decode.length());
			System.out.println(content.equals(decode));
		}

	}
	// ==============================================================================
	// ==============================================================================
	// ==============================================================================

}
