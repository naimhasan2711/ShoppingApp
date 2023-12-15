package com.example.android.shoppingapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.android.shoppingapp.screens.cart.CartScreen
import com.example.android.shoppingapp.screens.details.DetailsScreen
import com.example.android.shoppingapp.screens.home.HomeScreen
import com.example.android.shoppingapp.screens.purchase_history.PurchaseHistoryScreen
import com.example.android.shoppingapp.screens.search.SearchScreen
import com.example.android.shoppingapp.ui.theme.ShoppingAppTheme
import com.example.android.shoppingapp.utils.NavActions
import com.example.android.shoppingapp.utils.NavRoutes
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShoppingAppTheme {
                val navController = rememberNavController()
                val navActions = remember(navController) { NavActions(navController) }
                val coroutineScope = rememberCoroutineScope()
                MainScreen(
                    activity = this,
                    navActions = navActions,
                    coroutineScope = coroutineScope
                )
            }
        }
    }
}


@Composable
private fun MainScreen(
    activity: MainActivity,
    navActions: NavActions,
    coroutineScope: CoroutineScope,
) {
    val currentBackStackEntry by navActions.navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route ?: NavRoutes.HOME
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding(),
        color = MaterialTheme.colorScheme.background
    ) {
        NavHost(
            navController = navActions.navController,
            startDestination = NavRoutes.HOME
        ) {
            composable(NavRoutes.HOME) {
                HomeScreen(
                    currentRoute = currentRoute,
                    navActions = navActions,
                    coroutineScope = coroutineScope
                )
            }
            composable(
                NavRoutes.PRODUCT_DETAIL + "{productId}",
                arguments = listOf(navArgument(name = "productId") {
                    type = NavType.IntType
                    defaultValue = -1
                })
            ) { currentBackStackEntry ->
                val productId = currentBackStackEntry.arguments?.getInt("productId") ?: -1
                DetailsScreen(
                    navActions = navActions,
                    productId = productId
                )
            }
            composable(NavRoutes.CART) {
                CartScreen(
                    currentRoute = currentRoute,
                    navActions = navActions,
                    coroutineScope = coroutineScope
                )
            }

            composable(NavRoutes.PURCHASE_HISTORY) {
                PurchaseHistoryScreen(
                    navActions = navActions,
                    coroutineScope = coroutineScope
                )
            }

            composable(NavRoutes.SEARCH) {
                SearchScreen(
                    navActions = navActions,
                    coroutineScope = coroutineScope
                )
            }

        }
    }
}