package com.ensa.gestionpersonnel.ui.avancement.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ensa.gestionpersonnel.data.repository.AvancementRepository
import com.ensa.gestionpersonnel.domain.model.Avancement
import com.ensa.gestionpersonnel.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AvancementDetailViewModel @Inject constructor(
    private val avancementRepository: AvancementRepository
) : ViewModel() {

    private val _avancement = MutableLiveData<Avancement>()
    val avancement: LiveData<Avancement> = _avancement

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    private val _deleteState = MutableLiveData<NetworkResult<Unit>>()
    val deleteState: LiveData<NetworkResult<Unit>> = _deleteState

    fun loadAvancement(id: Long) {
        viewModelScope.launch {
            avancementRepository.getAvancementById(id).collect { result ->
                when (result) {
                    is NetworkResult.Loading<*> -> {
                        _isLoading.value = true
                        _error.value = null
                    }
                    is NetworkResult.Success<*> -> {
                        _isLoading.value = false
                        (result.data as? Avancement)?.let { _avancement.value = it }
                    }
                    is NetworkResult.Error<*> -> {
                        _isLoading.value = false
                        _error.value = result.message
                    }
                }
            }
        }
    }

    fun deleteAvancement(id: Long) {
        viewModelScope.launch {
            avancementRepository.deleteAvancement(id).collect { result ->
                _deleteState.value = result
            }
        }
    }
}
