# 飞马星球医院预约挂号微系统

## 一、 小组主要成员与分工

| 姓名 | 学号 | 角色 | 权重 | 具体分工任务 |
| :--- | :--- | :--- | :--- | :--- |
| 郭晨豪 | [学号] | 组长 | 34% | 统筹项目架构、数据库设计、后端核心 (Auth/Appointment)、服务器部署 |
| 刘洪程 | [学号] | 组员 | 33% | 前端页面开发 (JSP/Bootstrap)、医生管理模块、Excel 导入功能 |
| 陈海攀 | [学号] | 组员 | 33% | 患者中心模块、PDF 报表生成、统计功能、测试与文档编写 |

---

## 二、 项目简介

本项目是基于 Java Web 技术的 B/S 架构预约挂号系统。旨在为飞马星球医院提供高效的就医服务，解决患者挂号难、医院管理繁杂的问题。

系统主要包含两大角色：
1.  **患者端**：支持注册/登录、科室查询、医生查询、在线预约（并发控制）、取消预约、个人信息管理。
2.  **管理端**：支持批量导入医生排班（Excel）、导出预约记录（Excel）、生成月度统计报表（PDF）、医生信息管理。

---

## 三、 技术方案

本项目严格遵循面向对象设计原则，不使用大型框架（如 Spring），而是通过组装轻量级组件实现，以体现对 Java 基础技术（Servlet, JDBC, Reflection, Multithreading）的掌握。

### 1. 开发环境与基础构建

| 工具/库 | 版本建议 | 说明 |
| :--- | :--- | :--- |
| **JDK** | 21.0.8 | 使用 LTS 版本，利用新特性Record等 |
| **Maven** | 3.8.7 | 项目依赖管理与构建 |
| **Lombok** | 1.18.42 | 简化实体类代码（@Data） |

### 2. 后端核心架构 (MVC)

| 工具/库 | 名称 | 用途与说明 |
| :--- | :--- | :--- |
| **Web 服务器** | Apache Tomcat 10 | 11太新了 |
| **数据库** | MariaDB | 兼容 MySQL，轻量稳定 |
| **驱动** | mariadb-java-client | JDBC Driver |
| **ORM 工具** | Apache Commons DbUtils | 核心 DAO 层实现，使用 `QueryRunner` 简化 JDBC |
| **连接池** | HikariCP | 高性能连接池，确保高并发下的稳定性 |
| **工具包** | Apache Commons Lang3 | 字符串处理、随机号源生成 |

### 3. 前端技术

| 工具/库 | 名称 | 说明 |
| :--- | :--- | :--- |
| **核心技术** | JSP + JSTL | 服务端渲染，使用 JSTL 标签库处理列表循环 |
| **UI 框架** | Bootstrap 5 | 响应式布局，提供美观的表格、表单样式 |
| **图标库** | Bootstrap Icons | 界面交互图标 |
| **交互脚本** | 原生 JavaScript | 处理表单验证与简单的异步交互 |

### 4. 特定功能组件

| 功能 | 工具/库 | 说明 |
| :--- | :--- | :--- |
| **Excel 处理** | EasyExcel | 阿里巴巴开源库，用于医生排班的批量导入与导出 |
| **PDF 生成** | OpenPDF | 用于生成医院月度统计报表 |

### 5. 部署与运维

| 工具 | 说明 |
| :--- | :--- |
| **服务器 OS** | Ubuntu 24.04 Server | 云端部署环境 |
| **终端工具** | Termius / XShell | SSH 连接与管理 |

---

## 四、 系统设计与架构

本项目采用标准的自顶向下设计，将系统划分为表现层、控制层、业务层和持久层。为了提高代码的可维护性和开发效率，我们在不使用 Spring 框架的情况下，手动应用了以下关键设计模式：

### 1. 模块化设计

系统自顶向下分为四层，层与层之间低耦合：

*   **L1 - 视图层**
    *   负责数据的展示和用户交互。使用 JSTL 标签库替代 Java 脚本，保持页面整洁。
*   **L2 - 控制层**
    *   **Web 模块**：`PatientServlet`, `DoctorServlet`, `AdminServlet`。
    *   职责：接收 HTTP 请求，解析参数，调用 Service 层，并决定跳转视图或返回 JSON。
*   **L3 - 业务层**
    *   **Core 模块**：`AppointmentService` (处理预约并发逻辑), `AuthService` (处理登录注册)。
    *   职责：事务控制、业务规则校验（如：判断号源是否充足）、复杂计算。
*   **L4 - 持久层**
    *   **Data 模块**：`PatientDao`, `DoctorDao`, `AppointmentDao`。
    *   职责：只负责生成 SQL 并执行，原子性操作数据库，不包含业务逻辑。

### 2. 核心设计模式应用

