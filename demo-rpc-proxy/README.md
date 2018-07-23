# TODO List

1. HeterogeneousService 健康检查机制, 当 HeterogeneousService 存在服务问题时, Proxy 应该自动下线 HeterogeneousService
2. HeterogeneousService 应用重新上线后, 自动刷新所有 Reference 引用配置
3. Proxy 重新上线以后, 需要重新注册所有服务 (可以用配置文件的方式完成)
4. Proxy 与 HeterogeneousService 之间的服务调用配置 (如超时等) 如何与 Dubbo RPC 的配置一致
5. Proxy 的资源管理如何控制好, 理论上讲不应该对业务服务器造成压力 