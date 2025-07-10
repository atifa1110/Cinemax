package com.example.cinemax.screen.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.core.ui.components.ProfileTopAppBar
import com.example.cinemax.ui.theme.BlueAccent
import com.example.cinemax.ui.theme.Dark
import com.example.cinemax.ui.theme.Grey
import com.example.cinemax.ui.theme.Orange
import com.example.cinemax.ui.theme.Soft
import com.example.cinemax.ui.theme.White
import com.example.cinemax.R
import com.example.cinemax.ui.theme.CinemaxTheme

@Composable
fun ProfileRoute(
    onNavigateToAuth : () -> Unit,
    onNavigateToEditProfile : () -> Unit,
    viewModel: ProfileViewModel = hiltViewModel()
){
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ProfileScreen(
        userPhoto = uiState.userId?.photoUrl.toString(),
        userName = uiState.userId?.displayName,
        userEmail = uiState.userId?.email,
        onLogout = viewModel::logout,
        onNavigateToAuth = onNavigateToAuth,
        onNavigateToEditProfile = onNavigateToEditProfile
    )
}

@Composable
fun ProfileScreen(
    userPhoto: String?,
    userName : String?,
    userEmail : String?,
    onLogout : () -> Unit,
    onNavigateToAuth : () -> Unit,
    onNavigateToEditProfile: () -> Unit,
){
    Scaffold (
        topBar = {
            ProfileTopAppBar(title = R.string.profile)
        }
    ){
        ProfileContent(
            userPhoto = userPhoto,
            userName = userName,
            userEmail = userEmail,
            onLogout = onLogout,
            onNavigateToAuth = onNavigateToAuth,
            onNavigateToEditProfile = onNavigateToEditProfile,
            modifier = Modifier.padding(it))
    }
}

@Composable
fun ProfileContent(
    userPhoto :String?,
    userName : String?,
    userEmail : String?,
    onLogout : () -> Unit,
    onNavigateToAuth : () -> Unit,
    onNavigateToEditProfile : () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier
            .fillMaxSize().testTag("ScrollableColumn")
            .verticalScroll(rememberScrollState())
            .background(Dark)
            .padding(vertical = 8.dp, horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        ProfileCard(
            userPhoto = userPhoto,
            userName = userName,
            userEmail = userEmail,
            onNavigateToEditProfile = onNavigateToEditProfile
        )

        PremiumMemberCard()

        AccountSection()

        GeneralSection()

        MoreSection()

        Spacer(modifier = Modifier.height(8.dp))

        LogoutButton(onLogout = onLogout, onNavigateToAuth = onNavigateToAuth)
    }
}

@Composable
fun ProfileCard(
    userPhoto : String?,
    userName : String?,
    userEmail : String?,
    onNavigateToEditProfile: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp, color = Soft,
                shape = RoundedCornerShape(16.dp)
            )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box (modifier = Modifier.size(40.dp)
            ){
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(userPhoto)
                        .crossfade(true)
                        .build(),
                    error = painterResource(id = R.drawable.poster_placeholder),
                    contentDescription = userPhoto,
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(id = R.drawable.poster_placeholder),
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(shape = CircleShape)
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = userName.toString(),
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Text(
                    text = userEmail.toString(),
                    color = Color.Gray,
                    fontSize = 14.sp
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = onNavigateToEditProfile) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_edit),
                    contentDescription = "Edit Profile",
                    tint = Color.Cyan
                )
            }
        }
    }
}

@Composable
fun PremiumMemberCard() {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Orange
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box (modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(White.copy(0.2f)),
                contentAlignment = Alignment.Center
            ){
                Icon(
                    painter = painterResource(id = R.drawable.ic_premium),
                    contentDescription = "Premium Member",
                    tint = White,
                    modifier = Modifier.size(24.dp)
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = "Premium Member",
                    color = White,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(3.dp))
                Text(
                    text = "New movies are coming for you, Download Now!",
                    color = White,
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Composable
fun AccountSection(){
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp, color = Soft,
                shape = RoundedCornerShape(16.dp)
            )
    ) {
        Column(
            modifier = Modifier.padding(start = 16.dp, top= 16.dp, end=16.dp)
        ) {
            Text(
                text = "Account",
                color = White,
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp
            )
            SettingsItem(icon = R.drawable.ic_profile, title = "Member")
            HorizontalDivider(color = Soft.copy(alpha = 0.3f), thickness = 1.dp)
            SettingsItem(icon = R.drawable.ic_password, title = "Change Password")
        }
    }
}

@Composable
fun GeneralSection(){
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp, color = Soft,
                shape = RoundedCornerShape(16.dp)
            )
    ) {
        Column(
            modifier = Modifier.padding(top=16.dp, start = 16.dp,end=16.dp)
        ) {
            Text(
                text = "General",
                color = White,
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp
            )
            SettingsItem(icon = R.drawable.ic_notification, title = "Notification")
            HorizontalDivider(color = Soft, thickness = 1.dp)
            SettingsItem(icon = R.drawable.ic_language, title = "Language")
            HorizontalDivider(color = Soft, thickness = 1.dp)
            SettingsItem(icon = R.drawable.ic_country, title = "Country")
            HorizontalDivider(color = Soft, thickness = 1.dp)
            SettingsItem(icon = R.drawable.ic_cache, title = "Clear Cache")
        }
    }
}

@Composable
fun MoreSection() {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp, color = Soft,
                shape = RoundedCornerShape(16.dp)
            )
    ) {
        Column(
            modifier = Modifier.padding(start = 16.dp, top= 16.dp, end=16.dp)
        ) {
            Text(
                text = "More",
                color = White,
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp
            )
            SettingsItem(icon = R.drawable.ic_legal, title = "Legal and Policies")
            HorizontalDivider(color = Soft, thickness = 1.dp)
            SettingsItem(icon = R.drawable.ic_help, title = "Help & Feedback")
            HorizontalDivider(color = Soft, thickness = 1.dp)
            SettingsItem(icon = R.drawable.ic_alert, title = "About Us")
        }
    }
}

@Composable
fun SettingsItem(icon: Int, title: String) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Box(modifier = Modifier
            .size(32.dp)
            .clip(CircleShape)
            .background(Soft),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = title,
                tint = Grey,
                modifier = Modifier.size(20.dp)
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Text(modifier = Modifier.weight(1f),
            text = title, color = Color.White, fontSize = 16.sp)
        Spacer(modifier = Modifier.width(16.dp))
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
            contentDescription = "Arrow",
            tint = BlueAccent,
            modifier = Modifier.size(24.dp)
        )
    }
}

@Composable
fun LogoutButton(
    onLogout: () -> Unit,
    onNavigateToAuth: () -> Unit
) {
    OutlinedButton(
        onClick = {
            onLogout()
            onNavigateToAuth()
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = White
        ),
        border = BorderStroke(2.dp, BlueAccent),
        modifier = Modifier.testTag("LogoutButton")
            .fillMaxWidth()
            .height(56.dp),
        shape = CircleShape
    ) {
        Text(
            text = "Log Out",
            color = BlueAccent,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewProfileScreen() {
    CinemaxTheme {
        ProfileScreen(
            userPhoto = "",
            userName = "Atifa",
            userEmail = "atifafiorenza@gmail.com",
            onLogout = {},
            onNavigateToAuth = {},
            onNavigateToEditProfile = {}
        )
    }
}
