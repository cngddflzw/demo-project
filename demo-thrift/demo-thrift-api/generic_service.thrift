namespace cpp thricyclebusiness

struct Request {
    1: string class_name;
    2: string func_name;
    3: string param;
}

service GenericService {
    string Invoke(1: Request request);
}