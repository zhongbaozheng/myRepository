package cn.meiqu.lainmonitor;

import android.content.Intent;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.DownloadListener;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;

import cn.meiqu.baseproject.baseUi.BaseFragment;
import cn.meiqu.baseproject.util.LogUtil;

/**
 * Created by Fatel on 16-5-11.
 */
public abstract class FragmentWeb extends BaseFragment {


    private WebView mWeb;
    private ProgressBar pBar;

    public WebView getmWeb() {
        return mWeb;
    }

    public void setmWeb(WebView mWeb) {
        this.mWeb = mWeb;
    }

    private void assignViews() {
        mWeb = (WebView) findViewById(cn.meiqu.baseproject.R.id.web);
        pBar = (ProgressBar) findViewById(cn.meiqu.baseproject.R.id.proB_loading);
        //
        mWeb.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        mWeb.getSettings().setLoadWithOverviewMode(true);
        mWeb.getSettings().setUseWideViewPort(true);
        mWeb.getSettings().setJavaScriptEnabled(true);
//        mWeb.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mWeb.setWebChromeClient(new WebChromeClient());
        mWeb.setWebViewClient(new WebViewClient());
        mWeb.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype,
                                        long contentLength) {
                LogUtil.log("url=" + url);
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        loadUrl();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (contain == null) {
            contain = inflater.inflate(R.layout.f_web, null);
            assignViews();
        }
        return contain;
    }

    public abstract void loadUrl();

    class WebViewClient extends android.webkit.WebViewClient {
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // 使用自己的WebView组件来响应Url加载事件，而不是使用默认浏览器器加载页面
            mWeb.loadUrl(url);
            // 消耗掉这个事件。Android中返回True的即到此为止吧,事件就会不会冒泡传递了，我们称之为消耗掉
            return true;
        }

//        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
//            LogUtil.log("-----------------onReceivedSslError");
//            //handler.cancel(); // Android默认的处理方式
//            handler.proceed();  // 接受所有网站的证书
//            //handleMessage(Message msg); // 进行其他处理
//        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.proceed();  // 接受所有网站的证书
            LogUtil.log("-----------------onReceivedSslError");
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            LogUtil.log("-----------------onReceivedError");
        }
    }

    public class WebChromeClient extends android.webkit.WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            // TODO Auto-generated method stub
            super.onProgressChanged(view, newProgress);
            pBar.setProgress(newProgress);
            if (newProgress == 100)
                pBar.setVisibility(View.GONE);
            else
                pBar.setVisibility(View.VISIBLE);
        }

        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            Log.d("ANDROID_LAB", "TITLE=" + title);
//            initTitle(title);
        }
    }

    @Override
    public void onHttpHandle(String action, String data) {

    }

    @Override
    public void onResume() {
        super.onResume();
        mWeb.resumeTimers();
    }

    @Override
    public void onPause() {
        super.onPause();
        mWeb.pauseTimers();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mWeb.destroy();
    }
}
