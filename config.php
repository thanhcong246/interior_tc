<?php
    $host = "localhost";
    $username = "root";
    $password = "";
    $db = "interior_tc";

//    $host = "sql202.infinityfree.com";
//    $username = "if0_34529479";
//    $password = "";
//    $db = "if0_34529479_interior_tc";

    $con = @mysqli_connect($host, $username, $password, $db) or dir(mysqli_error);
//    if ($con->connect_error) {
//        echo "failed";
//    } else {
//        echo "success";
//    }
?>