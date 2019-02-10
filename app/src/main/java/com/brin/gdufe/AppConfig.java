package com.brin.gdufe;

public class AppConfig {

    public static String sno = "";                                  //用户学号
    public static String idsPwd = "";                               //信息门户密码
    public static String jwPwd = "";                                //教务系统密码
    public static final String schoolmateSno = "iAmSchoolMate";          //校友/外校体验账号
    public static final String schoolmatePwd = "areU";
    public static int defaultPage = 0;                              //app启动后的默认页面
    public static String localAesSeed = "local";                   //本地存密码的加密key
    public static String appVer = "";                               //ApiUtils.java没有Context，故全局变量实现
    public static String sdSaveDirName = "mygdufe";                 //Sd卡存储空间目录名

    public static final String BASE_URL = "http://app.wegdufe.com:82/";
//    public static final String BASE_URL = "http://192.168.0.103:82/";
    public static final String WXSHARE_URL = "http://www.wegdufe.com";
    public static final String BASE_URL_BACKUP = "http://app.wintercoder.com:82/";

    //在线头像获取
    public static final String Avator_URL_BASE = "https://www.94cb.com/";
    public static final String Avatar_URL = Avator_URL_BASE + "Material-Design-Avatars/avatar.php";
    public static final String WXShareAPP_ID = "wx676c0c6a46d9b86a";


    public static class Url {
        public static final String index = "index.php?r=";
        public static final String getScore = index + "jw/get-grade";
        public static final String getSchedule = index + "jw/get-schedule";
        public static final String getBasicInfo = index + "jw/get-basic";

        public static final String fewSztz = index + "info/few-sztz";

        public static final String getXiaoLi = index + "jwc/get-xiaoli";
        public static final String getCet = index + "jwc/get-cet";

        public static final String currentBook = index + "opac/current-book";
        public static final String borrowedBook = index + "opac/borrowed-book";
        public static final String searchBook = index + "opac/search-book";
        public static final String getRenewBookVerify = index + "opac/get-renew-book-verify";
        public static final String renewBook = index + "opac/renew-book";
        public static final String getBookStoreDetail = index + "opac/get-book-store-detail";

        public static final String getCurrentCash = index + "card/current-cash";
        public static final String cardConsumeToday = index + "card/consume-today";
        public static final String getElectric = index + "card/get-electric";

        public static final String feedback = index + "work/feedback";
        public static final String updateURL = index + "work/check-app-update";
        public static final String appTips = index + "work/get-app-tips";
        public static final String allLogout = index + "work/all-logout";

        public static final String getDocument = index + "work/get-document";

        public static final String getImFeedList = index + "social/feed/list-im-feed";
        public static final String getImFeedReplyList = index + "social/feed/list-im-feed-reply";
        public static final String createImFeedRelay = index + "social/feed/create-im-feed-reply";
        public static final String createImFeed = index + "social/feed/create-im-feed";



    }

}
