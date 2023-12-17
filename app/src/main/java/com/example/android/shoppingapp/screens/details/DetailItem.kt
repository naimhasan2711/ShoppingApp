package com.example.android.shoppingapp.screens.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.android.shoppingapp.data.models.ProductsItem
import java.util.Locale

/*
This composable is responsible for showing product details screen
 */
@Composable
fun DetailItem(
    modifier: Modifier = Modifier,
    product: ProductsItem,
    onAddToCart: (Boolean) -> Unit,
) {
    var isAddedToCart by remember { mutableStateOf(product.addedToCart) }
    val buttonText = if (isAddedToCart) {
        "Added to Cart"
    } else {
        "Add to cart"
    }
    val  buttonIcon = if(isAddedToCart){
        Icons.Default.Done
    }else{
        Icons.Outlined.ShoppingCart
    }
    Card(
        modifier = modifier,
        elevation = 6.dp,
    ) {
        Column(modifier = Modifier.background(MaterialTheme.colorScheme.surface)) {
            //product image
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .padding(horizontal = 16.dp),
                model = ImageRequest.Builder(LocalContext.current).data(product.image).build(),
                contentDescription = "description",
                contentScale = ContentScale.Inside,
            )
            Spacer(modifier = Modifier.height(16.dp))
            //product title
            Text(
                text = product.title,
                fontSize = 24.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(start = 24.dp, end = 24.dp, top = 16.dp),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                //product category
                Text(
                    text = product.category.toUpperCase(Locale.ROOT),
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.primary,
                    maxLines = 1,
                    fontWeight = FontWeight.Medium,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
                )

                //product rating and rating counts
                Text(
                    text = "${product.rating.rate} ⭐ (${product.rating.count})",
                    fontSize = 14.sp,
                    color = Color.Yellow,
                    maxLines = 1,
                    fontWeight = FontWeight.Medium,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                )
            }

            //product descriptions
            Text(
                text = product.description,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
            )
            Row(
                modifier = Modifier
                    .padding(start = 8.dp, end = 8.dp, top = 8.dp, bottom = 8.dp)
            ) {
                //add/remove to cart button
                OutlinedButton(
                    onClick = {
                        onAddToCart(!product.addedToCart)
                        isAddedToCart = !isAddedToCart
                    },
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .weight(1f),
                    shape = RoundedCornerShape(10)
                ) {
                        Icon(
                            imageVector = buttonIcon,
                            contentDescription = "Checked icon",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    Text(
                        text = buttonText,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}