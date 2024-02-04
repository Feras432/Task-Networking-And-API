package com.counter.taskapi.model

data class Pet(
    val id: Int,
    val name: String,
    val adopted: Boolean,
    val age: Int,
    val imageUrl: String,
    val gender: String
)
