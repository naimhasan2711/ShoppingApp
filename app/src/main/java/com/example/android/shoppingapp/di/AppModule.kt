package com.example.android.shoppingapp.di

import android.app.Application
import androidx.room.Room
import com.example.android.shoppingapp.data.local.MyShopDatabase
import com.example.android.shoppingapp.data.local.OrderDao
import com.example.android.shoppingapp.data.local.OrderDatabase
import com.example.android.shoppingapp.data.remote.api.ShoppingApi
import com.example.android.shoppingapp.data.repository.OrderRepository
import com.example.android.shoppingapp.data.repository.ProductsRepository
import com.example.android.shoppingapp.data.repository.ProductsRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit = Retrofit.Builder().baseUrl(ShoppingApi.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create()).build()

    @Provides
    @Singleton
    fun provideFakeStoreApi(retrofit: Retrofit) = retrofit.create<ShoppingApi>()

    @Provides
    @Singleton
    fun provideMyShopDatabase(application: Application): MyShopDatabase {
        return Room.databaseBuilder(
            application.applicationContext,
            MyShopDatabase::class.java,
            "my_shop"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideDispatcherIO(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @Singleton
    fun provideOrderDatabase(application: Application): OrderDatabase {
        return Room.databaseBuilder(
            application.applicationContext,
            OrderDatabase::class.java,
            "my_orders"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideProductsRepository(
        fakeStoreApi: ShoppingApi,
        db: MyShopDatabase,
    ): ProductsRepository {
        return ProductsRepositoryImpl(fakeStoreApi = fakeStoreApi, productDao = db.productDao)
    }

    @Provides
    @Singleton
    fun provideOrderRepository(
        orderDao: OrderDao,
    ): OrderRepository {
        return OrderRepository(orderDao)
    }

    @Provides
    fun provideOrderDAO(orderDatabase: OrderDatabase): OrderDao {
        return orderDatabase.orderDAO()
    }
}