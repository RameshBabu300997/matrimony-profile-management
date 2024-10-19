package com.map.matrimonytest.screens

import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.gson.Gson
import com.map.matrimonytest.R
import com.map.matrimonytest.db.entity.ProfileEntity
import com.map.matrimonytest.screens.viewmodel.ProfileViewModel

@Composable
fun HomeScreen(navController: NavController, gson: Gson, viewModel: ProfileViewModel) {
    val isLoading by viewModel.loading.observeAsState(true)
    LaunchedEffect(Unit) {
        viewModel.getAllProfiles()
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF3699e0),
                        Color(0xFF0d70b8),
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        if (isLoading) {
            CircularProgressIndicator(color = Color.White)
        } else {
            ProfileScreen(navController, gson, viewModel)
        }
    }
}


@Composable
fun HeaderSection(navController: NavController, gson: Gson, viewModel: ProfileViewModel) {
    val expanded  =  remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            color = Color.White,
            text = stringResource(R.string.my_matches),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        Box {
            IconButton(onClick = { expanded.value = true }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_more),
                    contentDescription = stringResource(R.string.menu_icon),
                    modifier = Modifier.size(24.dp),
                    tint = Color.White
                )
            }

            // Dropdown Menu
            DropdownMenu(
                expanded = expanded.value,
                onDismissRequest = { expanded.value = false },
                modifier = Modifier
                    .offset(y = 4.dp)
            ) {
                DropdownMenuItem(onClick = {
                    navController.navigate("screen2")
                    expanded.value = false // Dismiss menu
                }) {
                    Text(stringResource(R.string.details))
                }
            }
        }
    }
}

// Load all the pending profiles list
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
                    contentDescription = stringResource(R.string.profile_image),
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(260.dp),
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
                        fontWeight = FontWeight.Medium,
                    )
                    Text(
                        text = "${profile.profession}, ${profile.location}",
                        fontSize = 14.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontWeight = FontWeight.Medium,
                    )
                    Text(
                        text = "${profile.state},${profile.country}",
                        fontSize = 14.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontWeight = FontWeight.Medium,
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier
                    .padding(8.dp)
            ) {
                Button(
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(0xFFFFC107),
                    ),
                    onClick = { /* Yes button clicked */ },
                    modifier = Modifier
                        .width(80.dp)
                        .padding(end = 4.dp),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(
                        text = stringResource(R.string.txt_yes),
                        modifier = Modifier.padding(vertical = 2.dp)
                    )
                }

                Button(
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.White,
                    ),
                    onClick = { onNoClick() },
                    modifier = Modifier
                        .width(80.dp)
                        .border(
                            width = 1.dp,
                            color = Color.LightGray,
                            shape = CircleShape
                        ),
                    shape = RoundedCornerShape(24.dp),
                ) {
                    Text(
                        text = stringResource(R.string.txt_no),
                        modifier = Modifier.padding(vertical = 6.dp)
                    )
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
fun NewBadge(count: Int) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .border(
                border = BorderStroke(1.dp, Color.White),
                shape = CircleShape
            )
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(
            text = "$count NEW",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp
        )
    }
}

@Composable
fun ProfileScreen(navController: NavController, gson: Gson, viewModel:ProfileViewModel) {
    val profiles by viewModel.pendingProfiles.observeAsState(emptyList())

    Column( modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        HeaderSection(navController, gson, viewModel)
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "${profiles.size} Profiles pending with me",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(4.dp))
            NewBadge(count = profiles.size-2)
        }
        Spacer(modifier = Modifier.height(12.dp))

        val context = LocalContext.current;

        ProfileList(profiles = profiles, onProfileRemoved = { profileId ->
            Toast.makeText(context, "Profile removed successfully", Toast.LENGTH_SHORT).show()
            viewModel._pendingProfiles.value = profiles.filter { it.id != profileId }
        }, navController, gson)
    }
}
