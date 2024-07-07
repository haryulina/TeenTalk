package com.user.teentalk.Data.di

import com.user.teentalk.Data.Model.Educate.EducateRepository

object Injection {
    fun provideRepository(): EducateRepository {
        return EducateRepository.getInstance()
    }
}