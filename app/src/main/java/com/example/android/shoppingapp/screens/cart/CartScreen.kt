package com.example.android.shoppingapp.screens.cart

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.android.shoppingapp.OrderViewModel
import com.example.android.shoppingapp.data.models.Order
import com.example.android.shoppingapp.data.models.ProductsItem
import com.example.android.shoppingapp.utils.NavActions
import kotlinx.coroutines.CoroutineScope
/*
This class is responsible for showing cart screens
 */
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun CartScreen(
    currentRoute: String,
    navActions: NavActions,
    coroutineScope: CoroutineScope,
    cartViewModel: CartViewModel = hiltViewModel(),
    orderViewModel: OrderViewModel = hiltViewModel()
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val addedToCartProducts =
        cartViewModel.cartScreenState.value.allProducts.filter { it.addedToCart }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Red)
    ) {
        Scaffold(
            scaffoldState = rememberScaffoldState(),
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                //top-bar with text and back button
                TopAppBar(
                    title = {
                        Text(
                            text = "Cart",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Normal,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { navActions.navController.navigateUp() }) {
                            Icon(
                                Icons.Default.ArrowBack,
                                contentDescription = "Description",
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    },
                    colors = TopAppBarDefaults.smallTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                    ),
                    scrollBehavior = scrollBehavior
                )
            }
        ) { contentPadding ->
            if (addedToCartProducts.isNotEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(1f), horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    //columns for list or cart product
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f)
                            .padding(contentPadding)
                    ) {
                        item {
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                        items(
                            items = addedToCartProducts,
                            key = { it.hashCode() }
                        ) { product ->
                            //below functions is for showing single cart product
                            CartProductItem(
                                modifier = Modifier
                                    .padding(start = 4.dp, end = 4.dp, top = 4.dp, bottom = 12.dp)
                                    .fillMaxWidth()
                                    .animateItemPlacement(),
                                product = product,
                                removeFromCart = {
                                    cartViewModel.updateProduct(it)
                                }
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                    //buttons for pay products from the cart
                    Button(
                        onClick = {
                            val arrayList = ArrayList<String>()
                            var price = 0.0
                            var order: Order? = null;
                            for (item in addedToCartProducts) {
                                val orderId = (1000000..9000000).random().toString()
                                val prod = ProductsItem(
                                    item.category,
                                    item.description,
                                    item.id,
                                    item.image,
                                    item.price,
                                    item.rating,
                                    item.title,
                                    false
                                )
                                arrayList.add(item.title)
                                price += item.price
                                val time = System.currentTimeMillis()
                                order = Order(orderId, arrayList, price, time)
                                //after buying product, remove from teh cart
                                cartViewModel.updateProduct(prod)
                            }
                            //add to order history database
                            order?.let {
                                orderViewModel.addOrder(order)
                            }
                            navActions.navigateToHome()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp),
                        shape = RoundedCornerShape(20)
                    ) {
                        Text(
                            text = "Place Order",
                            color = Color.White,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            } else {
                //if cart is empty, show a text
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = MaterialTheme.colorScheme.background)
                        .verticalScroll(rememberScrollState())
                        .padding(contentPadding),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "No Cart Product",
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        }
    }
}