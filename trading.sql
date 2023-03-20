/*
 Navicat Premium Data Transfer

 Source Server         : 127.0.0.1
 Source Server Type    : MySQL
 Source Server Version : 80031
 Source Host           : localhost:3306
 Source Schema         : trading

 Target Server Type    : MySQL
 Target Server Version : 80031
 File Encoding         : 65001

 Date: 19/03/2023 10:28:42
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '分类名',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '无描述' COMMENT '分类描述',
  `deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除',
  `gmt_create` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of category
-- ----------------------------
INSERT INTO `category` VALUES (2, '书籍', '专业书、小说、考试资料等', 0, '2023-03-10 21:08:18', NULL);
INSERT INTO `category` VALUES (3, '电子产品', '四级听力耳机、键盘、鼠标等', 0, '2023-03-10 21:08:53', NULL);
INSERT INTO `category` VALUES (4, '桌椅', '电竞椅、桌子、靠椅、可收纳桌', 0, '2023-03-10 21:09:35', NULL);

-- ----------------------------
-- Table structure for feedback
-- ----------------------------
DROP TABLE IF EXISTS `feedback`;
CREATE TABLE `feedback`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `context` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `feedback_FK`(`user_id`) USING BTREE,
  CONSTRAINT `feedback_FK` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of feedback
-- ----------------------------
INSERT INTO `feedback` VALUES (1, 2, 'gdrg', 0);

-- ----------------------------
-- Table structure for good
-- ----------------------------
DROP TABLE IF EXISTS `good`;
CREATE TABLE `good`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商品名',
  `profile_display` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '事物展示图',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '无描述' COMMENT '商品描述',
  `count` int NOT NULL DEFAULT 1 COMMENT '商品数量',
  `category_id` int NOT NULL DEFAULT 0 COMMENT '产品类别',
  `user_id` int NOT NULL COMMENT '发布者id',
  `deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除',
  `gmt_create` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `goods_FK`(`category_id`) USING BTREE,
  INDEX `goods_FK_1`(`user_id`) USING BTREE,
  CONSTRAINT `goods_FK` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `goods_FK_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of good
-- ----------------------------
INSERT INTO `good` VALUES (1, 'fsefsefse', NULL, 'sefs', 0, 2, 1, 0, '2023-03-10 23:26:23', NULL);
INSERT INTO `good` VALUES (2, 'fsefsefse', NULL, 'sefs', 0, 2, 1, 0, '2023-03-10 23:26:23', NULL);
INSERT INTO `good` VALUES (3, '就，接口，接口，', NULL, '解开了。，就，kjk', 0, 3, 1, 0, '2023-03-13 22:31:49', NULL);
INSERT INTO `good` VALUES (4, '就，接口，接口，', NULL, '解开了。，就，kjk', 0, 3, 1, 0, '2023-03-13 22:31:49', NULL);
INSERT INTO `good` VALUES (5, '就，接口，接口，', NULL, '解开了。，就，kjk', 0, 3, 1, 0, '2023-03-13 22:31:53', NULL);
INSERT INTO `good` VALUES (6, '就，接口，接口，', NULL, '解开了。，就，kjk', 0, 3, 1, 0, '2023-03-13 22:31:53', NULL);
INSERT INTO `good` VALUES (7, '更多让人很感动让更多人', NULL, '二十分是否瑟夫', 0, 2, 1, 0, '2023-03-13 22:32:20', NULL);
INSERT INTO `good` VALUES (8, '更多让人很感动让更多人', NULL, '二十分是否瑟夫', 0, 2, 1, 0, '2023-03-13 22:32:20', NULL);
INSERT INTO `good` VALUES (9, 'sefes', NULL, 'fsefsefawd', 0, 4, 1, 0, '2023-03-13 23:49:22', NULL);
INSERT INTO `good` VALUES (10, 'sefes', NULL, 'fsefsefawd', 0, 4, 1, 0, '2023-03-13 23:49:22', NULL);
INSERT INTO `good` VALUES (11, 'awdawd', NULL, 'adadawd', 0, 3, 1, 0, '2023-03-13 23:56:56', NULL);
INSERT INTO `good` VALUES (12, 'awdawd', NULL, 'adadawd', 0, 3, 1, 0, '2023-03-13 23:56:56', NULL);
INSERT INTO `good` VALUES (13, '二甲氨基我低洼地hi哦阿瓦达', NULL, 'sfsfseffsfesffthft', 6, 3, 1, 0, '2023-03-14 00:01:07', NULL);
INSERT INTO `good` VALUES (14, '二甲氨基我低洼地hi哦阿瓦达', NULL, 'sfsfseffsfesffthft', 0, 3, 1, 0, '2023-03-14 00:01:07', NULL);
INSERT INTO `good` VALUES (16, 'd\'r\'g\'d\'g', NULL, 'drgdg多个但如果', 0, 4, 1, 0, '2023-03-14 00:02:56', NULL);
INSERT INTO `good` VALUES (17, '二甲氨基我低洼地hi哦阿瓦达', NULL, 'sfsfseffsfesffthft', 17, 3, 1, 0, '2023-03-14 15:07:46', NULL);
INSERT INTO `good` VALUES (19, '一本好书', NULL, '这里可以写一些描述', 1, 2, 2, 0, '2023-03-15 15:44:49', NULL);

-- ----------------------------
-- Table structure for luck_permission
-- ----------------------------
DROP TABLE IF EXISTS `luck_permission`;
CREATE TABLE `luck_permission`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `method` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `route` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `description` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '无描述',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `luck_permission_un_m_r`(`method`, `route`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 78 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of luck_permission
-- ----------------------------
INSERT INTO `luck_permission` VALUES (1, 'OPTIONS', '/luck-role-permissions', '无描述');
INSERT INTO `luck_permission` VALUES (2, 'DELETE', '/luck-role-permissions', '无描述');
INSERT INTO `luck_permission` VALUES (3, 'POST', '/luck-role-permissions', '无描述');
INSERT INTO `luck_permission` VALUES (4, 'TRACE', '/luck-role-permissions', '无描述');
INSERT INTO `luck_permission` VALUES (5, 'HEAD', '/luck-role-permissions', '无描述');
INSERT INTO `luck_permission` VALUES (6, 'PATCH', '/luck-role-permissions', '无描述');
INSERT INTO `luck_permission` VALUES (7, 'GET', '/luck-role-permissions', '无描述');
INSERT INTO `luck_permission` VALUES (8, 'PUT', '/luck-role-permissions', '无描述');
INSERT INTO `luck_permission` VALUES (10, 'PUT', '/goods', '无描述');
INSERT INTO `luck_permission` VALUES (11, 'HEAD', '/goods', '无描述');
INSERT INTO `luck_permission` VALUES (12, 'PATCH', '/goods', '无描述');
INSERT INTO `luck_permission` VALUES (13, 'DELETE', '/goods', '无描述');
INSERT INTO `luck_permission` VALUES (14, 'GET', '/goods', '无描述');
INSERT INTO `luck_permission` VALUES (15, 'POST', '/goods', '无描述');
INSERT INTO `luck_permission` VALUES (16, 'OPTIONS', '/goods', '无描述');
INSERT INTO `luck_permission` VALUES (17, 'TRACE', '/goods', '无描述');
INSERT INTO `luck_permission` VALUES (18, 'DELETE', '/orders', '用户订单管理接口');
INSERT INTO `luck_permission` VALUES (19, 'OPTIONS', '/orders', '用户订单管理接口');
INSERT INTO `luck_permission` VALUES (20, 'POST', '/orders', '用户订单管理接口');
INSERT INTO `luck_permission` VALUES (21, 'GET', '/orders', '用户订单管理接口');
INSERT INTO `luck_permission` VALUES (23, 'TRACE', '/orders', '用户订单管理接口');
INSERT INTO `luck_permission` VALUES (24, 'HEAD', '/orders', '用户订单管理接口');
INSERT INTO `luck_permission` VALUES (25, 'PATCH', '/orders', '用户订单管理接口');
INSERT INTO `luck_permission` VALUES (26, 'PUT', '/orders', '用户订单管理接口');
INSERT INTO `luck_permission` VALUES (27, 'PATCH', '/admin', '无描述');
INSERT INTO `luck_permission` VALUES (31, 'POST', '/admin', '无描述');
INSERT INTO `luck_permission` VALUES (33, 'OPTIONS', '/admin', '无描述');
INSERT INTO `luck_permission` VALUES (34, 'PUT', '/admin', '无描述');
INSERT INTO `luck_permission` VALUES (35, 'TRACE', '/admin', '无描述');
INSERT INTO `luck_permission` VALUES (38, 'GET', '/admin', '无描述');
INSERT INTO `luck_permission` VALUES (39, 'HEAD', '/admin', '无描述');
INSERT INTO `luck_permission` VALUES (42, 'DELETE', '/admin', '无描述');
INSERT INTO `luck_permission` VALUES (43, 'OPTIONS', '/feedback', '无描述');
INSERT INTO `luck_permission` VALUES (44, 'DELETE', '/feedback', '无描述');
INSERT INTO `luck_permission` VALUES (45, 'POST', '/feedback', '无描述');
INSERT INTO `luck_permission` VALUES (46, 'HEAD', '/feedback', '无描述');
INSERT INTO `luck_permission` VALUES (47, 'PATCH', '/feedback', '无描述');
INSERT INTO `luck_permission` VALUES (48, 'GET', '/feedback', '无描述');
INSERT INTO `luck_permission` VALUES (49, 'PUT', '/feedback', '无描述');
INSERT INTO `luck_permission` VALUES (50, 'TRACE', '/feedback', '无描述');
INSERT INTO `luck_permission` VALUES (51, 'PUT', '/admin/**', '无描述');
INSERT INTO `luck_permission` VALUES (52, 'PATCH', '/admin/**', '无描述');
INSERT INTO `luck_permission` VALUES (53, 'HEAD', '/admin/**', '无描述');
INSERT INTO `luck_permission` VALUES (54, 'DELETE', '/admin/**', '无描述');
INSERT INTO `luck_permission` VALUES (55, 'GET', '/goods/user/*', '无描述');
INSERT INTO `luck_permission` VALUES (56, 'GET', '/admin/**', '无描述');
INSERT INTO `luck_permission` VALUES (57, 'POST', '/admin/**', '无描述');
INSERT INTO `luck_permission` VALUES (58, 'GET', '/user/', '获取用户自己的信息');
INSERT INTO `luck_permission` VALUES (59, 'OPTIONS', '/admin/**', '无描述');
INSERT INTO `luck_permission` VALUES (60, 'TRACE', '/admin/**', '无描述');
INSERT INTO `luck_permission` VALUES (61, 'GET', '/luck-role-permissions/user', '无描述');
INSERT INTO `luck_permission` VALUES (62, 'POST', '/goods/**', '无描述');
INSERT INTO `luck_permission` VALUES (63, 'TRACE', '/goods/**', '无描述');
INSERT INTO `luck_permission` VALUES (64, 'OPTIONS', '/goods/**', '无描述');
INSERT INTO `luck_permission` VALUES (65, 'PUT', '/goods/**', '无描述');
INSERT INTO `luck_permission` VALUES (66, 'DELETE', '/goods/**', '无描述');
INSERT INTO `luck_permission` VALUES (67, 'HEAD', '/goods/**', '无描述');
INSERT INTO `luck_permission` VALUES (68, 'GET', '/goods/**', '无描述');
INSERT INTO `luck_permission` VALUES (69, 'PATCH', '/goods/**', '无描述');
INSERT INTO `luck_permission` VALUES (70, 'POST', '/orders/**', '用户订单管理接口');
INSERT INTO `luck_permission` VALUES (71, 'PATCH', '/orders/**', '用户订单管理接口');
INSERT INTO `luck_permission` VALUES (72, 'HEAD', '/orders/**', '用户订单管理接口');
INSERT INTO `luck_permission` VALUES (73, 'GET', '/orders/**', '用户订单管理接口');
INSERT INTO `luck_permission` VALUES (74, 'DELETE', '/orders/**', '用户订单管理接口');
INSERT INTO `luck_permission` VALUES (75, 'PUT', '/orders/**', '用户订单管理接口');
INSERT INTO `luck_permission` VALUES (76, 'TRACE', '/orders/**', '用户订单管理接口');
INSERT INTO `luck_permission` VALUES (77, 'OPTIONS', '/orders/**', '用户订单管理接口');

-- ----------------------------
-- Table structure for luck_role
-- ----------------------------
DROP TABLE IF EXISTS `luck_role`;
CREATE TABLE `luck_role`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `role_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `role_description` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '该角色无描述',
  `role_ban` tinyint NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of luck_role
-- ----------------------------
INSERT INTO `luck_role` VALUES (1, 'SuperAdmin', '超级管理员，可访问luckPermissionAPI', 0);
INSERT INTO `luck_role` VALUES (2, '普通用户', '普通用户可上架商品与购买商品', 0);

-- ----------------------------
-- Table structure for luck_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `luck_role_permission`;
CREATE TABLE `luck_role_permission`  (
  `role_id` int NOT NULL,
  `permission_id` int NOT NULL,
  `allow` tinyint NOT NULL DEFAULT 1,
  INDEX `luck_role_permission_FK_1`(`permission_id`) USING BTREE,
  INDEX `luck_role_permission_role_id_IDX`(`role_id`, `permission_id`, `allow`) USING BTREE,
  CONSTRAINT `luck_role_permission_FK` FOREIGN KEY (`role_id`) REFERENCES `luck_role` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `luck_role_permission_FK_1` FOREIGN KEY (`permission_id`) REFERENCES `luck_permission` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of luck_role_permission
-- ----------------------------
INSERT INTO `luck_role_permission` VALUES (1, 15, 1);
INSERT INTO `luck_role_permission` VALUES (1, 21, 1);
INSERT INTO `luck_role_permission` VALUES (1, 26, 1);
INSERT INTO `luck_role_permission` VALUES (1, 38, 1);
INSERT INTO `luck_role_permission` VALUES (1, 45, 1);
INSERT INTO `luck_role_permission` VALUES (1, 51, 1);
INSERT INTO `luck_role_permission` VALUES (1, 54, 1);
INSERT INTO `luck_role_permission` VALUES (1, 55, 1);
INSERT INTO `luck_role_permission` VALUES (1, 56, 1);
INSERT INTO `luck_role_permission` VALUES (1, 57, 1);
INSERT INTO `luck_role_permission` VALUES (1, 58, 1);
INSERT INTO `luck_role_permission` VALUES (1, 65, 1);
INSERT INTO `luck_role_permission` VALUES (1, 66, 1);
INSERT INTO `luck_role_permission` VALUES (1, 68, 1);
INSERT INTO `luck_role_permission` VALUES (1, 73, 1);
INSERT INTO `luck_role_permission` VALUES (1, 75, 1);
INSERT INTO `luck_role_permission` VALUES (2, 45, 1);
INSERT INTO `luck_role_permission` VALUES (2, 55, 1);
INSERT INTO `luck_role_permission` VALUES (2, 58, 1);
INSERT INTO `luck_role_permission` VALUES (2, 62, 1);
INSERT INTO `luck_role_permission` VALUES (2, 65, 1);
INSERT INTO `luck_role_permission` VALUES (2, 66, 1);
INSERT INTO `luck_role_permission` VALUES (2, 73, 1);
INSERT INTO `luck_role_permission` VALUES (2, 75, 1);

-- ----------------------------
-- Table structure for order
-- ----------------------------
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `good_id` int NOT NULL COMMENT '商品id',
  `good_user_id` int NOT NULL,
  `user_id` int NOT NULL COMMENT '购买者id',
  `status` int NOT NULL DEFAULT 0 COMMENT '订单状态',
  `count` int NOT NULL DEFAULT 1 COMMENT '购买数量',
  `gmt_create` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  `deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `orders_FK`(`user_id`) USING BTREE,
  INDEX `orders_FK_1`(`good_id`) USING BTREE,
  CONSTRAINT `orders_FK` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `orders_FK_1` FOREIGN KEY (`good_id`) REFERENCES `good` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 36 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of order
-- ----------------------------
INSERT INTO `order` VALUES (1, 17, 1, 2, 1, 1, '2023-03-14 15:54:08', NULL, 0);
INSERT INTO `order` VALUES (2, 17, 1, 2, 1, 1, '2023-03-14 15:54:08', NULL, 0);
INSERT INTO `order` VALUES (3, 17, 1, 2, 1, 1, '2023-03-14 15:54:12', NULL, 0);
INSERT INTO `order` VALUES (4, 17, 1, 2, 1, 1, '2023-03-14 15:54:12', NULL, 0);
INSERT INTO `order` VALUES (5, 13, 1, 2, 3, 1, '2023-03-15 10:40:32', NULL, 0);
INSERT INTO `order` VALUES (6, 13, 1, 2, 0, 1, '2023-03-15 10:40:32', NULL, 0);
INSERT INTO `order` VALUES (7, 13, 1, 2, 1, 1, '2023-03-15 10:40:35', NULL, 0);
INSERT INTO `order` VALUES (8, 13, 1, 2, 0, 1, '2023-03-15 10:40:35', NULL, 0);
INSERT INTO `order` VALUES (9, 13, 1, 2, 1, 1, '2023-03-15 10:40:36', NULL, 0);
INSERT INTO `order` VALUES (10, 13, 1, 2, 1, 1, '2023-03-15 10:40:36', NULL, 0);
INSERT INTO `order` VALUES (11, 13, 1, 2, 1, 1, '2023-03-15 15:43:29', NULL, 0);
INSERT INTO `order` VALUES (12, 19, 2, 1, 1, 1, '2023-03-15 16:07:10', NULL, 0);
INSERT INTO `order` VALUES (13, 19, 2, 1, 1, 1, '2023-03-17 21:16:43', NULL, 0);
INSERT INTO `order` VALUES (14, 19, 2, 1, 1, 1, '2023-03-17 21:21:52', NULL, 0);
INSERT INTO `order` VALUES (15, 9, 1, 2, 0, 1, '2023-03-17 21:32:20', NULL, 0);
INSERT INTO `order` VALUES (16, 10, 1, 2, 0, 1, '2023-03-17 21:32:27', NULL, 0);
INSERT INTO `order` VALUES (17, 7, 1, 2, 0, 1, '2023-03-17 21:32:33', NULL, 0);
INSERT INTO `order` VALUES (18, 8, 1, 2, 0, 1, '2023-03-17 21:32:41', NULL, 0);
INSERT INTO `order` VALUES (19, 5, 1, 2, 0, 1, '2023-03-17 21:32:44', NULL, 0);
INSERT INTO `order` VALUES (20, 6, 1, 2, 0, 1, '2023-03-17 21:32:49', NULL, 0);
INSERT INTO `order` VALUES (21, 3, 1, 2, 1, 1, '2023-03-17 21:58:08', NULL, 0);
INSERT INTO `order` VALUES (22, 4, 1, 2, 1, 1, '2023-03-17 21:59:07', NULL, 0);
INSERT INTO `order` VALUES (23, 1, 1, 2, 1, 1, '2023-03-17 21:59:16', NULL, 0);
INSERT INTO `order` VALUES (24, 2, 1, 2, 1, 1, '2023-03-17 21:59:25', NULL, 0);
INSERT INTO `order` VALUES (25, 16, 1, 2, 1, 1, '2023-03-17 22:18:56', NULL, 0);
INSERT INTO `order` VALUES (26, 12, 1, 2, 1, 1, '2023-03-17 22:19:16', NULL, 0);
INSERT INTO `order` VALUES (27, 11, 1, 2, 1, 1, '2023-03-17 22:21:26', NULL, 0);
INSERT INTO `order` VALUES (28, 14, 1, 2, 1, 1, '2023-03-17 22:24:56', NULL, 0);
INSERT INTO `order` VALUES (29, 13, 1, 2, 1, 1, '2023-03-17 22:25:11', NULL, 0);
INSERT INTO `order` VALUES (30, 13, 1, 2, 1, 3, '2023-03-17 22:28:03', NULL, 0);
INSERT INTO `order` VALUES (31, 13, 1, 2, 1, 1, '2023-03-17 22:29:08', NULL, 0);
INSERT INTO `order` VALUES (32, 13, 1, 2, 1, 4, '2023-03-17 22:29:12', NULL, 0);
INSERT INTO `order` VALUES (33, 17, 1, 2, 4, 1, '2023-03-17 22:51:10', NULL, 0);
INSERT INTO `order` VALUES (34, 19, 2, 1, 2, 1, '2023-03-18 23:53:40', NULL, 0);
INSERT INTO `order` VALUES (35, 19, 2, 1, 2, 1, '2023-03-18 23:53:45', NULL, 0);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `nickname` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '昵称',
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '邮箱',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
  `role_id` int NOT NULL DEFAULT 0 COMMENT '角色id',
  `gmt_create` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  `deleted` tinyint(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `user_un`(`email`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'liuscra', 'liuscraft@qq.com', '123123', 1, '2023-03-10 22:41:50', NULL, 0);
INSERT INTO `user` VALUES (2, '123123', '3434@qq.com', '123123', 2, '2023-03-14 15:14:35', NULL, 0);

SET FOREIGN_KEY_CHECKS = 1;
