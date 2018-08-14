# TODO List

1. 处理 proxy 与 c++ 之间 thrift 断线重连问题
2. 处理 dubbo 超时应用到 thrift 超时中的问题
3. 理清楚 thrift server 的线程池配置, 使用 cache 的还是 fix 的, 用 fix 的话应该配置多少线程

# 测试项目

# java 调用 c++ 测试

需配合 c++ demo 项目

1. 启动 c++ demo server (tricycle_demo_server)
2. 配置 sidecar 项目下的 service_config.json (这里写 c++ 暴露的服务)
2. 启动 sidecar (SidecarBootstrap)


1. 运行 Java2HeterogeneousInvocationTest

# c++ 调用 java 测试

1. 启动待测试的 java service (JavaServiceSpringBootDemo)
2. 配置 sidecar 项目下的 reference_config.json (这里配置 c++ 引用的服务)
3. 启动 sidecar (SidecarBootstrap)
4. 运行 c++ demo client (tricycle_demo_client)
 