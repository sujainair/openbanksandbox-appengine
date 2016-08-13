package com.appengine.openbank;

import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sujai on 23-06-2016.
 */


public class JSON2List {

    private static final String API_LINK = "https://apisandbox.openbankproject.com/obp/v1.2.1";
    private static final String BANK = "in-bank-x-1";
    private static final String AC_SUHAS = "suhas_IND";
    private static final String AC_SUJAI = "ing_1";

    public String getTransactions (String user) {
        //System.out.println("getTransactions" + user);
        OauthMethods oauthMethods = new OauthMethods();
        String transactionString = null;
        String ACName;
        switch (user){
            case OauthMethods.USER_SUHAS:
                ACName = AC_SUHAS;
                break;
            case OauthMethods.USER_SUJAI:
                ACName = AC_SUJAI;
                break;
            default:
                ACName = "FAILED";
        }
        String getLink = API_LINK + "/banks/" + BANK + "/accounts/" + ACName + "/owner/transactions";
        try {
            transactionString =  oauthMethods.get(getLink,user);
        } catch (OAuthMessageSignerException | OAuthExpectationFailedException | OAuthCommunicationException | IOException e) {
            e.printStackTrace();
        }
        return transactionString;
    }

    public List<Transaction> createList(String user){
        //System.out.println("CreateList : " + user);
        String JSONString = getTransactions(user);
        JSONObject obj = new JSONObject(JSONString);
        JSONArray transactionArr = obj.getJSONArray("transactions");
        List<Transaction> list = new ArrayList<>();
        //System.out.println(transactionArr.length());
        for (int i = 0; i < transactionArr.length(); i++) {
            //System.out.println(i);
            //System.out.println(transactionArr.getJSONObject(i).getString("id"));
            Transaction item = new Transaction();
            item.id = transactionArr.getJSONObject(i).getString("id");
            //System.out.println(item.id);
            item.other = transactionArr.getJSONObject(i).getJSONObject("other_account").getJSONObject("holder").getString("name");
            //System.out.println(item[i].getOther());
            item.bank = transactionArr.getJSONObject(i).getJSONObject("other_account").getJSONObject("bank").getString("name");
            //System.out.println(item[i].getBank());
            item.amount = transactionArr.getJSONObject(i).getJSONObject("details").getJSONObject("value").getString("amount");
            //System.out.println(item[i].getAmount());
            String time = transactionArr.getJSONObject(i).getJSONObject("details").getString("completed");
            item.time = time.replace("T"," ").replace("Z","");
            //System.out.println(item[i].getTime());
            item.balance = transactionArr.getJSONObject(i).getJSONObject("details").getJSONObject("new_balance").getString("amount");
            //System.out.println(item[i].getBalance());
            list.add(item);
        }
        return list;
    }

    public String sendJson(String user, String toName, String amount){
        String to_ac;
        switch (toName){
            case OauthMethods.NAME_SUHAS:
                to_ac = AC_SUHAS;
                break;
            case OauthMethods.NAME_SUJAI:
                to_ac = AC_SUJAI;
                break;
            default:
                to_ac = "FAILED";
        }
        JSONObject obj = new JSONObject();
        obj.put("bank_id",BANK);
        obj.put("account_id",to_ac);
        obj.put("amount",amount);
        String JSONString = obj.toString();
        OauthMethods oauthMethods = new OauthMethods();
        String response = null;
        String ACName;
        switch (user){
            case OauthMethods.USER_SUHAS:
                ACName = AC_SUHAS;
                break;
            case OauthMethods.USER_SUJAI:
                ACName = AC_SUJAI;
                break;
            default:
                ACName = "FAILED";
        }
        String getLink = API_LINK + "/banks/" + BANK + "/accounts/" + ACName + "/owner/transactions";
        String transactionid=null;
        try {
            response =  oauthMethods.post(getLink,JSONString,user);
            JSONObject respObj = new JSONObject(response);
            transactionid = respObj.getString("transaction_id");
        } catch (OAuthMessageSignerException | OAuthExpectationFailedException | OAuthCommunicationException | IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            transactionid = "failed";
        }
        return transactionid;
    }
}
