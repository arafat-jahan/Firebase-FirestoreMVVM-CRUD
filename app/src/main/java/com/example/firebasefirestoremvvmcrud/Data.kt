package com.example.firebasefirestoremvvmcrud

import com.google.firebase.Timestamp

data class Data(

var id: String? = null,
var studid: String? = null,
var name: String? = null,
var email: String? = null,
var subject: String? = null,
var birthdate: String? = null,
val timestamp: Timestamp? = null
)


