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


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class TransactionServlet extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
    String user = (String) req.getSession().getAttribute("user");
    if (user == null){
      resp.sendRedirect("/pages/login.jsp?action=failed");
      return;
    }
    JSON2List json2List = new JSON2List();
    List<Transaction> transactions = json2List.createList(user);
    req.setAttribute("transactions",transactions);
    RequestDispatcher dispatcher = req.getRequestDispatcher("/pages/home.jsp");
    dispatcher.forward(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String user = (String) req.getSession().getAttribute("user");
    if (user == null){
      resp.sendRedirect("/pages/login.jsp?action=failed");
      return;
    }
    String action = (String) req.getParameter("action");
    if (action.equals("transfer")){
      JSON2List json2List  = new JSON2List();
      String transactionId = json2List.sendJson(user,req.getParameter("toname"),req.getParameter("amount"));
      resp.sendRedirect("/pages/transfer.jsp?action=" + transactionId);
    }
    else
      resp.sendRedirect("/home");
  }
}
// [END example]
