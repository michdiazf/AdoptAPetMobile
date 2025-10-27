package com.example.adoptapetmobile.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.adoptapetmobile.ui.components.CardMascota
import com.example.adoptapetmobile.ui.components.TopBar
import com.example.adoptapetmobile.viewmodel.MascotaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Favoritos(
    navController: NavController,
    viewModel: MascotaViewModel
) {
    val favoritos by viewModel.favoriteMascotas.collectAsState()

    Scaffold(
        topBar = {
            TopBar(
                titulo = "Favoritos",
                mostrarAtras = true,
                onClickAtras = { navController.popBackStack() }
            )
        }
    ) { padding ->
        if (favoritos.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No tienes mascotas favoritas",
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
                favoritos.forEach { mascota ->
                    CardMascota(
                        mascota = mascota,
                        onClick = {
                            navController.navigate("detalle/${mascota.id}")
                        },
                        onFavoriteClick = {
                            viewModel.toggleFavorite(mascota.id, false)
                        }
                    )
                }
            }
        }
    }
}