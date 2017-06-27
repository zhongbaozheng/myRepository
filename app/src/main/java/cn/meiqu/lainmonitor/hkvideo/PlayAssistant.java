package cn.meiqu.lainmonitor.hkvideo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.widget.ImageView;

import com.hikvision.netsdk.HCNetSDK;
import com.hikvision.netsdk.NET_DVR_DEVICEINFO_V30;
import com.hikvision.netsdk.NET_DVR_JPEGPARA;
import com.hikvision.netsdk.NET_DVR_PREVIEWINFO;
import com.hikvision.netsdk.RealPlayCallBack;

import org.MediaPlayer.PlayM4.Player;

import java.io.File;

/**
 * 辅助播放类
 * 
 * @author lzc
 *
 */
public class PlayAssistant implements Callback
{

	private static String TAG = "lzc";
	private int m_iPort = -1;
	private int m_iLogID = -1;
	private int m_iPlayID = -1;
	private int m_iStartChan = 0; // start channel no
	private NET_DVR_DEVICEINFO_V30 m_oNetDvrDeviceInfoV30 = null;
	private SurfaceView mSurfaceView;
	private ImageView mImage;
	private boolean play = true;

	public PlayAssistant(ImageView imageView ,boolean isPlay){
		super();
		mImage = imageView;
		play = isPlay;

	}

	public PlayAssistant(SurfaceView mSurfaceView)
	{
		super();
		this.mSurfaceView = mSurfaceView;
		this.mSurfaceView.getHolder().addCallback(this);
	}

	public boolean isRealPlay()
	{
		if (m_iLogID >= 0 || m_iPlayID >= 0 )
		{
			// 只要有一个大于0即认为登录
			return true;
		}
		else
		{
			return false;
		}
	}

	/**
	 * 开始播放
	 */
	public void play(String ip, int port, String user, String psd, int channel,int hasCoverPosition)
	{

		try
		{

			if (m_iPlayID >= 0 )
			{
				// 1.若正在播放则停止播放
				if (!HCNetSDK.getInstance().NET_DVR_StopRealPlay(m_iPlayID) )
				{
					Log.e(TAG, "StopRealPlay is failed!Err:" + HCNetSDK.getInstance().NET_DVR_GetLastError());
					return;
				}
				stopPlayer();
			}

			if (m_iLogID >= 0 )
			{
				// 1.若已经登录 则退出登录
				if (!HCNetSDK.getInstance().NET_DVR_Logout_V30(m_iLogID) )
				{
					Log.e(TAG, " NET_DVR_Logout is failed!");
					return;
				}
				m_iLogID = -1;
			}
			if(hasCoverPosition>=16){

				Log.e("hasCoverPosition",hasCoverPosition+"");
				String myJpgPath = "/mnt/sdcard/surface/" + channel + ".jpg";
				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inSampleSize = 2;
				Bitmap bm = BitmapFactory.decodeFile(myJpgPath, options);
				mImage.setImageBitmap(bm);

			}else{

			// l.登录设备初始化
			m_iLogID = loginNormalDevice(ip, port, user, psd, channel);
			if (m_iLogID < 0 )
			{
				Log.e("lzc", "This device logins failed!");
				return;
			}
			Log.e(TAG, "Login sucess ****************************1***************************");
			// 2.初始化成功，开始准备播放
			if (m_iPlayID < 0 )
			{
				if(play){

					 //开始播放
				startSinglePreview(channel + 32);

				}

			}

			if(m_iLogID>=0 && !play){
				NET_DVR_JPEGPARA net_dvr_jpegpara = new NET_DVR_JPEGPARA();
				net_dvr_jpegpara.wPicSize = 1280*720;
				net_dvr_jpegpara.wPicQuality = 1;
				File file = new File("/mnt/sdcard/surface/");
				if(!file.exists()){
					file.mkdir();
					Log.e("mkdir","make success");
				}
				if(file.exists()) {
					if (HCNetSDK.getInstance().NET_DVR_CaptureJPEGPicture(m_iLogID, channel + 32, net_dvr_jpegpara, "/mnt/sdcard/surface/" + channel + ".jpg")) {

						String myJpgPath = "/mnt/sdcard/surface/" + channel + ".jpg";
						BitmapFactory.Options options = new BitmapFactory.Options();
						options.inSampleSize = 2;
						Bitmap bm = BitmapFactory.decodeFile(myJpgPath, options);
						mImage.setImageBitmap(bm);

						Log.e(TAG, "capture success");
					} else {
						Log.e(TAG, "capture error!");
					}

				}
			}

			}
		}
		catch (Exception err)
		{
			Log.e(TAG, "error: " + err.toString());
		}
	}

