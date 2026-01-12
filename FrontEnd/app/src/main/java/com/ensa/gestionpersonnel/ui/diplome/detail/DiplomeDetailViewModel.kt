package com.ensa.gestionpersonnel.ui.diplome.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ensa.gestionpersonnel.data.repository.DiplomeRepository
import com.ensa.gestionpersonnel.domain.model.Diplome
import com.ensa.gestionpersonnel.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiplomeDetailViewModel @Inject constructor(
    private val diplomeRepository: DiplomeRepository
) : ViewModel() {

    private val _diplome = MutableLiveData<Diplome?>()
    val diplome: LiveData<Diplome?> = _diplome

    private val _deleteState = MutableLiveData<NetworkResult<Unit>>()
    val deleteState: LiveData<NetworkResult<Unit>> = _deleteState

    fun loadDiplome(id: Long) {
        viewModelScope.launch {
            diplomeRepository.getDiplomeById(id).collect { result ->
                if (result is NetworkResult.Success<*>) {
                    _diplome.value = result.data as? Diplome
                }
            }
        }
    }

    fun deleteDiplome(id: Long) {
        viewModelScope.launch {
            diplomeRepository.deleteDiplome(id).collect { result ->
                _deleteState.value = result
            }
        }
    }
}
