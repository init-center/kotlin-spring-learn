package center.init.demo.common.exception.exceptions

import org.springframework.http.HttpStatus

class UnauthorizedHttpException(override var message: String): HttpException(4001, HttpStatus.UNAUTHORIZED, message)