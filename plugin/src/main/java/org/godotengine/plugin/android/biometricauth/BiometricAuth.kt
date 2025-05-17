package org.godotengine.plugin.android.biometricauth

import android.os.Bundle
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import org.godotengine.godot.Godot
import org.godotengine.godot.plugin.GodotPlugin
import org.godotengine.godot.plugin.SignalInfo
import org.godotengine.godot.plugin.UsedByGodot

class BiometricAuth(private val godot: Godot) : GodotPlugin(godot) {

	private var prompt: BiometricPrompt? = null

	override fun getPluginName(): String = "BiometricAuthPlugin"

	override fun getPluginSignals(): Set<SignalInfo> {
		return setOf(
			SignalInfo("on_auth_success"),
			SignalInfo("on_auth_failed", String::class.java)
		)
	}

	@UsedByGodot
	fun startAuthentication() {
		val activity = activity
		if (activity !is FragmentActivity) {
			emitSignal("on_auth_failed", "Activity is not a FragmentActivity")
			return
		}

		val executor = ContextCompat.getMainExecutor(activity)

		val promptInfo = BiometricPrompt.PromptInfo.Builder()
			.setTitle("Confirm Your Identity")
			.setSubtitle("Authenticate using fingerprint or screen lock")
			.setNegativeButtonText("Cancel")
			.build()

		prompt = BiometricPrompt(activity, executor, object : BiometricPrompt.AuthenticationCallback() {
			override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
				emitSignal("on_auth_success")
			}

			override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
				emitSignal("on_auth_failed", errString.toString())
			}

			override fun onAuthenticationFailed() {
				emitSignal("on_auth_failed", "Authentication attempt failed")
			}
		})

		prompt?.authenticate(promptInfo)
	}

}
