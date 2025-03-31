package com.example.foodapp.data.remote

import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

open class BaseRemoteService @Inject constructor() {
    open val firebaseDb: FirebaseFirestore = FirebaseFirestore.getInstance()
}