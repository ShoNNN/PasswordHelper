package com.vladislavkulikov.school.passwordlayout;

import java.util.Random;

public class PasswordsGenerator {

    private String [] englishSimbolsArray;
    private String [] digitsArray;
    private String [] serviceSimbolsArray;

    private int passwordLength;
    private boolean upperCase;
    private boolean digits;
    private boolean serviceSimbols;

    public PasswordsGenerator(String[] englishSimbols, String[] digitsArray, String[] serviceSimbolsArray, int passwordLength, boolean upperCase, boolean digits, boolean serviceSimbols) {
        this.englishSimbolsArray = englishSimbols;
        this.digitsArray = digitsArray;
        this.serviceSimbolsArray = serviceSimbolsArray;
        this.passwordLength = passwordLength;
        this.upperCase = upperCase;
        this.digits = digits;
        this.serviceSimbols = serviceSimbols;
    }

    public String generate(){

        StringBuilder dataString = new StringBuilder();
        StringBuilder result = new StringBuilder(passwordLength);
        Random random = new Random();

        for (String str:englishSimbolsArray) {
            dataString.append(str);
        }

        if (upperCase){
            for (String str:englishSimbolsArray) {
                dataString.append(str.toUpperCase());
            }
        }

        if (digits){
            for (String str:digitsArray) {
                dataString.append(str);
            }
        }

        if (serviceSimbols){
            for (String str:serviceSimbolsArray) {
                dataString.append(str);
            }
        }

        char[] dataStringsArray = dataString.toString().toCharArray();

        for (int i = 0; i < passwordLength; i++) {
            result.append(dataStringsArray[random.nextInt(dataStringsArray.length)]);
        }

        return result.toString();
    }

}
