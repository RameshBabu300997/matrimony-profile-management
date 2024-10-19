package com.map.matrimonytest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.map.matrimonytest.screens.viewmodel.ProfileViewModel
import com.map.matrimonytest.screens.viewmodel.ProfileViewModelFactory
import com.map.matrimonytest.ui.theme.MatrimonyTestTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val profileDao = AppDatabase.getDatabase(this).profileDao()
        val viewModel: ProfileViewModel by viewModels { ProfileViewModelFactory(profileDao) }
        setContent {
            MatrimonyTestTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    AppNavigation(viewModel)
                }
            }
        }
    }
}