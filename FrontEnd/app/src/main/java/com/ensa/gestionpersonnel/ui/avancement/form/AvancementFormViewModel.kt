package com.ensa.gestionpersonnel.ui.avancement.form

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ensa.gestionpersonnel.data.repository.AvancementRepository
import com.ensa.gestionpersonnel.data.repository.PersonnelRepository
import com.ensa.gestionpersonnel.domain.model.Avancement
import com.ensa.gestionpersonnel.domain.model.Personnel
import com.ensa.gestionpersonnel.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class AvancementFormViewModel @Inject constructor(
    private val avancementRepository: AvancementRepository,
    private val personnelRepository: PersonnelRepository
) : ViewModel() {

    private val _avancement = MutableLiveData<Avancement?>()
    val avancement: LiveData<Avancement?> = _avancement

    private val _personnelList = MutableLiveData<List<Personnel>>()
    val personnelList: LiveData<List<Personnel>> = _personnelList

    private val _saveState = MutableLiveData<NetworkResult<Avancement>>()
    val saveState: LiveData<NetworkResult<Avancement>> = _saveState

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun loadPersonnelList() {
        viewModelScope.launch {
            val result = personnelRepository.getAllPersonnel()
            if (result is NetworkResult.Success<*>) {
                val data = result.data as? List<Personnel>
                _personnelList.value = data?.filter { it.estActif } ?: emptyList()
            }
        }
    }

    fun loadAvancement(id: Long) {
        viewModelScope.launch {
            avancementRepository.getAvancementById(id).collect { result ->
                if (result is NetworkResult.Success<*>) {
                    _avancement.value = result.data as? Avancement
                }
            }
        }
    }

    fun saveAvancement(
        id: Long,
        personnelId: Long,
        dateDecision: Date,
        dateEffet: Date,
        gradePrecedent: String,
        gradeNouveau: String,
        echellePrecedente: Int,
        echelleNouvelle: Int,
        echelonPrecedent: Int,
        echelonNouveau: Int,
        description: String
    ) {
        if (gradeNouveau.isBlank()) {
            _error.value = "Le nouveau grade est obligatoire"
            return
        }

        if (personnelId == 0L) {
            _error.value = "Veuillez sélectionner un personnel"
            return
        }

        val avancement = Avancement(
            id = id,
            personnelId = personnelId,
            dateDecision = dateDecision,
            dateEffet = dateEffet,
            gradePrecedent = gradePrecedent,
            gradeNouveau = gradeNouveau,
            echellePrecedente = echellePrecedente,
            echelleNouvelle = echelleNouvelle,
            echelonPrecedent = echelonPrecedent,
            echelonNouveau = echelonNouveau,
            description = description
        )

        viewModelScope.launch {
            val flow = if (id == 0L) {
                avancementRepository.createAvancement(avancement)
            } else {
                avancementRepository.updateAvancement(avancement)
            }

            flow.collect { result ->
                _saveState.value = result
            }
        }
    }
}