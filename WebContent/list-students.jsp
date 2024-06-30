<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>List of Students</title>
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
<div class="container mt-5">
    <h2 class="mb-4">List of Students</h2>
    <input type="button" class="btn btn-primary" value="Add Student" onclick="window.location.href='add-student-form.jsp';return false;"/>
    <table class="table table-striped table-bordered">
        <thead class="thead-dark">
            <tr>
                <th>First Name</th>
                <th>Last Name</th>
                <th>Email</th>
                <th>Pin</th>
                <th>Action</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="temp" items="${STUDENT_LIST}">
		               <c:url var="templink" value="StudentControllerServlet">
		    				<c:param name="command" value="LOAD"/>
		  				    <c:param name="studentId" value="${temp.id}"/>
					   </c:url>
					   <c:url var="deletelink" value="StudentControllerServlet">
		    				<c:param name="command" value="DELETE"/>
		  				    <c:param name="studentId" value="${temp.id}"/>
					   </c:url>

                <tr>
                    <td>${temp.first_name}</td>
                    <td>${temp.last_name}</td>
                    <td>${temp.email}</td>
                    <td>${temp.pin}</td>
                    <td><a href="${templink}">Update</a>|<a href="${deletelink}"
                    onclick="if(!(confirm('are you sure you want to delete?'))) return false">Delete</a></td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>
<!-- Bootstrap JS and dependencies -->
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
