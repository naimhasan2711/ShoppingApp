package com.example.android.shoppingapp.screens.search

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.android.shoppingapp.R
import com.example.android.shoppingapp.data.models.ProductsItem
import com.example.android.shoppingapp.utils.NavActions
import kotlinx.coroutines.CoroutineScope

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    navActions: NavActions,
    coroutineScope: CoroutineScope,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    if (uiState.errorMessages.isNotEmpty()) {
        ShoppingShowToastMessage(message = uiState.errorMessages.first().asString())
        viewModel.errorConsumed()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(MaterialTheme.colorScheme.primary)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            IconButton(modifier = Modifier, onClick = {
                navActions.navigateToHome()
            }) {
                Icon(Icons.Filled.ArrowBack, "cart icon")
            }
            Text(
                text = "Search",
                fontSize = 24.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .padding(end = 8.dp)

            )
        }
        SearchScreenContent(
            modifier = modifier,
            searchValue = viewModel.searchedText,
            onSearchValChanged = {
                viewModel.updateSearchedText(it)
                viewModel.searchProductList()
            },
            searchResult = uiState.searchResult,
            isSearchResultEmpty = uiState.isSearchResultEmpty,
            onProductClick = {
                navActions.navigateToHome()
            }
        )
    }

}

@Composable
fun ShoppingShowToastMessage(message: String) {
    Toast.makeText(
        LocalContext.current,
        message,
        Toast.LENGTH_SHORT
    ).show()
}

@Composable
private fun SearchScreenContent(
    modifier: Modifier,
    searchValue: String,
    onSearchValChanged: (String) -> Unit,
    searchResult: List<ProductsItem>,
    isSearchResultEmpty: Boolean,
    onProductClick: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SearchField(modifier, searchValue, onSearchValChanged)
        LazyVerticalGrid(
            modifier = modifier.fillMaxWidth(),
            contentPadding = PaddingValues(
                vertical = 16.dp
            ),
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(items = searchResult){
                ShoppingProductItem(product = it)
            }
        }

        if (isSearchResultEmpty) {
            SearchResultEmptyView(
                modifier,
                R.drawable.ic_launcher_foreground,
                R.string.search_result_empty_message
            )
        } else if (searchResult.isEmpty()) {
            SearchResultEmptyView(
                modifier,
                R.drawable.ic_launcher_foreground,
                R.string.search_something

            )
        }
    }
}