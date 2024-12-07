package com.bhyb.celestenote.domain.repository

import com.bhyb.celestenote.domain.model.Category
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {

    suspend fun insert(category: Category)

    suspend fun update(category: Category)

    fun getAllCustomizedCategories(): Flow<List<Category>>

    suspend fun getCategoryById(id: Int): Category?

    suspend fun deleteCategoryById(id: Int)
}