	/** ͣ 停止播放并退出 */
	public void stop()
	{
		if (m_iPlayID >= 0 )
		{
			// // 1.若正在播放则停止播放
			if (!HCNetSDK.getInstance().NET_DVR_StopRealPlay(m_iPlayID) )
			{
				Log.e(TAG, "StopRealPlay is failed!Err:" + HCNetSDK.getInstance().NET_DVR_GetLastError());
				return;
			}
			stopPlayer();
		}
		if (m_iLogID >= 0 )
		{
			// 1.若已经登录 则退出登录
			if (!HCNetSDK.getInstance().NET_DVR_Logout_V30(m_iLogID) )
			{
				Log.e(TAG, " NET_DVR_Logout is failed!");
				return;
			}
			m_iLogID = -1;
		}
	}

	/** 停止播放器 */
	private void stopPlayer()
	{
		// player stop play
		if (!Player.getInstance().stop(m_iPort) )
		{
			Log.e(TAG, "stop is failed!");
			return;
		}

		if (!Player.getInstance().closeStream(m_iPort) )
		{
			Log.e(TAG, "closeStream is failed!");
			return;
		}

		if (!Player.getInstance().freePort(m_iPort) )
		{
			Log.e(TAG, "freePort is failed!" + m_iPort);
			return;
		}
		System.out.println("mIport: " + m_iPort + "mIPlayID: " + m_iPlayID + "m_ILogID: " + m_iLogID);
		m_iPort = -1;
		m_iPlayID = -1;
	}

	/** 开始播放 */
	private void startSinglePreview(int channel)
	{
		RealPlayCallBack fRealDataCallBack = getRealPlayerCbf();
		Log.i(TAG, "m_iStartChan:" + m_iStartChan);
		// 预览对象
		NET_DVR_PREVIEWINFO previewInfo = new NET_DVR_PREVIEWINFO();
		// 通道号
		previewInfo.lChannel = m_iStartChan;
		Log.i(TAG, "lChannel:" + previewInfo.lChannel);
		// 码流类型 0主码 1子码 2码流
		previewInfo.dwStreamType = 1; // substream
		// 阻塞取流1和非阻塞取流0
		previewInfo.bBlocked = 1;
		// 开启实时播放
		m_iPlayID = HCNetSDK.getInstance().NET_DVR_RealPlay_V40(m_iLogID, previewInfo, fRealDataCallBack);
		if (m_iPlayID < 0 )
		{
			Log.e(TAG, "NET_DVR_RealPlay is failed!Err:" + HCNetSDK.getInstance().NET_DVR_GetLastError());
			return;
		}
		Log.e(TAG, "NetSdk Play sucess ***********************3***************************");
	}

	/** 将播放流实时传送 */
	public void processRealData(int iPlayViewNo, int iDataType, byte[] pDataBuffer, int iDataSize,
			int iStreamMode)
	{
		if (HCNetSDK.NET_DVR_SYSHEAD == iDataType )
		{
			if (m_iPort >= 0 )
			{
				return;
			}
			m_iPort = Player.getInstance().getPort();
			Log.e(TAG,m_iPort+"");
			if (m_iPort == -1 )
			{
				Log.e(TAG, "getPort is failed with: " + Player.getInstance().getLastError(m_iPort));
				return;
			}
			Log.i(TAG, "getPort succ with: " + m_iPort);
			if (iDataSize > 0 )
			{
				if (!Player.getInstance().setStreamOpenMode(m_iPort, iStreamMode) )
				{
					Log.e(TAG, "setStreamOpenMode failed");
					return;
				}
				if (!Player.getInstance().openStream(m_iPort, pDataBuffer, iDataSize, 6 * 1024 * 1024) ) // open
																											// stream
				{
					Log.e(TAG, "openStream failed");
					return;
				}
				// 6 自适应模式
				if (!Player.getInstance().setDisplayBuf(m_iPort, 6) )
				{
					Log.e(TAG, "setDisplayBuf failed");
				}
				if (!Player.getInstance().play(m_iPort, mSurfaceView.getHolder()) )
				{
					Log.e(TAG, "play failed");
					return;
				}
				// 开启声音播放
				// Player.getInstance().playSound(m_iPort)
			}
		}
		else
		{
			Player.getInstance().inputData(m_iPort, pDataBuffer, iDataSize);
		}

	}

