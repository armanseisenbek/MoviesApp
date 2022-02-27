package com.onepercent.moviesapp.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.onepercent.moviesapp.model.MovieEntity

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel
@Inject
constructor(
    private val repository: DetailRepository
) : ViewModel() {

    val movie = MutableLiveData<MovieEntity>()

    fun getMovie(id: String) {
        viewModelScope.launch {
            movie.value = repository.getMovie(id)
        }

    }
}