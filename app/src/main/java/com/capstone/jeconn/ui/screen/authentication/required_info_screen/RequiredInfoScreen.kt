package com.capstone.jeconn.ui.screen.authentication.required_info_screen


import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.capstone.jeconn.component.Font
import com.capstone.jeconn.navigation.NavRoute
import com.capstone.jeconn.utils.intentWhatsApp
import com.capstone.jeconn.utils.navigateTo


@Composable
fun RequiredInfoScreen(navHostController: NavHostController) {

    val context = LocalContext.current
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        // Image
        Image(
            painterResource(id = R.drawable.mail_image),
            contentDescription = null,
            modifier = Modifier
                .height(200.dp)
        )

        //Spacing
        Spacer(modifier = Modifier.padding(vertical = 12.dp))

        //Title
        Row {
            Text(
                text = "${context.getString(R.string.verify_ur_acount)} ",
                style = TextStyle(
                    fontFamily = Font.QuickSand,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )
            )

        }

        //Spacing
        Spacer(modifier = Modifier.padding(vertical = 4.dp))

        //Teext Deskripsi
        Text(
            text = "${context.getString(R.string.verify_ur_acount_text1)} ",
            style = TextStyle(
                fontFamily = Font.QuickSand,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp
            )
        )

        //Teext Deskripsi
        Text(
            text = "${context.getString(R.string.verify_ur_acount_text2)} ",
            style = TextStyle(
                fontFamily = Font.QuickSand,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp
            )
        )

        //Spacing
        Spacer(modifier = Modifier.padding(vertical = 12.dp))

        //Button Refresh
        Button(
            onClick = { },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = MaterialTheme.colorScheme.onBackground
            ),
        )
        {
            Icon(
                imageVector = Icons.Filled.Refresh,
                contentDescription = null,
                )
        }

        //Spacing
        Spacer(modifier = Modifier.padding(vertical = 12.dp))
        //Text Pleass
        Text(
            text = "${context.getString(R.string.please_prees__)} ",
            style = TextStyle(
                fontFamily = Font.QuickSand,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp
            )
        )
        //Text Veriifed
        Text(
            text = "${context.getString(R.string.verified)} ",
            style = TextStyle(
                fontFamily = Font.QuickSand,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp
            )
        )
        //Login Button
        CustomButton(
            text = context.getString(R.string.Back_to_login),
            modifier = Modifier
                .padding(vertical = 24.dp).padding(horizontal = 24.dp),
        ) {

            navigateTo(navHostController, NavRoute.LoginScreen)
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
    }
}


