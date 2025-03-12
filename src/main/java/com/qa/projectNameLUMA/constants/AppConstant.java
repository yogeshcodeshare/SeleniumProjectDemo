package com.qa.projectNameLUMA.constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AppConstant {

    public static final String CONFIG_FILE_PATH = "./src/test/resources/config/config.properties";
    public static final String CONFIG_QA_FILE_PATH = "./src/test/resources/config/qa.properties";
    public static final String CONFIG_DEV_FILE_PATH = "./src/test/resources/config/dev.properties";
    public static final String CONFIG_STAGE_FILE_PATH = "./src/test/resources/config/stage.properties";
    public static final String CONFIG_UAT_FILE_PATH = "./src/test/resources/config/UAT.properties";




    public static final String LOGIN_PAGE_TITLE = "Customer Login";
    public static final String LOGIN_PAGE_FRACTION_URL = "/customer/account/login/";
    public static final String Account_PAGE_FRACTION_URL =  ("/customer/account/");
    public static final String REGISTRATION_PAGE_TITLE = "Create New Customer Account";
    public static final String ACCOUNTS_PAGE_TITLE = "My Account";
    public static final String ACCOUNT_CREATE_SUCCESS_MESSAGE = "Thank you for registering with Main Website Store.";

    public static final List<String> ACCOUNT_PAGE_HEADER_LIST = Arrays.asList("My Account", "My Orders", "My Downloadable Products", "My Wish List", "Address Book", "Account Information", "Stored Payment Methods", "My Product Reviews", "Newsletter Subscriptions");


}
