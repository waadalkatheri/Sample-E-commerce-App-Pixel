package com.example.pixelshop

import android.content.SharedPreferences
import com.example.pixelshop.data.local.models.UserCart
import com.example.pixelshop.data.local.repositories.LocalRepository
import com.example.pixelshop.ui.home.screens.cart_screen.CartViewModel
import com.example.pixelshop.util.Constants
import com.example.pixelshop.util.Resource
import io.mockk.*
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class CartViewModelTest {

    private lateinit var viewModel: CartViewModel
    private val localRepository: LocalRepository = mockk()
    private val sharedPreferences: SharedPreferences = mockk()
    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setUp() {
        coEvery { localRepository.getUserCartByUserIdFromDb("user123") } returns Resource.Success(mockUserCartList())
        coEvery { localRepository.getBadgeCountFromDb("user123") } returns 3
        coEvery { localRepository.getUserCartByUserIdFromDb("mockedUserId") } returns Resource.Success(mockUserCartList())
        coEvery { localRepository.getBadgeCountFromDb("mockedUserId") } returns 3
        // Adjust this line to match the actual key and default value used in getUserIdFromSharedPref
        every { sharedPreferences.getString(Constants.PREF_FIREBASE_USERID_KEY, Constants.PREF_DEF_STR) } returns "user123"

        Dispatchers.setMain(testDispatcher)




        viewModel = CartViewModel(localRepository, sharedPreferences)

    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // Reset the main dispatcher to the original Main dispatcher
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `getCartsByUserId should return success with correct cart size when user cart is fetched`() = runBlockingTest {
    // Act
        viewModel.getCartsByUserId()

        // Assert
        val cartState = viewModel.userCart.value
        assert(cartState is Resource.Success && cartState.data.size == mockUserCartList().size)
    }

    @Test
    fun `updateTotalPrice updates total price correctly`() = runBlockingTest {
        // Arrange
        val cartList = mockUserCartList()
        val expectedTotalPrice = cartList.sumOf { it.price.toDouble() * it.quantity }

        // Act
        viewModel.updateTotalPrice(cartList)

        // Assert
        assertEquals(expectedTotalPrice, viewModel.totalPrice.value, 0.01)

    }


    @Test
    fun `updateBadgeCount updates badge count correctly`() = runBlockingTest {
        // Arrange
        val newCount = 5

        // Act
        viewModel.updateBadgeCount(newCount)

        // Assert
        assertEquals(newCount, viewModel.badgeCount.value)
    }

    // Helper function to create mock user cart list
    private  fun mockUserCartList() = listOf(
        UserCart(
            userId = "user123",
            productId = 101,
            price = 150,
            quantity = 2,
            title = "Mock Product 1",
            image = "https://example.com/image1.jpg"
        ),
        UserCart(
            userId = "user123",
            productId = 102,
            price = 200,
            quantity = 1,
            title = "Mock Product 2",
            image = "https://example.com/image2.jpg"
        ),
    )

}
