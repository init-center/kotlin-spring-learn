package center.init.demo.common.response

data class Response<T>(
    var code: Int,
    var message: String,
    var method: String,
    var url: String,
    var Data: T
)