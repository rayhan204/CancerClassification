package com.dicoding.asclepius.view

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.dicoding.asclepius.data.local.entity.HistoryEntity
import com.dicoding.asclepius.databinding.ActivityResultBinding
import com.dicoding.asclepius.view_model.ResultViewModel
import com.dicoding.asclepius.view_model.ViewModelFactory

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding
    private val resultViewModel: ResultViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

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

        binding.btnSave.setOnClickListener{
            val historyEntity = HistoryEntity(
                id = System.currentTimeMillis().toString(),
                prediction = results ?: "no result",
                image = imageUri ?: ""
            )

            resultViewModel.saveHistory(historyEntity)
            finish()
        }
    }

    private fun displayResults(results: String) {
        binding.resultText.text = results
    }

}