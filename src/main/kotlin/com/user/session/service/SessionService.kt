package com.user.session.service



import com.user.session.dto.CreateSessionRequest
import com.user.session.dto.UpdateSessionRequest
import com.user.session.exception.InvalidSessionStateException
import com.user.session.exception.SessionNotFoundException
import com.user.session.exception.SessionPermissionException
import com.user.session.model.Session
import com.user.session.model.SessionStatus
import com.user.session.repository.SessionRepository
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class SessionService(
    private val sessionRepository: SessionRepository
) {

    fun createSession(request: CreateSessionRequest): Session {

        val session = Session(
            ownerId = request.ownerId,
            title = request.title,
            description = request.description,
            accessMode = request.accessMode!!,
            status = SessionStatus.CREATED,
            createdAt = Instant.now()
        )

        return sessionRepository.save(session)
    }

    fun getSession(sessionId: String): Session {
        return sessionRepository.findById(sessionId)
            .orElseThrow {
                SessionNotFoundException(
                    "Session not found with id: $sessionId"
                )
            }
    }

    fun getSessionsByOwner(ownerId: String): List<Session> {
        return sessionRepository.findByOwnerId(ownerId)
    }

    fun updateSession(
        sessionId: String,
        request: UpdateSessionRequest
    ): Session {

        val existingSession = getSession(sessionId)

        val updatedSession = existingSession.copy(
            title = request.title,
            description = request.description,
            accessMode = request.accessMode!!
        )

        return sessionRepository.save(updatedSession)
    }

    fun startSession(
        sessionId: String,
        ownerId: String
    ): Session {

        val session = getSession(sessionId)

        checkOwner(session, ownerId)

        if (session.status != SessionStatus.CREATED) {
            throw InvalidSessionStateException(
                "Only CREATED sessions can be started"
            )
        }

        val startedSession = session.copy(
            status = SessionStatus.LIVE,
            startedAt = Instant.now()
        )

        return sessionRepository.save(startedSession)
    }

    fun endSession(
        sessionId: String,
        ownerId: String
    ): Session {

        val session = getSession(sessionId)

        checkOwner(session, ownerId)

        if (session.status != SessionStatus.LIVE) {
            throw InvalidSessionStateException(
                "Only LIVE sessions can be ended"
            )
        }

        val endedSession = session.copy(
            status = SessionStatus.ENDED,
            endedAt = Instant.now()
        )

        return sessionRepository.save(endedSession)
    }

    private fun checkOwner(
        session: Session,
        ownerId: String
    ) {
        if (session.ownerId != ownerId) {
            throw SessionPermissionException(
                "Only the session owner can perform this operation"
            )
        }
    }
}