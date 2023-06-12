package com.capstone.jeconn.ui.screen.dashboard.profile_screen.edit_detail_info

import android.Manifest
import android.content.pm.PackageManager.PERMISSION_GRANTED
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import com.capstone.jeconn.R
import com.capstone.jeconn.component.CustomButton
import com.capstone.jeconn.component.CustomChip
import com.capstone.jeconn.component.CustomDatePickerTextField
import com.capstone.jeconn.component.CustomDialogBoxLoading
import com.capstone.jeconn.component.CustomDropDownMenu
import com.capstone.jeconn.component.CustomFlatIconButton
import com.capstone.jeconn.component.CustomNavbar
import com.capstone.jeconn.component.CustomSwitchItem
import com.capstone.jeconn.component.CustomTextField
import com.capstone.jeconn.component.Font
import com.capstone.jeconn.component.HorizontalDivider
import com.capstone.jeconn.component.rememberDropDownStateHolder
import com.capstone.jeconn.data.dummy.DummyData
import com.capstone.jeconn.di.Injection
import com.capstone.jeconn.state.UiState
import com.capstone.jeconn.utils.CropToSquareImage
import com.capstone.jeconn.utils.MakeToast
import com.capstone.jeconn.utils.PICK_IMAGE_PERMISSION_REQUEST_CODE
import com.capstone.jeconn.utils.ProfileViewModelFactory
import com.capstone.jeconn.utils.dateToTimeStamp
import com.capstone.jeconn.utils.patternNoHours
import com.capstone.jeconn.utils.uriToFile
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun EditDetailInfoScreen(navHostController: NavHostController) {

    val context = LocalContext.current

    val editDetailViewModel: EditDetailInfoViewModel = remember {
        ProfileViewModelFactory(Injection.provideProfileRepository(context)).create(
            EditDetailInfoViewModel::class.java
        )
    }

    val dateState = rememberSaveable {
        mutableStateOf(
            dateToTimeStamp(
                patternNoHours,
                LocalDate.now().format(DateTimeFormatter.ofPattern(patternNoHours)),
                false
            )
        )
    }
    val genderState = rememberDropDownStateHolder(
        "",
        listOf(
            context.getString(R.string.male),
            context.getString(R.string.female)
        )
    )
    val aboutState = rememberSaveable {
        mutableStateOf("")
    }

    val selectedCategory = remember {
        mutableStateMapOf(
            1 to false,
            2 to false,
            3 to false,
            4 to false,
            5 to false,
            6 to false,
            7 to false,
            8 to false,
            9 to false,
            10 to false,
        )
    }

    val pickImageLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if (uri != null) {
                // Gambar berhasil dipilih dari galeri, lanjutkan ke pengiriman ke API
                val myFile = uriToFile(uri, context)
                if (myFile.exists()) {
                    try {
                        editDetailViewModel.updateJobImage(myFile)
                    } catch (e: Exception) {
                        MakeToast.short(context, e.message.toString())
                    } finally {
                        editDetailViewModel.getPublicDataState
                    }
//                        val bitmapImage = resizeBitmap(BitmapFactory.decodeFile(selectedImageFile.value!!.path))
//                        val result = ObjectDetection.detect(context, bitmapImage)
//
//                        if (result.isNotEmpty()) {
//                            result.map { detection ->
//                                Log.e("categories", detection.categories.toString())
//                                Log.e("categories", detection.boundingBox.toString())
//                            }
//                        }
                }
            }
        }

    val isLoading = remember {
        mutableStateOf(false)
    }
    if (isLoading.value) {
        CustomDialogBoxLoading()
    }

    val updateJobImageState by rememberUpdatedState(newValue = editDetailViewModel.updateJobImageState.value)

    val imageJobListState = remember {
        mutableStateListOf("")
    }

    LaunchedEffect(updateJobImageState) {
        when (val currentState = updateJobImageState) {
            is UiState.Loading -> {
                isLoading.value = true
            }

            is UiState.Success -> {
                isLoading.value = false
                editDetailViewModel.getPublicData()
                MakeToast.short(context, currentState.data)
            }

            is UiState.Error -> {
                isLoading.value = false
                MakeToast.short(context, currentState.errorMessage)
            }

            else -> {
                isLoading.value = false
                // Nothing
            }
        }
    }

    val offersState = remember {
        mutableStateOf(false)
    }

    val getPublicDataState by rememberUpdatedState(newValue = editDetailViewModel.getPublicDataState.value)

    LaunchedEffect(getPublicDataState) {
        when (val currentState = getPublicDataState) {
            is UiState.Loading -> {
                isLoading.value = true
            }

            is UiState.Success -> {
                imageJobListState.clear()
                isLoading.value = false
                currentState.data.detail_information?.date_of_birth?.let {
                    dateState.value = it
                }
                currentState.data.detail_information?.gender?.let {
                    genderState.value = it
                }
                currentState.data.detail_information?.about_me?.let {
                    aboutState.value = it
                }
                currentState.data.jobInformation?.categories?.let {
                    it.map { id ->
                        selectedCategory[id] = true
                    }
                }

                currentState.data.jobInformation?.imagesUrl?.values?.sortedByDescending { it.post_image_uid }?.forEach {
                    it.post_image_url?.let { it1 -> imageJobListState.add(it1) }
                }
                currentState.data.jobInformation?.isOpenToOffer.let {
                    offersState.value = it ?: false
                }
            }

            is UiState.Error -> {
                isLoading.value = false
                MakeToast.short(context, currentState.errorMessage)
            }

            else -> {
                isLoading.value = false
                // Nothing
            }
        }
    }

    val updateDetailInfoState by rememberUpdatedState(newValue = editDetailViewModel.updateDetailInfoState.value)

    LaunchedEffect(updateDetailInfoState) {
        when (val currentState = updateDetailInfoState) {
            is UiState.Loading -> {
                isLoading.value = true
            }

            is UiState.Success -> {
                isLoading.value = false
                MakeToast.short(context, currentState.data)
            }

            is UiState.Error -> {
                isLoading.value = false
                MakeToast.short(context, currentState.errorMessage)
            }

            else -> {
                isLoading.value = false
                // Nothing
            }
        }
    }


    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        CustomNavbar {

            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = null,
                modifier = Modifier
                    .size(28.dp)
                    .clickable {
                        navHostController.popBackStack()
                    }
            )

            Spacer(modifier = Modifier.padding(horizontal = 8.dp))

            Text(
                text = context.getString(R.string.edit_detail_information),
                style = TextStyle(
                    fontFamily = Font.QuickSand,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                )
            )
        }

        Column(
            modifier = Modifier
                .padding(12.dp)
        ) {
            Image(
                painterResource(id = R.drawable.personal_info_image),
                contentDescription = null,
                modifier = Modifier
                    .height(200.dp)
            )

            Spacer(modifier = Modifier.padding(vertical = 12.dp))

            Row {
                Text(
                    text = "${context.getString(R.string.complete_your)} ",
                    style = TextStyle(
                        fontFamily = Font.QuickSand,
                        fontWeight = FontWeight.Normal,
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                )
                Text(
                    text = context.getString(R.string.information),
                    style = TextStyle(
                        fontFamily = Font.QuickSand,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.secondary
                    )
                )
            }

            Spacer(modifier = Modifier.padding(vertical = 12.dp))

            CustomDatePickerTextField(
                state = dateState,
                withHours = false,
                range = IntRange(1800, LocalDate.now().year),
                label = context.getString(R.string.dob),
                navHostController = navHostController
            )

            Spacer(modifier = Modifier.padding(vertical = 8.dp))

            CustomDropDownMenu(
                stateHolder = genderState,
                label = context.getString(R.string.gender),
                modifier = Modifier
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.padding(vertical = 8.dp))

            CustomTextField(
                label = context.getString(R.string.about),
                state = aboutState,
                length = 1000,
                modifier = Modifier
                    .fillMaxWidth()
            )

            HorizontalDivider()

            Row {
                Text(
                    text = "${context.getString(R.string.prefer_your)} ",
                    style = TextStyle(
                        fontFamily = Font.QuickSand,
                        fontWeight = FontWeight.Normal,
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                )
                Text(
                    text = context.getString(R.string.category),
                    style = TextStyle(
                        fontFamily = Font.QuickSand,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.secondary
                    )
                )
            }

            Spacer(modifier = Modifier.padding(vertical = 12.dp))

            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                DummyData.entertainmentCategories.onEachIndexed { index, categories ->
                    CustomChip(
                        iconId = Icons.Default.Done,
                        isSelected = selectedCategory[index + 1]!!,
                        text = categories.value
                    ) {
                        selectedCategory[index + 1] = !(selectedCategory[index + 1]!!)
                    }
                }
            }

            HorizontalDivider()

            FlowRow {
                Text(
                    text = "${context.getString(R.string.add_image_support)} ",
                    style = TextStyle(
                        fontFamily = Font.QuickSand,
                        fontWeight = FontWeight.Normal,
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                )
                Text(
                    text = context.getString(R.string.profession_or_skill),
                    style = TextStyle(
                        fontFamily = Font.QuickSand,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.secondary
                    )
                )
            }

            Spacer(modifier = Modifier.padding(vertical = 12.dp))

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(imageJobListState) { imageUrl ->
                    CropToSquareImage(
                        imageUrl = imageUrl,
                        contentDescription = null,
                        modifier = Modifier
                            .size(148.dp)
                            .clip(RoundedCornerShape(8.dp))
                    )
                }
            }

            Spacer(modifier = Modifier.padding(vertical = 12.dp))

            CustomFlatIconButton(
                icon = Icons.Default.Add,
                label = "Upload New"
            ) {
                val activity = context as ComponentActivity
                val permission = Manifest.permission.READ_EXTERNAL_STORAGE
                if (ContextCompat.checkSelfPermission(activity, permission) == PERMISSION_GRANTED) {
                    // Permission granted, proceed with image selection
                    pickImageLauncher.launch("image/*")
                } else {
                    // Permission has not been granted, request permission
                    ActivityCompat.requestPermissions(
                        activity,
                        arrayOf(permission),
                        PICK_IMAGE_PERMISSION_REQUEST_CODE
                    )
                }
            }

            HorizontalDivider()

            CustomSwitchItem(
                checked = offersState.value,
                onCheckedChange = {
                    offersState.value = !offersState.value
                },
                title = context.getString(R.string.open_to_freelancing),
                titleHighlight = context.getString(R.string.offers),
                description = context.getString(R.string.open_to_freelancing_description)
            )

            Spacer(modifier = Modifier.padding(vertical = 24.dp))

            CustomButton(
                text = context.getString(R.string.save),
                enabled = !isLoading.value
            ) {
                when {
                    (genderState.value == "") -> {
                        MakeToast.short(context, context.getString(R.string.empty_gender))
                    }

                    (aboutState.value == "") -> {
                        MakeToast.short(context, context.getString(R.string.empty_about_me))
                    }

                    (selectedCategory.none { it.value }) -> {
                        MakeToast.short(context, context.getString(R.string.empty_category))
                    }

                    (imageJobListState.size == 0) -> {
                        MakeToast.short(context, context.getString(R.string.empty_image))
                    }

                    else -> {
                        editDetailViewModel.updateDetailInfo(
                            dateState.value,
                            genderState.value,
                            aboutState.value,
                            selectedCategory.filterValues { it }.keys.toList(),
                            offersState.value
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.padding(vertical = 12.dp))
        }
    }
}