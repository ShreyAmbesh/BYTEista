<?php
ini_set('display_errors', 1);
ini_set('display_startup_errors', 1);
error_reporting(E_ALL);

if($_SERVER['REQUEST_METHOD']=='POST'){
 $registrationdatajson = $_POST['registrationdata'];
require_once('db_connect.php');
$registrationdata=json_decode($registrationdatajson);

$sql_if_available="SELECT name,dob,usn,semester,phone,branch,interests,gender,hostelite FROM byteista_users WHERE phone='".$registrationdata->PHONENUMBER."'";
 $result = pg_query($con,$sql_if_available);
if(pg_num_rows($result)==1){
    $s = pg_fetch_assoc($result);
    echo json_encode($s);
 }
 else
 {

pg_close($con);
}
 }else{
echo 'error';
}



?>