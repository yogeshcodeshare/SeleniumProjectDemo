package com.qa.projectNameLUMA.utils;

import java.util.Random;

public class StringUtil {


    public static String getRandomEmailID(){
        String emailID = "usertestautomation" + System.currentTimeMillis() + "@test.com";
        System.out.println("user email Id : " + emailID);
        return emailID;
    }

    public static String getRandomName(){
        String name = "usertestautomation" + System.currentTimeMillis();
        System.out.println("user name : " + name);
        return name;
    }

    /**
     * Generates a random 10-digit Indian mobile number starting with 96.
     *
     * @return A random 10-digit Indian mobile number as a String.
     */
    public static String generateIndianMobileNumber() {
        Random random = new Random();
        // Start the mobile number with "96"
        StringBuilder mobileNumber = new StringBuilder("96");

        // Generate the remaining 8 digits
        for (int i = 0; i < 8; i++) {
            mobileNumber.append(random.nextInt(10)); // Append a random digit between 0 and 9
        }
        return mobileNumber.toString();
    }
}
