package com.learning.barkmobile.Data

import com.learning.barkmobile.model.Animal
import com.learning.barkmobile.model.AnimalData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

class AnimalRepository {
    private val dummyAnimal = mutableListOf<Animal>()

    init {
        if (dummyAnimal.isEmpty()) {
            AnimalData.dummyAnimal.forEach {
                dummyAnimal.add(it)
            }
        }
    }

    fun getAnimalById(AnimalId: Int): Animal {
        return dummyAnimal.first {
            it.id == AnimalId
        }
    }

    fun getFavoriteAnimal(): Flow<List<Animal>> {
        return flowOf(dummyAnimal.filter { it.isFavorite })
    }

    fun searchAnimal(query: String) = flow {
        val data = dummyAnimal.filter {
            it.name.contains(query, ignoreCase = true)
        }
        emit(data)
    }

    fun updateAnimal(animalId: Int, newState: Boolean): Flow<Boolean> {
        val index = dummyAnimal.indexOfFirst { it.id == animalId }
        val result = if (index >= 0) {
            val animal = dummyAnimal[index]
            dummyAnimal[index] = animal.copy(isFavorite = newState)
            true
        } else {
            false
        }
        return flowOf(result)
    }

    companion object {
        @Volatile
        private var instance: AnimalRepository? = null

        fun getInstance(): AnimalRepository =
            instance ?: synchronized(this) {
                AnimalRepository().apply {
                    instance = this
                }
            }
    }
}