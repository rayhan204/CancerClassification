package com.dicoding.asclepius.helper

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.SystemClock
import android.provider.MediaStore
import android.util.Log
import com.dicoding.asclepius.R
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.task.core.BaseOptions
import org.tensorflow.lite.task.vision.classifier.Classifications
import org.tensorflow.lite.task.vision.classifier.ImageClassifier
import java.lang.IllegalStateException


class ImageClassifierHelper(
    var threshold: Float = 0.1f,
    var maxResults: Int = 3,
    val modelName: String = "cancer_classification.tflite",
    val context: Context,
    val classifierListener: ClassifierListener?
) {
    private var imageClassifier: ImageClassifier? = null

    init {
        setupImageClassifier()
    }

    private fun setupImageClassifier() {
        // TODO: Menyiapkan Image Classifier untuk memproses gambar.
        val optionsBuilder = ImageClassifier.ImageClassifierOptions.builder()
            .setScoreThreshold(threshold)
            .setMaxResults(maxResults)
        val baseOptionsBuilder = BaseOptions.builder()
            .setNumThreads(4)
        optionsBuilder.setBaseOptions(baseOptionsBuilder.build())

        try {
            imageClassifier = ImageClassifier.createFromFileAndOptions(
                context,
                modelName,
                optionsBuilder.build()
            )
        }catch (e: IllegalStateException) {
            classifierListener?.onError(context.getString(R.string.image_classifier_failed))
            Log.e(TAG, e.message.toString())
        }
    }

    fun classifyStaticImage(imageUri: Uri) {
        // TODO: mengklasifikasikan imageUri dari gambar statis.
       val bitmap = toBitmap(imageUri) ?: return

        val argbBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true)
        val image = TensorImage.fromBitmap(argbBitmap)
        val outputs = imageClassifier?.classify(image)
        var inferenceTime = SystemClock.uptimeMillis()
        inferenceTime = SystemClock.uptimeMillis() - inferenceTime

        outputs?.forEach { classification ->
            classification.categories.forEach { category ->
                Log.d(
                    "Classification Result",
                    "Label: ${category.label}, Confidence: ${category.score}"
                )
            }
        }
        classifierListener?.onResult(outputs, inferenceTime)
    }

    private fun toBitmap(imageUri: Uri): Bitmap? {
           return try {
               val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                   val source = ImageDecoder.createSource(context.contentResolver, imageUri)
                   ImageDecoder.decodeBitmap(source) { decoder, _, _ ->
                       decoder.isMutableRequired = true
                       decoder.setTargetColorSpace(android.graphics.ColorSpace.get(android.graphics.ColorSpace.Named.SRGB))
                   }
               } else {
                   MediaStore.Images.Media.getBitmap(context.contentResolver, imageUri)
               }
               bitmap.copy(Bitmap.Config.ARGB_8888, true)
           } catch (e: Exception) {
               Log.e(TAG, "Error converting URI to Bitmap: ${e.message}")
               null
           }
    }

    interface ClassifierListener {
        fun onError(error: String)
        fun onResult (
            result: MutableList<Classifications>?,
            inferenceTime: Long
        )
    }

    companion object {
        private const val TAG = "ImageClassifierHelper"
    }

}