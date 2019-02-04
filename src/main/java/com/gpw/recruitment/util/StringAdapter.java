package com.gpw.recruitment.util;


import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StringAdapter {
    private String adaptedString;


    public StringAdapter(String inputTxt) {
        adaptedString = inputTxt.substring(inputTxt.indexOf("\n"));
        adaptedString = adaptedString.replaceAll("1", " ").replaceAll("0", "#");

        String[] splitted = adaptedString.split("\n");
        splitted[2]="S" + splitted[2].substring(1,splitted[2].length());        //START POINT (0,1)
        splitted[splitted.length-2]=splitted[splitted.length-2].substring(0,splitted[splitted.length-2].length()-1)+"F";        //END POINT (n-1,n-2)


        Stream<String> lines = Arrays.stream(splitted);
        String data = lines.collect(Collectors.joining("\n"));
        lines.close();
        adaptedString = data.trim();
        System.out.println("AdaptedString");
        System.out.println(adaptedString);
    }

    @Override
    public String toString() {
        return adaptedString;
    }
}
