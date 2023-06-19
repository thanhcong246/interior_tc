<?php
global $con;
require_once('./config.php');
mysqli_set_charset($con, 'utf8');

//this for getting the url with function
@$request = explode('/', trim($_SERVER['PATH_INFO'], '/'));
$action = preg_replace('/[^a-z0-9_]+/i', '', array_shift($request));
// end

function register()
{
    global $con;
    $name = $_POST["name"];
    $email = $_POST["email"];
    $password = $_POST["password"];

    // Kiểm tra sự tồn tại của tên hoặc email
    $check_query = "SELECT name FROM users WHERE name = '$name'";
    $check_result = mysqli_query($con, $check_query);

    // Kiểm tra sự tồn tại của email
    $check_query1 = "SELECT email FROM users WHERE email = '$email'";
    $check_result1 = mysqli_query($con, $check_query1);

    if (mysqli_num_rows($check_result) > 0) {
        // Tên đã tồn tại
        $my_result = array("errorcode" => "201", "message" => "Name already exists");
        echo json_encode($my_result);
    } elseif (mysqli_num_rows($check_result1) > 0) {
        // Email đã tồn tại
        $my_result = array("errorcode" => "202", "message" => "Email already exists");
        echo json_encode($my_result);
    } else {
        // Tạo số ngẫu nhiên 6 chữ số
        $login_info = rand(100000, 999999);

        // Mã hóa mật khẩu
        $hashed_password = password_hash($password, PASSWORD_DEFAULT);

        // Thực hiện câu truy vấn INSERT
        $insert_query = "INSERT INTO users (name, email, password, login_info) VALUES ('$name', '$email', '$hashed_password', '$login_info')";
        $insert_result = mysqli_query($con, $insert_query);

        if ($insert_result) {
            // Thêm thành công
            $my_result = array("errorcode" => "000", "message" => "success");
            echo json_encode($my_result);
        } else {
            // Lỗi khi thêm
            $my_result = array("errorcode" => "111", "message" => "fail");
            echo json_encode($my_result);
        }
    }
}


function login()
{
    global $con;
    $email = $_POST["email"];
    $password = $_POST["password"];

    $my_query = "SELECT name, email, password FROM users WHERE email = '$email'";
    $result = mysqli_query($con, $my_query);
    $response = array();

    if (mysqli_num_rows($result) > 0) {
        $row = mysqli_fetch_assoc($result);
        $hashed_password = $row['password'];

        // So sánh mật khẩu đã nhập với mật khẩu đã mã hóa trong cơ sở dữ liệu
        if (password_verify($password, $hashed_password)) {
            // Mật khẩu khớp
            $row["error_login"] = "000"; // Thêm trường "error_login" với giá trị "000"
            $response = $row;
            echo json_encode($response);
        } else {
            // Mật khẩu không khớp
            $arr = array("error_login" => "101", "message" => "Password didn't match");
            echo json_encode($arr);
        }
    } else {
        $arr = array("error_login" => "111", "message" => "Email not found");
        echo json_encode($arr);
    }
}


function recover_send_mail()
{
    global $con;
    $email = $_POST["email"];
    $my_query = "SELECT email, login_info FROM users WHERE email = '$email'";
    $result = mysqli_query($con, $my_query);
    $response = array();

    if (mysqli_num_rows($result) > 0) {
        $row = mysqli_fetch_assoc($result);
        $row["error_recover_email"] = "000"; // Thêm trường "error_login" với giá trị "000"
        $response = $row;
        echo json_encode($response);
    } else {
        $arr = array("error_recover_email" => "111", "message" => "Email not found");
        echo json_encode($arr);
    }
}

function recover_send_mail_code()
{
    global $con;
    $email = $_POST["email"];
    $login_info = $_POST["login_info"];
    $my_query = "SELECT email, login_info FROM users WHERE email = '$email'";
    $result = mysqli_query($con, $my_query);
    $response = array();

    if (mysqli_num_rows($result) > 0) {
        $row = mysqli_fetch_assoc($result);

        if ($row['login_info'] === $login_info) {
            $new_login_info = rand(100000, 999999);
            $update_query = "UPDATE users SET login_info = '$new_login_info' WHERE email = '$email'";
            $update_result = mysqli_query($con, $update_query);
            if ($update_result) {
                $row["error_recover_email_code"] = "000";
                $response = $row;
                echo json_encode($response);
            } else {
                $arr = array("error_recover_email_code" => "113", "message" => "Failed to update login_info");
                echo json_encode($arr);
            }
        } else {
            $arr = array("error_recover_email_code" => "112", "message" => "Incorrect login_info");
            echo json_encode($arr);
        }
    } else {
        $arr = array("error_recover_email_code" => "111", "message" => "Email not found");
        echo json_encode($arr);
    }
}


