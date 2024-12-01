package com.bhyb.celestenote.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.bhyb.celestenote.data.entity.CategoryEntity

@Dao
interface CategoryDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(category: CategoryEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(categories: List<CategoryEntity>)

    @Update
    suspend fun update(category: CategoryEntity)

    @Query("SELECT * FROM category WHERE is_deletable = 0")
    suspend fun getAllDefaultCategories(): List<CategoryEntity>

    @Query("SELECT * FROM category WHERE is_deletable = 1")
    suspend fun getAllCustomizedCategories(): List<CategoryEntity>

    @Query("SELECT * FROM category WHERE id = :id")
    suspend fun getCategoryById(id: Int): CategoryEntity?

    @Query("DELETE FROM category WHERE id = :id AND is_deletable = 1")
    suspend fun deleteCategoryById(id: Int)

    @Query("SELECT id FROM category WHERE categoryTitle = :s")
    suspend fun getCategoryIdByTitle(s: String): Int
}