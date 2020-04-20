package com.example.car4rent

import android.app.Activity
import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_main.*
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() , View.OnClickListener {

    var formate = SimpleDateFormat("dd MMM, YYYY", Locale.US)
    var timeFormat = SimpleDateFormat("hh:mm a", Locale.US)

    private val PICK_IMAGE_REQUEST = 1234
    private var filePath: Uri? = null

    lateinit var carName: EditText
    lateinit var transmition: EditText
    lateinit var capacity: EditText
    lateinit var price: EditText
    lateinit var btnUpload: Button
    internal var storage: FirebaseStorage? = null
    internal var storageReference: StorageReference? = null

    override fun onClick(p0: View?) {
        if (p0 === btnChoose)
            showFileChooser()
        else if (p0 === btnUpload)
            uploadFile()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST &&
            resultCode == Activity.RESULT_OK &&
            data != null && data.data != null
        ) {
            filePath = data.data;
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, filePath)
                imageView!!.setImageBitmap(bitmap)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun buttonAdd() {
        val carModel = carName.text.toString().trim()
        val tansmitions = transmition.text.toString().trim()
        val capt = capacity.text.toString().trim()
        val prices = price.text.toString().trim()

        if (carModel.isEmpty()) {
            carName.error = "Insert the Car"
            return
        } else if (tansmitions.isEmpty()) {
            transmition.error = "Insert the Transmistion type"
            return
        } else if (capt.isEmpty()) {
            capacity.error = "Insert the Capacity"
            return
        } else if (prices.isEmpty()) {
            price.error = "Insert the Transmistion type"
            return
        }


        var ref = FirebaseDatabase.getInstance().getReference("cars")
        val carId = ref.push().key
        val car = Product(carId.toString(), carModel, tansmitions, capt, prices)
        ref.child(carModel).setValue(car).addOnCompleteListener {
            Toast.makeText(applicationContext, "Create success, thanks", Toast.LENGTH_LONG).show()
        }
    }

    private fun uploadFile() {
        if (filePath != null) {
            val progressDialog = ProgressDialog(this)
            progressDialog.setTitle("Uploding...")
            progressDialog.show()

            val imageRef = storageReference!!.child("images/" + UUID.randomUUID().toString())
            imageRef.putFile(filePath!!)
                .addOnSuccessListener {
                    progressDialog.dismiss()
                    Toast.makeText(applicationContext, "File Uploaded", Toast.LENGTH_SHORT).show()
                    buttonAdd()
                }
                .addOnFailureListener {
                    progressDialog.dismiss()
                    Toast.makeText(applicationContext, "Failed", Toast.LENGTH_SHORT).show()
                }
                .addOnProgressListener { taskSnapShot ->
                    val progress =
                        100.0 * taskSnapShot.bytesTransferred / taskSnapShot.totalByteCount
                    progressDialog.setMessage("Uploaded " + progress.toInt() + "%...")
                }
        }
    }

    private fun showFileChooser() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "SELECT PICTURE"), PICK_IMAGE_REQUEST)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        carName = findViewById(R.id.carName)
        transmition = findViewById(R.id.transmition)
        capacity = findViewById(R.id.capacity)
        price = findViewById(R.id.price)
        btnUpload = findViewById(R.id.btnUpload)

        storage = FirebaseStorage.getInstance()
        storageReference = storage!!.reference

        /*btnUpload.setOnClickListener{
            buttonAdd()
        }
*/
        btnChoose.setOnClickListener(this)
        btnUpload.setOnClickListener(this)

        btn_show.setOnClickListener {
            val now = Calendar.getInstance()
            val dateTv = DatePickerDialog(
                this, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                    val selectedDate = Calendar.getInstance()
                    selectedDate.set(Calendar.YEAR, year)
                    selectedDate.set(Calendar.MONTH, month)
                    selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    val date = formate.format(selectedDate.time)
                    dateTv.setText(date)
                    Toast.makeText(this, "date : " + date, Toast.LENGTH_SHORT).show()
                },
                now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH)
            )
            dateTv.show()
            try {
                if(btn_show.text != "Show Dialog") {
                    val date = timeFormat.parse(btn_show.text.toString())
                    now.time = date
                }
            }catch (e:Exception){
                e.printStackTrace()
            }
            val timePicker = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                val selectedTime = Calendar.getInstance()
                selectedTime.set(Calendar.HOUR_OF_DAY,hourOfDay)
                selectedTime.set(Calendar.MINUTE,minute)
                val time = timeFormat.format(selectedTime.time)
                timeTv.setText(time)
            },
                now.get(Calendar.HOUR_OF_DAY),now.get(Calendar.MINUTE),false)
            timePicker.show()
        }

    }
}


