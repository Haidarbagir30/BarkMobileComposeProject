package com.learning.barkmobile.di

import com.learning.barkmobile.Data.AnimalRepository

object Injection {
    fun provideRepository(): AnimalRepository {
        return AnimalRepository.getInstance()
    }
}