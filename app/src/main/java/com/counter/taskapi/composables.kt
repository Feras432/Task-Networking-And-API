package com.counter.taskapi

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.counter.taskapi.model.Pet
import com.counter.taskapi.viewModel.PetViewModel

@Composable
fun PetListScreen(modifier: Modifier = Modifier, viewModel: PetViewModel= viewModel()) {
    val pets = viewModel.pets
    LazyColumn(modifier = modifier) {
        items(pets) { pet ->
            PetItem(pet, viewModel())
        }
    }
}

@Composable
fun PetItem(pet: Pet, petViewModel: PetViewModel = viewModel()) {
    Card(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxWidth()
        ,
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),

        ) {
        Row (
            Modifier
                .padding(10.dp)
                .height(150.dp),
            verticalAlignment = Alignment.CenterVertically)
        {
            AsyncImage(model = pet.imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .height(100.dp)
                    .width(100.dp)
            )
            Column {
                Text(text = pet.name,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold)
                Text(text = pet.gender,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis)
                Text(text = "${pet.age}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    modifier = Modifier
                        .padding(top = 10.dp))
            }

        }
             Button(onClick = {
                 petViewModel.deletePet(pet.id)
                 petViewModel.fetchPets()
             }) {
                Text("Delete")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppContent() {
    val petViewModel: PetViewModel = viewModel()
    val navController = rememberNavController()

    Scaffold(
        topBar = { TopAppBar(title = { Text("My Pet List") }) }
        ,
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("addPet")}) {
                Text("+")
            }
        }
    ) { padding ->
        NavHost(navController = navController, startDestination = "petList") {
            composable("petList") {

                PetListScreen(Modifier.padding(padding), petViewModel)
            }
            composable("addPet") {
                AddPetScreen(viewModel())

            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AddPetScreen(petViewModel: PetViewModel = viewModel()) {

    var name by remember { mutableStateOf("") }
    var age by remember { mutableStateOf(0) }
    var imageUrl by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }

    Scaffold {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Add a New Pet",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            InputField(
                value = name,
                onValueChange = { name = it },
                label = "Name"
            )
            InputField(
                value = age.toString(),
                onValueChange = { age = it.toInt() },
                label = "Age"
            )
            InputField(
                value = imageUrl,
                onValueChange = { imageUrl = it },
                label = "Image URL"
            )
            InputField(
                value = gender,
                onValueChange = { gender = it },
                label = "Gender"
            )

            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    val newPet = Pet(
                        id = 0,
                        name = name,
                        adopted = false,
                        age = age,
                        imageUrl = imageUrl,
                        gender = gender,

                    )
                    petViewModel.addPet(newPet)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Add Pet")
            }
        }
    }
}

@Composable
fun InputField(value: String, onValueChange: (String) -> Unit, label: String, keyboardType: KeyboardType = KeyboardType.Text) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType)
    )
}