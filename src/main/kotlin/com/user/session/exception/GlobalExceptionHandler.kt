package com.user.session.exception



import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationException(
        exception: MethodArgumentNotValidException,
        request: HttpServletRequest
    ): ResponseEntity<ErrorResponse> {

        val message = exception.bindingResult
            .fieldErrors
            .joinToString(", ") { "${it.field}: ${it.defaultMessage}" }

        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(
                ErrorResponse(
                    status = HttpStatus.BAD_REQUEST.value(),
                    error = "VALIDATION_ERROR",
                    message = message,
                    path = request.requestURI
                )
            )
    }

    @ExceptionHandler(SessionNotFoundException::class)
    fun handleSessionNotFound(
        exception: SessionNotFoundException,
        request: HttpServletRequest
    ): ResponseEntity<ErrorResponse> {

        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(
                ErrorResponse(
                    status = HttpStatus.NOT_FOUND.value(),
                    error = "SESSION_NOT_FOUND",
                    message = exception.message ?: "Session not found",
                    path = request.requestURI
                )
            )
    }

    @ExceptionHandler(SessionPermissionException::class)
    fun handlePermissionException(
        exception: SessionPermissionException,
        request: HttpServletRequest
    ): ResponseEntity<ErrorResponse> {

        return ResponseEntity
            .status(HttpStatus.FORBIDDEN)
            .body(
                ErrorResponse(
                    status = HttpStatus.FORBIDDEN.value(),
                    error = "PERMISSION_DENIED",
                    message = exception.message ?: "Permission denied",
                    path = request.requestURI
                )
            )
    }

    @ExceptionHandler(InvalidSessionStateException::class)
    fun handleInvalidState(
        exception: InvalidSessionStateException,
        request: HttpServletRequest
    ): ResponseEntity<ErrorResponse> {

        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(
                ErrorResponse(
                    status = HttpStatus.BAD_REQUEST.value(),
                    error = "INVALID_SESSION_STATE",
                    message = exception.message ?: "Invalid session state",
                    path = request.requestURI
                )
            )
    }

    @ExceptionHandler(Exception::class)
    fun handleUnexpectedException(
        exception: Exception,
        request: HttpServletRequest
    ): ResponseEntity<ErrorResponse> {

        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(
                ErrorResponse(
                    status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    error = "INTERNAL_SERVER_ERROR",
                    message = "An unexpected error occurred",
                    path = request.requestURI
                )
            )
    }
}