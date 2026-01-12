package com.ensa.gestionpersonnel.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ensa.gestionpersonnel.data.local.PreferencesManager
import com.ensa.gestionpersonnel.data.repository.ProfileRepository
import com.ensa.gestionpersonnel.domain.model.ResponsableRH
import com.ensa.gestionpersonnel.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileRepository: ProfileRepository,
    private val preferencesManager: PreferencesManager
) : ViewModel() {

    private val _profile = MutableLiveData<ResponsableRH>()
    val profile: LiveData<ResponsableRH> = _profile

    private val _updateSuccess = MutableLiveData<Boolean>()
    val updateSuccess: LiveData<Boolean> = _updateSuccess

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        _isLoading.value = false
        _updateSuccess.value = null
    }

    fun loadProfile() {
        viewModelScope.launch {
            _isLoading.value = true
            
            // Récupérer le profil local
            val localProfile = preferencesManager.getRhProfile()
            if (localProfile != null) {
                _profile.value = localProfile
                
                // Charger les données fraîches du backend avec le bon ID
                val result = profileRepository.getProfile(localProfile.id)
                if (result is NetworkResult.Success) {
                    _profile.value = result.data
                }
            } else {
                // Si pas de session, on ne fait rien (l'utilisateur devrait être redirigé vers login)
            }
            
            _isLoading.value = false
        }
    }

    fun updateProfile(newRH: ResponsableRH) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = profileRepository.updateProfile(newRH.id, newRH)
            
            if (result is NetworkResult.Success) {
                _profile.value = result.data
                _updateSuccess.value = true
            } else {
                _updateSuccess.value = false
            }
            
            _isLoading.value = false
        }
    }
}