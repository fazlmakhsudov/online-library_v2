<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Book detail page</title>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">

</head>
<body>
<c:import url="header.jsp" />
<div class="container">
  <img src="/library/img/book.jpeg" class="img-thumbnail" alt="no image" />
  <div class="container" >
    <c:if test="${book != null}">

	<!--Book detailed page-->
        <table class="table table-hover">
          <caption><h2>Book detailed information</h2></caption>
          <thead>
            <tr>
              <th scope="raw">Book id</th>
              <th scope="raw"><c:out value="${book.id}"/></th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td>Name</td>
              <td><c:out value="${book.name}"/></td>+

            </tr>
            <tr>
              <td>Description</td>
              <td><c:out value="${book.description}"/></td>
            </tr>
            <tr>
              <td>Date of publish</td>
              <td><c:out value="${book.dateOfPublish}"/></td>
            </tr>
            <tr>
              <td>Authors:</td>
              <td>
                <c:forEach var="author" items="${book.authors}">
                   <a href="/library/authors?id=<c:out value='${author.key}' />">
                      <c:out value="${author.value}"/>
                   </a>
                   <span>, </span>
                 </c:forEach>
              </td>
            </tr>
          </tbody>
        </table>
     </c:if>
  </div>
</div>
</div>
<div style= "visibility: hidden; height: 180px;" >
</div>
<c:import url="footer.jsp" />

</body>
</html
