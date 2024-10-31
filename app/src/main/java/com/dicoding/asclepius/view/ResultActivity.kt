package com.dicoding.asclepius.view

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dicoding.asclepius.R
import com.dicoding.asclepius.databinding.ActivityResultBinding
import org.tensorflow.lite.task.vision.classifier.Classifications
import java.lang.StringBuilder

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // TODO: Menampilkan hasil gambar, prediksi, dan confidence score.
        val results = intent.getSerializableExtra("RESULT") as? List<Classifications>
        val imageUri = intent.getStringExtra("IMAGE_URI")

        imageUri?.let {
            binding.resultImage.setImageURI(Uri.parse(it))
        }
        results?.let { displayResults(it)}
    }

    private fun displayResults(results: List<Classifications>) {
        val resultText = StringBuilder()

        results.forEach { classifications ->
            classifications.categories.forEach{ category ->
                resultText.append("Label: ${category.label}\n")
                resultText.append("Confidence: ${category.score * 100}%\n\n")
            }

        }
        binding.resultText.text = resultText.toString()

    }

}