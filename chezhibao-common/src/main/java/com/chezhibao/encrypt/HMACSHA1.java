/*
 * Copyright (C), 2014-2014, 江苏乐博国际投资发展有限公司
 * FileName: HMACSHA1.java
 * Author:   王凯
 * Date:     2014年5月10日 下午1:45:44
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.chezhibao.encrypt;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * 〈信息摘要算法HMACSHA1〉<br>
 * 〈http://blog.csdn.net/xiaobaoxiaodun/article/details/6959016〉
 * 
 * @author 王凯
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class HMACSHA1
{

	private static final String	HMAC_SHA1	= "HmacSHA1";

	/**
	 * 生成签名数据
	 * 
	 * @param data
	 *            待加密的数据
	 * @param key
	 *            加密使用的key
	 * @throws InvalidKeyException
	 * @throws NoSuchAlgorithmException
	 */
	public static String getSignature(String data, String key)
	{
		byte[] keyBytes = key.getBytes();
		SecretKeySpec signingKey = new SecretKeySpec(keyBytes, HMAC_SHA1);
		Mac mac = null;
		try
		{
			mac = Mac.getInstance(HMAC_SHA1);
			mac.init(signingKey);
		}
		catch (NoSuchAlgorithmException | InvalidKeyException e)
		{
		}

		if (mac != null)
		{
			StringBuilder sb = new StringBuilder();
			byte[] rawHmac = mac.doFinal(data.getBytes());
			for (byte b : rawHmac)
			{
				sb.append(byteToHexString(b));
			}
			return sb.toString();
		}
		else
		{
			return null;
		}

	}

	private static String byteToHexString(byte ib)
	{
		char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		char[] ob = new char[2];
		ob[0] = Digit[(ib >>> 4) & 0X0f];
		ob[1] = Digit[ib & 0X0F];
		String s = new String(ob);
		return s;
	}

	public static void main(String[] args)
	{
		System.out.println(getSignature("车置宝竞拍活动auction123", "mychebao"));
	}

}
