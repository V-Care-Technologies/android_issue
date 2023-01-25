package com.example.recyclermulti

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.ext.SdkExtensions.getExtensionVersion
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recyclermulti.Adapters.TemplateListAdapter
import com.example.recyclermulti.helper.Constants
import com.example.recyclermulti.helper.Resource
import com.example.recyclermulti.helper.URIPathHelper

import com.example.recyclermulti.models.req.Categoryreq

import com.example.recyclermulti.models.res.CategoryItem
import com.example.recyclermulti.models.res.QuestionsDataItem

import com.example.recyclermulti.ui.viewmodels.SharedViewModel
import com.example.recyclermulti.databinding.ActivityFormDetailScreenBinding
import com.example.recyclermulti.helper.GlobalData
import com.example.recyclermulti.helper.PrefUtils
import com.example.recyclermulti.models.req.answer.AnswerDataItem
import com.example.recyclermulti.models.req.answer.AnswerItem
import com.example.recyclermulti.models.req.answer.AnswerReq
import com.example.recyclermulti.models.req.getBlock.BlockReq
import com.example.recyclermulti.models.req.getpanchayat.PanchayatReq
import com.example.recyclermulti.models.res.configdata.BlockItem
import com.example.recyclermulti.models.res.configdata.DistrictItem
import com.example.recyclermulti.models.res.configdata.PanchayatItem
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream


@AndroidEntryPoint
class FormDetailScreen : AppCompatActivity() {

    var district: MutableList<DistrictItem> = ArrayList()
    var blocklist: MutableList<BlockItem> = ArrayList()
    var panchayatlist: MutableList<PanchayatItem> = ArrayList()


    var selectedDistrict = "";
    var selectedDistrictId = "";
    var selectedBlock = "";
    var selectedBlockId = "";
    var selectedpanchayat = "";
    var selectedpanchayatId = "";


    var districtStringlist: MutableList<String> = ArrayList()
    var blockStringlist: MutableList<String> = ArrayList()
    var panchayatStringlist: MutableList<String> = ArrayList()


    lateinit var binding: ActivityFormDetailScreenBinding
    lateinit var category_id: String
    private val sharedViewModel: SharedViewModel by viewModels()
    lateinit var bundle: Bundle
    var imageAdapter: ImageAdapter? = null
    lateinit var questionList: MutableList<QuestionsDataItem>
    var newquestionlist: MutableList<QuestionsDataItem> = ArrayList()
    var position: Int = 0
    var imageid: String = "";
    var multi_image_stringlist: MutableList<String> = ArrayList()
    var video_list = "";


    fun persistImage(bitmap: Bitmap, name: String): File {
        val filesDir: File = applicationContext.getFilesDir()
        val imageFile = File(filesDir, "$name.jpg")
        val os: OutputStream
        try {
            os = FileOutputStream(imageFile)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os)
            os.flush()
            os.close()
        } catch (e: Exception) {
            Log.e(javaClass.simpleName, "Error writing bitmap", e)
        }
        val file_size: Int = (imageFile.length() / 1024).toString().toInt()