function recover_send_mail_code_to()
{
    global $con;
    $email = $_POST["email"];
    $my_query = "SELECT email, login_info FROM users WHERE email = '$email'";
    $result = mysqli_query($con, $my_query);
    $response = array();

    if (mysqli_num_rows($result) > 0) {
        $row = mysqli_fetch_assoc($result);
        $new_login_info_to = rand(100000, 999999);
        $update_query = "UPDATE users SET login_info = '$new_login_info_to' WHERE email = '$email'";
        $update_result = mysqli_query($con, $update_query);
        if ($update_result) {
            $row["error_recover_email_code_to"] = "000";
            $row["login_info"] = $new_login_info_to;
            $response = $row;
            echo json_encode($response);
        }
    } else {
        $arr = array("error_recover_email_code_to" => "111", "message" => "Email not found");
        echo json_encode($arr);
    }
}

function recover()
{
    global $con;
    $email = $_POST["email"];
    $new_password = $_POST["password"];

    $my_query = "SELECT email FROM users WHERE email = '$email'";
    $result = mysqli_query($con, $my_query);
    $response = array();

    if (mysqli_num_rows($result) > 0) {
        // Update the user's password
        $hashed_password = password_hash($new_password, PASSWORD_DEFAULT);
        $update_query = "UPDATE users SET password = '$hashed_password' WHERE email = '$email'";
        $update_result = mysqli_query($con, $update_query);

        if ($update_result) {
            $response["error_recover_password"] = "000"; // Thêm trường "error_recover_password" với giá trị "000"
            echo json_encode($response);
        } else {
            $arr = array("error_recover_password" => "114", "message" => "Failed to update password");
            echo json_encode($arr);
        }
    } else {
        $arr = array("error_recover_password" => "111", "message" => "Email not found");
        echo json_encode($arr);
    }
}

function add_category()
{
    global $con;

    $name = $_POST["name"];
    $description = $_POST["description"];

    $name = mysqli_real_escape_string($con, $name);
    $description = mysqli_real_escape_string($con, $description);

    $query = "INSERT INTO Categories (name, description) VALUES ('$name', '$description')";

    if (mysqli_query($con, $query)) {
        $response = array("errorcode" => "000", "message" => "Category added successfully");
        echo json_encode($response);
    } else {
        $response = array("errorcode" => "111", "message" => "Failed to add category");
        echo json_encode($response);
    }
}


function add_product()
{
    global $con;

    $name = $_POST["name"];
    $description = $_POST["description"];
    $price = $_POST["price"];
    $category_id = $_POST["category_id"];
    $specifications = $_POST["specifications"];

    $name = mysqli_real_escape_string($con, $name);
    $description = mysqli_real_escape_string($con, $description);
    $specifications = mysqli_real_escape_string($con, $specifications);

    if (isset($_FILES['image'])) {
        $image_file = $_FILES['image']['tmp_name'];
        $image_name = $_FILES['image']['name'];
        $image_path = 'uploads/' . $image_name;

        // Di chuyển tệp hình ảnh vào thư mục uploads
        if (move_uploaded_file($image_file, $image_path)) {
            $image_filename = basename($image_path);

            $query = "INSERT INTO Products (name, description, image_url, price, category_id, specifications)
                      VALUES ('$name', '$description', '$image_filename', $price, $category_id, '$specifications')";

            if (mysqli_query($con, $query)) {
                $response = array("errorcode" => "000", "message" => "Product added successfully");
                echo json_encode($response);
            } else {
                $response = array("errorcode" => "111", "message" => "Failed to add product");
                echo json_encode($response);
            }
        } else {
            $response = array("errorcode" => "111", "message" => "Failed to move uploaded image");
            echo json_encode($response);
        }
    } else {
        $response = array("errorcode" => "111", "message" => "No image uploaded");
        echo json_encode($response);
    }
}

