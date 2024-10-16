package com.map.matrimonytest.screens

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.google.gson.Gson
import com.map.matrimonytest.R
import com.map.matrimonytest.db.entity.ProfileEntity
import com.map.matrimonytest.screens.viewmodel.ProfileViewModel

@Composable
fun HomeScreen(navController: NavController, gson: Gson, viewModel: ProfileViewModel) {
    LaunchedEffect(Unit) {
        viewModel.getAllProfiles()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF87CEEB))
    ) {
        ProfileScreen(navController, gson, viewModel)
    }
}


@Composable
fun HeaderSection(navController: NavController, gson: Gson, viewModel: ProfileViewModel) {
    val expanded  =  remember { mutableStateOf(false) } // State to control dropdown visibility
    val profiles by viewModel.allProfiles.observeAsState(emptyList())

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            color = Color.White,
            text = "My Matches",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        // Use Box to position dropdown relative to the icon
        Box {
            IconButton(onClick = { expanded.value = true }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_more), // Replace with your menu icon resource
                    contentDescription = "Menu Icon",
                    modifier = Modifier.size(24.dp),
                    tint = Color.White
                )
            }

            // Dropdown Menu
            DropdownMenu(
                expanded = expanded.value,
                onDismissRequest = { expanded.value = false },
                modifier = Modifier
                    .offset(y = 8.dp) // Offset to position it below the button
            ) {
                DropdownMenuItem(onClick = {
                    val profileJson = gson.toJson(profiles)
                    navController.navigate("screen2") // Replace with your destination
                    expanded.value = false // Dismiss menu
                }) {
                    Text("Details")
                }
            }
        }
    }
}


@Composable
fun ProfileCard(profile: ProfileEntity, onNoClick:
    () -> Unit, navController: NavController, gson: Gson) {
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = 8.dp,
        modifier = Modifier
            .width(250.dp)
            .padding(8.dp)
    ) {
        Column() {
            Column(
                modifier = Modifier.clickable{
                    val profileJson = gson.toJson(profile)
                    navController.navigate("screen3/$profileJson")
                }
            ) {
                Image(
                    painter = painterResource(profile.imageResId),
                    contentDescription = "Profile Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxWidth()
                        .height(300.dp),
                )
                Spacer(modifier = Modifier.height(8.dp))
                Column(modifier = Modifier.padding(8.dp)) {
                    Text(
                        text = profile.name,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = "${profile.age} Yrs, ${profile.height}, ${profile.caste}",
                        fontSize = 14.sp,
                    )
                    Text(
                        text = "${profile.profession}, ${profile.location}",
                        fontSize = 14.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = "${profile.state},${profile.country}",
                        fontSize = 14.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Button(
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Yellow,
                    ),
                    onClick = { /* Yes button clicked */ },
                    modifier = Modifier
                        .width(50.dp)
                        .weight(1f)
                        .padding(end = 4.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Yes")
                }

                Button(
                    onClick = { onNoClick() },
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 4.dp)
                ) {
                    Text("No")
                }
            }
        }
    }
}

@Composable
fun ProfileList(profiles: List<ProfileEntity>, onProfileRemoved: (Int) -> Unit,
                navController: NavController, gson: Gson) {
    LazyRow(
        modifier = Modifier.fillMaxWidth()
    ) {
        items(profiles) { profile ->
            ProfileCard(profile = profile, onNoClick = { onProfileRemoved(profile.id) },
                navController, gson)
        }
    }
}

@Composable
fun NotificationBadge(count: Int) {
    Box(
        modifier = Modifier
            .size(40.dp) // Adjust the size as needed
            .background(Color.Red, shape = RoundedCornerShape(12.dp)), // Rounded badge
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "${count.toString()} New",
            color = Color.White,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )
    }
}


@Composable
fun ProfileScreen(navController: NavController, gson: Gson, viewModel:ProfileViewModel) {
    val profiles by viewModel.allProfiles.observeAsState(emptyList())

    Column( modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        HeaderSection(navController, gson, viewModel)
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "${profiles.size} Profiles pending with me",
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        // Add the header at the top of the screen
        Spacer(modifier = Modifier.height(8.dp))

        val context = LocalContext.current;

        ProfileList(profiles = profiles, onProfileRemoved = { profileId ->
            Toast.makeText(context, "Profile removed", Toast.LENGTH_SHORT).show()
            viewModel._allProfiles.value = profiles.filter { it.id != profileId }
        }, navController, gson)
    }
}
