This example intends to demo handling of webview client with SW. Each tab represents one use case. You can find the server side code at https://glitch.com/edit/#!/cactus-fixed-value. App has couple of components.
1. Main screen with Webview that loads an URL
2. WebviewClient that listens to page load events

## Lifecycle
### Tab 1 - Normal Case
Registers Service Worker (SW) when it is loaded first time. Note that it will reload the page once SW is installed.
* Subsequent load will act as passthrough mode via SW.
* ie. https://cactus-fixed-value.glitch.me
* Sample code to demonstrate what do we mean by pass through.

```
self.addEventListener("fetch", e => {
  e.respondWith(
    fetch(e.request)
  );
});
```

### Tab 2 & 3 - Simple Passthrough
Demonstrates that using simple passthrough doesn't propagate page load failures to WebViewClient.
* Tab 2 loads the page, bypassing the service worker, that will send 500 HTTP status code. In this mode, service worker simply returns from fetch handler without calling `respondWith`. Observe that WebViewClient registers it as failed page load by calling `onReceivedHttpError`. i.e. https://cactus-fixed-value.glitch.me/500?nosw=1
* Tab 3 loads the same page that is processed by the service worker. In this mode, service worker will actually `respondWith`. Observe that WebViewClient doesn't register it as failed page load and doesn't call `onReceivedHttpError`. i.e. https://cactus-fixed-value.glitch.me/500

```
self.addEventListener("fetch", e => {
  if(e.request.url.indexOf('nosw=1') < 0) return;

  e.respondWith(
    fetch(e.request)
  );
});
```

### Tab 4 & 5 - Hybrid page
Demonstrates what would happen when SW responds with local content & then makes a origin response to fetch the rest. Origin response when available is then piped to the renderer.
* Tab 4 demonstrates successful case where origin responds with valid content. ie.https://cactus-fixed-value.glitch.me/index-via-sw-200.html
* Tab 5 demonstrates unsuccessful case where origin responds with 5xx error. In this we detect the failure in SW layer and sends generic error occurred HTML snippet to renderer. In this case, WebViewClient does not report `onReceivedHttpError`. ie.https://cactus-fixed-value.glitch.me/index-via-sw-500.html

