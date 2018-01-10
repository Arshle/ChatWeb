package com.chezhibao.encrypt;

import java.security.MessageDigest;

/**
 * 
 * 〈MD5加密工具类〉<br>
 * 〈功能详细描述〉
 * 
 * @author 王凯
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class MD5
{

	/**
	 * 
	 * 返回32位大写的MD5加密结果 <br>
	 * 〈功能详细描述〉
	 * 
	 * @param plainText
	 *            明文
	 * @return
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	public static String encrypt(String plainText)
	{
		char[] charArray = plainText.toCharArray();
		byte[] byteArray = new byte[charArray.length];

		for (int i = 0; i < charArray.length; i++)
		{
			byteArray[i] = (byte) charArray[i];
		}

		MessageDigest md5Instance = null;
		try
		{
			md5Instance = MessageDigest.getInstance("MD5");
		}
		catch (Exception e)
		{
			throw new RuntimeException("MD5加密算法实例化失败", e);
		}

		byte[] md5Bytes = md5Instance.digest(byteArray);

		StringBuffer hexValue = new StringBuffer();

		for (int i = 0; i < md5Bytes.length; i++)
		{
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16)
			{
				hexValue.append("0");
			}
			hexValue.append(Integer.toHexString(val));
		}

		return hexValue.toString().toUpperCase();
	}

	/**
	 * 
	 * MD5加密后取8位特殊字符作为加密结果 <br>
	 * 〈功能详细描述〉
	 * 
	 * @param plainText
	 * @return
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	public static String encrypt8(String plainText)
	{
		String md5 = MD5.encrypt(plainText);
		return md5.substring(0, 2) + md5.substring(14, 18) + md5.substring(30);
	}

}
