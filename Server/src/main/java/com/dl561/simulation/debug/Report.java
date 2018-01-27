package com.dl561.simulation.debug;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Report {
    static List<ReportLine> reportList;
    private static final String DEBUG_FILE = "E:\\University\\CM30082\\debug_report.csv";

    public static void addToReport(ReportLine reportLine) {
        if (reportList == null) {
            initialiseReport();
        }
        reportList.add(reportLine);
    }

    private static void initialiseReport() {
        reportList = new LinkedList<>();
    }

    public static void saveReport() throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(DEBUG_FILE));
        bw.write(ReportLine.getHeaderLine());
        for (ReportLine reportLine : reportList) {
            bw.write(reportLine.toString());
        }
        bw.flush();
        bw.close();
    }

    public static void saveHeaderLine() throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(DEBUG_FILE, true));
        bw.write(ReportLine.getHeaderLine());
        bw.flush();
        bw.close();
    }

    public static void saveReportLine(ReportLine reportLine) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(DEBUG_FILE, true));
        bw.write(reportLine.toString());
        bw.flush();
        bw.close();
    }

}
