package com.example.swwebviewclientcompaibilitydemo;

import static com.example.swwebviewclientcompaibilitydemo.Utils.getPath;
import static com.example.swwebviewclientcompaibilitydemo.Utils.printRequest;

import android.app.Activity;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.util.Log;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;

import androidx.annotation.Nullable;

public class WebViewClient extends android.webkit.WebViewClient {
    private final Activity activity;
    private final Recorder recorder;

    public WebViewClient(Activity activity, Recorder recorder) {
        super();
        this.activity = activity;
        this.recorder = recorder;
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        Log.i("DemoWebViewClient","onPageStarted");
        super.onPageStarted(view, url, favicon);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        Log.i("DemoWebViewClient","onPageFinished");

        // replay all the received events
        for (String message: recorder.getAllRecordedMessages()) {
            view.loadUrl("javascript:showNativeNotification('"+ message+"')");
        }
        recorder.reset();

        super.onPageFinished(view, url);
    }

    @Override
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
        Log.i("DemoWebViewClient","onReceivedError");
        super.onReceivedError(view, request, error);
    }

    @Override
    public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
        if (request.isForMainFrame()) {
            Log.i("DemoWebViewClient","onReceivedHttpError");
        }
        super.onReceivedHttpError(view, request, errorResponse);
    }

    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        Log.i("DemoWebViewClient","onReceivedSslError");
        super.onReceivedSslError(view, handler, error);
    }


    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        Log.i("DemoWebViewClient","shouldOverrideUrlLoading - " + printRequest(request));
        return super.shouldOverrideUrlLoading(view, request);
    }

    @Override
    public void onLoadResource(WebView view, String url) {
        Log.i("DemoWebViewClient","onLoadResource - " + getPath(view.getUrl()));
        super.onLoadResource(view, url);
    }

    @Nullable
    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
        Log.i("DemoWebViewClient","shouldInterceptRequest - " + printRequest(request));
        if(request.isForMainFrame()) {
            recorder.addMessage("WebViewClient#shouldInterceptRequest, path=" + request.getUrl().getPath() + ", isMainFrame=" + request.isForMainFrame());
        }
        return super.shouldInterceptRequest(view, request);
    }
}
