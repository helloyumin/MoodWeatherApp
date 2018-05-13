<?php  

require "conn.php"; 

@mysqli_set_charset($conn,"utf8"); 

$id=$_GET['id'];
$sql = "select Score, Word from userscore, wordtable where Email2='$id' and Date=curdate() order by rand() limit 1";

@$result=mysqli_query($conn,$sql);
$data = array();   
if($result){  
    
    while($row=mysqli_fetch_array($result)){
        array_push($data, 
            array(//'Email2'=>$row[0],
			'Score'=>$row[0],
            'Word'=>$row[1]
        ));
    }
	
    header('Content-Type: application/json; charset=utf8');
	$json = json_encode(array("hello"=>$data), JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
	echo $json;

}  
else{  
    echo "SQL문 처리중 에러 발생 : " . mysqli_error($conn); 
} 


 
@mysqli_close($link);  
   
?>