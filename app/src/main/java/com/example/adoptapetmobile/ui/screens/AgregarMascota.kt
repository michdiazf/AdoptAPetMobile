package com.example.adoptapetmobile.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.adoptapetmobile.data.model.Mascota
import com.example.adoptapetmobile.ui.components.TopBar
import com.example.adoptapetmobile.viewmodel.MascotaViewModel

@Composable
fun AgregarMascota(
    navController: NavController,
    viewModel: MascotaViewModel
) {
    val formState by viewModel.formState.collectAsState()
    var mostrarDialogo by remember { mutableStateOf(false) }

    val galeriaLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            viewModel.onImagenUriChange(it.toString())
        }
    }

    Scaffold(
        topBar = {
            TopBar(
                titulo = "Agregar Mascota",
                mostrarAtras = true,
                onClickAtras = { navController.popBackStack() }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = formState.nombre,
                onValueChange = { viewModel.onNombreChange(it) },
                label = { Text("Nombre *") },
                isError = formState.nombreError != null,
                supportingText = {
                    formState.nombreError?.let {
                        Text(it, color = MaterialTheme.colorScheme.error)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            var expandido by remember { mutableStateOf(false) }
            val especies = listOf("Perro", "Gato", "Conejo", "Otro")

            ExposedDropdownMenuBox(
                expanded = expandido,
                onExpandedChange = { expandido = !expandido }
            ) {
                OutlinedTextField(
                    value = formState.especie,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Especie") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandido) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                )

                ExposedDropdownMenu(
                    expanded = expandido,
                    onDismissRequest = { expandido = false }
                ) {
                    especies.forEach { especie ->
                        DropdownMenuItem(
                            text = { Text(especie) },
                            onClick = {
                                viewModel.onEspecieChange(especie)
                                expandido = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = formState.raza,
                onValueChange = { viewModel.onRazaChange(it) },
                label = { Text("Raza") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = formState.edad,
                onValueChange = { viewModel.onEdadChange(it) },
                label = { Text("Edad (años) *") },
                isError = formState.edadError != null,
                supportingText = {
                    formState.edadError?.let {
                        Text(it, color = MaterialTheme.colorScheme.error)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = formState.descripcion,
                onValueChange = { viewModel.onDescripcionChange(it) },
                label = { Text("Descripción") },
                minLines = 3,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { galeriaLauncher.launch("image/*") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.CameraAlt, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Seleccionar foto de galería")
            }

            if (formState.imagenUri.isNotEmpty()) {
                Text(
                    text = "Foto seleccionada ✓",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    if (viewModel.validateForm()) {
                        val nuevaMascota = Mascota(
                            nombre = formState.nombre,
                            especie = formState.especie,
                            raza = formState.raza,
                            edad = formState.edad.toInt(),
                            descripcion = formState.descripcion,
                            imagenUri = formState.imagenUri
                        )
                        viewModel.addMascota(nuevaMascota)
                        viewModel.resetForm()
                        mostrarDialogo = true
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Guardar Mascota")
            }
        }
    }

    if (mostrarDialogo) {
        AlertDialog(
            onDismissRequest = { },
            title = { Text("¡Éxito!") },
            text = { Text("La mascota fue agregada correctamente") },
            confirmButton = {
                TextButton(onClick = {
                    mostrarDialogo = false
                    navController.popBackStack()
                }) {
                    Text("Aceptar")
                }
            }
        )
    }
}