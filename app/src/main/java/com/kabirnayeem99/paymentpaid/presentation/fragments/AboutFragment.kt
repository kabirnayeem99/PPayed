package com.kabirnayeem99.paymentpaid.presentation.fragments

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat
import com.danielstone.materialaboutlibrary.ConvenienceBuilder
import com.danielstone.materialaboutlibrary.MaterialAboutFragment
import com.danielstone.materialaboutlibrary.items.MaterialAboutActionItem
import com.danielstone.materialaboutlibrary.model.MaterialAboutCard
import com.danielstone.materialaboutlibrary.model.MaterialAboutList
import com.kabirnayeem99.paymentpaid.R

/**
 * Creates a fragment about the app name, developer name and so on.
 * Uses [material-about-library] library
 * Extends [MaterialAboutFragment]
 */
class AboutFragment : MaterialAboutFragment() {


    override fun getMaterialAboutList(activityContext: Context): MaterialAboutList {
        val context = requireContext()

        val miscCardBuilder = MaterialAboutCard.Builder()
        buildMisc(context, miscCardBuilder)

        val appCardBuilder = MaterialAboutCard.Builder()
        buildApp(context, appCardBuilder)

        val authorCardBuilder = MaterialAboutCard.Builder()
        buildAuthor(context, authorCardBuilder)

        return MaterialAboutList(
            miscCardBuilder.build(),
            authorCardBuilder.build(),
            appCardBuilder.build(),
        )
    }

    /**
     * Creates a section about the author
     * @param context of [Context] type
     * @param authorCardBuilder of [MaterialAboutCard.Builder] type
     */
    private fun buildAuthor(context: Context, authorCardBuilder: MaterialAboutCard.Builder) {
        authorCardBuilder.title(R.string.desc_developer_name)
        authorCardBuilder.addItem(
            MaterialAboutActionItem.Builder()
                .text("Naimul Kabir")
                .subText("Naimul Kabir")
                .icon(ContextCompat.getDrawable(context, R.drawable.ic_profile))
                .build()
        )
            .addItem(
                ConvenienceBuilder.createEmailItem(
                    context,
                    ContextCompat.getDrawable(context, R.drawable.ic_email),
                    getString(R.string.send_email),
                    true,
                    getString(R.string.email_address),
                    getString(R.string.question_concerning_ppayed)
                )
            )
    }

    /**
     * Creates a section about the app, app version, and so on
     * @param context of [Context] type
     * @param appCardBuilder of [MaterialAboutCard.Builder] type
     */
    private fun buildApp(context: Context, appCardBuilder: MaterialAboutCard.Builder) {
        appCardBuilder.addItem(
            MaterialAboutActionItem.Builder()
                .text(getString(R.string.version))
                .icon(ContextCompat.getDrawable(context, R.drawable.ic_update))
                .subText(R.string.version_detail)
                .build()
        )
            .addItem(MaterialAboutActionItem.Builder()
                .text(R.string.report_issue)
                .subText(R.string.report_issue_here)
                .icon(ContextCompat.getDrawable(context, R.drawable.ic_bug))
                .setOnClickAction {
                    val browserIntent =
                        Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.issue_url)))
                    startActivity(browserIntent)
                }
                .build())
    }

    /**
     * Creates a header section to give a general idea about the app
     * and the change log
     * @param context of [Context] type
     * @param miscCardBuilder of [MaterialAboutCard.Builder] type
     */
    private fun buildMisc(context: Context, miscCardBuilder: MaterialAboutCard.Builder) {
        miscCardBuilder.title(R.string.about)
            .addItem(MaterialAboutActionItem.Builder()
                .text(R.string.changelog)
                .icon(ContextCompat.getDrawable(context, R.drawable.ic_track_changes))
                .setOnClickAction {
                    val browserIntent =
                        Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.changes_url)))
                    startActivity(browserIntent)
                }
                .build())
    }

}