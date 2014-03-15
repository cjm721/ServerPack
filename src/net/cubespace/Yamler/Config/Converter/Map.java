package net.cubespace.Yamler.Config.Converter;

import net.cubespace.Yamler.Config.ConfigSection;
import net.cubespace.Yamler.Config.InternalConverter;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 */
public class Map implements Converter {
    private InternalConverter internalConverter;

    public Map(InternalConverter internalConverter) {
        this.internalConverter = internalConverter;
    }

    @Override
    public Object toConfig(Class<?> type, Object obj, ParameterizedType genericType) throws Exception {
        if (genericType != null) {
            if(genericType.getActualTypeArguments().length == 2) {
                java.util.Map<Object, Object> map1 = (java.util.Map) obj;

                for(java.util.Map.Entry<Object, Object> entry : map1.entrySet()) {
                    Class clazz;
                    if (genericType.getActualTypeArguments()[1] instanceof ParameterizedType) {
                        ParameterizedType parameterizedType = (ParameterizedType) genericType.getActualTypeArguments()[1];
                        clazz = (Class) parameterizedType.getRawType();
                    } else {
                        clazz = (Class) genericType.getActualTypeArguments()[1];
                    }

                    Converter converter = internalConverter.getConverter(clazz);
                    map1.put(entry.getKey(), ( converter != null ) ? converter.toConfig(clazz, entry.getValue(), (genericType.getActualTypeArguments()[1] instanceof ParameterizedType) ? (ParameterizedType) genericType.getActualTypeArguments()[1] : null) : entry.getValue());
                }

                return map1;
            }
        }

        return obj;
    }

    @Override
    public Object fromConfig(Class type, Object section, ParameterizedType genericType) throws Exception {
        if (genericType != null) {

            java.util.Map map;
            try {
                map = ((java.util.Map) ((Class) genericType.getRawType()).newInstance());
            } catch (InstantiationException e) {
                map = new HashMap();
            }

            if(genericType.getActualTypeArguments().length == 2 ) {
                Class keyClass = ((Class) genericType.getActualTypeArguments()[0]);

                java.util.Map<?, ?> map1 = (section instanceof java.util.Map) ? (java.util.Map) section : ((ConfigSection) section).getRawMap();
                for(java.util.Map.Entry<?, ?> entry : map1.entrySet()) {
                    Object key;

                    if (keyClass.equals(Integer.class)) {
                        key = Integer.valueOf((String) entry.getKey());
                    } else if (keyClass.equals(Short.class)) {
                        key = Short.valueOf((String) entry.getKey());
                    } else if (keyClass.equals(Byte.class)) {
                        key = Byte.valueOf((String) entry.getKey());
                    } else if (keyClass.equals(Float.class)) {
                        key = Float.valueOf((String) entry.getKey());
                    } else if (keyClass.equals(Double.class)) {
                        key = Double.valueOf((String) entry.getKey());
                    } else {
                        key = entry.getKey();
                    }

                    Class clazz;
                    if (genericType.getActualTypeArguments()[1] instanceof ParameterizedType) {
                        ParameterizedType parameterizedType = (ParameterizedType) genericType.getActualTypeArguments()[1];
                        clazz = (Class) parameterizedType.getRawType();
                    } else {
                        clazz = (Class) genericType.getActualTypeArguments()[1];
                    }

                    Converter converter = internalConverter.getConverter(clazz);
                    map.put(key, ( converter != null ) ? converter.fromConfig(clazz, entry.getValue(), (genericType.getActualTypeArguments()[1] instanceof ParameterizedType) ? (ParameterizedType) genericType.getActualTypeArguments()[1] : null) : entry.getValue());
                }
            } else {
                Converter converter = internalConverter.getConverter((Class) genericType.getRawType());

                if (converter != null) {
                    return converter.fromConfig((Class) genericType.getRawType(), section, null);
                }

                return (section instanceof java.util.Map) ? (java.util.Map) section : ((ConfigSection) section).getRawMap();
            }

            return map;
        } else {
            return section;
        }
    }

    @Override
    public boolean supports(Class<?> type) {
        return java.util.Map.class.isAssignableFrom(type);
    }
}
