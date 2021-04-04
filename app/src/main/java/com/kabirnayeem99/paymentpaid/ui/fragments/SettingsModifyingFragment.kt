package com.kabirnayeem99.paymentpaid.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatDialogFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.UserInfo
import com.google.firebase.auth.UserProfileChangeRequest
import com.kabirnayeem99.paymentpaid.R
import kotlinx.android.synthetic.main.activity_files.*
import kotlinx.android.synthetic.main.fragment_settings_modifying.*


class SettingsModifyingFragment : AppCompatDialogFragment() {
    private lateinit var etText: EditText
    val auth = FirebaseAuth.getInstance()

//    fun SettingsModifyingFragment() {
//        // Empty constructor is required for DialogFragment
//        // Make sure not to add arguments to the constructor
//        // Use `newInstance` instead as shown below
//    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_settings_modifying, container);
    }

    fun newInstance(title: String?): SettingsModifyingFragment {
        val frag = SettingsModifyingFragment()
        val args = Bundle()
        args.putString("title", title)
        frag.arguments = args
        return frag
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        etText = view.findViewById(R.id.etSettingsModifyingfragment) as EditText
        // Fetch arguments from bundle and set title
        // Fetch arguments from bundle and set title
        val title = requireArguments().getString("title", "Change the settings.")
        dialog!!.setTitle(title)
        // Show soft keyboard automatically and request focus to field
        // Show soft keyboard automatically and request focus to field
        etText.requestFocus()
        dialog!!.window!!.setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)

        btnSaveProfileDialog.setOnClickListener {
//            val providerData: MutableList<out UserInfo> = auth.currentUser.providerData
//            auth.currentUser.updatePhoneNumber(PhoneAuthCredential.zzb("", "01872184679"))
//            val profileUpdates = UserProfileChangeRequest.Builder()
//                    .setDisplayName(this.displayName).build()
            this.dismiss()
        }

        btnCancelProfileDialog.setOnClickListener {
            this.dismiss()
        }
    }


}