package com.map.matrimonytest.screens

import android.widget.Toast
import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.Card
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.map.matrimonytest.R
import com.map.matrimonytest.db.entity.ProfileEntity
import com.map.matrimonytest.screens.viewmodel.ProfileViewModel

@Composable
fun ProfileGestureScreen(navController: NavController, viewModel: ProfileViewModel) {
    Column {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            // Background with partial green and partial white
            Column(modifier = Modifier.fillMaxSize()) {
                // Green top section with Back Arrow and Title
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp) // Green top section
                        .background(Color(0xFF4CAF50))
                ) {
                    // Top Row for Back Arrow and Title
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Back Arrow
                        Image(
                            painter = painterResource(id = R.drawable.ic_baseline_arrow_back), // Replace with your back arrow drawable
                            contentDescription = "Back",
                            modifier = Modifier
                                .size(24.dp)
                                .clickable {
                                    navController.popBackStack()
                                }
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        // Title
                        Text(
                            text = "Daily Recommendations",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White) // White bottom section
                )
            }

            // The CardStack will be placed on top, overlapping both green and white sections
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.TopCenter
            ) {
                CardStack(viewModel) // Card stack content
            }
        }
    }
}

@Composable
fun CardStack(viewModel: ProfileViewModel) {
    var profiledetails = viewModel.allProfiles.value

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        // Iterate and draw the cards in reverse order to simulate stack

        if (!profiledetails.isNullOrEmpty()) {
            for (i in profiledetails.indices.reversed()) {
                val context = LocalContext.current
                DraggableCard(
                    card = profiledetails[i],
                    cardIndex = i,
                    onShortlistClick = {
                        Toast.makeText(context, "Profile Removed", Toast.LENGTH_SHORT).show()

                    },
                    modifier = Modifier
                        .offset(y = (-i * 20).dp)  // Apply negative offset to stack from top down
                        .scale(1f - i * 0.05f)
                )
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun DraggableCard(card: ProfileEntity, cardIndex: Int, onShortlistClick: () -> Unit, modifier: Modifier = Modifier) {
    val swipeThreshold = with(LocalDensity.current) { 150.dp.toPx() }
    val offsetX = remember { Animatable(0f) }
    val offsetY = remember { Animatable(0f) }
    val rotation = remember { Animatable(0f) }
    val coroutineScope = rememberCoroutineScope()

    var isDismissed by remember { mutableStateOf(false) }

    if (!isDismissed) {
        Box(
            modifier = modifier
                .pointerInput(Unit) {
                    detectTapGestures(
                        onTap = {
                            // Handle tap if needed, else just do nothing
                        }
                    )
                },
            contentAlignment = Alignment.Center
        ) {
            CardView(card, onShortlistClick)
        }
    }
}

@Composable
fun CardView(card: ProfileEntity, onShortlistClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .width(350.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp) // Add bottom padding
        ) {
            // Profile Image
            Image(
                painter = painterResource(id = card.imageResId), // Replace with actual image resource
                contentDescription = "Profile Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            )

            // Verified and Premium Icons
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Verified indicator
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Verified",
                        tint = Color.Green
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Verified", fontSize = 12.sp)
                }

                // Space between Verified and Premium
                Spacer(modifier = Modifier.width(16.dp))

                // Premium indicator
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Premium",
                        tint = Color(0xFFFFD700) // Gold color
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Premium", fontSize = 12.sp)
                }
            }

            // Basic Info
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(text = card.name, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text(text = "27 years, 5'5\"", fontSize = 14.sp)
                Text(text = "Software Engineer", fontSize = 14.sp)
                Text(text = "Mumbai, Maharashtra", fontSize = 14.sp)
            }

            // Divider
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                color = Color.LightGray
            )

            // Updated row with shortlist and action buttons
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Shortlist button
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable { }
                ) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Shortlist",
                        tint = Color.Gray
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Shortlist", color = Color.Gray)
                }

                // Like her? text and action buttons
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Like her?", color = Color.Gray, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.width(8.dp)) // Reduced space

                    // Action buttons
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp) // Reduced space between icons
                    ) {
                        // Cancel button
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .background(Color.Red, CircleShape)
                                .clickable { onShortlistClick() },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Cancel",
                                tint = Color.White,
                                modifier = Modifier.size(20.dp)
                            )
                        }

                        // Tick button
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .background(Color.Green, CircleShape)
                                .clickable { onShortlistClick() },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = "Accept",
                                tint = Color.White,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}


data class CardModel(val name: String, val details: String, val imageResId:Int)