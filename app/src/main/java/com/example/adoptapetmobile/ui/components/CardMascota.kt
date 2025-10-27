package com.example.adoptapetmobile.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.adoptapetmobile.data.model.Mascota

@Composable
fun CardMascota(
    mascota: Mascota,
    onClick: () -> Unit,
    onFavoriteClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Imagen de la mascota
            if (mascota.imagenUri.isNotEmpty()) {
                AsyncImage(
                    model = mascota.imagenUri,
                    contentDescription = "Foto de ${mascota.nombre}",
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
            } else {
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.secondaryContainer
                    ) {
                        Text(
                            text = mascota.nombre.first().toString(),
                            style = MaterialTheme.typography.headlineLarge,
                            modifier = Modifier.wrapContentSize(Alignment.Center)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Info de la mascota
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = mascota.nombre,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "${mascota.especie} - ${mascota.raza}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "${mascota.edad} año(s)",
                    style = MaterialTheme.typography.bodySmall
                )
            }

            // Botón de favorito
            IconButton(onClick = onFavoriteClick) {
                Icon(
                    imageVector = if (mascota.esFavorito) Icons.Default.Favorite
                    else Icons.Default.FavoriteBorder,
                    contentDescription = if (mascota.esFavorito) "Quitar de favoritos"
                    else "Agregar a favoritos",
                    tint = if (mascota.esFavorito) MaterialTheme.colorScheme.error
                    else MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}