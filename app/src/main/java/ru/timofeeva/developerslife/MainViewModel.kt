package ru.timofeeva.developerslife

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.timofeeva.developerslife.models.Tab

class MainViewModel : ViewModel() {
    private val _state: MutableLiveData<ViewState> = MutableLiveData(ViewState())

    fun getState(): LiveData<ViewState> {
        return _state
    }

    init {
        loadNextPost()
    }

    private fun loadNextPost() {
        _state.value = ViewState(currentPostText = "Text")
    }

    fun onNextPostClick() {

    }

    fun onPreviousPostClick() {

    }
}

data class ViewState(
    val selectedTab: Tab = Tab.Recent,
    val currentPostText: String = "",
    val currentPostUrl: String = "",
    val isLoading: Boolean = false,
    val isPreviousButtonIsActive: Boolean = false
)