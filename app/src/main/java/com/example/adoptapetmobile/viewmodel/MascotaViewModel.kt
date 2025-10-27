package com.example.adoptapetmobile.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.adoptapetmobile.data.local.MascotaDataBase
import com.example.adoptapetmobile.data.model.Mascota
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MascotaViewModel(application: Application) : AndroidViewModel(application) {

    private val mascotaDao = MascotaDataBase.getDatabase(application).mascotaDao()

    val allMascotas: StateFlow<List<Mascota>> = mascotaDao.getAllMascotas()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val favoriteMascotas: StateFlow<List<Mascota>> = mascotaDao.getFavoriteMascotas()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private val _formState = MutableStateFlow(MascotaFormState())
    val formState: StateFlow<MascotaFormState> = _formState.asStateFlow()

    fun addMascota(mascota: Mascota) {
        viewModelScope.launch {
            mascotaDao.insertMascota(mascota)
        }
    }

    fun toggleFavorite(mascotaId: Int, isFavorite: Boolean) {
        viewModelScope.launch {
            mascotaDao.updateFavorite(mascotaId, isFavorite)
        }
    }

    fun deleteMascota(mascota: Mascota) {
        viewModelScope.launch {
            mascotaDao.deleteMascota(mascota)
        }
    }

    fun onNombreChange(nombre: String) {
        _formState.value = _formState.value.copy(
            nombre = nombre,
            nombreError = if (nombre.isBlank()) "El nombre es requerido" else null
        )
    }

    fun onEspecieChange(especie: String) {
        _formState.value = _formState.value.copy(especie = especie)
    }

    fun onRazaChange(raza: String) {
        _formState.value = _formState.value.copy(raza = raza)
    }

    fun onEdadChange(edad: String) {
        val edadInt = edad.toIntOrNull()
        _formState.value = _formState.value.copy(
            edad = edad,
            edadError = when {
                edad.isBlank() -> "La edad es requerida"
                edadInt == null -> "Debe ser un número válido"
                edadInt < 0 || edadInt > 30 -> "La edad debe estar entre 0 y 30"
                else -> null
            }
        )
    }

    fun onDescripcionChange(descripcion: String) {
        _formState.value = _formState.value.copy(descripcion = descripcion)
    }

    fun onImagenUriChange(uri: String) {
        _formState.value = _formState.value.copy(imagenUri = uri)
    }

    fun validateForm(): Boolean {
        val state = _formState.value

        val nombreError = if (state.nombre.isBlank()) "El nombre es requerido" else null
        val edadError = if (state.edad.isBlank()) "La edad es requerida"
        else if (state.edad.toIntOrNull() == null) "Debe ser un número"
        else null

        _formState.value = state.copy(
            nombreError = nombreError,
            edadError = edadError
        )

        return nombreError == null && edadError == null
    }

    fun resetForm() {
        _formState.value = MascotaFormState()
    }
}

data class MascotaFormState(
    val nombre: String = "",
    val nombreError: String? = null,
    val especie: String = "Perro",
    val raza: String = "",
    val edad: String = "",
    val edadError: String? = null,
    val descripcion: String = "",
    val imagenUri: String = ""
)