package com.capstone.jeconn.ui.screen.dashboard.freelancer_screen.detail_freelancer_screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.capstone.jeconn.R
import com.capstone.jeconn.component.CustomButton
import com.capstone.jeconn.component.CustomLabel
import com.capstone.jeconn.component.CustomNavbar
import com.capstone.jeconn.component.Font
import com.capstone.jeconn.data.dummy.DummyData
import com.capstone.jeconn.data.entities.LocationEntity
import com.capstone.jeconn.utils.CropToSquareImage
import com.capstone.jeconn.utils.calculateDistance
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun DetailFreelancerScreen(
    navHostController: NavHostController,
    uid: String?
    ) {
    val auth = Firebase.auth
    val context = LocalContext.current
    val getId = uid!!
    val freelancerdata = DummyData.publicData[getId]


    Log.e("index",freelancerdata!!.jobInformation!!.imagesUrl!!.toList().toString())
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
    ) {
        //Nav Bar
        item {
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
                        text = context.getString(R.string.detail_freelance),
                        style = TextStyle(
                            fontSize = 24.sp,
                            fontFamily = Font.QuickSand,
                            fontWeight = FontWeight.Bold,
                        ),
                    )
                }
            }
        }
        //Item Detail
        item {
            Column(
                modifier = Modifier.padding(horizontal = 24.dp)
            ) {
                Row(
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically

                ) {
                    Box() {
                        //Freelancer Pcture
                        CropToSquareImage(
                            imageUrl = freelancerdata.profile_image_url.toString(),
                            contentDescription = null,
                            modifier = Modifier
                                .size(80.dp)
                                .clip(CircleShape)
                        )
                    }

                    Spacer(modifier = Modifier.padding(8.dp))
                    //Nama freelancer
                    Column {
                        Text(
                            text = freelancerdata.full_name.toString(),
                            style = TextStyle(
                                fontFamily = Font.QuickSand,
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        )
                        //Jarak freelancer
                        Text(
                            text = calculateDistance(
                                freelancerdata.jobInformation!!.location!!,
                                LocationEntity(51.5074, -0.1278)
                            ),
                            style = TextStyle(
                                fontFamily = Font.QuickSand,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Normal,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        )
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
                //About Freelancer
                Text(
                    text = freelancerdata.detail_information!!.about_me!!,
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
                // Category Skill
                for (categoryText in freelancerdata.jobInformation!!.categories!!) {
                    CustomLabel(
                        text = DummyData.entertainmentCategories[categoryText]!!
                    )
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

                Spacer(modifier = Modifier.padding(vertical = 4.dp))

                //Picture Post freelancer
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    itemsIndexed(freelancerdata.jobInformation.imagesUrl!!.toList()) { index, value ->
                        val data  = value.second
                        CropToSquareImage(
                            imageUrl = data.post_image_url.toString(),
                            contentDescription = null,
                            modifier = Modifier
                                .size(148.dp)
                                .clip(RoundedCornerShape(8.dp))
                        )
                    }
                }
                Spacer(modifier = Modifier.padding(vertical = 20.dp))

                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Bottom
                ) {
                    Spacer(modifier = Modifier.weight(1f))

                    CustomButton(
                        text = context.getString(R.string.contact_tenant),
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {

                    }
                }
            }
        }
    }
}