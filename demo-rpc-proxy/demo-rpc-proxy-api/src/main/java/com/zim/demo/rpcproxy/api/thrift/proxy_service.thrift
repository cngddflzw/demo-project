namespace java com.zim.demo.rpcproxy.api.thrift

struct Request {
    1: string group;
    2: string version;
    3: string class_name;
    4: string func_name;
    5: string param;
}

service ProxyService {
    string Invoke(1: Request request);
}