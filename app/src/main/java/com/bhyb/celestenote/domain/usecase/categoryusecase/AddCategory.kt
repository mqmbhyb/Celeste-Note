package com.bhyb.celestenote.domain.usecase.categoryusecase

import com.bhyb.celestenote.domain.model.Category
import com.bhyb.celestenote.domain.repository.CategoryRepository

class AddCategory(
    private val repository: CategoryRepository
) {
    suspend operator fun invoke(category: Category) {
        repository.insert(category)
    }
}
