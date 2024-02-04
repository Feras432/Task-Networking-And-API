package com.counter.taskapi.viewModel

import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.counter.taskapi.model.Pet
import com.counter.taskapi.petapiservice.PetApiService
import com.counter.taskapi.repo.PetRepo
import com.counter.taskapi.singleton.RetrofitHelper
import kotlinx.coroutines.launch

class PetViewModel: ViewModel() {
    private val petApiService = RetrofitHelper.getInstance().create(PetApiService::class.java)
    private val repository = PetRepo(petApiService)
    var pets by mutableStateOf(listOf<Pet>())
    init {
        fetchPets()
    }
    fun fetchPets() {
        viewModelScope.launch {
            try {
                pets = repository.getAllPets()
            } catch (e: Exception) {

            }
        }
    }
    fun addPet(pet: Pet){
        viewModelScope.launch {
            try {
                var response = petApiService.addPet(pet)
                if (response.isSuccessful && response.body() != null) {
                    print("The adding was successful")
                }
            } catch (e: Exception){
                    print("Error")
            }
        }
    }
    fun deletePet(petID: Int){
        viewModelScope.launch {
            try {
                var response = petApiService.deletePet(petID)
                if (response.isSuccessful) {
                    print("The deleting was successful")
                }
            } catch(e: Exception){
                    print("Error")
            }
        }

    }
}

