# 🐾 AdoptaPetMobile
**Aplicación Android para adopción de mascotas con Kotlin + Jetpack Compose + Room**

Este proyecto tiene como objetivo enseñar cómo crear una aplicación móvil completa con **arquitectura MVVM**, **persistencia local con Room**, **formularios validados** y **recursos nativos** usando Kotlin y Jetpack Compose.

---

## 🚀 Objetivo

Aprender paso a paso cómo:
- Implementar arquitectura MVVM en Android
- Usar Room para persistencia de datos local
- Crear formularios interactivos con validaciones en tiempo real
- Integrar recursos nativos del dispositivo (galería de imágenes)
- Gestionar estado reactivo con StateFlow
- Navegar entre pantallas con Navigation Compose

---

## 👥 Equipo

- **Michelle Diaz**
- **Kevin Morales**
- **Veronica Verde**

**Equipo:** AdoptAPetMobile  
**Sección:** 003D  
**Asignatura:** DSY1105 - Desarrollo de Aplicaciones Móviles  
**Institución:** DuocUC - 2025

---

## 🧩 Requisitos previos

Antes de comenzar, asegúrate de tener:

✅ **Android Studio** Hedgehog (2023.1.1) o superior  
✅ **JDK 8** o superior  
✅ **Android SDK 24+** (Android 7.0 Nougat o superior)  
✅ **Emulador Android** o dispositivo físico



2️⃣ Abrir en Android Studio
Abre Android Studio
Ve a File → Open
Selecciona la carpeta del proyecto
Espera a que termine el Sync Gradle (puede tardar unos minutos)
3️⃣ Verificar configuración de Gradle
Asegúrate de que tu archivo build.gradle.kts (Module: app) tenga estas dependencias:
dependencies {
    // Compose
    implementation(platform("androidx.compose:compose-bom:2023.10.01"))
    implementation("androidx.compose.material3:material3")
    
    // Room Database
    implementation("androidx.room:room-runtime:2.6.0")
    implementation("androidx.room:room-ktx:2.6.0")
    ksp("androidx.room:room-compiler:2.6.0")
    
    // Navigation
    implementation("androidx.navigation:navigation-compose:2.7.5")
    
    // ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")
    
    // Coil (imágenes)
    implementation("io.coil-kt:coil-compose:2.5.0")
}
4️⃣ Configurar permisos en AndroidManifest.xml
Asegúrate de que tu archivo AndroidManifest.xml tenga estos permisos:
<uses-permission android:name="android.permission.READ_MEDIA_IMAGES" />
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"
    android:maxSdkVersion="32" />
5️⃣ Ejecutar la aplicación
Conecta un dispositivo físico o inicia un emulador Android
Click en el botón Run ▶️ (o presiona Shift + F10)
Selecciona tu dispositivo
Espera a que compile e instale
📱 Funcionalidades principales
✅ Gestión de mascotas
Agregar mascotas con información completa (nombre, especie, raza, edad, descripción)
Validación de formularios en tiempo real
Seleccionar foto desde galería del dispositivo
Ver listado completo de mascotas registradas
✅ Sistema de favoritos
Marcar/desmarcar mascotas como favoritas
Vista dedicada para mascotas favoritas
Indicador visual con corazón rojo
✅ Detalles y gestión
Ver información completa de cada mascota
Eliminar mascotas con confirmación
Navegación fluida entre pantallas
✅ Persistencia de datos
Almacenamiento local con Room/SQLite
Los datos persisten al cerrar la aplicación
Operaciones CRUD completas
