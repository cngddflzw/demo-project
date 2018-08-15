# TODO List

1. 处理 proxy 与 c++ 之间 thrift 断线重连问题
2. 处理 dubbo 超时应用到 thrift 超时中的问题
3. 理清楚 thrift server 的线程池配置, 使用 cache 的还是 fix 的, 用 fix 的话应该配置多少线程

# 测试组件列表

C++ 部分:

1. tricycle: c++ 调用 proxy 与 被 proxy 调用的接口组件
2. tricycle_demo_server: 模拟 server 端 tricycle 组件的使用, 提供被 java 调用的 c++ rpc 服务
3. tricycle_demo_client: 模拟 client 端 tricycle 组件的使用, 用于调用外部 java 服务  

Java 部分:

1. rpc-proxy: 位于 tricycle 机器上的 proxy, 用于接受 c++ 和 java 的相互调用请求
2. java-service: 模拟 java 服务提供者, 用于被 tricycle_demo_client 调用
3. java-client: 模拟 java 服务调用者, 用于调用 tricycle_demo_server

# 测试项目

## java / c++ 相互调用测试

* java => c++ 调用流程: java-client => rpc-proxy => tricycle_demo_server
* c++ => java 调用流程: tricycle_demo_client => rpc-proxy => java-service

启动流程

1. 启动 c++ demo server (tricycle_demo_server)
2. 配置 rpc-proxy 项目下的 service_config.json (这里写 c++ 暴露的服务)
3. 配置 rpc-proxy 项目下的 reference_config.json (这里配置 c++ 引用的服务)
4. 启动 java-service (JavaServiceSpringBootDemo)
5. 运行 rpc-proxy (SidecarBootstrap)
6. 运行 java-client (GenericConsumerSpringBootDemo) 测试 java 调用 c++
7. 运行 c++ demo client (tricycle_demo_client) 测试 c++ 调用 java
 