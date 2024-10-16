package com.map.matrimonytest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.map.matrimonytest.db.entity.ProfileEntity
import com.map.matrimonytest.screens.viewmodel.ProfileViewModel
import com.map.matrimonytest.screens.viewmodel.ProfileViewModelFactory
import com.map.matrimonytest.ui.theme.MatrimonyTestTheme

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val profileDao = AppDatabase.getDatabase(this).profileDao()
        val viewModel: ProfileViewModel by viewModels { ProfileViewModelFactory(profileDao) }
//        val profile = ProfileEntity(
//            id = 3,
//            name ="Divya",
//            age = 25,
//            height = "5ft 1 in",
//            profession = "Business Analyst",
//            location = "Chennai",
//            imageResId = R.drawable.ic_profile_photo3,
//            caste = "Tamil, Nair",
//            state = "Tamil Nadu",
//            country = "India",
//            zodiac = "Leo",
//            imageIds = "${R.drawable.ic_profile_photo3},${R.drawable.ic_profile_photo6}"
//        )
//        viewModel.addProfile(profile);

//        viewModel.deleteProfile(5);

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