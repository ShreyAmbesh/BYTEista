<?php
ini_set('display_errors', 1);
ini_set('display_startup_errors', 1);
error_reporting(E_ALL);
echo "yupppaaa";
$target_dir = "uploads/";
$jabberId=$_POST["jabberId"];
$target_file = $target_dir .$jabberId."-".date("Y-m-d-h-i-sa").".JPG";
$uploadOk = 1;
$imageFileType = pathinfo(basename($_FILES["fileToUpload"]["name"]),PATHINFO_EXTENSION);
require_once('db_connect.php');
require 'vendor/autoload.php';

use Google\Cloud\Storage\StorageClient;
use Google\Cloud\Core\ServiceBuilder;
// Check if image file is a actual image or fake image
if(isset($_POST["submit"])) {
    $check = getimagesize($_FILES["fileToUpload"]["tmp_name"]);
    if($check != false) {
        echo "File is an image - " . $check["mime"] . ".";
        $uploadOk = 1;
    } else {
        echo "File is not an image.";
        $uploadOk = 0;
    }
}
// Check if file already exists
if (file_exists($target_file)) {
    echo "Sorry, file already exists.";
    $uploadOk = 0;
}
// Check file size
//if ($_FILES["fileToUpload"]["size"] > 500000) {
//    echo "Sorry, your file is too large.";
//    $uploadOk = 0;
//}
// Allow certain file formats
if($imageFileType != "jpg" && $imageFileType != "png" && $imageFileType != "jpeg"
	 && $imageFileType != "gif" && $imageFileType != "JPG" && $imageFileType != "PNG" && $imageFileType != "JPEG"
&& $imageFileType != "GIF" ) {
    echo "Sorry, only JPG, JPEG, PNG & GIF files are allowed.";
    $uploadOk = 0;
}
// Check if $uploadOk is set to 0 by an error
if ($uploadOk == 0) {
    echo "Sorry, your file was not uploaded.";
// if everything is ok, try to upload file
} else {

$gcloud = new ServiceBuilder([
    'keyFilePath' => 'MyFirstProject-d9e28d6a2d3d.json',
    'projectId' => 'leafy-stock-184410'
]);

$storage = $gcloud->storage();

$bucket = $storage->bucket('bucket_cortivia');

$options = [
    'resumable' => true,
    'name' => $target_file
];

// Upload a file to the bucket.
if($bucket->upload(
    fopen($_FILES["fileToUpload"]["tmp_name"], 'r+'),
$options
)){

   // if (move_uploaded_file($_FILES["fileToUpload"]["tmp_name"],$target_file)) {
        $query = "SELECT profile_pic FROM byteista_users WHERE jabberId='".$jabberId."'";
        $result = pg_query($con,$query);
        $del=pg_fetch_assoc($result);
        if (!is_null($del["profile_pic"])) {
            //unlink($del["profile_pic"]);
            $object = $bucket->object($del["profile_pic"]);
      if($object->exists())
        $object->delete();
        }

         $sql = "UPDATE byteista_users SET profile_pic='".$target_file."' WHERE phone='".$jabberId."'";
         if(pg_query($con,$sql)){
        echo "The file ". basename( $_FILES["fileToUpload"]["name"]). " has been uploaded.";}
    } else {
        echo "Sorry, there was an error uploading your file.";
    }
}
pg_close($con);
?>