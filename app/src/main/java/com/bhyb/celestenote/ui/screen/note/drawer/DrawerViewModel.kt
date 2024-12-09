package com.bhyb.celestenote.ui.screen.note.drawer

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bhyb.celestenote.domain.model.Category
import com.bhyb.celestenote.domain.usecase.CategoryUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DrawerViewModel @Inject constructor(
    private val categoryUseCases: CategoryUseCases
) : ViewModel() {

    private val _customizedCategories = MutableStateFlow<List<Category>>(emptyList())
    val customizedCategories: StateFlow<List<Category>> = _customizedCategories.asStateFlow()

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _category = MutableStateFlow<Category?>(null)
    val category: StateFlow<Category?> = _category.asStateFlow()

    data class UiState(
        var errorText: String? = null,
        val isCheckingDuplicate: Boolean = false
    )

    val categoryTitle = mutableStateOf("")

    init {
        onGetCustomizedCategories()
    }

    fun onGetCategory(categoryId: Int) {
        viewModelScope.launch {
            val category = categoryUseCases.getCategory.invoke(categoryId)

            withContext(Dispatchers.Main) {
                _category.value = category
            }
        }
    }

    fun initCategory() {
        viewModelScope.launch {
            _category.value = null
        }
    }

    private fun onGetCustomizedCategories() {
        viewModelScope.launch {
            categoryUseCases.getCustomizedCategories.invoke().collect { result ->
                _customizedCategories.value = result

            }
        }
    }

    private fun onAddCategory() {
        viewModelScope.launch {
            categoryUseCases.addCategory.invoke(
                Category(
                    id = 0,
                    title = categoryTitle.value,
                    isDeletable = true,
                )
            )
            onGetCustomizedCategories()
        }
    }

    fun onDeleteCategory(categoryId: Int) {
        viewModelScope.launch {
            categoryUseCases.deleteCategory.invoke(categoryId)
        }
        onGetCustomizedCategories()
    }

    private fun onUpdateCategory(category: Category) {
        viewModelScope.launch {
            categoryUseCases.updateCategory.invoke(category)
        }
        onGetCustomizedCategories()
    }

    private fun isCategoryTitleUnique(title: String, excludingId: Int? = null): Boolean {
        val categories = customizedCategories.value
        val filteredCategories = if (excludingId != null) {
            categories.filterNot { it.id == excludingId }
        } else {
            categories
        }
        return !filteredCategories.any { it.title.equals(title, ignoreCase = true) }
    }

    fun checkCategoryTitleAndSave(
        title: String,
        isEditMode: Boolean,
        categoryToEdit: Category? = null,
        onDismissRequest: () -> Unit
    ) {
        viewModelScope.launch {
            _uiState.update { it.copy(isCheckingDuplicate = true) }

            val newTitle = title.trim()
            if (newTitle.isNotBlank()) {
                if (!isCategoryTitleUnique(newTitle, categoryToEdit?.id)) {
                    _uiState.update { it.copy(errorText = "分类名称已存在") }
                } else {
                    if (isEditMode && categoryToEdit != null) {
                        onUpdateCategory(categoryToEdit.copy(title = newTitle))
                    } else {
                        onAddCategory()
                    }
                    onDismissRequest()
                }
            } else {
                _uiState.update { it.copy(errorText = "分类名称不能为空") }
            }
            _uiState.update { it.copy(isCheckingDuplicate = false) }
        }
    }
}