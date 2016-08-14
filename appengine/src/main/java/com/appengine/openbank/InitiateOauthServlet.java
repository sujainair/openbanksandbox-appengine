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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

// [START example]
@SuppressWarnings("serial")
public class InitiateOauthServlet extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    OauthMethods oauthMethods = new OauthMethods();
    try {
      String[] token = oauthMethods.initiate("https://apisandbox.openbankproject.com/oauth/token",req.getParameter("oauth_verifier"),req.getSession().getAttribute("token").toString(), req.getSession().getAttribute("token_secret").toString());
      req.getSession().setAttribute("token",token[0]);
      req.getSession().setAttribute("token_secret",token[1]);
    } catch (OAuthMessageSignerException | OAuthExpectationFailedException | OAuthCommunicationException e) {
      e.printStackTrace();
    }
    resp.sendRedirect("/home");
  }
}
// [END example]
