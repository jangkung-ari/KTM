<?php
include 'koneksi.php';



// Create connection
// $conn = new mysqli($HostName, $HostUser, $HostPass, $DatabaseName);

// if ($conn->connect_error) {
 
//  die("Connection failed: " . $conn->connect_error);
// } 
		@$username = $_GET['username'];
		@$nama = $_GET['nama'];
        @$tempat_lahir = $_GET['tempat_lahir'];
        @$tanggal_lahir= $_GET['tanggal_lahir'];
        @$gender = $_GET['gender'];
        @$hp = $_GET['hp'];
        @$email = $_GET['email'];
        $query_update_biodata = mysqli_query($con, "UPDATE user SET nama='$nama', tempat_lahir='$tempat_lahir', tanggal_lahir='$tanggal_lahir', gender='$gender', hp='$hp', email='$email' WHERE username='$username'");
        if ($query_update_biodata) {
            echo "Update Data Berhasil";
        } else {
            echo mysqli_error();
        }

// $sql = "UPDATE user SET nama='$nama', tempat_lahir='$kota', tanggal_lahir='$tanggal',gender='$gender',hp='$hp',email='$email'";

// if(isset($_GET['username'])){
// 	$username = $_GET['username'];
// 	$nama = $_GET['nama'];
// 	$kota = $_GET['city'];
// 	$tanggal = $_GET['tgllahir'];
// 	$gender = $_GET['kelamin'];
// 	$hp = $_GET['hape'];
// 	$email=$_GET['email'];

// 	$sql .= " WHERE username = '".$username."'";
// }

// $result = $con->query($sql);

// if ($result->num_rows >0) {
 
 
//  while($row[] = $result->fetch_assoc()) {
 
//  $tem = $row;
 
//  $json = json_encode($tem);
 
 
//  }
 
// } else {
//  echo "No Results Found.";
// }
//  echo $json;
$con->close();
// mysqli_close($con);
?>