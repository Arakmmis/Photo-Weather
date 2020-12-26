package com.example.photoweather.utils

import android.content.Context
import android.os.Environment
import android.util.Log
import androidx.core.content.FileProvider
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

object FileUtils {

    fun getImageFile(context: Context, tag: String): File? {
        val photoFile: File? = createImageFile(context, tag)

        photoFile?.let {
            FileProvider.getUriForFile(
                context,
                "com.example.android.fileprovider",
                it
            )
        }

        return photoFile
    }

    @Throws(IOException::class)
    fun createImageFile(context: Context, tag: String): File? {
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