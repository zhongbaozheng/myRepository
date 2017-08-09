package cn.meiqu.lainmonitor.hkvideo;

import com.sun.jna.Native;

/**
 * HCNetSDKJNAInstance和HKNetSDKByJNA是必备的，加上一些SDK的配置，最终实现HKSDK的初始化
 */
public enum HCNetSDKJNAInstance 
{	
	CLASS;
	private static HCNetSDKByJNA netSdk = null;
	/**
	 * get the instance of HCNetSDK
	 * @return the instance of HCNetSDK
	 */
	public static HCNetSDKByJNA getInstance()
	{
		if (null == netSdk)
		{
			synchronized (HCNetSDKByJNA.class)
			{
				netSdk = (HCNetSDKByJNA) Native.loadLibrary("hcnetsdk",
						HCNetSDKByJNA.class);
			}			
		}
		return netSdk;
	}
}