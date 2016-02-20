<%-- 
    Document   : Authors
    Created on : Feb 4, 2016, 11:42:12 PM
    Author     : Scott
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Author Table</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">
        <link href="myCss.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
        <div class="row">
            <div class ="container">
                <div class="panel panel-default">
                    <div class="panel-heading">All Authors</div>
                    <div class="panel-body">
                        <p>Displayed a List of Author objects created in our AuthorService().  This is not currently hooked up to a Database. </p>
                    </div>
                    <table class="table table-hover" width="600" border="1" cellspacing="2" cellpadding="5">

                        <tr>
                            <th  class=" ">Author ID</th>
                            <th  class=" ">Name</th>
                            <th  class=" ">Date Added</th>
                        </tr>
                        <c:forEach var="a" items="${authors}">
                            <tr>

                                <td class="info"> ${a.authorId} </td>
                                <td class="info"> ${a.authorName} </td>
                                <td class="info">
                                    <fmt:formatDate pattern="MM/dd/yyyy" value="${a.dateAdded}"></fmt:formatDate>
                                    </td>
                                </tr>
                        </c:forEach>
                    </table>
                    <center>
                        <form id="back" name="back" method="POST" action="Home.jsp" style="padding:10px;">
                            <input class="btn btn-info" type="submit" name="submit" value="Take me Home">
                        </form>
                    </center>
                </div>
            </div>
        </div>
        <br>
        <div class="row">
            <footer class="footer">
                <div class="container">
                    Created by Scott Muth
                </div>
            </footer>
        </div>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.0/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js" integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS" crossorigin="anonymous"></script>
    </body>
</html>