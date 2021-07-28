package center.init.demo.common.exception.exceptions

import org.springframework.http.HttpStatus

open class HttpException(
    open var code: Int,
    open var httpCode: HttpStatus,
    override var message: String
    ): Exception() {
}