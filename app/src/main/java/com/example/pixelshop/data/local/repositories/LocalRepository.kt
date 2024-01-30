package com.example.pixelshop.data.local.repositories

import com.example.pixelshop.data.local.models.FavoriteProduct
import com.example.pixelshop.data.local.models.UserCart
import com.example.pixelshop.util.Resource

interface LocalRepository {

    suspend fun getUserCartByUserIdFromDb(userId: String): Resource<List<UserCart>>

    suspend fun insertUserCartToDb(userCart: UserCart)

    suspend fun deleteUserCartFromDb(userCart: UserCart)

    suspend fun updateUserCartFromDb(userCart: UserCart)

    suspend fun getFavoriteProductsFromDb(userId: String): Resource<List<FavoriteProduct>>

    suspend fun insertFavoriteItemToDb(favoriteProduct: FavoriteProduct)

    suspend fun deleteFavoriteItemFromDb(favoriteProduct: FavoriteProduct)

    suspend fun getBadgeCountFromDb(userId: String): Int
}