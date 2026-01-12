package com.ensa.gestionpersonnel.ui.diplome.form

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ensa.gestionpersonnel.data.repository.DiplomeRepository
import com.ensa.gestionpersonnel.data.repository.PersonnelRepository
import com.ensa.gestionpersonnel.domain.model.Diplome
import com.ensa.gestionpersonnel.domain.model.NiveauDiplome
import com.ensa.gestionpersonnel.domain.model.Personnel
import com.ensa.gestionpersonnel.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class DiplomeFormViewModel @Inject constructor(
    private val diplomeRepository: DiplomeRepository,
    private val personnelRepository: PersonnelRepository
) : ViewModel() {

    private val _diplome = MutableLiveData<Diplome?>()
    val diplome: LiveData<Diplome?> = _diplome

    private val _personnelList = MutableLiveData<List<Personnel>>()
    val personnelList: LiveData<List<Personnel>> = _personnelList

    private val _saveSuccess = MutableLiveData<Boolean>()
    val saveSuccess: LiveData<Boolean> = _saveSuccess

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun loadPersonnelList() {
        viewModelScope.launch {
            val result = personnelRepository.getAllPersonnel()
            if (result is NetworkResult.Success<*>) {
                val data = result.data as? List<Personnel>
                _personnelList.value = data?.filter { it.estActif } ?: emptyList()
            } else if (result is NetworkResult.Error<*>) {
                _error.value = result.message
            }
        }
    }

    fun loadDiplome(id: Long) {
        viewModelScope.launch {
            diplomeRepository.getDiplomeById(id).collect { result ->
                if (result is NetworkResult.Success<*>) {
                    _diplome.value = result.data as? Diplome
                } else if (result is NetworkResult.Error<*>) {
                    _error.value = result.message
                }
            }
        }
    }

    fun saveDiplome(
        id: Long,
        personnelId: Long,
        intitule: String,
        specialite: String,
        niveau: NiveauDiplome,
        etablissement: String,
        dateObtention: Date?,
        fichierPreuve: String?
    ) {
        viewModelScope.launch {
            if (intitule.isBlank()) {
                _error.value = "L'intitulé est obligatoire"
                return@launch
            }

            if (specialite.isBlank()) {
                _error.value = "La spécialité est obligatoire"
                return@launch
            }

            if (etablissement.isBlank()) {
                _error.value = "L'établissement est obligatoire"
                return@launch
            }

            if (personnelId == 0L) {
                _error.value = "Veuillez sélectionner un personnel"
                return@launch
            }

            val diplome = Diplome(
                id = id,
                personnelId = personnelId,
                intitule = intitule,
                specialite = specialite,
                niveau = niveau,
                etablissement = etablissement,
                dateObtention = dateObtention,
                fichierPreuve = fichierPreuve
            )

            if (!diplome.estValide()) {
                _error.value = "Les données du diplôme sont invalides"
                return@launch
            }

            val flow = if (id == 0L) {
                diplomeRepository.createDiplome(diplome)
            } else {
                diplomeRepository.updateDiplome(diplome)
            }

            flow.collect { result ->
                when (result) {
                    is NetworkResult.Success<*> -> _saveSuccess.value = true
                    is NetworkResult.Error<*> -> _error.value = result.message
                    is NetworkResult.Loading<*> -> { /* Show loading if needed */ }
                }
            }
        }
    }
}