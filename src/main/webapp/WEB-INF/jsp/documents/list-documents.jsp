<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<jsp:include page="../includes/header.jsp" />

<div class="container">

	<div class="row">
		<div class="col-md-12">
			<h1 class="page-header">Your documents</h1>
		</div>
	</div>

	<div class="row">
		<div class="col-md-12">

			<h2 class="">Received</h2>

			<table class="table table-hover">
				<thead>
					<tr>
						<th>Date</th>
						<th>Title</th>
						<th>From</th>
						<th></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="doc" items="${receivedDocuments}" varStatus="status">
						<tr>
							<td><c:out value="${doc.date}" /></td>
							<td><c:out value="${doc.name}" /></td>
							<td><c:out value="${doc.origin}" /></td>
							<td><a class="btn btn-danger btn-sm"
								href="<c:url value="/docs/${doc.id}/delete"/>"><span
									class="glyphicon glyphicon-remove"></span> Delete</a> <a
								class="btn btn-primary btn-sm" href="<c:url value="/docs/${doc.id}"/>"><span
									class="glyphicon glyphicon-chevron-right"></span> View</a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>

	<div class="row">
		<div class="col-md-12">

			<h2 class="">Sent</h2>

			<table class="table table-hover">
				<thead>
					<tr>
						<th>Date</th>
						<th>Title</th>
						<th>To</th>
						<th></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="doc" items="${sentDocuments}" varStatus="status">
						<tr>
							<td><c:out value="${doc.date}" /></td>
							<td><c:out value="${doc.name}" /></td>
							<td><c:out value="${doc.destination}" /></td>
							<td><a class="btn btn-danger btn-sm"
								href="<c:url value="/docs/${doc.id}/delete"/>"><span
									class="glyphicon glyphicon-remove"></span> Delete</a> <a
								class="btn btn-primary btn-sm" href="<c:url value="/docs/${doc.id}"/>"><span
									class="glyphicon glyphicon-chevron-right"></span> View</a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>

</div>

<jsp:include page="../includes/footer.jsp" />
