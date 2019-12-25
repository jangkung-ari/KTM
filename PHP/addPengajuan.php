<?php
if($_SERVER['REQUEST_METHOD']=='POST'){

	//Getting values
	$nrp = $_POST['username'];
	$nama = $_POST['nama'];
	$prodi = $_POST['programstudi'];
	$kota = $_POST['city'];
	$tanggal = $_POST['tanggal'];
	$gender = $_POST['gender'];
	$status = $_POST['status'];
	$validasi = $_POST['validasi'];
	$active = $_POST['active'];


	//Creating an query sql query
	$sql = "INSERT INTO pengajuan (nrp,nama,prodi,kota,tanggal,gender,status,validasi,active) VALUES ('$nrp','$nama','$prodi','$kota','$tanggal','$gender','$status','$validasi','$active')";

	//Importing our db connection script
	require_once('dbConnect.php');

	//Excute query
	if(mysqli_query($con,$sql)){
		echo'Sukses';
	}else{
		echo 'Gagal';
	}

//Closing the database
mysqli_close($con);
}