package com.herald.mazaadyapp.domain.usecases

import com.herald.mazaadyapp.common.Resources
import com.herald.mazaadyapp.domain.models.Options
import com.herald.mazaadyapp.domain.repository.RetroRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetOptionsUseCase @Inject constructor(
    private val retroRepo: RetroRepo
){
    operator fun invoke(id:Int): Flow<Resources<Options>> = flow {
        try {
            emit(Resources.Loading())
            val data = retroRepo.getOptions(id)
            emit(Resources.Success(data.toOptions()))
        } catch (e: HttpException) {
            emit(Resources.Error(message = e.message.toString()))
        } catch (e: IOException) {
            emit(Resources.Error(message = e.message.toString()))
        }
    }
}