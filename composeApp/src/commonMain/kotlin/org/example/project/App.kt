package org.example.project

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import tugas4pam.composeapp.generated.resources.Res
import tugas4pam.composeapp.generated.resources.profile_photo
import org.jetbrains.compose.resources.painterResource

// ═══════════════════════════════════════════════
// DATA CLASS — UI State
// ═══════════════════════════════════════════════

data class ProfileUiState(
    val name: String        = "Sigit Kurnia Hartawan",
    val nim: String         = "123140033",
    val bio: String         = "Mahasiswa Teknik Informatika Institut Teknologi Sumatera yang tertarik mengeksplorasi Mobile Application Development dan Data Mining.",
    val email: String       = "sigit.123140033@student.itera.ac.id",
    val phone: String       = "+62 856 0935 3619",
    val location: String    = "Bandar Lampung, Indonesia",
    val isDarkMode: Boolean      = false,
    val isEditMode: Boolean      = false,
    val showContactInfo: Boolean = false,
    val showSuccessNotif: Boolean = false
)

// ═══════════════════════════════════════════════
// VIEWMODEL
// ═══════════════════════════════════════════════

class ProfileViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    fun toggleDarkMode()    { _uiState.update { it.copy(isDarkMode = !it.isDarkMode) } }
    fun toggleEditMode()    { _uiState.update { it.copy(isEditMode = !it.isEditMode) } }
    fun toggleContactInfo() { _uiState.update { it.copy(showContactInfo = !it.showContactInfo) } }
    fun dismissNotif()      { _uiState.update { it.copy(showSuccessNotif = false) } }

    fun saveAll(
        newName: String, newBio: String,
        newEmail: String, newPhone: String, newLocation: String
    ) {
        _uiState.update {
            it.copy(
                name = newName, bio = newBio,
                email = newEmail, phone = newPhone, location = newLocation,
                isEditMode = false,
                showContactInfo = true,
                showSuccessNotif = true
            )
        }
    }
}

// ═══════════════════════════════════════════════
// WARNA TEMA
// ═══════════════════════════════════════════════

private val PrimaryBlue    = Color(0xFF1E88E5)
private val LightBlue      = Color(0xFFE3F2FD)
private val DarkText       = Color(0xFF1A1A2E)
private val SubText        = Color(0xFF6B7280)
private val BackgroundGray = Color(0xFFF8F9FA)
private val CardWhite      = Color(0xFFFFFFFF)
private val SuccessGreen   = Color(0xFF43A047)

private val DarkBackground = Color(0xFF121212)
private val DarkCard       = Color(0xFF1E1E1E)
private val DarkTextLight  = Color(0xFFE0E0E0)
private val DarkSubText    = Color(0xFF9E9E9E)
private val DarkBlueAccent = Color(0xFF64B5F6)

// ═══════════════════════════════════════════════
// APP — Entry Point
// ═══════════════════════════════════════════════

