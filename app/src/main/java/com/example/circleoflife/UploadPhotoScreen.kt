package com.example.circleoflife // Make sure this matches your package name

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import kotlinx.coroutines.launch

enum class PhotoOption { CHOOSE_IMAGE, USE_EMOJI, AVATAR, NONE }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UploadPhotoScreen(navController: NavController) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }

    // --- MODIFY: Add state for all possible profile picture types ---
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var selectedEmoji by remember { mutableStateOf<String?>(null) }
    @DrawableRes var selectedAvatarResId by remember { mutableStateOf<Int?>(null) }

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            if (uri != null) {
                imageUri = uri
                selectedEmoji = null
                selectedAvatarResId = null
            }
        }
    )

    var selectedOptionInSheet by remember { mutableStateOf(PhotoOption.NONE) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Setup Account", fontWeight = FontWeight.Bold) },
                navigationIcon = { IconButton(onClick = { /* ... */ }) { Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back") } },
                actions = { TextButton(onClick = { /* ... */ }) { Text("Skip") } },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        },
        containerColor = Color.White
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    // --- REPLACED WITH OPTION 1 ---
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFFEADDFF),
                            Color(0xFFFEF4D5),
                            Color.White
                        ),
                        startY = 0f,
                        endY = 3000f
                    )
                )
        ){
            Column(
                modifier = Modifier.fillMaxSize().padding(paddingValues).padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                com.example.circleoflife.ui.theme.ProgressIndicator(currentStep = 3, totalSteps = 4)
                Spacer(modifier = Modifier.height(32.dp))
                Text("Upload Photo", fontSize = 32.sp, fontWeight = FontWeight.Bold, modifier = Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.height(16.dp))
                Text("Enhance your profile...", fontSize = 16.sp, color = Color.Gray, modifier = Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.height(64.dp))

                Box(contentAlignment = Alignment.BottomEnd) {
                    // --- MODIFY: Display the correct profile picture type ---
                    when {
                        imageUri != null -> {
                            AsyncImage(model = imageUri, contentDescription = "Profile Photo", modifier = Modifier.size(150.dp).clip(CircleShape), contentScale = ContentScale.Crop)
                        }
                        selectedEmoji != null -> {
                            Box(modifier = Modifier.size(150.dp).background(Color.LightGray.copy(alpha = 0.3f), CircleShape), contentAlignment = Alignment.Center) {
                                Text(text = selectedEmoji!!, fontSize = 80.sp)
                            }
                        }
                        selectedAvatarResId != null -> {
                            Image(painter = painterResource(id = selectedAvatarResId!!), contentDescription = "Avatar", modifier = Modifier.size(150.dp).clip(CircleShape), contentScale = ContentScale.Crop)
                        }
                        else -> {
                            Icon(Icons.Default.Person, "Placeholder", tint = Color.Gray, modifier = Modifier.size(150.dp).background(Color.LightGray.copy(alpha = 0.3f), CircleShape).border(2.dp, Color.LightGray.copy(alpha = 0.5f), CircleShape).padding(24.dp))
                        }
                    }

                    IconButton(onClick = { showBottomSheet = true }, modifier = Modifier.size(48.dp).background(Color.Black, CircleShape)) {
                        Icon(Icons.Default.Add, "Upload Photo", tint = Color.White)
                    }
                }

                Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = {
                        // --- THIS IS THE NEW NAVIGATION LOGIC ---
                        val route = "uploadSuccess"
                        // Pass the arguments based on what is selected
                        when {
                            imageUri != null -> {
                                // Important: Encode the URI string to make it safe for navigation
                                navController.navigate("$route?uri=${Uri.encode(imageUri.toString())}")
                            }
                            selectedEmoji != null -> {
                                navController.navigate("$route?emoji=$selectedEmoji")
                            }
                            selectedAvatarResId != null -> {
                                navController.navigate("$route?avatarId=$selectedAvatarResId")
                            }
                        }
                    },
                    enabled = imageUri != null || selectedEmoji != null || selectedAvatarResId != null,
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Black, disabledContainerColor = Color.DarkGray)
                ) {
                    Text("Continue", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.height(32.dp))
            }

            if (showBottomSheet) {
                ModalBottomSheet(onDismissRequest = { showBottomSheet = false }, sheetState = sheetState) {
                    Column(modifier = Modifier.fillMaxWidth().padding(bottom = 32.dp, top = 16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        Box(modifier = Modifier.width(40.dp).height(4.dp).background(Color.LightGray, CircleShape))
                        Spacer(modifier = Modifier.height(24.dp))

                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                            PhotoSheetOption(icon = Icons.Default.Image, text = "Choose Image", isSelected = selectedOptionInSheet == PhotoOption.CHOOSE_IMAGE,
                                onClick = {
                                    selectedOptionInSheet = PhotoOption.CHOOSE_IMAGE
                                    photoPickerLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                                    scope.launch { sheetState.hide() }.invokeOnCompletion { if (!sheetState.isVisible) showBottomSheet = false }
                                }
                            )
                            PhotoSheetOption(icon = Icons.Default.EmojiEmotions, text = "Use Emoji", isSelected = selectedOptionInSheet == PhotoOption.USE_EMOJI,
                                onClick = { selectedOptionInSheet = PhotoOption.USE_EMOJI }
                            )
                            PhotoSheetOption(painterResId = R.drawable.female_avatar, text = "Avatar", isSelected = selectedOptionInSheet == PhotoOption.AVATAR,
                                onClick = { selectedOptionInSheet = PhotoOption.AVATAR }
                            )
                        }

                        // --- ADD THIS: Dynamic grid content area ---
                        Spacer(modifier = Modifier.height(16.dp))
                        when (selectedOptionInSheet) {
                            PhotoOption.USE_EMOJI -> EmojiGrid { emoji ->
                                selectedEmoji = emoji
                                imageUri = null
                                selectedAvatarResId = null
                                scope.launch { sheetState.hide() }.invokeOnCompletion { if (!sheetState.isVisible) showBottomSheet = false }
                            }
                            PhotoOption.AVATAR -> AvatarGrid { avatarResId ->
                                selectedAvatarResId = avatarResId
                                imageUri = null
                                selectedEmoji = null
                                scope.launch { sheetState.hide() }.invokeOnCompletion { if (!sheetState.isVisible) showBottomSheet = false }
                            }
                            else -> Spacer(modifier = Modifier.height(1.dp)) // Empty space for other options
                        }
                    }
                }
            }
        }
    }
}