为了在原生 Java Web 环境下实现高效开发与解耦，本项目应用了以下模式：

#### A. 单例模式
*   **应用场景**：数据库连接池管理 (`DBUtil`)。
*   **实现原理**：HikariCP 连接池是一个重量级对象，不应频繁创建。我们使用静态代码块（Static Block）结合静态方法，确保全局只有一个 `DataSource` 实例，大幅降低资源消耗。

#### B. 数据访问对象模式
*   **应用场景**：所有数据库操作。
*   **实现原理**：将数据操作（SQL）与业务逻辑完全分离。Service 层通过接口或类依赖 DAO 层，这使得如果未来更换数据库（如从 MariaDB 换到 PostgreSQL），只需修改 DAO 层代码，业务逻辑无需变动。

#### C. 模板方法模式 & 反射
*   **应用场景**：`BaseServlet` 请求分发。
*   **实现原理**：
    *   传统 Servlet 开发需要为每个功能写一个 Servlet 类，导致类爆炸。
    *   本项目实现了一个 `BaseServlet`，利用 **Java 反射机制**。前端只需传递 `method` 参数（如 `?method=login`），`BaseServlet` 会自动查找并调用子类中名为 `login` 的方法。
    *   这极大地减少了代码量，模拟了 Spring MVC 的 Controller 分发机制。

#### D. 传输对象模式
*   **应用场景**：数据传输。
*   **实现原理**：使用 Lombok 定义的 `Entity` 类（如 `Patient`, `Doctor`）作为各层之间传输数据的载体，避免了散乱的参数传递。

项目结构如下：

```text
PegasusHospital/
├── pom.xml                     # Maven 配置文件 (依赖管理)
├── README.md                   # 说明文档
├── src/
│   ├── main/
│   │   ├── java/               # Java 源代码
│   │   │   └── com/
│   │   │       └── pegasus/
│   │   │           ├── entity/         # [模型层] 数据库实体 (Lombok)
│   │   │           ├── dao/            # [数据层] DbUtils 数据库操作
│   │   │           ├── service/        # [业务层] 核心逻辑 (锁机制在此实现)
│   │   │           ├── web/            # [控制层] Servlet (请求分发)
│   │   │           ├── filter/         # [过滤器] 编码与登录拦截
│   │   │           └── utils/          # [工具类] DBUtil, ExcelUtil, PdfUtil
│   │   ├── resources/
│   │   │   ├── db.properties   # 数据库连接配置
│   │   │   └── database.sql    # 数据库初始化脚本
│   │   └── webapp/             # Web 根目录
│   │       ├── assets/         # Bootstrap 静态资源
│   │       ├── files/          # 临时文件存储
│   │       ├── WEB-INF/        # 安全配置
│   │       ├── patient/        # 患者端 JSP 页面
│   │       └── admin/          # 管理端 JSP 页面
```

---

## 五、 功能亮点与实现逻辑

1.  **并发预约控制**
    *   为了防止多个患者同时抢占同一个号源，在 `AppointmentService` 中使用了 `synchronized` 关键字（或数据库乐观锁）对扣减库存的操作进行保护，确保数据一致性。

2.  **基于反射的 Servlet 分发**
    *   为了避免创建过多的 Servlet 类，我们使用了 `BaseServlet` 封装反射逻辑。前端只需传递 `method` 参数（如 `method=login`），后端即可自动调用对应的方法，简化了代码结构。

3.  **数据库连接池管理**
    *   使用 `HikariCP` 管理数据库连接，并在 `DBUtil` 中实现了静态单例模式，大幅提升了数据库访问性能。

4.  **文件流处理**
    *   使用 `EasyExcel` 监听器模式读取上传的 Excel 文件，实现流式解析，避免大文件内存溢出。

---

## 六、 安装与运行指南

### 1. 数据库配置
1.  安装 MariaDB。
2.  创建一个名为 `pegasus_hospital` 的数据库。
3.  运行 `src/main/resources/database.sql` 脚本建表。
4.  修改 `src/main/resources/db.properties` 中的账号密码。

### 2. 本地运行
1.  使用 IntelliJ IDEA 打开项目。
2.  等待 Maven 下载依赖。
3.  配置 Tomcat 10 服务器，将 Artifact `PegasusHospital:war exploded` 部署到 Tomcat。
4.  启动服务器，访问 `http://localhost:8080/hospital`。

### 3. 服务器部署
1.  在项目根目录运行 `mvn clean package` 打包生成 `.war` 文件。
2.  将 `.war` 文件上传至 Ubuntu 服务器 Tomcat 的 `webapps` 目录下。
3.  Tomcat 会自动解压，访问 `http://[服务器IP]:8080/hospital`。

---

## 七、 测试账户

*   **管理员账号**：admin / 123456
*   **测试患者**：1000000001 / 123456