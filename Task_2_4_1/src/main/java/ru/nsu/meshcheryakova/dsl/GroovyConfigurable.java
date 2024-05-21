package ru.nsu.meshcheryakova.dsl;

import groovy.lang.Closure;
import groovy.lang.GroovyObjectSupport;
import groovy.lang.MetaProperty;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;

/**
 * Base class for all config classes.
 */
public class GroovyConfigurable extends GroovyObjectSupport {
    /**
     * This method is called every time groovy encounters a method call that is missing
     * from the object.
     *
     * @param name the name of the method.
     * @param args the arguments of the method.
     */
    public void methodMissing(String name, Object args) throws NoSuchMethodException,
            InvocationTargetException, InstantiationException, IllegalAccessException {
        MetaProperty metaProperty = getMetaClass().getMetaProperty(name);
        if (metaProperty != null) {
            Closure closure = (Closure) ((Object[]) args)[0];
            Object value = getProperty(name) == null ?
                    metaProperty.getType().getConstructor().newInstance() :
                    getProperty(name);
            closure.setDelegate(value);
            closure.setResolveStrategy(Closure.DELEGATE_FIRST);
            closure.call();
            setProperty(name, value);
        } else {
            throw new IllegalArgumentException("No such field: " + name);
        }
    }

    /**
     * Post-processing of the config for adding lists.
     */
    public void postProcess() throws NoSuchFieldException, InvocationTargetException,
            InstantiationException, IllegalAccessException, NoSuchMethodException {
        List<String> expectedCollections = List.of(new String[]{"tasks", "groups", "students"});
        for (String val : expectedCollections) {
            MetaProperty metaProperty = getMetaClass().getMetaProperty(val);
            if (metaProperty != null) {
                Object value = getProperty(val);
                PostProcessProperty(metaProperty, value);
            }
        }
        for (MetaProperty metaProperty : getMetaClass().getProperties()) {
            Object value = getProperty(metaProperty.getName());
            PostProcessProperty(metaProperty, value);
        }
    }

    /**
     * Post-processing for a specific property.
     *
     * @param metaProperty the metadata of the property.
     * @param value the object of the property.
     */
    public void PostProcessProperty(MetaProperty metaProperty, Object value) throws
            NoSuchFieldException, InstantiationException, IllegalAccessException,
            NoSuchMethodException, InvocationTargetException {
        if (Collection.class.isAssignableFrom(metaProperty.getType()) &&
                value instanceof Collection) {
            ParameterizedType collectionType = (ParameterizedType) getClass().
                    getDeclaredField(metaProperty.getName()).getGenericType();
            Class itemClass = (Class) collectionType.getActualTypeArguments()[0];
            if (GroovyConfigurable.class.isAssignableFrom(itemClass)) {
                Collection collection = (Collection) value;
                Collection newValue = collection.getClass().newInstance();
                for (Object o : collection) {
                    if (o instanceof Closure) {
                        Object item = itemClass.getConstructor().newInstance();
                        ((Closure) o).setDelegate(item);
                        ((Closure) o).setResolveStrategy(Closure.DELEGATE_FIRST);
                        ((Closure) o).call();
                        ((GroovyConfigurable) item).postProcess();
                        newValue.add(item);
                    } else {
                        newValue.add(o);
                    }
                }
                setProperty(metaProperty.getName(), newValue);
            }
        }
    }
}
