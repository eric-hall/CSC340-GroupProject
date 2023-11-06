package com.restready.common.util;

import java.util.HashMap;

// TODO: Untested!
public class ServiceLocator {

    // TODO: Override methods for debug logging global activity.
    public static final ServiceLocator global = new ServiceLocator();

    public final HashMap<Class<?>, Object> _services;

    public ServiceLocator() {
        _services = new HashMap<>();
    }

    public <T> T locate(Class<T> serviceClass) {
        Object service = _services.getOrDefault(serviceClass, null);
        return serviceClass.cast(service);
    }

    public <T> void supply(Class<T> serviceClass, T service) {
        if (_services.containsKey(serviceClass)) {
            Log.warn(this, "Service {%s} already exists.".formatted(serviceClass.getName()));
            return;
        }
        _services.put(serviceClass, service);
    }

    public void remove(Class<?> serviceClass) {
        Object service = _services.remove(serviceClass);
        if (service == null) {
            Log.warn(this, "Service {%s} was not found in the registry.".formatted(serviceClass.getName()));
        }
    }
}
