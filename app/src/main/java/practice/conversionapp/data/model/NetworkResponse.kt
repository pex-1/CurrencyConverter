package practice.conversionapp.data.model

class NetworkResponse(val status: NetworkStatus, val message: String?) {

    enum class NetworkStatus {
        ERROR, SUCCESS
    }

    companion object {

        fun error(msg: String?): NetworkResponse {
            return NetworkResponse(NetworkStatus.ERROR,  msg)
        }

        fun success(): NetworkResponse {
            return NetworkResponse(NetworkStatus.SUCCESS, null)
        }

    }

}