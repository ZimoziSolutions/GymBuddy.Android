package com.zimozi.assessment.util

import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment
import android.util.Base64
import android.util.Log
import android.webkit.MimeTypeMap
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.regex.Pattern

/**
 * Created by Concaro at 19/9/18
 */
class CommonFunctions {
    companion object {

        fun isUsernameValid(username: String): Boolean {
            var isValid = false
            val expression =
                "^[a-z0-9._]*\$"
            val pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
            val matcher = pattern.matcher(username)
            if (matcher.matches()) {
                isValid = true
            }
            return isValid
        }

        fun isEmailValid(email: String): Boolean {
            var isValid = false
            val emailExpression =
                "^[a-z+0-9!#$%&'*/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$)"
            val pattern = Pattern.compile(emailExpression, Pattern.CASE_INSENSITIVE)
            val matcher = pattern.matcher(email)
            if (matcher.matches()) {
                isValid = true
            }
            return isValid
        }

        fun isPasswordValid(password: String): Boolean {
            var isValid = false
//            val passwordExpression = "(?=.*[a-zA-Z])(?=.*[\\d]).+"
//            val pattern = Pattern.compile(passwordExpression, Pattern.CASE_INSENSITIVE)
//            val matcher = pattern.matcher(password)
//            if (matcher.matches()) {
//                isValid = true
//            }
            if (password.length >= 8) {
                isValid = true
            }
            return isValid
        }

        fun isPhoneNumValid(num: String): Boolean {
            val phoneExpression = "[0-9]+"
            val pattern = Pattern.compile(phoneExpression, Pattern.CASE_INSENSITIVE)
            val matcher = pattern.matcher(num)
            if (matcher.matches() && num.count() <= 14) {
                return true
            }
            return false
        }

        // @JvmStatic
        // fun isPhoneNumberValid(num: String, locale: String): Boolean {
        //     var isValid = false
        //     val phoneNumberUtil = PhoneNumberUtil.getInstance()
        //     try {
        //         val swissNumberProto = phoneNumberUtil.parse(num, locale)
        //         isValid = phoneNumberUtil.isValidNumber(swissNumberProto)
        //     } catch (e: NumberParseException) {
        //         System.err.println("NumberParseException was thrown: " + e.toString())
        //         isValid = false
        //     }
        //     return isValid
        // }

        fun printKeyHashes(context: Context) {
            // Add code to print out the key hash
            try {
                val info = context.packageManager.getPackageInfo(
                    context.packageName,
                    PackageManager.GET_SIGNATURES
                )
                for (signature in info.signatures) {
                    val md = MessageDigest.getInstance("SHA")
                    md.update(signature.toByteArray())
                    Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT))
                }
            } catch (e: PackageManager.NameNotFoundException) {

            } catch (e: NoSuchAlgorithmException) {

            }
        }

        fun saveBitmap(path: String?, onSaveSuccessListener: OnSaveSuccessListener) {
            val compressdPicPath = ""

            //      ★★★★★★★★★★★★★★重点★★★★★★★★★★★★★
            /*  //★如果不压缩直接从path获取bitmap，这个bitmap会很大，下面在压缩文件到100kb时，会循环很多次，
        // ★而且会因为迟迟达不到100k，options一直在递减为负数，直接报错
        //★ 即使原图不是太大，options不会递减为负数，也会循环多次，UI会卡顿，所以不推荐不经过压缩，直接获取到bitmap
        Bitmap bitmap=BitmapFactory.decodeFile(path);*/
            //      ★★★★★★★★★★★★★★重点★★★★★★★★★★★★★
            val bitmap =
                decodeSampledBitmapFromPath(
                    path,
                    720,
                    1280
                )

            val baos = ByteArrayOutputStream()

            /* options表示 如果不压缩是100，表示压缩率为0。如果是70，就表示压缩率是70，表示压缩30%; */
            var options = 100
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)

            while (baos.toByteArray().size / 1024 > 200) {
                // 循环判断如果压缩后图片是否大于500kb继续压缩

                baos.reset()
                options -= 10
                if (options < 11) {//为了防止图片大小一直达不到200kb，options一直在递减，当options<0时，下面的方法会报错
                    // 也就是说即使达不到200kb，也就压缩到10了
                    bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos)
                    break
                }
                // 这里压缩options%，把压缩后的数据存放到baos中
                bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos)
            }

            val mDir = Environment.getExternalStorageDirectory().toString() + "/Needapp"
            val dir = File(mDir)
            if (!dir.exists()) {
                dir.mkdirs()//文件不存在，则创建文件
            }
            val fileName = System.currentTimeMillis().toString()
            val file = File(mDir, "$fileName.jpg")
            val fOut: FileOutputStream? = null

            try {
                val out = FileOutputStream(file)
                out.write(baos.toByteArray())
                out.flush()
                out.close()
                onSaveSuccessListener.onSuccess(file.getAbsolutePath())
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        private fun decodeSampledBitmapFromPath(path: String?, width: Int, height: Int): Bitmap {

            //      获取图片的宽和高，并不把他加载到内存当中
            val options = BitmapFactory.Options()
            options.inJustDecodeBounds = true
            BitmapFactory.decodeFile(path, options)

            options.inSampleSize =
                caculateInSampleSize(
                    options,
                    width,
                    height
                )
            //      使用获取到的inSampleSize再次解析图片(此时options里已经含有压缩比 options.inSampleSize，再次解析会得到压缩后的图片，不会oom了 )
            options.inJustDecodeBounds = false
            return BitmapFactory.decodeFile(path, options)
        }

        private fun caculateInSampleSize(
            options: BitmapFactory.Options,
            reqWidth: Int,
            reqHeight: Int
        ): Int {
            val width = options.outWidth
            val height = options.outHeight

            var inSampleSize = 1

            if (width >= reqWidth || height >= reqHeight) {

                val widthRadio = Math.round(width * 1.0f / reqWidth)
                val heightRadio = Math.round(width * 1.0f / reqHeight)

                inSampleSize = Math.max(widthRadio, heightRadio)
            }

            return inSampleSize
        }

        fun getMimeType(url: String): String {
            var type: String? = null
            val extension = MimeTypeMap.getFileExtensionFromUrl(url)
            if (extension != null) {
                type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)
            }
            return type ?: "image/jpeg"
        }
    }
}

interface OnSaveSuccessListener {
    fun onSuccess(path: String)
}