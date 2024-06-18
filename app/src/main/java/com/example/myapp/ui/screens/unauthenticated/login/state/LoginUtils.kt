package com.example.myapp.ui.screens.unauthenticated.login.state

import com.example.myapp.R
import com.example.myapp.ui.common.state.ErrorState


val emailOrMobileEmptyErrorState = ErrorState(
    hasError = true,
    errorMessageStringResource = R.string.login_error_msg_empty_email_mobile
)

val passwordEmptyErrorState = ErrorState(
    hasError = true,
    errorMessageStringResource = R.string.login_error_msg_empty_password
)