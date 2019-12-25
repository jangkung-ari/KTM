<?php
if($_SERVER['REQUEST_METHOD']=='POST'){
//Getting values
$username = $_POST['username'];

//importing database connection script
require_once('dbConnect.php'); 


//Creating sql query
$sql = "UPDATE pengajuan SET active = 'Inactive' WHERE nrp='$username' AND active='Active'";

//Update database table
if(mysqli_query($con,$sql)){
	echo 'Employee Updated Successfully';
}else{
	echo 'Gagal KAK';
}

//closing connection
mysqli_close($con);
}