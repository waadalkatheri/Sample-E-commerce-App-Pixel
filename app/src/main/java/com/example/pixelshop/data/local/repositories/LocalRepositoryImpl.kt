package com.example.pixelshop.data.local.repositories

import com.example.pixelshop.data.local.AppDao
import com.example.pixelshop.data.local.models.FavoriteProduct
import com.example.pixelshop.data.local.models.UserCart
import com.example.pixelshop.util.Resource
import javax.inject.Inject

class LocalRepositoryImpl @Inject constructor(
    private val appDao: AppDao
) : LocalRepository {

    // Retrieves a list of UserCart items for a specific user from the database.
    override suspend fun getUserCartByUserIdFromDb(userId: String): Resource<List<UserCart>> {
        return try {
            // Attempt to get the user's cart items from the database.
            val result = appDao.getCartByUserId(userId = userId)
            Resource.Success(result)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    // Inserts a UserCart item into the database.
    override suspend fun insertUserCartToDb(userCart: UserCart) {
        appDao.insertUserCart(userCart = userCart) // Perform the insert operation.
    }

    // Deletes a UserCart item from the database.
    override suspend fun deleteUserCartFromDb(userCart: UserCart) {
        appDao.deleteUserCartItem(userCart = userCart) // Perform the delete operation.
    }

    // Updates a UserCart item in the database.
    override suspend fun updateUserCartFromDb(userCart: UserCart) {
        appDao.updateUserCartItem(userCart = userCart) // Perform the update operation.
    }

    // Retrieves a list of FavoriteProduct items for a specific user from the database.
    override suspend fun getFavoriteProductsFromDb(userId: String): Resource<List<FavoriteProduct>> {
        return try {
            // Attempt to get the user's favorite products from the database.
            val result = appDao.getFavoriteProducts(userId = userId)
            Resource.Success(result)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    // Inserts a FavoriteProduct item into the database.
    override suspend fun insertFavoriteItemToDb(favoriteProduct: FavoriteProduct) {
        appDao.insertFavoriteItem(favoriteProduct = favoriteProduct) // Perform the insert operation.
    }

    // Deletes a FavoriteProduct item from the database.
    override suspend fun deleteFavoriteItemFromDb(favoriteProduct: FavoriteProduct) {
        appDao.deleteFavoriteItem(favoriteProduct = favoriteProduct) // Perform the delete operation.
    }

    // Retrieves the badge count for a specific user's cart from the database.
    override suspend fun getBadgeCountFromDb(userId: String): Int {
        return appDao.getBadgeCount(userId = userId) // Return the badge count.
    }
}
