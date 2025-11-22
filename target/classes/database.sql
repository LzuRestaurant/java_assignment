CREATE DATABASE IF NOT EXISTS pegasus_hospital DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE pegasus_hospital;

-- 1. 患者表
CREATE TABLE t_patient (
    id BIGINT PRIMARY KEY COMMENT '患者ID 10位',
    name VARCHAR(20) NOT NULL,
    password VARCHAR(100) NOT NULL,
    id_card VARCHAR(18) UNIQUE NOT NULL COMMENT '身份证号',
    phone VARCHAR(15),
    gender CHAR(1) COMMENT 'M或F',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 2. 医生表
CREATE TABLE t_doctor (
    id BIGINT PRIMARY KEY COMMENT '医生ID 8位',
    name VARCHAR(20) NOT NULL,
    password VARCHAR(100) NOT NULL,
    department VARCHAR(30) NOT NULL COMMENT '科室',
    specialty VARCHAR(200) COMMENT '专长',
    is_deleted TINYINT(1) DEFAULT 0 COMMENT '软删除标记'
);

-- 3. 管理员表
CREATE TABLE t_admin (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(20) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL
);

-- 4. 医生排班/号源表 (核心业务表)
CREATE TABLE t_schedule (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    doctor_id BIGINT NOT NULL,
    work_date DATE NOT NULL COMMENT '工作日期',
    shift_type VARCHAR(10) COMMENT 'Morning/Afternoon',
    max_slots INT DEFAULT 20 COMMENT '最大号源',
    used_slots INT DEFAULT 0 COMMENT '已挂号数',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    FOREIGN KEY (doctor_id) REFERENCES t_doctor(id),
    UNIQUE KEY uk_schedule (doctor_id, work_date, shift_type) -- 防止重复排班
);

-- 5. 预约记录表
CREATE TABLE t_appointment (
    id BIGINT PRIMARY KEY COMMENT '预约号 12位',
    patient_id BIGINT NOT NULL,
    schedule_id BIGINT NOT NULL,
    status TINYINT DEFAULT 0 COMMENT '0:已预约 1:已取消 2:已完成',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (patient_id) REFERENCES t_patient(id),
    FOREIGN KEY (schedule_id) REFERENCES t_schedule(id)
);

-- 初始化测试数据
INSERT INTO t_admin (username, password) VALUES ('admin', '123456');
-- 测试患者 (ID: 1000000001)
INSERT INTO t_patient (id, name, password, id_card, phone, gender)
VALUES (1000000001, '张三', '123456', '110101199001011234', '13800138000', 'M');

-- 插入测试医生数据
INSERT INTO t_doctor (id, name, password, department, specialty) VALUES
(80000001, '华佗', '123456', '外科', '擅长外科手术，麻沸散创始人'),
(80000002, '扁鹊', '123456', '内科', '望闻问切，疑难杂症'),
(80000003, '张仲景', '123456', '内科', '伤寒杂病论，感冒发烧'),
(80000004, '孙思邈', '123456', '儿科', '千金方，药王');

-- 插入测试排班数据
INSERT INTO t_schedule (doctor_id, work_date, shift_type, max_slots, used_slots, version)
VALUES
(80000001, DATE_ADD(CURDATE(), INTERVAL 1 DAY), 'Morning', 5, 0, 0),
(80000001, DATE_ADD(CURDATE(), INTERVAL 1 DAY), 'Afternoon', 5, 0, 0),
(80000002, DATE_ADD(CURDATE(), INTERVAL 2 DAY), 'Morning', 3, 0, 0);