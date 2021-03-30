package com.kabirnayeem99.paymentpaid.ui.activities

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.kabirnayeem99.paymentpaid.R
import com.kabirnayeem99.paymentpaid.data.db.WorkDatabase
import com.kabirnayeem99.paymentpaid.data.repositories.WorkRepository
import com.kabirnayeem99.paymentpaid.enums.AccountStatus
import com.kabirnayeem99.paymentpaid.ui.WorkViewModel
import com.kabirnayeem99.paymentpaid.ui.WorkViewModelProviderFactory
import kotlinx.android.synthetic.main.activity_files.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException

class FilesActivity : AppCompatActivity() {

    private lateinit var workViewModel: WorkViewModel

    companion object {
        private const val CREATE_FILE_REQUEST_CODE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_files)

        setUpViewModel()
        btnSave.setOnClickListener {
            createFile()
        }


    }

    private fun setUpViewModel() {
        val workRepository = WorkRepository(WorkDatabase(this), AccountStatus.OFFLINE)
        val workViewModelProviderFactory = WorkViewModelProviderFactory(workRepository)
        workViewModel = ViewModelProvider(this,
                workViewModelProviderFactory).get(WorkViewModel::class.java)
    }


    private fun createFile() {
        val intent = Intent(Intent.ACTION_CREATE_DOCUMENT)

        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TITLE, "${etFileName.text}.text")


        startActivityForResult(intent, CREATE_FILE_REQUEST_CODE)
    }

    private suspend fun writeFileContent(uri: Uri?) {
        try {
            val file = uri?.let { this.contentResolver.openFileDescriptor(it, "w") }



            file?.let {
                val fileOutputStream = FileOutputStream(
                        it.fileDescriptor
                )
                val textContent = etContent.text.toString()


//                //dfd
//                val document = Document()
//                PdfWriter.getInstance(document, FileOutputStream("${etFileName.text}.pdf"))
//                document.open()
//                document.add(Paragraph("${etContent.text}"))
//                document.close()
//                //dfd

                var string = "file"

                val workList = workViewModel.getAllWorksSync().first()

                for (work in workList) {

                    val tempStr = "________________________________________________\n" +
                            "Work name: ${work.name}\n" +
                            "Student name: ${work.studentName}\n" +
                            "Payment: ${work.payment}\n" +
                            "Date: ${work.day}-${work.month}-${work.year}\n" +
                            "________________________________________________\n"
                    string += tempStr
                }

                fileOutputStream.write(string.toString().toByteArray())


                fileOutputStream.close()
                it.close()
            }

        } catch (e: FileNotFoundException) {
            //print logs
        } catch (e: IOException) {
            //print logs
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        //Write the file content
        if (requestCode == CREATE_FILE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                CoroutineScope(Dispatchers.IO).launch {
                    withContext(Dispatchers.Main) {
                        writeFileContent(data.data)

                    }
                }
            }

        }
    }
}