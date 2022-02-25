package io.project.gitcasestudy.viewmodel

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import io.project.gitcasestudy.model.pojo.GitObject
import io.project.gitcasestudy.repository.GitRepository
import io.project.gitcasestudy.utils.DataState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GitViewModel
@Inject
constructor(
    private val gitRepository: GitRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _dataState: MutableLiveData<DataState<List<GitObject>>> = MutableLiveData()

    val dataState: LiveData<DataState<List<GitObject>>>
        get() = _dataState

    fun setStateEvent(mainStateEvent: GitListStateEvent) {
        viewModelScope.launch {
            when (mainStateEvent) {
                is GitListStateEvent.GetRepos -> {
                    gitRepository.getRepos()
                        .onEach { dataState ->
                            _dataState.value = dataState
                        }
                        .launchIn(viewModelScope)
                }
                else -> {

                }
            }
        }
    }
}

sealed class GitListStateEvent {

    object GetRepos : GitListStateEvent()

    object None : GitListStateEvent()

}