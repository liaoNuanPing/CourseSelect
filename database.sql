#教师表
#id、工号、姓名、性别、生日、手机、邮箱、头像

#学生表
#id、学号、姓名、年级、班级、家长姓名、家长电话

#亲属表
#id，学生id、姓名、手机

#课程表
#id、课程名、课程描述

#选课条件表
#id、课程id、年级【0默认全部可选，1小班，2中班，3大班】、学期【0默认全部可选，1，2】、班级【0默认全部可选】、收学生数量、已选数


#课程图片表
#id、课程id、id

CREATE DATABASE courses_selection;
USE courses_selection;



DROP TABLE IF EXISTS admin_info;
CREATE TABLE admin_info(
  id int primary key auto_increment,
  login_name VARCHAR(20) NOT NULL  COMMENT '帐号',
  psw VARCHAR(30) NOT NULL  COMMENT '密码',
  person_in_charge VARCHAR(20) COMMENT '负责人姓名',
  email VARCHAR(30) COMMENT '邮箱',
  phone VARCHAR(11) COMMENT '手机',
  this_login_time  TIMESTAMP DEFAULT now() COMMENT '本次登录时间',
  last_login_time  TIMESTAMP DEFAULT now() COMMENT '上次登录时间'
)engine=InnoDB default charset 'utf8' comment '管理员表';

DROP TABLE IF EXISTS student;
CREATE TABLE student(
  id int NOT NULL PRIMARY KEY AUTO_INCREMENT COMMENT '学号',
  stu_name VARCHAR(20) NOT NULL  COMMENT '姓名',
  grade VARCHAR(10) COMMENT '年级',
  class_now VARCHAR(10) COMMENT '班级',
  parent_name VARCHAR(20) NOT NULL  COMMENT '家长姓名',
  parent_phone VARCHAR(11) COMMENT '家长手机',
  head_img  VARCHAR(100) COMMENT '合照'
)engine=InnoDB default charset 'utf8' comment '学生表';



DROP TABLE IF EXISTS identity_auditing;
CREATE TABLE identity_auditing(
  id int NOT NULL PRIMARY KEY AUTO_INCREMENT COMMENT '主键',
  stu_name VARCHAR(20) NOT NULL  COMMENT '姓名',
  grade VARCHAR(10) COMMENT '年级',
  class_now VARCHAR(10) COMMENT '班级',
  parent_name VARCHAR(20) NOT NULL  COMMENT '家长姓名',
  parent_phone VARCHAR(11) COMMENT '家长手机',
  head_img  VARCHAR(100) COMMENT '合照',
  register_time timestamp default now() comment '注册时间',
  auditing_status CHAR DEFAULT '0' COMMENT '审核状态，0待审核、1通过、2不通过'
)engine=InnoDB default charset 'utf8' comment '学生身份审核表';


DROP TABLE IF EXISTS wx_student;
CREATE TABLE wx_student(
  id int primary key auto_increment,
  openid varchar(100) NOT NULL  COMMENT '微信id',
  stu_id int NULL  COMMENT '学生id-外键',
  auditing_id int NOT NULL  COMMENT '审核id-外键',
  create_time timestamp default now() comment '注册时间',
  FOREIGN KEY (stu_id) REFERENCES student(id),
  FOREIGN KEY (auditing_id) REFERENCES identity_auditing(id)
)engine=InnoDB default charset 'utf8' comment '微信对应学生表'


DROP TABLE IF EXISTS stu_select;
CREATE TABLE `stu_select`(
  id int NOT NULL PRIMARY KEY AUTO_INCREMENT,
  student_card VARCHAR(20) COMMENT '学号',
  stu_name VARCHAR(20) NOT NULL  COMMENT '姓名',
  grade VARCHAR(10) COMMENT '年级',
  class_now VARCHAR(10) COMMENT '班级',
  parent_name VARCHAR(20) NOT NULL  COMMENT '家长姓名',
  parent_phone VARCHAR(11) COMMENT '家长手机',
  head_img  VARCHAR(100) COMMENT '合照',
  course_id int COMMENT '课程号',
  c_name VARCHAR(20) NOT NULL  COMMENT '课程名',
  c_desc VARCHAR(100) COMMENT '课程描述',
  select_time TIMESTAMP DEFAULT time() COMMENT '选课时间',
  pass CHAR DEFAULT '0' COMMENT '0未通过审核，1通过'
)engine=InnoDB default charset 'utf8' comment '学生选课表';

DROP TABLE IF EXISTS course;
CREATE TABLE course (
  `id` int NOT NULL PRIMARY KEY AUTO_INCREMENT,
  `c_name` VARCHAR(20) NOT NULL  COMMENT '课程名',
  `c_desc` VARCHAR(100) COMMENT '课程描述'
)engine=InnoDB default charset 'utf8' comment '课程表';


DROP TABLE IF EXISTS per_class_course;
CREATE TABLE per_class_course(
  id int NOT NULL PRIMARY KEY AUTO_INCREMENT,
  course_id int COMMENT '外键--课程号',
  grade VARCHAR(10) COMMENT '年级【0默认全部可选，1小班，2中班，3大班】',
  term VARCHAR(10) COMMENT '学期【0默认全部可选，1，2】',
  to_class VARCHAR(10) COMMENT '班级【0默认全部可选】',
  total_need_stu_amount int DEFAULT 9999 COMMENT '收学生数 默认无限',
  have_stu_amount int DEFAULT 0 COMMENT '已收数 默认0',
  FOREIGN KEY(course_id) REFERENCES course(id) on delete cascade
)engine=InnoDB default charset 'utf8' comment '各年级各班选课信息表';

INSERT INTO `courses_selection`.`per_class_course` (`id`, `course_id`, `grade`, `term`, `to_class`, `total_need_stu_amount`, `have_stu_amount`) VALUES ('1', '1', '2', '0', '0', '40', '0');
INSERT INTO `courses_selection`.`per_class_course` (`id`, `course_id`, `grade`, `term`, `to_class`, `total_need_stu_amount`, `have_stu_amount`) VALUES ('2', '2', '1', '1', '1', '49', '0');



DROP TABLE IF EXISTS course_pic;
CREATE TABLE course_pic(
  id int NOT NULL PRIMARY KEY AUTO_INCREMENT,
  course_id int COMMENT '外键--课程号',
  id VARCHAR(100) COMMENT '图片地址',
  FOREIGN KEY(course_id) REFERENCES course(id) on delete cascade
)engine=InnoDB default charset 'utf8' comment '各年级各班选课信息表';

INSERT INTO `courses_selection`.`course_pic` (`id`, `course_id`, `id`) VALUES ('1', '1', 'static/images/keting.jpg');
INSERT INTO `courses_selection`.`course_pic` (`id`, `course_id`, `id`) VALUES ('2', '1', 'static/images/keting.jpg');
INSERT INTO `courses_selection`.`course_pic` (`id`, `course_id`, `id`) VALUES ('3', '1', 'static/images/keting.jpg');
INSERT INTO `courses_selection`.`course_pic` (`id`, `course_id`, `id`) VALUES ('4', '2', 'static/images/keting2.jpg');
INSERT INTO `courses_selection`.`course_pic` (`id`, `course_id`, `id`) VALUES ('5', '2', 'static/images/keting2.jpg');
