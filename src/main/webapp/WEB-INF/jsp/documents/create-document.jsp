<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<jsp:include page="../includes/header.jsp" />

<div class="container">

	<div class="row">
		<div class="col-md-12">
			<h1 class="page-header">Create document</h1>
		</div>
	</div>

	<div class="row">
		<div class="col-md-12">
			<form class="form-horizontal" role="form" method="post" action="<c:url value="/docs/create-impl"/>">
				<div class="form-group">
					<label for="inputEmail3" class="col-sm-2 control-label">Name</label>
					<div class="col-sm-10">
						<input name="name" class="form-control" id="input-name"
							placeholder="Name">
					</div>
				</div>
				<div class="form-group">
					<label for="inputEmail3" class="col-sm-2 control-label">To</label>
					<div class="col-sm-10">
						<input name="destination" class="form-control" id="input-to"
							placeholder="Email">
					</div>
				</div>
				<div class="form-group">
					<label for="exampleInputFile" class="col-sm-2 control-label">File
						input</label>
					<div class="col-sm-10">
						<input name="file" type="file" id="input-file">
					</div>
				</div>
				<div class="form-group">
					<div class="col-sm-offset-2 col-sm-10">
						<button type="submit" class="btn btn-default">Create</button>
					</div>
				</div>
			</form>
		</div>
	</div>
</div>

<jsp:include page="../includes/footer.jsp" />
