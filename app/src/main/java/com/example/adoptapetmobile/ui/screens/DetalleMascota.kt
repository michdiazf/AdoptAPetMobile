package com.example.adoptapetmobile.ui.screens


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.adoptapetmobile.ui.components.TopBar
import com.example.adoptapetmobile.viewmodel.MascotaViewModel
import kotlinx.coroutines.launch

@Composable
fun DetalleMascota(
    navController: NavController,
    viewModel: MascotaViewModel,
    mascotaId: Int
) {
    val mascotas by viewModel.allMascotas.collectAsState()
    val mascota = mascotas.find { it.id == mascotaId }
    var mostrarDialogo by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    if (mascota == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        return
    }

    Scaffold(
        topBar = {
            TopBar(
                titulo = mascota.nombre,
                mostrarAtras = true,
                onClickAtras = { navController.popBackStack() }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            if (mascota.imagenUri.isNotEmpty()) {
                AsyncImage(
                    model = mascota.imagenUri,
                    contentDescription = "Foto de ${mascota.nombre}",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop
                )
            } else {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    color = MaterialTheme.colorScheme.secondaryContainer
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = mascota.nombre.first().toString(),
                            style = MaterialTheme.typography.displayLarge
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = mascota.nombre,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row {
                Text(text = "Especie: ", fontWeight = FontWeight.Bold)
                Text(text = mascota.especie)
            }

            Row {
                Text(text = "Raza: ", fontWeight = FontWeight.Bold)
                Text(text = mascota.raza)
            }

            Row {
                Text(text = "Edad: ", fontWeight = FontWeight.Bold)
                Text(text = "${mascota.edad} año(s)")
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (mascota.descripcion.isNotEmpty()) {
                Text(
                    text = "Descripción:",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = mascota.descripcion)
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { mostrarDialogo = true },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Eliminar mascota"
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Eliminar mascota")
            }
        }
    }

    if (mostrarDialogo) {
        AlertDialog(
            onDismissRequest = { mostrarDialogo = false },
            title = { Text("¿Eliminar mascota?") },
            text = { Text("Esta acción no se puede deshacer") },
            confirmButton = {
                TextButton(
                    onClick = {
                        scope.launch {
                            viewModel.deleteMascota(mascota)
                            navController.popBackStack()
                        }
                    }
                ) {
                    Text("Eliminar", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { mostrarDialogo = false }) {
                    Text("Cancelar")
                }
            }
        )
    }
}