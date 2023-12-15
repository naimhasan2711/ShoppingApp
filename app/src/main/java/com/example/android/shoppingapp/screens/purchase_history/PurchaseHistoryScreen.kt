package com.example.android.shoppingapp.screens.purchase_history

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.android.shoppingapp.OrderViewModel
import com.example.android.shoppingapp.utils.NavActions
import kotlinx.coroutines.CoroutineScope
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.Date

@Composable
fun PurchaseHistoryScreen(
    navActions: NavActions,
    coroutineScope: CoroutineScope,
    orderViewModel: OrderViewModel = hiltViewModel()
) {
    val orderList = orderViewModel.orderList.observeAsState().value
    Timber.tag("PurchaseHistoryScreen: ").d(orderList.toString())
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Gray)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(androidx.compose.material3.MaterialTheme.colorScheme.primary)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(modifier = Modifier, onClick = {
                navActions.navigateToHome()
            }) {
                Icon(Icons.Filled.ArrowBack, "cart icon")
            }
            Text(
                text = "Products",
                fontSize = 24.sp,
                fontWeight = FontWeight.Medium,
                color = androidx.compose.material3.MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .padding(end = 8.dp)

            )
        }

        LazyColumn(
            modifier = Modifier.padding(vertical = 4.dp),
        ) {
            orderList?.let {
                items(it.toList()) { order ->
                    val sb = StringBuilder()
                    order.items.forEachIndexed { index, value ->
                        sb.append("${index + 1}. $value\n")
                    }
                    CardWithInformation(
                        order.id,
                        sb.toString(),
                        order.total_amount.toString(),
                        formatTimeStamp(order.order_date_time)
                    )
                }
            }
        }

    }

}

@SuppressLint("SimpleDateFormat")
private fun formatTimeStamp(time: Long): String {
    val sdf = SimpleDateFormat("dd/MM/yy hh:mm:ss a")
    val netDate = Date(time)
    return sdf.format(netDate)
}

@Composable
fun CardWithInformation(
    orderId: String,
    prodList: String,
    totalPrice: String,
    date: String,
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .background(MaterialTheme.colors.primary)
            .padding(horizontal = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(8.dp)
        ) {
            Text(
                text = "Order id: $orderId\n",
                style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = prodList,
                style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
                color = Color.White
            )

            Text(
                text = "Total Price: $${totalPrice}\n",
                style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
                color = Color.White
            )
            Text(
                text = "Placed order at: $date",
                style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,

                )
        }
    }
}