@Composable
fun App(viewModel: ProfileViewModel = viewModel()) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    // State Hoisting — semua field form
    var editName     by remember(uiState.name)     { mutableStateOf(uiState.name) }
    var editBio      by remember(uiState.bio)      { mutableStateOf(uiState.bio) }
    var editEmail    by remember(uiState.email)    { mutableStateOf(uiState.email) }
    var editPhone    by remember(uiState.phone)    { mutableStateOf(uiState.phone) }
    var editLocation by remember(uiState.location) { mutableStateOf(uiState.location) }

    // Auto-dismiss notifikasi setelah 3 detik
    LaunchedEffect(uiState.showSuccessNotif) {
        if (uiState.showSuccessNotif) {
            delay(3000)
            viewModel.dismissNotif()
        }
    }

    // Warna dinamis
    val backgroundColor = if (uiState.isDarkMode) DarkBackground else BackgroundGray
    val cardColor       = if (uiState.isDarkMode) DarkCard       else CardWhite
    val textColor       = if (uiState.isDarkMode) DarkTextLight  else DarkText
    val subTextColor    = if (uiState.isDarkMode) DarkSubText    else SubText
    val accentColor     = if (uiState.isDarkMode) DarkBlueAccent else PrimaryBlue
    val iconBgColor     = if (uiState.isDarkMode) Color(0xFF1A2A3A) else LightBlue

    MaterialTheme(
        colorScheme = if (uiState.isDarkMode) darkColorScheme() else lightColorScheme()
    ) {
        Box(modifier = Modifier.fillMaxSize().background(backgroundColor)) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp, vertical = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                // ── Dark Mode Toggle ──────────────────────
                DarkModeToggle(
                    isDarkMode  = uiState.isDarkMode,
                    onToggle    = { viewModel.toggleDarkMode() },
                    textColor   = subTextColor,
                    accentColor = accentColor
                )

                Spacer(modifier = Modifier.height(24.dp))

                // ── Header Profil ─────────────────────────
                ProfileHeader(
                    name         = uiState.name,
                    nim          = uiState.nim,
                    bio          = uiState.bio,
                    textColor    = textColor,
                    subTextColor = subTextColor,
                    accentColor  = accentColor,
                    badgeBgColor = iconBgColor
                )

                Spacer(modifier = Modifier.height(20.dp))

                // ── Tombol Tampilkan Kontak ───────────────
                ActionButton(
                    text    = if (uiState.showContactInfo) "✕  Sembunyikan Kontak" else "📋  Tampilkan Kontak",
                    color   = if (uiState.showContactInfo) subTextColor else accentColor,
                    onClick = { viewModel.toggleContactInfo() }
                )

                Spacer(modifier = Modifier.height(10.dp))

                // ── Card Kontak ───────────────────────────
                AnimatedVisibility(
                    visible = uiState.showContactInfo,
                    enter   = fadeIn() + expandVertically(),
                    exit    = fadeOut() + shrinkVertically()
                ) {
                    ProfileCard(title = "Informasi Kontak", cardColor = cardColor, textColor = textColor) {
                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            InfoItem(emoji = "📧", label = "Email",    text = uiState.email,    textColor = textColor, subTextColor = subTextColor, iconBgColor = iconBgColor)
                            InfoDivider()
                            InfoItem(emoji = "📱", label = "Phone",    text = uiState.phone,    textColor = textColor, subTextColor = subTextColor, iconBgColor = iconBgColor)
                            InfoDivider()
                            InfoItem(emoji = "📍", label = "Location", text = uiState.location, textColor = textColor, subTextColor = subTextColor, iconBgColor = iconBgColor)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // ── Form Edit ─────────────────────────────
                AnimatedVisibility(
                    visible = uiState.isEditMode,
                    enter   = fadeIn() + expandVertically(),
                    exit    = fadeOut() + shrinkVertically()
                ) {
                    EditForm(
                        name = editName, bio = editBio,
                        onNameChange = { editName = it },
                        onBioChange  = { editBio  = it },
                        email = editEmail, phone = editPhone, location = editLocation,
                        onEmailChange    = { editEmail    = it },
                        onPhoneChange    = { editPhone    = it },
                        onLocationChange = { editLocation = it },
                        onSave = { viewModel.saveAll(editName, editBio, editEmail, editPhone, editLocation) },
                        cardColor    = cardColor,
                        textColor    = textColor,
                        subTextColor = subTextColor,
                        accentColor  = accentColor
                    )
                }

                Spacer(modifier = Modifier.weight(1f))
                Spacer(modifier = Modifier.height(24.dp))

                // ── Tombol Edit — outlined, tidak mencolok ─
                OutlinedButton(
                    onClick = { viewModel.toggleEditMode() },
                    modifier = Modifier.fillMaxWidth().height(42.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = subTextColor),
                    border = ButtonDefaults.outlinedButtonBorder.copy(
                        width = 1.dp
                    )
                ) {
                    Text(
                        text = if (uiState.isEditMode) "✕  Batal Edit" else "✏️  Edit Profil & Kontak",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        color = subTextColor
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                FooterCopyright(subTextColor = subTextColor)
            }

            // ── Notifikasi Sukses — pill kecil di tengah bawah ──
            AnimatedVisibility(
                visible  = uiState.showSuccessNotif,
                enter    = fadeIn(),
                exit     = fadeOut(),
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 28.dp)
            ) {
                SuccessNotification()
            }
        }
    }
}

