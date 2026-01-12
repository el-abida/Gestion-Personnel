package com.ensa.gestionpersonnel.di

import android.content.Context
import com.ensa.gestionpersonnel.data.local.AvancementLocalStorage
import com.ensa.gestionpersonnel.data.local.DiplomeLocalStorage
import com.ensa.gestionpersonnel.data.local.PreferencesManager
import com.ensa.gestionpersonnel.data.remote.api.AuthApi
import com.ensa.gestionpersonnel.data.remote.api.DashboardApi
import com.ensa.gestionpersonnel.data.remote.api.DiplomeApi
import com.ensa.gestionpersonnel.data.remote.api.PersonnelApi
import com.ensa.gestionpersonnel.data.repository.AuthRepository
import com.ensa.gestionpersonnel.data.repository.AvancementRepository
import com.ensa.gestionpersonnel.data.repository.DashboardRepository
import com.ensa.gestionpersonnel.data.repository.MissionRepository
import com.ensa.gestionpersonnel.data.repository.PersonnelRepository
import com.ensa.gestionpersonnel.data.remote.api.AvancementApi
import com.ensa.gestionpersonnel.data.remote.api.MissionApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideAuthRepository(authApi: AuthApi, preferencesManager: PreferencesManager): AuthRepository {
        return AuthRepository(authApi, preferencesManager)
    }

    @Provides
    @Singleton
    fun providePersonnelRepository(personnelApi: PersonnelApi): PersonnelRepository {
        return PersonnelRepository(personnelApi)
    }

    @Provides
    @Singleton
    fun provideDashboardRepository(dashboardApi: DashboardApi): DashboardRepository {
        return DashboardRepository(dashboardApi)
    }

    @Provides
    @Singleton
    fun provideDiplomeRepository(diplomeApi: DiplomeApi): com.ensa.gestionpersonnel.data.repository.DiplomeRepository {
        return com.ensa.gestionpersonnel.data.repository.DiplomeRepository(diplomeApi)
    }

    @Provides
    @Singleton
    fun provideDiplomeLocalStorage(
        @ApplicationContext context: Context
    ): DiplomeLocalStorage {
        return DiplomeLocalStorage(context)
    }

    @Provides
    @Singleton
    fun provideAvancementLocalStorage(
        @ApplicationContext context: Context
    ): AvancementLocalStorage {
        return AvancementLocalStorage(context)
    }

    @Provides
    @Singleton
    fun provideMissionRepository(missionApi: MissionApi): MissionRepository {
        return MissionRepository(missionApi)
    }

    @Provides
    @Singleton
    fun provideAvancementRepository(avancementApi: AvancementApi): AvancementRepository {
        return AvancementRepository(avancementApi)
    }
}