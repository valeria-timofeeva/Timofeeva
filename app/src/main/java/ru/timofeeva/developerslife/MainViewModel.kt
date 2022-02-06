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
    private val postCache: MutableList<Post> = mutableListOf()
    private var currentPostPosition: Int = 1

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
                    val post = response.body()

                    if (response.isSuccessful && post != null) {

                        postCache.add(post)
                        currentPostPosition++

                        _state.value =
                            _state.value?.copy(
                                isLoading = false,
                                currentPost = post,
                                hasError = false,
                                isPreviousButtonIsActive = hasPreviousPost()
                            )
                    } else {
                        _state.value = _state.value?.copy(hasError = true, isLoading = false)
                    }
                }

                override fun onFailure(call: Call<Post>, t: Throwable) {
                    _state.value = _state.value?.copy(hasError = true, isLoading = false)
                }
            })
    }

    private fun hasPreviousPost() = postCache.size > 1 && currentPostPosition > 1

    fun onNextPostClick() {
        if (currentPostPosition < postCache.size) nextPostFromCache() else loadNextPost()
    }

    private fun nextPostFromCache() {
        currentPostPosition++
        _state.value = _state.value?.copy(
            currentPost = postCache[currentPostPosition - 1],
            isPreviousButtonIsActive = hasPreviousPost()
        )

    }

    fun onPreviousPostClick() {
        currentPostPosition--
        _state.value = _state.value?.copy(
            currentPost = postCache[currentPostPosition - 1],
            isPreviousButtonIsActive = hasPreviousPost()
        )
    }
}

data class ViewState(
    val selectedTab: Tab = Tab.Recent,
    val currentPost: Post? = null,
    val isLoading: Boolean = false,
    val hasError: Boolean = false,
    val isPreviousButtonIsActive: Boolean = false
)