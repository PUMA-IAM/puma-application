<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<jsp:include page="includes/header.jsp" />

<div class="jumbotron">
	<div class="container">
		<h1>Hello, welcome at eDocs Inc.!</h1>
		<p>eDocs Inc provides services to companies that gravely
					simplify your document management.</p>
		<p>
		<c:choose>
					<c:when test="${empty user_name}">
						<a class="btn btn-primary btn-lg" role="button" href="<c:url value="/user/login"/>">Log in &raquo;</a>
					</c:when>
					<c:otherwise>
						<!-- NOTHING -->
					</c:otherwise>
				</c:choose>
		</p>
	</div>
</div>

<div class="container">
	<!-- Example row of columns -->
	<div class="row">
		<div class="col-md-4">
			<h2>Upload</h2>
			<p>Your documents can be uploaded to the document management application. 
			eDocs Inc. provides a back-up service for its tenants. 
			We have different service package offerings.</p>
			<p>
				<a class="btn btn-default" href="#" role="button">View details
					&raquo;</a>
			</p>
		</div>
		<div class="col-md-4">
			<h2>Manage</h2>
			<p>The eDocs document management platform supports easy management for the 
			documents that in the system. We provide functionality that enables you to
			manage your documents as you want to.</p>
			<p>
				<a class="btn btn-default" href="#" role="button">View details
					&raquo;</a>
			</p>
		</div>
		<div class="col-md-4">
			<h2>Send</h2>
			<p>Share your documents with other organizations. The eDocs document management
			platform allows you to send the documents that you uploaded to other organizations,
			without necessarily knowing the precise person that you want to send to. </p>
			<p>
				<a class="btn btn-default" href="#" role="button">View details
					&raquo;</a>
			</p>
		</div>
	</div>

</div>

<jsp:include page="includes/footer.jsp" />
