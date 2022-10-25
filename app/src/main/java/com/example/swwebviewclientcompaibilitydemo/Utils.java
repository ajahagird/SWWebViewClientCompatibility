package com.example.swwebviewclientcompaibilitydemo;

import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;

import java.net.MalformedURLException;
import java.net.URL;

public class Utils {
    static void safeSleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static String printRequest(WebResourceRequest request) {
        return "["
                + "redirect=" + request.isRedirect()
                + ",url=" +request.getUrl()
                +",isForMainFrame=" + request.isForMainFrame()
                +",method=" + request.getMethod()
                +",requestHeaders=" + request.getRequestHeaders()
                +"]";
    }

    static String printWebResourceError(WebResourceError error) {

        return "["
                + "errorCode=" + error.getErrorCode()
                + ",description=" + error.getDescription()
                +"]";
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
