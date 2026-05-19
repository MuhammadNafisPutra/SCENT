package com.example.scent

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.scent.data.repository.ProductRepository
import com.example.scent.ui.favorit.FavoriteScreen
import com.example.scent.ui.favorit.FavoriteViewModel
import com.example.scent.ui.theme.SCENTTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SCENTTheme {
                ScentApp()
            }
        }
    }
}

@Composable
fun ScentApp() {
    val navController = rememberNavController()
    val repository    = remember { ProductRepository() }

    Scaffold(
        modifier       = Modifier.fillMaxSize(),
        containerColor = Color(0xFF0A0A0A)
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            ScentNavHost(
                navController = navController,
                repository    = repository
            )
        }
    }
}

@Composable
fun ScentNavHost(
    navController : NavHostController,
    repository    : ProductRepository
) {
    NavHost(
        navController    = navController,
        startDestination = "favorit"
    ) {
        composable("favorit") {
            val viewModel: FavoriteViewModel = viewModel(
                factory = FavoriteViewModel.Factory(repository)
            )
            FavoriteScreen(
                viewModel      = viewModel,
                onProductClick = { productId ->
                    navController.navigate("detail/$productId")
                }
            )
        }
    }
}