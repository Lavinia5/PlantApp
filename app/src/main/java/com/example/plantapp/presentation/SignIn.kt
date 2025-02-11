package com.example.plantapp.presentation

import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.plantapp.navigation.ROUTE_SIGNUP
import com.example.plantapp.components.AuthFooter
import com.example.plantapp.components.AuthHeader
import com.example.plantapp.components.AuthTextField
import com.example.plantapp.components.GradientButton
import com.example.fitbites.presentation.components.SocialMediaSection
import com.example.plantapp.BuildConfig
import com.example.plantapp.R
import com.example.plantapp.ui.theme.EventAppTheme
import com.example.plantapp.utils.Response
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import kotlinx.coroutines.delay


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    authViewModel: AuthViewModel = hiltViewModel(),
    navController: NavController,
    onSignInClick: () -> Unit,
    onForgotPasswordClick: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    val isUserAuthenticated = authViewModel.isUserAuthenticatedState.value
    val isSetupCompleted = authViewModel.isSetupComplete.value
    val context = LocalContext.current

    val googleSignInClient = remember {
        GoogleSignIn.getClient(
            context,
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(BuildConfig.GOOGLE_REQUEST_TOKEN)
                .requestEmail()
                .build()
        )
    }

    val passwordResetState by authViewModel.passwordResetState.collectAsState()

    LaunchedEffect(passwordResetState) {
        passwordResetState?.let { response ->
            when (response) {
                is Response.Success -> {
                    Toast.makeText(context, "Password reset email sent", Toast.LENGTH_SHORT).show()
                }
                is Response.Error -> {
                    Toast.makeText(context, response.message, Toast.LENGTH_SHORT).show()
                }
                is Response.Loading -> {
                }
            }
        }
    }


    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val account = task.getResult(ApiException::class.java)
                account?.idToken?.let { idToken ->
                    authViewModel.signInWithGoogle(idToken)
                }
            } catch (e: ApiException) {
                Log.e("GoogleSignIn", "Google sign-in failed", e)
            }
        }
    }


    LaunchedEffect(isUserAuthenticated, isSetupCompleted) {
        delay(500)
        if (isUserAuthenticated) {
            if (isSetupCompleted) {
                Log.d("AUTH", "Setup is complete, navigating to dashboard")
                navController.navigate("dashboard")
            } else {
                Log.d("AUTH", "Setup not complete, navigating to goal setup")
                navController.navigate("goal")
            }
        }
    }


    EventAppTheme(dynamicColor = false) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(10.dp))

            AuthHeader(
                title = stringResource(R.string.welcome_back),
                subtitle = stringResource(R.string.sign_in_to_access_your_account)
            )

            Spacer(modifier = Modifier.height(32.dp))

            AuthTextField(
                value = email,
                onValueChange = { email = it },
                label = "Email",
                trailingIcon = { Icon(Icons.Default.Email, contentDescription = "Email Icon", modifier = Modifier.size(22.dp)) }
            )

            Spacer(modifier = Modifier.height(16.dp))

            AuthTextField(
                value = password,
                onValueChange = { password = it },
                label = "Password",
                isPassword = true,
                isPasswordVisible = passwordVisible,
                onPasswordVisibilityChange = { passwordVisible = !passwordVisible },
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = stringResource(R.string.forgot_password),
                color = Color(0xFF16DB16),
                style = MaterialTheme.typography.headlineSmall,
                fontSize = 13.sp,
                modifier = Modifier
                    .align(Alignment.End)
                    .clickable { authViewModel.sendPasswordResetEmail(email) }
            )

            Spacer(modifier = Modifier.height(32.dp))

            GradientButton(
                text = "Sign In",
                onClick = {
                    authViewModel.signIn(email, password, context)
                },
                modifier = Modifier.padding(horizontal = 30.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            SocialMediaSection(
                onGoogleAuth = {
                    Log.d("SocialAuth", "Google icon clicked")
                    val signInIntent = googleSignInClient.signInIntent
                    launcher.launch(signInIntent)
                },
                onFacebookAuth = {
                    Log.d("SocialAuth", "Facebook icon clicked")
                },
                onAppleAuth = {
                    Log.d("SocialAuth", "Apple icon clicked")
                },
                text = "or sign in with"
            )

            Spacer(modifier = Modifier.weight(1f))

            AuthFooter(
                message = stringResource(R.string.don_t_have_an_account),
                actionText = stringResource(R.string.sign_up),
                onActionClick = {
                    navController.navigate(ROUTE_SIGNUP)
                }
            )

        }
    }
}