// ═══════════════════════════════════════════════
// Composable 1: ProfileHeader
// ═══════════════════════════════════════════════
@Composable
fun ProfileHeader(
    name: String, nim: String, bio: String,
    textColor: Color, subTextColor: Color,
    accentColor: Color, badgeBgColor: Color,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxWidth()
    ) {
        Image(
            painter = painterResource(Res.drawable.profile_photo),
            contentDescription = "Foto Profil",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(110.dp)
                .shadow(elevation = 8.dp, shape = CircleShape)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = name, fontSize = 26.sp, fontWeight = FontWeight.Bold, color = textColor)
        Spacer(modifier = Modifier.height(6.dp))
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .background(badgeBgColor)
                .padding(horizontal = 14.dp, vertical = 4.dp)
        ) {
            Text(text = "NIM: $nim", fontSize = 13.sp, fontWeight = FontWeight.Medium, color = accentColor)
        }
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = bio, fontSize = 14.sp, color = subTextColor,
            textAlign = TextAlign.Center, lineHeight = 22.sp,
            modifier = Modifier.padding(horizontal = 8.dp)
        )
    }
}

// ═══════════════════════════════════════════════
// Composable 2: ProfileCard
// ═══════════════════════════════════════════════
@Composable
fun ProfileCard(
    title: String, cardColor: Color, textColor: Color,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = cardColor)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(text = title, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = textColor)
            Spacer(modifier = Modifier.height(14.dp))
            content()
        }
    }
}

// ═══════════════════════════════════════════════
// Composable 3: InfoItem
// ═══════════════════════════════════════════════
@Composable
fun InfoItem(
    emoji: String, label: String, text: String,
    textColor: Color, subTextColor: Color, iconBgColor: Color,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth().padding(vertical = 8.dp)
    ) {
        Box(
            modifier = Modifier.size(42.dp).clip(CircleShape).background(iconBgColor),
            contentAlignment = Alignment.Center
        ) { Text(text = emoji, fontSize = 20.sp) }
        Spacer(modifier = Modifier.width(14.dp))
        Column {
            Text(text = label, fontSize = 12.sp, color = subTextColor, fontWeight = FontWeight.Medium)
            Text(text = text,  fontSize = 14.sp, color = textColor,    fontWeight = FontWeight.SemiBold)
        }
    }
}

// ═══════════════════════════════════════════════
// Composable 4: InfoDivider
// ═══════════════════════════════════════════════
@Composable
fun InfoDivider() {
    HorizontalDivider(
        modifier = Modifier.padding(start = 56.dp),
        thickness = 0.8.dp,
        color = Color(0xFFE0E0E0)
    )
}

// ═══════════════════════════════════════════════
// Composable 5: FooterCopyright
// ═══════════════════════════════════════════════
@Composable
fun FooterCopyright(subTextColor: Color, modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxWidth().padding(top = 4.dp)
    ) {
        HorizontalDivider(thickness = 0.8.dp, color = Color(0xFFDDE1E7))
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "© 2026 Tugas PAM 4 — 033",
            fontSize = 12.sp, color = subTextColor,
            fontWeight = FontWeight.Medium, textAlign = TextAlign.Center
        )
    }
}

