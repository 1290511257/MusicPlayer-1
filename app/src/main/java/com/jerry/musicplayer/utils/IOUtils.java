package com.jerry.musicplayer.utils;

import java.io.Closeable;
import java.io.IOException;
/**
 * 流处理类
 * @typename IOUtils
 * @author Jerry
 * @time 2015年1月27日 上午10:32:45
 * @version V1.0
 */
public class IOUtils {
	private final static String mTag = "IOUtils";
	/** 关闭流 */
	public static boolean close(Closeable io) {
		if (io != null) {
			try {
				io.close();
			} catch (IOException e) {
				LogUtils.e(mTag, e);
			}
		}
		return true;
	}
}
