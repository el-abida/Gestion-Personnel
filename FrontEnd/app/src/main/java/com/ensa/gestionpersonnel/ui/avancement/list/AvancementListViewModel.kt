package com.ensa.gestionpersonnel.ui.avancement.list

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
class AvancementListViewModel @Inject constructor(
    private val avancementRepository: AvancementRepository
) : ViewModel() {

    private val _avancements = MutableLiveData<List<Avancement>>()
    val avancements: LiveData<List<Avancement>> = _avancements

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    private var allAvancementsCache = listOf<Avancement>()

    fun loadAvancements() {
        viewModelScope.launch {
            avancementRepository.getAllAvancements().collect { result ->
                when (result) {
                    is NetworkResult.Loading -> {
                        _isLoading.value = true
                        _error.value = null
                    }
                    is NetworkResult.Success<*> -> {
                        _isLoading.value = false
                        allAvancementsCache = (result.data as? List<Avancement>) ?: emptyList()
                        _avancements.value = allAvancementsCache.sortedByDescending { it.dateEffet }
                    }
                    is NetworkResult.Error -> {
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
                if (result is NetworkResult.Success<*>) {
                    loadAvancements()
                } else if (result is NetworkResult.Error<*>) {
                    _error.value = result.message
                }
            }
        }
    }

    fun searchAvancements(query: String) {
        if (query.isBlank()) {
            _avancements.value = allAvancementsCache.sortedByDescending { it.dateEffet }
            return
        }

        val filtered = allAvancementsCache.filter {
            it.gradePrecedent.contains(query, ignoreCase = true) ||
                    it.gradeNouveau.contains(query, ignoreCase = true) ||
                    it.description.contains(query, ignoreCase = true) ||
                    (it.personnelNom?.contains(query, ignoreCase = true) ?: false) ||
                    (it.personnelPrenom?.contains(query, ignoreCase = true) ?: false)
        }

        _avancements.value = filtered.sortedByDescending { it.dateEffet }
    }
}