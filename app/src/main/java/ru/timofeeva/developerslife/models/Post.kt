package ru.timofeeva.developerslife.models

import com.google.gson.annotations.SerializedName

data class Post(
    @SerializedName("description")
    val text: String,
    @SerializedName("gifURL")
    val gifUrl: String
)