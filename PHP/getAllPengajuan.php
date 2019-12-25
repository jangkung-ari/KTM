<?php
require_once('dbConnect.php');

$sql ="SELECT * FROM pengajuan WHERE active='Active';";

$r = mysqli_query($con,$sql);

$result = array();

while($row = mysqli_fetch_array($r)){

	array_push($result,array(
		"id"=>$row['id'],
		"nama"=>$row['nama'],
		"status"=>$row['status'],
		"nrp"=>$row['nrp'],
		"prodi"=>$row['prodi'],
		"kota"=>$row['kota'],
		"tanggal"=>$row['tanggal'],
		"gender"=>$row['gender'],
		"validasi"=>$row['validasi'],));

}

echo json_encode(array('result'=>$result));

mysqli_close($con);