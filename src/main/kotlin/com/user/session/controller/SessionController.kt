package com.user.session.controller



import com.user.session.dto.CreateSessionRequest
import com.user.session.dto.UpdateSessionRequest
import com.user.session.model.Session
import com.user.session.service.SessionService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/sessions")
class SessionController(
    private val sessionService: SessionService
) {

    @PostMapping
    fun createSession(
        @Valid @RequestBody request: CreateSessionRequest
    ): ResponseEntity<Session> {

        val session = sessionService.createSession(request)

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(session)
    }

    @GetMapping("/{sessionId}")
    fun getSession(
        @PathVariable sessionId: String
    ): ResponseEntity<Session> {

        return ResponseEntity.ok(
            sessionService.getSession(sessionId)
        )
    }

    @GetMapping("/owner/{ownerId}")
    fun getSessionsByOwner(
        @PathVariable ownerId: String
    ): ResponseEntity<List<Session>> {

        return ResponseEntity.ok(
            sessionService.getSessionsByOwner(ownerId)
        )
    }

    @PutMapping("/{sessionId}")
    fun updateSession(
        @PathVariable sessionId: String,
        @Valid @RequestBody request: UpdateSessionRequest
    ): ResponseEntity<Session> {

        return ResponseEntity.ok(
            sessionService.updateSession(
                sessionId,
                request
            )
        )
    }

    @PostMapping("/{sessionId}/start")
    fun startSession(
        @PathVariable sessionId: String,
        @RequestParam ownerId: String
    ): ResponseEntity<Session> {

        return ResponseEntity.ok(
            sessionService.startSession(
                sessionId,
                ownerId
            )
        )
    }

    @PostMapping("/{sessionId}/end")
    fun endSession(
        @PathVariable sessionId: String,
        @RequestParam ownerId: String
    ): ResponseEntity<Session> {

        return ResponseEntity.ok(
            sessionService.endSession(
                sessionId,
                ownerId
            )
        )
    }

    @GetMapping("/health")
    fun health(): ResponseEntity<Map<String, String>> {

        return ResponseEntity.ok(
            mapOf(
                "status" to "healthy",
                "service" to "session-service"
            )
        )
    }
}