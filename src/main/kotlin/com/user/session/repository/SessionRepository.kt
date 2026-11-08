package com.user.session.repository



import com.user.session.model.Session
import org.springframework.data.mongodb.repository.MongoRepository

interface SessionRepository : MongoRepository<Session, String> {

    fun findByOwnerId(ownerId: String): List<Session>
}