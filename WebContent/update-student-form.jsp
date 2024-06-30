<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Update Student Form</title>
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
    <div class="container mt-5">
        <h2 class="mb-4">Update Student</h2>
        <form action="StudentControllerServlet" method="GET">
            <input type="hidden" name="command" value="UPDATE"/>
            <input type="hidden" name="studentId" value="${THE_STUDENT.id}"/>
            <div class="form-group row">
                <label for="first_name" class="col-sm-2 col-form-label">First Name:</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" id="first_name" name="first_name" value="${THE_STUDENT.first_name}" >
                </div>
            </div>
            <div class="form-group row">
                <label for="last_name" class="col-sm-2 col-form-label">Last Name:</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" id="last_name" name="last_name" value="${THE_STUDENT.last_name}" >
                </div>
            </div>
            <div class="form-group row">
                <label for="email" class="col-sm-2 col-form-label">Email:</label>
                <div class="col-sm-10">
                    <input type="email" class="form-control" id="email" name="email" value="${THE_STUDENT.email}" >
                </div>
            </div>
            <div class="form-group row">
                <label for="pin" class="col-sm-2 col-form-label">Pin:</label>
                <div class="col-sm-10">
                    <input type="number" class="form-control" id="pin" name="pin" value="${THE_STUDENT.pin}" >
                </div>
            </div>
            <div class="form-group row">
                <div class="col-sm-10 offset-sm-2">
                    <button type="submit" class="btn btn-primary">Save</button>
                </div>
            </div>
        </form>
        <p><a href="StudentControllerServlet?command=LIST" class="btn btn-secondary mt-3">Back to List</a></p>
    </div>
    <!-- Bootstrap JS and dependencies -->
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
