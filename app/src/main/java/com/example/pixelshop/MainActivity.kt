package com.example.pixelshop

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat.getSystemService
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.pixelshop.navigation.AppNavHost
import com.example.pixelshop.navigation.BottomNavigationBar
import com.example.pixelshop.navigation.ForgotPass
import com.example.pixelshop.navigation.Payment
import com.example.pixelshop.navigation.ProductDetail
import com.example.pixelshop.navigation.SignIn
import com.example.pixelshop.navigation.SignUp
import com.example.pixelshop.ui.home.screens.cart_screen.CartViewModel
import com.example.pixelshop.ui.theme.MyShopTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyShopTheme {
                val cartViewModel: CartViewModel = hiltViewModel()
                val badgeCount by cartViewModel.badgeCount.collectAsState()
                ShowScreen(badgeCount = badgeCount) { newBadgeCount ->
                    cartViewModel.updateBadgeCount(newBadgeCount)
                }
            }
        }
    }
}

@Composable
private fun ShowScreen(
    modifier: Modifier = Modifier,
    appState: EcommerceAppState = rememberEcommerceAppState(),
    badgeCount: Int,
    onBadgeCountChange: (Int) -> Unit
) {
    // A surface container using the 'background' color from the theme
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        if (!appState.isOnline) {
            OfflineDialog(onRetry = appState::refreshOnline)
        } else {
            val navHostController = rememberNavController()
            val bottomBarState = rememberSaveable { mutableStateOf(false) }
            val navBackStackEntry by navHostController.currentBackStackEntryAsState()
            when (navBackStackEntry?.destination?.route) {
                SignIn.route, SignUp.route, ForgotPass.route, ProductDetail.routeWithArgs, Payment.route -> bottomBarState.value =
                    false

                else -> bottomBarState.value = true
            }

            Scaffold(
                bottomBar = {
                    if (bottomBarState.value) {
                        BottomNavigationBar(
                            navController = navHostController,
                            bottomBarState = bottomBarState,
                            badgeState = badgeCount
                        )
                    }
                }
            ) { paddingValues ->
                AppNavHost(
                    navHostController = navHostController,
                    modifier = Modifier.padding(paddingValues),
                    onBadgeCountChange = onBadgeCountChange
                )

            }
        }
    }
}


@SuppressLint("RememberReturnType")
@Composable
fun rememberEcommerceAppState(
    navHostController: NavHostController = rememberNavController(),
    context: Context = LocalContext.current,
) = remember(navHostController, context) {
    EcommerceAppState(navHostController, context)
}

class EcommerceAppState(
    val navHostController: NavHostController,
    private val context: Context
) {
    var isOnline by mutableStateOf(checkIfOnline())

    fun refreshOnline() {
        isOnline = checkIfOnline()
    }


    private fun checkIfOnline(): Boolean {
        val cm = getSystemService(context, ConnectivityManager::class.java)
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val capabilities = cm?.getNetworkCapabilities(cm.activeNetwork) ?: return false
            capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
                    capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
        } else {
            cm?.activeNetworkInfo?.isConnectedOrConnecting == true
        }
    }
}




@Composable
fun OfflineDialog(onRetry: () -> Unit) {
    AlertDialog(
        onDismissRequest = { /*TODO*/ },
        title = { Text(text = "Pixel Shop") },
        text = { Text(text = "No Internet Connection") },
        confirmButton = {
            TextButton(onClick = onRetry) {
                Text(text = "Retry")
            }
        }
    )
}