// --- ADD THIS: Composable for the Emoji Grid ---
@Composable
fun EmojiGrid(onEmojiClick: (String) -> Unit) {
    val emojis = listOf(
        "😀", "😁", "😂", "😃", "😄", "😅", "😆", "😇", "😍", "😘",
        "😗", "🥲", "😋", "😛", "😜", "🤪", "🤨", "🧐", "🤓", "😎",
        "🤩", "🥳", "🤠", "🤢", "🤮", "🤧", "🥵", "🥶", "🥴", "😵"
    )
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 60.dp),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 24.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(emojis) { emoji ->
            Text(
                text = emoji,
                fontSize = 40.sp,
                modifier = Modifier.clickable { onEmojiClick(emoji) }
            )
        }
    }
}

// --- ADD THIS: Composable for the Avatar Grid ---
@Composable
fun AvatarGrid(onAvatarClick: (Int) -> Unit) {
    // IMPORTANT: Make sure you have these drawables in your res/drawable folder
    val avatars = listOf(
        R.drawable.avatar1, R.drawable.avatar1, R.drawable.avatar1, R.drawable.avatar1,
        R.drawable.avatar1, R.drawable.avatar1, R.drawable.avatar1, R.drawable.avatar1,
        R.drawable.avatar1, R.drawable.avatar1, R.drawable.avatar1, R.drawable.avatar1
    )
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 80.dp),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 24.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(avatars) { avatarResId ->
            Image(
                painter = painterResource(id = avatarResId),
                contentDescription = "Avatar",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .clickable { onAvatarClick(avatarResId) }
            )
        }
    }
}

@Composable
fun PhotoSheetOption(
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    @DrawableRes painterResId: Int? = null,
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier.clip(RoundedCornerShape(12.dp)).clickable(onClick = onClick).padding(12.dp)) {
        Box(contentAlignment = Alignment.TopEnd) {
            Box(modifier = Modifier.size(64.dp).background(Color.LightGray.copy(alpha = 0.2f), CircleShape), contentAlignment = Alignment.Center) {
                if (icon != null) Icon(icon, text, modifier = Modifier.size(32.dp), tint = Color.DarkGray)
                if (painterResId != null) Image(painterResource(painterResId), text, modifier = Modifier.size(64.dp).clip(CircleShape))
            }
            if (isSelected) Icon(Icons.Default.CheckCircle, "Selected", tint = Color(0xFF6A5AE0), modifier = Modifier.size(24.dp).background(Color.White, CircleShape))
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = text, fontWeight = FontWeight.SemiBold, color = Color.DarkGray)
    }
}