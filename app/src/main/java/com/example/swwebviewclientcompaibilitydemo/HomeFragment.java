package com.example.swwebviewclientcompaibilitydemo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        webView.setWebViewClient(new WebViewClient(getActivity()));
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setJavaScriptEnabled(true);
        if(getArguments() != null) {
            final String url = HomeFragmentArgs.fromBundle(getArguments()).getUrl();
            webView.loadUrl(url);
        } else {
            webView.loadUrl("https://cactus-fixed-value.glitch.me");
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