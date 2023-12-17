package com.example.android.shoppingapp.utils

import androidx.navigation.NavHostController


/*
This class is associates with navgraph
Usually contains screens name and their route
 */
object NavRoutes {
    const val HOME = "Home"
    const val PRODUCT_DETAIL = "Product_Detail?productId="
    const val CART = "Cart"
    const val PURCHASE_HISTORY = "Purchase_History"
    const val SEARCH = "Search"

}

class NavActions(private val navHostController: NavHostController) {
    val navController = navHostController

    val navigateToHome: () -> Unit = {
        navController.navigate(NavRoutes.HOME) {
            popUpTo(NavRoutes.HOME) { inclusive = true }
        }
    }

    val navigateToProductDetail: (Int) -> Unit = {
        navHostController.navigate(NavRoutes.PRODUCT_DETAIL + it)
    }

    val navigateToCart: () -> Unit = {
        navController.navigate(NavRoutes.CART)
    }

    val navigateToPurchaseHistory: () -> Unit = {
        navController.navigate(NavRoutes.PURCHASE_HISTORY)
    }
    val navigateToSearch: () -> Unit = {
        navController.navigate(NavRoutes.SEARCH)
    }
}