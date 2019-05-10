package com.example.hotingoadmin

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.media.MediaScannerConnection
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.ImageView
import android.widget.Toast
import java.io.ByteArrayOutputStream
import java.io.File
import java.util.*
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.provider.DocumentsContract
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityCompat
import com.google.gson.Gson
import gun0912.tedbottompicker.TedBottomPicker
import kotlinx.android.synthetic.main.activity_hotels_room.*
import kotlinx.android.synthetic.main.activity_hotels_room.view.*
import kotlinx.android.synthetic.main.add_room_dialog.*
import kotlinx.android.synthetic.main.add_room_dialog.view.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HotelsRoomActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hotels_room)

        fab.setOnClickListener { view ->
            val addRoomDialog = AddRoomDialog(this)
                addRoomDialog.show()
        }

    }

}
