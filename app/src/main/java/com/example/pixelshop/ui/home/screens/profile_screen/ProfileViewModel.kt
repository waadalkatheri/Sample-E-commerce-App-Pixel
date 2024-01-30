package com.example.pixelshop.ui.home.screens.profile_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pixelshop.data.auth.repository.FirebaseAuthRepository
import com.example.pixelshop.model.User
import com.example.pixelshop.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: FirebaseAuthRepository // Injected repository for Firebase operations.
) : ViewModel() {

    // StateFlow to hold and emit the current user information state.
    private val _userInfo = MutableStateFlow<Resource<User>?>(value = Resource.Idle)
    val userInfo: StateFlow<Resource<User>?> = _userInfo

    init {
        // Fetch user information from Firebase on initialization.
        getUserInfoFromFirebase()
    }

    // Retrieves user information from Firebase and updates the _userInfo StateFlow.
    private fun getUserInfoFromFirebase() = viewModelScope.launch {
        _userInfo.value = Resource.Loading
        val result = repository.retrieveData() // Fetch data
        _userInfo.value = result // Set result (success/failure)
    }

    // Logs out the current user and resets the user information state.
    fun logout() {
        repository.logout() // Perform logout through repository
        _userInfo.value = null // Reset user info state
    }

    // Updates user information in Firebase.
    fun updateUserInfoFirebase(user: User) = viewModelScope.launch {
        repository.updateData(user = user) // Update user data
    }
}
