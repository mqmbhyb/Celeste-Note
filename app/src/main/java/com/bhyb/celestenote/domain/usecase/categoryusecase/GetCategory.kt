package com.bhyb.celestenote.domain.usecase.categoryusecase

import com.bhyb.celestenote.domain.model.Category
import com.bhyb.celestenote.domain.repository.CategoryRepository

class GetCategory(
    private val repository: CategoryRepository
) {
    suspend operator fun invoke(id: Int): Category? {
        return repository.getCategoryById(id)
    }
}