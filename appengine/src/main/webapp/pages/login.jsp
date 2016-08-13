<jsp:include page="/pages/header.jsp">
    <jsp:param name="title" value="Login Page"/>
    <jsp:param name="style" value="/css/login.css"/>
</jsp:include>
  </head>
  <body>
<hgroup>
    <h1>Login to API Bank</h1>
</hgroup>
<%
    String action=request.getParameter("action");
    if (action != null){
        if (action.equalsIgnoreCase("failed")){
            %>
            <errormsg><p class="error">Login attempt failed</p></errormsg>
            <%
        }
        if (action.equalsIgnoreCase("logout")){
            %>
            <errormsg><p class="success">You have been logged out</p></errormsg>
            <%
        }
    }
%>
<form action="/login" method="post">
  <div class="group">
    <input type="text" name="uname"><span class="highlight"></span><span class="bar"></span>
    <label>Username</label>
  </div>
  <div class="group">
    <input type="password" name="upassword"><span class="highlight"></span><span class="bar"></span>
    <label>Password</label>
  </div>
  <button type="submit" class="button buttonBlue" name="action" value="login">Login
    <div class="ripples buttonRipples"><span class="ripplesCircle"></span></div>
  </button>
</form>
<jsp:include page="/pages/footer.jsp"/>
    <script src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>
        <script src="/js/login.js"></script>
  </body>
</html>
