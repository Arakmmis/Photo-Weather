package com.example.photoweather.utils

import android.content.Context
import android.net.Uri
import android.os.Environment
import android.util.Log
import androidx.core.content.FileProvider
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

object FileUtils {

    fun getImageFileUri(context: Context, tag: String): Uri? {
        val photoFile: File? = getFileName(context, tag)

        photoFile?.let {
            return FileProvider.getUriForFile(
                context,
                "com.example.android.fileprovider",
                it
            )
        }

        return null
    }

    @Throws(IOException::class)
    private fun getFileName(context: Context, tag: String): File? {
        return try {
            val timeStamp: String =
                SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ENGLISH).format(Date())

            val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)

            File.createTempFile(
                "JPEG_${timeStamp}_",
                ".jpg",
                storageDir
            )
        } catch (e: IOException) {
            Log.e(tag, e.localizedMessage ?: e.stackTraceToString())
            null
        }
    }
}