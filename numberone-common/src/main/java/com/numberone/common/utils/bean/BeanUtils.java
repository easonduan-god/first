package com.numberone.common.utils.bean;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;


/**
 * Bean 工具类
 * 
 * @author numberone
 */
public class BeanUtils
{
    /** Bean方法名中属性名开始的下标 */
    private static final int BEAN_METHOD_PROP_INDEX = 3;

    /** * 匹配getter方法的正则表达式 */
    private static final Pattern GET_PATTERN = Pattern.compile("get(\\p{javaUpperCase}\\w*)");

    /** * 匹配setter方法的正则表达式 */
    private static final Pattern SET_PATTERN = Pattern.compile("set(\\p{javaUpperCase}\\w*)");
    
    public static void copyProperties(Object target, Object source){
    	copyProperties(target, source, false);
    }
	/**
	 * 拷贝属性<br/>
	 * 有如下2个优势<br/>
	 * 1. 不会像apache的BeanUtils那样,给Long这种封装类型在为null时设值为0<br/>
	 * 2. 不会像spring的BeanHelper那样不会自动转换类型<br/>
	 * 注意：<br/>
	 * 如果源bean中字段是date，而目标bean值不是date，则根据yyyyMMdd的格式转换为String之后赋值<br/>
	 * 如果源bean中字段不是Date,而目标bean值是date，同理根据yyyyMMdd的格式转换成Date之后赋值
	 * 
	 * @param source
	 *            源
	 * @param target
	 *            目标
	 * @param ignoreNull
	 *            是否忽略空值，默认为否
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void copyProperties(Object target, Object source,
			boolean ignoreNull) {
		if (source instanceof Map) {
			Map sourceMap = (Map) source;
			Set<Entry<String, Object>> entrySet = sourceMap.entrySet();
			for (Iterator iterator = entrySet.iterator(); iterator.hasNext();) {
				try {
					Entry<String, Object> entry = (Entry<String, Object>) iterator
							.next();
					BeanUtils.setProperty(target, entry.getKey(),
							entry.getValue(), ignoreNull);
				} catch (Exception e) {
					System.out.println("warn:" + e.getMessage());
				}
			}
		} else {
			PropertyDescriptor[] sourceProperties = PropertyUtils
					.getPropertyDescriptors(source);
			for (int i = 0; i < sourceProperties.length; i++) {
				try {
					String sourcefieldName = sourceProperties[i].getName();
					Object sourcefieldValue = null;
					sourcefieldValue = PropertyUtils.getProperty(source,
							sourcefieldName);
					BeanUtils.setProperty(target, sourcefieldName,
							sourcefieldValue, ignoreNull);
				} catch (Exception e) {
					System.out.println("warn:" + e.getMessage());
				}
			}
		}
	}
    /**
     * Bean属性复制工具方法。
     * 
     * @param dest 目标对象
     * @param src 源对象
     */
    public static void copyBeanProp(Object dest, Object src)
    {
        List<Method> destSetters = getSetterMethods(dest);
        List<Method> srcGetters = getGetterMethods(src);
        try
        {
            for (Method setter : destSetters)
            {
                for (Method getter : srcGetters)
                {
                    if (isMethodPropEquals(setter.getName(), getter.getName())
                            && setter.getParameterTypes()[0].equals(getter.getReturnType()))
                    {
                        setter.invoke(dest, getter.invoke(src));
                    }
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 获取对象的setter方法。
     * 
     * @param obj 对象
     * @return 对象的setter方法列表
     */
    public static List<Method> getSetterMethods(Object obj)
    {
        // setter方法列表
        List<Method> setterMethods = new ArrayList<Method>();

        // 获取所有方法
        Method[] methods = obj.getClass().getMethods();

        // 查找setter方法

        for (Method method : methods)
        {
            Matcher m = SET_PATTERN.matcher(method.getName());
            if (m.matches() && (method.getParameterTypes().length == 1))
            {
                setterMethods.add(method);
            }
        }
        // 返回setter方法列表
        return setterMethods;
    }

    /**
     * 获取对象的getter方法。
     * 
     * @param obj 对象
     * @return 对象的getter方法列表
     */

    public static List<Method> getGetterMethods(Object obj)
    {
        // getter方法列表
        List<Method> getterMethods = new ArrayList<Method>();
        // 获取所有方法
        Method[] methods = obj.getClass().getMethods();
        // 查找getter方法
        for (Method method : methods)
        {
            Matcher m = GET_PATTERN.matcher(method.getName());
            if (m.matches() && (method.getParameterTypes().length == 0))
            {
                getterMethods.add(method);
            }
        }
        // 返回getter方法列表
        return getterMethods;
    }

    /**
     * 检查Bean方法名中的属性名是否相等。<br>
     * 如getName()和setName()属性名一样，getName()和setAge()属性名不一样。
     * 
     * @param m1 方法名1
     * @param m2 方法名2
     * @return 属性名一样返回true，否则返回false
     */

    public static boolean isMethodPropEquals(String m1, String m2)
    {
        return m1.substring(BEAN_METHOD_PROP_INDEX).equals(m2.substring(BEAN_METHOD_PROP_INDEX));
    }
    public static final String DateFormatString = "yyyyMMdd";
	/**
	 * 自动转换类型设置参数<br/>
	 * 注意，如果字段是date，而值不是date，则抓换为String之后，根据yyyyMMdd的格式转换成date
	 * 如果字段是String而值是date，同理转换成string
	 * 
	 * @param bean
	 * @param field
	 * @param value
	 * @throws Exception 
	 */
	@SuppressWarnings({ "deprecation", "unchecked", "rawtypes" })
	public static void setProperty(final Object bean, final String field,
			Object value, boolean ingoreNull) throws Exception {
		try {
			// 如果对象是Map
			if (bean instanceof Map) {
				if (value == null && ingoreNull) {
					// 什么也不做
				} else {
					((Map) bean).put(field, value);
				}

			} else {
				PropertyDescriptor dis = PropertyUtils.getPropertyDescriptor(
						bean, field);

				if (dis == null) {
					// System.err.println("警告未找到类" + bean.getClass().getName() +
					// "的"
					// + field + "属性");
					return;
				}
				String nameType = dis.getPropertyType().getSimpleName();

				if (value == null) {
					if (ingoreNull) {
						// 什么也不做
					} else {
						PropertyUtils.setProperty(bean, field, null);
					}
				} else {
					// 如果源是date
					if (nameType.equalsIgnoreCase("Date")
							|| nameType.equalsIgnoreCase("Timestamp")) {
						// 目标也是date，则赋值，返回
						if (value instanceof Date) {
							PropertyUtils.setProperty(bean, field, value);
						} else {// 目标不是date，尝试转换
							String tmp = value.toString();
							try {
								SimpleDateFormat sdf = new SimpleDateFormat(
										DateFormatString);
								if (tmp.indexOf("-") == -1) {
									Date date = sdf.parse(tmp);
									PropertyUtils
											.setProperty(bean, field, date);
								} else {
									Date date = sdf.parse(tmp.replaceAll("-",
											"").substring(0, 8));
									PropertyUtils
											.setProperty(bean, field, date);
								}
							} finally {

							}
						}
					} else {// 源不是date
							// 目标是date
						if (value instanceof Date) {
							SimpleDateFormat sdf = new SimpleDateFormat(
									DateFormatString);
							value = sdf.format((Date) value);
						}

						Object setValue = null;
						if (nameType.equalsIgnoreCase("BigDecimal")) {
							if (NumberUtils.isNumber(ObjectUtils
									.toString(value))) {
								setValue = stringToBigDecimal(ObjectUtils
										.toString(value));
								PropertyUtils
										.setProperty(bean, field, setValue);
							}
						} else if (nameType.equalsIgnoreCase("String")) {
							setValue = ObjectUtils.toString(value);
							PropertyUtils.setProperty(bean, field, setValue);
						} else if (nameType.equalsIgnoreCase("Integer")) {
							setValue = ObjectUtils.toString(value);

							if (StringUtils.isBlank((String) setValue)) {
								PropertyUtils.setProperty(bean, field, null);
							} else {
								if (NumberUtils.isNumber(ObjectUtils
										.toString(value))) {
									setValue = Integer
											.parseInt((String) setValue);
									PropertyUtils.setProperty(bean, field,
											setValue);
								}
							}
						} else if (nameType.equalsIgnoreCase("Boolean")) {
							PropertyUtils.setProperty(bean, field, Boolean
									.valueOf(ObjectUtils.toString(value)));
						} else if (nameType.equalsIgnoreCase("Long")) {
							setValue = ObjectUtils.toString(value);

							if (StringUtils.isBlank((String) setValue)) {
								PropertyUtils.setProperty(bean, field, null);
							} else {
								if (NumberUtils.isNumber(ObjectUtils
										.toString(value))) {
									PropertyUtils.setProperty(bean, field, Long
											.valueOf(ObjectUtils
													.toString(value)));
								}
							}

						} else if (nameType.equalsIgnoreCase("Float")) {
							setValue = ObjectUtils.toString(value);

							if (StringUtils.isBlank((String) setValue)) {
								PropertyUtils.setProperty(bean, field, null);
							} else {
								if (NumberUtils.isNumber(ObjectUtils
										.toString(value))) {
									PropertyUtils.setProperty(bean, field,
											Float.valueOf(ObjectUtils
													.toString(value)));
								}
							}

						} else if (nameType.equalsIgnoreCase("Double")) {
							setValue = ObjectUtils.toString(value);

							if (StringUtils.isBlank((String) setValue)) {
								PropertyUtils.setProperty(bean, field, null);
							} else {
								if (NumberUtils.isNumber(ObjectUtils
										.toString(value))) {
									PropertyUtils.setProperty(bean, field,
											Double.valueOf(ObjectUtils
													.toString(value)));
								}
							}
						} else if (nameType.equalsIgnoreCase("Short")) {
							setValue = ObjectUtils.toString(value);

							if (StringUtils.isBlank((String) setValue)) {
								PropertyUtils.setProperty(bean, field, null);
							} else {
								if (NumberUtils.isNumber(ObjectUtils
										.toString(value))) {
									PropertyUtils.setProperty(bean, field,
											Short.valueOf(ObjectUtils
													.toString(value)));
								}
							}
						} else {
							if (!"Class".equals(nameType)) {
								throw new Exception("不支持的类型" + nameType);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	public static BigDecimal stringToBigDecimal(String bigStr) {
		if (StringUtils.isBlank(bigStr)) {
			return BigDecimal.ZERO;
		} else {
			return new BigDecimal(bigStr);
		}
	}
}
