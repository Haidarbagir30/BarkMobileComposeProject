package com.learning.barkmobile.model


data class Animal(
    val id: Int,
    val name: String,
    val animal: String,
    val image: Int,
    val description: String,
    val favorite: String,
    val rating: Double,
    val active: String,
    var isFavorite: Boolean = false
)