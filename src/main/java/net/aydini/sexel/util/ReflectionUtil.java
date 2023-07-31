package net.aydini.sexel.util;

package net.aydini.mom.util.reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import net.aydini.sexel.exception.SexelException;




/**
 * 
 * @author <a href="mailto:hi@aydini.net">Aydin Nasrollahpour </a>
 *
 * Apr 1, 2021
 */
public class ReflectionUtil
{

    public static Set<Field> getClassFields(Class<?> clazz)
    {
        Set<Field> fields = new HashSet<Field>(Arrays.asList(clazz.getDeclaredFields()));
        Class<?> superClass = clazz.getSuperclass();
        while (!superClass.equals(Object.class))
        {
            fields.addAll(getClassFields(superClass));
            superClass = superClass.getSuperclass();
        }
        return fields;
    }

    public static Set<Field> getClassFields(Class<?> clazz,Predicate<Field> predicate)
    {
        return getClassFields(clazz).stream().filter(predicate).collect(Collectors.toSet());
    }

    @SafeVarargs
    public static <T extends Annotation> Set<Field> getAnnotatedClassFields(Class<?> clazz, Class<T>... annotations)
    {
        Set<Field> annotatedFields = new HashSet<Field>();
        for (Class<T> annotaion : annotations)
        {
            annotatedFields.addAll(getClassFields(clazz).stream().filter(field->field.isAnnotationPresent(annotaion)).collect(Collectors.toSet()));
        }
        return annotatedFields;
    }

    public static Field findClassFieldByFieldName(Class<?> clazz, String fieldName)
    {
        Set<Field> fields = getClassFields(clazz);
        for (Field field : fields)
        {
            if (field.getName().equals(fieldName)) return field;
        }
        return null;
    }

    /**
     *
     * @param field
     * @param object
     *            object from which the represented field's value is to be
     *            extracted
     * @return the value of the represented field in object {@code object};
     *         primitive values are wrapped in an appropriate object before
     *         being returned
     *
     * @exception IllegalAccessException
     *                if {@code field} object is enforcing Java language access
     *                control and the underlying field is inaccessible.
     * @exception IllegalArgumentException
     *                if the specified object is not an instance of the class or
     *                interface declaring the underlying field (or a subclass or
     *                implementor thereof).
     * @exception NullPointerException
     *                if the specified object is null and the field is an
     *                instance field.
     * @exception ExceptionInInitializerError
     *                if the initialization provoked by this method fails.
     *
     * @see Field#get
     */
    public static Object getFieldValueFromObject(Field field, Object object)
            throws IllegalArgumentException, IllegalAccessException, InvocationTargetException
    {
        Optional<Method> optionalGetterMethod = RWProperty.getReaderMethod(object.getClass(), field);
        if(optionalGetterMethod.isPresent())
        	return optionalGetterMethod.get().invoke(object, new Object[] {});
        return null;
    }
    
    public static <T> T getFieldValueFromObject(Field field, Object object,Class<T> type)
            throws IllegalArgumentException, IllegalAccessException, InvocationTargetException
    {
        Optional<Method> optionalGetterMethod = RWProperty.getReaderMethod(object.getClass(), field);
        if(optionalGetterMethod.isPresent())
        	return type.cast(optionalGetterMethod.get().invoke(object, new Object[] {}));
        return null;
    }


    /**
     *
     * @param targetField
     * @param targetObject
     *            the object whose field should be modified
     * @param value
     *            the new value for the field of {@code targetObject} being
     *            modified
     *
     * @see Field#set
     */
    public static void setFieldValueToObject(Field targetField, Object targetObject, Object value)
    {
        try
        {
            Method setterMethod = findSetterMethod(targetField.getName(), targetObject);
            if (setterMethod != null) setterMethod.invoke(targetObject, value);
        }
        catch(Exception e)
        {
            throw new SexelException(e);
        }
    }

    private static Method findSetterMethod(String propertyName, Object object)
    {
    	Optional<Method> optionalSettermethod = RWProperty.getWriterMethod(object.getClass(), findClassFieldByFieldName(object.getClass(), propertyName));
    	if(optionalSettermethod.isPresent())
    		return optionalSettermethod.get();
    	return null;
    }
    


    public static Class<?> getSuperClass(Class<?> clazz)
    {
        return clazz.getSuperclass();
    }

    public static boolean hasSuperClass(Class<?> clazz)
    {
    	Class<?> superClass = getSuperClass(clazz);
        return superClass ==null? false : !superClass.equals(Object.class);
    }

    public static boolean isSimpleType(Class<?> clazz)
    {
        return SimpleTypeUtil.isSimpleType(clazz);
    }
    
    
    public static <T>  T instantiate(Class<T> clazz,Object ... args)
    {
        try
        {
            return clazz.getConstructor().newInstance(args);
        }
        catch (Exception e)
        {
            throw new SexelException("no default constructor found",e);
        }
    }
    
    public static boolean isInterface(Class<?> clazz)
    {
        return clazz.isInterface();
    }
    
    
    public static boolean sameTypes(Class<?> t1 , Class<?> t2)
    {
        return t1.equals(t2);
    }
    
    
    
    public static boolean isCollection(Class<?> clazz)
    {
    	return Collection.class.isAssignableFrom(clazz);
    }
    
    
    public static boolean isSet(Class<?> clazz)
    {
    	return Set.class.isAssignableFrom(clazz);
    }
    
    
    public static boolean isList(Class<?> clazz)
    {
    	return List.class.isAssignableFrom(clazz) ;
    }
    
    public static boolean isMap(Class<?> clazz)
    {
    	return  Map.class.isAssignableFrom(clazz);
    }
    
    
    @SuppressWarnings("unchecked")
	public static <T> Class<T> getGenericType(Field field)
    {
    	ParameterizedType type = (ParameterizedType) field.getGenericType();
        return (Class<T>) type.getActualTypeArguments()[0];
    }
    
    
    public static boolean isEnum(Class<?> clazz)
    {
    	return clazz.isEnum();
    }

    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public static  Enum<?> createEnumByName(Class<?> enumType,String name)
    {
    	return Enum.valueOf((Class<Enum>) enumType, name);
    }
    
}