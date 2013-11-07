<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<jsp:include page="../includes/header.jsp" />

<div class="container">

	<div class="row">  
		<div class="col-md-12">  
      <h1 class="page-header">Document: Minutes of the meeting of last week</h1> 
    </div>
	</div>

	<div class="row">

		<div class="col-md-8">
			<iframe src="<c:url value="/resources/files/doc.pdf"/>" class="document"></iframe>
		</div>

		<div class="col-md-3">
			<h4>Name</h4>
			<p><c:out value="${doc.name}" /></p>
			<h4>File name</h4>
			<p>doc.pdf</p>
			<h4>Date</h4>
			<p><c:out value="${doc.date}" /></p>
			<h4>From</h4>
			<p><c:out value="${doc.origin}" /></p>
			<h4>To</h4>
			<p><c:out value="${doc.destination}" /></p>
			<p >
				<a class="btn btn-danger full-width" href="<c:url value="/docs/${doc.id}/delete"/>"><span class="glyphicon glyphicon-remove"></span> Delete</a>
      </p>
		</div>
	</div>
</div>

<jsp:include page="../includes/footer.jsp" />
