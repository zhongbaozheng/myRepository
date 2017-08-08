package cn.meiqu.lainmonitor.hkvideo;

import android.graphics.PixelFormat;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import com.hikvision.netsdk.HCNetSDK;
import com.hikvision.netsdk.NET_DVR_DEVICEINFO_V30;
import com.hikvision.netsdk.NET_DVR_PLAYBACK_INFO;
import com.hikvision.netsdk.NET_DVR_PREVIEWINFO;
import com.hikvision.netsdk.NET_DVR_STREAM_INFO;
import com.hikvision.netsdk.NET_DVR_TIME;
import com.hikvision.netsdk.NET_DVR_VOD_PARA;
import com.hikvision.netsdk.PTZCommand;
import com.hikvision.netsdk.PlaybackControlCommand;
import com.hikvision.netsdk.RealPlayCallBack;

import org.MediaPlayer.PlayM4.Player;

import cn.meiqu.baseproject.util.Config;

/**
 * 辅助播放类
 * @author lzc
 *
 */
public class PlayAssistant implements Callback
{

	private static String TAG = "lzc";
	private int m_iPort = -1;
	private int m_iLogID = -1;
	public int m_iPlayID = -1;
	private int m_iStartChan = 0; // start channel no
	private NET_DVR_DEVICEINFO_V30 m_oNetDvrDeviceInfoV30 = null;
	private SurfaceView mSurfaceView;

	private int m_iPlaybackID = -1; // return by NET_DVR_PlayBackByTime


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
	 * 设置回放参数
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public NET_DVR_VOD_PARA  setPlayBack(NET_DVR_TIME startTime,NET_DVR_TIME endTime){

		NET_DVR_STREAM_INFO net_dvr_stream_info = new NET_DVR_STREAM_INFO();
		net_dvr_stream_info.byID = new byte[6*1024];
		net_dvr_stream_info.dwChannel = m_iStartChan;

		NET_DVR_VOD_PARA net_dvr_vod_para = new NET_DVR_VOD_PARA();
		net_dvr_vod_para.hWnd = mSurfaceView.getHolder().getSurface();
		net_dvr_vod_para.struBeginTime = startTime;
		net_dvr_vod_para.struEndTime  = endTime;
		net_dvr_vod_para.byStreamType = 1;
		net_dvr_vod_para.struIDInfo = net_dvr_stream_info;

		return net_dvr_vod_para;
	}

	/**
	 * 开始回放  参数为 setPlayBack()
	 */
	public void playback(NET_DVR_VOD_PARA para,int loginId){

		m_iLogID = loginId;

		if (m_iLogID < 0) {
			Log.e(TAG, " login falied , net sdk playback failed!");
			return;
		}

		m_iPlaybackID = HCNetSDK.getInstance().NET_DVR_PlayBackByTime_V40(m_iLogID, para);

		if (m_iPlaybackID >= 0) {
			NET_DVR_PLAYBACK_INFO struPlaybackInfo = null;
			if (!HCNetSDK
					.getInstance()
					.NET_DVR_PlayBackControl_V40(
							m_iPlaybackID,
							PlaybackControlCommand.NET_DVR_PLAYSTART,
							null, 0, struPlaybackInfo)) {
				Log.e(TAG, "net sdk playback start failed!");
				return;
			}
		}

	}

	/**
	 * 登录功能
	 * @param ip
	 * @param port
	 * @param user
	 * @param psd
	 * @param channel
	 * @return
	 */
	public int  login(String ip, int port, String user, String psd, final int channel){
		return loginNormalDevice(ip, port, user, psd, channel);
	}

	/**
	 * 暂停回放
	 */
	public void stopPlayBack(){

		if(m_iPlaybackID>=0){

			NET_DVR_PLAYBACK_INFO struPlaybackInfo = null;
			if (!HCNetSDK
					.getInstance()
					. NET_DVR_StopPlayBack(m_iPlaybackID)) {
				Log.e(TAG,"net sdk playback stop failed!");
				return;
			}
		}

	}

