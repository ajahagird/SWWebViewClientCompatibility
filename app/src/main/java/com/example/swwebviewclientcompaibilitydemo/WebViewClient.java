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
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.swwebviewclientcompaibilitydemo.R;

public class WebViewClient extends android.webkit.WebViewClient {
    private final Activity activity;

    public WebViewClient(Activity activity) {
        super();
        this.activity = activity;
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        Toast.makeText(view.getContext(), "onPageStarted", Toast.LENGTH_SHORT).show();
        Log.i("DemoWebViewClient","onPageStarted");
        super.onPageStarted(view, url, favicon);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        Toast.makeText(view.getContext(), "onPageFinished", Toast.LENGTH_SHORT).show();
        Log.i("DemoWebViewClient","onPageFinished");
        super.onPageFinished(view, url);
    }

    @Override
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
        Toast.makeText(view.getContext(), "onReceivedError", Toast.LENGTH_SHORT).show();
        Log.i("DemoWebViewClient","onReceivedError");
        super.onReceivedError(view, request, error);
    }

    @Override
    public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
        if (request.isForMainFrame()) {
            Toast.makeText(view.getContext(), "onReceivedHttpError", Toast.LENGTH_SHORT).show();
            Log.i("DemoWebViewClient","onReceivedHttpError");
        }
        super.onReceivedHttpError(view, request, errorResponse);
    }

    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        Toast.makeText(view.getContext(), "onReceivedSslError", Toast.LENGTH_SHORT).show();
        Log.i("DemoWebViewClient","onReceivedSslError");
        super.onReceivedSslError(view, handler, error);
    }
}
