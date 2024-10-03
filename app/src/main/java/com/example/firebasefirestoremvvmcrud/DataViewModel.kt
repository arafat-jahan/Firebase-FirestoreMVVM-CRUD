package com.example.firebasefirestoremvvmcrud

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore

class DataViewModel : ViewModel() {
    private val dataRepository = DataRepository()

    private val _datalist: MutableLiveData<List<Data>> = MutableLiveData()
    val datalist: LiveData<List<Data>> get() = _datalist

    init {
        fetchAndUpdateData()
    }

    private fun fetchAndUpdateData() {
        dataRepository.fetchData().observeForever {
            _datalist.value = it
        }
    }

    // Add data to repository
    fun addData(data: Data, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        dataRepository.addData(data)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { exception -> onFailure(exception) }
    }

    // Update data in repository
    fun updateData(data: Data, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        dataRepository.updateData(data)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { exception -> onFailure(exception) }
    }

    // Delete data from repository
    fun deleteData(data: Data, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        dataRepository.deleteData(data)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { exception -> onFailure(exception) }
    }
}
