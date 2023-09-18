package com.jingxi.test_xiaorun.util

import android.util.Log
import com.jingxi.library.BaseApplication
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream

internal object HtmlFileUtils {
    private const val TAG = "HtmlFileUtils"
    public val HTML_ROOT: String = File(BaseApplication.instance!!.filesDir,"htmls_zh").absolutePath
    private const val TAG_FILE_NAME = "tag.dat"
    private const val HTML_VERSION = "1"
    private const val statusBarHeight = 48
    private const val totalHeight = 88

    @OptIn(DelicateCoroutinesApi::class)
    fun start(){
        GlobalScope.launch {  }
        val tagFile = File(HTML_ROOT,TAG_FILE_NAME)
        if(tagFile.exists()){
            val version = FileUtil.readFile(tagFile)
            if(version == HTML_VERSION){
                return
            }
        }
        copyFromAssetsToData()
        FileUtil.writeFile(tagFile, HTML_VERSION)
    }

    private fun copyFromAssetsToData() {
        val fileNames: Array<String?> =
            getFileListByDirName("htmls_zh")
                ?: return
        for (name in fileNames) {
            name!!.let {
                makeDir("htmls_zh")
                copyFromAssetsToData(name, "htmls_zh")
            }
        }
    }

    private fun copyFromAssetsToData(fileName: String, dirName: String) {
        if (isDirectory(fileName)) {
            val dirName2 = "$dirName/$fileName"
            makeDir(dirName2)
            val files: Array<String?> = getFileListByDirName(dirName2) ?: return
            for (file in files) {
                copyFromAssetsToData(file!!, dirName2)
            }
        } else {
            Log.w(
                TAG,
                "start copy file = $dirName/$fileName"
            )
            startCopyFile(fileName, dirName)
        }
    }

    private fun startCopyFile(fileName: String, dir: String) {
        var reader: InputStream? = null
        var writer: OutputStream? = null
        try {
            reader = BaseApplication.instance!!.assets.open("$dir/$fileName")
            val file = File(
                BaseApplication.instance!!.filesDir.absolutePath + "/$dir/$fileName"
            )
            file.createNewFile()
            writer = FileOutputStream(file)
            val bytes = ByteArray(20480)
            var k: Int
            while (reader.read(bytes).also { k = it } != -1) {
                if (fileName.endsWith(".html")) {
                    var temp = String(bytes, 0, k)
                    if (temp.contains("class=\"page_header\"")) {
                        temp = temp.replace(
                            "class=\"page_header\"".toRegex(),
                            "class=\"page_header\" style=\"height:" + totalHeight + "rem;padding-top:" + statusBarHeight + "rem\""
                        )
                    }
                    if (temp.contains("id=\"statusTopBar\"")) {
                        temp = temp.replace(
                            "id=\"statusTopBar\"".toRegex(),
                            "id=\"statusTopBar\" style=\"top:" + totalHeight + "rem\""
                        )
                    }
                    writer.write(temp.toByteArray())
                    continue
                }
                writer.write(bytes, 0, k)
            }
            writer.flush()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                writer?.close()
                reader?.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun makeDir(dirName: String) {
        val file = File(BaseApplication.instance!!.filesDir.absolutePath + "/$dirName")
        file.mkdirs()
    }

    private fun getFileListByDirName(dirName: String): Array<String?>? {
        try {
            return BaseApplication.instance!!.assets.list(dirName)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    private fun isDirectory(fileName: String): Boolean {
        return !fileName.contains(".")
    }
}