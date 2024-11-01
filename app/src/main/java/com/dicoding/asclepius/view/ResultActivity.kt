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
        val results = intent.getStringExtra("RESULT")
        val imageUri = intent.getStringExtra("IMAGE_URI")

        imageUri?.let {
            binding.resultImage.setImageURI(Uri.parse(it))
        }

        results?.let { displayResults(it)}
    }

    private fun displayResults(results: String) {
        binding.resultText.text = results
    }

}