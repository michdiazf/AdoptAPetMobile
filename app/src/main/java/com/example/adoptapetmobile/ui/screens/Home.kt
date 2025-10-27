package com.example.adoptapetmobile.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.adoptapetmobile.ui.components.CardMascota
import com.example.adoptapetmobile.ui.components.TopBar
import com.example.adoptapetmobile.viewmodel.AuthViewModel
import com.example.adoptapetmobile.viewmodel.MascotaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(
    navController: NavController,
    viewModel: MascotaViewModel,
    authViewModel: AuthViewModel = viewModel()
) {
    val mascotas by viewModel.allMascotas.collectAsState()

    Scaffold (
        topBar = {
            TopBar(titulo = "AdoptAPet")
            Spacer(
                modifier = Modifier
                    .height(40.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )

            Button(
                onClick = {
                    authViewModel.cerrarSesion()
                    navController.navigate("login"){
                        popUpTo("home") { inclusive = true}
                    }
                },
                modifier = Modifier
                    .padding(end = 16.dp)
                    .fillMaxWidth()
                    .wrapContentWidth(Alignment.End)
            ){
                Text("Cerrar Sesion")
            }
        },

        floatingActionButton = {
            Column {
                FloatingActionButton(
                    onClick = { navController.navigate("favoritos") },
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    modifier = Modifier.padding(bottom = 16.dp)
                ) {
                    Icon(Icons.Default.Favorite, contentDescription = "Ver favoritos")
                }

                FloatingActionButton(
                    onClick = { navController.navigate("agregar") }
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Agregar mascota")
                }
            }
        }
    ) { padding ->
        if (mascotas.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No hay mascotas registradas",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .verticalScroll(rememberScrollState())
            ) {
                mascotas.forEach { mascota ->
                    CardMascota(
                        mascota = mascota,
                        onClick = {
                            navController.navigate("detalle/${mascota.id}")
                        },
                        onFavoriteClick = {
                            viewModel.toggleFavorite(mascota.id, !mascota.esFavorito)
                        }
                    )
                }
            }
        }
    }
}