	private RealPlayCallBack getRealPlayerCbf()
	{
		RealPlayCallBack cbf = new RealPlayCallBack()
		{
			public void fRealDataCallBack(int iRealHandle, int iDataType, byte[] pDataBuffer, int iDataSize)
			{
				// player channel 1
				processRealData(1, iDataType, pDataBuffer, iDataSize, Player.STREAM_REALTIME);
			}
		};
		return cbf;
	}

	public void Cleanup()
	{
		stop();

		// release net SDK resource
		HCNetSDK.getInstance().NET_DVR_Cleanup();
	}

	/** 登录设备*/
	private int loginNormalDevice(String strIP, int nPort, String strUser, String strPsd, int channel)
	{
		// get instance
		m_oNetDvrDeviceInfoV30 = new NET_DVR_DEVICEINFO_V30();
		if (null == m_oNetDvrDeviceInfoV30 )
		{
			Log.e(TAG, "HKNetDvrDeviceInfoV30 new is failed!");
			return -1;
		}
		// call NET_DVR_Login_v30 to login on, port 8000 as default
		int iLogID = HCNetSDK.getInstance().NET_DVR_Login_V30(strIP, nPort, strUser, strPsd,
				m_oNetDvrDeviceInfoV30);
		if (iLogID < 0 )
		{
			Log.e(TAG, "NET_DVR_Login is failed!Err:" + HCNetSDK.getInstance().NET_DVR_GetLastError());
			return -1;
		}
		// logcat.toast("shebei: "+m_oNetDvrDeviceInfoV30.wDevType);
		// logcat.toast("shebei: "+m_oNetDvrDeviceInfoV30.byStartDChan);
		// logcat.toast("shebei: "+m_oNetDvrDeviceInfoV30.byDVRType);
		if (m_oNetDvrDeviceInfoV30.byChanNum > 0 )
		{
			// m_oNetDvrDeviceInfoV30.byStartChan
			m_iStartChan = channel;
		}
		else if (m_oNetDvrDeviceInfoV30.byIPChanNum > 0 )
		{
			// m_oNetDvrDeviceInfoV30.byStartDChan
			m_iStartChan = channel + 32;
			Log.e("m_iStartChan",m_iStartChan+"");
		}

		Log.i(TAG, "NET_DVR_Login is Successful!");

		return iLogID;
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder)
	{
		mSurfaceView.getHolder().setFormat(PixelFormat.TRANSLUCENT);
		Log.i(TAG, "surface is created" + m_iPort);
		if (-1 == m_iPort )
		{
			return;
		}
		Surface surface = holder.getSurface();
		if (true == surface.isValid() )
		{
			if (false == Player.getInstance().setVideoWindow(m_iPort, 0, holder) )
			{
				Log.e(TAG, "Player setVideoWindow failed!");
			}
		}

	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
	{
		mSurfaceView.setZOrderOnTop(true);
		mSurfaceView.setZOrderMediaOverlay(true);

		mSurfaceView.getHolder().setFormat(PixelFormat.TRANSLUCENT);

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder)
	{
		Log.i(TAG, "Player setVideoWindow release!" + m_iPort);
		if (-1 == m_iPort )
		{
			return;
		}
		if (true == holder.getSurface().isValid() )
		{
			if (false == Player.getInstance().setVideoWindow(m_iPort, 0, null) )
			{
				Log.e(TAG, "Player setVideoWindow failed!");
			}
		}

	}
}
