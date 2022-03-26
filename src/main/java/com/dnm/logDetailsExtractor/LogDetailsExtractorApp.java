package com.dnm.logDetailsExtractor;

import com.dnm.logDetailsExtractor.service.DetailsExtractor;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class LogDetailsExtractorApp {

    /**
     * Execution of the application is start with this method.Here are the duties of main method
     * -Lodding and reading log file
     * -executing DetailsExtractor's methods
     *
     * @param args
     */
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the log file path:");
        BufferedReader logFileReader;
        while (true){
            Path filepath= Paths.get(scanner.nextLine());
            try {
                logFileReader= Files.newBufferedReader(filepath);
            } catch (IOException e) {
                System.out.println("Wrong file.Please enter the correct log file path:");
                continue;
            }
            if (logFileReader!=null) {
                break;
            }
        }
        String logLine;
        DetailsExtractor detailsExtractor=new DetailsExtractor();
        try {
            while ((logLine = logFileReader.readLine()) != null) {
                detailsExtractor.setIPData(logLine);
                detailsExtractor.setUrlData(logLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        detailsExtractor.setMostActiveIps();
        detailsExtractor.setMostUsedUrls();

        System.out.println("Number of unique IPs: "+detailsExtractor.getNumberOfUniqueIps());
        printMostActiveIPs(detailsExtractor);
        printMostUsedUrls(detailsExtractor);
    }

    /**
     * This will use to print the details of IPs in"DetailsExtractor" class
     * @param detailsExtractor
     */
    private static void printMostActiveIPs(DetailsExtractor detailsExtractor){
        System.out.println("************************Most active IPs*******************************");
        for (String ip :
                detailsExtractor.getMostActiveIps()) {
            System.out.println(ip);
        }

        System.out.println("************************Second most active IPs************************");
        for (String ip :
                detailsExtractor.getSecMostActiveIps()) {
            System.out.println(ip);
        }

        System.out.println("************************Third most active IPs************************");
        for (String ip :
                detailsExtractor.getThiMostActiveIps()) {
            System.out.println(ip);
        }
    }

    /**
     * This will use to print the details of URLs in"DetailsExtractor" class
     * @param detailsExtractor
     */
    private static void printMostUsedUrls(DetailsExtractor detailsExtractor){
        System.out.println("************************Most used URLs************************");
        for (String url :
                detailsExtractor.getMostUsedUrls()) {
            System.out.println(url);
        }

        System.out.println("************************Second most used URLs************************");
        for (String url :
                detailsExtractor.getSecMostUsedUrls()) {
            System.out.println(url);
        }

        System.out.println("************************Third most used URLs************************");
        for (String url :
                detailsExtractor.getThiMostUsedUrls()) {
            System.out.println(url);
        }
    }
}
