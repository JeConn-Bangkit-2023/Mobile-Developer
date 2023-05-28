package com.capstone.jeconn.ui.screen.authentication.register_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.capstone.jeconn.R
import com.capstone.jeconn.component.CustomButton
import com.capstone.jeconn.component.CustomTextField
import com.capstone.jeconn.component.Font
import com.capstone.jeconn.component.TextFieldState
import com.capstone.jeconn.navigation.NavRoute
import com.capstone.jeconn.utils.intentWhatsApp
import com.capstone.jeconn.utils.navigateTo

@Composable
fun RegisterScreen(navHostController: NavHostController) {
    val emailState = remember {
        TextFieldState()
    }

    val usernameState = remember {
        TextFieldState()
    }

    val fullnameState = remember {
        TextFieldState()
    }


    val passwordState = remember {
        TextFieldState()
    }

    val context = LocalContext.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        // Image
        Image(
            painterResource(id = R.drawable.register_image),
            contentDescription = null,
            modifier = Modifier
                .height(325.dp)
        )

        //Title
        Row {
            Text(
                text = "${context.getString(R.string.register_title)} ",
                style = TextStyle(
                    fontFamily = Font.QuickSand,
                    fontWeight = FontWeight.Normal,
                    fontSize = 22.sp
                )
            )
            Text(
                text = context.getString(R.string.app_name),
                style = TextStyle(
                    fontFamily = Font.QuickSand,
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                    color = MaterialTheme.colorScheme.secondary
                )
            )
        }

        //Spacing
        Spacer(modifier = Modifier.padding(vertical = 12.dp))

        //Username Text Field
        CustomTextField(
            state = usernameState,
            label = "Username",
            type = KeyboardType.Text,
            leadingIcon = R.drawable.ic_account_key,
            imeAction = ImeAction.Next,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp),
        )

        //fullname Text Field
        CustomTextField(
            state = fullnameState,
            label = "Full Name",
            type = KeyboardType.Text,
            leadingIcon = Icons.Default.Person,
            imeAction = ImeAction.Next,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp),
        )

        //Email Text Field
        CustomTextField(
            state = emailState,
            label = "Email",
            type = KeyboardType.Email,
            leadingIcon = R.drawable.ic_email,
            imeAction = ImeAction.Next,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp),
        )

        //Password Text Field
        CustomTextField(
            state = passwordState,
            label = "Password",
            type = KeyboardType.Password,
            leadingIcon = R.drawable.ic_lock,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp),
        )

        //Comfirm Password Text Field
        CustomTextField(
            state = passwordState,
            label = "Comfirm Password",
            type = KeyboardType.Password,
            leadingIcon = R.drawable.ic_lock,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp),
        )

        Row(
            modifier = Modifier
                .padding(vertical = 8.dp)
        ) {
            //Don't have account
            Text(
                text = "${context.getString(R.string.alredy_have_account)} ",
                style = TextStyle(
                    fontFamily = Font.QuickSand,
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp
                )
            )
            //Login ButtonText
            Text(
                text = context.getString(R.string.login),
                style = TextStyle(
                    fontFamily = Font.QuickSand,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.secondary
                ),
                modifier = Modifier
                    .clickable {
                        navigateTo(navHostController, NavRoute.LoginScreen)
                    }
            )
        }

        //Register Button
        CustomButton(
            text = context.getString(R.string.register),
            modifier = Modifier
                .padding(vertical = 24.dp),
        ) {

            navigateTo(navHostController, NavRoute.Dashboard)
        }

        // Need support text
        Text(
            text = context.getString(R.string.need_support),
            style = TextStyle(
                fontFamily = Font.QuickSand,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp
            ),
        )
        // Customer Service Button
        Text(
            text = context.getString(R.string.contact_customer_service),
            style = TextStyle(
                fontFamily = Font.QuickSand,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.secondary
            ),
            modifier = Modifier
                .clickable {
                    intentWhatsApp(context)
                }
        )

        //Spacing
        Spacer(modifier = Modifier.padding(vertical = 20.dp))


    }




}