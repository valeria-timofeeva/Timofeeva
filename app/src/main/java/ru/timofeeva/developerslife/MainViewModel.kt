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
}

data class ViewState(
    val selectedTab: Tab = Tab.Recent,
    val currentPost: String = "",
    val isLoading: Boolean = false
)