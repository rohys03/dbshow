package com.st11.dbshow.common;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class DbShow {

    public static String getCurrentTime() {
        DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG);
        String formattedDate = dateFormat.format(new Date());

        return formattedDate;
    }

    public static Map getParameterMap(HttpServletRequest request){

        Map parameterMap = new HashMap();
        Enumeration enums = request.getParameterNames();

        while(enums.hasMoreElements()){
            String paramName = (String)enums.nextElement();
            String[] parameters = request.getParameterValues(paramName);

            if(parameters.length > 1){
                parameterMap.put(paramName, parameters);
            }else{
                parameterMap.put(paramName, parameters[0]);
            }
        }
        return parameterMap;
    }

    public static boolean isNullOrEmpty(String str) {
        if(str != null && !str.trim().isEmpty())
            return false;
        return true;
    }

    public static String getRankColor(int rank1, int rank2) {

        int rankDiff = rank1 - rank2;

        if (rankDiff == 0) return "WHITE";
        if (rank2 == 0) return "WHITE";

        else if (rankDiff > 0 && rankDiff <= 10) return "#AED6F1";
        else if (rankDiff > 10 && rankDiff <= 50) return "#AED6F1";
        else if (rankDiff > 50) return "#5DADE2";

        else if (rankDiff < 0 && rankDiff >= -10) return "#F5B7B1";
        else if (rankDiff < 10 && rankDiff >= -50) return "#F1948A";
        else if (rankDiff < -50) return "#EC7063";

        return "WHITE";

    }
    public static String getRankColor(int rankDiff) {

//        int rankDiff = rank1 - rank2;

        if (rankDiff == 0) return "WHITE";

        else if (rankDiff > 0 && rankDiff <= 10) return "#AED6F1";
        else if (rankDiff > 10 && rankDiff <= 50) return "#AED6F1";
        else if (rankDiff > 50) return "#5DADE2";

        else if (rankDiff < 0 && rankDiff >= -10) return "#F5B7B1";
        else if (rankDiff < 10 && rankDiff >= -50) return "#F1948A";
        else if (rankDiff < -50) return "#EC7063";

        return "WHITE";

    }
}
