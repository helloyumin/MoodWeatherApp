<?php  

//@$link=mysqli_connect("localhost","root","test1234", "test" );  
//if (!$link)  
//{  
//    echo "MySQL 접속 에러 : ";
//    echo mysqli_connect_error();
//    exit();  
//}

require "conn.php";  

@mysqli_set_charset($conn,"utf8"); 

//$id=$_POST[userId];
$id = $_GET['id'];

//$sql = "select Score, music 
//from userscore, musictable 
//where if((select score from userscore where email2 = '$id' AND Date=date_sub(curdate(), INTERVAL 1 day)), email2 = '$id' AND date = date_sub(curdate(), INTERVAL 1 day), email2 = 'no_score') AND (case (select weather from userscore where email2 = '$id' AND date = curdate()) when '1' then num = '1' when '2' then num = '2' when '3' then num = '3' when '4' then num = '4' END) order by rand() limit 5";

$sql = "select Score, music, Video
from userscore, mtfixed 
where if((select score from userscore where email2 = '$id' AND Date=date_sub(curdate(), INTERVAL 1 day)), email2 = '$id' AND date = date_sub(curdate(), INTERVAL 1 day), email2 = 'no_score') AND (case (select weather from userscore where email2 = '$id' AND date = curdate()) when '1' then num = '1' when '2' then num = '2' when '3' then num = '3' when '4' then num = '4' END) order by rand() limit 5";

@$result=mysqli_query($conn,$sql);
$data = array();   
if($result){  
    
    while($row=mysqli_fetch_array($result)){
        array_push($data, 
            array('Score'=>$row[0],
            'Music'=>$row[1],
			'Video'=>$row[2]
        ));
    }

    header('Content-Type: application/json; charset=utf8');
	$json = json_encode(array("hello"=>$data), JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
	echo $json;

}  
else{  
	echo "SQL문 처리중 에러 발생 : " . mysqli_error($conn); 
} 



@mysqli_close($conn);  
   
?>