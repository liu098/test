package com.liucong.conf;

import com.liucong.inte.Resource;
import com.liucong.inte.Service;
import com.liucong.util.ClassUtil;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author  liucong
 * Date 2018
 */
public class ClassPathApplication {
    private String packageName;
    private static ConcurrentHashMap<String, Object> concurrentMap = new ConcurrentHashMap<String, Object>();
    public ClassPathApplication(String packageName) {
        this.packageName = packageName;
        init();
        try {
            eatchMap();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    /**
     * 初始化map容器
     */
    private void init() {
        List<Class<?>> classList = get();
        try {
            initBean(classList);
        } catch (Exception x) {
            x.printStackTrace();
        }
    }

    /**
     * 调用对象
     * @param bean
     * @return
     * @throws Exception
     */
    public Object getBean(String bean) throws Exception {
        if ("".equals(bean) || bean == null) {
            return new Exception("无此bean");
        }
        Object object = concurrentMap.get(bean);
        return object;
    }

    /**
     * 扫包
     *
     * @return
     */
    private List<Class<?>> get() {
        List<Class<?>> classList = ClassUtil.getClasses(packageName);
        return classList;
    }

    /**
     * 存放的容器
     *
     * @return
     */
    private ConcurrentHashMap<String, Object> getMap() {
        return concurrentMap;
    }


    /**
     * 初始化map容器
     *
     * @param classList
     */
    private void initBean(List<Class<?>> classList) throws Exception {
        for (Class<?> cla : classList) {
            Service service = cla.getAnnotation(Service.class);
            if (service == null) {
                return;
            }
            String beanid = cla.getSimpleName();
            String bean = ClassUtil.toLowerCaseFirstOne(beanid);
            ConcurrentHashMap<String, Object> concurrentHashMap = getMap();
            Object object = newConstructor(cla);
            concurrentHashMap.put(bean, object);
            continue;
        }


    }


    /**
     * 实例化
     *
     * @param clas
     * @return
     * @throws Exception
     */
    private Object newConstructor(Class<?> clas) throws Exception {
        Constructor constructor = clas.getDeclaredConstructor();
        constructor.setAccessible(true);
        Object object = constructor.newInstance();
        return object;
    }

    private void eatchMap() throws Exception {
        for (Map.Entry<String, Object> entry : concurrentMap.entrySet()) {
            Object object = entry.getValue();
            setProperty(object);
        }
    }

    private void setProperty(Object object) throws Exception {
        Class<?> clas = object.getClass();
        Field[] fields = clas.getDeclaredFields();
        for (Field field : fields) {
            Resource resource = field.getAnnotation(Resource.class);
            if (resource == null) {
                return;
            }
            field.setAccessible(true);
            String fieldName = field.getName();
            Object bean = concurrentMap.get(fieldName);
            field.set(object, bean);
        }
    }
}
