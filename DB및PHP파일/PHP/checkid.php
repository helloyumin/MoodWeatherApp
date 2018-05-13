<?php

require "conn.php";

$id = $_GET['id'] ;
$sql="SELECT * FROM users WHERE Email='$id'";

//$sql="SELECT * FROM users WHERE Email='haku@gmail.com'";
@$result=mysqli_query($conn,$sql);
@$rowcount=mysqli_num_rows($result);

if ($rowcount)
  {
	//echo "중복된 아이디";  
	print($rowcount);
	//printf("Result set has %d rows.\n",$rowcount);
  } else {
	//echo "아이디 사용 가능"; 
	print($rowcount);	
	//printf("Result set has %d rows.\n",$rowcount);
	// Free result set
	//mysqli_free_result($result);  
  }

@mysqli_close($con);
?>