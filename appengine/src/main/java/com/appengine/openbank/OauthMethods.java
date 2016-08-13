package com.appengine.openbank;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.http.HttpParameters;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by sujai on 19-06-2016.
 */
public class OauthMethods {
    private static final String CONS_KEY_SUHAS = "awecf0r5ixzrycpuyqjam4jzk2tslz0ngh0o2hcc";
    private static final String CONS_SECRET_SUHAS = "32shes2iv3dhdqv1syjq1wp0i5sejs1impqoriqe";
    private static final String TOKEN_SUHAS = "LO2CE1HLIWSXEQLCEEDRPBVCXPXMGIP0LXE5D1VW";
    private static final String TOKEN_SECRET_SUHAS = "IUOWKVFEWTSIWRZDGGFCP5I2WCH4G02EG3LAZZGZ";
    public static final String USER_SUHAS = "0";
    public static final String NAME_SUHAS = "Suhas";
    private static final String CONS_KEY_SUJAI = "dhlk4ijb433mmbegc33q0bmmv2oub2wrzmgs1jvu";
    private static final String CONS_SECRET_SUJAI = "ikkai5e1z4s0ppv4u3y34tpp1cdb1bmn2cmzssob";
    private static final String TOKEN_SUJAI = "1DIVMBAGD3QTL5WNYTS4XFZ2PLSCECW1LZDC3I3T";
    private static final String TOKEN_SECRET_SUJAI = "LJMMR5QOGTS2ZPR33FTZ15YXOBSQ1TNMW5S3TP5Z";
    public static final String USER_SUJAI = "1";
    public static final String NAME_SUJAI = "Sujai";

    public String post(String endpointUrl, String JSONPayload, String user)throws IOException, OAuthMessageSignerException, OAuthExpectationFailedException, OAuthCommunicationException {

        String CONS_KEY = null, CONS_SECRET = null, TOKEN = null, TOKEN_SECRET = null;
        switch (user){
            case USER_SUHAS:
                CONS_KEY = CONS_KEY_SUHAS;
                CONS_SECRET = CONS_SECRET_SUHAS;
                TOKEN = TOKEN_SUHAS;
                TOKEN_SECRET = TOKEN_SECRET_SUHAS;
                break;
            case USER_SUJAI:
                CONS_KEY = CONS_KEY_SUJAI;
                CONS_SECRET = CONS_SECRET_SUJAI;
                TOKEN = TOKEN_SUJAI;
                TOKEN_SECRET = TOKEN_SECRET_SUJAI;
                break;
        }
        //final String JSONPayload = "{\r\n\t\"bank_id\":\"in-bank-x-1\",\r\n\t\"account_id\":\"suhas_IND\",\r\n\t\"amount\":\"100.00\"\r\n}";

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
        String CONS_KEY = null, CONS_SECRET = null, TOKEN = null, TOKEN_SECRET = null;
        switch (user){
            case USER_SUHAS:
                CONS_KEY = CONS_KEY_SUHAS;
                CONS_SECRET = CONS_SECRET_SUHAS;
                TOKEN = TOKEN_SUHAS;
                TOKEN_SECRET = TOKEN_SECRET_SUHAS;
                break;
            case USER_SUJAI:
                CONS_KEY = CONS_KEY_SUJAI;
                CONS_SECRET = CONS_SECRET_SUJAI;
                TOKEN = TOKEN_SUJAI;
                TOKEN_SECRET = TOKEN_SECRET_SUJAI;
                break;
        }

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
        String CONS_KEY = null, CONS_SECRET = null, TOKEN = null, TOKEN_SECRET = null;
        switch (user){
            case USER_SUHAS:
                CONS_KEY = CONS_KEY_SUHAS;
                CONS_SECRET = CONS_SECRET_SUHAS;
                break;
            case USER_SUJAI:
                CONS_KEY = CONS_KEY_SUJAI;
                CONS_SECRET = CONS_SECRET_SUJAI;
                break;
        }

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
