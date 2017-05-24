package cn.meiqu.baseproject.httpGet;

import android.util.Log;

import com.loopj.android.http.RequestParams;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import cn.meiqu.baseproject.API;
import cn.meiqu.baseproject.util.LogUtil;


//public class HttpGetBase extends AsyncHttpResponseHandler {
public class HttpGetBase {

    public IHttpResponListener httpResponListener;
    public String uri = "";
    public RequestParams params;
    public String action = "";

    //    public static AsyncHttpClient client = new AsyncHttpClient();
    public static HttpGetBase newInstance() {
        return new HttpGetBase();
    }

    HttpGetBase() {

    }

    //    @Override
//    public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
//        String object = null;
//        try {
//            object = new String(responseBody, "UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        LogUtil.log("服务器成功返回-url----" + uri + "--数据为---" + object);
//        if (object != null && object.startsWith("\ufeff")) {
//            object = object.substring(1);
//        }
//        httpResponListener.onHttpRespon(action + uri, object);
//    }
//
//    @Override
//    public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
//        if (responseBody != null)
//            LogUtil.log("服务器失败返回-url----" + uri + "--数据为---" + new String(responseBody));
//        else
//            LogUtil.log("服务器失败返回-url----" + uri + "--数据为---");
//        httpResponListener.onHttpRespon(action + uri, null);
//    }
//
//
//    public static HttpGetBase newInstance() {
//        client.setTimeout(15000);
//        return new HttpGetBase();
//    }
//
//
//    public void get(String uri, RequestParams params, String action,
//                    IHttpResponListener httpResponListener) {
//        this.uri = uri;
//        getBase(API.getAbsolutePath(uri), params, httpResponListener, action, true);
//    }
//
//    public void post(String uri, RequestParams params, String action,
//                     IHttpResponListener httpResponListener) {
//        this.uri = uri;
//        getBase(API.getAbsolutePath(uri), params, httpResponListener, action, false);
//    }
//
//    public void get(String host, String uri, RequestParams params, String action,
//                    IHttpResponListener httpResponListener) {
//        this.uri = uri;
//        getBase(host + uri, params, httpResponListener, action, true);
//    }
//
//    public void getBase(String url, RequestParams params, IHttpResponListener httpResponListene, String action, boolean isGet) {
//        this.httpResponListener = httpResponListene;
//        this.action = action;
//        LogUtil.log("requestParam=" + params.toString());
//        if (isGet)
//            client.get(url, params, this);
//        else
//            client.post(url, params, this);
//    }


    /*图片上传
        MultipartBuilder builder = new MultipartBuilder().type(MultipartBuilder.FORM);
            builder.addPart(new FormEncodingBuilder().add("name", "value").build());//一般参数提交
            builder.addPart(Headers.of("Content-Disposition", "form-data; name=\"key\""), RequestBody.create(MediaType.parse("image/*"), new File("path")));//图片提交
     */
    public static OkHttpClient okHttpClient = new OkHttpClient();

    public Call post(String uri, FormEncodingBuilder builder, String action) {
        this.uri = uri;
        this.action = action;
        String url = API.getAbsolutePath(uri);
        return call(url, builder, false);
    }

    public Call get(String uri, String param, String action) {
        this.uri = uri;
        this.action = action;
        String url = API.getAbsolutePath(uri) + "?" + param;
        Log.e("url",url);
        return call(url, null, true);
    }

    private Call call(String url, FormEncodingBuilder builder, boolean isGet) {
        Request request;
        LogUtil.log("请求" + url);
        url = url.replaceAll(" ", "%20");
//        try {
//            url = new String(url.getBytes( "UTF-8"));
//            url=new String(url.getBytes(),"UTF-8");
//            url = new String(url.getBytes("GBK"));
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
        try {
            if (isGet) {
                request = new Request.Builder()
                        .url(url)
                        .get().build();
            } else {
                if (builder != null) {
                    request = new Request.Builder()
                            .url(url)
                            .post(builder.build()).build();
                } else {
                    request = new Request.Builder()
                            .url(url)
                            .post(null).build();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.log("请求发起失败" + e.toString());
            return null;
        }
        Call callback = okHttpClient.newCall(request);
        callback.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                LogUtil.log("服务器失败返回-url----" + HttpGetBase.this.uri + "--数据为---" + e.toString());
                HttpResponController.getInstance().onHttpRespon(HttpGetBase.this.action + HttpGetBase.this.uri, null);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                if (response.isSuccessful()) {
                    String data = response.body().string();
                    LogUtil.log("ok----data=" + data);
                    HttpResponController.getInstance().onHttpRespon(HttpGetBase.this.action + HttpGetBase.this.uri, data);
                } else {
                    LogUtil.log("服务器失败返回-url----" + HttpGetBase.this.uri + "--数据为---" + response);
                    HttpResponController.getInstance().onHttpRespon(HttpGetBase.this.action + HttpGetBase.this.uri, null);
                }

            }
        });
        return callback;
    }
}
