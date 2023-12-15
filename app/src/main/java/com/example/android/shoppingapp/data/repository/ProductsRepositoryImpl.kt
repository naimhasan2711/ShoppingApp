package com.example.android.shoppingapp.data.repository

import com.example.android.shoppingapp.data.ProductListCategory
import com.example.android.shoppingapp.data.local.ProductDao
import com.example.android.shoppingapp.data.models.ProductsItem
import com.example.android.shoppingapp.data.remote.api.ShoppingApi
import com.example.android.shoppingapp.screens.search.dbCall
import com.example.android.shoppingapp.utils.getProductsByCategory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class ProductsRepositoryImpl @Inject constructor(
    private val fakeStoreApi: ShoppingApi,
    private val productDao: ProductDao,
) : ProductsRepository {

    override fun getProducts(
        coroutineScope: CoroutineScope,
        productListCategory: ProductListCategory,
        result: (List<ProductsItem>) -> Unit
    ) {
        coroutineScope.launch((Dispatchers.IO)) {
            productDao.getAllProducts().collectLatest { allProducts ->
                if (allProducts.isEmpty()) {
                    fakeStoreApi.getProducts().enqueue(object : Callback<List<ProductsItem>?> {
                        override fun onResponse(
                            call: Call<List<ProductsItem>?>,
                            response: Response<List<ProductsItem>?>
                        ) {
                            response.body()?.let { products ->
                                coroutineScope.launch(Dispatchers.IO) {
                                    productDao.insertProducts(products)
                                }
                                products.getProductsByCategory(productListCategory) { prods ->
                                    result(prods)
                                }
                            }
                                ?: result(emptyList())
                        }

                        override fun onFailure(call: Call<List<ProductsItem>?>, t: Throwable) {
                            result(emptyList())
                        }
                    })
                } else {
                    allProducts.getProductsByCategory(productListCategory) { result(it) }
                }
            }
        }
    }

    override suspend fun getAllProducts():com.example.android.shoppingapp.screens.search.Response<List<ProductsItem>>  {
        return dbCall { productDao.getAllProductss() }
    }

    override fun updateProduct(
        coroutineScope: CoroutineScope,
        product: ProductsItem,
        onProductUpdated: () -> Unit
    ) {
        val job = coroutineScope.launch(Dispatchers.IO) {
            productDao.insertProduct(product)
        }
        if (job.isCompleted) onProductUpdated()
    }

    override fun getProductById(
        productId: Int,
        coroutineScope: CoroutineScope,
        result: (ProductsItem) -> Unit
    ) {
        coroutineScope.launch(Dispatchers.IO) {
            val product = productDao.getProductById(productId)
            result(product)
        }
    }
}