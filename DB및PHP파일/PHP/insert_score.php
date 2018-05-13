<?php
	require "conn.php";
	
	$sql = "INSERT INTO userscore (Email2, Date, Score, Weather) VALUES 
	('$_POST[email]', now(), '$_POST[score]', '$_POST[weather]')";
	
	@$result = mysqli_query($conn, $sql);
	
	if($result)
	{
		//echo "1 record added";
		print(1);
	}
	else {
		//print(0);
		@die('Error: ' . mysqli_error($conn));
	}
	
	mysqli_close($conn);
?>