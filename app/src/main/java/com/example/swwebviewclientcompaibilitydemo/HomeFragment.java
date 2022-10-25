package com.example.swwebviewclientcompaibilitydemo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ServiceWorkerController;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.swwebviewclientcompaibilitydemo.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final WebView webView = binding.webview;
        final WebSettings settings = webView.getSettings();
        settings.setUserAgentString("Amazon.com/22.21.0.100 (Android/10/Android SDK built for x86)");

        Recorder recorder = new Recorder();
        ServiceWorkerController swController = ServiceWorkerController.getInstance();
        swController.setServiceWorkerClient(new ServiceWorkerClient(recorder));

        webView.setWebViewClient(new WebViewClient(getActivity(), recorder));
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setJavaScriptEnabled(true);
        if (getArguments() != null) {
            final String url = HomeFragmentArgs.fromBundle(getArguments()).getUrl();
            webView.loadUrl(url);
        } else {
            webView.loadUrl("https://unruly-zest-blossom.glitch.me");
        }

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding.webview.destroy();
        binding = null;
    }
}