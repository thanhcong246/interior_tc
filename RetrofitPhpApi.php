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

    $name = mysqli_real_escape_string($con, $name);

    if (isset($_FILES['category_img'])) {
        $image_file = $_FILES['category_img']['tmp_name'];
        $image_name = $_FILES['category_img']['name'];
        $image_path = 'uploads/' . uniqid() . '.' . $image_name;

        // Di chuyển tệp hình ảnh vào thư mục uploads
        if (move_uploaded_file($image_file, $image_path)) {
            $image_filename = basename($image_path);

            $query = "INSERT INTO categories (name, category_img) VALUES ('$name', '$image_filename')";

            if (mysqli_query($con, $query)) {
                $response = array("errorcode" => "000", "message" => "Category added successfully");
                echo json_encode($response);
            } else {
                $response = array("errorcode" => "101", "message" => "Failed to add Category");
                echo json_encode($response);
            }
        } else {
            $response = array("errorcode" => "102", "message" => "Failed to move uploaded image");
            echo json_encode($response);
        }
    } else {
        $response = array("errorcode" => "111", "message" => "No image uploaded");
        echo json_encode($response);
    }
}

function update_category()
{
    global $con;

    $category_id = $_POST["category_id"];
    $name = $_POST["name"];

    $name = mysqli_real_escape_string($con, $name);

    $query = "SELECT category_img FROM categories WHERE category_id = $category_id";
    $result = mysqli_query($con, $query);

    if ($result) {
        $category = mysqli_fetch_assoc($result);
        $old_image = $category['category_img'];

        if (isset($_FILES['category_img'])) {
            $image_file = $_FILES['category_img']['tmp_name'];
            $image_name = $_FILES['category_img']['name'];
            $image_path = 'uploads/' . uniqid() . '.' . $image_name;

            if (move_uploaded_file($image_file, $image_path)) {
                $image_filename = basename($image_path);

                $query = "UPDATE categories SET name = '$name', category_img = '$image_filename' WHERE category_id = $category_id";

                if (mysqli_query($con, $query)) {
                    // Delete old image file
                    if ($old_image && file_exists("uploads/$old_image")) {
                        unlink("uploads/$old_image");
                    }

                    $response = array("errorcode" => "000", "message" => "Category updated successfully");
                    echo json_encode($response);
                } else {
                    $response = array("errorcode" => "101", "message" => "Failed to update Category");
                    echo json_encode($response);
                }
            } else {
                $response = array("errorcode" => "102", "message" => "Failed to move uploaded image");
                echo json_encode($response);
            }
        } else {
            $query = "UPDATE categories SET name = '$name' WHERE category_id = $category_id";

            if (mysqli_query($con, $query)) {
                $response = array("errorcode" => "000", "message" => "Category updated successfully");
                echo json_encode($response);
            } else {
                $response = array("errorcode" => "101", "message" => "Failed to update Category");
                echo json_encode($response);
            }
        }
    } else {
        $response = array("errorcode" => "103", "message" => "Category not found");
        echo json_encode($response);
    }
}


