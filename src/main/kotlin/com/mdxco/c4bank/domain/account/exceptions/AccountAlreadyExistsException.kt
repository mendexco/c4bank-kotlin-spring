package com.mdxco.c4bank.domain.account.exceptions

data class AccountAlreadyExistsException(
    val code: ErrorCodes? = ErrorCodes.ACCOUNT_ALREADY_EXISTS,
    val taxIdentifier: String
) : RuntimeException(ErrorCodes.ACCOUNT_ALREADY_EXISTS.message)