package com.baza.firmy.constants;

import java.util.Locale;

public class WebClientConstants {

public static final String CALL_FAILED_LOG =
     "----------------- Call to %s failed. Cause: {} -------------------------";

 public static final String CALL_TO_CEIDG_FAILED_LOG =
     String.format(CALL_FAILED_LOG, "CEIDG");

 public static final String CALL_TO_KRS_FAILED_LOG =
     String.format(CALL_FAILED_LOG, "KRS");
}
