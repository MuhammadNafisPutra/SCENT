package com.contoh.scentapp.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.contoh.scentapp.ui.detail.DetailScreen
import com.contoh.scentapp.ui.favorite.FavoriteScreen
import com.contoh.scentapp.ui.home.HomeScreen
import com.contoh.scentapp.ui.profile.ProfileScreen
import com.contoh.scentapp.ui.screen.CartScreen
//import com.contoh.scentapp.ui.search.SearchScreen
import com.contoh.scentapp.ui.theme.ScentBlack
import com.contoh.scentapp.ui.theme.components.ScentBottomNavBar

// ── Routes ────────────────────────────────────────────────────────────────────

object Routes {
    const val HOME     = "home"
    const val FAVORITE = "favorite"
    const val CART     = "cart"
    const val PROFILE  = "profile"
    const val DETAIL   = "detail/{productId}"
    const val SEARCH   = "search?query={query}"

    fun detailRoute(productId: Int) = "detail/$productId"
    fun searchRoute(query: String = "") =
        if (query.isBlank()) "search?query=" else "search?query=$query"
}

// ── Screens yang tampilkan bottom bar ─────────────────────────────────────────

private val bottomNavRoutes = setOf(
    Routes.HOME, Routes.FAVORITE, Routes.CART, Routes.PROFILE
)

// ── App Navigation ────────────────────────────────────────────────────────────

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute   = backStackEntry?.destination?.route ?: Routes.HOME

    val showBottomBar = currentRoute in bottomNavRoutes

    Scaffold(
        containerColor = ScentBlack,
        bottomBar = {
            if (showBottomBar) {
                ScentBottomNavBar(
                    currentRoute = currentRoute,
                    onNavigate   = { route ->
                        navController.navigate(route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState    = true
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController    = navController,
            startDestination = Routes.HOME,
            modifier         = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // ── Tab utama ─────────────────────────────────────────────────────
            composable(Routes.HOME) {
                HomeScreen(
                    onProductClick = { productId ->
                        navController.navigate(Routes.detailRoute(productId))
                    },
                    onSearchClick  = {
                        navController.navigate(Routes.searchRoute())
                    }
                )
            }
            composable(Routes.FAVORITE) { FavoriteScreen() }
            composable(Routes.CART)     { CartScreen()     }
            composable(Routes.PROFILE)  { ProfileScreen()  }

            // ── Detail produk ─────────────────────────────────────────────────
            composable(
                route     = Routes.DETAIL,
                arguments = listOf(
                    navArgument("productId") { type = NavType.IntType }
                )
            ) { backStack ->
                val productId = backStack.arguments?.getInt("productId") ?: return@composable
                DetailScreen(
                    productId = productId,
                    onBack    = { navController.popBackStack() }
                )
            }

            // ── Search & filter ───────────────────────────────────────────────
            composable(
                route     = Routes.SEARCH,
                arguments = listOf(
                    navArgument("query") {
                        type         = NavType.StringType
                        defaultValue = ""
                        nullable     = false
                    }
                )
            ) { backStack ->
                val initialQuery = backStack.arguments?.getString("query") ?: ""
                SearchScreen(
                    initialQuery = initialQuery,
                    onBack       = { navController.popBackStack() }
                )
            }
        }
    }
}

@Composable
fun SearchScreen(initialQuery: String, onBack: () -> Boolean) {
    TODO("Not yet implemented")
}