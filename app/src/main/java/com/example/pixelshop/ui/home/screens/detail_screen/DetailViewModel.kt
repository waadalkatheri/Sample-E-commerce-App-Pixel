package com.example.pixelshop.ui.home.screens.detail_screen

import android.content.SharedPreferences
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pixelshop.data.local.models.FavoriteProduct
import com.example.pixelshop.data.local.models.UserCart
import com.example.pixelshop.data.local.repositories.LocalRepository
import com.example.pixelshop.data.network.dto.Product
import com.example.pixelshop.data.network.repository.ProductNetworkRepository
import com.example.pixelshop.util.Resource
import com.example.pixelshop.util.getUserIdFromSharedPref
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val productNetworkRepository: ProductNetworkRepository,
    private val localRepository: LocalRepository,
    private val sharedPreferences: SharedPreferences,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    // MutableStateFlow to hold and emit the state of product details.
    private val _details = MutableStateFlow<Resource<Product>>(Resource.Idle)
    val details: StateFlow<Resource<Product>> = _details

    init {
        // Fetch product details when ViewModel is created.
        getDetail()
    }

    // Retrieves product details based on ID from network repository.
    fun getDetail() = viewModelScope.launch {
        // Get the product ID from savedStateHandle.
        savedStateHandle.get<Int>("id")?.let { id ->
            // Fetch product details and update the state.
            val result = productNetworkRepository.getSingleProductByIdFromApi(productId = id)
            _details.value = result
        }
    }

    // Adds a product to the user's cart in the local database.
    fun addToCart(userCart: UserCart) = viewModelScope.launch {
        // Insert cart item into the local database.
        localRepository.insertUserCartToDb(
            userCart.copy(
                userId = getUserIdFromSharedPref(sharedPreferences)
            )
        )
    }

    // Adds a product to the user's favorites in the local database.
    fun addToFavorite(favoriteProduct: FavoriteProduct) = viewModelScope.launch {
        // Insert favorite item into the local database.
        localRepository.insertFavoriteItemToDb(
            favoriteProduct.copy(
                userId = getUserIdFromSharedPref(sharedPreferences)
            )
        )
    }
}
