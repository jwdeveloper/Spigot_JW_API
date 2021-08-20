package jw.dependency_injection;

import jw.utilites.ClassLoaderUtility;
import jw.utilites.ObjectHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class InjectionManager {

    private InjectionContainer serviceContainer;

    private static InjectionManager instance;

    public static InjectionManager Instance() {
        if (instance == null) {
            instance = new InjectionManager();
        }
        return instance;
    }


    private InjectionManager() {
        serviceContainer = new InjectionContainer();
    }

    public static <T> List<T> getObjectByType(Class<T> searchType)
    {
        List<T> result = new ArrayList<>();
        instance.serviceContainer.getInjections().forEach((type, b)->
        {
            if( ObjectHelper.classContainsType(type, searchType))
            {
                result.add(instance.serviceContainer.getObject(type));
            }
        });
      return result;
    }

    public static Set<Class<?>> getInjectedTypes()
    {
      return instance.serviceContainer.getInjections().keySet();
    }

    public static <T> T getObject(Class<T> tClass) {
        return instance.serviceContainer.getObject(tClass);
    }

    public static <T> void register(InjectionType serviceType, Class<T> type) {
        instance.serviceContainer.register(serviceType, type);
    }

    public static <T> void registerSingletone(Class<T> type) {
        instance.serviceContainer.register(InjectionType.SINGLETON, type);
    }

    public static <T> void registerTransient(Class<T> type) {
        instance.serviceContainer.register(InjectionType.TRANSIENT, type);
    }

    public static void registerAllFromPackage(Package packName) {
        var types = ClassLoaderUtility.getClassesInPackage(packName.getName());

        var toInstatiate = new ArrayList<Class<?>>();

        for (Class<?> type : types) {
            Injectable serviceAttribute = type.getAnnotation(Injectable.class);
            if (serviceAttribute == null)
                continue;
            register(serviceAttribute.injectionType(), type);

            if(serviceAttribute.autoInit())
            {
                toInstatiate.add(type);
            }
        }
        for(Class<?> type:toInstatiate)
        {
            getObject(type);
        }
    }
}