	/**
	 * 恢复下载
	 */
	public void goAheadPlayBack(){

		if(m_iPlaybackID>=0){
			NET_DVR_PLAYBACK_INFO struPlaybackInfo = null;
			Log.e("goAheadPlayBack","goAheadPlayBack");
			if (!HCNetSDK
					.getInstance()
					.NET_DVR_PlayBackControl_V40(
							m_iPlaybackID,
							PlaybackControlCommand.NET_DVR_PLAYRESTART ,
							null, 0, struPlaybackInfo)) {
				Log.e(TAG,"net sdk playback go ahead failed");
				return;
			}
		}
	}

	/**
	 * 停止下载录像文件
	 */
	public void poausPlayBack(){

		if(m_iPlaybackID>=0){
			NET_DVR_PLAYBACK_INFO struPlaybackInfo = null;
			Log.e("poausPlayBack","poausPlayBack");
			if (!HCNetSDK
					.getInstance()
					.NET_DVR_PlayBackControl_V40(
							m_iPlaybackID,
							PlaybackControlCommand.NET_DVR_PLAYPAUSE ,
							null, 0, struPlaybackInfo)) {
				Log.e(TAG,"net sdk playback go ahead failed");
				return;
			}
		}
	}

	/**
	 * seek playBack Listen,进度条播放
	 * @param startTime
	 * @param endTime
	 * @param loginId
	 */
	public void seekPlayBack(NET_DVR_TIME startTime,NET_DVR_TIME endTime,int loginId){
		playback(setPlayBack(startTime,endTime),loginId);
	}

