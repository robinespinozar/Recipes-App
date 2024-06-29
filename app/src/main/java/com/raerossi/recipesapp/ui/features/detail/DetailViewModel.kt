package com.raerossi.recipesapp.ui.features.detail

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.palette.graphics.Palette
import com.raerossi.recipesapp.domain.model.Recipe
import com.raerossi.recipesapp.domain.usecases.GetRecipeByIdUseCase
import com.raerossi.recipesapp.domain.usecases.GetRecipesUseCase
import com.raerossi.recipesapp.ui.features.home.HomeUiState
import dagger.Module
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

//@HiltViewModel(assistedFactory = DetailViewModel.DetailViewModelFactory::class)
class DetailViewModel @AssistedInject constructor(
    @Assisted private val id: Int,
    private val getRecipeByIdUseCase: GetRecipeByIdUseCase
) : ViewModel() {

    @AssistedFactory
    interface Factory {
        fun create(id: Int): DetailViewModel
    }

    private val _recipe = MutableLiveData<Recipe?>()
    val recipe: LiveData<Recipe?> = _recipe

    private val _uiState = MutableStateFlow(DetailUiState())
    val uiState: StateFlow<DetailUiState> = _uiState

    init {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                _recipe.value = getRecipeByIdUseCase(id)
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        showErrorDialog = true,
                        messageError = e.message.toString()
                    )
                }
            } finally {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    fun getBackgroundColor(drawabale: Drawable, onFinish: (Color) -> Unit) {
        val bitmap = (drawabale as BitmapDrawable).bitmap.copy(Bitmap.Config.ARGB_8888, true)

        Palette.from(bitmap).generate { palette ->
            palette?.dominantSwatch?.rgb?.let { colorValue ->
                onFinish(Color(colorValue))
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    companion object {
        fun provideFactory(
            assistedFactory: Factory,
            id: Int
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(id) as T
            }
        }
    }

}

@Module
@InstallIn(ActivityRetainedComponent::class)
interface AssistedInjectModule