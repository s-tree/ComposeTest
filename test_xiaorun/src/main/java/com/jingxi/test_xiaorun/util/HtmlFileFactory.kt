package com.jingxi.test_xiaorun.util

import java.io.File

internal object HtmlFileFactory {

    fun buildUrl(page: String): String {
        return File(HtmlFileUtils.HTML_ROOT + "/" + page).absolutePath
    }

    const val AGREEMENT_FEATURE = "agreement_feature.html"
    const val AGREEMENT_PRIVACY = "agreement_privacy.html"
    const val AGREEMENT_USER = "agreement_user.html"
}