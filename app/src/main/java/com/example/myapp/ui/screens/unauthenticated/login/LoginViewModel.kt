package com.example.myapp.ui.screens.unauthenticated.login

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myapp.base.BaseViewModel
import com.example.myapp.data.model.LoginRequest
import com.example.myapp.data.model.LoginResponse
import com.example.myapp.data.repository.LoginRepository
import com.example.myapp.ui.common.state.ErrorState
import com.example.myapp.ui.screens.unauthenticated.login.state.LoginErrorState
import com.example.myapp.ui.screens.unauthenticated.login.state.LoginState
import com.example.myapp.ui.screens.unauthenticated.login.state.LoginUiEvent
import com.example.myapp.ui.screens.unauthenticated.login.state.emailOrMobileEmptyErrorState
import com.example.myapp.ui.screens.unauthenticated.login.state.passwordEmptyErrorState
import com.example.myapp.util.NetWorkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for Login Screen
 */
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
    application: Application
) :
    BaseViewModel(application) {

    var loginState = mutableStateOf(LoginState())
        private set
    private val _responseLogin: MutableLiveData<NetWorkResult<LoginResponse>> = MutableLiveData()
    val responseLogin: LiveData<NetWorkResult<LoginResponse>> = _responseLogin

    /**
     * Function called on any login event [LoginUiEvent]
     */
    fun onUiEvent(loginUiEvent: LoginUiEvent) {
        when (loginUiEvent) {

            // Email/Mobile changed
            is LoginUiEvent.EmailOrMobileChanged -> {
                loginState.value = loginState.value.copy(
                    emailOrMobile = loginUiEvent.inputValue,
                    errorState = loginState.value.errorState.copy(
                        emailOrMobileErrorState = if (loginUiEvent.inputValue.trim().isNotEmpty()) {
                            loginState.value = loginState.value.copy(isLoginLoading = false)
                            ErrorState()
                        } else
                            emailOrMobileEmptyErrorState
                    )
                )
            }

            // Password changed
            is LoginUiEvent.PasswordChanged -> {
                loginState.value = loginState.value.copy(
                    password = loginUiEvent.inputValue,
                    errorState = loginState.value.errorState.copy(
                        passwordErrorState = if (loginUiEvent.inputValue.trim().isNotEmpty()) {
                            loginState.value = loginState.value.copy(isLoginLoading = false)
                            ErrorState()
                        } else
                            passwordEmptyErrorState
                    )
                )
            }

            // Submit Login
            is LoginUiEvent.Submit -> {
                val inputsValidated = validateInputs()
                if (inputsValidated) {
                    loginState.value = loginState.value.copy(isLoginLoading = true)
                    submitLogin()
                }
            }
        }
    }

    private fun submitLogin() {
        viewModelScope.launch {
            val loginRequest = LoginRequest(
                email = loginState.value.emailOrMobile,
                password = loginState.value.password
            )
            loginRepository.login(context, loginRequest = loginRequest).collect {
                _responseLogin.value = it
            }
            loginState.value =
                loginState.value.copy(isLoginSuccessful = false) // change this status after api call
        }


    }


    /**
     * Function to validate inputs
     * Ideally it should be on domain layer (usecase)
     * @return true -> inputs are valid
     * @return false -> inputs are invalid
     */
    private fun validateInputs(): Boolean {
        val emailOrMobileString = loginState.value.emailOrMobile.trim()
        val passwordString = loginState.value.password
        return when {

            // Email/Mobile empty
            emailOrMobileString.isEmpty() -> {
                loginState.value = loginState.value.copy(
                    errorState = LoginErrorState(
                        emailOrMobileErrorState = emailOrMobileEmptyErrorState
                    )
                )
                false
            }

            //Password Empty
            passwordString.isEmpty() -> {
                loginState.value = loginState.value.copy(
                    errorState = LoginErrorState(
                        passwordErrorState = passwordEmptyErrorState
                    )
                )
                false
            }

            // No errors
            else -> {
                // Set default error state
                loginState.value = loginState.value.copy(errorState = LoginErrorState())
                true
            }
        }
    }

}