package com.mdxco.c4bank.application.web.requests

import com.mdxco.c4bank.application.web.dtos.AccountAddressDTO
import com.mdxco.c4bank.commons.constants.RegexpMatches
import com.mdxco.c4bank.commons.constants.ResponseMessages
import com.mdxco.c4bank.domain.entities.Account
import com.mdxco.c4bank.domain.entities.enums.AccountStatus
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern
import org.hibernate.validator.constraints.Length
import java.math.BigDecimal
import java.util.UUID

data class CreateAccountRequest(
    @field:NotNull(message = ResponseMessages.FIELD_REQUIRED)
    @Schema(description = "Account owner address")
    var address: AccountAddressDTO,

    @field:NotNull(message = ResponseMessages.FIELD_REQUIRED)
    @field:Length(min = 2, max = 120, message = ResponseMessages.FIELD_INVALID)
    @field:Pattern(
        // '\\p{L}' allows letters from any language, and '\\p{Z}' allows whitespace separators
        regexp = RegexpMatches.NAME, message = ResponseMessages.CANNOT_CONTAIN_EMOJIS
    )
    @Schema(description = "Account owner full name", example = "André Cocão")
    var name: String,

    @field:NotNull(message = ResponseMessages.FIELD_REQUIRED)
    @field:Pattern(regexp = RegexpMatches.PHONE_NUMBER, message = ResponseMessages.FIELD_INVALID)
    @Schema(description = "Phone number without country code", example = "11988885555")
    var phone: String,

    @field:NotNull(message = ResponseMessages.FIELD_REQUIRED)
    @field:Pattern(regexp = RegexpMatches.TAX_IDENTIFIER, message = ResponseMessages.FIELD_INVALID)
    @Schema(description = "Account owner tax identifier", example = "55599944471")
    var taxIdentifier: String
) {
    init {
        name = name.trim()
        phone = phone.trim()
        taxIdentifier = taxIdentifier.trim()
    }

    fun toDomain(id: String = UUID.randomUUID().toString()) = Account(
        address = address.toDomain(),
        balance = BigDecimal.ZERO.plus(),
        id = id,
        name = name,
        phone = phone,
        taxIdentifier = taxIdentifier,
        status = AccountStatus.ACTIVE
    )
}