        return imageFile
        Log.d("imagesize", file_size.toString());
    }


    private val templateListAdapter: TemplateListAdapter by lazy {
        TemplateListAdapter(
            {
                position = it.position
                imageid = it.questionsitemid;

                val file = persistImage(it.image, it.questionname);
                val reqFile = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())

                val requestFile =
                    RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file)
                val requestFile2 = RequestBody.create("image/*".toMediaTypeOrNull(), file)

                val parts: MultipartBody.Part =
                    MultipartBody.Part.createFormData("image", file.getName(), reqFile)

                val file_size: Int = (file.length() / 1024).toString().toInt()
                Log.d("filesize", it.image.toString());
                Log.d("filesize", file_size.toString());
                sharedViewModel.uploadImage(parts)

            }, this@FormDetailScreen,
            PrefUtils.getStringPref(GlobalData.LANGUAGE_SELECTED, this@FormDetailScreen)

        );

    }


    var pathCollectionImage: MutableList<Uri> = ArrayList()
    private lateinit var pickMultipleMediaLauncher: ActivityResultLauncher<Intent>
    private lateinit var MediaPermissions: ActivityResultLauncher<Array<String>>
    val multipleImage = 101
    val video = 112
    val pickMultipleMedia =
        registerForActivityResult(ActivityResultContracts.PickMultipleVisualMedia(4)) { uris ->
            // Callback is invoked after the user selects media items or closes the
            // photo picker.
            if (uris.isNotEmpty()) {
                pathCollectionImage.clear()
                pathCollectionImage.addAll(uris)

                uploadImages(pathCollectionImage)

                imageAdapter = ImageAdapter(this, pathCollectionImage)
                binding.footer.rvImages.setAdapter(imageAdapter)
                binding.footer.rvImages.setLayoutManager(
                    GridLayoutManager(
                        this,
                        2,
                        GridLayoutManager.HORIZONTAL,
                        false
                    )
                )
                Log.d("PhotoPicker", "Number of items selected: ${uris.size}")
            } else {


                Log.d("PhotoPicker", "No media selected")
            }
        }
    val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        // Callback is invoked after the user selects a media item or closes the
        // photo picker.
        if (uri != null) {
            binding.footer.ivVideo.visibility = View.VISIBLE
            binding.footer.ivVideo.setVideoURI(uri)
            binding.footer.ivVideo.seekTo(1);
            var parts: MultipartBody.Part = prepareVideoFilePart(uri)

            sharedViewModel.uploadVideo(parts)


            Log.d("PhotoPicker", "Selected URI: $uri")
        } else {

            Log.d("PhotoPicker", "No media selected")
        }
    }

    var user_id = "";

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormDetailScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvRecyclerAdditem.adapter = templateListAdapter
        binding.rvRecyclerAdditem.layoutManager = LinearLayoutManager(this)
        binding.rvRecyclerAdditem.itemAnimator = null


        /*bundle = intent.extras!!
        var user = bundle!!.getParcelable<CategoryItem>(Constants.CATEGORY_ID)!!*/
