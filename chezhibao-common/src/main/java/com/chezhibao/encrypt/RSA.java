/*
 * Copyright (C), 2014-2015, 江苏乐博国际投资发展有限公司
 * FileName: RSA.java
 * Author:   wangkai
 * Date:     2015-7-24 上午10:02:23
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.chezhibao.encrypt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.crypto.Cipher;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * 〈RSA非对称加解密〉<br>
 * 〈功能详细描述〉
 * 
 * @author wangkai
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class RSA
{
	private static Logger		logger			= LoggerFactory.getLogger(RSA.class);

	private static final String	KEY_ALGORITHM	= "RSA";								//
	private static final String	split			= "#";									// 分隔符
	private static final int	max				= 117;									// 加密分段长度//不可超过117

	/**
	 * 初始化密钥
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String[] initKey() throws Exception
	{
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KEY_ALGORITHM);
		keyPairGenerator.initialize(1024);
		KeyPair keyPair = keyPairGenerator.generateKeyPair();

		// 公钥
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
		// 私钥
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

		return new String[] { parseByte2HexStr(publicKey.getEncoded()), parseByte2HexStr(privateKey.getEncoded()) };
	}

	/** 加密-公钥 */
	public static String encodeByPublicKey(String plainText, String key)
	{
		try
		{
			byte[] plainTextBytes = plainText.getBytes();
			byte[] keyBytes = parseHexStr2Byte(key);// 先把公钥转为2进制
			StringBuffer result = new StringBuffer();// 结果
			// 如果超过了100个字节就分段
			if (keyBytes.length <= max)
			{
				// 不超过直接返回即可
				return encodePub(plainTextBytes, keyBytes);
			}
			else
			{
				int size = plainTextBytes.length / max + (plainTextBytes.length % max > 0 ? 1 : 0);
				for (int i = 0; i < size; i++)
				{
					int len = i == size - 1 ? plainTextBytes.length % max : max;
					byte[] bs = new byte[len];// 临时数组
					System.arraycopy(plainTextBytes, i * max, bs, 0, len);
					result.append(encodePub(bs, keyBytes));
					if (i != size - 1)
						result.append(split);
				}
				return result.toString();
			}
		}
		catch (Exception e)
		{
			return null;
		}
	}

	/** 加密-私钥 */
	public static String encodeByPrivateKey(String plainText, String key)
	{
		try
		{
			byte[] plainTextBytes = plainText.getBytes();
			byte[] keyBytes = parseHexStr2Byte(key);
			StringBuffer result = new StringBuffer();
			// 如果超过了100个字节就分段
			if (keyBytes.length <= max)
			{
				// 不超过直接返回即可
				return encodePri(plainTextBytes, keyBytes);
			}
			else
			{
				int size = plainTextBytes.length / max + (plainTextBytes.length % max > 0 ? 1 : 0);
				for (int i = 0; i < size; i++)
				{
					int len = i == size - 1 ? plainTextBytes.length % max : max;
					byte[] bs = new byte[len];// 临时数组
					System.arraycopy(plainTextBytes, i * max, bs, 0, len);
					result.append(encodePri(bs, keyBytes));
					if (i != size - 1)
						result.append(split);
				}
				return result.toString();
			}
		}
		catch (Exception e)
		{
			return null;
		}
	}

	/** 解密-公钥 */
	public static String decodeByPublicKey(String cipherText, String key)
	{
		if (StringUtils.isEmpty(cipherText) || StringUtils.isEmpty(key))
		{
			return null;
		}

		try
		{
			byte[] keyBytes = parseHexStr2Byte(key);
			// 先分段
			String[] rs = cipherText.split("\\" + split);
			// 分段解密
			if (rs != null)
			{
				int len = 0;
				// 组合byte[]
				byte[] result = new byte[rs.length * max];
				for (int i = 0; i < rs.length; i++)
				{
					byte[] bs = decodePub(parseHexStr2Byte(rs[i]), keyBytes);
					System.arraycopy(bs, 0, result, i * max, bs.length);
					len += bs.length;
				}
				byte[] newResult = new byte[len];
				System.arraycopy(result, 0, newResult, 0, len);
				// 还原字符串
				return new String(newResult);
			}
		}
		catch (Exception e)
		{

		}
		return null;
	}

	/** 解密-私钥 */
	public static String decodeByPrivateKey(String cipherText, String key)
	{
		if (StringUtils.isEmpty(cipherText) || StringUtils.isEmpty(key))
		{
			return null;
		}

		try
		{
			byte[] keyBytes = parseHexStr2Byte(key);
			// 先分段
			String[] rs = cipherText.split("\\" + split);
			// 分段解密
			if (rs != null)
			{
				int len = 0;
				// 组合byte[]
				byte[] result = new byte[rs.length * max];
				for (int i = 0; i < rs.length; i++)
				{
					byte[] bs = decodePri(parseHexStr2Byte(rs[i]), keyBytes);
					System.arraycopy(bs, 0, result, i * max, bs.length);
					len += bs.length;
				}
				byte[] newResult = new byte[len];
				System.arraycopy(result, 0, newResult, 0, len);
				// 还原字符串
				return new String(newResult);
			}
		}
		catch (Exception e)
		{

		}
		return null;
	}

	/** 将二进制转换成16进制 */
	public static String parseByte2HexStr(byte buf[])
	{
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < buf.length; i++)
		{
			String hex = Integer.toHexString(buf[i] & 0xFF);
			if (hex.length() == 1)
			{
				hex = '0' + hex;
			}
			sb.append(hex.toUpperCase());
		}
		return sb.toString();
	}

	/** 将16进制转换为二进制 */
	public static byte[] parseHexStr2Byte(String hexStr)
	{
		if (hexStr.length() < 1)
			return null;
		byte[] result = new byte[hexStr.length() / 2];
		for (int i = 0; i < hexStr.length() / 2; i++)
		{
			int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
			int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
			result[i] = (byte) (high * 16 + low);
		}
		return result;
	}

	/** 加密-公钥-无分段 */
	private static String encodePub(byte[] plainTextBytes, byte[] keyBytes)
	{
		X509EncodedKeySpec x5 = new X509EncodedKeySpec(keyBytes);// 用2进制的公钥生成x509
		try
		{
			KeyFactory kf = KeyFactory.getInstance(KEY_ALGORITHM);
			Key pubKey = kf.generatePublic(x5);// 用KeyFactory把x509生成公钥pubKey
			Cipher cp = Cipher.getInstance(kf.getAlgorithm());// 生成相应的Cipher
			cp.init(Cipher.ENCRYPT_MODE, pubKey);// 给cipher初始化为加密模式，以及传入公钥pubKey
			return parseByte2HexStr(cp.doFinal(plainTextBytes));// 以16进制的字符串返回
		}
		catch (Exception e)
		{
			// logger.error("公钥加密失败", e);
		}
		return null;
	}

	/** 加密-私钥-无分段 */
	private static String encodePri(byte[] plainTextBytes, byte[] keyBytes)
	{
		PKCS8EncodedKeySpec pk8 = new PKCS8EncodedKeySpec(keyBytes);
		try
		{
			KeyFactory kf = KeyFactory.getInstance(KEY_ALGORITHM);
			Key priKey = kf.generatePrivate(pk8);
			Cipher cp = Cipher.getInstance(kf.getAlgorithm());
			cp.init(Cipher.ENCRYPT_MODE, priKey);
			return parseByte2HexStr(cp.doFinal(plainTextBytes));
		}
		catch (Exception e)
		{
			// logger.error("私钥加密失败", e);
		}
		return null;
	}

	/** 解密-公钥-无分段 */
	private static byte[] decodePub(byte[] cipherTextBytes, byte[] keyBytes)
	{
		X509EncodedKeySpec x5 = new X509EncodedKeySpec(keyBytes);
		try
		{
			KeyFactory kf = KeyFactory.getInstance(KEY_ALGORITHM);
			Key pubKey = kf.generatePublic(x5);
			Cipher cp = Cipher.getInstance(kf.getAlgorithm());
			cp.init(Cipher.DECRYPT_MODE, pubKey);
			return cp.doFinal(cipherTextBytes);
		}
		catch (Exception e)
		{
			// logger.error("公钥解密失败", e);
		}
		return null;
	}

	/** 解密-私钥-无分段 */
	private static byte[] decodePri(byte[] cipherTextBytes, byte[] keyBytes)
	{
		PKCS8EncodedKeySpec pk8 = new PKCS8EncodedKeySpec(keyBytes);
		try
		{
			KeyFactory kf = KeyFactory.getInstance(KEY_ALGORITHM);
			Key priKey = kf.generatePrivate(pk8);
			Cipher cp = Cipher.getInstance(kf.getAlgorithm());
			cp.init(Cipher.DECRYPT_MODE, priKey);
			return cp.doFinal(cipherTextBytes);
		}
		catch (Exception e)
		{
			// logger.error("私钥解密失败", e);
		}
		return null;
	}

	/**
	 * 功能描述: <br>
	 * 〈功能详细描述〉
	 * 
	 * @param args
	 * @throws Exception
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	public static void main(String[] args)
	{
		String[] key = null;
		try
		{
			key = initKey();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return;
		}

		String publicKey = key[0];
		String privateKey = key[1];

		System.out.println("公钥\n" + publicKey);
		System.out.println("私钥\n" + privateKey);
		System.out.println();

		String plainText = "mychebao";
		String _plainText = null;
		String _cipherText = null;

		// 公钥加密，私钥解密、公钥解密
		System.out.println("明文：" + plainText);
		_cipherText = encodeByPublicKey(plainText, publicKey);
		System.out.println("公钥加密密文：" + _cipherText);
		_plainText = decodeByPrivateKey(_cipherText, privateKey);
		System.out.println("私钥解密明文：" + _plainText);
		_plainText = decodeByPublicKey(_cipherText, publicKey);
		System.out.println("公钥解密明文：" + _plainText);

		// 私钥加密，公钥解密、私钥解密
		System.out.println("明文：" + plainText);
		_cipherText = encodeByPrivateKey(plainText, privateKey);
		System.out.println("私钥加密密文：" + _cipherText);
		_plainText = decodeByPublicKey(_cipherText, publicKey);
		System.out.println("公钥解密明文：" + _plainText);
		_plainText = decodeByPrivateKey(_cipherText, privateKey);
		System.out.println("私钥解密明文：" + _plainText);
	}
}
