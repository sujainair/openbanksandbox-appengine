<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="com.appengine.openbank.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
    String title = request.getParameter("title");
    String style = request.getParameter("style");
%>
<html >
  <head>
    <meta charset="UTF-8">
    <title><%= title %></title>
    <link rel="stylesheet" href="<%= style %>">