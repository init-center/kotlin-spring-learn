package center.init.demo.common.exception.exceptions

import org.springframework.http.HttpStatus

class NotFoundHttpException(
    override var message: String ): HttpException(4000, HttpStatus.NOT_FOUND, message) {
}