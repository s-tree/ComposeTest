package com.jingxi.test_xiaorun.ui.web

import com.jingxi.test_xiaorun.constant.Page

object WebViewParams {

    const val URL : String = "url"

    fun buildUrl(url : String = "") : String{
        return "${Page.WEB_VIEW}/{${url}}"
    }
}