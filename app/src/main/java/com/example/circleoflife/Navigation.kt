package com.example.circleoflife

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import android.net.Uri
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument


@Composable

fun AppNavigation() {
    val navController: NavHostController = rememberNavController()

    NavHost(navController = navController, startDestination = "AuthScreen") {
        //Nav Start
        composable("getStarted") {
            GetStartedScreen(navController)
        }
        composable("AuthScreen") {
            AuthScreen()
        }
        composable("WelcomeScreen") {
            WelcomeScreen(navController)
        }

        composable("UserName") {
            UserName(navController)
        }

        composable("TopicSelectScreen") {
            TopicSelectScreen(navController)
        }

        composable("UploadPhotoScreen") {
            UploadPhotoScreen(navController)
        }
        composable("InterestsScreen") {
            InterestsScreen(navController)
        }

        composable(
            route = "uploadSuccess?uri={uri}&emoji={emoji}&avatarId={avatarId}",
            arguments = listOf(
                navArgument("uri") {
                    type = NavType.StringType
                    nullable = true
                },
                navArgument("emoji") {
                    type = NavType.StringType
                    nullable = true
                },
                navArgument("avatarId") {
                    type = NavType.IntType
                    defaultValue = -1 // Use -1 or another invalid ID as default
                }
            )
        ) { backStackEntry ->
            // Extract the arguments
            val imageUriString = backStackEntry.arguments?.getString("uri")
            val imageUri = imageUriString?.let { Uri.parse(it) }

            val emoji = backStackEntry.arguments?.getString("emoji")

            val avatarId = backStackEntry.arguments?.getInt("avatarId")
            val avatarResId = if (avatarId == -1) null else avatarId

            // Call the success screen with the extracted data
            UploadSuccessScreen(
                navController = navController,
                imageUri = imageUri,
                emoji = emoji,
                avatarResId = avatarResId
            )
        }
        composable("getStartedUi") {
            CircleOfLifeScreen(navController)
        }
        composable("terms") {
            TermsScreen(navController)
        }

        composable("SetupAccountScreen") {
            SetupAccountScreen(navController)
        }
        composable("walletSetup") {
            WalletSetupScreen(navController)
        }
        composable("createPassword") {
            CreatePasswordScreen(navController)
        }
        composable("secureWallet") {
            SecureWalletScreen(navController)
        }
        composable("revealSecret") {
            RevealSecretPhraseScreen(navController)
        }
        composable("confirmSecret") {
            ConfirmSecretPhraseScreen(navController)
        }
        composable("success") {
            SuccessScreen(navController)
        }
        //Nav End
    }
}

