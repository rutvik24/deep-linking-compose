package com.atliq.deeplinkingcompose

import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.atliq.deeplinkingcompose.ui.theme.DeepLinkingComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DeepLinkingComposeTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "home") {
                    composable("home") {
                        Box(
                            modifier = Modifier
                                .fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Button(onClick = {
                                navController.navigate("detail")
                            }) {
                                Text(text = "To Detail")
                            }
                        }
                    }
                    composable(
                        route = "detail",
                        deepLinks = listOf(
                            navDeepLink {
                                uriPattern = "https://rutviknabhoya.me/{id}"
                                action = Intent.ACTION_VIEW
                            }
                        ),
                        arguments = listOf(
                            navArgument("id") {
                                type = NavType.IntType
                                defaultValue = -1
                            }
                        )
                    ) { entry ->
                        val id = entry.arguments?.getInt("id")
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "The id is $id")
                        }
                    }
                }
                // add below code in other app for deep linking open app from other app
//                @Composable
//                fun App() {
//                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
//                        Button(onClick = {
//                            val intent = Intent(
//                                Intent.ACTION_VIEW,
//                                Uri.parse("https://rutviknabhoya.me/1675")
//                            )
//                            val pendingIntent = TaskStackBuilder.create(applicationContext).run {
//                                addNextIntentWithParentStack(intent)
//                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                                    getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
//                                } else {
//                                    getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
//                                }
//                            }
//                            pendingIntent.send()
//                        }) {
//                              Text(text = "Trigger Deep Link")
//                        }
//                    }
//                }
            }
        }
    }
}

