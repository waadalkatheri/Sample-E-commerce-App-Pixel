package com.example.pixelshop

import android.content.SharedPreferences
import androidx.lifecycle.SavedStateHandle
import com.example.pixelshop.data.local.models.FavoriteProduct
import com.example.pixelshop.data.local.repositories.LocalRepository
import com.example.pixelshop.data.network.dto.Product
import com.example.pixelshop.data.network.repository.ProductNetworkRepository
import com.example.pixelshop.ui.home.screens.detail_screen.DetailViewModel
import com.example.pixelshop.util.Resource
import io.mockk.*
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
class DetailViewModelTest {

    // Lateinit vars for dependencies and ViewModel. They will be initialized in the setUp method.
    private lateinit var viewModel: DetailViewModel
    private val productNetworkRepository: ProductNetworkRepository = mockk()
    private val localRepository: LocalRepository = mockk()
    private val sharedPreferences: SharedPreferences = mockk()
    private val savedStateHandle: SavedStateHandle = mockk()
    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setUp() {
        // Setting up mocks for dependencies of DetailViewModel.
        // Mocking responses for network calls and local database operations.
        coEvery { productNetworkRepository.getSingleProductByIdFromApi(any()) } returns Resource.Success(mockProduct())
        coEvery { localRepository.insertUserCartToDb(any()) } just Runs
        coEvery { localRepository.insertFavoriteItemToDb(any()) } just Runs
        every { sharedPreferences.getString(any(), any()) } returns "mockedUserId"
        every { savedStateHandle.get<Int>("id") } returns 1

        // Setting the main dispatcher to a test dispatcher for coroutines.
        Dispatchers.setMain(testDispatcher)

        // Instantiating the ViewModel with mocked dependencies.
        viewModel = DetailViewModel(productNetworkRepository, localRepository, sharedPreferences, savedStateHandle)
    }

    @After
    fun tearDown() {
        // Resetting main dispatcher to its original state to avoid interference with other tests.
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `addToFavorite inserts product into favorites`() = runBlockingTest {
        // Arrange: Creating a mock favorite product.
        val mockFavorite = mockFavoriteProduct()

        // Act: Invoking the method to test.
        viewModel.addToFavorite(mockFavorite)

        // Assert: Verifying that the expected method on the localRepository was called.
        coVerify(exactly = 1) { localRepository.insertFavoriteItemToDb(any()) }
    }

    // Helper function to create a mock FavoriteProduct instance.
    fun mockFavoriteProduct() =
        FavoriteProduct(
            userId = "user123",
            productId = 101,
            price = 299,
            quantity = 2,
            title = "Mac Laptop",
            image = "https://example.com/mock-image.jpg"
        )

    // Helper function to create a mock Product instance.
    fun mockProduct() = Product(
        brand = "Samsunge",
        category = "Electronics",
        description = "Mock description of the product.",
        discountPercentage = 10.0,
        id = 123,
        images = listOf("https://example.com/image1.jpg", "https://example.com/image2.jpg"),
        price = 299,
        rating = 4.5,
        stock = 50,
        thumbnail = "https://example.com/thumbnail.jpg",
        title = "IPhone 14 PRO MAX"
    )
}
