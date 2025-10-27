package com.example.adoptapetmobile.data.local

import androidx.room.*
import com.example.adoptapetmobile.data.model.Mascota
import kotlinx.coroutines.flow.Flow

@Dao
interface MascotaDao {

    @Query("SELECT * FROM mascotas ORDER BY fechaRegistro DESC")
    fun getAllMascotas(): Flow<List<Mascota>>

    @Query("SELECT * FROM mascotas WHERE id = :mascotaId")
    suspend fun getMascotaById(mascotaId: Int): Mascota?

    @Query("SELECT * FROM mascotas WHERE esFavorito = 1")
    fun getFavoriteMascotas(): Flow<List<Mascota>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMascota(mascota: Mascota)

    @Update
    suspend fun updateMascota(mascota: Mascota)

    @Delete
    suspend fun deleteMascota(mascota: Mascota)

    @Query("UPDATE mascotas SET esFavorito = :isFavorite WHERE id = :mascotaId")
    suspend fun updateFavorite(mascotaId: Int, isFavorite: Boolean)
}