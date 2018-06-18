<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="description" content="">
        <meta name="author" content="Juan Carlos Hernández">
        <title>Exam Application System</title>
        <link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
        <link href="vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
        <link href="css/sb-admin.css" rel="stylesheet">
    </head>

    <body class="bg-dark">
        <div class="container">
            <div class="card card-register mx-auto mt-5">
                <div class="card-header">Register an Account</div>
                <div class="card-body">
                    <form action="servletLogin" method="post">
                        <div class="form-group">
                            <div class="form-row">
                                <div class="col-md-4">
                                    <label for="firstname">First name</label>
                                    <input class="form-control" id="firstname" name="firstname" type="text" aria-describedby="nameHelp" placeholder="Enter first name" required>
                                </div>
                                <div class="col-md-4">
                                    <label for="pSurname">Paternal surname</label>
                                    <input class="form-control" id="pSurname" name="psurname" type="text" aria-describedby="nameHelp" placeholder="Enter paternal surname" required>
                                </div>
                                <div class="col-md-4">
                                    <label for="mSurname">Maternal surname</label>
                                    <input class="form-control" id="mSurname" name="msurname" type="text" aria-describedby="nameHelp" placeholder="Enter maternal surname">
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="exampleInputEmail1">Email address</label>
                            <input class="form-control" id="exampleInputEmail1" name="email" type="email" aria-describedby="emailHelp" placeholder="Enter email" required>
                        </div>
                        <div class="form-group">
                            <div class="form-row">
                                <div class="col-md-6">
                                    <label for="Password">Password</label>
                                    <input class="form-control" id="Password" name="password" type="password" placeholder="Password" required>
                                </div>
                                <div class="col-md-6">
                                    <label for="ConfirmPassword">Confirm password</label>
                                    <input class="form-control" id="ConfirmPassword" name="confirmpassword" type="password" placeholder="Confirm password" required>
                                </div>
                            </div>
                        </div>
                        <input type="submit" class="btn btn-primary btn-block" id="btnRegister" name="btnRegister" value="Register" disabled>
                        <input type="hidden" name="action" value="register">
                        <span id='message'></span>
                    </form>
                    <div class="text-center">
                        <a class="d-block small mt-3" href="login.jsp">Login Page</a>
                        <a class="d-block small" href="forgot-password.jsp">Forgot Password?</a>
                    </div>
                </div>
            </div>
        </div>
        <!-- Bootstrap core JavaScript-->
        <script src="vendor/jquery/jquery.min.js"></script>
        <script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
        <!-- Core plugin JavaScript-->
        <script src="vendor/jquery-easing/jquery.easing.min.js"></script>
        <script>
            $('#Password, #ConfirmPassword').keyup(function(){
                var pass    =   $('#Password').val();
                var cpass   =   $('#ConfirmPassword').val();
                if(pass!=cpass){
                    document.getElementById('btnRegister').disabled = true;
                    document.getElementById('message').innerHTML = "The field password and confirm password not match";
                }else{
                    document.getElementById('btnRegister').disabled = false;
                    document.getElementById('message').innerHTML = "";
                }
            });
        </script>
    </body>

</html>
