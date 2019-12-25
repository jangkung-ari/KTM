<?php
if($_SERVER['REQUEST_METHOD']=='POST'){
//Getting values
$id = $_POST['id'];
$validasi = $_POST['validasi'];


//importing database connection script
require_once('dbConnect.php'); 


//Creating sql query
$sql = "UPDATE pengajuan SET validasi = '$validasi'WHERE id='$id'";

//Update database table
if(mysqli_query($con,$sql)){
	echo 'Employee Updated Successfully';
}else{
	echo 'Gagal KAK';
}

//closing connection
mysqli_close($con);
}
