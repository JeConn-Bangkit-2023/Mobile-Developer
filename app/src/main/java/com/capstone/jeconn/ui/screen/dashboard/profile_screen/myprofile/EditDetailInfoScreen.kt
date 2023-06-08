package com.capstone.jeconn.ui.screen.dashboard.profile_screen.myprofile

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import com.capstone.jeconn.component.CustomDropDownMenu
import com.capstone.jeconn.component.CustomFlatIconButton
import com.capstone.jeconn.component.CustomNavbar
import com.capstone.jeconn.component.CustomSwitchItem
import com.capstone.jeconn.component.CustomTextField
import com.capstone.jeconn.component.Font
import com.capstone.jeconn.component.HorizontalDivider
import com.capstone.jeconn.component.rememberDropDownStateHolder
import com.capstone.jeconn.data.dummy.DummyData
import com.capstone.jeconn.utils.CropToSquareImage
import com.capstone.jeconn.utils.MakeToast
import com.capstone.jeconn.utils.PICK_IMAGE_PERMISSION_REQUEST_CODE
import com.capstone.jeconn.utils.dateToTimeStamp
import com.capstone.jeconn.utils.patternNoHours
import com.capstone.jeconn.utils.uriToFile
import java.io.File
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun EditDetailInfoScreen(navHostController: NavHostController) {

    val context = LocalContext.current

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

    val selectedImageFile = remember { mutableStateOf<File?>(null) }

    val pickImageLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if (uri != null) {
                // Gambar berhasil dipilih dari galeri, lanjutkan ke pengiriman ke API
                val myFile = uriToFile(uri, context)
                if (myFile.exists()) {
                    selectedImageFile.value = myFile
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

    var offersState by remember {
        mutableStateOf(false)
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
                withHours = false, IntRange(1800, LocalDate.now().year)
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
                val dummyImage = DummyData.publicData["john_doe66"]!!.jobInformation!!.imagesUrl
                items(dummyImage!!) { imageUrl ->
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
                checked = offersState,
                onCheckedChange = {
                    offersState = !offersState
                },
                title = context.getString(R.string.open_to_freelancing),
                titleHighlight = context.getString(R.string.offers),
                description = context.getString(R.string.open_to_freelancing_description)
            )

            Spacer(modifier = Modifier.padding(vertical = 24.dp))

            CustomButton(text = context.getString(R.string.save)) {
                MakeToast.short(context, "Tunggu API dari anak CC jadi dulu")
            }

            Spacer(modifier = Modifier.padding(vertical = 12.dp))
        }
    }
}