package jw.dependency_injection;

import jw.InitializerAPI;

import java.util.HashMap;

public class InjectionContainer
{
    private final HashMap<Class<?>, Injection> serviceHashMap = new HashMap<>();


    public HashMap<Class<?>, Injection> getInjections()
    {
        return serviceHashMap;
    }

    public Injection getInjection(Class<?> _class)
    {
        if(serviceHashMap.containsKey(_class))
        {
            return serviceHashMap.get(_class);
        }
        return null;
    }

    public <T> void register(InjectionType serviceType, Class<T> type)
    {
      if(serviceHashMap.containsKey(type))
          return;

      serviceHashMap.put(type,new Injection(serviceType,type));
    }
    public <T> T getObject(Class<?> type)
    {
        try
        {
            if(!serviceHashMap.containsKey(type))
            {
                InitializerAPI.errorLog("Service "+type.getTypeName()+" not found!");
                return null;
            }
            Injection injection = serviceHashMap.get(type);
            if(injection.getInjectionType() == InjectionType.TRANSIENT ||
              !injection.isInit())
            {
              if(injection.setParams(this::getObject))
              {
                  injection.createInstance();
              }
              else
              {
                  InitializerAPI.errorLog("Count not create "+type.getTypeName()+" due to null parameter in constructor");
                  return null;
              }
            }
            return injection.getInstance();
        }
        catch (Exception e)
        {
            InitializerAPI.errorLog("Exception with getting service "+type.getTypeName());
            InitializerAPI.errorLog(e.toString());
            return null;
        }
    }
}
