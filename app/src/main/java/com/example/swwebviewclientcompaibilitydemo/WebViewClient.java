package com.example.swwebviewclientcompaibilitydemo;

import android.app.Activity;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.util.Log;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;

import java.net.MalformedURLException;
import java.net.URL;

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
        recorder.addMessage("onPageStarted - " + getPath(view.getUrl()));
        super.onPageStarted(view, url, favicon);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        Log.i("DemoWebViewClient","onPageFinished");
        recorder.addMessage("onPageFinished - " + getPath(view.getUrl()));

        // replay all the received events
        for (String message: recorder.getAllRecordedMessages()) {
            view.loadUrl("javascript:showNativeNotification('"+ message+"')");
        }

        super.onPageFinished(view, url);
    }

    @Override
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
        Log.i("DemoWebViewClient","onReceivedError");
        recorder.addMessage("onReceivedError - " + getPath(view.getUrl()));
        super.onReceivedError(view, request, error);
    }

    @Override
    public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
        if (request.isForMainFrame()) {
            Log.i("DemoWebViewClient","onReceivedHttpError");
            recorder.addMessage("onReceivedHttpError - " + getPath(view.getUrl()));
        }
        super.onReceivedHttpError(view, request, errorResponse);
    }

    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        Log.i("DemoWebViewClient","onReceivedSslError");
        recorder.addMessage("onReceivedSslError - " + getPath(view.getUrl()));
        super.onReceivedSslError(view, handler, error);
    }

    static String getPath(String incomingUrl) {
        try {
            URL url = new URL(incomingUrl);
            return url.getPath();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return incomingUrl;
    }
}