function add_product()
{
    global $con;

    $name = $_POST["name"];
    $old_price = $_POST["old_price"];
    $category_id = $_POST["category_id"];
    $discount = $_POST["discount"];

    $name = mysqli_real_escape_string($con, $name);

    if (isset($_FILES['image'])) {
        $image_file = $_FILES['image']['tmp_name'];
        $image_name = $_FILES['image']['name'];
        $image_path = 'uploads/' . uniqid() . '.' . $image_name;

        // Di chuyển tệp hình ảnh vào thư mục uploads
        if (move_uploaded_file($image_file, $image_path)) {
            $image_filename = basename($image_path);

            $query = "INSERT INTO Products (name, image_url, old_price, category_id, discount)
                      VALUES ('$name', '$image_filename', $old_price, $category_id, $discount)";

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
    $old_price = $_POST["old_price"];
    $category_id = $_POST["category_id"];
    $discount = $_POST["discount"];

    $name = mysqli_real_escape_string($con, $name);

    // Lấy thông tin sản phẩm cũ
    $query_select = "SELECT image_url FROM Products WHERE product_id = $product_id";
    $result_select = mysqli_query($con, $query_select);
    $row_select = mysqli_fetch_assoc($result_select);
    $old_image = $row_select['image_url'];

    if (isset($_FILES['image'])) {
        $image_file = $_FILES['image']['tmp_name'];
        $image_name = $_FILES['image']['name'];
        $image_path = 'uploads/' . uniqid() . '.' . $image_name;

        // Xóa ảnh cũ
        if (!empty($old_image)) {
            $old_image_path = 'uploads/' . $old_image;
            if (file_exists($old_image_path)) {
                unlink($old_image_path);
            }
        }

        // Di chuyển tệp hình ảnh mới vào thư mục uploads
        if (move_uploaded_file($image_file, $image_path)) {
            $image_filename = basename($image_path);

            $query = "UPDATE Products SET name = '$name', image_url = '$image_filename', old_price = $old_price, category_id = $category_id, discount = $discount WHERE product_id = $product_id";

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
        $query = "UPDATE Products SET name = '$name', old_price = $old_price, category_id = $category_id, discount = $discount WHERE product_id = $product_id";

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
    $search_product = trim($search_product); // Xóa khoảng trắng ở đầu và cuối

    // Tìm kiếm theo tên sản phẩm và áp dụng giảm giá
    $query = "SELECT *, CAST(old_price - (old_price * discount / 100) AS INT) AS price 
              FROM Products 
              WHERE name LIKE '%$search_product%'";
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
        $response = array("errorcode" => "111", "message" => "No search results found");
        echo json_encode($response);
    }
}


function get_all_category()
{
    global $con;

    $query = "SELECT * FROM categories";
    $result = mysqli_query($con, $query);

    if (mysqli_num_rows($result) > 0) {
        $products = array();

        while ($row = mysqli_fetch_assoc($result)) {
            $product = array(
                "category_id" => $row["category_id"],
                "name" => $row["name"],
                "category_img" => $row["category_img"],
            );
            $products[] = $product;
        }

        echo json_encode($products);
    } else {
        $response = array("errorcode" => "111", "message" => "No categoris found");
        echo json_encode($response);
    }
}


function get_all_product()
{
    global $con;

    $query = "SELECT *, CAST(old_price - (old_price * discount / 100) AS INT) AS price FROM Products";
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

function get_all_product_by_category_id()
{
    global $con;

    $category_id = $_POST["category_id"];
    $category_id = mysqli_real_escape_string($con, $category_id);

    $query = "SELECT *, CAST(old_price - (old_price * discount / 100) AS INT) AS price 
              FROM Products 
              WHERE category_id = $category_id";
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
        $response = array("errorcode" => "111", "message" => "No products found for the given category");
        echo json_encode($response);
    }
}

function get_product_by_id()
{
    global $con;

    $product_id = $_POST["product_id"];

    $query = "SELECT Products.product_id, Products.name, Products.old_price, Products.image_url, Products.discount, CAST(Products.old_price - (Products.old_price * Products.discount / 100) AS INT) AS price, Product_Details.img_url_one, Product_Details.img_url_two, Product_Details.img_url_three, Product_Details.img_url_four
              FROM Products
              LEFT JOIN product_details ON Products.product_id = product_details.product_id
              WHERE Products.product_id = '$product_id'";
    $result = mysqli_query($con, $query);

    if (mysqli_num_rows($result) > 0) {
        $products = array();

        while ($row = mysqli_fetch_assoc($result)) {
            $products[] = $row;
        }

        echo json_encode($products);
    } else {
        $response = array("error_product_by_id" => "111", "message" => "Product not found");
        echo json_encode($response);
    }
}

function add_product_descriptions()
{
    global $con;

    $description = $_POST["description"];
    $product_id = $_POST["product_id"];

    $description = mysqli_real_escape_string($con, $description);


    $query = "INSERT INTO product_detail_descriptions (description, product_id) VALUES ('$description', '$product_id')";

    if (mysqli_query($con, $query)) {
        $response = array("error_product_detail_descriptions" => "000", "message" => "Product desc added successfully");
        echo json_encode($response);
    } else {
        $response = array("error_product_detail_descriptions" => "111", "message" => "Failed to add product desc");
        echo json_encode($response);
    }

}

function add_product_detail_imgs()
{
    global $con;

    $product_id = $_POST["product_id"];

    if (isset($_FILES['img_url_one'])) {
        $image_file_img_url_one = $_FILES['img_url_one']['tmp_name'];
        $image_name_img_url_one = $_FILES['img_url_one']['name'];
        $image_extension_img_url_one = pathinfo($image_name_img_url_one, PATHINFO_EXTENSION);
        $image_path_img_url_one = 'img_product_details/' . uniqid() . '.' . $image_extension_img_url_one;

        $image_file_img_url_two = $_FILES['img_url_two']['tmp_name'];
        $image_name_img_url_two = $_FILES['img_url_two']['name'];
        $image_extension_img_url_two = pathinfo($image_name_img_url_two, PATHINFO_EXTENSION);
        $image_path_img_url_two = 'img_product_details/' . uniqid() . '.' . $image_extension_img_url_two;

        $image_file_img_url_three = $_FILES['img_url_three']['tmp_name'];
        $image_name_img_url_three = $_FILES['img_url_three']['name'];
        $image_extension_img_url_three = pathinfo($image_name_img_url_three, PATHINFO_EXTENSION);
        $image_path_img_url_three = 'img_product_details/' . uniqid() . '.' . $image_extension_img_url_three;

        $image_file_img_url_four = $_FILES['img_url_four']['tmp_name'];
        $image_name_img_url_four = $_FILES['img_url_four']['name'];
        $image_extension_img_url_four = pathinfo($image_name_img_url_four, PATHINFO_EXTENSION);
        $image_path_img_url_four = 'img_product_details/' . uniqid() . '.' . $image_extension_img_url_four;

        // Di chuyển tệp hình ảnh vào thư mục
        if (move_uploaded_file($image_file_img_url_one, $image_path_img_url_one)) {
            move_uploaded_file($image_file_img_url_two, $image_path_img_url_two);
            move_uploaded_file($image_file_img_url_three, $image_path_img_url_three);
            move_uploaded_file($image_file_img_url_four, $image_path_img_url_four);

            $image_filename_img_one = basename($image_path_img_url_one);
            $image_filename_img_two = basename($image_path_img_url_two);
            $image_filename_img_three = basename($image_path_img_url_three);
            $image_filename_img_four = basename($image_path_img_url_four);

            $query = "INSERT INTO product_details (img_url_one, img_url_two, img_url_three, img_url_four, product_id)
                      VALUES ('$image_filename_img_one', '$image_filename_img_two', '$image_filename_img_three', '$image_filename_img_four', '$product_id')";

            if (mysqli_query($con, $query)) {
                $response = array("errorcode_detail_imgs" => "000", "message" => "Product detail imgs added successfully");
                echo json_encode($response);
            } else {
                $response = array("errorcode_detail_imgs" => "111", "message" => "Failed to add product details imgs");
                echo json_encode($response);
            }
        } else {
            $response = array("errorcode_detail_imgs" => "111", "message" => "Failed to move uploaded image");
            echo json_encode($response);
        }
    } else {
        $response = array("errorcode_detail_imgs" => "111", "message" => "No image uploaded");
        echo json_encode($response);
    }
}


function update_product_detail_imgs()
{
    global $con;

    $product_detail_id = $_POST["product_detail_id"];
    $product_id = $_POST["product_id"];

    // Lấy thông tin ảnh cũ
    $query_select = "SELECT img_url_one, img_url_two, img_url_three, img_url_four FROM product_details WHERE product_detail_id = product_detail_id";
    $result_select = mysqli_query($con, $query_select);
    $row_select = mysqli_fetch_assoc($result_select);
    $old_img_url_one = $row_select['img_url_one'];
    $old_img_url_two = $row_select['img_url_two'];
    $old_img_url_three = $row_select['img_url_three'];
    $old_img_url_four = $row_select['img_url_four'];

    if (isset($_FILES['img_url_one'])) {
        $image_file_img_url_one = $_FILES['img_url_one']['tmp_name'];
        $image_name_img_url_one = $_FILES['img_url_one']['name'];
        $image_extension_img_url_one = pathinfo($image_name_img_url_one, PATHINFO_EXTENSION);
        $image_path_img_url_one = 'img_product_details/' . uniqid() . '.' . $image_extension_img_url_one;

        $image_file_img_url_two = $_FILES['img_url_two']['tmp_name'];
        $image_name_img_url_two = $_FILES['img_url_two']['name'];
        $image_extension_img_url_two = pathinfo($image_name_img_url_two, PATHINFO_EXTENSION);
        $image_path_img_url_two = 'img_product_details/' . uniqid() . '.' . $image_extension_img_url_two;

        $image_file_img_url_three = $_FILES['img_url_three']['tmp_name'];
        $image_name_img_url_three = $_FILES['img_url_three']['name'];
        $image_extension_img_url_three = pathinfo($image_name_img_url_three, PATHINFO_EXTENSION);
        $image_path_img_url_three = 'img_product_details/' . uniqid() . '.' . $image_extension_img_url_three;

        $image_file_img_url_four = $_FILES['img_url_four']['tmp_name'];
        $image_name_img_url_four = $_FILES['img_url_four']['name'];
        $image_extension_img_url_four = pathinfo($image_name_img_url_four, PATHINFO_EXTENSION);
        $image_path_img_url_four = 'img_product_details/' . uniqid() . '.' . $image_extension_img_url_four;

        // Xóa ảnh cũ
        if (!empty($old_img_url_one)) {
            $old_image_path_one = 'img_product_details/' . $old_img_url_one;
            if (file_exists($old_image_path_one)) {
                unlink($old_image_path_one);
            }
        }

        if (!empty($old_img_url_two)) {
            $old_image_path_two = 'img_product_details/' . $old_img_url_two;
            if (file_exists($old_image_path_two)) {
                unlink($old_image_path_two);
            }
        }

        if (!empty($old_img_url_three)) {
            $old_image_path_three = 'img_product_details/' . $old_img_url_three;
            if (file_exists($old_image_path_three)) {
                unlink($old_image_path_three);
            }
        }

        if (!empty($old_img_url_four)) {
            $old_image_path_four = 'img_product_details/' . $old_img_url_four;
            if (file_exists($old_image_path_four)) {
                unlink($old_image_path_four);
            }
        }

        // Di chuyển tệp hình ảnh mới vào thư mục
        if (move_uploaded_file($image_file_img_url_one, $image_path_img_url_one)) {
            move_uploaded_file($image_file_img_url_two, $image_path_img_url_two);
            move_uploaded_file($image_file_img_url_three, $image_path_img_url_three);
            move_uploaded_file($image_file_img_url_four, $image_path_img_url_four);

            $image_filename_img_one = basename($image_path_img_url_one);
            $image_filename_img_two = basename($image_path_img_url_two);
            $image_filename_img_three = basename($image_path_img_url_three);
            $image_filename_img_four = basename($image_path_img_url_four);

            $query = "UPDATE product_details SET img_url_one = '$image_filename_img_one', img_url_two = '$image_filename_img_two', img_url_three = '$image_filename_img_three', img_url_four = '$image_filename_img_four', product_id = '$product_id' WHERE product_detail_id = $product_detail_id";

            if (mysqli_query($con, $query)) {
                $response = array("errorcode_detail_imgs" => "000", "message" => "Product detail imgs updated successfully");
                echo json_encode($response);
            } else {
                $response = array("errorcode_detail_imgs" => "111", "message" => "Failed to update product details imgs");
                echo json_encode($response);
            }
        } else {
            $response = array("errorcode_detail_imgs" => "111", "message" => "Failed to move uploaded image");
            echo json_encode($response);
        }
    } else {
        $response = array("errorcode_detail_imgs" => "111", "message" => "No image uploaded");
        echo json_encode($response);
    }
}


function add_specifications()
{

    global $con;

    $product_id = $_POST["product_id"];
    $width = $_POST["width"];
    $height = $_POST["height"];
    $number_of_drawers = $_POST["number_of_drawers"];
    $type = $_POST["type"];

    $width = mysqli_real_escape_string($con, $width);
    $height = mysqli_real_escape_string($con, $height);
    $number_of_drawers = mysqli_real_escape_string($con, $number_of_drawers);
    $type = mysqli_real_escape_string($con, $type);

    $query = "INSERT INTO specifications (width, height, number_of_drawers, type, product_id) 
                        VALUES ('$width', '$height', '$number_of_drawers', '$type', '$product_id')";

    if (mysqli_query($con, $query)) {
        $response = array("error_product_detail_specifications" => "000", "message" => "Product specifications added successfully");
        echo json_encode($response);
    } else {
        $response = array("error_product_detail_specifications" => "111", "message" => "Failed to add product specifications");
        echo json_encode($response);
    }
}

function update_specifications()
{
    global $con;

    $specification_id = $_POST["specification_id"];
    $product_id = $_POST["product_id"];
    $width = $_POST["width"];
    $height = $_POST["height"];
    $number_of_drawers = $_POST["number_of_drawers"];
    $type = $_POST["type"];

    $width = mysqli_real_escape_string($con, $width);
    $height = mysqli_real_escape_string($con, $height);
    $number_of_drawers = mysqli_real_escape_string($con, $number_of_drawers);
    $type = mysqli_real_escape_string($con, $type);

    $query = "UPDATE specifications SET width = '$width', height = '$height', number_of_drawers = '$number_of_drawers', type = '$type', product_id = '$product_id' WHERE specification_id = '$specification_id'";

    if (mysqli_query($con, $query)) {
        $response = array("error_product_detail_specifications" => "000", "message" => "Product specifications updated successfully");
        echo json_encode($response);
    } else {
        $response = array("error_product_detail_specifications" => "111", "message" => "Failed to update product specifications");
        echo json_encode($response);
    }
}

function get_product_by_id_description()
{
    global $con;

    $product_id = $_POST["product_id"];

    $query = "SELECT product_detail_descriptions.description
          FROM product_detail_descriptions
          LEFT JOIN Products ON product_detail_descriptions.product_id = Products.product_id
          WHERE Products.product_id = '$product_id'";
    $result = mysqli_query($con, $query);

    if (mysqli_num_rows($result) > 0) {
        $products = array();

        while ($row = mysqli_fetch_assoc($result)) {
            $products[] = $row;
        }

        echo json_encode($products);
    } else {
        $response = array("error_product_by_id_desc" => "111");
        echo json_encode($response);
    }
}

function get_product_by_id_specification()
{
    global $con;

    $product_id = $_POST["product_id"];

    $query = "SELECT specifications.width, specifications.height, specifications.number_of_drawers, specifications.type
          FROM specifications
          LEFT JOIN Products ON specifications.product_id = Products.product_id
          WHERE Products.product_id = '$product_id'";
    $result = mysqli_query($con, $query);

    if (mysqli_num_rows($result) > 0) {
        $products = array();

        while ($row = mysqli_fetch_assoc($result)) {
            $products[] = $row;
        }

        echo json_encode($products);
    } else {
        $response = array("error_product_by_id_spec" => "111");
        echo json_encode($response);
    }
}


function add_product_detail_reviews()
{
    global $con;

    $product_id = $_POST["product_id"];
    $content = $_POST["content"];
    $rating = $_POST["rating"];
    $user_name = $_POST["user_name"];

    $content = mysqli_real_escape_string($con, $content);
    $rating = mysqli_real_escape_string($con, $rating);
    $user_name = mysqli_real_escape_string($con, $user_name);

    $query = "INSERT INTO reviews (product_id, content, rating, user_name, comment_date) 
              VALUES ('$product_id', '$content', '$rating', '$user_name', NOW())";

    if (mysqli_query($con, $query)) {
        $response = array("error_product_detail_reviews" => "000");
        echo json_encode($response);
    } else {
        $response = array("error_product_detail_reviews" => "111");
        echo json_encode($response);
    }
}


function get_product_by_id_reviews()
{
    global $con;

    $product_id = $_POST["product_id"];

    $query = "SELECT content, rating, user_name, comment_date FROM reviews WHERE product_id = '$product_id' ORDER BY review_id DESC";
    $result = mysqli_query($con, $query);

    if (mysqli_num_rows($result) > 0) {
        $products = array();

        while ($row = mysqli_fetch_assoc($result)) {
            $products[] = $row;
        }

        echo json_encode($products);
    } else {
        $response = array("error_product_by_id_reviews" => "111");
        echo json_encode($response);
    }
}


function get_product_by_id_review_rating_sum()
{
    global $con;

    $product_id = $_POST["product_id"];

    $query = "SELECT
                rating_start1,
                rating_start2,
                rating_start3,
                rating_start4,
                rating_start5,
                rating_start1 + rating_start2 + rating_start3 + rating_start4 + rating_start5 AS rating_sum,
                ROUND((1 * rating_start1 + 2 * rating_start2 + 3 * rating_start3 + 4 * rating_start4 + 5 * rating_start5) / 
                (rating_start1 + rating_start2 + rating_start3 + rating_start4 + rating_start5), 4) AS rating_review_overall
            FROM
                (SELECT 
                    COUNT(CASE WHEN rating = 1 THEN rating END) AS rating_start1,
                    COUNT(CASE WHEN rating = 2 THEN rating END) AS rating_start2,
                    COUNT(CASE WHEN rating = 3 THEN rating END) AS rating_start3,
                    COUNT(CASE WHEN rating = 4 THEN rating END) AS rating_start4,
                    COUNT(CASE WHEN rating = 5 THEN rating END) AS rating_start5
                FROM reviews
                WHERE product_id = '$product_id'
                GROUP BY product_id) AS subquery";

    $result = mysqli_query($con, $query);

    if (mysqli_num_rows($result) > 0) {
        $row = mysqli_fetch_assoc($result);

        // Rounding logic
        $rating_review_overall = $row['rating_review_overall'];
        if ($rating_review_overall < 0.4) {
            $row['rating_review_overall'] = floor($rating_review_overall);
        } else {
            $row['rating_review_overall'] = ceil($rating_review_overall);
        }

        echo json_encode($row);
    } else {
        $response = array("error_product_by_id_reviews_rating_sum" => "111");
        echo json_encode($response);
    }
}

function add_cart_by_id()
{

    global $con;

    $product_id = $_POST["product_id"];
    $email = $_POST["email"];
    $price = $_POST["price"];
    $cart_name = $_POST["cart_name"];
    $cart_img = $_POST["cart_img"];


    $query = "INSERT INTO carts (product_id, email, price, cart_name, cart_img) 
              VALUES ('$product_id', '$email', '$price', '$cart_name', '$cart_img')";

    if (mysqli_query($con, $query)) {
        $response = array("error_cart_add" => "000");
        echo json_encode($response);
    } else {
        $response = array("error_cart_add" => "111");
        echo json_encode($response);
    }

}

function add_cart_payment_by_id()
{

    global $con;

    $product_id = $_POST["product_id"];
    $email = $_POST["email"];
    $price = $_POST["price"];
    $cart_name = $_POST["cart_name"];
    $cart_img = $_POST["cart_img"];


    $query = "INSERT INTO cart_payment (product_id, email, price, cart_name, cart_img) 
              VALUES ('$product_id', '$email', '$price', '$cart_name', '$cart_img')";

    if (mysqli_query($con, $query)) {
        $response = array("error_cart_add" => "000");
        echo json_encode($response);
    } else {
        $response = array("error_cart_add" => "111");
        echo json_encode($response);
    }

}

function get_cart_by_id()
{
    global $con;

    $email = $_POST["email"];
    $query = "SELECT * FROM carts WHERE email = '$email'";
    $result = mysqli_query($con, $query);

    if (mysqli_num_rows($result) > 0) {
        $products = array();
        $product_ids = array(); // Mảng lưu trữ product_id đã xuất hiện
        $quantity_product_id = array(); // Mảng lưu trữ số lần xuất hiện của từng product_id

        while ($row = mysqli_fetch_assoc($result)) {
            $product_id = $row["product_id"];

            // Kiểm tra xem product_id đã tồn tại trong mảng kết hợp chưa
            if (!in_array($product_id, $product_ids)) {
                $product = array(
                    "product_id" => $product_id,
                    "email" => $row["email"],
                    "price" => $row["price"],
                    "cart_name" => $row["cart_name"],
                    "cart_img" => $row["cart_img"]
                );
                $products[] = $product;

                // Đánh dấu product_id đã xuất hiện
                $product_ids[] = $product_id;
            }

            // Tăng số lần xuất hiện của product_id
            if (isset($quantity_product_id[$product_id])) {
                $quantity_product_id[$product_id]++;
            } else {
                $quantity_product_id[$product_id] = 1;
            }
        }

        // Thêm quantity_product_id và sum_product_id_price vào mỗi sản phẩm trong mảng $products
        foreach ($products as &$product) {
            $product_id = $product["product_id"];
            $quantity = $quantity_product_id[$product_id];
            $product["quantity_product_id"] = $quantity;
            $product["sum_product_id_price"] = $product["price"] * $quantity;
        }

        echo json_encode($products);
    } else {
        $response = array("error_get_cart" => "111");
        echo json_encode($response);
    }
}

function get_cart_payment_by_id()
{
    global $con;

    $email = $_POST["email"];
    $query = "SELECT * FROM cart_payment WHERE email = '$email'";
    $result = mysqli_query($con, $query);

    if (mysqli_num_rows($result) > 0) {
        $products = array();
        $product_ids = array(); // Mảng lưu trữ product_id đã xuất hiện
        $quantity_product_id = array(); // Mảng lưu trữ số lần xuất hiện của từng product_id

        while ($row = mysqli_fetch_assoc($result)) {
            $product_id = $row["product_id"];

            // Kiểm tra xem product_id đã tồn tại trong mảng kết hợp chưa
            if (!in_array($product_id, $product_ids)) {
                $product = array(
                    "product_id" => $product_id,
                    "email" => $row["email"],
                    "price" => $row["price"],
                    "cart_name" => $row["cart_name"],
                    "cart_img" => $row["cart_img"]
                );
                $products[] = $product;

                // Đánh dấu product_id đã xuất hiện
                $product_ids[] = $product_id;
            }

            // Tăng số lần xuất hiện của product_id
            if (isset($quantity_product_id[$product_id])) {
                $quantity_product_id[$product_id]++;
            } else {
                $quantity_product_id[$product_id] = 1;
            }
        }

        // Thêm quantity_product_id và sum_product_id_price vào mỗi sản phẩm trong mảng $products
        foreach ($products as &$product) {
            $product_id = $product["product_id"];
            $quantity = $quantity_product_id[$product_id];
            $product["quantity_product_id"] = $quantity;
            $product["sum_product_id_price"] = $product["price"] * $quantity;
        }

        echo json_encode($products);
    } else {
        $response = array("error_get_cart" => "111");
        echo json_encode($response);
    }
}

function total_price_and_quantity_cart_by_id()
{
    global $con;

    $email = $_POST["email"];
    $query = "SELECT product_id, COUNT(DISTINCT product_id) AS total_quantity, SUM(price) AS total_price
              FROM carts 
              WHERE email = '$email' 
              GROUP BY product_id";
    $result = mysqli_query($con, $query);

    $total_price = 0;
    $total_quantity = 0;

    while ($row = mysqli_fetch_assoc($result)) {
        $total_price += $row["total_price"];
        $total_quantity += $row["total_quantity"];
    }

    $total_payment = $total_price + 220000;

    $response = array("transportation_costs" => 220000, "total_price" => $total_price, "total_quantity" => $total_quantity, "total_payment" => $total_payment);
    echo json_encode($response);
}

function total_price_and_quantity_cart_payment_by_id()
{
    global $con;

    $email = $_POST["email"];
    $query = "SELECT product_id, COUNT(DISTINCT product_id) AS total_quantity, SUM(price) AS total_price
              FROM cart_payment 
              WHERE email = '$email' 
              GROUP BY product_id";
    $result = mysqli_query($con, $query);

    $total_price = 0;
    $total_quantity = 0;

    while ($row = mysqli_fetch_assoc($result)) {
        $total_price += $row["total_price"];
        $total_quantity += $row["total_quantity"];
    }

    $total_payment = $total_price + 220000;

    $response = array("transportation_costs" => 220000, "total_price" => $total_price, "total_quantity" => $total_quantity, "total_payment" => $total_payment);
    echo json_encode($response);
}

function delete_cart_by_id()
{
    global $con;

    $email = $_POST["email"];
    $product_id = $_POST["product_id"];
    $query = "DELETE FROM carts WHERE email = '$email' AND product_id = '$product_id' LIMIT 1";
    $result = mysqli_query($con, $query);

    if ($result) {
        $response = array("error_remove_cart_by_id" => "000");
    } else {
        $response = array("error_remove_cart_by_id" => "111");
    }

    echo json_encode($response);
}

function delete_cart_payment_by_id()
{
    global $con;

    $email = $_POST["email"];
    $product_id = $_POST["product_id"];
    $query = "DELETE FROM cart_payment WHERE email = '$email' AND product_id = '$product_id' LIMIT 1";
    $result = mysqli_query($con, $query);

    if ($result) {
        $response = array("error_remove_cart_by_id" => "000");
    } else {
        $response = array("error_remove_cart_by_id" => "111");
    }

    echo json_encode($response);
}

function delete_cart_by_id_all()
{
    global $con;

    $email = $_POST["email"];
    $product_id = $_POST["product_id"];
    $query = "DELETE FROM carts WHERE email = '$email' AND product_id = '$product_id'";
    $result = mysqli_query($con, $query);

    if ($result) {
        $response = array("error_remove_cart_by_id_all" => "000");
    } else {
        $response = array("error_remove_cart_by_id_all" => "111");
    }

    echo json_encode($response);
}

function delete_cart_payment_by_id_all()
{
    global $con;

    $email = $_POST["email"];
    $product_id = $_POST["product_id"];
    $query = "DELETE FROM cart_payment WHERE email = '$email' AND product_id = '$product_id'";
    $result = mysqli_query($con, $query);

    if ($result) {
        $response = array("error_remove_cart_by_id_all" => "000");
    } else {
        $response = array("error_remove_cart_by_id_all" => "111");
    }

    echo json_encode($response);
}

function delete_all_cart()
{
    global $con;

    $email = $_POST["email"];
    $query = "DELETE FROM carts WHERE email = '$email'";
    $result = mysqli_query($con, $query);

    if ($result) {
        $response = array("error_all_cart_by_product_id" => "000");
    } else {
        $response = array("error_all_cart_by_product_id" => "111");
    }

    echo json_encode($response);
}

function delete_all_cart_payment()
{
    global $con;

    $email = $_POST["email"];
    $query = "DELETE FROM cart_payment WHERE email = '$email'";
    $result = mysqli_query($con, $query);

    if ($result) {
        $response = array("error_all_cart_by_product_id" => "000");
    } else {
        $response = array("error_all_cart_by_product_id" => "111");
    }

    echo json_encode($response);
}

function add_payments()
{
    global $con;
    $name = $_POST["name"];
    $email = $_POST["email"];
    $phone = $_POST["phone"];
    $apartment_number = $_POST["apartment_number"];
    $ward = $_POST["ward"];
    $district = $_POST["district"];
    $province = $_POST["province"];
    $note = $_POST["note"];
    $quantityProduct = $_POST["quantityProduct"];
    $total = $_POST["total"];

    $name = mysqli_real_escape_string($con, $name);
    $email = mysqli_real_escape_string($con, $email);
    $phone = mysqli_real_escape_string($con, $phone);
    $apartment_number = mysqli_real_escape_string($con, $apartment_number);
    $ward = mysqli_real_escape_string($con, $ward);
    $district = mysqli_real_escape_string($con, $district);
    $province = mysqli_real_escape_string($con, $province);
    $note = mysqli_real_escape_string($con, $note);
    $quantityProduct = mysqli_real_escape_string($con, $quantityProduct);
    $total = mysqli_real_escape_string($con, $total);

    $query = "INSERT INTO payments (name, email, phone, apartment_number, ward, district, province, note, quantityProduct, total) 
                        VALUES ('$name', '$email', '$phone', '$apartment_number', '$ward', '$district', '$province', '$note', '$quantityProduct', '$total')";

    if (mysqli_query($con, $query)) {
        $response = array("payments" => "000");
        echo json_encode($response);
    } else {
        $response = array("payments" => "111");
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

    case "update_category":
        update_category();
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

    case "get_all_category":
        get_all_category();
        break;

    case "get_all_product":
        get_all_product();
        break;

    case "get_all_product_by_category_id":
        get_all_product_by_category_id();
        break;

    case "get_product_by_id":
        get_product_by_id();
        break;

    case "add_product_descriptions";
        add_product_descriptions();
        break;

    case "add_product_detail_imgs":
        add_product_detail_imgs();
        break;

    case "update_product_detail_imgs":
        update_product_detail_imgs();
        break;

    case "add_specifications":
        add_specifications();
        break;

    case "update_specifications":
        update_specifications();
        break;

    case "get_product_by_id_description":
        get_product_by_id_description();
        break;

    case "get_product_by_id_specification":
        get_product_by_id_specification();
        break;

    case "add_product_detail_reviews":
        add_product_detail_reviews();
        break;

    case "get_product_by_id_reviews":
        get_product_by_id_reviews();
        break;

    case "get_product_by_id_review_rating_sum":
        get_product_by_id_review_rating_sum();
        break;

    case "add_cart_by_id":
        add_cart_by_id();
        break;

    case "add_cart_payment_by_id":
        add_cart_payment_by_id();
        break;

    case "get_cart_by_id":
        get_cart_by_id();
        break;

    case "get_cart_payment_by_id":
        get_cart_payment_by_id();
        break;

    case "total_price_and_quantity_cart_by_id":
        total_price_and_quantity_cart_by_id();
        break;

    case "total_price_and_quantity_cart_payment_by_id":
        total_price_and_quantity_cart_payment_by_id();
        break;

    case "delete_cart_by_id":
        delete_cart_by_id();
        break;

    case "delete_cart_payment_by_id":
        delete_cart_payment_by_id();
        break;

    case "delete_cart_by_id_all":
        delete_cart_by_id_all();
        break;

    case "delete_cart_payment_by_id_all":
        delete_cart_payment_by_id_all();
        break;

    case "delete_all_cart":
        delete_all_cart();
        break;

    case "delete_all_cart_payment":
        delete_all_cart_payment();
        break;

    case "add_payments":
        add_payments();
        break;

    default :
        echo "no function found";
        break;
}
