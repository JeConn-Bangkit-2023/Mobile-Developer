package com.capstone.jeconn.ui.screen.dashboard.home_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.capstone.jeconn.R
import com.capstone.jeconn.component.CustomNavbar
import com.capstone.jeconn.component.Font
import com.capstone.jeconn.component.card.HorizontalVacanciesCard
import com.capstone.jeconn.component.card.VerticalFreelancerCard
import com.capstone.jeconn.data.dummy.DummyData
import com.capstone.jeconn.data.dummy.DummyData.publicData
import com.capstone.jeconn.data.entities.PublicDataEntity

@Composable
fun HomeScreen(navHostController: NavHostController) {
    val context = LocalContext.current
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            CustomNavbar {
                Text(
                    text = context.getString(R.string.app_name),
                    style = TextStyle(
                        fontSize = 24.sp,
                        fontFamily = Font.QuickSand,
                        fontWeight = FontWeight.Bold,
                    ),
                )

                Spacer(modifier = Modifier.weight(1f))

                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = null,
                    modifier = Modifier.size(28.dp)
                )

                Spacer(modifier = Modifier.padding(horizontal = 4.dp))

                Icon(
                    painter = painterResource(id = R.drawable.ic_message),
                    contentDescription = null,
                    modifier = Modifier.size(38.dp)
                )
            }
            Text(
                text = context.getString(R.string.talent_in_your_city),
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = Font.QuickSand,
                    fontWeight = FontWeight.Bold,
                ),
                modifier = Modifier
                    .padding(vertical = 16.dp, horizontal = 12.dp)
            )

            LazyRow(
                contentPadding = PaddingValues(horizontal = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(publicData) { user ->
                    if (user.detail_information != null && user.jobInformation != null)
                        VerticalFreelancerCard(
                            imageUrl = user.profile_image_url,
                            name = user.full_name,
                            city = user.detail_information.city,
                            listSkills = user.jobInformation.skills
                        )
                }
            }

            Text(
                text = context.getString(R.string.vacancies_in_your_city),
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = Font.QuickSand,
                    fontWeight = FontWeight.Bold,
                ),
                modifier = Modifier
                    .padding(vertical = 16.dp, horizontal = 12.dp)
            )
        }

        items(DummyData.vacanciesList) { vacancies ->
            val user: PublicDataEntity = publicData.find { it.username == vacancies.username }!!
            Box(
                modifier = Modifier.padding(horizontal = 12.dp)
            ) {
                HorizontalVacanciesCard(
                    imageUrl = user.profile_image_url,
                    name = user.full_name,
                    city = vacancies.city,
                    timestamp = vacancies.timestamp,
                    description = vacancies.description
                )
            }
        }

        item {
            Spacer(modifier = Modifier.padding(vertical = 40.dp))
        }
    }
}