package com.capstone.jeconn.ui.screen.dashboard.home_screen.message_screen.create_invoice_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.capstone.jeconn.R
import com.capstone.jeconn.component.CustomButton
import com.capstone.jeconn.component.CustomDatePickerTextField
import com.capstone.jeconn.component.CustomNavbar
import com.capstone.jeconn.component.CustomTextField
import com.capstone.jeconn.component.Font
import com.capstone.jeconn.utils.dateToTimeStamp
import com.capstone.jeconn.utils.patternWithHours
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun CreateInvoiceScreen(
    navHostController: NavHostController,
    tenant: String?,
    freelancer: String?
) {

    val context = LocalContext.current

    val serviceState = rememberSaveable {
        mutableStateOf("")
    }

    val dateOfStartState = rememberSaveable {
        mutableStateOf(
            dateToTimeStamp(
                patternWithHours,
                LocalDateTime.now().format(DateTimeFormatter.ofPattern(patternWithHours)),
                true
            )
        )
    }

    val dateOfEndState = rememberSaveable {
        mutableStateOf(
            dateOfStartState.value
        )
    }

    val jobDescriptionState = rememberSaveable {
        mutableStateOf("")
    }

    val noteState = rememberSaveable {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        CustomNavbar {

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

            Spacer(modifier = Modifier.padding(horizontal = 8.dp))

            Text(
                text = context.getString(R.string.create_invoice),
                style = TextStyle(
                    fontFamily = Font.QuickSand,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                )
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp)
        ) {
            Image(
                painterResource(id = R.drawable.printing_invoice_illustration),
                contentDescription = null,
                modifier = Modifier
                    .height(204.dp)
            )

            CustomTextField(
                label = context.getString(R.string.service),
                state = serviceState,
                modifier = Modifier.fillMaxWidth()
            )

            CustomDatePickerTextField(
                state = dateOfStartState,
                withHours = true,
                range = IntRange(LocalDate.now().year, LocalDate.now().year + 100),
                label = context.getString(R.string.date_of_start),
                navHostController = navHostController
            )

            CustomDatePickerTextField(
                state = dateOfStartState,
                withHours = true,
                range = IntRange(LocalDate.now().year, LocalDate.now().year + 100),
                label = context.getString(R.string.date_of_end),
                navHostController = navHostController
            )

            CustomTextField(
                label = context.getString(R.string.job_description),
                state = jobDescriptionState,
                modifier = Modifier.fillMaxWidth()
            )

            CustomTextField(
                label = context.getString(R.string.note),
                state = noteState,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.padding(vertical = 12.dp))

            CustomButton(text = context.getString(R.string.create_invoice)) {
                //TODO
            }
        }
    }
}