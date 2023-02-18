package me.ruslan1024.kk2048game.ui.screen

import android.app.Activity.RESULT_OK
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.ActionCodeSettings
import com.google.firebase.auth.FirebaseAuth
import me.ruslan1024.kk2048game.R
import me.ruslan1024.kk2048game.app.App
import me.ruslan1024.kk2048game.databinding.ScreenRegisterBinding
import timber.log.Timber

class RegisterScreen : Fragment(R.layout.screen_register) {
    private val binding by viewBinding(ScreenRegisterBinding::bind)
    private val AUTH_REQUEST_CODE = 2134

    private val actionCodeSettings = ActionCodeSettings.newBuilder()
        .setAndroidPackageName(App.instance.packageName,
            true,
            null  /* minimumVersion= */
        )
        .setHandleCodeInApp(true) // This must be set to true
        .setUrl("kk-2048-game.firebaseapp.com") // This URL needs to be whitelisted
        .build()

    // Choose authentication providers
    private val providers = arrayListOf(
        AuthUI.IdpConfig.EmailBuilder()
            .enableEmailLinkSignIn()
            .setActionCodeSettings(actionCodeSettings)
            .build(),
        AuthUI.IdpConfig.GoogleBuilder().build(),
    )

    // Create and launch sign-in intent

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        Timber.d("start")
    }

    private fun init() {
        val signInIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setIsSmartLockEnabled(false)
            .setAvailableProviders(providers)
            .build()
        signInLauncher.launch(signInIntent)

    }

    private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ) { res ->
        onSignInResult(res)
    }

    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        val response = result.idpResponse
        Timber.d("response -> $response")

        if (result.resultCode == RESULT_OK) {
            // Successfully signed in
            val user = FirebaseAuth.getInstance().currentUser
            Timber.d("user -> $user")
            // ...
        } else {
            Timber.d("error -> ${response?.error?.errorCode}")
            // Sign in failed. If response is null the user canceled the
            // sign-in flow using the back button. Otherwise check
            // response.getError().getErrorCode() and handle the error.
            // ...
        }
    }

}