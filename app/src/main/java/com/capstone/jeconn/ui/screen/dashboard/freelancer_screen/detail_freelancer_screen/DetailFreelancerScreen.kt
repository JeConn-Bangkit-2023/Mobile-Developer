package com.capstone.jeconn.ui.screen.dashboard.freelancer_screen.detail_freelancer_screen

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.capstone.jeconn.R
import com.capstone.jeconn.component.CustomDialogBoxLoading
import com.capstone.jeconn.component.CustomFlatIconButton
import com.capstone.jeconn.component.CustomLabel
import com.capstone.jeconn.component.CustomNavbar
import com.capstone.jeconn.component.CustomRoundedIcon
import com.capstone.jeconn.component.Font
import com.capstone.jeconn.component.OpenImageDialog
import com.capstone.jeconn.data.dummy.DummyData
import com.capstone.jeconn.data.entities.LocationEntity
import com.capstone.jeconn.data.entities.PublicDataEntity
import com.capstone.jeconn.di.Injection
import com.capstone.jeconn.navigation.NavRoute
import com.capstone.jeconn.state.UiState
import com.capstone.jeconn.utils.CropToSquareImage
import com.capstone.jeconn.utils.FreelancerViewModelFactory
import com.capstone.jeconn.utils.MakeToast
import com.capstone.jeconn.utils.calculateDistance
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DetailFreelancerScreen(
    navHostController: NavHostController,
    username: String?
) {
    val context = LocalContext.current
    val auth = Firebase.auth.currentUser!!
    val showDialogStateProfileImage = rememberSaveable { mutableStateOf(false) }

    val showDialogStatePostImage = rememberSaveable { mutableStateOf(false) }

    val freelancerState = remember {
        mutableStateOf(PublicDataEntity())
    }

    val postImageUrl = rememberSaveable {
        mutableStateOf("")
    }

    val isLoading = rememberSaveable {
        mutableStateOf(false)
    }

    if (isLoading.value) {
        CustomDialogBoxLoading()
    }

    val freelancerViewModel: DetailFreelancerViewModel = remember {
        FreelancerViewModelFactory(
            Injection.provideFreelancerRepository(
                context = context,
            ),
            chatRepository = Injection.provideChatRepository(context),
            username = username ?: "fauzanramadhani06"
        ).create(
            DetailFreelancerViewModel::class.java
        )
    }

    val isSuccess = remember {
        mutableStateOf(false)
    }

    val isError = remember {
        mutableStateOf(false)
    }

    if (isError.value) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            CustomFlatIconButton(
                icon = Icons.Default.Refresh, label = context.getString(R.string.refresh)
            ) {
                freelancerViewModel.loadFreelancer(username ?: "")
            }
        }
    }

    val loadFreelancerState by rememberUpdatedState(newValue = freelancerViewModel.loadFreelancerState.value)


    LaunchedEffect(loadFreelancerState) {
        when (val currentState = loadFreelancerState) {
            is UiState.Loading -> {
                isLoading.value = true
                isError.value = false
            }

            is UiState.Success -> {
                isLoading.value = false
                isError.value = false
                freelancerState.value = currentState.data
            }

            is UiState.Error -> {
                isLoading.value = false
                isError.value = true
                MakeToast.short(context, currentState.errorMessage)
            }

            else -> {
                //Nothing
                isError.value = false
                isLoading.value = false
            }
        }
    }

    val openChatState by rememberUpdatedState(newValue = freelancerViewModel.openChatState.value)

    when (val currentState = openChatState) {
        is UiState.Loading -> {
            isLoading.value = true
        }

        is UiState.Success -> {
            isLoading.value = false
            isSuccess.value = true

            LaunchedEffect(isSuccess) {
                if (isSuccess.value) {
                    navHostController.navigate(
                        NavRoute.DetailMessageScreen.navigateWithId(
                            currentState.data.toString(),
                            freelancerState.value.full_name ?: "p",
                            Uri.encode(freelancerState.value.profile_image_url ?: "p")
                        )
                    ) {
                        launchSingleTop = true
                    }
                }
            }
        }

        is UiState.Error -> {
            MakeToast.short(context, currentState.errorMessage)
            isLoading.value = false
        }

        else -> {
            //Nothing
            isLoading.value = false
        }
    }

    Scaffold(
        topBar = {
            CustomNavbar(padding = 0.dp) {

                IconButton(

                    onClick = { navHostController.popBackStack() }
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = null,
                        modifier = Modifier
                            .size(28.dp)
                    )
                }

                Spacer(modifier = Modifier.padding(horizontal = 4.dp))

                Text(
                    text = context.getString(R.string.detail_freelance),
                    style = TextStyle(
                        fontSize = 24.sp,
                        fontFamily = Font.QuickSand,
                        fontWeight = FontWeight.Bold,
                    ),
                )
            }
        }
    ) { paddingValues ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            contentPadding = PaddingValues(
                vertical = paddingValues.calculateTopPadding() + 12.dp, horizontal = 12.dp
            ),
            horizontalArrangement = Arrangement.spacedBy(2.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp),
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .fillMaxSize()
        ) {
            item(
                span = {
                    GridItemSpan(3)
                }
            ) {
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically

                    ) {
                        Box {
                            //Freelancer Pcture
                            CropToSquareImage(
                                imageUrl = freelancerState.value.profile_image_url ?: "",
                                contentDescription = null,
                                modifier = Modifier
                                    .size(80.dp)
                                    .clip(CircleShape)
                                    .clickable {
                                        showDialogStateProfileImage.value = true
                                    }
                            )
                            OpenImageDialog(
                                showDialogState = showDialogStateProfileImage,
                                imageUrl = freelancerState.value.profile_image_url ?: ""
                            )
                        }

                        Spacer(modifier = Modifier.padding(8.dp))

                        Column {
                            Text(
                                text = freelancerState.value.full_name ?: "",
                                style = TextStyle(
                                    fontFamily = Font.QuickSand,
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                            )

                            Text(
                                text = calculateDistance(
                                    freelancerState.value.jobInformation?.location
                                        ?: LocationEntity(
                                            51.5074,
                                            -0.1278
                                        ),
                                    freelancerState.value.myLocation ?: LocationEntity(
                                        51.5074,
                                        -0.1278
                                    )
                                ),
                                style = TextStyle(
                                    fontFamily = Font.QuickSand,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Normal,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                            )
                        }

                        Spacer(modifier = Modifier.weight(1f))

                        if (auth.displayName != freelancerState.value.username) {

                            IconButton(
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .size(48.dp)
                                    .background(MaterialTheme.colorScheme.secondary),
                                onClick = {
                                    freelancerViewModel.openChat(
                                        auth.displayName ?: "",
                                        freelancerState.value.username ?: "fauzanramadhani06"
                                    )
                                },
                                ) {
                                Icon(
                                    painterResource(id = R.drawable.ic_message),
                                    tint = MaterialTheme.colorScheme.background,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(32.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.padding(vertical = 12.dp))

                    Text(
                        text = context.getString(R.string.about), style = TextStyle(
                            fontFamily = Font.QuickSand,
                            fontWeight = FontWeight.Light,
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    )

                    Text(
                        text = freelancerState.value.detail_information?.about_me ?: "",
                        style = TextStyle(
                            fontFamily = Font.QuickSand,
                            fontWeight = FontWeight.Normal,
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    )

                    Spacer(modifier = Modifier.padding(vertical = 6.dp))

                    Text(
                        text = context.getString(R.string.category),
                        style = TextStyle(
                            fontFamily = Font.QuickSand,
                            fontWeight = FontWeight.Light,
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    )

                    Spacer(modifier = Modifier.padding(vertical = 4.dp))

                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        for (categoryText in freelancerState.value.jobInformation?.categories
                            ?: listOf()) {
                            CustomLabel(
                                text = DummyData.entertainmentCategories[categoryText]!!
                            )
                        }
                    }

                    Spacer(modifier = Modifier.padding(vertical = 6.dp))

                    Text(
                        text = context.getString(R.string.profession_supporting_picture),
                        style = TextStyle(
                            fontFamily = Font.QuickSand,
                            fontWeight = FontWeight.Light,
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    )

                    Spacer(modifier = Modifier.padding(vertical = 6.dp))
                }
            }

            if (freelancerState.value.jobInformation?.imagesUrl?.toList()
                    ?.isNotEmpty() == true
            ) {
                items(
                    freelancerState.value.jobInformation?.imagesUrl?.toList()
                        ?: listOf()
                ) { value ->
                    val data = value.second

                    CropToSquareImage(
                        imageUrl = data.post_image_url.toString(),
                        contentDescription = null,
                        modifier = Modifier
                            .size(148.dp)
                            .clip(RoundedCornerShape(0.dp))
                            .clickable {
                                postImageUrl.value = data.post_image_url.toString()
                                showDialogStatePostImage.value = true
                            }
                    )

                    OpenImageDialog(
                        showDialogState = showDialogStatePostImage,
                        imageUrl = postImageUrl.value
                    )
                }
            }
        }
    }
}