package com.example.flixster

data class Person(
    val id: Int,
    val name: String,
    val profilePath: String?,
    val knownFor: List<String>,
    val posterPath: String?,
    val biography: String
)
