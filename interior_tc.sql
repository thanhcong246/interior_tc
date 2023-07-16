-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th7 16, 2023 lúc 05:37 AM
-- Phiên bản máy phục vụ: 10.4.27-MariaDB
-- Phiên bản PHP: 8.1.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `interior_tc`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `carts`
--

CREATE TABLE `carts` (
  `cart_id` int(11) NOT NULL,
  `product_id` int(11) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `price` int(11) DEFAULT NULL,
  `cart_name` varchar(255) DEFAULT NULL,
  `cart_img` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `carts`
--

INSERT INTO `carts` (`cart_id`, `product_id`, `email`, `price`, `cart_name`, `cart_img`) VALUES
(6, 1008, 'tcshopfd@gmail.com', 27456545, 'Sofa Coastal 3 chỗ', '64b278aa21cd7.Sofa-Coastal-3-cho-3-300x200.jpg'),
(7, 1043, 'tcshopfd@gmail.com', 189928000, 'Tủ áo Gallery', '64b2994b21cec.Tu-quan-ao-AA-Gallery-Q9-01-2-300x200.jpg');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `cart_payment`
--

CREATE TABLE `cart_payment` (
  `cart_id` int(11) NOT NULL,
  `product_id` int(11) NOT NULL,
  `email` varchar(255) NOT NULL,
  `price` int(11) NOT NULL,
  `cart_name` varchar(255) NOT NULL,
  `cart_img` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `categories`
--

CREATE TABLE `categories` (
  `category_id` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `category_img` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `categories`
--

INSERT INTO `categories` (`category_id`, `name`, `category_img`) VALUES
(1, 'Sofa', '64b277ed190c3.Sofa-Coastal-1-cho-1-300x200.jpg'),
(2, 'Ghế thư giãn', '64b27f0934913.ghe-thu-gian-dien-LAZBOY-1PT559-EDEN-SL-da-CLOUD-2-300x200.jpg'),
(3, 'Thảm trang trí', '64b2831fd4c07.THAM-TERRA-NEVE-170X200-39216P-1-300x200.jpg'),
(4, 'Đèn trang trí', '64b284f130490.den-ban-aria-marb-nickel-60x42cm-50191a-300x200.jpg'),
(5, 'Kệ sách', '64b28667a7bb7.80432-ke-sach-lib-trang-300x200.jpg'),
(6, 'Tủ âm tường', '64b287f9400da.tu_am_tuong_whitecalypso_l-300x210.jpg'),
(7, 'Tủ áo', '64b28971c410a.Tu-quan-ao-AA-Gallery-Q9-01-2-300x200.jpg'),
(8, 'Ghế bar', '64b299ca3e935.ghe-bar-3-101653-3-300x200.jpg'),
(9, 'Bàn ăn', '64b29b15f1f7c.ban-an-peak-hien-dai-van-may-ceramic-22-300x180.jpg');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `notifications`
--

CREATE TABLE `notifications` (
  `notification_id` int(11) NOT NULL,
  `title` varchar(255) DEFAULT NULL,
  `date_time` datetime DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `notifications`
--

INSERT INTO `notifications` (`notification_id`, `title`, `date_time`, `email`) VALUES
(1, 'Đặt hàng thành công', '2023-07-15 20:43:29', 'tcshopfd@gmail.com'),
(2, 'Đặt hàng thành công', '2023-07-15 20:50:43', 'tcshopfd@gmail.com'),
(3, 'Đặt hàng thành công', '2023-07-15 21:00:25', 'tcshopfd@gmail.com');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `payments`
--

CREATE TABLE `payments` (
  `payment_id` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `apartment_number` varchar(255) DEFAULT NULL,
  `ward` varchar(255) DEFAULT NULL,
  `district` varchar(255) DEFAULT NULL,
  `province` varchar(255) DEFAULT NULL,
  `note` text DEFAULT NULL,
  `quantityProduct` int(11) DEFAULT NULL,
  `total` int(11) DEFAULT NULL,
  `payment_date` datetime DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `payments`
--

INSERT INTO `payments` (`payment_id`, `name`, `email`, `phone`, `apartment_number`, `ward`, `district`, `province`, `note`, `quantityProduct`, `total`, `payment_date`) VALUES
(1, 'shop tc', 'tcshopfd@gmail.com', '0987654321', '470 tran dai nghia', 'Phường Hoà Quý', 'Quận Ngũ Hành Sơn', 'Thành phố Đà Nẵng', '', 2, 70876090, '2023-07-15 20:43:29'),
(2, 'shop tc', 'tcshopfd@gmail.com', '0987654321', '4 hung vuong', 'Phường Phúc Xá', 'Quận Ba Đình', 'Thành phố Hà Nội', '', 1, 27676545, '2023-07-15 20:50:43'),
(3, 'shop tc', 'tcshopfd@gmail.com', '1234567890', '4 hung vuong', 'Phường Phúc Xá', 'Quận Ba Đình', 'Thành phố Hà Nội', '', 2, 49681545, '2023-07-15 21:00:25');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `products`
--

CREATE TABLE `products` (
  `product_id` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `old_price` int(11) DEFAULT NULL,
  `category_id` int(11) DEFAULT NULL,
  `discount` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `products`
--

INSERT INTO `products` (`product_id`, `name`, `image_url`, `old_price`, `category_id`, `discount`) VALUES
(1008, 'Sofa Coastal 3 chỗ', '64b278aa21cd7.Sofa-Coastal-3-cho-3-300x200.jpg', 32301818, 1, 15),
(1009, 'Sofa 3 chỗ vải màu nâu MB22-05', '64b2798781ae1.SOFA-3-CHO-VAI-MAU-NAU-MB22-05-1-300x200.jpg', 24450000, 1, 10),
(1010, 'Sofa Dax Everest 3 chỗ vải màu vàng', '64b279ea5948b.Sofa-Dax-Everest-3-cho-mau-vang-1-300x200.jpg', 22490000, 1, 30),
(1011, 'Sofa 3 chỗ ONA HIM da den', '64b27af832ee7.Sofa-3-Cho-Ona-Him-da-den-1-300x200.jpg', 76000000, 1, 45),
(1012, 'Sofa 3 chỗ Osaka mẫu 1 vải 29', '64b27b3e514ad.sofa-osaka-3-cho-3101896-1-300x177.jpg', 28380000, 1, 5),
(1013, 'Sofa 3 chỗ PENNY - vải màu cam', '64b27b92ad378.sofa-penny-vai-cam-2-300x200.jpg', 25600000, 1, 15),
(1014, 'Sofa Jadora 2.5 chỗ vải VANT4029/VACT4709', '64b27be733541.JADORA-SAC-MAU-TRANG-NHA-300x200.jpg', 34269999, 1, 15),
(1015, 'Sofa Moon 2 chỗ vải MB hồng L1-14', '64b27c1b1a09e.sofa-moon-1-300x200.jpg', 16999999, 1, 10),
(1016, 'Ghế thư giãn điện Lazboy da Ginger Dreamtime', '64b27f5b2c7d5.ghe-thu-gian-dien-LAZBOY-1PT554-DREAMTIME-FL-da-GINGER-300x200.jpg', 57740000, 2, 25),
(1017, 'Ghế thư giãn điện Lazboy da Pebble', '64b27f7e861b5.ghe-thu-gian-dien-LAZBOY-1PT554-DREAMTIME-EL-da-PEBBLE-300x200.jpg', 54440000, 2, 25),
(1018, 'Ghế thư giãn điện Lazboy da Stone', '64b27fbb768fb.ghe-thu-gian-dien-LAZBOY-1PT559-EDEN-SL-da-STONE-300x200.jpg', 44470000, 2, 10),
(1019, 'Ghế thư giãn Lazboy 10T352 Shannon da Sienna', '64b2800041b60.GHE-LAZBOY-10T352-SHANNON-DA-SIENNA-3106238-300x200.jpg', 33860000, 2, 12),
(1020, 'Ghế thư giãn Lazboy Canyon 10T560 Da Pebble', '64b28037c4080.GHE-LAZBOY-CANYON-10T560-DA-PEBBLE-3106235-300x200.jpg', 47860000, 2, 19),
(1021, 'Ghế thư giãn Lazboy Canyon 10T560 Pebble', '64b2806c3db25.GHE-LAZBOY-CANYON-10T560-SIENNA-3106216-300x200.jpg', 45460000, 2, 5),
(1022, 'Thảm Solar Grey/Terra 610046Z', '64b283f5beb4d.THAM-SOLAR-200X290-GREYTERRA-610046Z-4-300x200.jpg', 28450000, 3, 20),
(1023, 'Thảm Magic mùa thu', '64b2841d977e7.THAM-MAGIC-200X290-AUTUMN-600072Z-2-300x200.jpg', 28750000, 3, 27),
(1024, 'Thảm Magic đại dương', '64b2843c7be89.THAM-MAGIC-200X290-OCEAN-6000070Z-3-300x200.jpg', 28750000, 3, 27),
(1025, 'Thảm Labyrinth 150x240', '64b28477ed816.THAM-LABYRINTH-150X240-52739K-1-300x200.jpg', 10610000, 3, 3),
(1026, 'Đèn bàn Antiq Brass Cryst 36cm', '64b28528e3ec7.den-ban-antiq-brass-cryst-36cm-5617a-300x200.jpg', 13941818, 4, 13),
(1027, 'Đèn bàn Lombard 107338E', '64b28558e2d40.Den-Ban-Lombard-107338E-3105570-1-300x177.jpg', 16887273, 4, 10),
(1028, 'Đèn bàn Fotellina Ant Brass 114940E', '64b285ad02bcd.Den-Ban-Fotellina-Ant-Brass-114940E-3105569-3-300x177.jpg', 35934545, 4, 40),
(1029, 'Đèn bàn Giafranco Vintage', '64b285eebec49.DEN-BAN-GIANFRANCO-VINTAGE-116913E-300x200.jpg', 32282182, 4, 22),
(1030, 'Đèn bàn Giafranco Gumetal', '64b285ff118df.DEN-BAN-GIANFRANCO-GUNMETAL-116914E-300x200.jpg', 32282182, 4, 22),
(1031, 'Kệ sách Cabo PMA 732007', '64b2872be7921.KE-CABO-1400x400x1760-PMA732007-1-300x200.jpg', 16700000, 5, 25),
(1032, 'Kệ sách Maxine -4 tầng/2 ngăn kéo', '64b2875f0db5c.3_93711_2-300x200.jpg', 30700000, 5, 5),
(1033, 'Kệ sách Brem màu trắng', '64b2879fbf850.nha-xinh-ke-sach-bem-300x200.jpg', 24970000, 5, 50),
(1034, 'Tủ âm Whitecalypso', '64b2883ab97d2.tu_am_tuong_whitecalypso_l-300x210.jpg', 124970000, 6, 32),
(1035, 'Tủ âm Kiwi', '64b2885ed1603.tu_am_tuong_kiwi_b_l-300x200.jpg', 99999999, 6, 20),
(1036, 'Tủ âm Diamond', '64b288807ec99.tu_am_tuong_diamond_l-300x210.jpg', 60489333, 6, 4),
(1037, 'Tủ âm Cannon', '64b288a3473eb.tu_am_tuong_canon_l-300x200.jpg', 260489333, 6, 10),
(1038, 'Tủ áo Maxine', '64b289a846b12.3_91000_2-300x200.jpg', 42420000, 7, 30),
(1039, 'Tủ áo hiện đại', '64b289ca0bfec.3-99496-1-300x200.jpg', 24009999, 7, 30),
(1040, 'Tủ áo hiện đại', '64b289e9376d4.tu-ao-hien-dai-4-300x200.jpg', 34009999, 7, 14),
(1041, 'Tủ áo Harmony', '64b28a0bdfc13.tu-ao-harmony-4-3-300x200.jpg', 12440001, 7, 0),
(1042, 'Tủ áo Acrylic', '64b28a2f2a80b.Tu-ao-Acrylic-2-300x200.jpg', 32310000, 7, 50),
(1043, 'Tủ áo Gallery', '64b2994b21cec.Tu-quan-ao-AA-Gallery-Q9-01-2-300x200.jpg', 237410000, 7, 20),
(1044, 'Ghế bar xoay Cognac', '64b299f711e94.80665-1000-nhaxinh-phong-an-ghe-bar-300x200.jpg', 5000000, 8, 0),
(1045, 'Ghế bar Monaco', '64b29a110372a.ghe-monaco-5002-300x200.jpg', 9400000, 8, 17),
(1046, 'Ghế bar Jenny - 96364J', '64b29a32c5923.ghe-bar-3-101653-3-300x200.jpg', 9400000, 8, 17),
(1047, 'Ghế bar Bridge màu nâu Da Cognac', '64b29a6812aec.ghe-bar-bridge-mau-nau-cognac-go-soi-300x200.jpg', 29400450, 8, 30),
(1048, 'Ghế bar Boheme da taupe', '64b29a99b6550.ghe-bar-boheme-1-300x200.jpg', 9730000, 8, 70),
(1049, 'Bàn ăn gỗ Pio 6 chỗ 1m8 mẫu 2', '64b29b5b5483d.ban-an-pio-300x200.jpg', 12770000, 9, 8),
(1050, 'Bàn ăn Peak hiện đại Caramic vân mây', '64b29b8e7f70b.ban-an-peak-hien-dai-van-may-ceramic-22-300x180.jpg', 44999000, 9, 28),
(1051, 'Bàn ăn gỗ Miami 1m7', '64b29bb56a607.phong-an-miami5-300x200.jpg', 13200000, 9, 46),
(1052, 'Bàn ăn gỗ Miami 1m2', '64b29bceb7756.nha-xinh-phong-an-miami-bac-au-300x200.jpg', 9200000, 9, 46),
(1053, 'Bàn ăn Jazz 1m6', '64b29be844345.phong-an-jazz-300x200 (1).jpg', 15200000, 9, 3),
(1054, 'Bàn ăn 8 chỗ PortoM2', '64b29c0b32975.phong-an-jazz-300x200 (1).jpg', 25200000, 9, 0);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `product_details`
--

CREATE TABLE `product_details` (
  `product_detail_id` int(11) NOT NULL,
  `img_url_one` varchar(255) NOT NULL,
  `img_url_two` varchar(255) NOT NULL,
  `img_url_three` varchar(255) NOT NULL,
  `img_url_four` varchar(255) NOT NULL,
  `product_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `product_details`
--

INSERT INTO `product_details` (`product_detail_id`, `img_url_one`, `img_url_two`, `img_url_three`, `img_url_four`, `product_id`) VALUES
(1, '64b2b86b9f21d.jpg', '64b2b86b9f221.jpg', '64b2b86b9f222.jpg', '64b2b86b9f223.', 1008),
(2, '64b2b9741bdfd.jpg', '64b2b9741be01.jpg', '64b2b9741c47d.', '64b2b9741c8dc.', 1009),
(3, '64b2b98dd1101.jpg', '64b2b98dd1106.jpg', '64b2b98dd1107.', '64b2b98dd1108.', 1009),
(4, '64b34e5af05c4.jpg', '64b34e5af05c8.jpg', '64b34e5af05c9.', '64b34e5af05ca.', 1010),
(5, '64b34f1d4533f.jpg', '64b34f1d45343.jpg', '64b34f1d45344.jpg', '64b34f1d45345.', 1011),
(6, '64b35006258c1.jpg', '64b35006258c7.jpg', '64b35006258c9.jpg', '64b35006258ca.jpg', 1012),
(7, '64b350e304800.jpg', '64b350e304805.jpg', '64b350e304808.jpg', '64b350e30480a.jpg', 1013),
(8, '64b351c74a549.jpg', '64b351c74a54d.jpg', '64b351c74a54f.', '64b351c74a550.', 1014),
(9, '64b3523293718.jpg', '64b352329371c.jpg', '64b352329371d.jpg', '64b352329371e.jpg', 1015),
(10, '64b3530ebb306.jpg', '64b3530ebb308.', '64b3530ebb309.', '64b3530ebb30a.', 1016),
(11, '64b3534a79513.jpg', '64b3534a7951c.', '64b3534a7951f.', '64b3534a79522.', 1017),
(12, '64b353a14067e.jpg', '64b353a140681.', '64b353a140682.', '64b353a140683.', 1018),
(13, '64b353cf31ce8.jpg', '64b353cf31cec.', '64b353cf31ced.', '64b353cf31cee.', 1019),
(14, '64b354541d779.jpg', '64b354541d77d.', '64b354541d77e.', '64b354541d77f.', 1020),
(15, '64b354e4a3d65.jpg', '64b354e4a3d68.', '64b354e4a3d69.', '64b354e4a3d6a.', 1021),
(16, '64b3558664385.jpg', '64b355866438a.jpg', '64b355866438b.jpg', '64b355866438d.jpg', 1022),
(17, '64b355b36c67d.jpg', '64b355b36c681.jpg', '64b355b36c682.jpg', '64b355b36c684.', 1023),
(18, '64b355ffc7bc6.jpg', '64b355ffc7bca.jpg', '64b355ffc7bcb.jpg', '64b355ffc7bcc.jpg', 1024),
(19, '64b35675d76b0.jpg', '64b35675d76b3.jpg', '64b35675d76b4.', '64b35675d76b5.', 1025),
(20, '64b356fceaefc.jpg', '64b356fceaeff.', '64b356fceaf00.', '64b356fceaf01.', 1026),
(21, '64b357493b590.jpg', '64b357493b593.jpg', '64b357493b595.jpg', '64b357493b596.', 1027),
(22, '64b357accf3b4.jpg', '64b357accf3b7.jpg', '64b357accf3b8.jpg', '64b357accf3b9.jpg', 1028),
(23, '64b35b7291468.jpg', '64b35b729146c.', '64b35b729146d.', '64b35b729146e.', 1029),
(24, '64b35bde72e2f.jpg', '64b35bde72e32.', '64b35bde72e33.', '64b35bde72e34.', 1030),
(25, '64b35c4e16c55.jpg', '64b35c4e16c5a.jpg', '64b35c4e16c5b.', '64b35c4e16c5d.', 1031),
(26, '64b35cf94bb9b.jpg', '64b35cf94bb9e.jpg', '64b35cf94bb9f.', '64b35cf94bba0.', 1032),
(27, '64b35d1859ab1.jpg', '64b35d1859ab5.jpg', '64b35d1859ab6.', '64b35d1859ab7.', 1032),
(28, '64b35d3a131da.jpg', '64b35d3a131df.jpg', '64b35d3a131e1.', '64b35d3a131e2.', 1033),
(29, '64b35dcadb3d5.jpg', '64b35dcadb3d9.', '64b35dcadb3db.', '64b35dcadb3dc.', 1034),
(30, '64b35dfeb57d8.jpg', '64b35dfeb57db.jpg', '64b35dfeb57dc.', '64b35dfeb57dd.', 1035),
(31, '64b35e67efd05.jpg', '64b35e67efd09.', '64b35e67efd0b.', '64b35e67efd0c.', 1036),
(32, '64b35e88b6a9a.jpg', '64b35e88b6a9c.', '64b35e88b6a9d.', '64b35e88b6a9e.', 1037),
(33, '64b35f17db43e.jpg', '64b35f17db447.jpg', '64b35f17db44b.', '64b35f17db44e.', 1038),
(34, '64b35f4eb9e2f.jpg', '64b35f4eb9e34.', '64b35f4eb9e35.', '64b35f4eb9e36.', 1039),
(35, '64b35fa7de362.jpg', '64b35fa7de365.jpg', '64b35fa7de366.jpg', '64b35fa7de367.jpg', 1040),
(36, '64b35fdbc963b.jpg', '64b35fdbc963e.jpg', '64b35fdbc963f.jpg', '64b35fdbc9640.jpg', 1041),
(37, '64b36096713ca.jpg', '64b36096713cd.jpg', '64b36096713ce.jpg', '64b36096713cf.', 1042),
(38, '64b360aa513db.jpg', '64b360aa513e8.jpg', '64b360aa513eb.jpg', '64b360aa513ef.jpg', 1043),
(39, '64b3614e373dd.jpg', '64b3614e373e1.', '64b3614e373e2.', '64b3614e373e3.', 1044),
(40, '64b3618244892.jpg', '64b3618244895.jpg', '64b3618244897.', '64b3618244898.', 1045),
(41, '64b361f569ff7.jpg', '64b361f569ffb.jpg', '64b361f569ffc.', '64b361f569ffd.', 1046),
(42, '64b36207ac57e.jpg', '64b36207ac580.', '64b36207ac581.', '64b36207ac582.', 1047),
(43, '64b362b3645a7.jpg', '64b362b3645aa.jpg', '64b362b3645ab.jpg', '64b362b3645ac.jpg', 1048),
(44, '64b36322ab026.jpg', '64b36322ab028.jpg', '64b36322ab029.jpg', '64b36322ab02a.jpg', 1049),
(45, '64b363a358b37.jpg', '64b363a358b3a.jpg', '64b363a358b3b.jpg', '64b363a358b3c.', 1050),
(46, '64b363c9c0e99.jpg', '64b363c9c0e9c.jpg', '64b363c9c0e9d.jpg', '64b363c9c0e9e.jpg', 1051),
(47, '64b3647ada44e.jpg', '64b3647ada451.jpg', '64b3647ada452.jpg', '64b3647ada453.jpg', 1052),
(48, '64b364b951106.jpg', '64b364b951108.jpg', '64b364b951109.jpg', '64b364b95110a.jpg', 1053),
(49, '64b365302c7e0.jpg', '64b365302c7e2.jpg', '64b365302c7e3.jpg', '64b365302c7e4.jpg', 1054);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `product_detail_descriptions`
--

CREATE TABLE `product_detail_descriptions` (
  `description_id` int(11) NOT NULL,
  `product_id` int(11) NOT NULL,
  `description` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `product_detail_descriptions`
--

INSERT INTO `product_detail_descriptions` (`description_id`, `product_id`, `description`) VALUES
(1, 1008, 'Ngay từ tên gọi đã mang đến cảm giác mát lành bởi biển xanh, cát vàng và những cơn gió biển tươi mới. Từ nguồn cảm hứng ấy, BST Coastal hình thành với mong muốn gói trọn cảnh biển tươi đẹp vào không gian của bạn. Ngôn ngữ thiết kế của Coastal là sự giao thoa giữa những đường nét mềm mại, tự nhiên phản ánh vùng duyên hải hoang sơ, phóng khoáng; cùng sự kết hợp vật liệu và màu sắc hòa quyện giữa tông xanh tươi mát của chất vải mềm mại và màu nâu ấm áp từ gỗ vững chắc. Tất cả những điểm tương phản ấy lại hợp nhất tạo nên một tổng thể tràn đầy nguồn năng lượng tích cực, như biển rộng đầy những bí ẩn và đất liền thân thuộc giao nhau tạo nên vùng duyên hải xinh đẹp.'),
(2, 1009, 'Ngay từ tên gọi đã mang đến cảm giác mát lành bởi biển xanh, cát vàng và những cơn gió biển tươi mới. Từ nguồn cảm hứng ấy, BST Coastal hình thành với mong muốn gói trọn cảnh biển tươi đẹp vào không gian của bạn. Ngôn ngữ thiết kế của Coastal là sự giao thoa giữa những đường nét mềm mại, tự nhiên phản ánh vùng duyên hải hoang sơ, phóng khoáng; cùng sự kết hợp vật liệu và màu sắc hòa quyện giữa tông xanh tươi mát của chất vải mềm mại và màu nâu ấm áp từ gỗ vững chắc. Tất cả những điểm tương phản ấy lại hợp nhất tạo nên một tổng thể tràn đầy nguồn năng lượng tích cực, như biển rộng đầy những bí ẩn và đất liền thân thuộc giao nhau tạo nên vùng duyên hải xinh đẹp.'),
(3, 1010, 'Ngay từ tên gọi đã mang đến cảm giác mát lành bởi biển xanh, cát vàng và những cơn gió biển tươi mới. Từ nguồn cảm hứng ấy, BST Coastal hình thành với mong muốn gói trọn cảnh biển tươi đẹp vào không gian của bạn. Ngôn ngữ thiết kế của Coastal là sự giao thoa giữa những đường nét mềm mại, tự nhiên phản ánh vùng duyên hải hoang sơ, phóng khoáng; cùng sự kết hợp vật liệu và màu sắc hòa quyện giữa tông xanh tươi mát của chất vải mềm mại và màu nâu ấm áp từ gỗ vững chắc. Tất cả những điểm tương phản ấy lại hợp nhất tạo nên một tổng thể tràn đầy nguồn năng lượng tích cực, như biển rộng đầy những bí ẩn và đất liền thân thuộc giao nhau tạo nên vùng duyên hải xinh đẹp.'),
(4, 1011, 'Sofa 3 chỗ ONA HIM da đen với cảm hứng bắt nguồn từ sự lịch lãm của các quý ông hiện đại. Những giao thoa về xúc chạm tinh tế được khơi nguồn khi chạm nhẹ lên bề mặt lớp da phủ cao cấp. Chân gỗ oak cùng đường nét bo tròn và thu nhỏ dần xuống phía dưới, tạo nên một thiết kế tinh xảo mà đầy vững chắc. Với những tỉ lệ hoàn hảo cùng các đường may tỉ mỉ, sofa ONA HIM giúp không gian căn hộ toát lên vẻ sang trọng và đẳng cấp.'),
(5, 1012, 'Sofa 3 chỗ từ bộ sưu tập Osaka mang nét hiện đại và thơ mộng của Nhật Bản, tạo nên một không gian sống độc đáo đầy sang trọng. Sản phẩm có phần chân bằng inox màu gold chắc chắn, phần nệm được bọc vải và có thể tháo rời được. Sofa 3 chỗ Osaka mẫu 1 vải 29 không chỉ mang đến thiết kế tinh tế, sang trọng mà còn tạo cho người ngồi cảm giác thoải mái, dễ chịu.'),
(6, 1013, 'Sự đơn giản, tinh tế, sang trọng và không kém phần nổi bật của chiếc Sofa mang dòng máu bất diệt Scandivian này với lần lượt các phiên bản màu từ tối tới sáng bật Pastel sẽ mang lại các sắc màu không thể lẫn vào đâu và đa dạng cho từng không gian sống nhà bạn. Thiết kế vuông vức, thanh mảnh nhẹ nhàng là sự pha trộn giữa vững chãi và nhẹ nhàng là tất cả những yếu tố thiết yếu hội tụ ở chiếc sofa này.'),
(7, 1014, 'Sofa Jadora là sản phẩm được thiết kế và sản xuất bởi Nhà Xinh. Với kiểu dáng rộng rãi cùng đệm ngồi êm ái, Jadora hứa hẹn sẽ mang đến cho người dùng trải nghiệm thư thái nhất. Trong phiên bản mới, Jadora khoác lên mình màu sắc trang nhã, hiện đại với sự kết hợp của các tông màu mới mẻ sẽ là điểm nhấn tuyệt vời cho tổ ấm của bạn. Thiết kế tựa như một chiếc giường ngủ, sofa Jadora rất phù hợp để bạn ngồi đọc sách hoặc ngả lưng thư giãn. Sản phẩm dễ dàng phối hợp với nhiều thiết kế khác như bàn nước hoặc bàn bên để tạo nên không gian sống độc đáo.'),
(8, 1015, 'Một chiếc sofa nhỏ gọn cho căn hộ của bạn nhưng vẫn mang vẻ hiện đại. Phần tựa lưng của sofa moon với đường nét bo tròn, nghiêng hỗ trợ tối đa cho việc nghỉ ngơi thư giãn. Chất liệu vải bông mềm tạo sự thoải mái khi ngồi kết hợp với màu sắc pastel nhẹ nhàng sẽ là giải pháp lựa chọn tối ưu dành cho không gian nội thất căn hộ có diện tích khiêm tốn.   '),
(9, 1026, 'Đèn bàn, đèn trang trí phòng khách, phòng ngủ từ lâu không chỉ đơn thuần là giải pháp cung cấp ánh sáng, mà còn là một vật dụng nội thất trang trí giúp gia tăng tính thẩm mỹ, phong cách trong không gian của gia đình bạn. Tại Nhà Xinh có đa dạng các mẫu đèn trang trí đẹp, kiểu dáng hiện đại để bạn tham khảo.'),
(10, 1027, 'Đèn bàn, đèn trang trí phòng khách, phòng ngủ từ lâu không chỉ đơn thuần là giải pháp cung cấp ánh sáng, mà còn là một vật dụng nội thất trang trí giúp gia tăng tính thẩm mỹ, phong cách trong không gian của gia đình bạn. Tại Nhà Xinh có đa dạng các mẫu đèn trang trí đẹp, kiểu dáng hiện đại để bạn tham khảo.'),
(11, 1028, 'Đèn bàn, đèn trang trí phòng khách, phòng ngủ từ lâu không chỉ đơn thuần là giải pháp cung cấp ánh sáng, mà còn là một vật dụng nội thất trang trí giúp gia tăng tính thẩm mỹ, phong cách trong không gian của gia đình bạn. Tại Nhà Xinh có đa dạng các mẫu đèn trang trí đẹp, kiểu dáng hiện đại để bạn tham khảo.'),
(12, 1029, 'Đèn bàn, đèn trang trí phòng khách, phòng ngủ từ lâu không chỉ đơn thuần là giải pháp cung cấp ánh sáng, mà còn là một vật dụng nội thất trang trí giúp gia tăng tính thẩm mỹ, phong cách trong không gian của gia đình bạn. Tại Nhà Xinh có đa dạng các mẫu đèn trang trí đẹp, kiểu dáng hiện đại để bạn tham khảo.'),
(13, 1030, 'Đèn bàn, đèn trang trí phòng khách, phòng ngủ từ lâu không chỉ đơn thuần là giải pháp cung cấp ánh sáng, mà còn là một vật dụng nội thất trang trí giúp gia tăng tính thẩm mỹ, phong cách trong không gian của gia đình bạn. Tại Nhà Xinh có đa dạng các mẫu đèn trang trí đẹp, kiểu dáng hiện đại để bạn tham khảo.'),
(14, 1031, 'Kệ sách Cabo được cấu tạo từ khung kim loại màu gold sang trọng, sự kết hợp giữa gỗ,và kim loại vàng bóng. Đặc biệt những nét bo viền trong từng chi tiết tỉ mỉ khéo léo tạo ra điểm nhấn riêng biệt độc đáo tạo không gian trưng bày đậm chất văn học cho những ai yêu thích đọc sách là điểm nhấn sáng giá cho không gian làm việc của bạn.'),
(15, 1032, 'Kệ sách Maxine tạo không gian trưng bày đậm chất văn học cho những ai yêu thích đọc sách. Màu sắc nâu trầm, khung kim loại đồng giúp tạo điểm nhấn cho không gian làm việc của bạn. Maxine – Nét nâu trầm sang trọng Maxine, mang thiết kế vượt thời gian, gửi gắm và gói gọn lại những nét đẹp của thiên nhiên và con người nhưng vẫn đầy tính ứng dụng cao trong suốt hành trình phiêu lưu của nhà thiết kế người Pháp Dominique Moal. Bộ sưu tập nổi bật với màu sắc nâu trầm sang trọng, là sự kết hợp giữa gỗ, da bò và kim loại vàng bóng. Đặc biệt trên mỗi sản phẩm, những nét bo viên, chi tiết kết nối được sử dụng kéo léo tạo ra điểm nhất rất riêng cho bộ sưu tập. Maxine thể hiện nét trầm tư, thư giãn để tận hưởng không gian sống trong nhịp sống hiện đại. Sản phẩm thuộc BST Maxine được sản xuất tại Việt Nam.'),
(16, 1033, 'Là dòng sản phẩm cao cấp nhập khẩu từ Pháp. Kệ sách Brem sử dụng vật liệu MFC cao cấp chống ẩm, đạt chuẩn an toàn châu Âu, hoàn thiện sơn lacquer. Phần kệ kính cường lực 10mm có thể điều chỉnh độ cao tùy ý. Có thể kết hợp với đồ nội thất khác tạo thành vách ngăn mở cho không gian.'),
(17, 1038, 'Tủ áo Maxine chứa đựng đầy đủ công năng tối ưu cho việc cất trữ quần áo bằng việc bố trí sắp xếp hợp lý các ngăn tủ. Những chi tiết về phụ kiện cao cấp giúp cho việc thao tác nhẹ nhàng. Bề ngoài, tủ được thiết kế duyên dáng và thu hút với sắc nâu trầm và kim loại đồng. Maxine – Nét nâu trầm sang trọng Maxine, mang thiết kế vượt thời gian, gửi gắm và gói gọn lại những nét đẹp của thiên nhiên và con người nhưng vẫn đầy tính ứng dụng cao trong suốt hành trình phiêu lưu của nhà thiết kế người Pháp Dominique Moal. Bộ sưu tập nổi bật với màu sắc nâu trầm sang trọng, là sự kết hợp giữa gỗ, da bò và kim loại vàng bóng. Đặc biệt trên mỗi sản phẩm, những nét bo viên, chi tiết kết nối được sử dụng kéo léo tạo ra điểm nhất rất riêng cho bộ sưu tập. Maxine thể hiện nét trầm tư, thư giãn để tận hưởng không gian sống trong nhịp sống hiện đại. Sản phẩm thuộc BST Maxine được sản xuất tại Việt Nam.'),
(18, 1039, 'Mẫu tủ áo hiện đại của Nhà Xinh với thiết kế giản đơn, tối đa tiện ích bằng nhiều ngăn kéo và khoảng trống để cất trữ quần áo và đồ đạc.'),
(19, 1040, 'Mẫu tủ áo hiện đại của Nhà Xinh với thiết kế giản đơn, tối đa tiện ích bằng nhiều ngăn kéo và khoảng trống để cất trữ quần áo và đồ đạc.'),
(20, 1041, 'Là sự kết hợp của màu trắng tinh khôi với màu gỗ ấm áp trên nền những đường nét thiết kế hiện đại, trang nhã. Harmony được đánh giá rất cao cả về kiểu dáng và công năng, đó sẽ là niềm tự hào của gia chủ khi khách đến thăm nhà.'),
(21, 1042, 'Là sự kết hợp của màu trắng tinh khôi với màu gỗ ấm áp trên nền những đường nét thiết kế hiện đại, trang nhã. Harmony được đánh giá rất cao cả về kiểu dáng và công năng, đó sẽ là niềm tự hào của gia chủ khi khách đến thăm nhà.'),
(22, 1043, 'Là sự kết hợp của màu trắng tinh khôi với màu gỗ ấm áp trên nền những đường nét thiết kế hiện đại, trang nhã. Harmony được đánh giá rất cao cả về kiểu dáng và công năng, đó sẽ là niềm tự hào của gia chủ khi khách đến thăm nhà.'),
(23, 1044, 'Ghế quầy bar Cognac được nhập khẩu từ Pháp có màu sắc sang trọng. Sản phẩm được làm bằng khung kim loại chắc chắn bọc lớp da nâu tự nhiên cho vẻ đẹp hoàn hảo.'),
(24, 1045, 'Ghế quầy bar Cognac được nhập khẩu từ Pháp có màu sắc sang trọng. Sản phẩm được làm bằng khung kim loại chắc chắn bọc lớp da nâu tự nhiên cho vẻ đẹp hoàn hảo.'),
(25, 1046, 'Ghế quầy bar Cognac được nhập khẩu từ Pháp có màu sắc sang trọng. Sản phẩm được làm bằng khung kim loại chắc chắn bọc lớp da nâu tự nhiên cho vẻ đẹp hoàn hảo.'),
(26, 1047, 'Ghế quầy bar Cognac được nhập khẩu từ Pháp có màu sắc sang trọng. Sản phẩm được làm bằng khung kim loại chắc chắn bọc lớp da nâu tự nhiên cho vẻ đẹp hoàn hảo.'),
(27, 1048, 'Ghế quầy bar Boheme nhập khẩu từ Ý có màu sắc sang trọng. Sản phẩm được thiết kế đơn giản nhưng không kém phần tinh tế, hiện đại. Ghế được làm bằng khung kim loại chắc chắn bọc lớp da nâu tự nhiên cho vẻ đẹp hoàn hảo'),
(28, 1049, 'Bàn ăn gỗ Pio được yêu thích với mặt bàn được làm từ chất liệu melamine marble màu trắng sang trọng cùng những vân đá độc đáo. Thiết kế dạng oval và phần chân thon gọn giúp tiết kiệm không gian phòng ăn. Bàn ăn Pio là lựa chọn tối ưu cho những không gian phòng ăn mang đậm phong cách Bắc Âu.'),
(29, 1050, 'Bàn ăn Peak hiện đại với bề mặt Ceramic được nhập khẩu từ Ý. Mặt Ceramic có khả năng chịu nhiệt tốt với họa tiết vân mây tinh xảo tạo cảm giác vừa vững chắc, vừa uyển chuyển. Chân bàn được thiết kế theo hình tam giác với các góc cạnh bo tròn hiện đại cùng những đường nét uốn lượn mềm mại. Bàn ăn Peak với tông màu trắng trang nhã sẽ mang đến một không gian sống sang trọng và đẳng cấp.'),
(30, 1051, 'Bàn ăn Miami được làm chính từ ván MDF chống ẩm cao cấp, phần dưới được làm từ gỗ Sồi trắng nhập khẩu từ Mỹ tạo nên sự chắc chắn cho sản phẩm. Đây là sự lựa chọn hoàn hảo cho không gian phòng ăn mang đậm phong cách Bắc Âu'),
(31, 1052, 'Bàn ăn Miami được làm chính từ ván MDF chống ẩm cao cấp, phần dưới được làm từ gỗ Sồi trắng nhập khẩu từ Mỹ tạo nên sự chắc chắn cho sản phẩm. Đây là sự lựa chọn hoàn hảo cho không gian phòng ăn mang đậm phong cách Bắc Âu.'),
(32, 1053, 'Bàn ăn Jazz được ghép từ những thanh gỗ sồi già tự nhiên. Bề mặt đặc trưng với những đường nứt tét gỗ tự nhiên được xử lý khéo léo, kết hợp với chân sắt sơn tĩnh điện đầy mạnh mẽ sẽ mang lại nét cá tính độc đáo cho gia chủ.'),
(33, 1054, 'Bàn ăn 8 chỗ Porto M2 thiết kế theo xu hướng hiện đại, đó là sự kết hợp tuyệt vời giữa vật liệu gỗ và inox được liên kết một cách tỉ mỉ. Mặt bàn gỗ với công nghệ hiện đại giúp bề mặt sáng bóng tôn thêm vẻ đẹp quyền quý, sang trọng.');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `reviews`
--

CREATE TABLE `reviews` (
  `review_id` int(11) NOT NULL,
  `product_id` int(11) DEFAULT NULL,
  `content` text DEFAULT NULL,
  `rating` int(11) DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  `comment_date` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `reviews`
--

INSERT INTO `reviews` (`review_id`, `product_id`, `content`, `rating`, `user_name`, `comment_date`) VALUES
(1, 1008, 'san pham tot', 5, 'shop tc', '2023-07-15');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `sliders`
--

CREATE TABLE `sliders` (
  `slider_id` int(11) NOT NULL,
  `img_1` varchar(255) NOT NULL,
  `img_2` varchar(255) NOT NULL,
  `img_3` varchar(255) NOT NULL,
  `img_4` varchar(255) NOT NULL,
  `img_5` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `sliders`
--

INSERT INTO `sliders` (`slider_id`, `img_1`, `img_2`, `img_3`, `img_4`, `img_5`) VALUES
(1, '64b29fabb9827.jpg', '64b29fabb9834.jpg', '64b29fabb9838.jpg', '64b29fabb983b.jpg', '64b29fabb983f.jpg');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `specifications`
--

CREATE TABLE `specifications` (
  `specification_id` int(11) NOT NULL,
  `product_id` int(11) NOT NULL,
  `width` varchar(255) NOT NULL,
  `length` varchar(255) NOT NULL,
  `height` varchar(255) NOT NULL,
  `number_of_drawers` varchar(255) NOT NULL,
  `type` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `specifications`
--

INSERT INTO `specifications` (`specification_id`, `product_id`, `width`, `length`, `height`, `number_of_drawers`, `type`) VALUES
(1, 1008, '850 mm', '2220 mm', '720 mm', '3 chỗ', 'Khung gỗ Ash - nệm bọc vải'),
(2, 1009, '880 mm', '2060 mm', '680 mm', '3 chỗ', 'Chân gỗ - bọc vải'),
(3, 1010, '900 mm', '2100 mm', '740 mm', '3 chỗ', 'Khung gỗ bọc vải'),
(4, 1011, '990 mm', '2000 mm', '700 mm', '3 chỗ', 'Gỗ Oak - da bò tự nhiên cao cấp'),
(5, 1012, '750 mm', '2060 mm', '820/400 mm', '3 chỗ', 'Chân Inox màu gold, tay gỗ, bọc vải, nệm ngồi tháo rời'),
(6, 1013, '800 mm', '2400 mm', '850 mm', '3 chỗ', 'Chân kim loại sơn, nệm bọc vải cam, nệm ngồi & lưng rời'),
(7, 1014, '800 mm', '2400 mm', '850 mm', '2.5 chỗ', 'Khung gỗ - Nệm bọc vải 2 màu - 5 gối'),
(8, 1015, '700 mm', '1500 mm', '750 mm', '2 chỗ', 'Khung gỗ bọc vải màu hồng -chất liệu vải sợi bông mềm êm'),
(9, 1016, '1000 mm', '1000 mm', '1080 mm', '1 chỗ', 'Khung gỗ cao su - Bọc da cao cấp'),
(10, 1017, '1000 mm', '1000 mm', '1080 mm', '1 chỗ', 'Khung gỗ cao su - Bọc da cao cấp'),
(11, 1018, '1000 mm', '1000 mm', '1080 mm', '1 chỗ', 'Khung gỗ cao su - Bọc da cao cấp'),
(12, 1019, '860 mm', '1060 mm', '970 mm', '1 chỗ', 'Khung gỗ cao su bọc da bò cao cao cấp'),
(13, 1020, '960 mm', '1060 mm', '1130 mm', '1 chỗ', 'Khung gỗ cao su bọc da bò cao cao cấp'),
(14, 1021, '960 mm', '1060 mm', '1130 mm', '1 chỗ', 'Khung gỗ cao su bọc da bò cao cao cấp'),
(15, 1022, '2900 mm', '2000 mm', '', '', 'sợi tổng hợp'),
(16, 1023, '2900 mm', '2000 mm', '', '', 'sợi tổng hợp'),
(17, 1024, '2900 mm', '2000 mm', '', '', 'sợi tổng hợp'),
(18, 1025, '1500 mm', '2400 mm', '', '', 'sợi tổng hợp'),
(19, 1026, '210 mm', '360 mm', '575 mm', '', 'Chụp vải - chân đồng cổ, thủy tinh'),
(20, 1027, '210 mm', '360 mm', '575 mm', '', 'Thủy tinh pha lê, hoàn thiện bằng niken, hình dạng 90% polyester, 10% bông'),
(21, 1028, '210 mm', '360 mm', '575 mm', '', 'Đế đèn hoàn thiện bằng đồng cổ - thủy tinh pha lê'),
(22, 1029, '170 mm', '410 mm', '630 mm', '', 'Metal'),
(23, 1030, '170 mm', '410 mm', '630 mm', '', 'Metal'),
(24, 1031, '400 mm', '1400 mm', '1760 mm', '', 'Cabo'),
(25, 1033, '430 mm', '800mm', '2000 mm', '', 'MFC sơn lacquer'),
(26, 1034, '600 mm', '3430 mm', '2350 mm', '', 'Gỗ óc chó (Walnut), gỗ xà cừ (Mahogany), gỗ ép, ván lạng óc chó nhân tạo (Walnut Recon)'),
(27, 1035, '600 mm', '2275/4400/2275 mm', '2450 mm', '', 'Gỗ óc chó (Walnut), gỗ ép và ván lạng óc chó nhân tạo (Walnut recon)'),
(28, 1036, '600 mm', '250 mm', '2200 mm', '4 buồng', 'Gỗ xà cừ (Mahogany), MDF'),
(29, 1037, '600 mm', '2500/3000 mm', '2200 mm', '', 'Gỗ óc chó (Walnut), gỗ ép và ván lạng Sồi nhân tạo (Oak recon)'),
(30, 1038, '600 mm', '1200 mm', '2200 mm', '2 buồng', 'Khung gỗ Okumi, MDF veneer recon Walnut, chân inox mạ PVD màu gold'),
(31, 1039, '600 mm', '1600 mm', '2200 mm', '3 buồng', 'MFC chống ẩm -phụ kiện Hafele'),
(32, 1040, '600 mm', '1600 mm', '2200 mm', '3 buồng', 'MFC chống ẩm -phụ kiện Hafele'),
(33, 1041, '630 mm', '990 mm', '1900 mm', '2 buồng', 'Harmony'),
(34, 1042, '600 mm', '1990 mm', '2000 mm', '3 buồng', 'Thùng MFC chống ẩm - mặt MDF Acrylic Parc50'),
(35, 1043, '750 mm', '4600 mm', '2400 mm', '', 'Gỗ sồi - MDF Veneer sồi - Khung nhôm kính màu xám khói - Cánh cửa lùa hai bên'),
(36, 1044, '330 mm', '330 mm', '750 mm', '1 chỗ', 'Khung kim loại- Da cao cấp'),
(37, 1045, '480 mm', '480 mm', '1000 mm', '1 chỗ', 'Kim loại không rỉ, da công nghiệp'),
(38, 1046, '480 mm', '480 mm', '1000 mm', '1 chỗ', 'Kim loại không rỉ, da công nghiệp'),
(39, 1047, '500 mm', '500 mm', '750/900 mm', '1 chỗ', 'Khung gỗ sồi đặc tự nhiên nhập khẩu từ Mỹ - da Ý tự nhiên cao cấp'),
(40, 1048, '400 mm', '400 mm', '750/900 mm', '1 chỗ', 'Khung kim loại - bọc da tổng hợp cao cấp'),
(41, 1049, '1000 mm', '1800 mm', '750 mm', '', 'Gỗ dẻ gai, mặt melamine vân cẩm thạch'),
(42, 1050, '1000 mm', '1800 mm', '750 mm', '', 'Chân composite không thấm nước Mặt bàn Ceramic nhập khẩu Ý chịu nhiệt'),
(43, 1051, '800 mm', '1700 mm', '760 mm', '', 'Gỗ sồi tự nhiên + MDF chống ẩm'),
(44, 1052, '800 mm', '1200 mm', '760 mm', '', 'Gỗ sồi tự nhiên + MDF chống ẩm'),
(45, 1053, '900 mm', '1600 mm', '760 mm', '', 'Jazz'),
(46, 1054, '900 mm', '1600 mm', '760 mm', '', 'Chân inox màu gold -cạnh bàn ốp inox màu gold -Mặt bàn Gỗ+mdf veneer Oak');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `users`
--

CREATE TABLE `users` (
  `user_id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `login_info` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `users`
--

INSERT INTO `users` (`user_id`, `name`, `email`, `password`, `login_info`) VALUES
(1, 'cong', 'tcshopfd@gmail.com', '$2y$10$CENcPpTPZIkND56rxdxDjeAVhgEBfZ9hOPgvY1MffRCuSiMAx.adC', 457436);

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `carts`
--
ALTER TABLE `carts`
  ADD PRIMARY KEY (`cart_id`),
  ADD KEY `fk_cart_product` (`product_id`);

--
-- Chỉ mục cho bảng `cart_payment`
--
ALTER TABLE `cart_payment`
  ADD PRIMARY KEY (`cart_id`),
  ADD KEY `fk_cart_payment` (`product_id`);

--
-- Chỉ mục cho bảng `categories`
--
ALTER TABLE `categories`
  ADD PRIMARY KEY (`category_id`);

--
-- Chỉ mục cho bảng `notifications`
--
ALTER TABLE `notifications`
  ADD PRIMARY KEY (`notification_id`);

--
-- Chỉ mục cho bảng `payments`
--
ALTER TABLE `payments`
  ADD PRIMARY KEY (`payment_id`);

--
-- Chỉ mục cho bảng `products`
--
ALTER TABLE `products`
  ADD PRIMARY KEY (`product_id`),
  ADD KEY `category_id` (`category_id`);

--
-- Chỉ mục cho bảng `product_details`
--
ALTER TABLE `product_details`
  ADD PRIMARY KEY (`product_detail_id`),
  ADD KEY `fk_product_details` (`product_id`);

--
-- Chỉ mục cho bảng `product_detail_descriptions`
--
ALTER TABLE `product_detail_descriptions`
  ADD PRIMARY KEY (`description_id`),
  ADD KEY `fk_product_dt_des` (`product_id`);

--
-- Chỉ mục cho bảng `reviews`
--
ALTER TABLE `reviews`
  ADD PRIMARY KEY (`review_id`),
  ADD KEY `product_id` (`product_id`);

--
-- Chỉ mục cho bảng `sliders`
--
ALTER TABLE `sliders`
  ADD PRIMARY KEY (`slider_id`);

--
-- Chỉ mục cho bảng `specifications`
--
ALTER TABLE `specifications`
  ADD PRIMARY KEY (`specification_id`),
  ADD KEY `fk_product_id` (`product_id`);

--
-- Chỉ mục cho bảng `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`user_id`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `carts`
--
ALTER TABLE `carts`
  MODIFY `cart_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT cho bảng `cart_payment`
--
ALTER TABLE `cart_payment`
  MODIFY `cart_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=61;

--
-- AUTO_INCREMENT cho bảng `categories`
--
ALTER TABLE `categories`
  MODIFY `category_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT cho bảng `notifications`
--
ALTER TABLE `notifications`
  MODIFY `notification_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT cho bảng `payments`
--
ALTER TABLE `payments`
  MODIFY `payment_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT cho bảng `products`
--
ALTER TABLE `products`
  MODIFY `product_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1055;

--
-- AUTO_INCREMENT cho bảng `product_details`
--
ALTER TABLE `product_details`
  MODIFY `product_detail_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=50;

--
-- AUTO_INCREMENT cho bảng `product_detail_descriptions`
--
ALTER TABLE `product_detail_descriptions`
  MODIFY `description_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=34;

--
-- AUTO_INCREMENT cho bảng `reviews`
--
ALTER TABLE `reviews`
  MODIFY `review_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT cho bảng `sliders`
--
ALTER TABLE `sliders`
  MODIFY `slider_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT cho bảng `specifications`
--
ALTER TABLE `specifications`
  MODIFY `specification_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=47;

--
-- AUTO_INCREMENT cho bảng `users`
--
ALTER TABLE `users`
  MODIFY `user_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- Các ràng buộc cho các bảng đã đổ
--

--
-- Các ràng buộc cho bảng `carts`
--
ALTER TABLE `carts`
  ADD CONSTRAINT `fk_cart_product` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`);

--
-- Các ràng buộc cho bảng `cart_payment`
--
ALTER TABLE `cart_payment`
  ADD CONSTRAINT `fk_cart_payment` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`);

--
-- Các ràng buộc cho bảng `products`
--
ALTER TABLE `products`
  ADD CONSTRAINT `products_ibfk_1` FOREIGN KEY (`category_id`) REFERENCES `categories` (`category_id`);

--
-- Các ràng buộc cho bảng `product_details`
--
ALTER TABLE `product_details`
  ADD CONSTRAINT `fk_product_details` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`);

--
-- Các ràng buộc cho bảng `product_detail_descriptions`
--
ALTER TABLE `product_detail_descriptions`
  ADD CONSTRAINT `fk_product_dt_des` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`);

--
-- Các ràng buộc cho bảng `reviews`
--
ALTER TABLE `reviews`
  ADD CONSTRAINT `reviews_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`);

--
-- Các ràng buộc cho bảng `specifications`
--
ALTER TABLE `specifications`
  ADD CONSTRAINT `fk_product_id` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
