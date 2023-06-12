package com.capstone.jeconn.ui.screen.dashboard.vacancies_screen.create_vacancies_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.capstone.jeconn.R
import com.capstone.jeconn.component.CustomButton
import com.capstone.jeconn.component.CustomChip
import com.capstone.jeconn.component.CustomDialogBoxLoading
import com.capstone.jeconn.component.CustomNavbar
import com.capstone.jeconn.component.CustomTextField
import com.capstone.jeconn.component.Font
import com.capstone.jeconn.data.dummy.DummyData
import com.capstone.jeconn.di.Injection
import com.capstone.jeconn.state.UiState
import com.capstone.jeconn.utils.MakeToast
import com.capstone.jeconn.utils.VacanciesViewModelFactory

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CreateVacanciesScreen(navHostController: NavHostController) {
    val context = LocalContext.current

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
    val descriptionState = remember {
        mutableStateOf("")
    }

    val startRange = remember {
        mutableStateOf("")
    }

    val endRange = remember {
        mutableStateOf("")
    }

    var loadingState by remember {
        mutableStateOf(false)
    }

    if (loadingState) {
        CustomDialogBoxLoading()
    }

    val createVacanciesViewModel: CreateVacanciesViewModel = remember {
        VacanciesViewModelFactory(
            vacanciesRepository = Injection.provideVacanciesRepository(context),
            chatRepository = Injection.provideChatRepository(context)
        ).create(
            CreateVacanciesViewModel::class.java
        )
    }

    val createVacanciesState by rememberUpdatedState(newValue = createVacanciesViewModel.createVacanciesState.value)

    LaunchedEffect(createVacanciesState) {
        when (val currentState = createVacanciesState) {

            is UiState.Loading -> {
                loadingState = true
            }

            is UiState.Success -> {
                loadingState = false
                MakeToast.short(context, currentState.data)
                navHostController.navigateUp()
            }

            is UiState.Error -> {
                loadingState = false
                MakeToast.short(context, currentState.errorMessage)
            }

            else -> {
                loadingState = false
                //Nothing
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
    ) {
        CustomNavbar {

            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
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
                    text = context.getString(R.string.create_vacancies),
                    style = TextStyle(
                        fontSize = 24.sp,
                        fontFamily = Font.QuickSand,
                        fontWeight = FontWeight.Bold,
                    ),
                )
            }
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .padding(12.dp)
        ) {
            Row {
                Text(
                    text = "${context.getString(R.string.select_a_job)} ",
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

            Row {
                Text(
                    text = "${context.getString(R.string.fill_job)} ",
                    style = TextStyle(
                        fontFamily = Font.QuickSand,
                        fontWeight = FontWeight.Normal,
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                )
                Text(
                    text = context.getString(R.string.description),
                    style = TextStyle(
                        fontFamily = Font.QuickSand,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.secondary
                    )
                )
            }

            CustomTextField(
                label = context.getString(R.string.job_description),
                state = descriptionState,
                imeAction = ImeAction.Next,
                length = 500,
                modifier = Modifier
                    .fillMaxWidth()
                    .size(100.dp)
            )

            Row {
                Text(
                    text = "${context.getString(R.string.fill_job)} ",
                    style = TextStyle(
                        fontFamily = Font.QuickSand,
                        fontWeight = FontWeight.Normal,
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                )
                Text(
                    text = context.getString(R.string.salary_range),
                    style = TextStyle(
                        fontFamily = Font.QuickSand,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.secondary
                    )
                )
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                CustomTextField(
                    label = context.getString(R.string.start),
                    state = startRange,
                    imeAction = ImeAction.Next,
                    type = KeyboardType.NumberPassword,
                    modifier = Modifier
                        .weight(1f)
                )

                CustomTextField(
                    label = context.getString(R.string.up_to),
                    state = endRange,
                    imeAction = ImeAction.Done,
                    type = KeyboardType.NumberPassword,
                    modifier = Modifier
                        .weight(1f)
                )
            }

            CustomButton(
                text = context.getString(R.string.create_vacancies),
                modifier = Modifier
                    .padding(vertical = 24.dp)
            ) {
                val newCategory = mutableMapOf<String, Int>()

                when {
                    (selectedCategory.none { it.value }) -> {
                        MakeToast.short(context, context.getString(R.string.empty_category))
                    }

                    (descriptionState.value == "") -> {
                        MakeToast.short(context, context.getString(R.string.empty_description))
                    }

                    (startRange.value == "") -> {
                        MakeToast.short(context, context.getString(R.string.empty_start_range))
                    }

                    (endRange.value == "") -> {
                        MakeToast.short(context, context.getString(R.string.empty_end_range))
                    }

                    else -> {
                        selectedCategory.filter { it.value }.keys.map { id ->
                            newCategory[id.toString()] = id
                        }
                        val newStartRange = startRange.value.replace(" ","").toLong()
                        val newEndRange = endRange.value.replace(" ","").toLong()
                        if (newStartRange > newEndRange) {
                            MakeToast.long(context, context.getString(R.string.end_less_than_start))
                        } else {
                            createVacanciesViewModel.createVacancies(
                                category = newCategory.values.toList(),
                                description = descriptionState.value,
                                salaryStart = newStartRange,
                                salaryEnd = newEndRange
                            )
                        }
                    }
                }
            }
        }
    }
}