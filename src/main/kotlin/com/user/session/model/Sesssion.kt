package com.user.session.model



import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant

@Document(collection = "sessions")
data class Session(

    @Id
    val id: String? = null,

    @Indexed
    val ownerId: String,

    val title: String,

    val description: String? = null,

    val accessMode: AccessMode,

    val status: SessionStatus = SessionStatus.CREATED,

    val createdAt: Instant? = null,

    val startedAt: Instant? = null,

    val endedAt: Instant? = null
)