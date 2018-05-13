<?php 
	require "conn.php";
	//error_log($_POST['id']);
	$id = $_GET['id'];
	$pwd = $_GET['pw'];
	$salt = '@adf$sQ{dsfdksd';
	$pwd2 = hash('sha256', $salt.$pwd);
	
//	@$r=mysqli_query($conn, "select Email, Password, Name, flag from users, flagtable
	//where Email='$id' AND password = '$pwd2' AND if((select Score from userscore where Email2 = Email AND Date = curdate()), flag =1, flag = 0)");
	
		@$r=mysqli_query($conn, "select Email, if((select password from users where email = '$id') = '$pwd2', '$pwd', 'invalid') as Password, Name, flag from users, flagtable
		where email = '$id' AND if((select Score from userscore where Email2 = Email AND Date = curdate()), flag =1, flag = 0)");

	@$row=mysqli_fetch_array($r);

	print(json_encode($row));
	@mysqli_close($conn);
?>