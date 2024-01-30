package com.example.pixelshop.ui.home.screens.cart_screen

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pixelshop.data.local.models.UserCart
import com.example.pixelshop.data.local.repositories.LocalRepository
import com.example.pixelshop.util.Resource
import com.example.pixelshop.util.getUserIdFromSharedPref
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val localRepository: LocalRepository,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    // StateFlow for managing and emitting user cart data
    private val _userCart = MutableStateFlow<Resource<List<UserCart>>>(Resource.Idle)
    val userCart: StateFlow<Resource<List<UserCart>>> = _userCart

    // StateFlow for tracking and emitting the total price of items in the cart
    private val _totalPrice: MutableStateFlow<Double> = MutableStateFlow(0.0)
    val totalPrice: StateFlow<Double> = _totalPrice

    // StateFlow for tracking and emitting the badge count (e.g., number of items) in the cart
    private val _badgeCountState = MutableStateFlow(value = 0)
    val badgeCount: StateFlow<Int> = _badgeCountState.asStateFlow()

    init {
        // Initial data loading
        getCartsByUserId()
        getBadgeCount()
    }

    // Fetches cart items for the current user from the local repository
    fun getCartsByUserId() = viewModelScope.launch {
        val userId = getUserIdFromSharedPref(sharedPreferences)
        val result = localRepository.getUserCartByUserIdFromDb(userId)
        _userCart.value = result
    }

    // Updates the total price of items in the cart
    fun updateTotalPrice(cartList: List<UserCart>) = viewModelScope.launch {
        _totalPrice.value = calculateTotalPrice(cartList)
    }

    // Updates the badge count in the cart
    fun updateBadgeCount(newCount: Int) = viewModelScope.launch {
        _badgeCountState.value = newCount
    }

    // Deletes a cart item for the current user.
    fun deleteUserCartItem(userCart: UserCart) = viewModelScope.launch {
        localRepository.deleteUserCartFromDb(userCart)
        getCartsByUserId() // Refresh the cart items
    }

    // Updates a cart item for the current user
    fun updateUserCartItem(userCart: UserCart) = viewModelScope.launch {
        localRepository.updateUserCartFromDb(userCart)
        getCartsByUserId() // Refresh the cart items
    }

    // Retrieves the current badge count from the local repository
    private fun getBadgeCount() = viewModelScope.launch {
        val userId = getUserIdFromSharedPref(sharedPreferences)
        val result = localRepository.getBadgeCountFromDb(userId)
        _badgeCountState.value = result
    }


    // Calculates the total price of items in the cart
    private fun calculateTotalPrice(cartList: List<UserCart>): Double {
        var totalPrice = 0.0
        for (cart in cartList) {
            totalPrice += cart.price * cart.quantity
        }
        return totalPrice
    }
}