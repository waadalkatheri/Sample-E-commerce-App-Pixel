package com.example.pixelshop.data.network.repository

import com.example.pixelshop.data.network.api.ApiService
import com.example.pixelshop.data.network.dto.Product
import com.example.pixelshop.util.Resource
import javax.inject.Inject

class ProductNetworkRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : ProductNetworkRepository {

    // Fetches a list of all products from the API.
    override suspend fun getProductsListFromApi(): Resource<List<Product>> {
        return try {
            // Executes the API call and returns a success Resource with the products list.
            val response = apiService.getProductsListFromApi()
            Resource.Success(response.products)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    // Fetches details of a single product by its ID from the API.
    override suspend fun getSingleProductByIdFromApi(productId: Int): Resource<Product> {
        return try {
            // Executes the API call for a single product and returns a success Resource.
            val response = apiService.getSingleProductByIdFromApi(productId = productId)
            Resource.Success(response)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    // Fetches a list of products based on a search query from the API.
    override suspend fun getProductsListBySearchFromApi(query: String): Resource<List<Product>> {
        return try {
            // Retrieves products matching the search query and returns a success Resource.
            val response = apiService.getProductsListBySearchFromApi(query = query)
            Resource.Success(response.products)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    // Fetches a list of all product categories from the API.
    override suspend fun getAllCategoriesListFromApi(): Resource<List<String>> {
        return try {
            // Calls the API to get all categories and returns a success Resource.
            val response = apiService.getAllCategoriesListFromApi()
            Resource.Success(response)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    // Fetches a list of products from a specific category from the API.
    override suspend fun getProductsListByCategoryNameFromApi(categoryName: String): Resource<List<Product>> {
        return try {
            // Retrieves products for a given category and returns a success Resource.
            val response = apiService.getProductsListByCategoryNameFromApi(categoryName = categoryName)
            Resource.Success(response.products)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }
}
