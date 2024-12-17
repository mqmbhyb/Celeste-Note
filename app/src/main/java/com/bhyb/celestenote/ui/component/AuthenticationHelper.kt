package com.bhyb.celestenote.ui.component

import android.annotation.SuppressLint
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity

class BiometricAuthenticationHelper(private val activity: FragmentActivity, private val callback: Callback) {

    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo

    init {
        setupBiometricPrompt()
    }

    private fun setupBiometricPrompt() {
        // Create an executor for the BiometricPrompt
        val executor = ContextCompat.getMainExecutor(activity)

        // Initialize the BiometricPrompt with a listener that receives callbacks
        biometricPrompt = BiometricPrompt(activity, executor, object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                callback.onError(errString.toString())
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                callback.onSuccess("验证成功")
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                callback.onFailed("验证失败")
            }
        })

        // Set up the prompt information
        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("请验证指纹")
            .setSubtitle("验证成功以继续")
            .setNegativeButtonText("使用密码")
            .setAllowedAuthenticators(BIOMETRIC_STRONG)
            .build()
    }

    // Method to start the authentication process
    @SuppressLint("SwitchIntDef")
    fun authenticate() {
        // Check if the device has a biometric sensor and if it's available
        val biometricManager = BiometricManager.from(activity)
        when (biometricManager.canAuthenticate(BIOMETRIC_STRONG)) {
            BiometricManager.BIOMETRIC_SUCCESS -> {
                // Show the biometric prompt
                biometricPrompt.authenticate(promptInfo)
            }
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> callback.onError("此设备不支持生物识别认证")
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> callback.onError("生物识别硬件当前不可用")
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> callback.onError("您尚未设置生物识别凭证")
        }
    }

    // Callback interface for handling authentication results
    interface Callback {
        fun onSuccess(message: String)
        fun onError(message: String)
        fun onFailed(message: String)
    }
}