package com.example.adoptapetmobile.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.adoptapetmobile.ui.screens.AgregarMascota
import com.example.adoptapetmobile.ui.screens.DetalleMascota
import com.example.adoptapetmobile.ui.screens.Favoritos
import com.example.adoptapetmobile.ui.screens.Home
import com.example.adoptapetmobile.ui.screens.LoginScreen
import com.example.adoptapetmobile.ui.screens.RegisterScreen
import com.example.adoptapetmobile.viewmodel.MascotaViewModel

@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController(),
    viewModel: MascotaViewModel = viewModel()
) {
    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        composable("login"){
            LoginScreen(
                onLoggedIn = {
                    navController.navigate("home"){
                        popUpTo("login") { inclusive = true}
                    }
                },
                onGoRegister = {
                    navController.navigate("register")
                }
            )
        }

        composable("register"){
            RegisterScreen(
                onGoLogin = {
                    navController.navigate("login") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }

        composable("home") {
            Home(
                navController = navController,
                viewModel = viewModel
            )
        }

        composable("agregar") {
            AgregarMascota(
                navController = navController,
                viewModel = viewModel
            )
        }

        composable("favoritos") {
            Favoritos(
                navController = navController,
                viewModel = viewModel
            )
        }

        composable(
            route = "detalle/{mascotaId}",
            arguments = listOf(navArgument("mascotaId") { type = NavType.IntType })
        ) { backStackEntry ->
            val mascotaId = backStackEntry.arguments?.getInt("mascotaId") ?: 0
            DetalleMascota(
                navController = navController,
                viewModel = viewModel,
                mascotaId = mascotaId
            )
        }
    }
}