<%
    if (session.getAttribute("user") == null) {
        %>
        <jsp:forward page = "/pages/login.jsp?action=failed" />
        <%
    }
    String title = "Send Money - " + session.getAttribute("username");
%>
<jsp:include page="/pages/header.jsp">
    <jsp:param name="title" value="<%= title %>"/>
    <jsp:param name="style" value="/css/login.css"/>
</jsp:include>
  </head>
  <body>
<hgroup>
    <h1>Send Money</h1>
</hgroup>
<%
    String action=request.getParameter("action");
    if (action != null){
        if (action.equalsIgnoreCase("failed")){
            %>
            <errormsg><p class="error">Transfer failed</p></errormsg>
            <%
        }
        else {
            %>
                <errormsg><p class="success">Transaction done with ID : <%= action %></p></errormsg>
            <%
        }
    }
%>
<form action="/home" method="post">
  <div class="group">
    <input type="text" name="toname"><span class="highlight"></span><span class="bar"></span>
    <label>Send To</label>
  </div>
  <div class="group">
    <input type="number" name="amount" min="1"><span class="highlight"></span><span class="bar"></span>
    <label>Amount (EUR)</label>
  </div>
  <button type="submit" class="button buttonYellow" name="action" value="transfer">Transfer
    <div class="ripples buttonRipples"><span class="ripplesCircle"></span></div>
  </button>
  <button type="submit" class="button buttonBlue" name="action" value="home">Back Home
      <div class="ripples buttonRipples"><span class="ripplesCircle"></span></div>
  </button>
</form>
<jsp:include page="/pages/footer.jsp"/>
    <script src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>
        <script src="/js/login.js"></script>
  </body>
</html>
