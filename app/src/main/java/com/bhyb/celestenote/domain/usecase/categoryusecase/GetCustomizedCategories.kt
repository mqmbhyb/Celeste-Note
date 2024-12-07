package com.bhyb.celestenote.domain.usecase.categoryusecase

import com.bhyb.celestenote.domain.model.Category
import com.bhyb.celestenote.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow

class GetCustomizedCategories(
    private val repository: CategoryRepository
) {
    operator fun invoke(): Flow<List<Category>> {
        return repository.getAllCustomizedCategories()
    }
}