// ═══════════════════════════════════════════════
// Composable 6: DarkModeToggle
// ═══════════════════════════════════════════════
@Composable
fun DarkModeToggle(
    isDarkMode: Boolean, onToggle: () -> Unit,
    textColor: Color, accentColor: Color,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.fillMaxWidth()
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = if (isDarkMode) "🌙" else "☀️", fontSize = 18.sp)
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = if (isDarkMode) "Dark Mode" else "Light Mode",
                fontSize = 14.sp, fontWeight = FontWeight.Medium, color = textColor
            )
        }
        Switch(
            checked = isDarkMode,
            onCheckedChange = { onToggle() },
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = accentColor
            )
        )
    }
}

// ═══════════════════════════════════════════════
// Composable 7: ActionButton (reusable)
// ═══════════════════════════════════════════════
@Composable
fun ActionButton(
    text: String, color: Color, onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier.fillMaxWidth().height(50.dp),
        shape = RoundedCornerShape(14.dp),
        colors = ButtonDefaults.buttonColors(containerColor = color),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
    ) {
        Text(text = text, fontSize = 15.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
    }
}

// ═══════════════════════════════════════════════
// Composable 8: EditForm — Profil + Kontak 1 Card
// State Hoisting: semua TextField stateless
// ═══════════════════════════════════════════════
@Composable
fun EditForm(
    name: String, bio: String,
    onNameChange: (String) -> Unit, onBioChange: (String) -> Unit,
    email: String, phone: String, location: String,
    onEmailChange: (String) -> Unit,
    onPhoneChange: (String) -> Unit,
    onLocationChange: (String) -> Unit,
    onSave: () -> Unit,
    cardColor: Color, textColor: Color,
    subTextColor: Color, accentColor: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = cardColor)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // ── Edit Profil ───────────────────────────
            Text("Edit Profil", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = textColor)

            OutlinedTextField(
                value = name, onValueChange = onNameChange,
                label = { Text("Nama") }, singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = accentColor,
                    focusedLabelColor  = accentColor,
                    cursorColor        = accentColor
                )
            )

            OutlinedTextField(
                value = bio, onValueChange = onBioChange,
                label = { Text("Bio") }, minLines = 3, maxLines = 5,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = accentColor,
                    focusedLabelColor  = accentColor,
                    cursorColor        = accentColor
                )
            )

            // ── Garis Pemisah ─────────────────────────
            HorizontalDivider(
                thickness = 1.dp,
                color = subTextColor.copy(alpha = 0.2f),
                modifier = Modifier.padding(vertical = 2.dp)
            )

            // ── Edit Kontak ───────────────────────────
            Text("Edit Kontak", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = textColor)

            OutlinedTextField(
                value = email, onValueChange = onEmailChange,
                label = { Text("Email") }, singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = accentColor,
                    focusedLabelColor  = accentColor,
                    cursorColor        = accentColor
                )
            )

            OutlinedTextField(
                value = phone, onValueChange = onPhoneChange,
                label = { Text("Phone") }, singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = accentColor,
                    focusedLabelColor  = accentColor,
                    cursorColor        = accentColor
                )
            )

            OutlinedTextField(
                value = location, onValueChange = onLocationChange,
                label = { Text("Location") }, singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = accentColor,
                    focusedLabelColor  = accentColor,
                    cursorColor        = accentColor
                )
            )

            // ── Tombol Simpan ─────────────────────────
            Button(
                onClick = onSave,
                modifier = Modifier.fillMaxWidth().height(48.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = accentColor)
            ) {
                Text("💾  Simpan Semua Perubahan", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
            }
        }
    }
}

// ═══════════════════════════════════════════════
// Composable 9: SuccessNotification — minimal & profesional
// ═══════════════════════════════════════════════
@Composable
fun SuccessNotification(
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.wrapContentWidth(),
        shape = RoundedCornerShape(50.dp),
        color = Color(0xFF2E7D32),
        shadowElevation = 10.dp
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 18.dp, vertical = 11.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(20.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.25f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "✓",
                    fontSize = 11.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    lineHeight = 11.sp,
                    modifier = Modifier.offset(y = (-0.5).dp)
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = "Profil berhasil diperbarui",
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White
            )
        }
    }
}