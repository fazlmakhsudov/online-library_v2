<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <title>Bootstrap Example</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
</head>
<body>
<c:import url="header.jsp" />

<div class="jumbotron text-center">
  <h1>Online Library</h1>
  <p>Totally free for people who like to read </p>
</div>

<div class="container">

    <div class="row">
       <c:set var="raw_count" value="0" scope="page" />
       <c:forEach var="book" items="${list_books}">
           <c:set var="raw_count" value="${raw_count + 1}" scope="page"/>
           <div class="col-sm-4">
                <img src="/online-library/img/book2.jpeg" class="img-thumbnail" alt="no image" />
                <h3 class ="text-center">
                    <a href="books?id=<c:out value='${book.id}' />">
                        <c:out value="${book.name}"/>
                    </a>
                </h3>
           </div>
           <c:if test="${raw_count == 3}">
             </div>
             <div class="row">
             <c:set var="raw_count" value="0" scope="page"/>
           </c:if>
       </c:forEach>
   </div>

</div>
<c:import url="footer.jsp" />
</body>
</html>