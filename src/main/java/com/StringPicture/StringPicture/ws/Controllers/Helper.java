package com.StringPicture.StringPicture.ws.Controllers;

import com.google.gson.JsonObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;

public class Helper {
    public static String generateJsonResponseMessage(String message) {

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("timestamp", generateNowTimeStamp());
        jsonObject.addProperty("status", 200);
        jsonObject.addProperty("message", message);

        return jsonObject.toString();
    }

    public static String generateJsonErrorMessage(String error, int status) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("timestamp", generateNowTimeStamp());
        jsonObject.addProperty("status", status);
        jsonObject.addProperty("message", error);

        return jsonObject.toString();
    }

    private static String generateNowTimeStamp() {
        return ZonedDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.ISO_INSTANT);
    }
}
