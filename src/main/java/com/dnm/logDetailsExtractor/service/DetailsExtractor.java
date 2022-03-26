package com.dnm.logDetailsExtractor.service;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This Class is used to calculate and store all the IPs and URL data
 */
public class DetailsExtractor {

    private static final String IP_REGEX="[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}";
    private static final String URL_REGEX="(\\\"GET ).+( HTTP\\/1\\.1\\\")";

    private static final Map<String,Integer> IP_DATA=new HashMap<String,Integer>();
    private static final Map<String,Integer> URL_DATA=new HashMap<String,Integer>();

    private List<String> mostActiveIps=new ArrayList<String>();
    private List<String> secMostActiveIps=new ArrayList<String>();
    private List<String> thiMostActiveIps=new ArrayList<String>();

    private int mostActiveIpsOccurrences=0;
    private int secMostActiveIpsOccurrences=0;
    private int thiMostActiveIpsOccurrences=0;

    private List<String> mostUsedUrls=new ArrayList<String>();
    private List<String> secMostUsedUrls=new ArrayList<String>();
    private List<String> thiMostUsedUrls=new ArrayList<String>();

    private int mostUsedUrlsOccurrences=0;
    private int secMostUsedUrlsOccurrences=0;
    private int thiMostUsedUrlsOccurrences=0;

    /**
     * This method is used to calculate # of occurrence of IPs and store those data
     * @param logLine
     */
    public void setIPData(String logLine){
        Pattern pattern = Pattern.compile(IP_REGEX);
        Matcher matcher = pattern.matcher(logLine);
        if (matcher.find()) {
            String ip = matcher.group(0);
            Integer currentValue = IP_DATA.get(ip);
            if (currentValue == null) {
                IP_DATA.put(ip, 1);
            } else {
                IP_DATA.put(ip, currentValue + 1);
            }
        }
    }


    /**
     * This method is used to calculate # of occurrence of URLs and store those data
     * @param logLine
     */
    public void setUrlData(String logLine){
        Pattern pattern = Pattern.compile(URL_REGEX);
        Matcher matcher = pattern.matcher(logLine);
        if (matcher.find())
        {
            String urlComponent= matcher.group(0);
            String url=urlComponent.replaceAll("\"GET ","")
                                    .replaceAll(" HTTP/1.1\"","");
            Integer currentValue = URL_DATA.get(url);
            if (currentValue == null) {
                URL_DATA.put(url, 1);
            } else {
                URL_DATA.put(url, currentValue + 1);
            }
        }
    }

    public int getNumberOfUniqueIps(){
        return IP_DATA.keySet().size();
    }

    /**
     * This method is user to find most active IPs and store those data
     */
    public void setMostActiveIps(){
        Set<String> ips=IP_DATA.keySet();

        for (String ip:ips ) {
            int occurrences=IP_DATA.get(ip);
            if (occurrences>mostActiveIpsOccurrences){
                mostActiveIpsOccurrences=occurrences;
                secMostActiveIps.addAll(mostActiveIps);
                mostActiveIps.clear();
                mostActiveIps.add(ip);
            }else if(occurrences==mostActiveIpsOccurrences){
                mostActiveIps.add(ip);
            }else if(occurrences<mostActiveIpsOccurrences && occurrences>secMostActiveIpsOccurrences ){
                secMostActiveIpsOccurrences=occurrences;
                thiMostActiveIps.addAll(secMostActiveIps);
                secMostActiveIps.clear();
                secMostActiveIps.add(ip);
            }else if(occurrences==secMostActiveIpsOccurrences){
                secMostActiveIps.add(ip);
            }else if(occurrences<mostActiveIpsOccurrences && occurrences<secMostActiveIpsOccurrences &&
                    occurrences>thiMostActiveIpsOccurrences){
                thiMostActiveIpsOccurrences=occurrences;
                thiMostActiveIps.clear();
                thiMostActiveIps.add(ip);
            }else if(occurrences==thiMostActiveIpsOccurrences){
                thiMostActiveIps.add(ip);
            }
        }
    }

    /**
     * This method is user to find most active URLs and store those data
     */
    public void setMostUsedUrls(){
        Set<String> urls=URL_DATA.keySet();
        for (String url:urls ) {
            int occurrences=URL_DATA.get(url);
            if (occurrences>mostUsedUrlsOccurrences){
                mostUsedUrlsOccurrences=occurrences;
                secMostUsedUrls.addAll(mostUsedUrls);
                mostUsedUrls.clear();
                mostUsedUrls.add(url);
            }else if(occurrences==mostUsedUrlsOccurrences){
                mostUsedUrls.add(url);
            }else if(occurrences<mostUsedUrlsOccurrences && occurrences>secMostUsedUrlsOccurrences ){
                secMostUsedUrlsOccurrences=occurrences;
                thiMostUsedUrls.addAll(secMostUsedUrls);
                secMostUsedUrls.clear();
                secMostUsedUrls.add(url);
            }else if(occurrences==secMostUsedUrlsOccurrences){
                secMostUsedUrls.add(url);
            }else if(occurrences<mostUsedUrlsOccurrences && occurrences<secMostUsedUrlsOccurrences &&
                    occurrences>thiMostUsedUrlsOccurrences){
                thiMostUsedUrlsOccurrences=occurrences;
                thiMostUsedUrls.clear();
                thiMostUsedUrls.add(url);
            }else if(occurrences==thiMostUsedUrlsOccurrences){
                thiMostUsedUrls.add(url);
            }
        }
    }


    public List<String> getMostActiveIps() {
        return mostActiveIps;
    }

    public List<String> getSecMostActiveIps() {
        return secMostActiveIps;
    }

    public List<String> getThiMostActiveIps() {
        return thiMostActiveIps;
    }

    public int getMostActiveIpsOccurrences() {
        return mostActiveIpsOccurrences;
    }

    public int getSecMostActiveIpsOccurrences() {
        return secMostActiveIpsOccurrences;
    }

    public int getThiMostActiveIpsOccurrences() {
        return thiMostActiveIpsOccurrences;
    }

    public List<String> getMostUsedUrls() {
        return mostUsedUrls;
    }

    public List<String> getSecMostUsedUrls() {
        return secMostUsedUrls;
    }

    public List<String> getThiMostUsedUrls() {
        return thiMostUsedUrls;
    }

    public int getMostUsedUrlsOccurrences() {
        return mostUsedUrlsOccurrences;
    }

    public int getSecMostUsedUrlsOccurrences() {
        return secMostUsedUrlsOccurrences;
    }

    public int getThiMostUsedUrlsOccurrences() {
        return thiMostUsedUrlsOccurrences;
    }

}
