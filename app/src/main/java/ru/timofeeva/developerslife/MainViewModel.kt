package ru.timofeeva.developerslife

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.timofeeva.developerslife.models.Post
import ru.timofeeva.developerslife.models.Tab
import ru.timofeeva.developerslife.network.RetrofitProvider

class MainViewModel : ViewModel() {
    private val _state: MutableLiveData<ViewState> = MutableLiveData(ViewState())

    fun getState(): LiveData<ViewState> {
        return _state
    }

    init {
        loadNextPost()
    }

    private fun loadNextPost() {
        _state.value = _state.value?.copy(isLoading = true)
        RetrofitProvider.developersLifeApi
            .getNextPost()
            .enqueue(object : Callback<Post> {
                override fun onResponse(call: Call<Post>, response: Response<Post>) {
                    if (response.isSuccessful) {
                        _state.value =
                            _state.value?.copy(isLoading = false, currentPost = response.body())
                    }

                }

                override fun onFailure(call: Call<Post>, t: Throwable) {
                    TODO("Not yet implemented")
                }
            })
    }

    fun onNextPostClick() {

    }

    fun onPreviousPostClick() {

    }
}

data class ViewState(
    val selectedTab: Tab = Tab.Recent,
    val currentPost: Post? = null,
    val isLoading: Boolean = false,
    val isPreviousButtonIsActive: Boolean = false
)