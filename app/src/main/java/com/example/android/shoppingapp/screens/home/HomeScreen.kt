package com.example.android.shoppingapp.screens.home

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.android.shoppingapp.R
import com.example.android.shoppingapp.data.ProductListCategory
import com.example.android.shoppingapp.utils.NavActions
import kotlinx.coroutines.CoroutineScope


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    currentRoute: String,
    navActions: NavActions,
    coroutineScope: CoroutineScope,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val homeScreenState by homeViewModel.homeScreenState.collectAsState()
    val context = LocalContext.current
    val categoriesMap: Map<ProductListCategory, String> = remember {
        mapOf(
            ProductListCategory.AllCategories to "All",
            ProductListCategory.Electronics to "Electronics",
            ProductListCategory.Jewelery to "Jewellery",
            ProductListCategory.MenClothing to "Men Clothing",
            ProductListCategory.WomenClothing to "Women Clothing"
        )
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Gray)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(MaterialTheme.colorScheme.primary)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Products",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .padding(end = 8.dp)
                    .weight(1f)
            )
            IconButton(modifier = Modifier, onClick = {
                navActions.navigateToCart()
            }) {
                Icon(Icons.Filled.ShoppingCart, "cart icon")
            }
            IconButton(modifier = Modifier, onClick = {
                navActions.navigateToPurchaseHistory()
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_history_24),
                    contentDescription = "purchase history"
                )
            }
        }
        /*
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(Color.Cyan)
        ) {

        }

         */
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(12.dp)
        ) {
            item {
                if (homeScreenState.isLoading) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .aspectRatio(1f),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator()
                    }
                }
                if (homeScreenState.products.isNotEmpty()) {
                    ProductListCategories(
                        homeScreenState = homeScreenState,
                        categoriesMap = categoriesMap,
                        onClick = { category ->
                            homeViewModel.getProducts(productListCategory = category)
                        })
                }
            }
            items(
                items = homeScreenState.products,
                key = { it.hashCode() }
            ) { product ->
                ProductItem(
                    modifier = Modifier
                        .padding(start = 4.dp, end = 4.dp, top = 4.dp, bottom = 12.dp)
                        .fillMaxWidth(),
                    product = product,
                    onProductClick = { navActions.navigateToProductDetail(product.id) },
                )
            }
        }
    }

}