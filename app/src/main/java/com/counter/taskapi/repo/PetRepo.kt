package com.counter.taskapi.repo

import com.counter.taskapi.petapiservice.PetApiService

class PetRepo(private val api: PetApiService) {
    suspend fun getAllPets() = api.getAllPets()
}