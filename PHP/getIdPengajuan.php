<?php

$id = $_GET['id'];

require_once('dbConnect.php');

$sql = "SELECT * FROM pengajuan WHERE id=$id";

$r= mysqli_query($con,$sql);

$result = array();
$row = mysqli_fetch_array($r);
array_push($result,array(
	"id"=>$row['id'],
	"nrp"=>$row['nrp'],
	"nama"=>$row['nama'],
	"prodi"=>$row['prodi'],
	"kota"=>$row['kota'],
	"tanggal"=>$row['tanggal'],
	"gender"=>$row['gender'],
	"status"=>$row['status'],
	"validasi"=>$row['validasi'],));

echo json_encode(array('result'=>$result));

mysqli_close($con);
