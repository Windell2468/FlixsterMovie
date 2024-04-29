package com.example.flixster


import com.google.gson.annotations.SerializedName

class BestMovie {
    @SerializedName("title")
    var title: String? = null

    @SerializedName("description")
    var description: String? = null

    @SerializedName("poster_path")
    var movieImageUrl: String? = null
}