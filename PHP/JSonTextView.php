<?php
include 'koneksi.php';

// Create connection
// $conn = new mysqli($HostName, $HostUser, $HostPass, $DatabaseName);

// if ($conn->connect_error) {
 
//  die("Connection failed: " . $conn->connect_error);
// } 



$sql = "SELECT * FROM user ";

if(isset($_GET['username'])){
	$username = $_GET['username'];
	$sql .= " WHERE username = '".$username."'";
}

$result = $con->query($sql);

if ($result->num_rows >0) {
 
 
 while($row[] = $result->fetch_assoc()) {
 
 $tem = $row;
 
 $json = json_encode($tem);
 
 
 }
 
} else {
 echo "No Results Found.";
}
 echo $json;
$con->close();
?>