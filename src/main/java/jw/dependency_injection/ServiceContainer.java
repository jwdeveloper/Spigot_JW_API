package jw.dependency_injection;

import jw.InitializerAPI;

import java.lang.reflect.Type;
import java.util.HashMap;

public class ServiceContainer
{
    private final HashMap<Class<?>,Service> serviceHashMap = new HashMap<>();


    public HashMap<Class<?>,Service> getServices()
    {
        return serviceHashMap;
    }

    public <T> void register(ServiceType serviceType,Class<T> type)
    {
      if(serviceHashMap.containsKey(type))
      {
          return;
      }
      Service service = new Service(serviceType,type);
      serviceHashMap.put(type,service);


    }
    public <T> T getService(Class<?> type)
    {
        try
        {
            if(!serviceHashMap.containsKey(type))
            {
                InitializerAPI.errorLog("Service "+type.getTypeName()+" not found!");
                return null;
            }
            Service service = serviceHashMap.get(type);
            if(service.getServiceType() ==ServiceType.TRANSIENT || !service.isInit())
            {
              if(service.setParams(this::getService))
              {
                  service.createInstance();
              }
              else
              {
                  InitializerAPI.errorLog("Count not create "+type.getTypeName()+" due to null parameter in constructor");
                  return null;
              }
            }
            return service.getInstance();
        }
        catch (Exception e)
        {
            InitializerAPI.errorLog("Exception with getting service "+type.getTypeName());
            InitializerAPI.errorLog(e.toString());
            return null;
        }
    }
}
