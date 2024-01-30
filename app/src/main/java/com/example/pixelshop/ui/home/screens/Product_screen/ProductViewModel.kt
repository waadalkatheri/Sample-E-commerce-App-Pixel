package com.example.pixelshop.ui.home.screens.Product_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pixelshop.data.network.dto.Product
import com.example.pixelshop.data.network.repository.ProductNetworkRepository
import com.example.pixelshop.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val productNetworkRepository: ProductNetworkRepository
) : ViewModel() {

    private val _products = MutableStateFlow<Resource<List<Product>>>(Resource.Idle)
    val products: StateFlow<Resource<List<Product>>> = _products

    private val _categories = MutableStateFlow<Resource<List<String>>>(Resource.Idle)
    val categories: StateFlow<Resource<List<String>>> = _categories

    var state by mutableStateOf(DashboardScreenState())


    // A background job.
    // Conceptually, a job is a cancellable thing with a life-cycle that culminates in its completion
    private var searchJob: Job? = null

    fun onEvent(event: ProductScreenEvent) {
        when(event) {
            is ProductScreenEvent.OnCategoryChange -> {
                state = state.copy(category = event.category)
                getProductsByCategory(categoryName = state.category)
            }

            is ProductScreenEvent.OnProductClicked -> {
                state = state.copy(selectedProduct = event.product)
            }

            is ProductScreenEvent.OnSearchIconClicked -> {
                state = state.copy(
                    isSearchBarVisible = true,
                    productsList = emptyList()
                )
            }

            is ProductScreenEvent.OnCloseIconClicked -> {
                state = state.copy(isSearchBarVisible = false)
                getProductsByCategory(categoryName = state.category)
            }

            is ProductScreenEvent.OnSearchQueryChanged -> {
                // update the state of searchQuery
                state = state.copy(searchQuery = event.searchQuery)
                // searchJob will be null because it is first time but when change the search query before
                // the completion of the one second delay so it will cancel the previous job
                // and the new job will start the delay of one second
                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    delay(1000)
                    searchProduct(query = state.searchQuery)
                }
            }
        }
    }

    init {
        getAllCategories()
        getAllProducts()
    }

    private fun getAllProducts() = viewModelScope.launch {
        _products.value = Resource.Loading
        val result = productNetworkRepository.getProductsListFromApi()
        _products.value = result
    }

    private fun searchProduct(query: String) = viewModelScope.launch {
//        _products.value = Resource.Loading
        val result = productNetworkRepository.getProductsListBySearchFromApi(query = query)
        _products.value = result
    }

    private fun getAllCategories() = viewModelScope.launch {
//        _categories.value = Resource.Loading
        val result = productNetworkRepository.getAllCategoriesListFromApi()
        _categories.value = result
    }

    private fun getProductsByCategory(categoryName: String) = viewModelScope.launch {
//        _products.value = Resource.Loading
        val result =
            productNetworkRepository.getProductsListByCategoryNameFromApi(categoryName = categoryName)
        _products.value = result
    }

}