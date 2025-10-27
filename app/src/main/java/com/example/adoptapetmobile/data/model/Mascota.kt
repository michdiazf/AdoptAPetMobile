package com.example.adoptapetmobile.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "mascotas")
data class Mascota(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nombre: String,
    val especie: String,
    val raza: String,
    val edad: Int,
    val descripcion: String,
    val imagenUri: String = "",
    val esFavorito: Boolean = false,
    val fechaRegistro: Long = System.currentTimeMillis()
)