<%-- 
    Document   : Edit
    Created on : Feb 25, 2016, 10:31:42 PM
    Author     : Scott
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <c:choose>
                    <c:when test="${not empty author}">
                        <tr>
                            <td style="background-color: black;color:white;" align="left">ID</td>
                            <td align="left"><input type="text" value="${author.authorId}" name="authorId" readonly/></td>
                        </tr>         
                    </c:when>
                </c:choose>
                
                <tr>
                    <td style="background-color: black;color:white;" align="left">Name</td>
                    <td align="left"><input type="text" value="${author.authorName}" name="authorName" /></td>
                </tr>
                
                <c:choose>
                    <c:when test="${not empty author}">
                        <tr>
                            <td style="background-color: black;color:white;" align="left">Date Added</td>
                    <td align="left"><input type="text" value="${author.dateAdded}" name="dateAdded" readonly /></td>
                        </tr>         
                    </c:when>
                </c:choose>
    </body>
</html>
