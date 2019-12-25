<?php

$nrp = $_GET['nrp'];

require_once('dbConnect.php');

$sql = "SELECT * FROM pengajuan WHERE active='Active' AND nrp = $nrp";

$r = mysqli_query($con,$sql);

$result = array();
$row = mysqli_fetch_array($r);
array_push($result,array(
	// "nrp"=>$row['nrp'],
	"validasi"=>$row['validasi']));
// var_dump($row);
echo json_encode(array('result'=>$result));

mysqli_close($con);