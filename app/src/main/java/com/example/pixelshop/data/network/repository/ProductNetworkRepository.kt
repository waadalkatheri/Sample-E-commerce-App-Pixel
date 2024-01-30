package com.example.pixelshop.data.network.repository

import com.example.pixelshop.data.network.dto.Product
import com.example.pixelshop.util.Resource

interface ProductNetworkRepository {
    suspend fun getProductsListFromApi(): Resource<List<Product>>

    suspend fun getSingleProductByIdFromApi(productId: Int): Resource<Product>

    suspend fun getProductsListBySearchFromApi(query: String): Resource<List<Product>>

    suspend fun getAllCategoriesListFromApi(): Resource<List<String>>

    suspend fun getProductsListByCategoryNameFromApi(categoryName: String): Resource<List<Product>>
}