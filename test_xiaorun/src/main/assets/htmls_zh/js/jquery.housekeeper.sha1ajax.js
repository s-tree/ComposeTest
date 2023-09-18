/**
 * Author: weiweigu
 * CreateTime: 21/04/25 上午15:26
 * Description: 慧管家加密接口请求
 */

$.extend({
    /**
     * 封装AJAX请求
     * @param options   请求参数
     */
    slAjax: function (options) {
        // 默认参数配置
        var defaultOps = {
            accessToken: '',
            url: '',
            data: {},
            dataType: 'JSON',
            type: 'POST',
            async: true,
            contentType:'application/x-www-form-urlencoded'
        };

        // 用户传入参数配置
        var userOpt = $.extend({}, defaultOps, options);

        $.ajax({
            url: userOpt.url,
            data: userOpt.data,
            dataType: userOpt.dataType,
            type: userOpt.type,
            async: userOpt.async,
            contentType: userOpt.contentType,
            beforeSend: function (XMLHttpRequest) {
                XMLHttpRequest.setRequestHeader("Authorization", 'bearer ' + accessToken);
                XMLHttpRequest.setRequestHeader("compoundId", communityId);

                if (typeof userOpt.beforeSend != 'undefined') {
                    userOpt.beforeSend.call(this);
                }
            },
            complete: function () {
                if (typeof userOpt.complete != 'undefined') {
                    userOpt.complete.call(this);
                }
            },
            statusCode :{
                200: function(response){
                },
                424: function(response){
                    callHandlerBridge('onTokenValid', {}, function (response) {
                    });
                }
            },
            success: function (result) {
                if (typeof userOpt.success != 'undefined') {
                    userOpt.success.call(this, result);
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.log("http status = " + jqXHR.status)
                if (jqXHR.responseJSON) {
                    userOpt.success.call(this, jqXHR.responseJSON);
                }
            }
        });
    }
});