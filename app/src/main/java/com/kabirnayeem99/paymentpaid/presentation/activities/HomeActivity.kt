package com.kabirnayeem99.paymentpaid.presentation.activities

import android.app.Activity
import android.content.Intent
import android.media.ExifInterface
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.documentfile.provider.DocumentFile
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.kabirnayeem99.paymentpaid.R
import com.kabirnayeem99.paymentpaid.domain.models.Work
import com.kabirnayeem99.paymentpaid.other.Resource
import com.kabirnayeem99.paymentpaid.presentation.viewmodels.WorkViewModel
import com.kabirnayeem99.paymentpaid.presentation.fragments.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.coroutines.*
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.util.*
import javax.inject.Inject


/**
 * This Activity is the entry point of the application
 * and holds three fragment, such as works fragment, payment fragment and about fragment.
 */
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private lateinit var drawerToggle: ActionBarDrawerToggle

    @Inject
    lateinit var workFragment: WorkFragment

    @Inject
    lateinit var analyticsFragment: AnalyticsFragment

    @Inject
    lateinit var paymentsFragment: PaymentsFragment

    @Inject
    lateinit var profileFragment: ProfileFragment

    @Inject
    lateinit var aboutFragment: AboutFragment

    @Inject
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_PaymentPaid)
        setContentView(R.layout.activity_home)
        setUpFragmentNavigation()
        setUpNavigationDrawer()
        setUpLoggedOutListener()
        setUpNavigationView()
    }

    private fun setUpNavigationView() {
        val navigationView = findViewById<View>(R.id.navView) as NavigationView
        val hView: View = navigationView.getHeaderView(0)
        val tvUserEmail = hView.findViewById(R.id.tvUserEmail) as TextView
        with(auth.currentUser!!) {
            tvUserEmail.text = email
            if (displayName?.isNotEmpty() == true)
                tvUserEmail.text = displayName
        }
    }


    /**
     * With the start of the activity, it first sets the current
     * fragment to the [WorkFragment]
     */
    private fun setUpFragmentNavigation() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.flFragmentPlaceholder, WorkFragment())
            .commit()
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
                    makeCurrentFragment(workFragment, WorkFragment::class.java.simpleName)
                }
                R.id.paymentNavMenu -> {
                    makeCurrentFragment(paymentsFragment, PaymentsFragment::class.java.simpleName)
                }
                R.id.analyticsNavMenu -> {
                    makeCurrentFragment(analyticsFragment, AnalyticsFragment::class.java.simpleName)
                }
                R.id.aboutNavMenu -> {
                    makeCurrentFragment(aboutFragment, AboutFragment::class.java.simpleName)
                }
                R.id.userProfileNavMenu -> {
                    makeCurrentFragment(profileFragment, ProfileFragment::class.java.simpleName)
                }

                R.id.logOutMenu -> {
                    auth.signOut()
                }
            }
            true
        }
    }

    /**
     * This simple method sets up [WorkViewModel]
     */
    val workViewModel: WorkViewModel by viewModels()

//    private fun setUpViewModel() {
//        firestoreViewModel = ViewModelProviders.of(this).get(FirestoreViewModel::class.java)
//    }


    /**
     * This method instantly moves user to the sign in activity once he is logged out
     *  in some way.
     */
    private fun setUpLoggedOutListener() {
        auth.addAuthStateListener { authState ->
            if (authState.currentUser == null) {
                Toast.makeText(this, "You are logged out.", Toast.LENGTH_SHORT).show()
                moveToSignInActivity()
            }
        }
    }

    /**
     * This method closes the drawer if the drawer is open
     */
    private fun closeDrawer() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_appbar, menu)
        return true
    }


    /**
     * This method will show the current fragment on screen
     * @param fragment of [Fragment] type
     * @param tag of [String] type, which can be something like [WorkFragment.TAG]
     */
    private fun makeCurrentFragment(fragment: Fragment, tag: String) {
        closeDrawer()

        supportFragmentManager
            .beginTransaction()
            // adds animation from https://github.com/yendangn/Fragment-Transaction-Animation
            .setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit)
            .replace(R.id.flFragmentPlaceholder, fragment, tag)
            .addToBackStack(this::class.java.simpleName)
            .commit()
    }


    override fun overridePendingTransition(enterAnim: Int, exitAnim: Int) {
        super.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
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


    /**
     * Pressing back button in the home activity will move user to the home
     * launcher, not in the sign in or splash screen activity.
     */
    override fun onBackPressed() {
        val homeIntent = Intent(Intent.ACTION_MAIN)
        homeIntent.addCategory(Intent.CATEGORY_HOME)
        homeIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(homeIntent)
    }


    /**
     * This method moves user back to the [SignInActivity]
     */
    private fun moveToSignInActivity() {
        val intent = Intent(this, SignInActivity::class.java)
        startActivity(intent)
        this.finish()
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


    private fun getSnackBar(message: String?): Snackbar {
        return Snackbar.make(
            findViewById<View>(android.R.id.content).rootView,
            message ?: "Something went wrong",
            Snackbar.LENGTH_LONG
        )
    }


    private fun writeFileContent(uri: Uri?) {
        try {
            val file = uri?.let { this.contentResolver.openFileDescriptor(it, "w") }

            file?.let {
                val fileOutputStream = FileOutputStream(it.fileDescriptor)

//                //dfd
//                val document = Document()
//                PdfWriter.getInstance(document, FileOutputStream("${etFileName.text}.pdf"))
//                document.open()
//                document.add(Paragraph("${etContent.text}"))
//                document.close()
//                //dfd

                var string = ""
                var workList: List<Work> = listOf()


                when (val resource = workViewModel.workList.value) {
                    is Resource.Success -> {
                        workList = resource.data!!
                    }
                    else -> {
                        getSnackBar("Could not get the list of works").show()
                    }
                }


                if (workList.isNotEmpty()) {
                    for (work in workList) {

                        val tempStr = "__________________________________________\n" +
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
            getSnackBar("File was not found").show()
        } catch (e: IOException) {
            getSnackBar("File could not be saved.").show()
        }

    }


    /**
     * This private method creates a [text/plain] file.
     */
    private fun createFile() {
        val intent = Intent(Intent.ACTION_CREATE_DOCUMENT)

        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "text/plain"

        with(Calendar.getInstance()) {
            intent.putExtra(
                Intent.EXTRA_TITLE,
                "ppayed_${auth.currentUser}_data_${this.get(Calendar.YEAR)}.txt"
            )
        }

        startActivityForResult(intent, CREATE_FILE_REQUEST_CODE)
    }


    companion object {
        private const val OPEN_FILE_REQUEST_CODE = 6
        private const val CREATE_FILE_REQUEST_CODE = 1
        private const val OPEN_FOLDER_REQUEST_CODE = 2
        private const val CHOOSE_FILE = 4
    }


}