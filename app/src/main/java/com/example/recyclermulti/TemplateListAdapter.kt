package com.example.recyclermulti.Adapters

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.net.Uri
import android.os.Environment
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclermulti.CheckboxAdapter
import com.example.recyclermulti.DependentNewAdapter
import com.example.recyclermulti.MultiAdapter.MultiAdapter
import com.example.recyclermulti.MultiAdapter.MultiNewAdapter
import com.example.recyclermulti.R
import com.example.recyclermulti.databinding.ActivitySignatureBinding
import com.example.recyclermulti.databinding.RvTemplateItemBinding
import com.example.recyclermulti.models.MultiEtModel.MultiModel
import com.example.recyclermulti.models.MultiEtModel.MultiModelThree
import com.example.recyclermulti.models.res.QuestionsDataItem
import com.github.gcacace.signaturepad.views.SignaturePad
import com.google.gson.Gson
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.*


data class DependentId(
    var questionsitemid: String,
    var questionname: String,
    var position: Int,
    var image: Bitmap
)

class TemplateListAdapter(
    var SingleSignature: (DependentId) -> Unit,
    val context: Context,
   var isLanguageEnglish: String

    ) :
    ListAdapter<QuestionsDataItem, TemplateListAdapter.TemplateListViewHolder>(DiffUtilCallback) {


    inner class TemplateListViewHolder(val binding: RvTemplateItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        

        fun bind(questionitem: QuestionsDataItem,isLanguageEnglish: String) {
            Log.d("isLanguage",isLanguageEnglish)
            Log.d("hindiquestions", Gson().toJson(questionitem.questionHn))
            binding.apply {
                var name = questionitem.question;

                /*if(!questionitem.dependentQuesId.equals("0")){
                    this.tvTitleText.visibility=View.GONE
                    this.rgTemplate.visibility=View.GONE
                    this.etTemplate.visibility=View.GONE
                    this.cbTemplate.visibility=View.GONE
                    this.templateClSpn.visibility=View.GONE
                    this.ivSign.visibility=View.GONE
                    this.etOtherInformation.visibility=View.GONE
                    this.clSignture.visibility=View.GONE
                    this.llCbtemplate.visibility=View.GONE
                    this.tvDateTemp.visibility=View.GONE
                    llCbtemplate.visibility = View.GONE

                }*/

                if(isLanguageEnglish.equals("2")){
                    this.tvTitleText.text = questionitem.questionHn

                }else{
                    this.tvTitleText.text = questionitem.question
                }


                if (questionitem.type.equals("1") && questionitem.dependentQuesId.equals("0")) {
                    binding.rgTemplate.removeAllViews()
                    tvTitleText.visibility = View.VISIBLE


                    val textsize: Float =
                        context.getResources().getDimensionPixelSize(com.intuit.ssp.R.dimen._5ssp)
                            .toFloat()
                    for (i in 0 until questionitem.options!!.size) {
                        var radioButton = RadioButton(context)
                        radioButton.id = questionitem.options.get(i).id!!.toInt()

                        if(isLanguageEnglish.equals("2")){
                            radioButton.text = questionitem.options.get(i).optionsHn

                        }else{
                            radioButton.text = questionitem.options.get(i).options
                        }

                        radioButton.tag = questionitem.options.get(i).isDependent
                        radioButton.buttonTintList =
                            ColorStateList.valueOf(context.getColor(R.color.black))
                        radioButton.setPadding(20, 0, 0, 0)
                        val typeface =
                            ResourcesCompat.getFont(context, com.example.recyclermulti.R.font.inter_regular)
                        radioButton.textSize = textsize;
                        radioButton.typeface = typeface
                        val params = RadioGroup.LayoutParams(
                            RadioGroup.LayoutParams.WRAP_CONTENT,
                            RadioGroup.LayoutParams.WRAP_CONTENT
                        )



                        rgTemplate.addView(radioButton, params)




                        this.rgTemplate.visibility = View.VISIBLE
                    }


                } else if (questionitem.type.equals("2") && questionitem.dependentQuesId.equals("0")) {
                    binding.llCbtemplate.removeAllViews()
                    tvTitleText.visibility = View.VISIBLE
                    val textsize: Float =
                        context.getResources().getDimensionPixelSize(com.intuit.ssp.R.dimen._5ssp)
                            .toFloat()
                    rvCbtemplate.visibility = View.VISIBLE

                    if(isLanguageEnglish.equals("2")){

                        this.tvTitleText.text = questionitem.questionHn

                    }else{
                        this.tvTitleText.text = questionitem.question
                    }
                    if (questionitem.question!!.isEmpty()) {
                        tvTitleText.visibility = View.GONE
                    } else {
                        tvTitleText.visibility = View.VISIBLE

                    }

                    /*val twelveDp = TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP, 6f,
                        context.getResources().getDisplayMetrics()
                    )
                    val typeface =
                        ResourcesCompat.getFont(context, com.example.recyclermulti.R.font.inter_regular)
                    val checkBox = CheckBox(context)
                    checkBox.text = questionitem.options[i].options
                    checkBox.tag=questionitem.options[i].id;
                    checkBox.setButtonDrawable(R.drawable.cb_selector)
                    checkBox.setPadding(20, 0, 0, 0)
                    checkBox.compoundDrawablePadding = 0
                    checkBox.buttonTintList =
                        ColorStateList.valueOf(context.getColor(R.color.black))
                    checkBox.textSize = textsize
                    checkBox.typeface = typeface
                    llCbtemplate.addView(checkBox)*/


                    var checkboxAdapter: CheckboxAdapter =
                        CheckboxAdapter(context, questionitem.options!!,isLanguageEnglish,"")
                    binding.rvCbtemplate.adapter = checkboxAdapter
                    binding.rvCbtemplate.layoutManager = LinearLayoutManager(context);


                } else if (questionitem.type.equals("3") && questionitem.dependentQuesId.equals("0")) {

                    tvTitleText.visibility = View.VISIBLE
                    if(isLanguageEnglish.equals("2")){

                        this.tvTitleText.text = questionitem.questionHn

                    }else{
                        this.tvTitleText.text = questionitem.question
                    }
                    this.etTemplate.visibility = View.VISIBLE
                    this.etTemplate.inputType = InputType.TYPE_CLASS_TEXT
                } else if (questionitem.type.equals("4") && questionitem.dependentQuesId.equals("0")) {
                    tvTitleText.visibility = View.VISIBLE
                    if(isLanguageEnglish.equals("2")){

                        this.tvTitleText.text = questionitem.questionHn

                    }else{
                        this.tvTitleText.text = questionitem.question
                    }
                    this.etTemplate.visibility = View.VISIBLE
                    this.etTemplate.inputType = InputType.TYPE_CLASS_NUMBER
                } else if (questionitem.type.equals("6") && questionitem.dependentQuesId.equals("0")) {

                    tvTitleText.visibility = View.VISIBLE
                    if(isLanguageEnglish.equals("2")){

                        this.tvTitleText.text = questionitem.questionHn

                    }else{
                        this.tvTitleText.text = questionitem.question
                    }
                    this.etOtherInformation.visibility = View.VISIBLE

                } else if (questionitem.type.equals("7") && questionitem.dependentQuesId.equals("0")) {
                    tvTitleText.visibility = View.VISIBLE
                    if(isLanguageEnglish.equals("2")){

                        this.tvTitleText.text = questionitem.questionHn

                    }else{
                        this.tvTitleText.text = questionitem.question
                    }
                    this.tvDateTemp.visibility = View.VISIBLE
                } else if (questionitem.type.equals("8") && questionitem.dependentQuesId.equals("0")) {
                    tvTitleText.visibility = View.VISIBLE
                    if(isLanguageEnglish.equals("2")){

                        this.tvTitleText.text = questionitem.questionHn

                    }else{
                        this.tvTitleText.text = questionitem.question
                    }
                    this.clSignture.visibility = View.VISIBLE
                        


                } else if(questionitem.type.equals("9")&& questionitem.dependentQuesId.equals("0")) {

                    tvTitleText.visibility = View.VISIBLE
                    rvAddMulti.visibility = View.VISIBLE
                    if (isLanguageEnglish.equals("2")) {

                        this.tvTitleText.text = questionitem.questionHn

                    } else {
                        this.tvTitleText.text = questionitem.question
                    }
                    btnAddAnswer.visibility = View.GONE;
                    btnRemoveAnswer.visibility = View.GONE;
                    var multiList: MutableList<MultiModel> = ArrayList();
                    var multiAdapter: MultiAdapter = MultiAdapter(context, ArrayList())
                    rvAddMulti.adapter = multiAdapter
                    rvAddMulti.layoutManager = LinearLayoutManager(context)
                    questionitem.multiModel= MultiModel("Social Gr.", "No")



                    multiAdapter.updateList(questionitem.multiModel)




                    btnAddAnswer.setOnClickListener {
                        multiAdapter.updateList(questionitem.multiModel)
                        Log.d("multiadapterlist",Gson().toJson(multiAdapter.list).toString());


                    }







                    btnRemoveAnswer.setOnClickListener {
                        if (multiAdapter.list.size != 1) {
                            multiAdapter.removeList(questionitem.multiModel)
                        } else {
                            Toast.makeText(context, "minimum should be one", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
                    else if(questionitem.type.equals("11")&& questionitem.dependentQuesId.equals("0")) {

                    tvTitleText.visibility = View.VISIBLE
                    rvAddMulti.visibility = View.VISIBLE
                    if (isLanguageEnglish.equals("2")) {

                        this.tvTitleText.text = questionitem.questionHn

                    } else {
                        this.tvTitleText.text = questionitem.question
                    }
                    btnAddAnswer.visibility = View.GONE;
                    btnRemoveAnswer.visibility = View.GONE;
                    var multiList: MutableList<MultiModel> = ArrayList();
                    var multiAdapter: MultiAdapter = MultiAdapter(context, ArrayList())
                    rvAddMulti.adapter = multiAdapter
                    rvAddMulti.layoutManager = LinearLayoutManager(context)

                    questionitem.multiModel = MultiModel("Type","No")

                    multiAdapter.updateList(questionitem.multiModel)
                    btnAddAnswer.setOnClickListener {
                        multiAdapter.updateList(questionitem.multiModel)

                    }
                    btnRemoveAnswer.setOnClickListener {
                        if (multiAdapter.list.size != 1) {
                            multiAdapter.removeList(questionitem.multiModel)
                        } else {
                            Toast.makeText(context, "minimum should be one", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }




                }  else if(questionitem.type.equals("12")&& questionitem.dependentQuesId.equals("0")) {

                    tvTitleText.visibility = View.VISIBLE
                    rvAddMulti.visibility = View.VISIBLE
                    if (isLanguageEnglish.equals("2")) {

                        this.tvTitleText.text = questionitem.questionHn

                    } else {
                        this.tvTitleText.text = questionitem.question
                    }

                    btnAddAnswer.visibility = View.GONE;
                    btnRemoveAnswer.visibility = View.GONE;
                    var multiList: MutableList<MultiModel> = ArrayList();
                    var multiAdapter: MultiAdapter = MultiAdapter(context, ArrayList())
                    rvAddMulti.adapter = multiAdapter
                    rvAddMulti.layoutManager = LinearLayoutManager(context)
                    questionitem.multiModel=MultiModel("Name of materials", "No")
                    multiAdapter.updateList(questionitem.multiModel)
                    btnAddAnswer.setOnClickListener {
                        multiAdapter.updateList(questionitem.multiModel)

                    }
                    btnRemoveAnswer.setOnClickListener {
                        if (multiAdapter.list.size != 1) {
                            multiAdapter.removeList(questionitem.multiModel)
                        } else {
                            Toast.makeText(context, "minimum should be one", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
                else if(questionitem.type.equals("10")&& questionitem.dependentQuesId.equals("0")){

                    tvTitleText.visibility=View.VISIBLE
                    rvAddMulti.visibility=View.VISIBLE
                    if(isLanguageEnglish.equals("2")){

                        this.tvTitleText.text = questionitem.questionHn

                    }else{
                        this.tvTitleText.text = questionitem.question
                    }

                    btnAddAnswer.visibility = View.GONE;
                    btnRemoveAnswer.visibility = View.GONE;
                    var multiList: MutableList<MultiModel> = ArrayList();
                    var multiAdapter :MultiNewAdapter= MultiNewAdapter(context,ArrayList())
                    rvAddMulti.adapter=multiAdapter
                    rvAddMulti.layoutManager=LinearLayoutManager(context)

                    multiAdapter.updateList(MultiModelThree())
                    btnAddAnswer.setOnClickListener {
                        multiAdapter.updateList(MultiModelThree())

                    }
                    btnRemoveAnswer.setOnClickListener{
                        if(multiAdapter.list.size!=1){
                            multiAdapter.removeList(MultiModelThree())
                        }else{
                            Toast.makeText(context,"minimum should be one",Toast.LENGTH_SHORT).show()
                        }
                    }

                }
                else {
                    rvAddMulti.visibility=View.GONE

                    this.btnAddAnswer.visibility=View.GONE
                    this.btnRemoveAnswer.visibility=View.GONE
                    this.tvTitleText.visibility = View.GONE
                    this.rgTemplate.visibility = View.GONE
                    this.etTemplate.visibility = View.GONE
                    this.cbTemplate.visibility = View.GONE
                    this.templateClSpn.visibility = View.GONE
                    this.ivSign.visibility = View.GONE
                    this.etOtherInformation.visibility = View.GONE
                    this.clSignture.visibility = View.GONE
                    this.llCbtemplate.visibility = View.GONE
                    this.tvDateTemp.visibility = View.GONE
                    llCbtemplate.visibility = View.GONE


                }





                binding.tvDateTemp.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                        Log.d("textChange", "no");
                        Log.d("textChange", etTemplate.text.toString());

                    }

                    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                        questionitem.passData = etTemplate.text.toString()
                        questionitem.position = adapterPosition.toString()
                        questionitem.passId = questionitem.id!!;

                        if(isLanguageEnglish.equals("2")){
                            questionitem.questionName = questionitem.questionHn.toString()

                        }else{
                            questionitem.questionName = questionitem.question.toString()
                        }
                        questionitem.questionName = questionitem.question.toString()
                        Log.d("textChange", etTemplate.text.toString());

                    }

                    override fun afterTextChanged(p0: Editable?) {
                        Log.d("textChange", etTemplate.text.toString());

                    }


                })


                binding.etTemplate.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    }

                    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                        questionitem.passData = etTemplate.text.toString()
                        questionitem.position = adapterPosition.toString()
                        questionitem.passId = questionitem.id!!;
                        if(isLanguageEnglish.equals("2")){
                            questionitem.questionName = questionitem.questionHn.toString()

                        }else{
                            questionitem.questionName = questionitem.question.toString()
                        }

                    }

                    override fun afterTextChanged(p0: Editable?) {

                    }


                })
                binding.etOtherInformation.addTextChangedListener(object : TextWatcher {


                    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    }

                    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                        questionitem.passData = etOtherInformation.text.toString()
                        questionitem.position = adapterPosition.toString()
                        questionitem.passId = questionitem.id!!;

                        if(isLanguageEnglish.equals("2")){

                            questionitem.questionName = questionitem.questionHn.toString()

                        }else{
                            questionitem.questionName = questionitem.question.toString()
                        }
                    }

                    override fun afterTextChanged(p0: Editable?) {
                    }


                })


                /* binding.rgTemplate.setOnCheckedChangeListener(
                     RadioGroup.OnCheckedChangeListener
                     { radioGroup, i ->
                         var isDependent: Boolean

                         for (j in 0 until questionitem.options!!.size) {
                             if (rgTemplate.checkedRadioButtonId.toString()
                                     .equals(questionitem.options.get(j).id)
                             ) {

                                 if (questionitem.options.get(j).isDependent.equals("0")) {
                                     isDependent = false
                                     binding.rvDependent.visibility == View.GONE

                                     for (j in 0 until currentList.size) {
                                         if (questionitem.options.get(0).questionsId!!.equals(
                                                 currentList.get(j).dependentQuesId
                                             )
                                         ) {
                                             val currentelement: QuestionsDataItem =
                                                 currentList.get(j)

                                             currentList.toMutableList().remove(currentelement);

                                         }
                                     }


                                 } else {
                                     isDependent = true
                                     binding.rvDependent.visibility == View.VISIBLE
                                     var adapter: DependentNewAdapter = DependentNewAdapter(
                                         currentList,
                                         context,
                                         rgTemplate.checkedRadioButtonId.toString()
                                     )
                                     binding.rvDependent.adapter = adapter
                                     binding.rvDependent.layoutManager = LinearLayoutManager(context)


                                 }
                                 questionitem.options.get(j).passId =
                                     rgTemplate.checkedRadioButtonId.toString()
                                 *//*onRadioButtonClick(
                                    DependentId(
                                        rgTemplate.checkedRadioButtonId.toString(),
                                        adapterPosition,
                                        isDependent,
                                        questionitem.options.get(j).questionsId.toString()

                                    )
                                )*//*


                            }


                        }


                    })*/

                questionitem.passData = "";
                questionitem.passId = "";

                binding.rgTemplate.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { radioGroup, i ->

                    var isDependent: Boolean = false;


                    if(isLanguageEnglish.equals("2")){

                        questionitem.questionName = questionitem.questionHn.toString()

                    }else{
                        questionitem.questionName = questionitem.question.toString()
                    }

                    for (j in 0 until questionitem.options!!.size) {
                        if (rgTemplate.checkedRadioButtonId.toString()
                                .equals(questionitem.options.get(j).id)
                        ) {
                            questionitem.passData = questionitem.options.get(j).options.toString();

                            questionitem.passId =
                                rgTemplate.checkedRadioButtonId.toString();



                            if (questionitem.options.get(j).isDependent.equals("0")) {
                                isDependent = false
                            } else {
                                isDependent = true;
                            }

                        }
                    }

                    if (!isDependent) {
                        binding.rvDependent.visibility = View.GONE
                    } else {
                        binding.rvDependent.visibility = View.VISIBLE
                        val dependList: MutableList<QuestionsDataItem> = ArrayList();
                        dependList.clear()
                        for (j in 0 until currentList.size) {
                            if (currentList.get(j).quesOptionId!!.equals(rgTemplate.checkedRadioButtonId.toString())) {
                                dependList.add(currentList.get(j));
                            } else {

                            }
                        }
                        var dependentNewAdapter: DependentNewAdapter = DependentNewAdapter(
                            dependList,
                            context,
                            radioGroup.checkedRadioButtonId.toString(),
                            adapterPosition,
                            isLanguageEnglish

                        )
                        binding.rvDependent.adapter = dependentNewAdapter
                        binding.rvDependent.layoutManager = LinearLayoutManager(context)
                    }
                });
            }
        }
    }


    object DiffUtilCallback : DiffUtil.ItemCallback<QuestionsDataItem>() {
        override fun areItemsTheSame(
            oldItem: QuestionsDataItem,
            newItem: QuestionsDataItem
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: QuestionsDataItem,
            newItem: QuestionsDataItem
        ): Boolean {

            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TemplateListViewHolder =
        TemplateListViewHolder(
            RvTemplateItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )


    override fun onBindViewHolder(
        holder: TemplateListViewHolder,
        position: Int
    ) {


        holder.binding.tvDateTemp.setOnClickListener(View.OnClickListener { v -> // TODO Auto-generated method stub
            /*holder.binding.tvDateTemp.transformIntoDatePicker(
                context,
                "MM/dd/yyyy"
            )*/

            /*  DatePickerDialog(
                  v.rootView.context, date, myCalendar
                      .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                  myCalendar.get(Calendar.DAY_OF_MONTH)
              ).show()*/


            val c = Calendar.getInstance()
            val mYear = c[Calendar.YEAR]
            val mMonth = c[Calendar.MONTH]
            val mDay = c[Calendar.DAY_OF_MONTH]

            val datePickerDialog = DatePickerDialog(
                context,
                { view, year, monthOfYear, dayOfMonth ->
                    val fr = SimpleDateFormat("dd-MM-yyyy")
                    holder.binding.tvDateTemp.setText(dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year)

                    getItem(position).passData = holder.binding.tvDateTemp.text.toString()
                    getItem(position).position = position.toString()
                    getItem(position).passId = getItem(position).id!!;
                    getItem(position).questionName = getItem(position).question.toString()


                }, mYear, mMonth, mDay
            )
            datePickerDialog.datePicker.maxDate=Date().time

            datePickerDialog.show()


        })


        holder.bind(getItem(holder.adapterPosition),isLanguageEnglish)




        holder.binding.clSignture.setOnClickListener {

            val dialog: Dialog
            val imageBinding: ActivitySignatureBinding =
                ActivitySignatureBinding.inflate(LayoutInflater.from(context))
            dialog = Dialog(
                context,
                android.R.style.Theme_Material_Light_NoActionBar_Fullscreen
            )
            val signaturePad: SignaturePad? = imageBinding.signaturePad
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(imageBinding.getRoot())

            signaturePad!!.setOnSignedListener(object :
                SignaturePad.OnSignedListener {
                override fun onStartSigning() {}
                override fun onSigned() {
                    imageBinding.saveButton.setEnabled(true)
                    imageBinding.clearButton.setEnabled(true)
                }

                override fun onClear() {
                    imageBinding.saveButton.setEnabled(false)
                    imageBinding.clearButton.setEnabled(false)
                }
            })
            imageBinding.clearButton.setOnClickListener { view -> signaturePad.clear() }
            dialog.setCancelable(false)
            dialog.show()
            imageBinding.ivClose.setOnClickListener { dialog.dismiss() }
            imageBinding.saveButton.setOnClickListener { view ->
                holder.binding.ivSign.setImageBitmap(signaturePad!!.signatureBitmap)
                SingleSignature(
                    DependentId(
                        getItem(holder.adapterPosition).id.toString(),
                        getItem(holder.adapterPosition).question.toString(),
                        holder.adapterPosition,
                        signaturePad!!.signatureBitmap
                    )
                )
                /*getItem(holder.adapterPosition).signatureData.add(signaturePad!!.signatureBitmap);*/


                /*bitmapToFile(signaturePad!!.signatureBitmap,
                    getItem(holder.adapterPosition).question.toString()
                )
*/

                dialog.dismiss()
            }


        }


    }


    override fun getItemId(position: Int): Long {
        return currentList.get(position).id!!.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount(): Int {
        return currentList.size
    }


    override fun setHasStableIds(hasStableIds: Boolean) {
        super.setHasStableIds(hasStableIds)
    }

    fun addJpgSignatureToGallery(signature: Bitmap?): Boolean {
        var result = false
        try {
            val photo = File(
                getAlbumStorageDir("SignaturePad"),
                String.format("Signature_%d.jpg", System.currentTimeMillis())
            )
            if (signature != null) {
                saveBitmapToJPG(signature, photo)
            }
            scanMediaFile(photo)
            result = true
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return result
    }

    fun getAlbumStorageDir(albumName: String?): File? {
        // Get the directory for the user's public pictures directory.
        val file = File(
            Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES
            ), albumName
        )
        if (!file.mkdirs()) {
            Log.e("SignaturePad", "Directory not created")
        }
        return file
    }

    fun saveBitmapToJPG(bitmap: Bitmap, photo: File?) {
        val newBitmap = Bitmap.createBitmap(bitmap.width, bitmap.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(newBitmap)
        canvas.drawColor(Color.WHITE)
        canvas.drawBitmap(bitmap, 0f, 0f, null)
        val stream: OutputStream = FileOutputStream(photo)
        newBitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream)
        stream.close()
    }

    private fun scanMediaFile(photo: File) {
        val mediaScanIntent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
        val contentUri: Uri = Uri.fromFile(photo)
        mediaScanIntent.data = contentUri
        context.sendBroadcast(mediaScanIntent)
    }


}