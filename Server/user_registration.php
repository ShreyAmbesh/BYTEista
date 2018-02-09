<?php
ini_set('display_errors', 1);
ini_set('display_startup_errors', 1);
error_reporting(E_ALL);

if($_SERVER['REQUEST_METHOD']=='POST'){
require_once('db_connect.php');

$sql_if_available="SELECT * FROM events ";
 $result = pg_query($con,$sql_if_available);
 $events = array();
if(pg_num_rows($result)>0){
	while($s=pg_fetch_assoc($result))
    {
    array_push($events, $s)
}
    echo json_encode($events);
 }
 else
 {
echo "yuppp";
pg_close($con);
}
 }else{
echo 'error';
}



?>