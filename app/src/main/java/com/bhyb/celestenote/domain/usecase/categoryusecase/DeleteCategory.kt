package com.bhyb.celestenote.domain.usecase.categoryusecase

import com.bhyb.celestenote.domain.repository.CategoryRepository

class DeleteCategory(
    private val repository: CategoryRepository
) {
    suspend operator fun invoke(categoryId: Int) {
        repository.deleteCategoryById(categoryId)
    }
}