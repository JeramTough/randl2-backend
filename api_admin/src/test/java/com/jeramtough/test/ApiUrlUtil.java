package com.jeramtough.test;

/**
 * <pre>
 * Created on 2020/9/16 10:57
 * by @author WeiBoWen
 * </pre>
 */
public class ApiUrlUtil {

    /*public static void main(String[] args) {
        List<SystemApi> apiList = getAll();
        L.debug(apiList.size());
    }

    public static List<SystemApi> getAll() {
        List<Class<?>> classList = ClassUtil.getClassesByPagename(
                "com.jeramtough.randl2.adminapp.action.controller");
        L.debug(classList.size());

        List<SystemApi> apiList = new ArrayList<>();
        for (Class<?> aClass : classList) {
            io.swagger.annotations.Api apiAnnotation = aClass.getAnnotation(io.swagger.annotations.Api.class);
            if (apiAnnotation != null) {
                String groupName = apiAnnotation.tags()[0];
                String url1 = aClass.getAnnotation(RequestMapping.class).value()[0];
                Method[] methods = aClass.getMethods();
                for (Method method : methods) {
                    ApiOperation apiOperationAnnotation = Objects.requireNonNull(method).getAnnotation(
                            ApiOperation.class);

                    RequestMapping requestMappingAnnotation = Objects.requireNonNull(method).getAnnotation(
                            RequestMapping.class);

                    if (requestMappingAnnotation == null) {
                        continue;
                    }

                    String url2 = requestMappingAnnotation.value()[0];

                    List<String> methodStrs = new ArrayList<>();
                    for (RequestMethod requestMethod : requestMappingAnnotation.method()) {
                        methodStrs.add(requestMethod.toString());
                    }

                    SystemApi api =new SystemApi();
                    api.setMethods(JSON.toJSONString(methodStrs));
                    api.setGroupName(groupName);
                    api.setDescription(apiOperationAnnotation.notes());
                    api.setAlias(aClass.getSimpleName() + "." + method.getName());
                    api.setPath(url1 + url2);

                    apiList.add(api);
                }
            }
        }
        return apiList;
    }*/
}
