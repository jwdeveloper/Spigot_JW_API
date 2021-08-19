package jw.dependency_injection;

import jw.utilites.ClassLoaderUtility;
import jw.utilites.ObjectHelper;

import java.util.ArrayList;
import java.util.List;

public class ServiceManager {

    private ServiceContainer serviceContainer;

    private static ServiceManager instance;

    public static ServiceManager Instance() {
        if (instance == null) {
            instance = new ServiceManager();
        }
        return instance;
    }


    private ServiceManager() {
        serviceContainer = new ServiceContainer();
    }

    public static <T> List<T> getServicesByType(Class<T> searchType)
    {
        List<T> result = new ArrayList<>();
        instance.serviceContainer.getServices().forEach((type,b)->
        {
            if( ObjectHelper.classContainsType(type, searchType))
            {
                result.add(instance.serviceContainer.getService(type));
            }
        });
      return result;
    }

    public static <T> T getService(Class<T> tClass) {
        return instance.serviceContainer.getService(tClass);
    }

    public static <T> void register(ServiceType serviceType, Class<T> type) {
        instance.serviceContainer.register(serviceType, type);
    }

    public static <T> void registerSingletone(Class<T> type) {
        instance.serviceContainer.register(ServiceType.SINGLETON, type);
    }

    public static <T> void registerTransient(Class<T> type) {
        instance.serviceContainer.register(ServiceType.TRANSIENT, type);
    }

    public static void registerAllFromPackage(Package packName) {
        var types = ClassLoaderUtility.getClassesInPackage(packName.getName());
        for (Class<?> type : types) {
            ServiceAttribute serviceAttribute = type.getAnnotation(ServiceAttribute.class);
            if (serviceAttribute == null)
                continue;
            register(serviceAttribute.serviceType(), type);

            if(serviceAttribute.autoInit())
                getService(type);

        }
    }
}
