/*
 * Copyright (C), 2012-2020, Dragon.net
 * FileName: Des.java
 * Author:   wangkai
 * Date:     2012-05-01
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.chezhibao.encrypt;

/**
 * @author    leemenz
 * @company   leemenz (C) copyright
 * @time      Nov 1, 2006  10:18:41 AM
 * @version   1.0.0.0
 * @package   com.des
 * @modify by zzy 修改encrypt方法, 为解决和c++互解,为明文长度不是四的整数倍数的字符串用 "\0" 补齐
 */

import org.springframework.util.StringUtils;

import javax.crypto.Cipher;
import java.security.Key;
import java.security.Security;
import java.util.HashMap;
import java.util.Map;

public class Des
{

	private static String				DefaultKey			= "DragonEncrypt_DES";

	{
		Security.addProvider(new com.sun.crypto.provider.SunJCE());
	}

	/**
	 * 缓存的加密、解密密码<br>
	 * 加密密码key=密码＋E<br>
	 * 解密密码key=密码＋D
	 */
	private static Map<String, Cipher>	ciphers				= new HashMap<String, Cipher>();
	private static final String			decryptKeyEndFlag	= "D";
	private static final String			encryptKeyEndFlag	= "E";

	/**
	 * 
	 * 缓存加解密密码 <br>
	 * 〈功能详细描述〉
	 * 
	 * @param key
	 *            用户自定义秘钥
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	public static void cacheKey(String key)
	{
		if (!StringUtils.isEmpty(key))
		{
			// DES加密密码
			if (!ciphers.containsKey(key + encryptKeyEndFlag))
			{
				ciphers.put(key + encryptKeyEndFlag, initEncryptCipher(key));
			}

			// DES解密密码
			if (!ciphers.containsKey(key + decryptKeyEndFlag))
			{
				ciphers.put(key + decryptKeyEndFlag, initDecryptCipher(key));
			}
		}
	}

	/**
	 * 
	 * 加密初始化 <br>
	 * 〈功能详细描述〉
	 * 
	 * @param strKey
	 *            自定义秘钥
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	private static Cipher initEncryptCipher(String strKey)
	{
		// 从缓存中获取
		Cipher cipher = ciphers.get(strKey + encryptKeyEndFlag);
		if (cipher != null)
		{
			return cipher;
		}

		try
		{
			String keyStr = StringUtils.isEmpty(strKey) ? DefaultKey : strKey;
			Key key = getKey(keyStr.getBytes());

			Cipher encryptCipher = Cipher.getInstance("DES/ECB/NoPadding");
			encryptCipher.init(Cipher.ENCRYPT_MODE, key);

			return encryptCipher;
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	/**
	 * 
	 * 解密初始化 <br>
	 * 〈功能详细描述〉
	 * 
	 * @param strKey
	 *            自定义秘钥
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	private static Cipher initDecryptCipher(String strKey)
	{
		// 从缓存中获取
		Cipher cipher = ciphers.get(strKey + decryptKeyEndFlag);
		if (cipher != null)
		{
			return cipher;
		}

		try
		{
			String keyStr = StringUtils.isEmpty(strKey) ? DefaultKey : strKey;
			Key key = getKey(keyStr.getBytes());

			Cipher decryptCipher = Cipher.getInstance("DES/ECB/NoPadding");
			decryptCipher.init(Cipher.DECRYPT_MODE, key);

			return decryptCipher;
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	/**
	 * 从指定字符串生成密钥，密钥所需的字节数组长度为8位 不足8位时后面补0，超出8位只取前8位
	 * 
	 * @param arrBTmp
	 *            构成该字符串的字节数组
	 * @return 生成的密钥
	 * @throws Exception
	 */
	private static Key getKey(byte[] arrBTmp) throws Exception
	{
		// 创建一个空的8位字节数组（默认值为0）
		byte[] arrB = new byte[8];

		// 将原始字节数组转换为8位
		for (int i = 0; i < arrBTmp.length && i < arrB.length; i++)
		{
			arrB[i] = arrBTmp[i];
		}

		// 生成密钥
		Key key = new javax.crypto.spec.SecretKeySpec(arrB, "DES");

		return key;
	}

