package com.user.teentalk.Data.Model.Educate

import kotlinx.coroutines.flow.flow

class EducateRepository {
    private val educates = mutableListOf<Educate>()

    init {
        if (educates.isEmpty()){
            EducateData.educates.forEach {
                educates.add(it)
            }
        }
    }

    fun searchEducate(query : String) = flow {
        val data = educates.filter {
            it.judul.contains(query, ignoreCase = true)
        }
        emit(data)
    }

    fun getEducateById(educateId : Int) : Educate {
        return educates.first{
            it.id == educateId
        }

    }

    companion object {
        @Volatile
        private var instance: EducateRepository? = null

        fun getInstance(): EducateRepository =
            instance ?: synchronized(this) {
                EducateRepository().apply {
                    instance = this
                }
            }
    }
}