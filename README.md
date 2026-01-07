# 飞马星球医院预约挂号微系统

[TOC]

## 一、 小组主要成员与分工

| 姓名 | 角色 | 权重 |
| :--- | :--- | :--- |
| 郭晨豪 | 组长 | 34% |
| 刘洪程 | 组员 | 33% |
| 陈海攀 | 组员 | 33% |

---

## 二、 项目简介

本项目是基于 Java Web 技术的 B/S 架构预约挂号系统。旨在为飞马星球医院提供高效的就医服务，解决患者挂号难、医院管理繁杂的问题。

系统主要包含两大角色：
1.  **患者端**：支持注册/登录、科室查询、医生查询、在线预约、取消预约、个人信息管理。
2.  **管理端**：支持批量导入医生排班（Excel）、生成月度统计报表（PDF）、医生信息管理。

---

## 三、 技术方案

### 一、 开发环境与基础构建

| 工具/库 | 版本 | 说明 |
| :--- | :--- | :--- |
| **JDK** | 21.0.8 | 使用 Java 21 LTS 版本 |
| **Maven** | 3.8.7 | 项目依赖管理与构建工具 |
| **Lombok** | 1.18.30 | 通过注解简化 Entity 类代码 |

### 二、 后端核心架构

本项目未使用 Spring 框架。

| 模块 | 工具/库 | 说明 |
| :--- | :--- | :--- |
| **Web 容器** | **Apache Tomcat 10** | 使用 Jakarta EE 9 |
| **数据库** | MariaDB 10.11.13 | 完全兼容 MySQL |
| **驱动** | mariadb-java-client | JDBC 驱动程序 |
| **ORM 封装** | Apache Commons DbUtils | 轻量级 JDBC 封装，用于数据库操作 |
| **连接池** | **HikariCP** | 高性能数据库连接池，在 `DBUtil` 中实现单例管理 |
| **工具包** | Commons Lang3 | 字符串处理、随机数生成 |
| **多线程** | **Java Concurrency** | 实现多线程异步短信通知 |

### 三、 前端技术

| 模块 | 技术 | 说明 |
| :--- | :--- | :--- |
| **视图层** | JSP + JSTL | 服务端渲染 |
| **UI 框架** | **Bootstrap 5** | 简单上手 |
| **图标库** | Bootstrap Icons | 和 Bootstrap 集成 |
| **交互** | Native JavaScript | 处理气泡提示 |

### 四、 特定功能组件

| 功能 | 工具/库 | 说明 |
| :--- | :--- | :--- |
| **Excel 导入** | **EasyExcel** | Excel 读取 |
| **PDF 报表** | **OpenPDF** | 有中文支持 |

### 五、 部署环境

| 项目 | 说明 |
| :--- | :--- |
| **服务器 OS** | Ubuntu 24.04 LTS Server(基于华为云) |
| **部署方式** | WAR 包部署 (`PegasusHospital-1.0.war`) |

### 六、 项目目录结构说明

```text
PegasusHospital/
├── pom.xml                     # Maven 项目配置文件
├── README.md                   # 项目说明文档
├── src/
│   └── main/
│       ├── java/               # Java 源代码目录
│       │   └── com/
│       │       └── pegasus/
│       │           ├── entity/         # [模型层] 数据库实体类
│       │           │   ├── Admin.java          # 管理员实体
│       │           │   ├── Appointment.java    # 预约记录实体
│       │           │   ├── Doctor.java         # 医生实体
│       │           │   ├── Patient.java        # 患者实体
│       │           │   ├── Schedule.java       # 排班实体
│       │           │   └── excel/
│       │           │       └── DoctorExcel.java # EasyExcel 导入模型
│       │           │
│       │           ├── dao/            # [持久层] 数据库访问对象
│       │           │   ├── BasicDao.java       # 泛型 DAO，封装通用 CRUD
│       │           │   ├── AdminDao.java       # 管理员登录查询
│       │           │   ├── AppointmentDao.java # 预约记录管理
│       │           │   ├── DoctorDao.java      # 医生信息管理
│       │           │   ├── PatientDao.java     # 患者信息管理
│       │           │   └── ScheduleDao.java    # 排班与库存扣减
│       │           │
│       │           ├── service/        # [业务层] 复杂业务逻辑
│       │           │   └── AppointmentService.java # 处理预约核心逻辑(事务+锁)
│       │           │
│       │           ├── web/            # [控制层] Servlet 控制器
│       │           │   ├── BaseServlet.java    # 利用反射实现请求分发的基类
│       │           │   ├── AuthServlet.java    # 处理登录、注册、注销
│       │           │   ├── PatientServlet.java # 患者端业务控制器
│       │           │   └── AdminServlet.java   # 管理端业务控制器
│       │           │
│       │           ├── filter/         # [过滤器]
│       │           │   ├── EncodingFilter.java # 全局 UTF-8 编码过滤
│       │           │   └── LoginFilter.java    # 权限拦截
│       │           │
│       │           └── utils/          # [工具类]
│       │               ├── DBUtil.java         # HikariCP 数据库连接池工具
│       │               ├── AsyncTaskUtil.java  # [多线程] 异步任务执行器
│       │               ├── ValidatorUtil.java  # [数据校验] 正则校验工具
│       │               ├── ExcelUtil.java      # Excel 导入工具封装
│       │               └── PdfUtil.java        # PDF 生成工具封装
│       │
│       ├── resources/          # 配置文件
│       │   ├── db.properties   # 数据库连接配置 (URL, User, Password)
│       │   └── database.sql    # 数据库初始化脚本
│       │
│       └── webapp/             # Web 应用根目录
│           ├── files/          # 文件存储目录
│           │   └── template.xlsx # 供管理员下载的导入模板
│           ├── WEB-INF/        # 安全目录
│           │   └── web.xml     # Web 应用配置
│           ├── index.jsp       # 统一登录页
│           ├── register.jsp    # 患者注册页
│           ├── patient/        # [患者端页面]
│           │   ├── home.jsp            # 患者个人中心
│           │   ├── doctors.jsp         # 医生列表与查询
│           │   ├── schedule_pick.jsp   # 号源选择与预约
│           │   ├── my_appointments.jsp # 我的预约记录
│           │   └── profile.jsp         # 个人信息修改
│           └── admin/          # [管理端页面]
│               ├── dashboard.jsp       # 管理后台首页
│               ├── doctor_mgr.jsp      # 医生与排班管理 (含 Excel 上传)
│               └── stats.jsp           # 统计报表下载页
│
└── target/                     # 构建输出目录
    └── PegasusHospital-1.0.war # 可部署的 WAR 包
```