/*
        if (PrefUtils.getStringPref(GlobalData.LANGUAGE_SELECTED, this@FormDetailScreen)
                .equals("1")
        ) {
            binding!!.tvProfileTxt.text = user.name;

        } else {
            binding!!.tvProfileTxt.text = user.nameHn;

        }*/

        category_id = "4"


        if (category_id.equals("6")) {
            binding.header.tvTitlePanchayat.visibility = View.GONE
            binding.header.spinnerPanchayat.visibility = View.GONE
            binding.header.spinnerDropDownPanchayat.visibility = View.GONE

        } else {
            binding.header.tvTitlePanchayat.visibility = View.VISIBLE

            binding.header.spinnerPanchayat.visibility = View.VISIBLE
            binding.header.spinnerDropDownPanchayat.visibility = View.VISIBLE
        }

        getData(category_id)

        attachObservers()
        user_id = "27"
        binding!!.ivBack.setOnClickListener {
            onBackPressed()
        }

        MediaPermissions =
            registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { result ->
                var readImages =
                    result.getOrDefault(Manifest.permission.READ_MEDIA_IMAGES, false)
                var readVideos =
                    result.getOrDefault(Manifest.permission.READ_MEDIA_VIDEO, false)

                if (readImages && readVideos) {
                    handlePhotoPickerLaunch()
                } else {
                    Toast.makeText(
                        this,
                        "permission are required to work please enable from settings",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }


        binding.footer.btnImage.setOnClickListener {
            checkPermission()
        }

        binding.footer.btnVideo.setOnClickListener {
            handleVideoPicker()
        }

        binding.footer.btnSave.setOnClickListener {


            if (selectedDistrict.isEmpty() || selectedDistrict.equals("Select District")) {
                Toast.makeText(this@FormDetailScreen, "Select District", Toast.LENGTH_SHORT).show()

            } else if (selectedDistrict.isEmpty() || selectedDistrict.equals("Select Block")) {
                Toast.makeText(this@FormDetailScreen, "Select Block", Toast.LENGTH_SHORT).show()

            } else if (selectedDistrict.isEmpty() || selectedDistrict.equals("Select Panchayat")) {
                Toast.makeText(this@FormDetailScreen, "Select Panchayat", Toast.LENGTH_SHORT).show()
            } else {
                var answer_main_DataItem: MutableList<AnswerDataItem> = ArrayList();





                for (i in 0 until templateListAdapter.currentList.size) {

                    var answerDataItem: MutableList<AnswerDataItem> = ArrayList();


                    var option_id: MutableList<String?> = ArrayList();
                    var answername: MutableList<String?> = ArrayList();
                    var answeritemlist: MutableList<AnswerItem> = ArrayList();
                    if (!templateListAdapter.currentList.get(i).type.equals("1")) {
                        if (templateListAdapter.currentList[i].options!!.size != 0) {
                            for (y in 0 until templateListAdapter.currentList[i].options!!.size) {
                                if (templateListAdapter.currentList[i].options!![y].isChecked) {
                                    option_id.add(
                                        templateListAdapter.currentList[i].options!![y].id
                                    )
                                    answername.add(
                                        templateListAdapter.currentList[i].options!![y].options
                                    )


                                }
                            }
                            Log.d(
                                "selected_",
                                "" + Gson().toJson(
                                    answername
                                )
                            )
                        } else {
                            option_id.add(
                                templateListAdapter.currentList[i].passId
                            )
                            answername.add(
                                templateListAdapter.currentList[i].passData
                            );
                        }
                    } else {
                        option_id.add(
                            templateListAdapter.currentList[i].passId
                        )
                        answername.add(
                            templateListAdapter.currentList[i].passData
                        );
                    }

                    val option_id_string: String = java.lang.String.join("|", option_id)
                    val answer_string: String = java.lang.String.join("|", answername)

                    if(templateListAdapter.currentList.get(i).type.equals("9")){
                        Log.d("multiadapter", Gson().toJson(templateListAdapter.currentList.get(i).multiModel))
                    }

                    var answerItem: AnswerItem = AnswerItem(
                        templateListAdapter.currentList.get(i).passId,
                        templateListAdapter.currentList.get(i).question,
                        option_id_string,
                        answer_string
                    );



                    answeritemlist.add(answerItem)
/*
                        answerDataItem.add(AnswerDataItem(templateListAdapter.currentList.get(i).id,answeritemlist))
*/


                    val finalDataItem: AnswerDataItem = AnswerDataItem();
                    finalDataItem.answer = answeritemlist;
                    finalDataItem.questionsId = templateListAdapter.currentList.get(i).id



                    answer_main_DataItem.add(finalDataItem)
                }




                Log.d("globalLocation", GlobalData.EndLocation.latitude.toString())

                val multi_image_string: String = java.lang.String.join(",", multi_image_stringlist)

                Log.d("option_size", "" + Gson().toJson(answer_main_DataItem))

                var answerReq: AnswerReq = AnswerReq(
                    "",
                    user_id,
                    category_id,
                    selectedDistrictId,
                    selectedBlockId,
                    selectedpanchayatId,
                    "1",
                    "0",
                    GlobalData.EndLocation.latitude.toString(),
                    GlobalData.EndLocation.longitude.toString(),

                    multi_image_string,
                    video_list,
                    answer_main_DataItem


                )


/*
                sharedViewModel.addAnswer(answerReq);*/


            }
        }

        binding.footer.btnSubmit.setOnClickListener {


            if (selectedDistrict.isEmpty() || selectedDistrict.equals("Select District")) {
                Toast.makeText(this@FormDetailScreen, "Select District", Toast.LENGTH_SHORT).show()

            } else if (selectedDistrict.isEmpty() || selectedDistrict.equals("Select Block")) {
                Toast.makeText(this@FormDetailScreen, "Select Block", Toast.LENGTH_SHORT).show()

            } else if (selectedDistrict.isEmpty() || selectedDistrict.equals("Select Panchayat")) {
                Toast.makeText(this@FormDetailScreen, "Select Panchayat", Toast.LENGTH_SHORT).show()
            } else {
                var answer_main_DataItem: MutableList<AnswerDataItem> = ArrayList();





                for (i in 0 until templateListAdapter.currentList.size) {

                    var answerDataItem: MutableList<AnswerDataItem> = ArrayList();


                    var option_id: MutableList<String?> = ArrayList();
                    var answername: MutableList<String?> = ArrayList();
                    var answeritemlist: MutableList<AnswerItem> = ArrayList();
                    if (!templateListAdapter.currentList.get(i).type.equals("1")) {
                        if (templateListAdapter.currentList[i].options!!.size != 0) {
                            for (y in 0 until templateListAdapter.currentList[i].options!!.size) {
                                if (templateListAdapter.currentList[i].options!![y].isChecked) {
                                    option_id.add(
                                        templateListAdapter.currentList[i].options!![y].id
                                    )
                                    answername.add(
                                        templateListAdapter.currentList[i].options!![y].options
                                    )


                                }
                            }
                            Log.d(
                                "selected_",
                                "" + Gson().toJson(
                                    answername
                                )
                            )
                        } else {
                            option_id.add(
                                templateListAdapter.currentList[i].passId
                            )
                            answername.add(
                                templateListAdapter.currentList[i].passData
                            );
                        }
                    } else {
                        option_id.add(
                            templateListAdapter.currentList[i].passId
                        )
                        answername.add(
                            templateListAdapter.currentList[i].passData
                        );
                    }

                    val option_id_string: String = java.lang.String.join("|", option_id)
                    val answer_string: String = java.lang.String.join("|", answername)

                    Log.d("option_size", "" + Gson().toJson(answername))


                    var answerItem: AnswerItem = AnswerItem(
                        templateListAdapter.currentList.get(i).passId,
                        templateListAdapter.currentList.get(i).question,
                        option_id_string,
                        answer_string
                    );



                    answeritemlist.add(answerItem)
/*
                        answerDataItem.add(AnswerDataItem(templateListAdapter.currentList.get(i).id,answeritemlist))
*/


                    val finalDataItem: AnswerDataItem = AnswerDataItem();
                    finalDataItem.answer = answeritemlist;
                    finalDataItem.questionsId = templateListAdapter.currentList.get(i).id



                    answer_main_DataItem.add(finalDataItem)
                }
                Log.d("globalLocation", GlobalData.EndLocation.latitude.toString())

                val multi_image_string: String = java.lang.String.join(",", multi_image_stringlist)


                var answerReq: AnswerReq = AnswerReq(
                    "",
                    user_id,
                    category_id,
                    selectedDistrictId,
                    selectedBlockId,
                    selectedpanchayatId,
                    "0",
                    "1",
                    GlobalData.EndLocation.latitude.toString(),
                    GlobalData.EndLocation.longitude.toString(),

                    multi_image_string,
                    video_list,
                    answer_main_DataItem


                )




                sharedViewModel.addAnswer(answerReq);


            }

        }


    }


    private fun checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.READ_MEDIA_IMAGES
                ) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.READ_MEDIA_VIDEO
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                handlePhotoPickerLaunch();
            } else {
                MediaPermissions.launch(
                    arrayOf(
                        Manifest.permission.READ_MEDIA_IMAGES,
                        Manifest.permission.READ_MEDIA_VIDEO
                    )
                )
            }
        } else {
            if (ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                defaultPicker();
            } else {
                requestPermissions(
                    arrayOf(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                    ), 1
                )
            }


        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                defaultPicker()
            } else {
                Toast.makeText(this, "permission required", Toast.LENGTH_SHORT).show()
            }


        }

    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun isPhotoPickerAvailable(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            true
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            getExtensionVersion(Build.VERSION_CODES.R) >= 2
        } else {
            false
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    fun handleVideoPicker() {
        if (isPhotoPickerAvailable()) {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.VideoOnly))
        } else {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(Intent.createChooser(intent, "Select Video"), video)
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    fun handlePhotoPickerLaunch() {
        if (isPhotoPickerAvailable()) {
            // To launch the system photo picker, invoke an intent that includes the
            // ACTION_PICK_IMAGES action. Consider adding support for the
            // EXTRA_PICK_IMAGES_MAX intent extra.
            pickMultipleMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))


        } else {
            val intent = Intent()
            intent.type = "image/*"
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(
                Intent.createChooser(intent, "Select Picture"),
                multipleImage
            )

            // Consider implementing fallback functionality so that users can still
            // select images and videos.
        }
    }

    fun uploadImages(paths: List<Uri>) {
        var list: MutableList<MultipartBody.Part> = ArrayList();

        for (i in 0 until paths.size) {
            var imageRequest: MultipartBody.Part = prepareFilePart(paths.get(i));
            list.add(imageRequest);
        }

        sharedViewModel.uploadMultipleimage(list)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == multipleImage && resultCode == RESULT_OK) {
            pathCollectionImage.clear()
            var count = data!!.clipData
            if (count != null) {
                if (count.itemCount > 4) {


                    Toast.makeText(
                        this,
                        "Can Select maximum of 4 images",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    for (i in 0 until count.itemCount) {
                        pathCollectionImage.add(data.clipData!!.getItemAt(i).uri)
                    }
                    imageAdapter = ImageAdapter(this, pathCollectionImage);
                    binding.footer.rvImages.setAdapter(imageAdapter)
                    binding.footer.rvImages.setLayoutManager(
                        GridLayoutManager(
                            this,
                            2,
                            GridLayoutManager.HORIZONTAL,
                            false
                        )
                    )
                    uploadImages(pathCollectionImage)


                }
            } else {
                pathCollectionImage.clear()
                pathCollectionImage.add(data.data!!)
                imageAdapter = ImageAdapter(this, pathCollectionImage)
                binding.footer.rvImages.setAdapter(imageAdapter)
                binding.footer.rvImages.setLayoutManager(
                    GridLayoutManager(
                        this,
                        1,
                        GridLayoutManager.HORIZONTAL,
                        false
                    )
                )
                uploadImages(pathCollectionImage)

            }
            /*if (count != null) {
                    for (i in 0 until count.itemCount) {
                        pathCollectionImage.add(data!!.clipData!!.getItemAt(i).uri)
                    }
                    imageAdapter = ImageAdapter(this, pathCollectionImage)
                    binding.recyclerImages.setAdapter(imageAdapter)
                    binding.recyclerImages.setLayoutManager(
                        GridLayoutManager(
                            this,
                            2,
                            GridLayoutManager.HORIZONTAL,
                            false
                        )
                    )
                }*/

        } else if (requestCode == video && resultCode == Activity.RESULT_OK) {
            binding.footer.ivVideo.visibility = View.VISIBLE
            binding.footer.ivVideo.setVideoURI(data!!.data)
            binding.footer.ivVideo.seekTo(1);
            var parts: MultipartBody.Part = prepareVideoFilePart(data!!.data!!)

            sharedViewModel.uploadVideo(parts)
        }
    }

    private fun prepareFilePart(fileUri: Uri): MultipartBody.Part {
        val uriPathHelper = URIPathHelper()
        val filePath = uriPathHelper.getPath(this, fileUri)


        val file = File(filePath)
        val reqFile = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
        val file_size: Int = (file.length() / 1024).toString().toInt()

        Log.d("multiple_file_path", file.path.toString());

        Log.d("multiple_file_size", file_size.toString());

        // MultipartBody.Part is used to send also the actual file name
        return MultipartBody.Part.createFormData("image[]", file.name, reqFile)
    }


    private fun prepareVideoFilePart(fileUri: Uri): MultipartBody.Part {
        val uriPathHelper = URIPathHelper()
        val filePath = uriPathHelper.getPath(this, fileUri)


        val file = File(filePath)
        val reqFile = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
        val file_size: Int = (file.length() / 1024).toString().toInt()

        Log.d("multiple_file_path", file.path.toString());

        Log.d("multiple_file_size", file_size.toString());

        // MultipartBody.Part is used to send also the actual file name
        return MultipartBody.Part.createFormData("video", file.name, reqFile)
    }

    fun defaultPicker() {
        val intent = Intent()
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(
            Intent.createChooser(intent, "Select Picture"),
            multipleImage
        )
    }


    fun getData(id: String) {
        sharedViewModel.getQuestions(Categoryreq(id))
        sharedViewModel.getConfig()
    }


    private fun attachObservers() {
        lifecycleScope.launchWhenCreated {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                sharedViewModel.questionsResponse.asLiveData().observe(this@FormDetailScreen) {
                    when (it) {
                        is Resource.Empty -> {
                        }
                        is Resource.Error -> {
                            Toast.makeText(
                                this@FormDetailScreen,
                                "Something went wrong",
                                Toast.LENGTH_SHORT
                            ).show();
                        }
                        is Resource.Loading -> {
                            binding!!.progressBar.progressBarLayout.visibility =
                                View.VISIBLE
                            binding!!.footer.root.visibility = View.GONE
                            binding!!.header.root.visibility = View.GONE

                        }
                        is Resource.Success -> {
                            val gson = Gson()
                            val json = gson.toJson(it.data)
                            Log.d("questionsdata", json)

                            if (templateListAdapter?.currentList.isEmpty()) {
                                questionList = (it.data!!.data as MutableList<QuestionsDataItem>?)!!

                                for (j in 0 until questionList.size) {
                                    newquestionlist.add(questionList.get(j));

                                }


                                binding.rvRecyclerAdditem.adapter = templateListAdapter


                                templateListAdapter.submitList(ArrayList(newquestionlist))

                            }
                            binding!!.footer.root.visibility = View.VISIBLE
                            binding!!.header.root.visibility = View.VISIBLE

                            binding!!.progressBar.progressBarLayout.visibility = View.GONE


                        }
                    }
                }
            }
        }


        lifecycleScope.launchWhenCreated {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                sharedViewModel.singleUploadResponse.asLiveData().observeForever {
                    when (it) {
                        is Resource.Empty -> {
                        }
                        is Resource.Error -> {
                            Toast.makeText(
                                this@FormDetailScreen,
                                it.message,
                                Toast.LENGTH_SHORT
                            ).show();

                            Log.d("error_message", Gson().toJson(it))


                        }
                        is Resource.Loading -> {
                            binding!!.progressBar.progressBarLayout.visibility =
                                View.VISIBLE


                        }
                        is Resource.Success -> {
                            binding!!.progressBar.progressBarLayout.visibility = View.GONE

                            val gson = Gson()
                            val json = gson.toJson(it.data)
                            Log.d("imageresponse", json)


                            templateListAdapter.currentList.get(position).passData = it.data!!.data
                            templateListAdapter.currentList.get(position).passId = imageid

                            val newj = Gson().toJson(it.data)
                            Log.d("setdata", templateListAdapter.currentList.get(position).passData)
                            Log.d(
                                "checkdata",
                                Gson().toJson(templateListAdapter.currentList.get(position))
                            )
                            sharedViewModel.singleUploadResponse.asLiveData()
                                .removeObservers(this@FormDetailScreen)

                        }
                    }
                }

            }
        }

        lifecycleScope.launchWhenCreated {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                sharedViewModel.multiUploadResponse.asLiveData().observeForever {
                    when (it) {
                        is Resource.Empty -> {
                        }
                        is Resource.Error -> {
                            Toast.makeText(
                                this@FormDetailScreen,
                                it.message,
                                Toast.LENGTH_SHORT
                            ).show();

                            Log.d("error_message", Gson().toJson(it))


                        }
                        is Resource.Loading -> {
                            binding!!.progressBar.progressBarLayout.visibility =
                                View.VISIBLE


                        }
                        is Resource.Success -> {
                            binding!!.progressBar.progressBarLayout.visibility = View.GONE

                            val gson = Gson()
                            val json = gson.toJson(it.data)
                            Log.d("imageresponse", json)
                            var images_data = "";


                            multi_image_stringlist.clear()
                            for (i in 0 until it.data!!.data.size) {
                                images_data = images_data + it.data!!.data.get(i).toString() + ","

                                multi_image_stringlist.add(it.data!!.data.get(i).images);
                            }




                            Log.d("strings", gson.toJson(multi_image_stringlist))




                            sharedViewModel.multiUploadResponse.asLiveData()
                                .removeObservers(this@FormDetailScreen)

                        }
                    }
                }

            }

        }

        lifecycleScope.launchWhenCreated {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                sharedViewModel.videoUploadResponse.asLiveData().observeForever {
                    when (it) {
                        is Resource.Empty -> {
                        }
                        is Resource.Error -> {
                            Toast.makeText(
                                this@FormDetailScreen,
                                it.message,
                                Toast.LENGTH_SHORT
                            ).show();

                            Log.d("error_message", Gson().toJson(it))


                        }
                        is Resource.Loading -> {
                            binding!!.progressBar.progressBarLayout.visibility =
                                View.VISIBLE


                        }
                        is Resource.Success -> {
                            binding!!.progressBar.progressBarLayout.visibility = View.GONE

                            val gson = Gson()
                            val json = gson.toJson(it.data)
                            Log.d("imageresponse", json)
                            var video_data = "";

                            Log.d("strings", it.data!!.data)

                            video_list = it.data.data


                            sharedViewModel.videoUploadResponse.asLiveData()
                                .removeObservers(this@FormDetailScreen)

                        }
                    }
                }

            }

        }

        lifecycleScope.launchWhenCreated {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                sharedViewModel.configresponse.asLiveData().observeForever {
                    when (it) {
                        is Resource.Empty -> {
                        }
                        is Resource.Error -> {
                            Toast.makeText(
                                this@FormDetailScreen,
                                it.message,
                                Toast.LENGTH_SHORT
                            ).show();

                            Log.d("error_message", Gson().toJson(it))


                        }
                        is Resource.Loading -> {
                            binding!!.progressBar.progressBarLayout.visibility =
                                View.VISIBLE


                        }
                        is Resource.Success -> {
                            binding!!.progressBar.progressBarLayout.visibility = View.GONE
                            blocklist.clear()
                            panchayatlist.clear()
                            district.clear()


                            district.addAll(it.data!!.district)


                            setDistrictAdapter(district)



                            sharedViewModel.configresponse.asLiveData()
                                .removeObservers(this@FormDetailScreen)

                        }
                    }
                }

            }
        }



        lifecycleScope.launchWhenCreated {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                sharedViewModel.blockresponse.asLiveData().observeForever {
                    when (it) {
                        is Resource.Empty -> {
                        }
                        is Resource.Error -> {
                            Toast.makeText(
                                this@FormDetailScreen,
                                it.message,
                                Toast.LENGTH_SHORT
                            ).show();

                            Log.d("error_message", Gson().toJson(it))


                        }
                        is Resource.Loading -> {
                            binding!!.progressBar.progressBarLayout.visibility =
                                View.VISIBLE


                        }
                        is Resource.Success -> {
                            binding!!.progressBar.progressBarLayout.visibility = View.GONE
                            blocklist.clear()
                            blocklist.addAll(it.data!!.data)





                            setBlock(it.data!!.data)



                            sharedViewModel.blockresponse.asLiveData()
                                .removeObservers(this@FormDetailScreen)

                        }
                    }
                }

            }
        }




        lifecycleScope.launchWhenCreated {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                sharedViewModel.panchayatresponse.asLiveData().observeForever {
                    when (it) {
                        is Resource.Empty -> {
                        }
                        is Resource.Error -> {
                            Toast.makeText(
                                this@FormDetailScreen,
                                it.message,
                                Toast.LENGTH_SHORT
                            ).show();

                            Log.d("error_message", Gson().toJson(it))


                        }
                        is Resource.Loading -> {
                            binding!!.progressBar.progressBarLayout.visibility =
                                View.VISIBLE


                        }
                        is Resource.Success -> {
                            binding!!.progressBar.progressBarLayout.visibility = View.GONE
                            panchayatlist.clear()
                            panchayatlist.addAll(it.data!!.data)





                            setPanchayat(it.data!!.data)



                            sharedViewModel.panchayatresponse.asLiveData()
                                .removeObservers(this@FormDetailScreen)

                        }
                    }
                }

            }
        }



        lifecycleScope.launchWhenCreated {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                sharedViewModel.addAnswerResponse.asLiveData().observeForever {
                    when (it) {
                        is Resource.Empty -> {
                        }
                        is Resource.Error -> {
                            Toast.makeText(
                                this@FormDetailScreen,
                                it.message,
                                Toast.LENGTH_SHORT
                            ).show();

                            Log.d("error_message", Gson().toJson(it))


                        }
                        is Resource.Loading -> {
                            binding!!.progressBar.progressBarLayout.visibility =
                                View.VISIBLE


                        }
                        is Resource.Success -> {
                            binding!!.progressBar.progressBarLayout.visibility = View.GONE


                            Toast.makeText(
                                this@FormDetailScreen,
                                "data inserted successfully",
                                Toast.LENGTH_LONG
                            ).show()


                            onBackPressed()



                            sharedViewModel.addAnswerResponse.asLiveData()
                                .removeObservers(this@FormDetailScreen)

                        }
                    }
                }

            }
        }

    }


    fun setDistrictAdapter(list: MutableList<DistrictItem>) {
        districtStringlist.clear()
        districtStringlist.add("Select District")
        for (i in 0 until list.size) {
            districtStringlist.add(list.get(i).name)
        }
        val districtAdapter = object :
            ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, districtStringlist) {

            override fun isEnabled(position: Int): Boolean {
                // Disable the first item from Spinner
                // First item will be used for hint
                return position != 0
            }

            override fun getDropDownView(
                position: Int,
                convertView: View?,
                parent: ViewGroup
            ): View {
                val view: TextView =
                    super.getDropDownView(position, convertView, parent) as TextView
                //set the color of first item in the drop down list to gray
                if (position == 0) {
                    view.setTextColor(Color.GRAY)
                } else {
                    view.setTextColor(Color.BLACK)

                    //here it is possible to define color for other items by
                    //view.setTextColor(Color.RED)
                }
                return view
            }
        }
        districtAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.header.spinnerDistrict.adapter = districtAdapter

        binding.header.spinnerDistrict.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    p2: Int,
                    p3: Long
                ) {

                    val value = parent!!.getItemAtPosition(p2).toString()
                    if (value == districtStringlist[0]) {
                        (view as TextView).setTextColor(Color.GRAY)
                    }
                    selectedDistrict = value

                    for (i in 0 until district.size) {
                        if (selectedDistrict.equals(district.get(i).name)) {
                            selectedDistrictId = district.get(i).id;
                        }
                    }
                    sharedViewModel.getBlock(BlockReq(selectedDistrictId))


                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                }
            }

    }


    fun setBlock(blocklistmain: MutableList<BlockItem>) {


        blockStringlist.clear()
        blockStringlist.add("Select Block")
        for (i in 0 until blocklistmain.size) {
            blockStringlist.add(blocklistmain.get(i).name);
        }


        val blockadapter = object :
            ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, blockStringlist) {

            override fun isEnabled(position: Int): Boolean {
                // Disable the first item from Spinner
                // First item will be used for hint
                return position != 0
            }

            override fun getDropDownView(
                position: Int,
                convertView: View?,
                parent: ViewGroup
            ): View {
                val view: TextView =
                    super.getDropDownView(position, convertView, parent) as TextView
                //set the color of first item in the drop down list to gray
                if (position == 0) {
                    view.setTextColor(Color.GRAY)
                } else {
                    view.setTextColor(Color.BLACK)


                    //here it is possible to define color for other items by
                    //view.setTextColor(Color.RED)
                }
                return view
            }
        }
        blockadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.header.spinnerBlock.adapter = blockadapter

        binding.header.spinnerBlock.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    p2: Int,
                    p3: Long
                ) {

                    val value = parent!!.getItemAtPosition(p2).toString()
                    if (value == blockStringlist[0]) {
                        (view as TextView).setTextColor(Color.GRAY)
                    }
                    selectedBlock = value

                    for (i in 0 until blocklistmain.size) {
                        if (selectedBlock.equals(blocklistmain.get(i).name)) {
                            selectedBlockId = blocklistmain.get(i).id;
                        }
                    }
                    sharedViewModel.getPanchayat(PanchayatReq(selectedBlockId))


                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                }
            }

    }


    fun setPanchayat(panchayatmainlist: MutableList<PanchayatItem>) {

        panchayatStringlist.clear()
        panchayatStringlist.add("Select Panchayat")
        for (i in 0 until panchayatmainlist.size) {
            panchayatStringlist.add(panchayatmainlist.get(i).name);
        }
        val panchayatAdapter = object :
            ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, panchayatStringlist) {

            override fun isEnabled(position: Int): Boolean {
                // Disable the first item from Spinner
                // First item will be used for hint
                return position != 0
            }

            override fun getDropDownView(
                position: Int,
                convertView: View?,
                parent: ViewGroup
            ): View {
                val view: TextView =
                    super.getDropDownView(position, convertView, parent) as TextView
                //set the color of first item in the drop down list to gray
                if (position == 0) {
                    view.setTextColor(Color.GRAY)
                } else {
                    view.setTextColor(Color.BLACK)

                    //here it is possible to define color for other items by
                    //view.setTextColor(Color.RED)
                }
                return view
            }
        }
        panchayatAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.header.spinnerPanchayat.adapter = panchayatAdapter

        binding.header.spinnerPanchayat.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    p2: Int,
                    p3: Long
                ) {

                    val value = parent!!.getItemAtPosition(p2).toString()
                    if (value == panchayatStringlist[0]) {
                        (view as TextView).setTextColor(Color.GRAY)
                    }
                    selectedpanchayat = value

                    for (i in 0 until panchayatlist.size) {
                        if (selectedpanchayat.equals(panchayatlist.get(i).name)) {
                            selectedpanchayatId = panchayatlist.get(i).id;
                        }
                    }
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                }
            }

    }
}


/*override fun onInsertClick(id: String, position: Int) {
    var range: Int = 0;
    var addeditem: MutableList<QuestionsDataItem> = ArrayList()
    for (i in 0 until questionList.size) {
        Log.d("datalist", "" + Gson().toJson(adapter.itemList))
        if (questionList.get(i).quesOptionId.equals(id)) {
            addeditem.add(questionList.get(i))
            newquestionlist.add(i+1,questionList.get(i))


            range = range + 1


*//*
                adapter.addData(questionList.get(i))
*//*
                Log.d("data_added", "" + Gson().toJson(questionList.get(i)))
                Log.d("added_list", "" + Gson().toJson(range))

            }

        }
        adapter.notifyDataSetChanged()
    *//*
        if (addeditem.size != 0) {
            newquestionlist.addAll(
                Integer.parseInt(addeditem.get(0).displayOrder) - 1, addeditem
            )
            adapter.notifyItemRangeInserted(
                Integer.parseInt(addeditem.get(0).displayOrder),
                addeditem.size
            )
        }*//*


*//*
        adapter.setData(newquestionlist)
*//*

    }

    override fun onDeleteClick(id: String, position: Int) {
        for (i in 0 until questionList.size) {

        }


    }*/



