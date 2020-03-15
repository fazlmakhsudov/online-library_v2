<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
    <head>
        <title>
          <c:if test="${book != null}">
                <c:out value="Book detail"/>
            </c:if>
              <c:if test="${author != null}">
                    <c:out value="Author detail"/>
               </c:if>
           <c:if test="${param.search != null}">
                <c:out value="Search page"/>
            </c:if>
            Online library
        </title>
      <link href="css/style.css" type="text/css" rel="stylesheet">
    </head>
    <body>
        <header>
        <div class="header"><p id="header_text"><c:if test="${book != null}"><c:out value="Book detail for ${book.name}"/>
        </c:if><c:if test="${author != null}"><c:out value="Author detail for ${author.name}"/>
        </c:if><c:if test="${param.search != null}"><c:out value="Search book ${param.search_name}"/></c:if></p></div>
        </header>
        <nav>
            <div><a href="book-controller?action=main">Main</a></div>
            <div><a href="book-controller?action=main">Books</a></div>
             <div><a href="author-controller?action=list_authors">Authors</a></div>
             <div><a href="/online-library?search=show">Search</a></div>
        </nav>
        <div id="books_">
          <c:forEach var="book" items="${list_books}">
               <div class="book">
                <img src="img/book2.jpeg" alt="no picture"/>
                  <a href="book-controller?action=bookDetail&book_id=<c:out value='${book.id}' />">
                    <h1><c:out value="${book.name}"/></h1>
                  </a>
               </div>
           </c:forEach>
                <c:forEach var="author" items="${list_authors}">
                       <div id="author_style">
                       <img src="img/author2.jpeg" alt="no picture"/>
                        <a href="author-controller?action=authorDetail&author_id=<c:out value='${author.id}' />">
                             <h1><c:out value="${author.name}"/></h1>
                        </a>
                     </div>
                 </c:forEach>
          <div id="book_detail">
                <c:if test="${book != null}">
                        <img src="img/book.jpeg" alt="no image"/>
                        <article>
                               <h2>Book id:  <c:out value="${book.id}"/></h3>
                               <h2>Name:  <c:out value="${book.name}"/></h4>
                                <h2>Description:  <c:out value="${book.description}"/></h4>
                                <h2>Date of publish: <c:out value="${book.dateOfPublish}"/></h4>
                                <h2> Authors:
                                    <c:forEach var="author_name" items="${book_authors_name}">
                                         <a href="author-controller?action=search&search_name=<c:out value='${author_name}' />">
                                            <c:out value="${author_name}"/>
                                        </a>
                                        <span>, </span>
                                    </c:forEach>
                                </h2>
                        </article>
                 </c:if>
            </div>
            <div id="search_div">
                <c:if test="${param.search != null}">
                    <form action="search-controller">
                        <section class="form_section">
                           <label for="search">Type your name to search: </label>
                           <input type="search" id="search" name="search_name"
                                                   minlength=4 placeholder="Search book or author by name"/>
                        </section>
                        <section class="form_section">
                            <select name="entity" id="select">
                                <option value="book">Book</option>
                                <option value="author">Author</option>
                             </select>
                        </section>
                        <section class="form_section">
                            <input type="submit" id="submit" value="Search ..."/>
                        </section>
                    </form>
                </c:if>
            </div>
            <div id="author_detail">
                            <c:if test="${author != null}">
                                    <img src="img/author.jpeg" alt="no image"/>
                                    <article>
                                           <h2>Author id:  <c:out value="${author.id}"/></h3>
                                           <h2>Name:  <c:out value="${author.name}"/></h4>
                                            <h2>Second name:  <c:out value="${author.secondName}"/></h4>
                                            <h2>Date of birth: <c:out value="${author.dateOfBirth}"/></h4>
                                            <h2> Books:
                                                <c:forEach var="book_name" items="${author_books_name}">
                                                    <a href="book-controller?action=search&search_name=<c:out value='${book_name}' />">
                                                        <c:out value="${book_name}"/>
                                                    </a>
                                                    <span>, </span>
                                                </c:forEach>
                                            </h2>
                                     </article>
                             </c:if>
               </div>

        </div>
                      <footer>
                           <h1>Nothing
                           </h1>
                           <script src="js/script.js"></script>
                       </footer>
    </body>
</html>