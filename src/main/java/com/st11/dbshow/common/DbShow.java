package com.st11.dbshow.common;

import com.st11.dbshow.repository.DaDbVO;
import com.st11.dbshow.service.DbShowService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.util.*;

public class DbShow {

    private TreeMap dbList = new TreeMap<>();
    @Autowired
    DbShowService dbShowService;

    public DbShow(Collection<DaDbVO> dbList) {

//        System.out.println("[setDbList.dbList]" + dbList.toString());
        for (DaDbVO dbVO : dbList ) {
            this.dbList.put(dbVO.getDbId(),dbVO.getDbNm());
        }
    }

    public Map<Integer, String> getDbList() {
        return this.dbList;
    }


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


    public static String divDataChar(long numerator, long denominator) {
        String data = "";

        if (denominator > 0) {
            try {
                data = String.format("%.2f", (numerator / denominator) );
                System.out.println("Data] ] : " + data);
            } catch (ArithmeticException e) {
                e.printStackTrace();
                throw e;
            }
        }
        return data;
    }

    public static String getRankColor(int rank2, int rank1) {

        String rankColor = "WHITE";
        int rankDiff = rank1 - rank2;

        if (rankDiff == 0) rankColor = "WHITE";
        if (rank2 == 0) rankColor = "#FFFF00";

        // Blue Color
        else if (rankDiff > 0 && rankDiff <= 10) rankColor = "#AED6F1";
        else if (rankDiff > 10 && rankDiff <= 50) rankColor = "#AED6F1";
        else if (rankDiff > 50) rankColor =  "#5DADE2";

        //Red Color
        else if (rankDiff < 0 && rankDiff >= -10) rankColor = "#F5B7B1";
        else if (rankDiff < -10 && rankDiff >= -50) rankColor = "#F1948A";
        else if (rankDiff < -50) rankColor = "#EC7063";

//        System.out.println("[getRankColor]: " + rank1 +", " + rank2 + ", " + rankColor);
        return rankColor;

    }

}
