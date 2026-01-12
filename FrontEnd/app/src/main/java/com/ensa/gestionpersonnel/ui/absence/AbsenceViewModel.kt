package com.ensa.gestionpersonnel.ui.absence

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ensa.gestionpersonnel.data.repository.AbsenceRepository
import com.ensa.gestionpersonnel.domain.model.Absence
import com.ensa.gestionpersonnel.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AbsenceViewModel @Inject constructor(
    private val absenceRepository: AbsenceRepository
) : ViewModel() {

    private val _absences = MutableLiveData<NetworkResult<List<Absence>>>()
    val absences: LiveData<NetworkResult<List<Absence>>> = _absences

    private val _absenceOperation = MutableLiveData<NetworkResult<Absence?>>()
    val absenceOperation: LiveData<NetworkResult<Absence?>> = _absenceOperation

    private val _filteredAbsences = MutableLiveData<List<Absence>>(emptyList())
    val filteredAbsences: LiveData<List<Absence>> = _filteredAbsences

    private val _currentFilterType = MutableLiveData<String?>()
    val currentFilterType: LiveData<String?> = _currentFilterType

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _currentSearchQuery = MutableLiveData<String>("")
    private val _currentValidatedFilter = MutableLiveData<Boolean?>(null)

    init {
        loadAllAbsences()
    }

    fun loadAllAbsences() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                absenceRepository.getAllAbsences().collect { result ->
                    _absences.value = result
                    if (result is NetworkResult.Success) {
                        applyFilters()
                    }
                    if (result !is NetworkResult.Loading) {
                        _isLoading.value = false
                    }
                }
            } catch (e: Exception) {
                _absences.value = NetworkResult.Error("Erreur: ${e.message}")
                _filteredAbsences.value = emptyList()
                _isLoading.value = false
            }
        }
    }

    fun loadAbsencesByPersonnel(personnelId: Long) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                absenceRepository.getAbsencesByPersonnel(personnelId).collect { result ->
                    _absences.value = result
                    if (result is NetworkResult.Success) {
                        applyFilters()
                    }
                    if (result !is NetworkResult.Loading) {
                        _isLoading.value = false
                    }
                }
            } catch (e: Exception) {
                _absences.value = NetworkResult.Error("Erreur: ${e.message}")
                _filteredAbsences.value = emptyList()
                _isLoading.value = false
            }
        }
    }

    fun createAbsence(absence: Absence) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                absenceRepository.createAbsence(absence).collect { result ->
                    _absenceOperation.value = result as? NetworkResult<Absence?>
                    _isLoading.value = false
                    if (result is NetworkResult.Success<*> && result.data != null) {
                        val data = result.data as? Absence ?: return@collect
                        // Recharger la liste après création
                        val currentAbsences = _absences.value
                        if (currentAbsences is NetworkResult.Success<*>) {
                            val newList = (currentAbsences.data as? List<Absence>)?.toMutableList() ?: mutableListOf()
                            newList.add(data)
                            _absences.value = NetworkResult.Success(newList)
                            applyFilters()
                        } else {
                            loadAllAbsences()
                        }
                    }
                }
            } catch (e: Exception) {
                _absenceOperation.value = NetworkResult.Error("Erreur: ${e.message}")
                _isLoading.value = false
            }
        }
    }

    fun updateAbsence(absence: Absence) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                absenceRepository.updateAbsence(absence).collect { result ->
                    _absenceOperation.value = result as? NetworkResult<Absence?>
                    _isLoading.value = false
                    if (result is NetworkResult.Success<*> && result.data != null) {
                        val data = result.data as? Absence ?: return@collect
                        // Mettre à jour l'absence dans la liste
                        val currentAbsences = _absences.value
                        if (currentAbsences is NetworkResult.Success<*>) {
                            val updatedList = (currentAbsences.data as? List<Absence>)?.map { existingAbsence ->
                                if (existingAbsence.id == absence.id) data else existingAbsence
                            } ?: emptyList()
                            _absences.value = NetworkResult.Success(updatedList)
                            applyFilters()
                        }
                    }
                }
            } catch (e: Exception) {
                _absenceOperation.value = NetworkResult.Error("Erreur: ${e.message}")
                _isLoading.value = false
            }
        }
    }

    fun validateAbsence(absenceId: Long) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                absenceRepository.validateAbsence(absenceId).collect { result ->
                    _absenceOperation.value = result as? NetworkResult<Absence?>
                    _isLoading.value = false
                    if (result is NetworkResult.Success<*> && result.data != null) {
                        val data = result.data as? Absence ?: return@collect
                        // Mettre à jour l'absence dans la liste
                        val currentAbsences = _absences.value
                        if (currentAbsences is NetworkResult.Success<*>) {
                            val updatedList = (currentAbsences.data as? List<Absence>)?.map { existingAbsence ->
                                if (existingAbsence.id == absenceId) data else existingAbsence
                            } ?: emptyList()
                            _absences.value = NetworkResult.Success(updatedList)
                            applyFilters()
                        }
                    }
                }
            } catch (e: Exception) {
                _absenceOperation.value = NetworkResult.Error("Erreur: ${e.message}")
                _isLoading.value = false
            }
        }
    }

    fun deleteAbsence(absenceId: Long) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                absenceRepository.deleteAbsence(absenceId).collect { result ->
                    _isLoading.value = false
                    _absenceOperation.value = when (result) {
                        is NetworkResult.Success<*> -> NetworkResult.Success(null)
                        is NetworkResult.Error<*> -> NetworkResult.Error(result.message ?: "Erreur")
                        is NetworkResult.Loading<*> -> NetworkResult.Loading()
                    }

                    if (result is NetworkResult.Success<*>) {
                        // Supprimer l'absence de la liste
                        val currentAbsences = _absences.value
                        if (currentAbsences is NetworkResult.Success<*>) {
                            val filteredList = (currentAbsences.data as? List<Absence>)?.filter { it.id != absenceId } ?: emptyList()
                            _absences.value = NetworkResult.Success(filteredList)
                            applyFilters()
                        }
                    }
                }
            } catch (e: Exception) {
                _absenceOperation.value = NetworkResult.Error("Erreur: ${e.message}")
                _isLoading.value = false
            }
        }
    }

    fun filterAbsencesByType(type: String?) {
        _currentFilterType.value = type
        applyFilters()
    }

    fun filterAbsencesByStatus(validated: Boolean?) {
        _currentValidatedFilter.value = validated
        applyFilters()
    }

    fun searchAbsences(query: String) {
        _currentSearchQuery.value = query
        applyFilters()
    }

    fun clearFilters() {
        _currentFilterType.value = null
        _currentSearchQuery.value = ""
        _currentValidatedFilter.value = null
        applyFilters()
    }

    private fun applyFilters() {
        val currentAbsences = _absences.value
        if (currentAbsences is NetworkResult.Success<*>) {
            val data = (currentAbsences.data as? List<Absence>) ?: emptyList()
            var filtered = data

            val type = _currentFilterType.value
            val validated = _currentValidatedFilter.value
            val searchQuery = _currentSearchQuery.value

            // Appliquer le filtre par type
            if (!type.isNullOrEmpty()) {
                filtered = filtered.filter { it.type.name == type }
            }

            // Appliquer le filtre par statut de validation
            if (validated != null) {
                filtered = filtered.filter { it.estValideeParAdmin == validated }
            }

            // Appliquer la recherche
            if (!searchQuery.isNullOrBlank()) {
                filtered = filtered.filter { absence ->
                    val nom = absence.personnelNom ?: ""
                    val prenom = absence.personnelPrenom ?: ""
                    val ppr = absence.personnelPpr ?: ""
                    val motif = absence.motif ?: ""
                    
                    nom.contains(searchQuery, ignoreCase = true) ||
                    prenom.contains(searchQuery, ignoreCase = true) ||
                    ppr.contains(searchQuery, ignoreCase = true) ||
                    motif.contains(searchQuery, ignoreCase = true)
                }
            }

            _filteredAbsences.value = filtered
        } else {
            _filteredAbsences.value = emptyList()
        }
    }
}