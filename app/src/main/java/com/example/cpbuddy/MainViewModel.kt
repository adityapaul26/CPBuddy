package com.example.cpbuddy

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.awaitResponse

sealed class UiState<out T> {
    object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val message: String) : UiState<Nothing>()
}

class MainViewModel : ViewModel() {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://competeapi.vercel.app/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val competeApi = retrofit.create(CompeteApi::class.java)
    private val codeforcesApi = retrofit.create(CodeforcesApi::class.java)

    private val _contestsState = mutableStateOf<UiState<List<Contest>>>(UiState.Loading)
    val contestsState: State<UiState<List<Contest>>> = _contestsState

    private val _userProfileState = mutableStateOf<UiState<User>?>(null)
    val userProfileState: State<UiState<User>?> = _userProfileState

    init {
        fetchContests()
    }

    fun fetchContests() {
        viewModelScope.launch {
            _contestsState.value = UiState.Loading
            try {
                val response = competeApi.getUpcomingContests().awaitResponse()
                if (response.isSuccessful) {
                    _contestsState.value = UiState.Success(response.body() ?: emptyList())
                } else {
                    _contestsState.value = UiState.Error("Failed to fetch contests")
                }
            } catch (e: Exception) {
                _contestsState.value = UiState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun searchUser(handle: String) {
        if (handle.isBlank()) return
        
        viewModelScope.launch {
            _userProfileState.value = UiState.Loading
            try {
                val response = codeforcesApi.getUserInfo(handle).awaitResponse()
                if (response.isSuccessful && !response.body().isNullOrEmpty()) {
                    _userProfileState.value = UiState.Success(response.body()!![0])
                } else {
                    _userProfileState.value = UiState.Error("User not found")
                }
            } catch (e: Exception) {
                _userProfileState.value = UiState.Error(e.message ?: "Unknown error")
            }
        }
    }
}