	/**
	 * 
	 * 字符串加密 <br>
	 * 〈使用默认秘钥〉
	 * 
	 * @param str
	 *            明文
	 * @return
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	public static String encrypt(String str)
	{
		return encrypt(str, null);
	}

	/**
	 * 
	 * 字符串加密 <br>
	 * 〈使用自定义秘钥〉
	 * 
	 * @param str
	 *            明文
	 * @param key
	 *            自定义秘钥
	 * @return
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	public static String encrypt(String str, String key)
	{
		return encrypt(str, key, 3);
	}

	/**
	 * 
	 * 字符串加密 <br>
	 * 〈功能详细描述〉
	 * 
	 * @param str
	 *            明文
	 * @param key
	 *            自定义秘钥
	 * @param cnLength
	 *            一个中文的计算长度
	 * @return
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	public static String encrypt(String str, String key, int cnLength)
	{
		Cipher encryptCipher = initEncryptCipher(key);
		return encryptStr(str, encryptCipher, cnLength);
	}

	/**
	 * 加密字符串
	 * 
	 * @param strIn
	 *            需加密的字符串
	 * @param cnLength
	 *            一个中文的计算长度
	 * @return 加密后的字符串
	 * @throws Exception
	 */
	private static String encryptStr(String strIn, Cipher encryptCipher, int cnLength)
	{
		int len = 0;
		char[] chars = strIn.toCharArray();
		for (char c : chars)
		{
			if (c >= 128 && c <= 255)
			{
				len += 2;
			}
			else if (c > 255)
			{
				len += cnLength;
			}
			else
			{
				len++;
			}
		}
		if (len % 8 != 0)
		{
			int nums = 8 - len % 8;
			for (int i = 0; i < nums; i++)
			{
				strIn += "\0";
			}
		}

		try
		{
			return byteArr2HexStr(encrypt(encryptCipher, strIn.getBytes()));
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	/**
	 * 将byte数组转换为表示16进制值的字符串， 如：byte[]{8,18}转换为：0813， 和public static byte[]
	 * hexStr2ByteArr(String strIn) 互为可逆的转换过程
	 * 
	 * @param arrB
	 *            需要转换的byte数组
	 * @return 转换后的字符串
	 * @throws Exception
	 *             本方法不处理任何异常，所有异常全部抛出
	 */
	private static String byteArr2HexStr(byte[] arrB) throws Exception
	{
		int iLen = arrB.length;
		// 每个byte用两个字符才能表示，所以字符串的长度是数组长度的两倍
		StringBuffer sb = new StringBuffer(iLen * 2);
		for (int i = 0; i < iLen; i++)
		{
			int intTmp = arrB[i];
			// 把负数转换为正数
			while (intTmp < 0)
			{
				intTmp = intTmp + 256;
			}
			// 小于0F的数需要在前面补0
			if (intTmp < 16)
			{
				sb.append("0");
			}
			sb.append(Integer.toString(intTmp, 16));
		}
		return sb.toString();
	}

	/**
	 * 加密字节数组
	 * 
	 * @param arrB
	 *            需加密的字节数组
	 * @return 加密后的字节数组
	 * @throws Exception
	 */
	private static byte[] encrypt(Cipher encryptCipher, byte[] arrB) throws Exception
	{
		return encryptCipher.doFinal(arrB);
	}

	/**
	 * 
	 * 字符串解密 <br>
	 * 〈使用默认秘钥〉
	 * 
	 * @param str
	 *            密文
	 * @return
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	public static String decrypt(String str)
	{
		return decrypt(str, null);
	}

	/**
	 * 
	 * 字符串解密 <br>
	 * 〈使用自定义秘钥〉
	 * 
	 * @param str
	 *            密文
	 * @param key
	 *            秘钥
	 * @return
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	public static String decrypt(String str, String key)
	{
		Cipher decryptCipher = initDecryptCipher(key);
		String result = decryptStr(str, decryptCipher);
		return result != null ? result.trim() : null;
	}

	/**
	 * 解密字符串
	 * 
	 * @param strIn
	 *            需解密的字符串
	 * @return 解密后的字符串
	 * @throws Exception
	 */
	private static String decryptStr(String strIn, Cipher decryptCipher)
	{
		try
		{
			byte[] decrypt = decrypt(decryptCipher, hexStr2ByteArr(strIn));
			return new String(decrypt, "utf8").trim();// TODO hard-coded
		}
		catch (Exception e)
		{
			return null;
		}
	}

	/**
	 * 解密字节数组
	 * 
	 * @param arrB
	 *            需解密的字节数组
	 * @return 解密后的字节数组
	 * @throws Exception
	 */
	private static byte[] decrypt(Cipher decryptCipher, byte[] arrB) throws Exception
	{
		return decryptCipher.doFinal(arrB);
	}

	/**
	 * 将表示16进制值的字符串转换为byte数组， 和public static String byteArr2HexStr(byte[] arrB)
	 * 互为可逆的转换过程
	 * 
	 * @param strIn
	 *            需要转换的字符串
	 * @return 转换后的byte数组
	 * @throws Exception
	 *             本方法不处理任何异常，所有异常全部抛出
	 * @author <a href="mailto:leo841001@163.com">LiGuoQing</a>
	 */
	private static byte[] hexStr2ByteArr(String strIn) throws Exception
	{
		byte[] arrB = strIn.getBytes();
		int iLen = arrB.length;

		// 两个字符表示一个字节，所以字节数组长度是字符串长度除以2
		byte[] arrOut = new byte[iLen / 2];
		for (int i = 0; i < iLen; i = i + 2)
		{
			String strTmp = new String(arrB, i, 2);
			arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
		}
		return arrOut;
	}

	public static void main(String[] args)
	{
//		System.out.println("defaultCharset:" + Charset.defaultCharset());
		System.out.println(Des.decrypt(
				"d92a7964fd4fd0ba"));
	}

}
