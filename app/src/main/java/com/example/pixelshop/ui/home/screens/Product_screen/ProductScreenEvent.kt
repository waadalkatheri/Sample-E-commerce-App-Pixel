package com.example.pixelshop.ui.home.screens.Product_screen

import com.example.pixelshop.data.network.dto.Product

sealed class ProductScreenEvent {
    data class OnProductClicked(var product: Product) : ProductScreenEvent()
    data class OnCategoryChange(var category: String) : ProductScreenEvent()
    data class OnSearchQueryChanged(var searchQuery: String) : ProductScreenEvent()
    object OnSearchIconClicked: ProductScreenEvent()
    object OnCloseIconClicked: ProductScreenEvent()
}
