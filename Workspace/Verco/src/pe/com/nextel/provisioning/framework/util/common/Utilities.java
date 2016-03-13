package pe.com.nextel.provisioning.framework.util.common;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaClass;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;

import pe.com.nextel.provisioning.framework.exception.FormatException;
import pe.com.nextel.provisioning.framework.exception.UtilException;
import pe.com.nextel.provisioning.framework.util.format.DateFormatter;

public class Utilities
{
	   private static Logger logger;

	    static 
	    {
	        logger = Logger.getLogger(pe.com.nextel.provisioning.framework.util.common.Utilities.class.getName());
	    }
    public Utilities()
    {
    }

    public static Object getObjectProperty(Object obj, String str)
        throws UtilException
    {
        try
        {
            if(obj == null)
            {
                return null;
            } else
            {
                Field f = obj.getClass().getDeclaredField(str);
                f.setAccessible(true);
                return f.get(obj) != null ? f.get(obj) : null;
            }
        }
        catch(NoSuchFieldException nsfex)
        {
            logger.error(nsfex.getMessage(), nsfex);
            throw new UtilException("Propiedad: " + str + (" no existe en Clase: " + obj.getClass().getName()));
        }
        catch(IllegalAccessException iacex)
        {
            logger.error(iacex.getMessage(), iacex);
        }
        throw new UtilException("Propiedad: " + str + (" no puede ser accedida en Clase: " + obj.getClass().getName()));
    }

    public static void setObjectProperty(Object objFrom, Object objTo, String str)
        throws UtilException
    {
        try
        {
            Field f = objTo.getClass().getDeclaredField(str);
            f.setAccessible(true);
            f.set(objTo, objFrom);
        }
        catch(NoSuchFieldException nsfex)
        {
            logger.error(nsfex.getMessage(), nsfex);
            throw new UtilException("Propiedad: " + str + (" no existe en Clase: " + objTo.getClass().getName()));
        }
        catch(IllegalAccessException iacex)
        {
            logger.error(iacex.getMessage(), iacex);
            throw new UtilException("Propiedad: " + str + (" no puede ser accedida en Clase: " + objTo.getClass().getName()));
        }
    }

    public static boolean isNull(Object obj)
    {
        return obj == null;
    }

    public static String evalToString(Object object)
    {
        if(object != null)
            return object.toString();
        else
            return "";
    }

    public static void cleanBean(Object obj)
    {
        if(obj == null)
            return;
        Field f[] = obj.getClass().getDeclaredFields();
        for(int k = 0; k < f.length; k++)
            try
            {
                BeanUtils.setProperty(obj, f[k].getName(), null);
            }
            catch(RuntimeException e)
            {
                System.out.println("cleanBean:RuntimeException" + f[k].getName());
                e.printStackTrace();
            }
            catch(Throwable e)
            {
                System.out.println("cleanBean:Throwable" + f[k].getName());
                e.printStackTrace();
            }

    }

    public static void copyPropertiesBeanToVO(Object objVO, Object objBean)
        throws IllegalAccessException, InvocationTargetException
    {
        Object orig = objBean;
        if(objVO == null)
            throw new IllegalArgumentException("No destination bean specified");
        if(orig == null)
            throw new IllegalArgumentException("No origin bean specified");
        if(orig instanceof DynaBean)
        {
            DynaProperty origDescriptors[] = ((DynaBean)orig).getDynaClass().getDynaProperties();
            for(int i = 0; i < origDescriptors.length; i++)
            {
                String name = origDescriptors[i].getName();
                if(PropertyUtils.isWriteable(objVO, name))
                {
                    Object value = ((DynaBean)orig).get(name);
                    copyProperty(objVO, name, value);
                }
            }

        } else
        if(orig instanceof Map)
        {
            for(Iterator names = ((Map)orig).keySet().iterator(); names.hasNext();)
            {
                String name = (String)names.next();
                if(PropertyUtils.isWriteable(objVO, name))
                {
                    Object value = ((Map)orig).get(name);
                    copyProperty(objVO, name, value);
                }
            }

        } else
        {
            PropertyDescriptor origDescriptors[] = PropertyUtils.getPropertyDescriptors(orig);
            for(int i = 0; i < origDescriptors.length; i++)
            {
                String name = origDescriptors[i].getName();
                if(!"class".equals(name) && PropertyUtils.isReadable(orig, name) && PropertyUtils.isWriteable(objVO, name))
                    try
                    {
                        Object value = PropertyUtils.getSimpleProperty(orig, name);
                        copyProperty(objVO, name, value);
                    }
                    catch(NoSuchMethodException nosuchmethodexception) { }
            }

        }
    }

