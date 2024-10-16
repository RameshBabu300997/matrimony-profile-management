package com.map.matrimonytest.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.map.matrimonytest.R
import com.map.matrimonytest.db.entity.ProfileEntity

data class ProfileModel(
    val name: String,
    val age: Int,
    val profession: String,
    val location: String,
    val images: List<Int>,
)

@SuppressLint("UnrememberedMutableState")
@Composable
fun ProfileDetailScreen(navController: NavController, profile: ProfileEntity) {
//    val sampleProfile = ProfileModel(
//        name = "Pragati",
//        age = 27,
//        profession = "Software Engineer",
//        location = "Mumbai, Maharashtra",
//        images = mutableStateListOf(
//            R.drawable.ic_launcher_background,
//            R.drawable.ic_launcher_background,
//            R.drawable.ic_launcher_background
//        )
//    )
    ProfileDetail(profile = profile, navController)
}

@Composable
fun ProfileInfoCard(profile: ProfileEntity, modifier: Modifier = Modifier) {
    Card(
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Row 1: Name and age
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("${profile.name}, ${profile.age}", fontWeight = FontWeight.Bold, fontSize = 24.sp)
            }
            Spacer(modifier = Modifier.height(8.dp))

            // Row 2: Profession
            Text(profile.profession, fontSize = 16.sp)
            Spacer(modifier = Modifier.height(8.dp))

            // Row 3: Location
            Text(profile.location, fontSize = 16.sp)

            // Divider with equal padding
            Spacer(modifier = Modifier.height(16.dp))
            Divider(
                modifier = Modifier.fillMaxWidth(),
                color = Color.LightGray,
                thickness = 1.dp
            )
            Spacer(modifier = Modifier.height(16.dp))

            // You can add more content below the divider if needed
            // For example:
            // Text("Additional information", fontSize = 14.sp)
        }
    }
}

@OptIn(ExperimentalPagerApi::class, ExperimentalPagerApi::class)
@Composable
fun ProfileDetail(profile: ProfileEntity, navController: NavController) {
    val pagerState = rememberPagerState(initialPage = 0)
    val images = profile.imageIds.split(",").map { it.toInt() }

    Box(modifier = Modifier.fillMaxSize()) {
        // Image carousel
        HorizontalPager(
            count = images.size,
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            Image(
                painter = painterResource(id = images[page]),
                contentDescription = "Profile Image ${page + 1}",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
        // Back arrow and title
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .align(Alignment.TopStart),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = Color.White,
                modifier = Modifier.clickable {
                    navController.popBackStack()
                }
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text("Profile", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        ) {
            // Pager indicator
            if (profile.imageResId > 1) {
                HorizontalPagerIndicator(
                    pagerState = pagerState,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(bottom = 16.dp),
                    activeColor = Color.White,
                    inactiveColor = Color.Gray.copy(alpha = 0.5f)
                )
            }
            // Profile info card
            ProfileInfoCard(
                profile = profile,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
