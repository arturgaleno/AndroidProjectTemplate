package br.com.app.template.utils;

import android.content.Context;

import org.acra.collector.CrashReportData;
import org.acra.sender.ReportSender;
import org.acra.sender.ReportSenderException;
import org.androidannotations.annotations.EBean;

/**
 * Created by artur on 05/08/15.
 *
 * Custom Sender for Acra
 *
 */
@EBean
public class ACRASender implements ReportSender {

    @Override
    public void send(Context context, CrashReportData errorContent) throws ReportSenderException {

    }
}