    public static void copyPropertiesVOToBean(Object objBean, Object objVO)
        throws IllegalAccessException, InvocationTargetException
    {
        Object orig = objVO;
        PropertyDescriptor descriptor = null;
        if(objBean == null)
            throw new IllegalArgumentException("No destination bean specified");
        if(orig == null)
            throw new IllegalArgumentException("No origin bean specified");
        if(orig instanceof DynaBean)
        {
            DynaProperty origDescriptors[] = ((DynaBean)orig).getDynaClass().getDynaProperties();
            for(int i = 0; i < origDescriptors.length; i++)
            {
                String name = origDescriptors[i].getName();
                if(PropertyUtils.isWriteable(objBean, name))
                {
                    Object value = ((DynaBean)orig).get(name);
                    copyProperty(objBean, objVO, name, value);
                }
            }

        } else
        if(orig instanceof Map)
        {
            for(Iterator names = ((Map)orig).keySet().iterator(); names.hasNext();)
            {
                String name = (String)names.next();
                if(PropertyUtils.isWriteable(objBean, name))
                {
                    Object value = ((Map)orig).get(name);
                    copyProperty(objBean, objVO, name, value);
                }
            }

        } else
        {
            PropertyDescriptor origDescriptors[] = PropertyUtils.getPropertyDescriptors(orig);
            for(int i = 0; i < origDescriptors.length; i++)
            {
                String name = origDescriptors[i].getName();
                if(!"class".equals(name) && PropertyUtils.isReadable(orig, name) && PropertyUtils.isWriteable(objBean, name))
                    try
                    {
                        Object value = PropertyUtils.getSimpleProperty(orig, name);
                        copyProperty(objBean, objVO, name, value);
                    }
                    catch(NoSuchMethodException nosuchmethodexception) { }
            }

        }
    }

    private static void copyProperty(Object beanDest, String name, Object value)
        throws IllegalAccessException, InvocationTargetException
    {
        Object target = beanDest;
        int delim = name.lastIndexOf('.');
        if(delim >= 0)
        {
            try
            {
                target = PropertyUtils.getProperty(beanDest, name.substring(0, delim));
            }
            catch(NoSuchMethodException e)
            {
                return;
            }
            name = name.substring(delim + 1);
        }
        String propName = null;
        Class type = null;
        int index = -1;
        String key = null;
        propName = name;
        int i = propName.indexOf('[');
        if(i >= 0)
        {
            int k = propName.indexOf(']');
            try
            {
                index = Integer.parseInt(propName.substring(i + 1, k));
            }
            catch(NumberFormatException numberformatexception) { }
            propName = propName.substring(0, i);
        }
        int j = propName.indexOf('(');
        if(j >= 0)
        {
            int k = propName.indexOf(')');
            try
            {
                key = propName.substring(j + 1, k);
            }
            catch(IndexOutOfBoundsException indexoutofboundsexception) { }
            propName = propName.substring(0, j);
        }
        if(target instanceof DynaBean)
        {
            DynaClass dynaClass = ((DynaBean)target).getDynaClass();
            DynaProperty dynaProperty = dynaClass.getDynaProperty(propName);
            if(dynaProperty == null)
                return;
            type = dynaProperty.getType();
        } else
        {
            PropertyDescriptor descriptor = null;
            try
            {
                descriptor = PropertyUtils.getPropertyDescriptor(target, name);
                if(descriptor == null)
                    return;
            }
            catch(NoSuchMethodException e)
            {
                return;
            }
            type = descriptor.getPropertyType();
            if(type == null)
                return;
        }
        if(index >= 0)
        {
            Converter converter = ConvertUtils.lookup(type.getComponentType());
            if(converter != null)
                value = converter.convert(type, value);
            try
            {
                PropertyUtils.setIndexedProperty(target, propName, index, value);
            }
            catch(NoSuchMethodException e)
            {
                throw new InvocationTargetException(e, "Cannot set " + propName);
            }
        } else
        if(key != null)
        {
            try
            {
                PropertyUtils.setMappedProperty(target, propName, key, value);
            }
            catch(NoSuchMethodException e)
            {
                throw new InvocationTargetException(e, "Cannot set " + propName);
            }
        } else
        {
            Converter converter = ConvertUtils.lookup(type);
            if(converter != null && value != null)
                if(type.getName().equals("java.sql.Timestamp"))
                {
                    if("".equals(value.toString()))
                        value = null;
                    else
                        value = new Timestamp(getLongSpecificDate(value.toString()));
                } else
                if(type.getName().equals("java.math.BigDecimal"))
                {
                    if("".equals(value.toString()))
                        value = null;
                    else
                        value = converter.convert(type, value);
                } else
                {
                    value = converter.convert(type, value);
                }
            try
            {
                PropertyUtils.setSimpleProperty(target, propName, value);
            }
            catch(NoSuchMethodException e)
            {
                throw new InvocationTargetException(e, "Cannot set " + propName);
            }
        }
    }

