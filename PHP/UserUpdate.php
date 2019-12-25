<?php
if($_SERVER['REQUEST_METHOD']=='POST'){
//Getting values
$username = $_POST['username'];
$name = $_POST['nama'];
$kota = $_POST['city'];
$tanggal = $_POST['tanggal'];
$gender = $_POST['gender'];
$hape = $_POST['hape'];
$email = $_POST['email'];

//importing database connection script
require_once('dbConnect.php'); 


//Creating sql query
$sql = "UPDATE user SET nama = '$name', tempat_lahir = '$kota', tanggal_lahir = '$tanggal', gender='$gender', hp='$hape', email='$email' WHERE username='$username'";

//Update database table
if(mysqli_query($con,$sql)){
	echo 'Employee Updated Successfully';
}else{
	echo 'Gagal KAK';
}

//closing connection
mysqli_close($con);
}
