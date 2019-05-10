package com.example.hotingoadmin

import android.app.Activity
import android.app.Dialog
import android.content.ContentUris
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityCompat.requestPermissions
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.example.hotingoadmin.model.AdminResponce
import com.example.hotingoadmin.model.PostNewRoom
import com.example.hotingoadmin.service.ApiClient
import com.example.hotingoadmin.service.ApiInterface
import com.google.gson.Gson
import gun0912.tedbottompicker.TedBottomPicker
import kotlinx.android.synthetic.main.add_room_dialog.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.util.ArrayList


class AddRoomDialog(var activity: AppCompatActivity) : Dialog(activity) {

    var gson: Gson = Gson()
    lateinit var shared: SharedPreferences
    var retriveuser: String = ""
    lateinit var shareEditor: SharedPreferences.Editor

    private var imagessParts: MutableList<MultipartBody.Part> = mutableListOf()
    private val REQUEST_CODE_ASK_PERMISSIONS = 123

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_room_dialog)

        //Save Data in SharedPreferences
        shared = context.getSharedPreferences("Hotingo Admin", Context.MODE_PRIVATE)!!
        shareEditor = shared.edit()

        //Get Data From SharedPreferences
        retriveuser = shared.getString("ADMIN", "")
        var admin = gson.fromJson(retriveuser, AdminResponce::class.java)

        var token = "Bearer " + admin.token



        btn.setOnClickListener {
            CheckUserPermsions()
        }
        btnPost.setOnClickListener {

            if (roomnumberET.text.isEmpty()) {
                roomnumberET.setError("Enter Room Number ...")
            } else if (roompriceET.text.isEmpty()) {
                roompriceET.setError("Enter Room Price ...")
            } else if (roomdescET.text.isEmpty()) {
                roomdescET.setError("Enter Room Description ...")
            } else {

                var roomNumber = roomnumberET.text.toString().toInt()

                var roomprice = roompriceET.text.toString().toInt()

                var descRoom = roomdescET.text.toString()

                var roomdesc = RequestBody.create(MediaType.parse("text-plain"), descRoom)

                var apiInterface: ApiInterface = ApiClient.getAdmin().create(ApiInterface::class.java)
                var call: Call<PostNewRoom> =
                    apiInterface.getNewRoom(token, imagessParts, roomNumber, roomprice, roomdesc)
                call.enqueue(object : Callback<PostNewRoom> {
                    override fun onFailure(call: Call<PostNewRoom>?, t: Throwable?) {

                    }

                    override fun onResponse(call: Call<PostNewRoom>?, response: Response<PostNewRoom>?) {
                        Toast.makeText(context, "Add Room Succ", Toast.LENGTH_LONG).show()


                    }
                })

                dismiss()
            }

        }
    }

    fun CheckUserPermsions() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (activity?.let {
                    ActivityCompat.checkSelfPermission(
                        it,
                        android.Manifest.permission.READ_EXTERNAL_STORAGE
                    )
                } != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(
                    activity,
                    arrayOf(
                        android.Manifest.permission.READ_EXTERNAL_STORAGE,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ),
                    REQUEST_CODE_ASK_PERMISSIONS
                )
                return
            }
        }

        getImageFromAlbum()

    }

    private fun getImageFromAlbum() {
        var bottomSheetDialogFragment = TedBottomPicker.Builder(context!!)
            .setOnMultiImageSelectedListener {
                imagessParts.clear()

                val selectedImageUris = it
                var imagesFiles: ArrayList<File> = arrayListOf()

                for (uri in selectedImageUris) {

                    var imagePath = context?.let { getPathFromUri(it, uri!!) }
                    val imageFile = File(imagePath)
                    imagesFiles.add(imageFile)

                }
                convertImageFileToMultiPart(imagesFiles, imagessParts)
                iv?.setImageURI(selectedImageUris[0])
            }
            .setPeekHeight(1600)
            .showTitle(false)
            .setCompleteButtonText("Done")
            .setEmptySelectionText("No Select")
            .create()

        bottomSheetDialogFragment.show(activity?.supportFragmentManager)
    }


    private fun convertImageFileToMultiPart(imageFiles: ArrayList<File>, imagesParts: MutableList<MultipartBody.Part>) {

        for (i in imageFiles.indices) {
            val file = imageFiles[i]
            val requestBody = RequestBody.create(MediaType.parse("image/*"), file)
            val filePart = MultipartBody.Part.createFormData("imgs", file.getName(), requestBody)
            imagesParts.add(filePart)
        }
    }


    fun getPathFromUri(context: Context, uri: Uri): String? {

        val isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                val docId = DocumentsContract.getDocumentId(uri)
                val split = docId.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                val type = split[0]

                if ("primary".equals(type, ignoreCase = true)) {
                    return "${Environment.getExternalStorageDirectory()}/${split[1]}"
                }

                // TODO handle non-primary volumes
            } else if (isDownloadsDocument(uri)) {

                val id = DocumentsContract.getDocumentId(uri)
                val contentUri = ContentUris.withAppendedId(
                    Uri.parse("content://downloads/public_downloads"), java.lang.Long.valueOf(id)
                )

                return getDataColumn(context, contentUri, null, null)
            } else if (isMediaDocument(uri)) {
                val docId = DocumentsContract.getDocumentId(uri)
                val split = docId.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                val type = split[0]

                var contentUri: Uri? = null
                if ("image" == type) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                } else if ("video" == type) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                } else if ("audio" == type) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                }

                val selection = "_id=?"
                val selectionArgs = arrayOf(split[1])

                return getDataColumn(context, contentUri, selection, selectionArgs)
            }// MediaProvider
            // DownloadsProvider
        } else if ("content".equals(uri.getScheme(), ignoreCase = true)) {

            // Return the remote address
            return if (isGooglePhotosUri(uri)) uri.getLastPathSegment() else getDataColumn(context, uri, null, null)

        } else if ("file".equals(uri.getScheme(), ignoreCase = true)) {
            return uri.getPath()
        }// File
        // MediaStore (and general)

        return null
    }

    fun getDataColumn(
        context: Context, uri: Uri?, selection: String?,
        selectionArgs: Array<String>?
    ): String? {

        var cursor: Cursor? = null
        val column = "_data"
        val projection = arrayOf(column)

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null)
            if (cursor != null && cursor!!.moveToFirst()) {
                val index = cursor!!.getColumnIndexOrThrow(column)
                return cursor!!.getString(index)
            }
        } finally {
            if (cursor != null)
                cursor!!.close()
        }
        return null
    }


    fun isExternalStorageDocument(uri: Uri): Boolean {
        return "com.android.externalstorage.documents" == uri.getAuthority()
    }

    fun isDownloadsDocument(uri: Uri): Boolean {
        return "com.android.providers.downloads.documents" == uri.getAuthority()
    }

    fun isMediaDocument(uri: Uri): Boolean {
        return "com.android.providers.media.documents" == uri.getAuthority()
    }

    fun isGooglePhotosUri(uri: Uri): Boolean {
        return "com.google.android.apps.photos.content" == uri.getAuthority()
    }


    companion object {
        private val IMAGE_DIRECTORY = "/demonuts"
    }

}


