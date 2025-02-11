package com.example.plantapp.presentation

import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckBox
import androidx.compose.material.icons.filled.CheckBoxOutlineBlank
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.fitbites.presentation.components.SocialMediaSection
import com.example.plantapp.BuildConfig
import com.example.plantapp.navigation.ROUTE_LOGIN
import com.example.plantapp.R
import com.example.plantapp.components.AuthFooter
import com.example.plantapp.components.AuthHeader
import com.example.plantapp.components.AuthTextField
import com.example.plantapp.components.GradientButton
import com.example.plantapp.onboarding.OnboardingViewModel
import com.example.plantapp.ui.theme.EventAppTheme
import com.example.plantapp.utils.Response
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    authViewModel: AuthViewModel = hiltViewModel(),
    onboardingViewModel: OnboardingViewModel,
    navController: NavController,
    onSignUpClick: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var checked by remember { mutableStateOf(false) }
    val emailVerificationState by authViewModel.emailVerificationState.collectAsState()


    val callbackManager = remember { CallbackManager.Factory.create() }
    LoginManager.getInstance().registerCallback(
        callbackManager,
        object : FacebookCallback<LoginResult> {
            override fun onSuccess(result: LoginResult) {
                val accessToken = result.accessToken.token
                authViewModel.signUpWithFacebook(accessToken)
            }

            override fun onCancel() {
                Log.d("FacebookAuth", "Facebook login cancelled")
            }

            override fun onError(error: FacebookException) {
                Log.e("FacebookAuth", "Facebook login error", error)
            }
        }
    )


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

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val account = task.getResult(ApiException::class.java)
                account?.idToken?.let { idToken ->
                    authViewModel.signUpWithGoogle(idToken)
                }
            } catch (e: ApiException) {
                Log.e("GoogleSignIn", "Google sign-in failed", e)
            }
        }
    }

    LaunchedEffect(emailVerificationState) {
        emailVerificationState?.let { response ->
            when (response) {
                is Response.Success -> {
                    Toast.makeText(context, "Verification email sent. Please check your inbox.", Toast.LENGTH_SHORT).show()
                }
                is Response.Error -> {
                    Toast.makeText(context, response.message, Toast.LENGTH_SHORT).show()
                }
                is Response.Loading -> {
                }
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
                title = stringResource(R.string.get_started),
                subtitle = stringResource(R.string.by_creating_a_free_account)
            )

            Spacer(modifier = Modifier.height(32.dp))

            AuthTextField(
                value = name,
                onValueChange = {
                    name = it
                    onboardingViewModel.updateName(name) },
                label = "Full name",
                trailingIcon = { Icon(Icons.Default.Person, contentDescription = "Name Icon", modifier = Modifier.size(22.dp)) }
            )

            Spacer(modifier = Modifier.height(16.dp))

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
                onPasswordVisibilityChange = { passwordVisible = !passwordVisible }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 5.dp),
                horizontalArrangement = Arrangement.Absolute.Left
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(end = 5.dp)
                        .size(16.dp)
                        .clickable { checked = !checked }
                ) {
                    Icon(
                        imageVector = if (checked) Icons.Filled.CheckBox else Icons.Filled.CheckBoxOutlineBlank,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }


                Text(
                    text = "Agree to our",
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Light
                    ),
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
                Text(
                    text = " Terms",
                    style = TextStyle(
                        color = Color.Green,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Light
                    ),
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
                Text(
                    text = " and",
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Light
                    ),
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
                Text(
                    text = " Conditions",
                    style = TextStyle(
                        color = Color.Green,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Light
                    ),
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            GradientButton(
                text = "Sign Up",
                onClick = {
                    authViewModel.signUp(email, password, name, context)
                    navController.navigate(ROUTE_LOGIN)
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
                    LoginManager.getInstance().logInWithReadPermissions(
                        context as Activity,
                        listOf("email", "public_profile")
                    )
                },
                onAppleAuth = {
                    Log.d("SocialAuth", "Apple icon clicked")
                },
                text = "or sign up with"
            )

            Spacer(modifier = Modifier.weight(1f))

            Spacer(modifier = Modifier.height(32.dp))

            AuthFooter(
                message = "Already have an account? ",
                actionText = "Sign in",
                onActionClick = {
                    navController.navigate(ROUTE_LOGIN)
                }
            )

        }
    }
}




