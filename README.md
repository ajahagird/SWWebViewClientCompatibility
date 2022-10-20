This example intends to demo handling of webview client with SW. Each tab represents one use case. You can find the server side code at https://glitch.com/edit/#!/unruly-zest-blossom. App has couple of components.
1. Main screen with Webview that loads an URL and installed passthrough service worker.
```
self.addEventListener("fetch", e => {
  if(e.request.url.indexOf('nosw=1') < 0) return;

  e.respondWith(
    fetch(e.request)
  );
});
```
2. WebviewClient that listens to page load events

### Steps
#### Tab 1 - Home
* Open the App. 
* It will register the service worker and reload the page. Look for "Service Worker Installed" confirmation. 
* Observe native WebViewClient notifications that we recieved both `onPageStarted` and `onPageFinished` event.

#### Tab 2 - Non SW 500
* Go to second tab.
* It loads the page, bypassing the service worker, that will send 500 HTTP status code. i.e https://unruly-zest-blossom.glitch.me/500?nosw=1
* You can verify the status code via Dev Tools.
* Observe that native WebViewClient notifications has additional entry of `onReceivedHttpError` apart from `onPageStarted` and `onPageFinished` event.

#### Tab 3 - SW 500
* Go to third tab.
* It loads the same page, that returns 500, but this time it will be intercepted and processed by Service Workers. i.e https://unruly-zest-blossom.glitch.me/500
* You can verify the status code via Dev Tools.
* Observe that native WebViewClient notifications doesn't have entry of `onReceivedHttpError` or any other error.