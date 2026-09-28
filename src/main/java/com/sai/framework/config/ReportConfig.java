package com.sai.framework.config;

public final class ReportConfig {

    private ReportConfig(){}

    public static String getReportType(){

        String reportType = System.getProperty("reportType");

        if(reportType == null || reportType.isBlank()){
            return "NONE";
        }

        return reportType.trim().toUpperCase();
    }

    public static boolean isHtmlEnabled(){

        return "HTML_ONLY".equals(getReportType()) || "HTML_AND_WORD".equals(getReportType());
    }

    public static boolean isWordEnabled(){

        return "HTML_AND_WORD".equals(getReportType());
    }


}
