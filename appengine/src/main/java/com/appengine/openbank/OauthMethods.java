package com.appengine.openbank;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.http.HttpParameters;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;
import java.util.Scanner;

/**
 * Created by sujai on 19-06-2016.
 */
public class OauthMethods {
    public static final String USER_SUHAS = "0";
    public static final String NAME_SUHAS = "Suhas";
    public static final String USER_SUJAI = "1";
    public static final String NAME_SUJAI = "Sujai";

    public String post(String endpointUrl, String JSONPayload, String user)throws IOException, OAuthMessageSignerException, OAuthExpectationFailedException, OAuthCommunicationException {

        String CONS_KEY, CONS_SECRET, TOKEN, TOKEN_SECRET;
        Properties prop = new Properties();
        try{
            prop.load(new FileInputStream("resources/dev.properties"));
        } catch (FileNotFoundException e){
            System.err.println("[ERROR] Properties file missing!");
        } catch (IOException e) {
            System.err.println("[ERROR] Unable to read properties file");
        }
        //Google DB not free so using prop file
        CONS_KEY = prop.getProperty("CONS_KEY");
        CONS_SECRET = prop.getProperty("CONS_SECRET");
        TOKEN = prop.getProperty("TOKEN");
        TOKEN_SECRET = prop.getProperty("TOKEN_SECRET");

        //Create an HttpURLConnection and add some headers
        URL url = new URL(endpointUrl);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestProperty("Accept", "application/json");
        urlConnection.setRequestProperty("Content-Type", "application/json; charset=utf8");
        urlConnection.setRequestMethod("POST");
        urlConnection.setDoOutput(true);

        //Sign the request
        OAuthConsumer dealabsConsumer = new DefaultOAuthConsumer(CONS_KEY,CONS_SECRET);
        dealabsConsumer.setTokenWithSecret(TOKEN,TOKEN_SECRET);
        dealabsConsumer.sign(urlConnection);

        //Send the payload to the connection
        OutputStreamWriter outputStreamWriter = null;
        try {
                outputStreamWriter = new OutputStreamWriter(urlConnection.getOutputStream(), "UTF-8");
                outputStreamWriter.write(JSONPayload);
                //System.out.println(JSONPayload);
        } finally {
                if (outputStreamWriter != null) {
                        outputStreamWriter.close();
                }
        }

        //Send the request and read the output
        try {
                //System.out.println("Response: " + urlConnection.getResponseCode() + " " + urlConnection.getResponseMessage());
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                String inputStreamString = new Scanner(in,"UTF-8").useDelimiter("\\A").next();
                //System.out.println(inputStreamString);
                return inputStreamString;
        }
        finally {
                urlConnection.disconnect();
        }
    }

    public String get(String endpointUrl, String user)throws IOException, OAuthMessageSignerException, OAuthExpectationFailedException, OAuthCommunicationException {
        String CONS_KEY, CONS_SECRET, TOKEN, TOKEN_SECRET;
        Properties prop = new Properties();
        try{
            prop.load(new FileInputStream("resources/dev.properties"));
        } catch (FileNotFoundException e){
            System.err.println("[ERROR] Properties file missing!");
        } catch (IOException e) {
            System.err.println("[ERROR] Unable to read properties file");
        }
        //Google DB not free so using prop file
        CONS_KEY = prop.getProperty("CONS_KEY");
        CONS_SECRET = prop.getProperty("CONS_SECRET");
        TOKEN = prop.getProperty("TOKEN");
        TOKEN_SECRET = prop.getProperty("TOKEN_SECRET");

        //Create an HttpURLConnection and add some headers
        URL url = new URL(endpointUrl);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestProperty("Accept", "application/json");
        urlConnection.setRequestMethod("GET");
        urlConnection.setDoOutput(true);

        //Sign the request
        OAuthConsumer dealabsConsumer = new DefaultOAuthConsumer(CONS_KEY,CONS_SECRET);
        dealabsConsumer.setTokenWithSecret(TOKEN,TOKEN_SECRET);
        dealabsConsumer.sign(urlConnection);

        //Send the request and read the output
        try {
            //System.out.println("Response: " + urlConnection.getResponseCode() + " " + urlConnection.getResponseMessage());
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            String inputStreamString = new Scanner(in,"UTF-8").useDelimiter("\\A").next();
            //System.out.println(inputStreamString);
            return inputStreamString;
        }
        finally {
            urlConnection.disconnect();
        }
    }

    public String[] initiate (String callback,String user)throws IOException, OAuthMessageSignerException, OAuthExpectationFailedException, OAuthCommunicationException {
        String CONS_KEY, CONS_SECRET;
        Properties prop = new Properties();
        try{
            prop.load(new FileInputStream("resources/dev.properties"));
        } catch (FileNotFoundException e){
            System.err.println("[ERROR] Properties file missing!");
        } catch (IOException e) {
            System.err.println("[ERROR] Unable to read properties file");
        }
        //Google DB not free so using prop file
        CONS_KEY = prop.getProperty("CONS_KEY");
        CONS_SECRET = prop.getProperty("CONS_SECRET");

        //Create an HttpURLConnection and add some headers
        URL url = new URL("https://apisandbox.openbankproject.com/oauth/initiate");
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestProperty("Accept", "application/json");
        urlConnection.setRequestMethod("POST");
        urlConnection.setDoOutput(true);

        //Sign the request
        OAuthConsumer dealabsConsumer = new DefaultOAuthConsumer(CONS_KEY,CONS_SECRET);
        HttpParameters doubleEncodedParams =  new HttpParameters();
        doubleEncodedParams.put("oauth_callback","http://driven-rider-133516.appspot.com/initiate");
        dealabsConsumer.setAdditionalParameters(doubleEncodedParams);
        dealabsConsumer.sign(urlConnection);

        String response = null;
        //Send the request and read the output
        try {
            //System.out.println("Response: " + urlConnection.getResponseCode() + " " + urlConnection.getResponseMessage());
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            String inputStreamString = new Scanner(in,"UTF-8").useDelimiter("\\A").next();
            //System.out.println(inputStreamString);
            response = inputStreamString;
        }
        finally {
            urlConnection.disconnect();
        }

        String[] respArray = response.split("&");
        String[] tempToken = {respArray[0].split("=")[1],respArray[1].split("=")[1]};
        return tempToken;
    }
}
