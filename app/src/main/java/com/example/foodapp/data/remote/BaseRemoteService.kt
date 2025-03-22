package com.example.foodapp.data.remote

import com.google.firebase.firestore.FirebaseFirestore

open class BaseRemoteService {
    protected val firebaseDb: FirebaseFirestore = FirebaseFirestore.getInstance()
}