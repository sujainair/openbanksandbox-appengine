<%@ page import="java.util.List" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.appengine.openbank.*" %>
<%
    if (session.getAttribute("user") == null) {
        %>
        <jsp:forward page = "/pages/login.jsp?action=failed" />
        <%
    }
    String title = "Welcome - " + session.getAttribute("username");
    List<Transaction> transactions = (List) session.getAttribute("transactions");
%>
<jsp:include page="/pages/header.jsp">
    <jsp:param name="title" value="<%= title %>"/>
    <jsp:param name="style" value="/css/home.css"/>
</jsp:include>
        <link rel="stylesheet" href="/css/material.css">
        <script src="/js/webcomponents-lite.js"></script>
        <script src="/js/material.js"></script>
    </head>
    <body>
    <!-- Always shows a header, even in smaller screens. -->
    <div class="mdl-layout mdl-js-layout mdl-layout--fixed-header">
      <header class="mdl-layout__header">
        <div class="mdl-layout__header-row">
          <!-- Title -->
          <span class="mdl-layout-title"><%= title %></span>
          <!-- Add spacer, to align navigation to the right -->
          <div class="mdl-layout-spacer"></div>
          <!-- Navigation. We hide it in small screens. -->
          <nav class="mdl-navigation mdl-layout--large-screen-only">
            <a class="mdl-navigation__link" href="/home">View Transactions</a>
            <a class="mdl-navigation__link" href="/pages/transfer.jsp">Transfer Money</a>
            <a class="mdl-navigation__link" href="/login?action=logout">Logout</a>
          </nav>
        </div>
      </header>
      <div class="mdl-layout__drawer">
        <!--<span class="mdl-layout-title">Title</span> -->
        <nav class="mdl-navigation">
          <a class="mdl-navigation__link" href="/home">View Transactions</a>
          <a class="mdl-navigation__link" href="/pages/transfer.jsp">Transfer Money</a>
          <a class="mdl-navigation__link" href="/login?action=logout">Logout</a>
        </nav>
      </div>
      <main class="mdl-layout__content">
        <div class="page-content">
            <c:set var="iterator" value="0"/>
            <c:forEach items="${transactions}" var="transaction" >
            <c:set var="iterator" value="${iterator + 1}"/>
            <button class="heading" aria-controls="collapse${iterator}" onclick="toggle('#collapse${iterator}')" aria-expanded="false">
                <div class="button_left"><c:out value="${transaction.other}" /></div>
                <div class="button_right"><c:out value="${transaction.amount}" /></div>
            </button>
            <iron-collapse id="collapse${iterator}" tabindex="0" role="group" aria-hidden="true" aria-expanded="false" class="iron-collapse-closed" style="transition-property: max-height; max-height: 100px; transition-duration: 0s;">
                <div class="content">
                <div>
                <table>
                    <tr>
                        <td>Transaction ID</td>
                        <td> : <c:out value="${transaction.id}"/></td>
                    </tr>
                    <tr>
                        <td>Time</td>
                        <td> : <c:out value="${transaction.time}"/></td>
                    </tr>
                    <tr>
                        <td>From Bank</td>
                        <td> : <c:out value="${transaction.bank}"/></td>
                    </tr>
                    <tr>
                        <td>Balance after transaction</td>
                        <td> : <c:out value="${transaction.balance}"/></td>
                    </tr>
                </table>
                </div>
                </div>
            </iron-collapse>
            </c:forEach>
            <jsp:include page="/pages/footer.jsp"/>
        </div>
      </main>
    </div>
<script>

  function toggle(selector) {
    if (document.querySelector(selector).getAttribute("class") == "iron-collapse-closed")
        document.querySelector(selector).setAttribute("class","iron-collapse-opened");
    else
        document.querySelector(selector).setAttribute("class","iron-collapse-closed");
  }
</script>
  </body>
</html>