    private static long getLongSpecificDate(String specificDate)
    {
        try
        {
            return DateFormatter.parseDate(specificDate).getTime();
        }
        catch(FormatException formatexception)
        {
            return 0L;
        }
    }

    private static void copyProperty(Object beanDest, Object VOOrig, String name, Object value)
        throws IllegalAccessException, InvocationTargetException
    {
        Object target = VOOrig;
        int delim = name.lastIndexOf('.');
        if(delim >= 0)
        {
            try
            {
                target = PropertyUtils.getProperty(target, name.substring(0, delim));
            }
            catch(NoSuchMethodException e)
            {
                return;
            }
            name = name.substring(delim + 1);
        }
        String propName = null;
        Class type = null;
        int index = -1;
        String key = null;
        propName = name;
        int i = propName.indexOf('[');
        if(i >= 0)
        {
            int k = propName.indexOf(']');
            try
            {
                index = Integer.parseInt(propName.substring(i + 1, k));
            }
            catch(NumberFormatException numberformatexception) { }
            propName = propName.substring(0, i);
        }
        int j = propName.indexOf('(');
        if(j >= 0)
        {
            int k = propName.indexOf(')');
            try
            {
                key = propName.substring(j + 1, k);
            }
            catch(IndexOutOfBoundsException indexoutofboundsexception) { }
            propName = propName.substring(0, j);
        }
        if(target instanceof DynaBean)
        {
            DynaClass dynaClass = ((DynaBean)target).getDynaClass();
            DynaProperty dynaProperty = dynaClass.getDynaProperty(propName);
            if(dynaProperty == null)
                return;
            type = dynaProperty.getType();
        } else
        {
            PropertyDescriptor descriptor = null;
            try
            {
                descriptor = PropertyUtils.getPropertyDescriptor(target, name);
                if(descriptor == null)
                    return;
            }
            catch(NoSuchMethodException e)
            {
                return;
            }
            type = descriptor.getPropertyType();
            if(type == null)
                return;
        }
        if(index >= 0)
        {
            Converter converter = ConvertUtils.lookup(type.getComponentType());
            if(converter != null)
                value = converter.convert(type, value);
            try
            {
                PropertyUtils.setIndexedProperty(target, propName, index, value);
            }
            catch(NoSuchMethodException e)
            {
                throw new InvocationTargetException(e, "Cannot set " + propName);
            }
        } else
        if(key != null)
        {
            try
            {
                PropertyUtils.setMappedProperty(target, propName, key, value);
            }
            catch(NoSuchMethodException e)
            {
                throw new InvocationTargetException(e, "Cannot set " + propName);
            }
        } else
        {
            Converter converter = ConvertUtils.lookup(type);
            if(converter != null)
                if(type.getName().equals("java.sql.Timestamp"))
                {
                    if(value == null)
                        value = "";
                    else
                        value = DateFormatter.convertTimestampToString((Timestamp)value);
                } else
                if(type.getName().equals("java.math.BigDecimal"))
                    if(value == null)
                        value = "";
                    else
                        value = converter.convert(type, value).toString();
            try
            {
                PropertyUtils.setSimpleProperty(beanDest, propName, value);
            }
            catch(NoSuchMethodException e)
            {
                throw new InvocationTargetException(e, "Cannot set " + propName);
            }
        }
    }

    public static String[] validateSelectProperty(String strings[])
    {
        String rpta[] = (String[])null;
        if(strings.length == 1 && strings[0].equals(""))
            rpta = (String[])null;
        else
            rpta = strings;
        return rpta;
    }

    public static String[] validateSelectProperty1(String strings[])
    {
        String rpta[] = (String[])null;
        if(strings.length == 1 && strings[0].equals(""))
            rpta = (String[])null;
        else
            rpta = strings;
        return rpta;
    }

 
}