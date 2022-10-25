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
* It will register the service worker and reload the page. Look for "Service Worker Installed" confirmation. It will by default install without nav preload.
* If this was your first app-start, this page load would be likely without service worker installed.
* Observe native WebViewClient notifications of `shouldInterceptRequest` is received with `isMainFrameRequest=true`

#### Tab 2 - Non SW 200
* Go to second tab.
* It loads the page, bypassing the fetch event interception using query parameter, that will send 200 HTTP status code. i.e https://unruly-zest-blossom.glitch.me?nosw=1. This query param is read in fetch handler and not responded with.
* Observe native WebViewClient notifications of `shouldInterceptRequest` is received with `isMainFrameRequest=true`


#### Tab 3 - SW 200
* Go to third tab.
* It loads the same page, that returns 200, but this time it will be intercepted and processed by Service Workers. i.e https://unruly-zest-blossom.glitch.me
* You can verify the status code via Dev Tools.
* Observe native ServiceWorkerClient notifications of `shouldInterceptRequest` is received with `isMainFrameRequest=false`. Note that when fetch is intercepted `respondWith`, then we receive notification at ServiceWorkerClient rather than at WebViewClient for non nav preload case.


#### With NP, Tab 3 - SW 200
* On the third tab, click on Enable NP. Wait for 2 seconds till it annouces that "Service Worker Installed" with NP.
* This actually installs different copy of service worker code that enables NP, reads nav preload response with fallback.
```
self.addEventListener("fetch", function (event) {
  if (event.request.url.indexOf('nosw=1') > -1) {
    console.log("[SW] nosw=1 detected. ignoring.. ", event.request.url);
    return;
  }
  
  console.log("[SW] fetching.." , event.request.url);
  event.respondWith(async function() {
    // Else, use the preloaded response, if it's there
    const response = await event.preloadResponse;
    if (response) return response;

    // Else try the network.
    return fetch(event.request);
  }());
});
```
* Refresh the page by clicking on third tab again. 
* Observe native WebViewClient notifications of `shouldInterceptRequest` is received with `isMainFrameRequest=true`, but ServiceWorkerClient notifications of `shouldInterceptRequest` is received with `isMainFrameRequest=false`