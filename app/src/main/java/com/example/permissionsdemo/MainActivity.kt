package com.example.permissionsdemo

import android.Manifest
import android.app.Dialog
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_custom.*
import java.nio.file.attribute.AclEntry


class MainActivity : AppCompatActivity() {

    private val cameraAndLocationResultLauncher : ActivityResultLauncher<Array<String>> =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions() ){
            permissions ->
            permissions.entries.forEach{
                val permissionName = it.key
                val isGranted = it.value
                if(isGranted){
                    if(permissionName==Manifest.permission.ACCESS_FINE_LOCATION){
                        Toast.makeText(this, "Permission granted for Location", Toast.LENGTH_LONG).show()
                    }else{
                        Toast.makeText(this, "Permission granted for Camera", Toast.LENGTH_LONG).show()
                    }
                }else{
                    if(permissionName==Manifest.permission.ACCESS_FINE_LOCATION){
                        Toast.makeText(this, "Permission denied for fine Location", Toast.LENGTH_LONG).show()
                    }else if(permissionName==Manifest.permission.ACCESS_COARSE_LOCATION){

                        Toast.makeText(this, "Permission denied for Course Location", Toast.LENGTH_LONG).show()

                    } else{
                        Toast.makeText(this, "Permission denied for Camera", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener{

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                    shouldShowRequestPermissionRationale(Manifest.permission.CAMERA )){
                showRationalDialog("Camera access required",  "Camera cannot be used, camera access denied")
            }else{
               cameraAndLocationResultLauncher.launch(
                   arrayOf(Manifest.permission.CAMERA,
                           Manifest.permission.ACCESS_FINE_LOCATION,
                           Manifest.permission.ACCESS_COARSE_LOCATION)
               )
            }
        }

        Alert_dialog.setOnClickListener{
            showAlertDialog()
        }

        Custom_Alert_dialog.setOnClickListener{
            showCustomDialog()
        }

        Custom_Progress_Bar.setOnClickListener{
            showCustomProgressDialog()
        }
    }

    private fun showCustomProgressDialog() {
        val customProgressDialog = Dialog(this)

        customProgressDialog.setContentView(R.layout.custom_progress)

        customProgressDialog.show()



    }

    private fun showCustomDialog() {
        val customDialog = Dialog(this)

        customDialog.setContentView(R.layout.dialog_custom)
        customDialog.customSubmit.setOnClickListener{
            Toast.makeText(applicationContext, "clicked submit", Toast.LENGTH_LONG).show()
            customDialog.dismiss()
        }

        customDialog.customCancel.setOnClickListener{
            Toast.makeText(applicationContext, "clicked cancel", Toast.LENGTH_LONG).show()
            customDialog.dismiss()
        }


        customDialog.show()
    }

    private fun showAlertDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Alert Dialog")
        builder.setMessage("This is an alert dialog which is used to show alert pop-up")
        builder.setIcon(android.R.drawable.ic_dialog_alert)

        builder.setPositiveButton("Yes"){ dialogInterface, which ->
            Toast.makeText(applicationContext, "clicked yes", Toast.LENGTH_LONG).show()
            dialogInterface.dismiss()

        }
        builder.setNeutralButton("Cancel"){dialogInterface, which ->
            Toast.makeText(applicationContext, "Clicked Cancel", Toast.LENGTH_LONG).show()
            dialogInterface.dismiss()
        }
        builder.setNegativeButton("No"){dialogInterface, which ->
            Toast.makeText(applicationContext, "Clicked No", Toast.LENGTH_LONG).show()
            dialogInterface.dismiss()
        }

        val alertDialog : AlertDialog =builder.create()
        alertDialog.setCancelable(false)// will NOT allow user to dismiss dialog by clicking somewhere else in the screen.
                                        // if u want to cancel by clicking somewhere on the screen and set it to TRUE

        alertDialog.show()

    }

    private fun showRationalDialog(Title: String, message:String){
        val builder :AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle(Title)
            .setMessage(message)
            .setPositiveButton("Cancel"){ dialog, _->
                dialog.dismiss()

            }
        builder.create().show()
    }
}