---

## 四、 系统设计与架构

系统自顶向下划分为五层，各层职责明确：

*   **视图层**
    *   **实现**：JSP + JSTL + Bootstrap 5。
    *   **职责**：负责页面渲染与交互。采用了服务端渲染模式，通过 JSTL 标签库处理数据展示，结合 HTML5 原生属性进行第一道数据校验。
*   **过滤器层**
    *   **实现**：`EncodingFilter`, `LoginFilter`。
    *   **职责**：负责统一请求编码（UTF-8）以及权限拦截，确保未登录用户无法访问受保护资源。
*   **控制层**
    *   **实现**：`BaseServlet` 以及各业务 Servlet。
    *   **职责**：利用反射机制解析 HTTP 请求动作，将请求分发至对应的业务方法，实现路由解耦。
*   **业务层**
    *   **实现**：`AppointmentService`。
    *   **职责**：负责核心业务逻辑的处理，包括事务控制、并发锁机制以及异步任务调度。
*   **持久层**
    *   **实现**：`BasicDao` 及具体 DAO。
    *   **职责**：基于 Apache Commons DbUtils 封装，负责与 MariaDB 数据库交互，执行原子性的 SQL 操作。

---

## 五、 软件部署

本步骤用于在华为云鲲鹏 Ubuntu 24.04 Server 服务器上部署`PegasusHospital` 系统。

### 一、 环境准备

1. 更新系统软件包
```bash
sudo apt update
```

1. 安装 Java 21
```bash
sudo apt install openjdk-21-jdk -y
```

1. 安装并配置 MariaDB 数据库
```bash
# 安装
sudo apt install mariadb-server -y

# 启动服务
sudo systemctl start mariadb
sudo systemctl enable mariadb

# 配置数据库账号
# 请确保这里的密码与项目中 db.properties 的密码一致！
# 项目配置为 user: root, password: root
sudo mysql -uroot -proot
```

在 MariaDB 命令行中执行：
```sql
-- 1. 设置 root 密码
ALTER USER 'root'@'localhost' IDENTIFIED BY 'root';
FLUSH PRIVILEGES;

-- 2. 创建数据库
CREATE DATABASE IF NOT EXISTS pegasus_hospital DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 3. 退出
exit;
```

4. 导入初始数据
需要先将 `src/main/resources/database.sql` 上传至服务器当前目录。

```bash
# 导入表结构和测试数据
mariadb -uroot -proot pegasus_hospital < src/main/resources/database.sql
```

---

### 二、 Web 服务器部署

1. 安装 Tomcat 10
```bash
sudo apt install tomcat10 -y
```

2. 部署 WAR 包
```bash
# 1. 重命名为ROOT.war
cp target/PegasusHospital-1.0.war ROOT.war

# 2. 删除 Tomcat 默认的主页
sudo rm -rf /var/lib/tomcat10/webapps/ROOT

# 3. 移动 WAR 包到部署目录
sudo mv ROOT.war /var/lib/tomcat10/webapps/

# 4. 创建文件目录，用于文件上传和PDF生成
sudo mkdir -p /var/lib/tomcat10/webapps/ROOT/files
```

3. 重启服务
```bash
sudo systemctl restart tomcat10
```

---

### 三、 验证访问

**浏览器访问**：
*   地址：`http://localhost:8080/`
*   **测试管理员账号**：`admin` / `123456`
*   **测试患者账号**：`1000000001` / `123456`

---

### 四、 常见问题排查

#### 数据库连接失败

检查 `/var/lib/tomcat10/webapps/ROOT/WEB-INF/classes/db.properties` 文件中的密码是否与 MariaDB 设置一致。