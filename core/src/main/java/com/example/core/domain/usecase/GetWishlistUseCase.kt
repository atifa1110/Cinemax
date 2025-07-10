package com.example.core.domain.usecase

import com.example.core.data.network.response.CinemaxResponse
import com.example.core.domain.model.WishlistModel
import com.example.core.domain.repository.wishlist.WishListRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetWishlistUseCase @Inject constructor(
    private val wishlistRepository: WishListRepository,
) {
    operator fun invoke() : Flow<CinemaxResponse<List<WishlistModel>>> {
        return wishlistRepository.getWishlist()
    }
}