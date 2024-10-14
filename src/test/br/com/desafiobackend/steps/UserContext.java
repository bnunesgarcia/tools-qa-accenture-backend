package br.com.desafiobackend.steps;

public class UserContext {

    private static String userID;

    public static String getUserID() {
        return userID;
    }

    public static void setUserID(String userID) {
        UserContext.userID = userID;
    }

}
