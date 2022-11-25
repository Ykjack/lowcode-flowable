# SpringBoot-Flowable
本项目整合了springboot2.7.5与flowable6.7.0，集成了`flowable-ui`并提供了修改用户鉴权的入口。

对于大多数流程里需要任务分配以及使用过程中可能会涉及到的多数据源切换也给出了可行的技术方案

## 使用说明
- 填充好数据库信息就可启动该工程。
- 由于用户认证是自定义的，当前工程是任意用户都能登录。
- 可重新编写`com.fusiontech.lowcode.flowable.service.impl`下的类，只需实现对应接口就可以了。
- 集成后，项目可做普通http/rpc服务端对外提供服务。主要是利用`flowable-api`对审批流做一些操作。`impl.service.com.fusiontech.lowcode.flowable.ProcessServiceImpl.start`是使用`flowable-api`的小demo。
- 项目中的`biz`表示业务，可以替换为任意名称
- 如果修改了`package`路径里的`biz`，需注意修改`config.common.com.fusiontech.lowcode.flowable.MybatisPlusConfig.sqlSessionFactory`下`53`行的路径以及启动类里`@MapperScan`里的路径。
- 很多类文件里的`TODO`，表示此处需根据实际业务来处理逻辑

## flowable 数据库表说明
- 表分类	表名	解释
-  一般数据		
-  [ACT_GE_BYTEARRAY]	通用的流程定义和流程资源
- [ACT_GE_PROPERTY]	系统相关属性
-  流程历史记录		
-  [ACT_HI_ACTINST]	历史的流程实例
-  [ACT_HI_ATTACHMENT]	历史的流程附件
-  [ACT_HI_COMMENT]	历史的说明性信息
-  [ACT_HI_DETAIL]	历史的流程运行中的细节信息
-  [ACT_HI_IDENTITYLINK]	历史的流程运行过程中用户关系
-  [ACT_HI_PROCINST]	历史的流程实例
- [ACT_HI_TASKINST]	历史的任务实例
-  [ACT_HI_VARINST]	历史的流程运行中的变量信息
-  流程定义表		
-  [ACT_RE_DEPLOYMENT]	部署单元信息
-  [ACT_RE_MODEL]	模型信息
-  [ACT_RE_PROCDEF]	已部署的流程定义
-  运行实例表		
-  [ACT_RU_EVENT_SUBSCR]	运行时事件
-  [ACT_RU_EXECUTION]	运行时流程执行实例
-  [ACT_RU_IDENTITYLINK]	运行时用户关系信息，存储任务节点与参与者的相关信息
-  [ACT_RU_JOB]	运行时作业
-  [ACT_RU_TASK]	运行时任务
-  [ACT_RU_VARIABLE]	运行时变量表
  用户用户组表		
-  [ACT_ID_BYTEARRAY]	二进制数据表
- [ACT_ID_GROUP]	用户组信息表
- [ACT_ID_INFO]	用户信息详情表
- [ACT_ID_MEMBERSHIP]	人与组关系表
-  [ACT_ID_PRIV]	权限表
-  [ACT_ID_PRIV_MAPPING]	用户或组权限关系表
-  [ACT_ID_PROPERTY]	属性表
-  [ACT_ID_TOKEN]	记录用户的token信息
- [ACT_ID_USER]	用户表



