package com.map.matrimonytest.screens

import android.widget.Toast
import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.Card
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.map.matrimonytest.R
import com.map.matrimonytest.db.entity.ProfileEntity
import com.map.matrimonytest.screens.viewmodel.ProfileViewModel
import kotlinx.coroutines.launch
import java.lang.Math.abs

@Composable
fun ProfileGestureScreen(navController: NavController, viewModel: ProfileViewModel) {
    LaunchedEffect(Unit) {
        viewModel.getDailyRecommendations()
    }
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        elevation = 8.dp
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color(0xFF49e680),
                                    Color(0xFF25c4aa),
                                )
                            )
                        )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_baseline_arrow_back),
                            contentDescription = stringResource(R.string.txt_back),
                            modifier = Modifier
                                .size(24.dp)
                                .clickable {
                                    navController.popBackStack()
                                }
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        // Title
                        Text(
                            text = stringResource(R.string.txt_daily_recommendation),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFFF3F3F3))
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.TopCenter
            ) {
                CardStack(viewModel)
            }
        }
    }
}

@Composable
fun CardStack(viewModel: ProfileViewModel) {
    val profileDetails: List<ProfileEntity> by viewModel.dailyRecommendations.observeAsState(emptyList())

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (!profileDetails.isNullOrEmpty()) {
            for (i in profileDetails.indices.reversed()) {
                val context = LocalContext.current
                DraggableCard(
                    card = profileDetails[i],
                    onShortlistClick = { profileId ->
                        Toast.makeText(context, "Profile removed successfully", Toast.LENGTH_SHORT).show()
                        viewModel.removeProfile(profileId)
                    },
                    onCardSwiped = {
                        Toast.makeText(context, "Profile removed successfully", Toast.LENGTH_SHORT).show()
                    },
                    modifier = Modifier
                        .offset(y = (-i * 20).dp)
                        .scale(1f - i * 0.05f)
                )
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun DraggableCard(
    card: ProfileEntity,
    onShortlistClick: (Int) -> Unit,
    onCardSwiped: () -> Unit,
    modifier: Modifier = Modifier
) {
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
                    detectDragGestures(
                        onDragEnd = {
                            if (abs(offsetX.value) > swipeThreshold) {
                                coroutineScope.launch {
                                    offsetX.animateTo(
                                        if (offsetX.value > 0) swipeThreshold * 2 else -swipeThreshold * 2
                                    )
                                    isDismissed = true
                                    onCardSwiped()
                                }
                            } else {
                                coroutineScope.launch {
                                    offsetX.animateTo(0f)
                                    offsetY.animateTo(0f)
                                    rotation.animateTo(0f)
                                }
                            }
                        },
                        onDrag = { change, dragAmount ->
                            change.consume() // Consume the gesture event
                            coroutineScope.launch {
                                offsetX.snapTo(offsetX.value + dragAmount.x)
                                offsetY.snapTo(offsetY.value + dragAmount.y)
                                rotation.snapTo((offsetX.value / swipeThreshold) * 15f) // Rotation effect
                            }
                        }
                    )
                }
                .graphicsLayer(
                    translationX = offsetX.value,
                    translationY = offsetY.value,
                    rotationZ = rotation.value,
                ),
            contentAlignment = Alignment.Center
        ) {
            CardView(card, onShortlistClick, card.id)
        }
    }
}
@Composable
fun CardView(profile: ProfileEntity, onShortlistClick: (Int) -> Unit, profileId:Int) {
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .width(320.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            Image(
                painter = painterResource(id = profile.imageResId),
                contentDescription = stringResource(id = R.string.profile_image),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_verified_icon),
                        contentDescription = stringResource(R.string.txt_verified),
                        tint = Color.Blue
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        stringResource(R.string.txt_verified),
                        fontSize = 12.sp,
                        color = Color.Blue)
                }
                // Space between Verified and Premium
                Spacer(modifier = Modifier.width(4.dp))
                // Premium indicator
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_premium_icon),
                        contentDescription = stringResource(R.string.txt_premium),
                        tint = Color(0xFFB87DF0)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(stringResource(R.string.txt_premium), fontSize = 12.sp, color = Color(0xFFB87DF0) )
                }
            }
            Column(modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)) {
                Text(
                    text = profile.name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "${profile.age} Yrs, ${profile.height}, ${profile.profession}",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                )
                Text(
                    text = "${profile.caste}, ${profile.location}",
                    fontSize = 14.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.Medium,
                )
                Text(
                    text = profile.state,
                    fontSize = 14.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.Medium,
                )
            }
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                color = Color.LightGray
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable { }
                ) {
                    Icon(
                        painterResource(id = R.drawable.ic_shortlist_icon),
                        contentDescription = stringResource(R.string.txt_shortlist),
                        tint = Color.Gray,
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(stringResource(R.string.txt_shortlist), color = Color.Black, fontSize = 12.sp)
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(stringResource(R.string.txt_like), color = Color.Black, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.width(8.dp)) // Reduced space
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp) // Reduced space between icons
                    ) {
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .background(Color.White, CircleShape)
                                .clickable { onShortlistClick(profileId) },
                            contentAlignment = Alignment.Center
                        ) {
                            Canvas(modifier = Modifier.size(32.dp)) {
                                drawCircle(
                                    color = Color.Gray,
                                    radius = size.minDimension / 2,
                                    style = Stroke(width = 1.dp.toPx())
                                )
                            }
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = stringResource(R.string.txt_cancel),
                                tint = Color.Gray,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Box(
                            modifier = Modifier
                                .width(50.dp)
                                .height(32.dp)
                                .background(Color(0xFFFFC107), CircleShape)
                                .clickable { onShortlistClick(profileId) },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = stringResource(R.string.txt_accept),
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
