package com.kabirnayeem99.paymentpaid.ui.fragments;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import androidx.core.content.ContextCompat;

import com.danielstone.materialaboutlibrary.ConvenienceBuilder;
import com.danielstone.materialaboutlibrary.MaterialAboutFragment;
import com.danielstone.materialaboutlibrary.items.MaterialAboutActionItem;
import com.danielstone.materialaboutlibrary.model.MaterialAboutCard;
import com.danielstone.materialaboutlibrary.model.MaterialAboutList;
import com.kabirnayeem99.paymentpaid.R;

public class AboutFragment extends MaterialAboutFragment {

    public static AboutFragment getInstance() {
        return new AboutFragment();
    }

    @Override
    protected MaterialAboutList getMaterialAboutList(Context activityContext) {
        Context context = requireContext();

        MaterialAboutCard.Builder miscCardBuilder = new MaterialAboutCard.Builder();
        buildMisc(context, miscCardBuilder);
        MaterialAboutCard.Builder appCardBuilder = new MaterialAboutCard.Builder();
        buildApp(context, appCardBuilder);
        MaterialAboutCard.Builder authorCardBuilder = new MaterialAboutCard.Builder();
        buildAuthor(context, authorCardBuilder);
        return new MaterialAboutList(miscCardBuilder.build(), authorCardBuilder.build(), appCardBuilder.build());
    }

    private void buildAuthor(Context context, MaterialAboutCard.Builder authorCardBuilder) {
        authorCardBuilder.title(R.string.desc_developer_name);
        authorCardBuilder.addItem(new MaterialAboutActionItem.Builder()
                .text("Naimul Kabir")
                .subText("Naimul Kabir")
                .icon(ContextCompat.getDrawable(context, R.drawable.ic_profile))
                .build())
                .addItem(ConvenienceBuilder.createEmailItem(context, ContextCompat.getDrawable(context, R.drawable.ic_email),
                        getString(R.string.send_email), true, getString(R.string.email_address), getString(R.string.question_concerning_ppayed)));
    }

    private void buildApp(Context context, MaterialAboutCard.Builder appCardBuilder) {
        appCardBuilder.addItem(new MaterialAboutActionItem.Builder()
                .text(getString(R.string.version))
                .icon(ContextCompat.getDrawable(context, R.drawable.ic_update))
                .subText(R.string.version_detail)
                .build())
                .addItem(new MaterialAboutActionItem.Builder()
                        .text(R.string.report_issue)
                        .subText(R.string.report_issue_here)
                        .icon(ContextCompat.getDrawable(context, R.drawable.ic_bug))
                        .setOnClickAction(() -> {
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.issue_url)));
                            startActivity(browserIntent);
                        })
                        .build());
    }

    private void buildMisc(Context context, MaterialAboutCard.Builder miscCardBuilder) {
        miscCardBuilder.title(R.string.about)
                .addItem(new MaterialAboutActionItem.Builder()
                        .text(R.string.changelog)
                        .icon(ContextCompat.getDrawable(context, R.drawable.ic_track_changes))
                        .setOnClickAction(() -> {
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.changes_url)));
                            startActivity(browserIntent);
                        })
                        .build())
        ;
    }

}
