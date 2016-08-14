/**
 * Copyright 2015 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.appengine.openbank;

import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// [START example]
@SuppressWarnings("serial")
public class Login extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action.equals("logout")) {
            req.getSession().removeAttribute("user");
            req.getSession().removeAttribute("username");
            resp.sendRedirect("/pages/login.jsp?action=logout");
        }
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        final String USER_SUHAS = "Suhas";
        final String PWD_SUHAS = "Suhas123";
        final String USER_SUJAI = "Sujai";
        final String PWD_SUJAI = "Sujai123";
        OauthMethods oauthMethods = new OauthMethods();
        String action = req.getParameter("action");
        String uname = req.getParameter("uname");
        String upassword = req.getParameter("upassword");
        if (action.equalsIgnoreCase("login")){
            switch (uname){
                case USER_SUHAS:
                    if(upassword.equals(PWD_SUHAS)){
                        req.getSession().setAttribute("user", OauthMethods.USER_SUHAS);
                        req.getSession().setAttribute("username","Suhas");
                        try {
                            //String token = oauthMethods.initiate("http://driven-rider-133516.appspot.com/initiate");
                            String[] token = oauthMethods.initiate("http://localhost:8080/initiate");
                            req.getSession().setAttribute("token_secret",token[1]);
                            req.getSession().setAttribute("token",token[0]);
                            resp.sendRedirect("https://apisandbox.openbankproject.com/oauth/authorize?oauth_token=" + token[0]);
                        } catch (OAuthMessageSignerException | OAuthExpectationFailedException | OAuthCommunicationException e) {
                            e.printStackTrace();
                        }
                    }
                    else {
                        resp.sendRedirect("/pages/login.jsp?action=failed");
                    }
                    break;
                case USER_SUJAI:
                    if(upassword.equals(PWD_SUJAI)){
                        req.getSession().setAttribute("user", OauthMethods.USER_SUJAI);
                        req.getSession().setAttribute("username","Sujai");
                        try {
                            String[] token = oauthMethods.initiate("http://localhost:8080/initiate");
                            req.getSession().setAttribute("token_secret",token[1]);
                            req.getSession().setAttribute("token",token[0]);
                            resp.sendRedirect("https://apisandbox.openbankproject.com/oauth/authorize?oauth_token=" + token[0]);
                        } catch (OAuthMessageSignerException | OAuthExpectationFailedException | OAuthCommunicationException e) {
                            e.printStackTrace();
                        }
                    }
                    else {
                        resp.sendRedirect("/pages/login.jsp?action=failed");
                    }
                    break;
                default:
                    resp.sendRedirect("/pages/login.jsp?action=failed");
            }
        }
        else
            resp.sendRedirect("/pages/login.jsp?action=failed");
    }
}
// [END example]
