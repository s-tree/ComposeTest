//测试服务器请求地址
// var root = "http://10.10.6.244:6969/";

//运营服务器请求地址
var root = "https://gateway.house-keeper.cn/";
//var root = "http://10.10.6.234:9999/";
//var root = "http://frp.missblog.net:27020/";

var PAGESIZE = 30;

// 获取客户端导航栏、状态栏高度 - 默认
var navigationBar = (getQueryString("navigationBar") !== null ? getQueryString("navigationBar") : getCookie("navigationBar"));
var statusBar = (getQueryString("statusBar") !== null ? getQueryString("statusBar") : getCookie("statusBar"));

if (navigationBar) {
    window.localStorage.setItem('NAVIGATION_BAR', navigationBar);
} else {
    navigationBar = window.localStorage.getItem('NAVIGATION_BAR');
}

if (statusBar) {
    window.localStorage.setItem('STATUS_BAR', statusBar);
} else {
    statusBar = window.localStorage.getItem('STATUS_BAR');
}

document.onreadystatechange = function () {
    if (document.readyState === 'interactive') {
        console.log(document.readyState);
        if (navigationBar && statusBar) {
            var statusBarHeight = Number(statusBar) / 100; // 状态栏高度
            var totalHeight = (Number(navigationBar) / 100 + Number(statusBar) / 100).toFixed(2); // 状态栏+导航栏总高度
            var topSpaceBasic = (Number(navigationBar) / 100 + Number(statusBar) / 100 + Number(0.2)).toFixed(2); // 普通页主内容区域距顶距离
            var topSpaceOrder = (Number(navigationBar) / 100 + Number(statusBar) / 100 + Number(0.9)).toFixed(2); // 订单列表、服务记录页的主内容区域距顶距离
            var topSpaceOrderDetail = (Number(navigationBar) / 100 + Number(statusBar) / 100 + Number(0.1)).toFixed(2); // 订单详情页的主内容区域距顶距离

            console.log('totalHeight:' + totalHeight, ' statusBarHeight:' + statusBarHeight);

            $('.page_header').css({ "height": totalHeight + 'rem', "padding-top": +statusBarHeight + 'rem' });
            $('#statusTopBar').css({ "top": totalHeight + 'rem' });
            $('#mescrollOrderRecent').css({ "top": topSpaceOrder + 'rem' });
            $('#mescrollOrderRecent_search').css({ "top": totalHeight + 'rem' });
            $('.adjustHeight').css({ "padding-top": topSpaceBasic + 'rem' });
            $('.adjustHeightFixed').css({ "top": topSpaceBasic + 'rem' });
            $('.adjustHeightDetail').css({ "top": topSpaceOrderDetail + 'rem' });
        }
    }
};

// 获得 accessToken
var accessToken = (getQueryString("accessToken") != null ? getQueryString("accessToken") : getCookie("accessToken"));

if (!(accessToken == '' || accessToken == null)) {
    window.localStorage.setItem('ACCESS_TOKEN', accessToken);
} else {
    accessToken = window.localStorage.getItem('ACCESS_TOKEN');
}

var communityId = getQueryString("communityId") !== null ? getQueryString("communityId") : getCookie("communityId");
if (!(communityId == '' || communityId == null)) {
    window.localStorage.setItem('COMMUNITY_ID', communityId);
} else {
    communityId = window.localStorage.getItem('COMMUNITY_ID');
}

var accId = getQueryString("accId") !== null ? getQueryString("accId") : getCookie("accId");
if (!(accId == '' || accId == null)) {
    window.localStorage.setItem('MEMBER_ID', accId);
} else {
    accId = window.localStorage.getItem('MEMBER_ID');
}

function getImage(url){
    if(url.startsWith(url)){
        return url;
    }
    return root + url;
}

// 获取地址栏的参数
function getQueryString(name, isNewCode) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (isNewCode != "" || isNewCode != null) {
        if (r != null) return decodeURI(r[2]);
        return null;
    } else {
        if (r != null) return unescape(r[2]);
        return null;
    }
}

// 获取地址栏参数
function getCookie(cname) {
    var name = cname + "=";
    var ca = document.cookie.split(';');
    for (var i = 0; i < ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0) == ' ') c = c.substring(1);
        if (c.indexOf(name) != -1) return c.substring(name.length, c.length);
    }
    return "";
}

//禁止iOS双指缩放
document.documentElement.addEventListener('touchstart', function (event) {
    if (event.touches.length > 1) {
        event.preventDefault();
    }
}, false);

//禁止iOS双击缩放
var lastTouchEnd = 0;
document.documentElement.addEventListener('touchend', function (event) {
    var now = Date.now();
    if (now - lastTouchEnd <= 300) {
        event.preventDefault();
    }
    lastTouchEnd = now;
}, false);

//iOS :active生效
document.body.addEventListener('touchstart', function () { });

$('.page_header_main_back').unbind('click').bind('click', function () {
    var code = $(this).data('code');
    if (code == 'repair') {
        window.localStorage.removeItem("repairArea");
    } 
    callHandlerBridge('autoGoback');
});

Number.prototype.toFixed = function (d) {
    var s = this + "";
    if (!d) d = 0;
    if (s.indexOf(".") == -1) s += ".";
    s += new Array(d + 1).join("0");
    if (new RegExp("^(-|\\+)?(\\d+(\\.\\d{0," + (d + 1) + "})?)\\d*$").test(s)) {
        var s = "0" + RegExp.$2, pm = RegExp.$1, a = RegExp.$3.length, b = true;
        if (a == d + 2) {
            a = s.match(/\d/g);
            if (parseInt(a[a.length - 1]) > 4) {
                for (var i = a.length - 2; i >= 0; i--) {
                    a[i] = parseInt(a[i]) + 1;
                    if (a[i] == 10) {
                        a[i] = 0;
                        b = i != 1;
                    } else break;
                }
            }
            s = a.join("").replace(new RegExp("(\\d+)(\\d{" + d + "})\\d$"), "$1.$2");
        } if (b) s = s.substr(1);
        return (pm + s).replace(/\.$/, "");
    } return this + "";
};

