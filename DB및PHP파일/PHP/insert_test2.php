<?php

	require "conn.php";
	
	$id = $_POST['email'];
	//$pwd = md5($_POST['pwd']);
	$pwd = $_POST['pwd'];
	$name = $_POST['name'];
	$phone = $_POST['phone'];
	$salt = '@adf$sQ{dsfdksd';
	$password = hash('sha256', $salt.$pwd);
	
	
	@$sql = "INSERT INTO users (Email, Password, Name, Phone)
	VALUES ('$id', '$password', '$name', '$phone')";
	
	@$result = mysqli_query($conn, $sql);
	
		if($result)
		{
			echo "1 record added"; 
		}
		else {
			@die('Error: ' . mysqli_error($conn));
		} 
	
	mysqli_close($conn);

?>