package com.blm.saytheirnames.util;

public class StringValidation {

    //Return true if neither string is empty
    public static boolean isValidInput(String email, String password) {
        if (email.isEmpty() || password.isEmpty()) {
            return false;
        }
        return true;
    }

    //Returns true if the email is valid
    public static boolean isValidEmail(String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }

    public static boolean isPasswordEqual(String password, String retypePassword) {
        return password.equals(retypePassword);
    }
}
