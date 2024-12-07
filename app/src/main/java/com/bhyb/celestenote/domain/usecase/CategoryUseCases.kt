package com.bhyb.celestenote.domain.usecase

import com.bhyb.celestenote.domain.usecase.categoryusecase.AddCategory
import com.bhyb.celestenote.domain.usecase.categoryusecase.DeleteCategory
import com.bhyb.celestenote.domain.usecase.categoryusecase.GetCategory
import com.bhyb.celestenote.domain.usecase.categoryusecase.GetCustomizedCategories
import com.bhyb.celestenote.domain.usecase.categoryusecase.UpdateCategory

data class CategoryUseCases(
    val addCategory: AddCategory,
    val deleteCategory: DeleteCategory,
    val getCategory: GetCategory,
    val getCustomizedCategories: GetCustomizedCategories,
    val updateCategory: UpdateCategory
)
