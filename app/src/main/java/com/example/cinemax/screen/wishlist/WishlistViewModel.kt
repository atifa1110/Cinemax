package com.example.cinemax.screen.wishlist

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinemax.R
import com.example.cinemax.screen.wishlist.WishlistEvent.ShowSnackbar
import com.example.core.data.network.response.CinemaxResponse
import com.example.core.domain.model.WishlistModel
import com.example.core.domain.usecase.DeleteMovieFromWishlistUseCase
import com.example.core.domain.usecase.DeleteTvShowFromWishlistUseCase
import com.example.core.domain.usecase.GetWishlistUseCase
import com.example.core.ui.mapper.asWishlist
import com.example.core.ui.model.WishList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class WishlistUiState(
    val wishlist: List<WishList> = emptyList(),
    val isLoading: Boolean = false,
)

sealed class WishlistEvent {
    data class ShowSnackbar(@StringRes val message: Int) : WishlistEvent()
}

@HiltViewModel
class WishlistViewModel @Inject constructor(
    private val getWishlistUseCase: GetWishlistUseCase,
    private val deleteMovieFromWishlistUseCase: DeleteMovieFromWishlistUseCase,
    private val deleteTvShowFromWishlistUseCase: DeleteTvShowFromWishlistUseCase
) :ViewModel(){

    private val _uiState = MutableStateFlow(WishlistUiState())
    val uiState = _uiState.asStateFlow()

    private val _eventFlow = MutableSharedFlow<WishlistEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        loadWishlist()
    }

    private fun loadWishlist() = viewModelScope.launch {
        getWishlistUseCase.invoke().collect{ response ->
            when(response){
                is CinemaxResponse.Loading -> {
                    _uiState.update { it.copy(isLoading = true) }
                }
                is CinemaxResponse.Success -> {
                    val result = response.value.map(WishlistModel::asWishlist)
                    _uiState.update { it.copy(isLoading = false, wishlist = result) }
                }
                is CinemaxResponse.Failure -> {
                    _uiState.update { it.copy(isLoading = false) }
                }
            }
        }
    }

    fun deleteMovieFromWishlist(id: Int) = viewModelScope.launch {
        deleteMovieFromWishlistUseCase.invoke(id)
        _uiState.update { currentState ->
            currentState.copy(
                wishlist = currentState.wishlist.filterNot { it.id == id },
            )
        }
        _eventFlow.emit(ShowSnackbar(R.string.success_delete_from_wishlist))
    }

    fun deleteTvShowFromWishlist(id: Int) = viewModelScope.launch {
        deleteTvShowFromWishlistUseCase.invoke(id)
        _uiState.update { currentState ->
            currentState.copy(
                wishlist = currentState.wishlist.filterNot { it.id == id },
            )
        }
        _eventFlow.emit(ShowSnackbar(R.string.success_delete_from_wishlist))
    }


}