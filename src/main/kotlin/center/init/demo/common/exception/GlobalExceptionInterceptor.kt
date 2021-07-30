package center.init.demo.common.exception

import center.init.demo.common.exception.exceptions.HttpException
import center.init.demo.common.response.Response
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import javax.servlet.http.HttpServletRequest
import javax.validation.ConstraintViolationException

@ControllerAdvice
class GlobalExceptionInterceptor {
    @ExceptionHandler(value = [Exception::class])
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleException(request: HttpServletRequest, e: Exception): Response<Nothing?> {
        return Response(5000, "服务器内部错误", request.method, request.requestURI, null)
    }

    @ExceptionHandler(value = [HttpException::class])
    fun handleHttpException(request: HttpServletRequest, e: HttpException): ResponseEntity<Response<Nothing?>> {
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        return ResponseEntity<Response<Nothing?>>(
            Response(e.code, e.message, request.method, request.requestURI, null),
            headers,
            e.httpCode
        )
    }

    @ExceptionHandler(value = [MethodArgumentNotValidException::class])
    @ResponseBody
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    fun handleBodyNotValidException(request: HttpServletRequest, e: MethodArgumentNotValidException):Response<Nothing?> {
        val message = e.bindingResult.allErrors.map { it.defaultMessage }.reduce{acc,cur -> "$acc;$cur"}
        return Response(4022, message ?: "参数错误", request.method, request.requestURI, null)
    }

    @ExceptionHandler(value = [ConstraintViolationException::class])
    @ResponseBody
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    fun handleUrlNotValidException(request: HttpServletRequest, e: ConstraintViolationException):Response<Nothing?> {
        val message = e.localizedMessage.split(",").joinToString(";") { it -> it.split(":")[1] }
        return Response(4022, message, request.method, request.requestURI, null)
    }
}