	/**
	 * 开始播放
	 */
	public void play(String ip, int port, String user, String psd, final int channel) {

			if (m_iPlayID >= 0) {
				// 1.若正在播放则停止播放
				if (!HCNetSDK.getInstance().NET_DVR_StopRealPlay(m_iPlayID)) {
					Log.e(TAG, "StopRealPlay is failed!Err:" + HCNetSDK.getInstance().NET_DVR_GetLastError());
					return;
				}
				stopPlayer();
			}

			if (m_iLogID >= 0) {
				// 1.若已经登录 则退出登录
				if (!HCNetSDK.getInstance().NET_DVR_Logout_V30(m_iLogID)) {
					Log.e(TAG, " NET_DVR_Logout is failed!");
					return;
				}
				m_iLogID = -1;
			}

				// l.登录设备初始化
				m_iLogID = loginNormalDevice(ip, port, user, psd, channel);
				if (m_iLogID < 0) {
					Log.e("lzc", "This device logins failed!");
					return;
				}
				Log.e(TAG, "Login sucess ****************************1***************************");
				// 2.初始化成功，开始准备播放
				if (m_iPlayID < 0) {
					//开始播放
					new Thread(new Runnable() {
						@Override
						public void run() {
							startSinglePreview(channel + 32);
						}
					}).start();
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


	//暂停播放
	public void stopPlay(){
		if (m_iPlayID >= 0 )
		{
			// // 1.若正在播放则停止播放
			if (!HCNetSDK.getInstance().NET_DVR_StopRealPlay(m_iPlayID) )
			{
				Log.e(TAG, "StopRealPlay is failed!Err:" + HCNetSDK.getInstance().NET_DVR_GetLastError());
				return;
			}
			m_iPlayID = -1;
		}
	}


	//开始录像
	public void startRecord(){

		if(m_iPlayID>=0){

			if(HCNetSDK.getInstance().NET_DVR_SaveRealData(m_iPlayID,
					"/mnt/sdcard/surface/"+ Config.getInt("channel")+".mp4")){
				Log.e(TAG,"record success");
			}else{
				Log.e(TAG,"record failed");
			}
		}

	}


	//停止并保存录像文件
	public void stopRecord(){

		if(m_iPlayID>=0){

			if(HCNetSDK.getInstance().NET_DVR_StopSaveRealData(m_iPlayID)){
				Log.e(TAG,"stop record success");
			}else{
				Log.e(TAG,"stop record failed");
			}
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
		previewInfo.dwStreamType = 0; // substream
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

	//上下左右   左上4   右上 5  左下6 右下 7
	//0 1 2 3
	public void startPTZControl(int contrl){
		if(m_iLogID<0){
			Log.e(TAG,"login failed");
		}else {
			if(contrl == 0){
				if(!HCNetSDK.getInstance().NET_DVR_PTZControl_Other(m_iLogID,m_iStartChan, PTZCommand.TILT_UP,0)){
					Log.e(TAG,"up control failed");
				}
			}else if(contrl == 1){
				if(!HCNetSDK.getInstance().NET_DVR_PTZControl_Other(m_iLogID,m_iStartChan, PTZCommand.TILT_DOWN,0)){
					Log.e(TAG,"down control failed");
				}
			}else if(contrl == 2){
				if(!HCNetSDK.getInstance().NET_DVR_PTZControl_Other(m_iLogID,m_iStartChan, PTZCommand.PAN_LEFT,0)){
					Log.e(TAG,"left control failed");
				}
			}else if(contrl == 3){
				if(!HCNetSDK.getInstance().NET_DVR_PTZControl_Other(m_iLogID,m_iStartChan, PTZCommand.PAN_RIGHT,0)){
					Log.e(TAG,"right control failed");
				}
			}else if(contrl == 4){
				if(!HCNetSDK.getInstance().NET_DVR_PTZControl_Other(m_iLogID,m_iStartChan, PTZCommand.UP_LEFT,0)){
					Log.e(TAG,"right control failed");
				}
			}else if(contrl == 5){
				if(!HCNetSDK.getInstance().NET_DVR_PTZControl_Other(m_iLogID,m_iStartChan, PTZCommand.UP_RIGHT,0)){
					Log.e(TAG,"right control failed");
				}
			}else if(contrl == 6){
				if(!HCNetSDK.getInstance().NET_DVR_PTZControl_Other(m_iLogID,m_iStartChan, PTZCommand.DOWN_LEFT ,0)){
					Log.e(TAG,"right control failed");
				}
			}else if(contrl == 7){
				if(!HCNetSDK.getInstance().NET_DVR_PTZControl_Other(m_iLogID,m_iStartChan, PTZCommand.DOWN_RIGHT,0)){
					Log.e(TAG,"right control failed");
				}
			}
		}
	}


	//上下左右
	//0 1 2 3
	public void stopPTZControl(int contrl){
		if(m_iLogID<0){
			Log.e(TAG,"login failed");
		}else {
			if(contrl == 0){
				if(!HCNetSDK.getInstance().NET_DVR_PTZControl_Other(m_iLogID,m_iStartChan, PTZCommand.TILT_UP,1)){
					Log.e(TAG,"up control failed");
				}
			}else if(contrl == 1){
				if(!HCNetSDK.getInstance().NET_DVR_PTZControl_Other(m_iLogID,m_iStartChan, PTZCommand.TILT_DOWN,1)){
					Log.e(TAG,"down control failed");
				}
			}else if(contrl == 2){
				if(!HCNetSDK.getInstance().NET_DVR_PTZControl_Other(m_iLogID,m_iStartChan, PTZCommand.PAN_LEFT,1)){
					Log.e(TAG,"left control failed");
				}
			}else if(contrl == 3){
				if(!HCNetSDK.getInstance().NET_DVR_PTZControl_Other(m_iLogID,m_iStartChan, PTZCommand.PAN_RIGHT,1)){
					Log.e(TAG,"right control failed");
				}
			}else if(contrl == 4){
				if(!HCNetSDK.getInstance().NET_DVR_PTZControl_Other(m_iLogID,m_iStartChan, PTZCommand.UP_LEFT,1)){
					Log.e(TAG,"right control failed");
				}
			}else if(contrl == 5){
				if(!HCNetSDK.getInstance().NET_DVR_PTZControl_Other(m_iLogID,m_iStartChan, PTZCommand.UP_RIGHT,1)){
					Log.e(TAG,"right control failed");
				}
			}else if(contrl == 6){
				if(!HCNetSDK.getInstance().NET_DVR_PTZControl_Other(m_iLogID,m_iStartChan, PTZCommand.DOWN_LEFT ,1)){
					Log.e(TAG,"right control failed");
				}
			}else if(contrl == 7){
				if(!HCNetSDK.getInstance().NET_DVR_PTZControl_Other(m_iLogID,m_iStartChan, PTZCommand.DOWN_RIGHT,1)){
					Log.e(TAG,"right control failed");
				}
			}
		}
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
