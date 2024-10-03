package com.example.firebasefirestoremvvmcrud



import android.app.AlertDialog
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firebasefirestoremvvmcrud.databinding.ActivityMainBinding
import com.google.firebase.Timestamp

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: DataAdapter
    private val dataViewModel: DataViewModel by viewModels()

    private fun clearInputFields() {
        binding.idEtxt.text.clear()
        binding.nameEtxt.text.clear()
        binding.emailEtxt.text.clear()
        binding.subjectEtxt.text.clear()
        binding.birthDateEtxt.text.clear()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize adapter
        adapter = DataAdapter(emptyList(), object : DataAdapter.ItemClickListener {
            override fun onEditItemClick(data: Data) {
                showEditDialog(data)
            }

            override fun onDeleteItem(data: Data) {
                onDeleteItemClick(data)
            }
        })

        // Set up RecyclerView
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        // Observe changes in data list
        dataViewModel.datalist.observe(this, Observer {
            adapter.updateData(it)
        })

        // Add new data
        binding.saveBtn.setOnClickListener {
            val studid = binding.idEtxt.text.toString()
            val name = binding.nameEtxt.text.toString()
            val email = binding.emailEtxt.text.toString()
            val subject = binding.subjectEtxt.text.toString()
            val birthdate = binding.birthDateEtxt.text.toString()

            if (studid.isNotEmpty() && name.isNotEmpty() && email.isNotEmpty() && subject.isNotEmpty() && birthdate.isNotEmpty()) {
                val data = Data(null, studid, name, email, subject, birthdate, Timestamp.now())
                dataViewModel.addData(data,
                    onSuccess = {
                        clearInputFields()
                        Toast.makeText(this@MainActivity, "Data added successfully", Toast.LENGTH_SHORT).show()
                    },
                    onFailure = {
                        Toast.makeText(this@MainActivity, "Failed to add data", Toast.LENGTH_SHORT).show()
                    }
                )
            }
        }
    }

    // Handle delete item click
    private fun onDeleteItemClick(data: Data) {
        AlertDialog.Builder(this).apply {
            setTitle("Delete Data")
            setMessage("Are you sure you want to delete this data?")
            setPositiveButton("Yes") { _, _ ->
                dataViewModel.deleteData(data,
                    onSuccess = {
                        Toast.makeText(this@MainActivity, "Data deleted successfully", Toast.LENGTH_SHORT).show()
                    },
                    onFailure = {
                        Toast.makeText(this@MainActivity, "Failed to delete data", Toast.LENGTH_SHORT).show()
                    }
                )
            }
            setNegativeButton("No", null)
        }.show()
    }

    // Show edit dialog
    private fun showEditDialog(data: Data) {
        binding.idEtxt.setText(data.studid)
        binding.nameEtxt.setText(data.name)
        binding.emailEtxt.setText(data.email)
        binding.subjectEtxt.setText(data.subject)
        binding.birthDateEtxt.setText(data.birthdate)

        binding.saveBtn.setOnClickListener {
            val updatedData = Data(
                data.id,
                binding.idEtxt.text.toString(),
                binding.nameEtxt.text.toString(),
                binding.emailEtxt.text.toString(),
                binding.subjectEtxt.text.toString(),
                binding.birthDateEtxt.text.toString(),
                Timestamp.now()
            )

            dataViewModel.updateData(updatedData,
                onSuccess = {
                    clearInputFields()
                    Toast.makeText(this@MainActivity, "Data updated successfully", Toast.LENGTH_SHORT).show()
                },
                onFailure = {
                    Toast.makeText(this@MainActivity, "Failed to update data", Toast.LENGTH_SHORT).show()
                }
            )
        }
    }
}
