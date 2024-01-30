package com.example.pixelshop.ui.home.screens.favourite_screen

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pixelshop.data.local.models.FavoriteProduct
import com.example.pixelshop.data.local.repositories.LocalRepository
import com.example.pixelshop.util.Resource
import com.example.pixelshop.util.getUserIdFromSharedPref
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val localRepository: LocalRepository,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    // MutableStateFlow to hold and emit the state of favorite products.
    private val _favoriteCarts = MutableStateFlow<Resource<List<FavoriteProduct>>>(Resource.Idle)
    val favoriteCarts: StateFlow<Resource<List<FavoriteProduct>>> = _favoriteCarts

    init {
        // Fetch favorite products when ViewModel is created.
        getFavoriteProducts()
    }

    // Retrieves favorite products from the local database by user ID
    private fun getFavoriteProducts() = viewModelScope.launch {
        val userId = getUserIdFromSharedPref(sharedPreferences)
        val result = localRepository.getFavoriteProductsFromDb(userId)
        _favoriteCarts.value = result // Update the state with the result
    }

    // Deletes a specified favorite product and updates the list.
    fun deleteFavoriteItem(favoriteProduct: FavoriteProduct) = viewModelScope.launch {
        localRepository.deleteFavoriteItemFromDb(favoriteProduct)
        getFavoriteProducts() // Refresh the favorite products list
    }
}
