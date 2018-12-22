package murer.rudy.api

import okhttp3.MediaType
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import java.net.SocketTimeoutException

private data class ErrorResponse(val message: String, val stackTrace: String)

fun <T: Any> Response<T>.getErrorMessage(): String {
    if (this.isSuccessful) {
        //throw IllegalStateException() //you should not call getErrorMessage on a successful response
    }
    return when (this.code()) {
        510 -> this.errorBody()!!.string().fromJson(ErrorResponse::class.java).message
        400 -> "Veuillez vérifier votre saisie"
        401-> "Acces Token invalide"
        408 -> "La plateforme est indisponible pour le moment"
        404 -> "Une erreur inconnue est survenue dans l'application"
        500 -> "Une erreur inconnue est survenue sur la plateforme"
        else -> "Une erreur inconnue est survenue" //what can this be ?
    }
}

fun <T:Any> Call<T>.safeExecute() = try {
    this.execute()
} catch (e: SocketTimeoutException) {
    Response.error<T>(408, ResponseBody.create(MediaType.parse("text/plain"), "Timeout"))
}