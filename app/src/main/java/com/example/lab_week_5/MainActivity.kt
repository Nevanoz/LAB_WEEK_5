package com.example.lab_week_5

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.lab_week_5.api.CatApiService
import com.example.lab_week_5.model.ImageData
import retrofit2.*
import retrofit2.converter.moshi.MoshiConverterFactory

class MainActivity : AppCompatActivity() {

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.thecatapi.com/v1/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    private val catApiService by lazy {
        retrofit.create(CatApiService::class.java)
    }

    private val tvBreed: TextView by lazy {
        findViewById(R.id.tv_breed)
    }

    private val imageView: ImageView by lazy {
        findViewById(R.id.image_result)
    }

    private val imageLoader: ImageLoader by lazy {
        GlideLoader()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadCatData()
    }

    private fun loadCatData() {
        val call = catApiService.searchImages(1, "full")

        call.enqueue(object : Callback<List<ImageData>> {

            override fun onFailure(call: Call<List<ImageData>>, t: Throwable) {
                tvBreed.text = "Breed: Error"
                Log.e(MAIN_ACTIVITY, "Network Error", t)
            }

            override fun onResponse(
                call: Call<List<ImageData>>,
                response: Response<List<ImageData>>
            ) {
                if (!isDestroyed && response.isSuccessful) {

                    val data = response.body()?.firstOrNull()

                    val imageUrl = data?.imageUrl.orEmpty()

                    val breedName = if (!data?.breeds.isNullOrEmpty()) {
                        data?.breeds?.firstOrNull()?.name ?: "Unknown"
                    } else {
                        "Unknown"
                    }

                    tvBreed.text = "Breed: $breedName"

                    if (imageUrl.isNotBlank()) {
                        imageLoader.loadImage(imageUrl, imageView)
                    }
                }
            }
        })
    }

    companion object {
        const val MAIN_ACTIVITY = "MAIN_ACTIVITY"
    }
}
