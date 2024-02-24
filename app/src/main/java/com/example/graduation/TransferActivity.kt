package com.example.graduation

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.graphics.drawable.BitmapDrawable
import android.media.ExifInterface
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.googlecode.tesseract.android.TessBaseAPI
import java.io.*
import java.text.SimpleDateFormat
import java.util.*


class TransferActivity : AppCompatActivity() {
    private var image: Bitmap? = null
    private var mTess: TessBaseAPI? = null
    private var datapath = ""
    private var btnPicture: Button? = null
    private var btnOCR: Button? = null
    private var imageFilePath: String? = null
    private var pUri: Uri? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transfer)
        // import com.google.cloud.vision.v1.ImageAnnotatorClient
        // import java.io.File



        btnPicture = findViewById<View>(R.id.takePicture) as Button
        btnOCR = findViewById<View>(R.id.ocrButton) as Button

        // 언어파일 경로
        datapath = "$filesDir/tesseract/"

        // 트레이닝데이터가 카피되어 있는지 체크
        checkFile(File(datapath + "tessdata/"), "kor")
        checkFile(File(datapath + "tessdata/"), "eng")

        // Tesseract API 초기화
        val lang = "kor+eng"
        mTess = TessBaseAPI()
        mTess!!.init(datapath, lang)

        // 이미지 촬영 버튼 클릭 시 카메라 실행
        btnPicture!!.setOnClickListener { sendTakePhotoIntent() }

        // 텍스트 추출 버튼
        btnOCR!!.setOnClickListener {
            // 이미지뷰에서 비트맵 추출
            val d = (findViewById<View>(R.id.imageView) as ImageView).drawable as BitmapDrawable
            image = d.bitmap
            var OCRResult: String? = null

            // Tesseract에 이미지 설정 및 텍스트 추출
            mTess!!.setImage(image)
            OCRResult = mTess!!.utF8Text

            // 추출된 텍스트에서 숫자 데이터만 추출
            val numericText = extractNumericText(OCRResult)

            // 추출된 숫자 데이터를 TextView에 설정
            val OCRTextView = findViewById<View>(R.id.OCRTextView) as TextView
            OCRTextView.text = numericText
        }
    }

    private fun extractNumericText(text: String): String {
        return text.replace(Regex("[^0-9]"), "")
    }

    private fun sendTakePhotoIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(packageManager) != null) {
            val photoFile: File? = try {
                createImageFile()
            } catch (ex: IOException) {
                null
            }
            photoFile?.let {
                pUri = FileProvider.getUriForFile(
                    this, "com.example.graduation.fileprovider", it
                )
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, pUri)
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            (findViewById<View>(R.id.imageView) as ImageView).setImageURI(pUri)
            var exif: ExifInterface? = null
            val bitmap = BitmapFactory.decodeFile(imageFilePath)
            try {
                exif = ExifInterface(imageFilePath!!)
            } catch (e: IOException) {
                e.printStackTrace()
            }
            val exifOrientation: Int
            val exifDegree: Int
            if (exif != null) {
                exifOrientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL
                )
                exifDegree = exifOrientationToDegrees(exifOrientation)
            } else {
                exifDegree = 0
            }
            (findViewById<View>(R.id.imageView) as ImageView).setImageBitmap(
                rotate(
                    bitmap,
                    exifDegree.toFloat()
                )
            )
        }
    }

    private fun createImageFile(): File {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = "TEST_" + timeStamp + "_"
        val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            imageFileName,
            ".jpg",
            storageDir
        ).apply {
            imageFilePath = absolutePath
        }
    }

    private fun exifOrientationToDegrees(exifOrientation: Int): Int {
        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
            return 90
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
            return 180
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
            return 270
        }
        return 0
    }

    private fun rotate(bitmap: Bitmap, degree: Float): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(degree)
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
    }

    private fun copyFiles(lang: String) {
        try {
            val filepath = "$datapath/tessdata/$lang.traineddata"
            val assetManager = assets
            val instream: InputStream = assetManager.open("tessdata/$lang.traineddata")
            val outstream: OutputStream = FileOutputStream(filepath)
            val buffer = ByteArray(1024)
            var read: Int
            while (instream.read(buffer).also { read = it } != -1) {
                outstream.write(buffer, 0, read)
            }
            outstream.flush()
            outstream.close()
            instream.close()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun checkFile(dir: File, lang: String) {
        if (!dir.exists() && dir.mkdirs()) {
            copyFiles(lang)
        }
        if (dir.exists()) {
            val datafilepath = "$datapath/tessdata/$lang.traineddata"
            val datafile = File(datafilepath)
            if (!datafile.exists()) {
                copyFiles(lang)
            }
        }
    }

    companion object {
        const val REQUEST_IMAGE_CAPTURE = 672
    }
}


/*
package com.example.graduation

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.graphics.drawable.BitmapDrawable
import android.media.ExifInterface
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.googlecode.tesseract.android.TessBaseAPI
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.Date

class transferActivity : AppCompatActivity() {
    var image: Bitmap? = null //사용되는 이미지
    private var mTess: TessBaseAPI? = null //Tess API reference
    var datapath = "" //언어데이터가 있는 경로
    var btn_picture: Button? = null //사진 찍는 버튼
    var btn_ocr: Button? = null //텍스트 추출 버튼
    private var imageFilePath: String? = null //이미지 파일 경로
    private var p_Uri: Uri? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transfer)
        btn_picture = findViewById<View>(R.id.takePicture) as Button
        btn_ocr = findViewById<View>(R.id.ocrButton) as Button

        //언어파일 경로
        datapath = "$filesDir/tesseract/"

        //트레이닝데이터가 카피되어 있는지 체크
        checkFile(File(datapath + "tessdata/"), "kor")
        checkFile(File(datapath + "tessdata/"), "eng")
        */
/**
 * Tesseract API
 * 한글 + 영어(함께 추출)
 * 한글만 추출하거나 영어만 추출하고 싶다면
 * String lang = "eng"와 같이 작성해도 무관
 *//*

        val lang = "kor+eng"

        mTess = TessBaseAPI()
        mTess!!.init(datapath, lang)


       //이미 권한이 부여되어 있으면
        // 사진 찍는 버튼 클릭시 카메라 킴
        btn_picture!!.setOnClickListener { sendTakePhotoIntent() }


        // 텍스트 추출 버튼
        btn_ocr!!.setOnClickListener { // 가져와진 사진을 bitmap으로 추출
            val d =
                (findViewById<View>(R.id.imageView) as ImageView).drawable as BitmapDrawable
            image = d.bitmap
            var OCRresult: String? = null
            mTess!!.setImage(image)

            //텍스트 추출
            OCRresult = mTess!!.utF8Text
            val OCRTextView =
                findViewById<View>(R.id.OCRTextView) as TextView
            OCRTextView.text = OCRresult
        }
    }

    private fun exifOrientationToDegrees(exifOrientation: Int): Int {
        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
            return 90
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
            return 180
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
            return 270
        }
        return 0
    }

    private fun rotate(bitmap: Bitmap, degree: Float): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(degree)
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
    }

    */
/*private fun sendTakePhotoIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(packageManager) != null) {
            var photoFile: File? = null
            try {
                photoFile = createImageFile()
            } catch (ex: IOException) {
                // Error occurred while creating the File
            }
            if (photoFile != null) {
                p_Uri = FileProvider.getUriForFile(
                    *//*
*/
/*this,
                    packageName, photoFile*//*
*/
/*
                    this, "com.example.graduation.fileprovider",photoFile
                )
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, p_Uri)
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }*//*


    private fun sendTakePhotoIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(packageManager) != null) {
            val photoFile: File? = try {
                createImageFile()
            } catch (ex: IOException) {
                null
            }
            photoFile?.let {
                p_Uri = FileProvider.getUriForFile(
                    */
/*this,
                    packageName, photoFile*//*

                    this, "com.example.graduation.fileprovider",it
                )
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, p_Uri)
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            (findViewById<View>(R.id.imageView) as ImageView).setImageURI(p_Uri)
            var exif: ExifInterface? = null
            val bitmap = BitmapFactory.decodeFile(imageFilePath)
            try {
                exif = ExifInterface(imageFilePath!!)
            } catch (e: IOException) {
                e.printStackTrace()
            }
            val exifOrientation: Int
            val exifDegree: Int
            if (exif != null) {
                exifOrientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL
                )
                exifDegree = exifOrientationToDegrees(exifOrientation)
            } else {
                exifDegree = 0
            }
            (findViewById<View>(R.id.imageView) as ImageView).setImageBitmap(
                rotate(
                    bitmap,
                    exifDegree.toFloat()
                )
            )
        }
    }


    private fun createImageFile(): File {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = "TEST_" + timeStamp + "_"
        val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            imageFileName,
            ".jpg",
            storageDir
        ).apply {
            imageFilePath = absolutePath
        }
    }
    //장치에 파일 복사
    private fun copyFiles(lang: String) {
        try {
            //파일이 있을 위치
            val filepath = "$datapath/tessdata/$lang.traineddata"

            //AssetManager에 액세스
            val assetManager = assets

            //읽기/쓰기를 위한 열린 바이트 스트림
            val instream = assetManager.open("tessdata/$lang.traineddata")
            val outstream: OutputStream = FileOutputStream(filepath)

            //filepath에 의해 지정된 위치에 파일 복사
            val buffer = ByteArray(1024)
            var read: Int
            while (instream.read(buffer).also { read = it } != -1) {
                outstream.write(buffer, 0, read)
            }
            outstream.flush()
            outstream.close()
            instream.close()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    //check file on the device
    private fun checkFile(dir: File, lang: String) {
        //디렉토리가 없으면 디렉토리를 만들고 그후에 파일을 카피
        if (!dir.exists() && dir.mkdirs()) {
            copyFiles(lang)
        }
        //디렉토리가 있지만 파일이 없으면 파일카피 진행
        if (dir.exists()) {
            val datafilepath = "$datapath/tessdata/$lang.traineddata"
            val datafile = File(datafilepath)
            if (!datafile.exists()) {
                copyFiles(lang)
            }
        }
    }

    companion object {
        const val REQUEST_IMAGE_CAPTURE = 672
    }
}


/*
package com.example.graduation

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.graphics.drawable.BitmapDrawable
import android.media.ExifInterface
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.googlecode.tesseract.android.TessBaseAPI
import java.io.*
import java.text.SimpleDateFormat
import java.util.*


class TransferActivity : AppCompatActivity() {
    private var image: Bitmap? = null
    private var mTess: TessBaseAPI? = null
    private var datapath = ""
    private var btnPicture: Button? = null
    private var btnOCR: Button? = null
    private var imageFilePath: String? = null
    private var pUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transfer)

        btnPicture = findViewById<View>(R.id.takePicture) as Button
        btnOCR = findViewById<View>(R.id.ocrButton) as Button

        // 언어파일 경로
        datapath = "$filesDir/tesseract/"

        // 트레이닝데이터가 카피되어 있는지 체크
        checkFile(File(datapath + "tessdata/"), "kor")
        checkFile(File(datapath + "tessdata/"), "eng")

        // Tesseract API 초기화
        val lang = "kor+eng"
        mTess = TessBaseAPI()
        mTess!!.init(datapath, lang)

        // 이미지 촬영 버튼 클릭 시 카메라 실행
        btnPicture!!.setOnClickListener { sendTakePhotoIntent() }

        // 텍스트 추출 버튼
        btnOCR!!.setOnClickListener {
            // 이미지뷰에서 비트맵 추출
            val d = (findViewById<View>(R.id.imageView) as ImageView).drawable as BitmapDrawable
            image = d.bitmap
            var OCRResult: String? = null

            // Tesseract에 이미지 설정 및 텍스트 추출
            mTess!!.setImage(image)
            OCRResult = mTess!!.utF8Text

            // 추출된 텍스트에서 숫자 데이터만 추출
            val numericText = extractNumericText(OCRResult)

            // 추출된 숫자 데이터를 TextView에 설정
            val OCRTextView = findViewById<View>(R.id.OCRTextView) as TextView
            OCRTextView.text = numericText
        }
    }

    private fun extractNumericText(text: String): String {
        return text.replace(Regex("[^0-9]"), "")
    }

    private fun sendTakePhotoIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(packageManager) != null) {
            val photoFile: File? = try {
                createImageFile()
            } catch (ex: IOException) {
                null
            }
            photoFile?.let {
                pUri = FileProvider.getUriForFile(
                    this, "com.example.graduation.fileprovider", it
                )
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, pUri)
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            (findViewById<View>(R.id.imageView) as ImageView).setImageURI(pUri)
            var exif: ExifInterface? = null
            val bitmap = BitmapFactory.decodeFile(imageFilePath)
            try {
                exif = ExifInterface(imageFilePath!!)
            } catch (e: IOException) {
                e.printStackTrace()
            }
            val exifOrientation: Int
            val exifDegree: Int
            if (exif != null) {
                exifOrientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL
                )
                exifDegree = exifOrientationToDegrees(exifOrientation)
            } else {
                exifDegree = 0
            }
            (findViewById<View>(R.id.imageView) as ImageView).setImageBitmap(
                rotate(
                    bitmap,
                    exifDegree.toFloat()
                )
            )
        }
    }

    private fun createImageFile(): File {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = "TEST_" + timeStamp + "_"
        val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            imageFileName,
            ".jpg",
            storageDir
        ).apply {
            imageFilePath = absolutePath
        }
    }

    private fun exifOrientationToDegrees(exifOrientation: Int): Int {
        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
            return 90
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
            return 180
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
            return 270
        }
        return 0
    }

    private fun rotate(bitmap: Bitmap, degree: Float): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(degree)
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
    }

    private fun copyFiles(lang: String) {
        try {
            val filepath = "$datapath/tessdata/$lang.traineddata"
            val assetManager = assets
            val instream: InputStream = assetManager.open("tessdata/$lang.traineddata")
            val outstream: OutputStream = FileOutputStream(filepath)
            val buffer = ByteArray(1024)
            var read: Int
            while (instream.read(buffer).also { read = it } != -1) {
                outstream.write(buffer, 0, read)
            }
            outstream.flush()
            outstream.close()
            instream.close()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun checkFile(dir: File, lang: String) {
        if (!dir.exists() && dir.mkdirs()) {
            copyFiles(lang)
        }
        if (dir.exists()) {
            val datafilepath = "$datapath/tessdata/$lang.traineddata"
            val datafile = File(datafilepath)
            if (!datafile.exists()) {
                copyFiles(lang)
            }
        }
    }

    companion object {
        const val REQUEST_IMAGE_CAPTURE = 672
    }
}
*/*/