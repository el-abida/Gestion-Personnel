package com.ensa.gestionpersonnel.ui.diplome.list

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
class DiplomeListViewModel @Inject constructor(
    private val diplomeRepository: DiplomeRepository
) : ViewModel() {

    private val _diplomes = MutableLiveData<List<Diplome>>()
    val diplomes: LiveData<List<Diplome>> = _diplomes

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    private var allDiplomesCache: List<Diplome> = emptyList()

    fun loadDiplomes() {
        viewModelScope.launch {
            diplomeRepository.getAllDiplomes().collect { result ->
                when (result) {
                    is NetworkResult.Loading<*> -> _isLoading.value = true
                    is NetworkResult.Success<*> -> {
                        _isLoading.value = false
                        val list = (result.data as? List<Diplome>) ?: emptyList()
                        allDiplomesCache = list
                        _diplomes.value = list.sortedByDescending { it.dateObtention }
                    }
                    is NetworkResult.Error<*> -> {
                        _isLoading.value = false
                        _error.value = result.message
                    }
                }
            }
        }
    }

    fun deleteDiplome(id: Long) {
        viewModelScope.launch {
            diplomeRepository.deleteDiplome(id).collect { result ->
                if (result is NetworkResult.Success<*>) {
                    loadDiplomes()
                } else if (result is NetworkResult.Error<*>) {
                    _error.value = result.message
                }
            }
        }
    }

    fun searchDiplomes(query: String) {
        if (query.isBlank()) {
            _diplomes.value = allDiplomesCache.sortedByDescending { it.dateObtention }
            return
        }

        val filtered = allDiplomesCache.filter {
            it.intitule.contains(query, ignoreCase = true) ||
                    it.specialite.contains(query, ignoreCase = true) ||
                    it.etablissement.contains(query, ignoreCase = true) ||
                    (it.personnelNom?.contains(query, ignoreCase = true) == true) ||
                    (it.personnelPrenom?.contains(query, ignoreCase = true) == true)
        }

        _diplomes.value = filtered.sortedByDescending { it.dateObtention }
    }
}