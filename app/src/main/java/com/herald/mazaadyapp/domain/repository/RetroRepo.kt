package com.herald.mazaadyapp.domain.repository

import com.herald.mazaadyapp.data.remote.dto.CategoriesDTO
import com.herald.mazaadyapp.data.remote.dto.OptionsDTO
import com.herald.mazaadyapp.data.remote.dto.PropertiesDTO

interface RetroRepo {

    suspend fun getCategories(): CategoriesDTO

    suspend fun getProperties(id: Int): PropertiesDTO

    suspend fun getOptions(id: Int): OptionsDTO

}