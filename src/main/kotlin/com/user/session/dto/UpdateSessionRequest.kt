package com.user.session.dto



import com.user.session.model.AccessMode
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class UpdateSessionRequest(

    @field:NotBlank(message = "title is required")
    @field:Size(
        min = 3,
        max = 100,
        message = "title must be between 3 and 100 characters"
    )
    val title: String,

    @field:Size(
        max = 500,
        message = "description must not exceed 500 characters"
    )
    val description: String? = null,

    @field:NotNull(message = "accessMode is required")
    val accessMode: AccessMode?
)