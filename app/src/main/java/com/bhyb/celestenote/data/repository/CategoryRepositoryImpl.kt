package com.bhyb.celestenote.data.repository

import com.bhyb.celestenote.data.dao.CategoryDao
import com.bhyb.celestenote.data.util.toConvert
import com.bhyb.celestenote.domain.model.Category
import com.bhyb.celestenote.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CategoryRepositoryImpl(
    private val categoryDao: CategoryDao
): CategoryRepository {
    override suspend fun insert(category: Category) {
        categoryDao.insert(category.toConvert())
    }

    override suspend fun update(category: Category) {
        categoryDao.update(category.toConvert())
    }

    override fun getAllCustomizedCategories(): Flow<List<Category>> {
        return categoryDao.getAllCustomizedCategories()
            .map { it.map { result -> result.toConvert() } }
    }

    override suspend fun getCategoryById(id: Int): Category? {
        return categoryDao.getCategoryById(id)?.toConvert()
    }

    override suspend fun deleteCategoryById(id: Int) {
        categoryDao.deleteCategoryById(id)
    }

}