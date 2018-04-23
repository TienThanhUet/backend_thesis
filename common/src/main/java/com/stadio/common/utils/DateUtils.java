package com.stadio.common.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils
{
    private static Logger logger = LogManager.getLogger(DateUtils.class);

    public static Date dateFormStr(String inDate, String dateFormatStr)
    {
        logger.debug(">>> dateFormStr: " + inDate + " -> " + dateFormatStr);
        SimpleDateFormat dateFormat = new SimpleDateFormat(dateFormatStr);
        dateFormat.setLenient(false);
        try
        {
            return dateFormat.parse(inDate.trim());
        }
        catch (ParseException pe)
        {
            return null;
        }
    }
}