function update_product()
{
    global $con;

    $product_id = $_POST["product_id"];
    $name = $_POST["name"];
    $description = $_POST["description"];
    $price = $_POST["price"];
    $category_id = $_POST["category_id"];
    $specifications = $_POST["specifications"];

    $name = mysqli_real_escape_string($con, $name);
    $description = mysqli_real_escape_string($con, $description);
    $specifications = mysqli_real_escape_string($con, $specifications);

    if (isset($_FILES['image'])) {
        $image_file = $_FILES['image']['tmp_name'];
        $image_name = $_FILES['image']['name'];
        $image_path = 'uploads/' . $image_name;

        // Di chuyển tệp hình ảnh vào thư mục uploads
        if (move_uploaded_file($image_file, $image_path)) {
            $image_filename = basename($image_path);

            $query = "UPDATE Products SET name = '$name', description = '$description', image_url = '$image_filename', 
                      price = $price, category_id = $category_id, specifications = '$specifications'
                      WHERE product_id = $product_id";

            if (mysqli_query($con, $query)) {
                $response = array("errorcode" => "000", "message" => "Product updated successfully");
                echo json_encode($response);
            } else {
                $response = array("errorcode" => "111", "message" => "Failed to update product");
                echo json_encode($response);
            }
        } else {
            $response = array("errorcode" => "111", "message" => "Failed to move uploaded image");
            echo json_encode($response);
        }
    } else {
        $query = "UPDATE Products SET name = '$name', description = '$description', price = $price, 
                  category_id = $category_id, specifications = '$specifications'
                  WHERE product_id = $product_id";

        if (mysqli_query($con, $query)) {
            $response = array("errorcode" => "000", "message" => "Product updated successfully");
            echo json_encode($response);
        } else {
            $response = array("errorcode" => "111", "message" => "Failed to update product");
            echo json_encode($response);
        }
    }
}


function search_products()
{
    global $con;

    $search_product = $_POST["search_product"];

    // Tìm kiếm theo tên sản phẩm
    $product_query = "SELECT * FROM Products WHERE name LIKE '%$search_product%'";
    $product_result = mysqli_query($con, $product_query);
    $products = array();

    if (mysqli_num_rows($product_result) > 0) {
        while ($row = mysqli_fetch_assoc($product_result)) {
            $products[] = $row;
        }
    }

    // Tìm kiếm theo tên danh mục
    $category_query = "SELECT * FROM Categories WHERE name LIKE '%$search_product%'";
    $category_result = mysqli_query($con, $category_query);
    $category_products = array();

    if (mysqli_num_rows($category_result) > 0) {
        while ($category = mysqli_fetch_assoc($category_result)) {
            $category_id = $category['category_id'];

            // Tìm kiếm sản phẩm theo danh mục
            $category_product_query = "SELECT * FROM Products WHERE category_id = $category_id";
            $category_product_result = mysqli_query($con, $category_product_query);

            if (mysqli_num_rows($category_product_result) > 0) {
                while ($row = mysqli_fetch_assoc($category_product_result)) {
                    $category_products[] = $row;
                }
            }
        }
    }

    // Kết hợp kết quả từ tìm kiếm theo tên sản phẩm và tìm kiếm theo danh mục
    $products = array_unique(array_merge($products, $category_products), SORT_REGULAR);

    if (count($products) > 0) {
        $response = array("errorcode" => "000", "message" => "Search results found", "products" => $products);
        echo json_encode($response);
    } else {
        $response = array("errorcode" => "111", "message" => "No search results found");
        echo json_encode($response);
    }
}


function get_all_product()
{
    global $con;

    $query = "SELECT * FROM Products";
    $result = mysqli_query($con, $query);

    if (mysqli_num_rows($result) > 0) {
        $products = array();

        while ($row = mysqli_fetch_assoc($result)) {
            $product = array(
                "product_id" => $row["product_id"],
                "name" => $row["name"],
                "image_url" => $row["image_url"],
                "price" => $row["price"]
            );
            $products[] = $product;
        }

        echo json_encode($products);
    } else {
        $response = array("errorcode" => "111", "message" => "No products found");
        echo json_encode($response);
    }
}


switch ($action) {
    case "register":
        register();
        break;

    case "login":
        login();
        break;

    case "recover_send_mail":
        recover_send_mail();
        break;

    case "recover_send_mail_code":
        recover_send_mail_code();
        break;

    case "recover_send_mail_code_to":
        recover_send_mail_code_to();
        break;

    case "recover":
        recover();
        break;

    case "add_category":
        add_category();
        break;

    case "add_product":
        add_product();
        break;

    case "update_product":
        update_product();
        break;

    case "search_products":
        search_products();
        break;

    case "get_all_product":
        get_all_product();
        break;

    default :
        echo "no function found";
        break;
}
