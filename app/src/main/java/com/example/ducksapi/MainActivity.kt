package com.example.ducksapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import com.squareup.picasso.Picasso
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Call

class MainActivity : AppCompatActivity() {
    private lateinit var tvQuote: TextView
    private lateinit var tapMeButton: Button
    private lateinit var imageBox: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initializeViews()
        setupListeners()
    }

    private fun initializeViews() {
        tvQuote = findViewById(R.id.tvquote)
        tapMeButton = findViewById(R.id.tapme)
        imageBox = findViewById(R.id.imagebox)
    }

    private fun setupListeners() {
        tapMeButton.setOnClickListener {
            fetchDuckImage()
        }
    }

    private fun fetchDuckImage() {
        val call = ApiClient.apiService.getDucks()
        call.enqueue(object : Callback<Ducks> {
            override fun onResponse(call: Call<Ducks>, response: Response<Ducks>) {
                if (response.isSuccessful) {
                    val duck = response.body()
                    tvQuote.isVisible = false
                    imageBox.isVisible = true
                    val imageUrl = duck?.url.orEmpty()
                    tapMeButton.text = "More Ducks"

                    if (imageUrl.isNotBlank()) {
                        loadDuckImage(imageUrl)
                    } else {
                        tvQuote.text = "Image URL is empty"
                    }
                } else {
                    tvQuote.text = "Error fetching image"
                }
            }

            override fun onFailure(call: Call<Ducks>, t: Throwable) {
                tvQuote.text = "Failed to fetch image"
            }
        })
    }

    private fun loadDuckImage(imageUrl: String) {
        Picasso.get()
            .load(imageUrl)
            .placeholder(R.drawable.baseline_downloading_24)
            .error(R.drawable.ic_launcher_foreground)
            .into(imageBox)
    }
}
