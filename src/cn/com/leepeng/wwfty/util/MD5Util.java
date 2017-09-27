package cn.com.leepeng.wwfty.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5加密工具类
 * 
 * @author LEEPENG
 * 
 */
public class MD5Util {
	/**
	 * 获取加密后的MD5字符串
	 * @param sequence 字符序列 
	 * @return 加密后的MD5字符串
	 */
	public static String parseMD5String(String sequence) {
		/*
		 * 将字符串转换成字节数组，在进行MD5算法，最后返回的也是一个字节数组，转成32位的字符串。
		 */
		try {
			// 拿到一个MD5转换器（如果想要SHA1加密参数换成"SHA1"）
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			// 输入的字符串转换成字节数组
			byte[] inputByteArray = sequence.getBytes();
			// inputByteArray是输入字符串转换得到的字节数组
			messageDigest.update(inputByteArray);
			// 转换并返回结果，也是字节数组，包含16个元素
			byte[] resultByteArray = messageDigest.digest();
			// 字符数组转换成字符串返回
			return byteArrayToHex(resultByteArray);
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
	}

	private static String byteArrayToHex(byte[] byteArray) {
		// 首先初始化一个字符数组，用来存放每个16进制字符
		char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		// new一个字符数组，这个就是用来组成结果字符串的（解释一下：一个byte是八位二进制，也就是2位十六进制字符）
		char[] resultCharArray = new char[byteArray.length * 2];
		// 遍历字节数组，通过位运算（位运算效率高），转换成字符放到字符数组中去
		int index = 0;
		for (byte b : byteArray) {
			resultCharArray[index++] = hexDigits[b >>> 4 & 0xf];
			resultCharArray[index++] = hexDigits[b & 0xf];
		}
		// 字符数组组合成字符串返回
		return new String(resultCharArray);
	}

	/**
	 * 获取文件MD5加密后的字符串
	 * 
	 * @param filePath
	 *            待加密的文件路径
	 * @return 文件MD5加密后的字符串
	 */
	public static String parseFileToMD5String(String filePath) {
		// 缓冲区大小（这个可以抽出一个参数）
		int bufferSize = 256 * 1024;
		FileInputStream fileInputStream = null;
		DigestInputStream digestInputStream = null;
		byte[] resultByteArray = null;
		try {
			// 拿到一个MD5转换器（同样，这里可以换成SHA1）
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			// 使用DigestInputStream
			fileInputStream = new FileInputStream(filePath);
			digestInputStream = new DigestInputStream(fileInputStream, messageDigest);
			// read的过程中进行MD5处理，直到读完文件
			byte[] buffer = new byte[bufferSize];
			while (digestInputStream.read(buffer) > 0)
				;
			// 获取最终的MessageDigest
			messageDigest = digestInputStream.getMessageDigest();
			// 拿到结果，也是字节数组，包含16个元素
			resultByteArray = messageDigest.digest();
			// 同样，把字节数组转换成字符串
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (digestInputStream != null) {
				try {
					digestInputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (fileInputStream != null) {
				try {
					fileInputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return byteArrayToHex(resultByteArray);
	}

	/**
	 * 已过时：获取文件MD5加密后的字符串,文件大于2G，那么这种方式会出现异常。不推荐。
	 * 
	 * @deprecated
	 * @param file
	 * @return
	 */
	public static String parseFileToMD5String(File file) {
		MessageDigest messageDigest = null;
		FileInputStream inputStream = null;
		FileChannel channel = null;
		MappedByteBuffer byteBuffer = null;
		try {
			messageDigest = MessageDigest.getInstance("MD5");
			inputStream = new FileInputStream(file);
			channel = inputStream.getChannel();
			byteBuffer = channel.map(FileChannel.MapMode.READ_ONLY, 0, file.length());
			messageDigest.update(byteBuffer);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (channel != null) {
				try {
					channel.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return byteArrayToHex(messageDigest.digest());
	}
}
