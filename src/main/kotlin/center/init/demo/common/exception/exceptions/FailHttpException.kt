package center.init.demo.common.exception.exceptions

import org.springframework.http.HttpStatus

class FailHttpException(override var message: String): HttpException(4000, HttpStatus.BAD_REQUEST, message)