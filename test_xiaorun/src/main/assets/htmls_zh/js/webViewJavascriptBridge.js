function setupWebViewJavascriptBridge(callback) {
    if (window.WebViewJavascriptBridge) { return callback(WebViewJavascriptBridge); }
    if (window.WVJBCallbacks) { return window.WVJBCallbacks.push(callback); }
    window.WVJBCallbacks = [callback];
    var WVJBIframe = document.createElement('iframe');
    WVJBIframe.style.display = 'none';
    WVJBIframe.src = 'wvjbscheme://__BRIDGE_LOADED__';
    document.documentElement.appendChild(WVJBIframe);
    setTimeout(function () { document.documentElement.removeChild(WVJBIframe); }, 0);
}

function callHandlerBridge(name, param, callback) {
    setupWebViewJavascriptBridge(function (bridge) {
        bridge.callHandler(name, param, function (response) {
            callback(response);
        });
    });
}

function registerHandlerBridge(name, param, callback) {
    setupWebViewJavascriptBridge(function (bridge) {
        bridge.registerHandler(name, param, function (response) {
            callback(response);
        });
    });
}
