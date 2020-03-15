<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <title>Search page</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
</head>
<body>
<c:import url="header.jsp" />
<div class="container">
  <h2>Type name of searched one below in the form</h2>
  <form action="/online-library/search" method = "POST">
     <div class="form-group">
        <label for="search">Name</label>
        <input class="form-control input-lg" id="search" name="name"
            minlength=3 type="text" placeholder = "type name of searched one strait here ... " />
      </div>
      <div class="container">
        <button type="submit" class="btn btn-info btn-lg">Search</button>
      </div>
  </form>
  <c:if test="${nothing_found != null}">
     <h2 class="text-center"><c:out value="${nothing_found}"/></h2>
  </c:if>
</div>
<div style= "visibility: hidden; height: 300px;" >
</div>
<c:import url="footer.jsp" />
</body>
</html>