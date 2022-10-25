package com.example.swwebviewclientcompaibilitydemo;

import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;

import androidx.annotation.Nullable;

public class ServiceWorkerClient extends android.webkit.ServiceWorkerClient {

    private Recorder recorder;

    public ServiceWorkerClient(Recorder recorder) {
        this.recorder = recorder;
    }

    @Nullable
    @Override
    public WebResourceResponse shouldInterceptRequest(WebResourceRequest request) {
        Log.i("DemoServiceWorkerClient","shouldInterceptRequest request = " + Utils.printRequest(request));
        recorder.addMessage("ServiceWorkerClient#shouldInterceptRequest, path=" + request.getUrl().getPath() + ", isMainFrame=" + request.isForMainFrame());
        return super.shouldInterceptRequest(request);
    }
}