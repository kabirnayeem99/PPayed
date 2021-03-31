package com.kabirnayeem99.paymentpaid.ui.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.documentfile.provider.DocumentFile
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.kabirnayeem99.paymentpaid.R
import com.kabirnayeem99.paymentpaid.data.db.entities.Work
import com.kabirnayeem99.paymentpaid.ui.*
import com.kabirnayeem99.paymentpaid.ui.fragments.AboutFragment
import com.kabirnayeem99.paymentpaid.ui.fragments.AnalyticsFragment
import com.kabirnayeem99.paymentpaid.ui.fragments.PaymentsFragment
import com.kabirnayeem99.paymentpaid.ui.fragments.WorkFragment
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import spencerstudios.com.bungeelib.Bungee
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.util.*


/**
 * This Activity is the entry point of the application
 * and holds three fragment, such as works fragment, payment fragment and about fragment.
 */
class HomeActivity : AppCompatActivity() {

    private lateinit var drawerToggle: ActionBarDrawerToggle
    private lateinit var workFragment: WorkFragment
    private lateinit var analyticsFragment: AnalyticsFragment
    private lateinit var paymentsFragment: PaymentsFragment
    private lateinit var aboutFragment: AboutFragment
    lateinit var firestoreViewModel: FirestoreViewModel
    private lateinit var logInRegisterViewModel: LogInRegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_PaymentPaid)
        setContentView(R.layout.activity_home)
        initFragments()
        setUpFragmentNavigation()
        setUpNavigationDrawer()
        setUpViewModel()
        setUpLoggedOutListener()
    }


    private fun initFragments() {
        workFragment = WorkFragment()
        paymentsFragment = PaymentsFragment()
        aboutFragment = AboutFragment()
        analyticsFragment = AnalyticsFragment()
    }

    private fun closeDrawer() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_export, menu)
        return true
    }


    /**
     * This method sets up the DrawerLayout for the home activity
     */
    private fun setUpNavigationDrawer() {
        drawerToggle = ActionBarDrawerToggle(
                this, drawerLayout, R.string.open_drawer, R.string.close_drawer
        )
        drawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.workNavMenu -> {
                    makeCurrentFragment(workFragment)
                }
                R.id.paymentNavMenu -> {
                    makeCurrentFragment(paymentsFragment)
                }
                R.id.analyticsNavMenu -> {
                    makeCurrentFragment(analyticsFragment)
                }
                R.id.aboutNavMenu -> {
                    makeCurrentFragment(aboutFragment)
                }
            }
            true
        }
    }

    /**
     * This method will show the current fragment on screen
     * @param fragment of [Fragment] type
     */
    private fun makeCurrentFragment(fragment: Fragment) {
        closeDrawer()
        supportFragmentManager
                .beginTransaction()
                // adds animation from https://github.com/yendangn/Fragment-Transaction-Animation
                .setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit)
                .replace(R.id.flFragmentPlaceholder, fragment)
                .commit()
    }


    override fun overridePendingTransition(enterAnim: Int, exitAnim: Int) {
        super.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

    private fun setUpFragmentNavigation() {
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.flFragmentPlaceholder, WorkFragment())
                .commit()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.menuExport) {
            createFile()
            return true
        }
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }


    private fun setUpLoggedOutListener() {
        logInRegisterViewModel.getLoggedOutLiveData().observe(this, { isLoggedOut ->
            if (isLoggedOut) {
                Toast.makeText(this, "You are logged out", Toast.LENGTH_SHORT).show()
                moveToSignInActivity()
            }
        })
    }

    /**
     * This method starts the [SignInActivity]
     */
    private fun moveToSignInActivity() {
        val intent = Intent(this, SignInActivity::class.java)
        startActivity(intent)
        this.finish()
    }

    /**
     * This method sets up [LogInRegisterViewModel] & [WorkViewModel] for the [HomeActivity]
     */
    private fun setUpViewModel() {
        val logInRegisterViewModelFactory = LogInRegisterViewModelProviderFactory(application)

        logInRegisterViewModel = ViewModelProvider(this,
                logInRegisterViewModelFactory).get(LogInRegisterViewModel::class.java)


        firestoreViewModel = if (logInRegisterViewModel.getOfflineStatus()) {
            ViewModelProviders.of(this).get(FirestoreViewModel::class.java)
        } else {
            ViewModelProviders.of(this).get(FirestoreViewModel::class.java)
        }

    }

    /**
     * Request Permission For Read Storage
     * @param context
     */
    private fun requestPermissionForReadWrite(context: Context) {
        ActivityCompat.requestPermissions(
                context as Activity,
                arrayOf(
                        android.Manifest.permission.READ_EXTERNAL_STORAGE,
                        android.Manifest.permission.READ_EXTERNAL_STORAGE
                ), PERMISSION_READ_EXTERNAL_STORAGE
        )
    }


    /**
     * Request Permission if not given
     * @param context [Context]
     */
    @RequiresApi(Build.VERSION_CODES.Q)
    fun requestPermissionForAccessMediaLocation(context: Context) {
        Log.i(TAG, "requestPermissionForAML")

        ActivityCompat.requestPermissions(
                context as Activity,
                arrayOf(android.Manifest.permission.ACCESS_MEDIA_LOCATION),
                MEDIA_LOCATION_PERMISSION_REQUEST_CODE
        )

    }

    /**
     * Check if Permission granted for Accessing Media Location
     * @param context [Context]
     * @return Boolean
     */
    @RequiresApi(Build.VERSION_CODES.Q)
    private fun isPermissionGrantedForMediaLocationAccess(context: Context): Boolean {
        Log.i("Tag", "checkPermissionForAML")
        val result: Int =
                ContextCompat.checkSelfPermission(
                        context,
                        android.Manifest.permission.ACCESS_MEDIA_LOCATION
                )
        return result == PackageManager.PERMISSION_GRANTED
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
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == OPEN_FILE_REQUEST_CODE) {
                data?.data?.also { documentUri ->

                    //Permission needed if you want to retain access even after reboot
                    contentResolver.takePersistableUriPermission(
                            documentUri,
                            Intent.FLAG_GRANT_READ_URI_PERMISSION
                    )
                    Toast.makeText(this, documentUri.path.toString(), Toast.LENGTH_LONG).show()
                }
            } else if (requestCode == OPEN_FOLDER_REQUEST_CODE) {
                val directoryUri = data?.data ?: return

                //Taking permission to retain access
                contentResolver.takePersistableUriPermission(
                        directoryUri,
                        Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
                //Now you have access to the folder, you can easily view the content or do whatever you want.
                val documentsTree = DocumentFile.fromTreeUri(application, directoryUri) ?: return
                val childDocuments = documentsTree.listFiles().asList()
                Toast.makeText(
                        this,
                        "Total Items Under this folder =" + childDocuments.size.toString(),
                        Toast.LENGTH_LONG
                ).show()

            } else if (requestCode == CHOOSE_FILE) {
                if (data != null) {
                    var inputStream: InputStream? = null
                    //Not guaranteed to get the metadata
                    try {
                        inputStream = contentResolver.openInputStream(data.data!!)
                        val exifInterface = ExifInterface(inputStream!!)

                        Toast.makeText(
                                this,
                                "Path = " + data.data + "   ,Latitude = " + exifInterface.getAttribute(
                                        ExifInterface.TAG_GPS_LATITUDE
                                ) + "   ,Longitude =" + exifInterface.getAttribute(ExifInterface.TAG_GPS_LONGITUDE),
                                Toast.LENGTH_LONG
                        ).show()
                    } catch (e: IOException) {
                        // Handle any errors
                    } finally {
                        if (inputStream != null) {
                            try {
                                inputStream.close()
                            } catch (ignored: IOException) {
                            }

                        }
                    }
                }
            }
        }
    }


    companion object {
        private const val PERMISSION_READ_EXTERNAL_STORAGE = 5
        private const val MEDIA_LOCATION_PERMISSION_REQUEST_CODE = 3
        private const val OPEN_FILE_REQUEST_CODE = 6
        private const val CREATE_FILE_REQUEST_CODE = 1
        private const val OPEN_FOLDER_REQUEST_CODE = 2
        private const val CHOOSE_FILE = 4
    }


    private suspend fun writeFileContent(uri: Uri?) {
        try {
            val file = uri?.let { this.contentResolver.openFileDescriptor(it, "w") }



            file?.let {
                val fileOutputStream = FileOutputStream(
                        it.fileDescriptor
                )


//                //dfd
//                val document = Document()
//                PdfWriter.getInstance(document, FileOutputStream("${etFileName.text}.pdf"))
//                document.open()
//                document.add(Paragraph("${etContent.text}"))
//                document.close()
//                //dfd

                var string = ""
                var workList: List<Work> = listOf()

                if (this::firestoreViewModel.isInitialized) {

                    workList = firestoreViewModel.getWorkList().value!!
                }
                if (workList.isNotEmpty()) {
                    for (work in workList) {

                        val tempStr = "________________________________________________\n" +
                                "Work name: ${work.name}\n" +
                                "Student name: ${work.studentName}\n" +
                                "Payment: ${work.payment}\n" +
                                "Date: ${work.day}-${work.month}-${work.year}\n" +
                                "________________________________________________\n"
                        string += tempStr
                    }
                }

                fileOutputStream.write(string.toByteArray())


                fileOutputStream.close()
                it.close()
            }

        } catch (e: FileNotFoundException) {
            //print logs
        } catch (e: IOException) {
            //print logs
        }

    }

    private fun createFile() {
        val intent = Intent(Intent.ACTION_CREATE_DOCUMENT)

        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TITLE, "exported_data_${Calendar.getInstance().get(Calendar.YEAR)}.txt")

        startActivityForResult(intent, CREATE_FILE_REQUEST_CODE).also {
            Bungee.slideLeft(this)
        }
    }

}