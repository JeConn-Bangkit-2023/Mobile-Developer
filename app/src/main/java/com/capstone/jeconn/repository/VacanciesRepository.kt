package com.capstone.jeconn.repository

import android.content.Context
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.capstone.jeconn.R
import com.capstone.jeconn.data.entities.LocationEntity
import com.capstone.jeconn.data.entities.VacanciesEntity
import com.capstone.jeconn.state.UiState
import com.capstone.jeconn.utils.asMap
import com.capstone.jeconn.utils.getRandomNumeric
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class VacanciesRepository(
    private val context: Context,
) {
    private val currentUser = Firebase.auth.currentUser!!
    private val ref = Firebase.database.reference

    val createVacanciesState: MutableState<UiState<String>> = mutableStateOf(UiState.Empty)
    val vacanciesListState: MutableState<UiState<List<VacanciesEntity>>> =
        mutableStateOf(UiState.Empty)
    val vacanciesDetailState: MutableState<UiState<VacanciesEntity>> =
        mutableStateOf(UiState.Empty)

    fun getVacanciesList() {
        val vacanciesList = mutableListOf<VacanciesEntity>()
        vacanciesListState.value = UiState.Loading
        ref.child("vacanciesList").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.childrenCount > 0) {
                    snapshot.children.forEach { vacancies ->
                        vacancies.getValue(VacanciesEntity::class.java)?.let { item ->
                            item.username?.let { username ->
                                ref.child("publicData").child(username).get()
                                    .addOnSuccessListener { userData ->
                                        val fullName =
                                            userData.child("full_name").getValue(String::class.java)
                                        val profileImageUrl = userData.child("profile_image_url")
                                            .getValue(String::class.java)
                                        ref.child("publicData").child(currentUser.displayName!!)
                                            .child("jobInformation").child("location").get()
                                            .addOnSuccessListener { myLocationSnapshot ->
                                                val myLocation =
                                                    myLocationSnapshot.getValue(LocationEntity::class.java)
                                                val newData = VacanciesEntity(
                                                    id = item.id,
                                                    username = item.username,
                                                    full_name = fullName,
                                                    imageUrl = profileImageUrl,
                                                    timestamp = item.timestamp,
                                                    start_salary = item.start_salary,
                                                    end_salary = item.end_salary,
                                                    category = item.category,
                                                    description = item.description,
                                                    location = item.location,
                                                    myLocation = myLocation
                                                )
                                                vacanciesList.add(newData)
                                                if (vacanciesList.size == snapshot.childrenCount.toInt()) {
                                                    vacanciesListState.value =
                                                        UiState.Success(vacanciesList)
                                                }
                                            }
                                    }
                            }
                        }
                    }
                } else {
                    vacanciesListState.value = UiState.Error(context.getString(R.string.empty))
                }

            }

            override fun onCancelled(error: DatabaseError) {
                UiState.Error(context.getString(R.string.server_fail))
                Log.e("error Vacancies List", error.message)
            }

        })
    }

    fun getVacanciesDetail(id: Int) {
        vacanciesDetailState.value = UiState.Loading

        ref.child("vacanciesList").child(id.toString()).get().addOnSuccessListener { vacancies ->
            val vacanciesDetail = vacancies.getValue(VacanciesEntity::class.java)
            vacanciesDetail?.let {
                vacanciesDetailState.value = UiState.Success(it)
            }
        }
    }

    fun createVacancies(
        category: List<Int>,
        description: String,
        salaryStart: Long,
        salaryEnd: Long
    ) {
        createVacanciesState.value = UiState.Loading
        val currentDateTime = System.currentTimeMillis()
        val myUsername = currentUser.displayName!!
        val getRandomId = getRandomNumeric()
        ref.child("publicData").child(myUsername).child("jobInformation")
            .child("location")
            .get().addOnSuccessListener { snapshot ->
                val currentLocation = snapshot.getValue(LocationEntity::class.java)
                val newData = VacanciesEntity(
                    id = getRandomId,
                    username = myUsername,
                    timestamp = currentDateTime,
                    start_salary = salaryStart,
                    end_salary = salaryEnd,
                    category = category,
                    description = description,
                    location = currentLocation
                )
                val recordId = mapOf(
                    getRandomId.toString() to getRandomId
                )

                ref.child("vacanciesList").child(getRandomId.toString())
                    .updateChildren(newData.asMap()).addOnSuccessListener {
                        ref.child("publicData").child(myUsername).child("vacanciesPostId")
                            .updateChildren(recordId)
                            .addOnSuccessListener {
                                createVacanciesState.value =
                                    UiState.Success(context.getString(R.string.successfully_create_a_vacancy_post))
                            }.addOnFailureListener {
                                UiState.Error(context.getString(R.string.server_fail))
                                Log.e("put into vacanciesList", it.message.toString())
                            }

                    }.addOnFailureListener {
                        UiState.Error(context.getString(R.string.server_fail))
                        Log.e("put into vacanciesList", it.message.toString())
                    }
            }.addOnFailureListener {
                UiState.Error(context.getString(R.string.server_fail))
                Log.e("get location data", it.message.toString())
            }
    }
}