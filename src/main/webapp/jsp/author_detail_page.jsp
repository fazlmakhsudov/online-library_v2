<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Author detail page</title>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
  <style>
    img {
        padding-left: 50px;
    }
  </style>
</head>
<body>
<c:import url="header.jsp" />
   <div class="container">
    <div class="media">
      <h2>Author detailed information</h2>
      <div class="media-left">
        <img src="/online-library/img/author2.jpeg" class="img-rounded" alt="no image" />
      </div>
      <div class="media-body" >
        <c:if test="${author != null}">
            <table class="table table-hover">
              <thead class="media-heading">
                <tr>
                  <th>Author id</th>
                  <th><c:out value="${author.id}"/></th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td>Name</td>
                  <td><c:out value="${author.name}"/></td>
                </tr>
                <tr>
                  <td>Second name</td>
                  <td><c:out value="${author.secondName}"/></td>
                </tr>
                <tr>
                  <td>Date of birth</td>
                  <td><c:out value="${author.dateOfBirth}"/></td>
                </tr>
                <tr>
                  <td>Books</td>
                  <td>
                    <c:forEach var="book" items="${author.books}">
                       <a href="/online-library/books?id=<c:out value='${book.key}' />">
                          <c:out value="${book.value}"/>
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
</div>
<div style= "visibility: hidden; height: 180px;" >
</div>
<c:import url="footer.jsp" />
</body>
</html
