package com.capstone.jeconn.ui.screen.dashboard.vacancies_screen.detail_vacanies_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.capstone.jeconn.component.CustomLabel
import com.capstone.jeconn.component.CustomNavbar
import com.capstone.jeconn.component.Font
import com.capstone.jeconn.data.dummy.DummyData
import com.capstone.jeconn.data.dummy.DummyData.entertainmentCategories
import com.capstone.jeconn.data.dummy.DummyData.vacancies
import com.capstone.jeconn.data.entities.LocationEntity
import com.capstone.jeconn.data.entities.VacanciesEntity
import com.capstone.jeconn.utils.CropToSquareImage
import com.capstone.jeconn.utils.calculateDistance
import com.capstone.jeconn.utils.getTimeAgo
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun DetailVacanciesScreen(navHostController: NavHostController, key: String?) {
    val auth = Firebase.auth
    val context = LocalContext.current
    val id = key!!.toInt()+1
    val vacancy = vacancies[id] ?: VacanciesEntity()
    val tenant = DummyData.publicData[vacancy.username]!!
    val timestamp = vacancy.timestamp!!
    val salary = vacancy.salary!!.toString()
    val description = vacancy.description!!
    val latitude = vacancy.location!!.latitude!!
    val longitude = vacancy.location.longitude!!

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
    ) {
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
                        text = context.getString(R.string.detai_vacancies),
                        style = TextStyle(
                            fontSize = 24.sp,
                            fontFamily = Font.QuickSand,
                            fontWeight = FontWeight.Bold,
                        ),
                    )
                }
            }
        }

        item {
            Column(
                modifier = Modifier
                    .padding(24.dp)

            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CropToSquareImage(
                        imageUrl = tenant.profile_image_url!!,
                        contentDescription = null,
                        modifier = Modifier
                            .size(60.dp)
                            .clip(CircleShape)
                    )
                    
                    Spacer(modifier = Modifier.padding(8.dp))

                    Column(
                    ) {
                        Text(
                            text = tenant.full_name!!,
                            style = TextStyle(
                                fontFamily = Font.QuickSand,
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        )

                        Text(
                            text = getTimeAgo(context, timestamp * 1000),
                            style = TextStyle(
                                fontFamily = Font.QuickSand,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Normal,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        )

                    }
                }

                Spacer(modifier = Modifier.padding(8.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                  Icon(
                      imageVector = Icons.Filled.LocationOn,
                      contentDescription = "Location icon",
                      tint = MaterialTheme.colorScheme.onBackground

                  )
                    Spacer(modifier = Modifier.padding(8.dp))

                    Text(
                        text = calculateDistance(LocationEntity(51.5074, -0.1278), LocationEntity(longitude, latitude)),
                        style = TextStyle(
                            fontFamily = Font.QuickSand,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    )
                }

                Spacer(modifier = Modifier.padding(8.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_attach_money),
                        contentDescription = "Icon",
                        tint = MaterialTheme.colorScheme.onBackground

                    )

                    Spacer(modifier = Modifier.padding(8.dp))

                    Text(
                        text = "Rp $salary",
                        style = TextStyle(
                            fontFamily = Font.QuickSand,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    )
                }

                Spacer(modifier = Modifier.padding(8.dp))

                Text(
                    text = context.getString(R.string.category),
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = Font.QuickSand,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onBackground
                    ),
                )

                Spacer(modifier = Modifier.padding(vertical = 4.dp))

                val categoryTextList = vacancy.category!!.mapNotNull { entertainmentCategories[it] } // Mengubah daftar kategori menjadi daftar teks menggunakan entertainmentCategories
                // Menampilkan daftar label kategori
                for (categoryText in categoryTextList) {
                    CustomLabel(
                        text = categoryText
                    )
                }

                Spacer(modifier = Modifier.padding(vertical = 8.dp))

                Text(
                    text = context.getString(R.string.deskripsi),
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = Font.QuickSand,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onBackground
                    ),
                )

                Spacer(modifier = Modifier.padding(vertical = 4.dp))

                Text(
                    text = description,
                    style = TextStyle(
                        fontFamily = Font.QuickSand,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                )

                Spacer(modifier = Modifier.padding(vertical = 40.dp))

                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Bottom
                ) {
                    // Konten lainnya di atas tombol
                    // ...

                    Spacer(modifier = Modifier.weight(1f))

                    Button(
                        onClick = { /* Aksi saat tombol diklik */ },
                        modifier = Modifier
                            .padding(bottom = 16.dp)
                            .align(Alignment.CenterHorizontally)
                    ) {
                        Text(text = "Contact Tenant")
                    }
                }

            }
        }
        item {
            Spacer(modifier = Modifier.padding(vertical = 4.dp))
        }
    }


}