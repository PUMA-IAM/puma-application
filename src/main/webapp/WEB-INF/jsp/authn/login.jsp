<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<jsp:include page="../includes/header.jsp" />

<div class="jumbotron">
	<div class="container">
		<h1>Hello, welcome at eDocs Inc.!</h1>
		<p>eDocs Inc provides services to companies that gravely
					simplify your document management.</p>
		<p>
			<a class="btn btn-primary btn-lg" role="button" href="/login">Log in &raquo;</a>
		</p>
	</div>
</div>

<div class="container">

	<div class="row">  
		<div class="col-md-12">  
      <h1 class="page-header">Login</h1> 
    </div>
	</div>

  <form class="form-signin">
    <input type="text" class="form-control" placeholder="Email address" required autofocus>
    <input type="password" class="form-control" placeholder="Password" required>
    <button class="btn btn-lg btn-primary btn-block" type="submit">Log in</button>
  </form>
</div>

<jsp:include page="../includes/footer.jsp" />
