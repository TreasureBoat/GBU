/**
 * The MIT License (MIT)
 * Copyright (c) 2014 TreasureBoat
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.webobjects.foundation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public final class _NSUtilities {
	public static final Class<?> _CLASS = _NSUtilitiesExtra._classWithFullySpecifiedNamePrime("com.webobjects.foundation._NSUtilities");

	private static final _NSThreadsafeMutableDictionary<String, Class<?>> _classesByPartialName = new _NSThreadsafeMutableDictionary<String, Class<?>>(new NSMutableDictionary<String, Class<?>>(1024));

	private static final _NSThreadsafeMutableArray<String> _packages = new _NSThreadsafeMutableArray<String>(new NSMutableArray<String>(32));

	private static volatile _ResourceSearcher _privateResourceSearcher = null;

	static boolean _principalClassLoadingWarningsNeeded = true;
	public static final String WebObjectsRootPropertyKey = "WORootDirectory";
	public static final String WebObjectsLocalRootPropertyKey = "WOLocalRootDirectory";
	public static final String WebObjectsUserDirectoryPropertyKey = "WOUserDirectory";
	public static final String WebObjectsVersionPropertyKey = "webobjects.version";
	public static final Class<?> _ObjectClass = _classWithFullySpecifiedName("java.lang.Object");

	public static final Class<?> _ClassClass = _classWithFullySpecifiedName("java.lang.Class");

	public static final Class<?> _StringClass = _classWithFullySpecifiedName("java.lang.String");

	public static final Class<?> _NumberClass = _classWithFullySpecifiedName("java.lang.Number");

	public static final Class<?> _BigDecimalClass = _classWithFullySpecifiedName("java.math.BigDecimal");

	public static final Class<?> _BigIntegerClass = _classWithFullySpecifiedName("java.math.BigInteger");

	public static final Class<?> _BooleanClass = _classWithFullySpecifiedName("java.lang.Boolean");

	public static final Class<?> _DateClass = _classWithFullySpecifiedName("java.util.Date");

	public static final Class<?> _LocaleClass = _classWithFullySpecifiedName("java.util.Locale");

	public static final Class<?> _ShortClass = _classWithFullySpecifiedName("java.lang.Short");

	public static final Class<?> _ByteClass = _classWithFullySpecifiedName("java.lang.Byte");

	public static final Class<?> _IntegerClass = _classWithFullySpecifiedName("java.lang.Integer");

	public static final Class<?> _LongClass = _classWithFullySpecifiedName("java.lang.Long");

	public static final Class<?> _DoubleClass = _classWithFullySpecifiedName("java.lang.Double");

	public static final Class<?> _FloatClass = _classWithFullySpecifiedName("java.lang.Float");

	public static final Class<?> _VoidClass = _classWithFullySpecifiedName("java.lang.Void");

	public static final Class<?> _CharacterClass = _classWithFullySpecifiedName("java.lang.Character");

	public static final Object[] _NoObjectArray = new Object[0];

	public static final byte[] _NoByteArray = new byte[0];

	public static final int[] _NoIntArray = new int[0];

	public static final Class<?>[] _NoClassArray = new Class[0];

	public static final String[] _NoStringArray = new String[0];

	public static final Class<?>[] _ObjectClassArray = { _ObjectClass };

	public static final Class<?>[] _StringClassArray = { _StringClass };

	public static final Class<?>[] _ArrayClassArray = { NSArray._CLASS };

	public static final Class<?>[] _DictionaryClassArray = { NSDictionary._CLASS };

	public static final Class<?>[] _NotificationClassArray = { NSNotification._CLASS };

	public static final Class<?> _DateFormatSymbolsClass = _classWithFullySpecifiedName("java.text.DateFormatSymbols");

	public static final Class<?> _DecimalFormatSymbolsClass = _classWithFullySpecifiedName("java.text.DecimalFormatSymbols");

	public static final Class<?> _ObjectArrayClass = new Object[0].getClass();

	private static final JavaClassFilter TheJavaClassFilter = new JavaClassFilter();

	private static final BigDecimal _zeroBigDecimal = BigDecimal.valueOf(0L);

	private static final BigInteger _zeroBigInteger = BigInteger.valueOf(0L);
	private static final Integer[] _integers;
	private static final int _intMin = -3;
	private static final int _intMax = 513;
	private static final int _intOffset = 3;
	protected static final Short _shortFalse;
	protected static final Short _shortTrue;
	private static final String _isoLatinEncoding;
	public static final String MacOSRomanStringEncoding = "MacRoman";
	public static final String ASCIIStringEncoding = "ASCII";
	public static final String NEXTSTEPStringEncoding;
	public static final String JapaneseEUCStringEncoding = "EUC_JP";
	public static final String UTF8StringEncoding = "UTF-8";
	public static final String ISOLatin1StringEncoding;
	public static final String SymbolStringEncoding = "MacSymbol";
	public static final String NonLossyASCIIStringEncoding = "ASCII";
	public static final String ShiftJISStringEncoding = "SJIS";
	public static final String ISOLatin2StringEncoding = "ISO8859_2";
	public static final String UnicodeStringEncoding = "Unicode";
	public static final String WindowsCP1251StringEncoding = "Cp1251";
	public static final String WindowsCP1252StringEncoding = "Cp1252";
	public static final String WindowsCP1253StringEncoding = "Cp1253";
	public static final String WindowsCP1254StringEncoding = "Cp1254";
	public static final String WindowsCP1250StringEncoding = "Cp1250";
	public static final String ISO2022JPStringEncoding = "ISO2022JP";

	public static Object valueForKeyArray(NSKeyValueCoding kvcObject, NSArray<String> keyArray) {
		Object retVal = null;

		Object currentObject = kvcObject;
		if (keyArray != null) {
			for (String key : keyArray) {
				if (currentObject instanceof NSKeyValueCoding) {
					currentObject = ((NSKeyValueCoding) currentObject).valueForKey(key);
				}
				if (currentObject == null)
					return null;
			}
			retVal = currentObject;
		}
		return retVal;
	}

	public static Integer IntegerForInt(int i) {
		if ((i < _intMax) && (i >= _intMin)) {
			return _integers[(i + _intOffset)];
		}
		return new Integer(i);
	}

	public static String shortClassName(Object obj) {
		if (obj == null) {
			return "null";
		}
		return _NSStringUtilities.lastComponentInString(obj.getClass().getName(), '.');
	}

	public static Class<?> _classWithFullySpecifiedName(String className) {
		Throwable throwable = null;
		try {
			Class<?> result = Class.forName(className);
			_classesByPartialName.setObjectForKey(result, className);
			return result;
		} catch (ClassNotFoundException exception) {
			throwable = exception;
		} catch (ClassFormatError exception) {
			throwable = exception;
		} catch (SecurityException exception) {
			throwable = exception;
		}

		NSLog.err.appendln("Cannot load class with name " + className);
		if (NSLog.debugLoggingAllowedForLevel(1)) {
			NSLog.debug.appendln(throwable);
		}
		throw NSForwardException._runtimeExceptionForThrowable(throwable);
	}

	public static Class<?> classObjectForClass(Class<?> objectClass) {
		if (objectClass.isPrimitive()) {
			if (objectClass == Boolean.TYPE)
				return _BooleanClass;
			if (objectClass == Short.TYPE)
				return _ShortClass;
			if (objectClass == Integer.TYPE)
				return _IntegerClass;
			if (objectClass == Long.TYPE)
				return _LongClass;
			if (objectClass == Double.TYPE)
				return _DoubleClass;
			if (objectClass == Float.TYPE)
				return _FloatClass;
			if (objectClass == Character.TYPE)
				return _CharacterClass;
			if (objectClass == Void.TYPE) {
				return _VoidClass;
			}
		}
		return objectClass;
	}

	public static _ResourceSearcher _resourceSearcher() {
		return _privateResourceSearcher;
	}

	public static void _setResourceSearcher(_ResourceSearcher resourceSearcher) {
		_privateResourceSearcher = resourceSearcher;
	}

	public static URL _pathURLForResourceWithName(Class<?> resourceClass, String resourceName, String extension) {
		_ResourceSearcher searcher = _privateResourceSearcher;
		return ((searcher != null) ? searcher._searchPathURLForResourceWithName(resourceClass, resourceName, extension) : null);
	}

	public static void _setPrincipalClassLoadingWarningsNeeded(boolean flag) {
		_principalClassLoadingWarningsNeeded = flag;
	}

	public static void registerPackage(String packageName) {
		if (packageName != null)
			_packages.addObjectIfAbsent(packageName);
	}

	public static void setClassForName(Class<?> objectClass, String className) {
		_classesByPartialName.setObjectForKey(objectClass, className);
	}

	public static Class<?> classWithName(String className) {
		if ((className != null) && (className.length() > 0)) {
			Class<?> objectClass = _classWithPartialName(className, className.indexOf('.') < 0);
			if (objectClass != null) {
				return objectClass;
			}
		}
		return null;
	}

	static Class<?> _classWithPartialName(String partialName, boolean extensiveSearch) {
		_NSThreadsafeMutableDictionary<String, Class<?>> classesByPartialName = _classesByPartialName;
		Class<?> objectClass = classesByPartialName.objectForKey(partialName);
		if (objectClass != null) {
			return ((objectClass != _NoClassUnderTheSun._CLASS) ? objectClass : null);
		}
		try {
			objectClass = Class.forName(partialName);
			if (objectClass != null) {
				classesByPartialName.setObjectForKey(objectClass, partialName);
				return objectClass;
			}
		} catch (ClassNotFoundException e) {
			NSLog._conditionallyLogPrivateException(e);
		} catch (ClassFormatError e) {
			NSLog._conditionallyLogPrivateException(e);
		} catch (SecurityException e) {
			NSLog._conditionallyLogPrivateException(e);
		} catch (IllegalArgumentException e) {
			NSLog.err.appendln("The class name \"" + partialName + "\" is not a valid Java class name.");
			if (NSLog.debugLoggingAllowedForLevel(1)) {
				NSLog.debug.appendln(e);
			}
			throw e;
		}

		if (!(extensiveSearch)) {
			return null;
		}

		_packages.acquireReadLock();
		try {
			objectClass = _searchForClassInPackages(partialName, _packages.array(), false, true);
		} finally {
			_packages.releaseReadLock();
		}

		if (objectClass != null) {
			return objectClass;
		}

		_ResourceSearcher searcher = _privateResourceSearcher;
		if (searcher != null) {
			objectClass = searcher._searchForClassWithName(partialName);
			if (objectClass != null) {
				return objectClass;
			}
		}

		classesByPartialName.setObjectForKey(_NoClassUnderTheSun._CLASS, partialName);
		return null;
	}

	static Class<?> _searchForClassInPackages(String className, NSMutableArray<String> packages, boolean registerPackageOnHit, boolean sortPackageArray) {
		int count = packages.count();
		StringBuffer buffer = new StringBuffer(64 + className.length());

		for (int i = 0; i < count; ++i) {
			String packageName = (String) (String) packages.objectAtIndex(i);
			String toast;
			if (packageName.length() > 0) {
				buffer.append(packageName);
				buffer.append('.');
				buffer.append(className);
				toast = buffer.toString();
			} else {
				toast = className;
			}
			try {
				Class<?> result = Class.forName(toast);
				if (result != null) {
					_classesByPartialName.setObjectForKey(result, className);

					if (registerPackageOnHit) {
						registerPackage(packageName);
					}

					if (sortPackageArray) {
						packages._moveObjectAtIndexToIndex(i, 0);
					}

					return result;
				}
			} catch (ClassNotFoundException e) {
				NSLog._conditionallyLogPrivateException(e);
			} catch (ClassFormatError e) {
				NSLog._conditionallyLogPrivateException(e);
			} catch (SecurityException e) {
				NSLog._conditionallyLogPrivateException(e);
			}

			buffer.setLength(0);
		}

		return null;
	}

	public static NSArray<String> classNamesFromArchiveInputStream(InputStream is) {
		NSMutableArray<String> classes = null;

		if (is != null) {
			classes = new NSMutableArray<String>();
			ZipInputStream zipStream;
			if (is instanceof ZipInputStream)
				zipStream = (ZipInputStream) is;
			else
				zipStream = new ZipInputStream(is);
			ZipEntry nextEntry;
			try {
				nextEntry = zipStream.getNextEntry();
			} catch (IOException e) {
				throw NSForwardException._runtimeExceptionForThrowable(e);
			}

			if (nextEntry != null) {
				do {
					String nextName = nextEntry.getName();
					if (TheJavaClassFilter.accept(null, nextName)) {
						String nextClassName = nextName.substring(0, nextName.lastIndexOf(46));

						nextClassName = nextClassName.replace('/', '.');
						nextClassName = nextClassName.intern();
						classes.add(nextClassName);
					}
					try {
						nextEntry = zipStream.getNextEntry();
					} catch (IOException e) {
						throw NSForwardException._runtimeExceptionForThrowable(e);
					}
				} while (nextEntry != null);
			}
			try {
				zipStream.close();
			} catch (IOException e) {
				throw NSForwardException._runtimeExceptionForThrowable(e);
			}
		}

		if ((classes == null) || (classes.count() == 0)) {
			return NSArray.emptyArray();
		}
		return classes;
	}

	public static NSArray<String> classNamesFromArchive(File anArchive) {
		if ((anArchive != null) && (anArchive.length() > 0L)) {
			try {
				return classNamesFromArchiveInputStream(new FileInputStream(anArchive));
			} catch (FileNotFoundException fnfe) {
				throw NSForwardException._runtimeExceptionForThrowable(fnfe);
			}
		}
		return NSArray.emptyArray();
	}

	public static NSArray<String> classNamesFromArchiveAtPathURL(URL archivePathURL) {
		if (archivePathURL != null) {
			try {
				return classNamesFromArchiveInputStream(archivePathURL.openStream());
			} catch (IOException ioe) {
				throw NSForwardException._runtimeExceptionForThrowable(ioe);
			}
		}

		return NSArray.emptyArray();
	}

	public static NSArray<String> classNamesFromArchiveAtPath(String archivePath) {
		if ((archivePath != null) && (!(archivePath.equals("")))) {
			File anArchive = new File(archivePath);
			return classNamesFromArchive(anArchive);
		}

		return NSArray.emptyArray();
	}

	private static RuntimeException _explainInstantiationException(Class<?> objectClass, Class<?>[] parameters, Throwable throwable, boolean shouldLog) {
		StringBuffer buffer = new StringBuffer(32);
		buffer.append('(');
		if (parameters != null) {
			for (int i = 0; i < parameters.length; ++i) {
				if (i > 0) {
					buffer.append(", ");
				}
				buffer.append((parameters[i] == null) ? "null" : parameters[i].getName());
			}
		}
		buffer.append(')');

		String parametersDescription = new String(buffer);
		String className = (objectClass == null) ? "null" : objectClass.getName();
		if (throwable instanceof InstantiationException)
			return new NSForwardException(throwable, "<" + className + "> is an abstract class. It can not be instantiated !");
		if (throwable instanceof IllegalArgumentException) {
			return new NSForwardException(throwable, "<" + className + "> Constructor 'new " + className + " " + parametersDescription + "' has wrong number of arguments, object can not be instantiated.");
		}
		if (throwable instanceof NoSuchMethodException)
			return new NSForwardException(throwable, "<" + className + "> Constructor 'new " + className + " " + parametersDescription + "' does not exist, component can not be instantiated.");
		if (throwable instanceof IllegalAccessException)
			return new NSForwardException(throwable, "<" + className + "> Could not invoke constructor 'new " + className + parametersDescription + "' as it is not public.");
		if (throwable instanceof InvocationTargetException) {
			return new NSForwardException(throwable);
		}
		if (throwable instanceof NoSuchMethodError)
			return new NSForwardException(throwable, "<" + className + "> Could not find default initializer method " + className + "() :" + throwable.toString());
		if (throwable instanceof SecurityException) {
			return new NSForwardException(throwable, "<" + className + "> There is no permission to create a new instance of " + className + ".");
		}
		return new NSForwardException(throwable, "<" + className + "> failed instantiation. Exception thrown :\n" + throwable + ": " + throwable.getMessage());
	}

	public static Object instantiateObject(Class<?> objectClass, Class<?>[] parameterTypes, Object[] parameters, boolean shouldThrow, boolean shouldLog) {
		try {
			if ((parameterTypes != null) && (parameters != null)) {
				Constructor<?> constructor = objectClass.getDeclaredConstructor(parameterTypes);
				return constructor.newInstance(parameters);
			}
			return objectClass.newInstance();
		} catch (Throwable throwable) {
			NSLog._conditionallyLogPrivateException(throwable);
			RuntimeException exception = _explainInstantiationException(objectClass, parameterTypes, throwable, shouldLog);
			if (shouldThrow)
				throw exception;
			if (shouldLog) {
				NSLog.err.appendln("<" + ((objectClass == null) ? "null" : objectClass.getName()) + "> Ignoring Exception During Instantiation: " + exception.toString());
			}
		}
		return null;
	}

	public static Object instantiateObjectWithConstructor(
		Constructor<?> constructor,
		Class<?> objectClass,
		Object[] parameters,
		boolean shouldThrow,
		boolean shouldLog) {
		try {
			if ((constructor != null) && (parameters != null)) {
				return constructor.newInstance(parameters);
			}
			return objectClass.newInstance();
		} catch (Throwable throwable) {
			NSLog._conditionallyLogPrivateException(throwable);
			RuntimeException exception = _explainInstantiationException(objectClass, null, throwable, shouldLog);
			if (shouldThrow)
				throw exception;
			if (shouldLog) {
				NSLog.err.appendln("<" + ((objectClass == null) ? "null" : objectClass.getName()) + "> Ignoring Exception During Instantiation: " + exception.toString());
			}
		}
		return null;
	}

	public static Constructor<?> constructorForClass(Class<?> objectClass, Class<?>[] parameterTypes) {
		try {
			return objectClass.getConstructor(parameterTypes);
		} catch (NoSuchMethodException ex) {
			NSLog._conditionallyLogPrivateException(ex);
		} catch (SecurityException ex) {
			NSLog._conditionallyLogPrivateException(ex);
		}

		return null;
	}

	public static boolean _isClassABoolean(Class<?> valueClass) {
		return ((valueClass == Boolean.TYPE) || (valueClass == _BooleanClass));
	}

	public static boolean _isClassANumber(Class<?> valueClass) {
		return ((valueClass == _NumberClass) || (valueClass == Integer.TYPE) || (valueClass == _IntegerClass) || (valueClass == Double.TYPE) || (valueClass == _DoubleClass) || (Number.class.isAssignableFrom(valueClass)) || (valueClass == Long.TYPE) || (valueClass == Float.TYPE) || (valueClass == Byte.TYPE) || (valueClass == Short.TYPE));
	}

	public static boolean _isClassANumberOrABoolean(Class<?> valueClass) {
		return ((_isClassANumber(valueClass)) || (_isClassABoolean(valueClass)));
	}

	public static Object tryToConvertIntoNumberOrBooleanValueClass(Object value, Class<?> valueClass) {
		if ((value != null) && (valueClass != null)) {
			Class<?> originalValueClass = value.getClass();
			if (_isClassABoolean(valueClass)) {
				if (_isClassABoolean(originalValueClass))
					return value;
				if (_isClassANumber(originalValueClass))
					return convertNumberIntoBooleanValue((Number) value);
			} else if (_isClassANumber(valueClass)) {
				if (_isClassABoolean(originalValueClass))
					return convertBooleanIntoCompatibleNumberValue((Boolean) value, valueClass);
				if (_isClassANumber(originalValueClass)) {
					return convertNumberIntoCompatibleValue((Number) value, valueClass);
				}
			}
		}
		return value;
	}

	public static Number convertBooleanIntoNumberValue(Boolean value) {
		if (value != null) {
			return ((value.booleanValue()) ? _shortTrue : _shortFalse);
		}
		return null;
	}

	public static Boolean convertNumberIntoBooleanValue(Number value) {
		if (value != null) {
			Class<?> valueClass = value.getClass();
			if ((valueClass == _IntegerClass) || (valueClass == _ShortClass) || (valueClass == _ByteClass))
				return ((value.intValue() != 0) ? Boolean.TRUE : Boolean.FALSE);
			if (valueClass == _LongClass)
				return ((value.longValue() != 0L) ? Boolean.TRUE : Boolean.FALSE);
			if ((valueClass == _DoubleClass) || (valueClass == _FloatClass))
				return ((value.doubleValue() != 0.0D) ? Boolean.TRUE : Boolean.FALSE);
			if (valueClass == _BigDecimalClass)
				return ((((BigDecimal) value).compareTo(_zeroBigDecimal) != 0) ? Boolean.TRUE : Boolean.FALSE);
			if (valueClass == _BigIntegerClass) {
				return ((((BigInteger) value).compareTo(_zeroBigInteger) != 0) ? Boolean.TRUE : Boolean.FALSE);
			}
			return ((value.doubleValue() != 0.0D) ? Boolean.TRUE : Boolean.FALSE);
		}
		return null;
	}

	public static Number convertBooleanIntoCompatibleNumberValue(Boolean value, Class<?> newValueType) {
		return convertNumberIntoCompatibleValue(convertBooleanIntoNumberValue(value), newValueType);
	}

	public static Number convertNumberIntoCompatibleValue(Number value, Class<?> newValueType) {
		if (value != null) {
			Class<?> valueClass = value.getClass();
			if (!(valueClass.isAssignableFrom(newValueType))) {
				if ((newValueType == Integer.TYPE) || (newValueType == Integer.class))
					return IntegerForInt(value.intValue());
				if ((newValueType == Double.TYPE) || (newValueType == Double.class))
					return new Double(value.doubleValue());
				if ((newValueType == Float.TYPE) || (newValueType == Float.class))
					return new Float(value.floatValue());
				if ((newValueType == Long.TYPE) || (newValueType == Long.class))
					return new Long(value.longValue());
				if ((newValueType == Short.TYPE) || (newValueType == Short.class))
					return new Short(value.shortValue());
				if ((newValueType == Byte.TYPE) || (newValueType == Byte.class))
					return new Byte(value.byteValue());
				if (newValueType == BigDecimal.class) {
					if (BigInteger.class.isAssignableFrom(valueClass)) {
						return new BigDecimal((BigInteger) value);
					}
					return new BigDecimal(value.toString());
				}
				if (newValueType == BigInteger.class) {
					if (BigDecimal.class.isAssignableFrom(valueClass)) {
						return ((BigDecimal) value).toBigInteger();
					}
					return BigInteger.valueOf(value.longValue());
				}
			}
		}
		return value;
	}

	public static int compareNumbers(Number number1, Number number2) {
		if (number1 == number2)
			return 0;
		if (number1 == null)
			return -1;
		if (number2 == null) {
			return 1;
		}

		Number firstNumber = number1;
		Number secondNumber = number2;
		Class<?> class1 = firstNumber.getClass();
		Class<?> class2 = secondNumber.getClass();
		if (class1 != class2) {
			boolean byteOrShort1 = (class1 == _ShortClass) || (class1 == _ByteClass);
			boolean byteOrShort2 = (class2 == _ShortClass) || (class2 == _ByteClass);
			boolean byteOrShortOrIntegerOrLong1 = (byteOrShort1) || (class1 == _IntegerClass) || (class1 == _LongClass);
			boolean byteOrShortOrIntegerOrLong2 = (byteOrShort2) || (class2 == _IntegerClass) || (class2 == _LongClass);

			if ((byteOrShortOrIntegerOrLong1) && (byteOrShortOrIntegerOrLong2)) {
				long value1 = firstNumber.longValue();
				long value2 = secondNumber.longValue();
				if (value1 == value2) {
					return 0;
				}
				return ((value1 < value2) ? -1 : 1);
			}
			if ((((byteOrShort1) || (class1 == _FloatClass) || (class1 == _DoubleClass))) && (((byteOrShort2) || (class2 == _FloatClass) || (class2 == _DoubleClass)))) {
				double value1 = firstNumber.doubleValue();
				double value2 = secondNumber.doubleValue();
				if (value1 == value2) {
					return 0;
				}
				return ((value1 < value2) ? -1 : 1);
			}
			if ((((byteOrShortOrIntegerOrLong1) || (class1 == _BigIntegerClass))) && (((byteOrShortOrIntegerOrLong2) || (class2 == _BigIntegerClass)))) {
				if (class1 != _BigIntegerClass) {
					firstNumber = BigInteger.valueOf(firstNumber.longValue());
				}
				if (class2 != _BigIntegerClass)
					secondNumber = BigInteger.valueOf(secondNumber.longValue());
			} else {
				if (!(BigDecimal.class.isAssignableFrom(class1))) {
					if (byteOrShortOrIntegerOrLong1)
						firstNumber = BigDecimal.valueOf(firstNumber.longValue());
					else if (BigInteger.class.isAssignableFrom(class1))
						firstNumber = new BigDecimal((BigInteger) firstNumber);
					else {
						firstNumber = new BigDecimal(firstNumber.doubleValue());
					}
				}
				if (!(BigDecimal.class.isAssignableFrom(class2))) {
					if (byteOrShortOrIntegerOrLong2)
						secondNumber = BigDecimal.valueOf(secondNumber.longValue());
					else if (BigInteger.class.isAssignableFrom(class2))
						secondNumber = new BigDecimal((BigInteger) secondNumber);
					else {
						secondNumber = new BigDecimal(secondNumber.doubleValue());
					}
				}
			}

		}

		Class<?> numberClass = firstNumber.getClass();
		if ((numberClass == _LongClass) || (numberClass == _IntegerClass) || (numberClass == _ShortClass) || (numberClass == _ByteClass)) {
			long value1 = firstNumber.longValue();
			long value2 = secondNumber.longValue();
			if (value1 == value2) {
				return 0;
			}
			return ((value1 < value2) ? -1 : 1);
		}
		if (BigDecimal.class.isAssignableFrom(numberClass)) {
			int comparison = ((BigDecimal) firstNumber).compareTo((BigDecimal) secondNumber);
			if (comparison < 0)
				return -1;
			if (comparison > 0) {
				return 1;
			}
			return 0;
		}
		if (BigInteger.class.isAssignableFrom(numberClass)) {
			int comparison = ((BigInteger) firstNumber).compareTo((BigInteger) secondNumber);
			if (comparison < 0)
				return -1;
			if (comparison > 0) {
				return 1;
			}
			return 0;
		}

		double value1 = firstNumber.doubleValue();
		double value2 = secondNumber.doubleValue();
		if (value1 == value2) {
			return 0;
		}
		return ((value1 < value2) ? -1 : 1);
	}

	public static Object convertNumberOrBooleanIntoCompatibleValue(Object value, Class<?> newValueType) {
		if (value != null) {
			Class<?> valueClass = value.getClass();
			if (_isClassABoolean(newValueType)) {
				return ((valueClass == _BooleanClass) ? value : convertNumberIntoBooleanValue((Number) value));
			}
			return ((valueClass == _BooleanClass) ? convertBooleanIntoCompatibleNumberValue((Boolean) value, newValueType) : convertNumberIntoCompatibleValue((Number) value, newValueType));
		}

		return value;
	}

	public static int compareNumbersOrBooleans(Object numericalValue1, Object numericalValue2) {
		if (numericalValue1 == numericalValue2)
			return 0;
		if (numericalValue1 == null)
			return -1;
		if (numericalValue2 == null) {
			return 1;
		}

		Class<?> valueClass1 = numericalValue1.getClass();
		Class<?> valueClass2 = numericalValue2.getClass();
		if ((valueClass1 == _BooleanClass) && (valueClass2 == _BooleanClass)) {
			boolean boolean1 = ((Boolean) numericalValue1).booleanValue();
			boolean boolean2 = ((Boolean) numericalValue2).booleanValue();
			if (boolean1 == boolean2)
				return 0;
			if (boolean2) {
				return -1;
			}
			return 1;
		}
		if (valueClass1 == _BooleanClass)
			return compareNumbers(convertBooleanIntoNumberValue((Boolean) numericalValue1), (Number) numericalValue2);
		if (valueClass2 == _BooleanClass) {
			return compareNumbers((Number) numericalValue1, convertBooleanIntoNumberValue((Boolean) numericalValue2));
		}
		return compareNumbers((Number) numericalValue1, (Number) numericalValue2);
	}

	static String _isoLatinEncoding() {
		String encoding = "ISO8859_1";
		try {
			String string = "test";
			string.getBytes(encoding);
		} catch (UnsupportedEncodingException exception) {
			NSLog._conditionallyLogPrivateException(exception);
			if (System.getProperty("java.vendor").startsWith("Microsoft")) {
				encoding = "8859_1";
			}
		}
		return encoding;
	}

	public static String _encodingNameFromObjectiveC(String objectiveCEncodingName) {
		if (objectiveCEncodingName.equals("NSISOLatin1StringEncoding"))
			return ISOLatin1StringEncoding;
		if (objectiveCEncodingName.equals("NSMacOSRomanStringEncoding"))
			return MacOSRomanStringEncoding;
		if (objectiveCEncodingName.equals("NSASCIIStringEncoding"))
			return ASCIIStringEncoding;
		if (objectiveCEncodingName.equals("NSNEXTSTEPStringEncoding"))
			return NEXTSTEPStringEncoding;
		if (objectiveCEncodingName.equals("NSJapaneseEUCStringEncoding"))
			return JapaneseEUCStringEncoding;
		if (objectiveCEncodingName.equals("NSUTF8StringEncoding"))
			return UTF8StringEncoding;
		if (objectiveCEncodingName.equals("NSSymbolStringEncoding"))
			return SymbolStringEncoding;
		if (objectiveCEncodingName.equals("NSNonLossyASCIIStringEncoding"))
			return NonLossyASCIIStringEncoding;
		if (objectiveCEncodingName.equals("NSShiftJISStringEncoding"))
			return ShiftJISStringEncoding;
		if (objectiveCEncodingName.equals("NSISOLatin2StringEncoding"))
			return ISOLatin2StringEncoding;
		if (objectiveCEncodingName.equals("NSUnicodeStringEncoding"))
			return UnicodeStringEncoding;
		if (objectiveCEncodingName.equals("NSWindowsCP1251StringEncoding"))
			return WindowsCP1251StringEncoding;
		if (objectiveCEncodingName.equals("NSWindowsCP1252StringEncoding"))
			return WindowsCP1252StringEncoding;
		if (objectiveCEncodingName.equals("NSWindowsCP1253StringEncoding"))
			return WindowsCP1253StringEncoding;
		if (objectiveCEncodingName.equals("NSWindowsCP1254StringEncoding"))
			return WindowsCP1254StringEncoding;
		if (objectiveCEncodingName.equals("NSWindowsCP1250StringEncoding"))
			return WindowsCP1250StringEncoding;
		if (objectiveCEncodingName.equals("NSISO2022JPStringEncoding")) {
			return ISO2022JPStringEncoding;
		}
		return objectiveCEncodingName;
	}

	static {
		try {
			_packages.addObject("com.webobjects.foundation");
			_packages.addObject("java.lang");
			_packages.addObject("java.math");
			_packages.addObject("java.util");

			_classesByPartialName.setObjectForKey(NSRange._CLASS, "com.webobjects.foundation.NSRange");
			_classesByPartialName.setObjectForKey(NSMutableRange._CLASS, "com.webobjects.foundation.NSMutableRange");
			_classesByPartialName.setObjectForKey(NSDictionary._CLASS, "com.webobjects.foundation.NSDictionary");
			_classesByPartialName.setObjectForKey(NSArray._CLASS, "com.webobjects.foundation.NSArray");
			_classesByPartialName.setObjectForKey(NSMutableArray._CLASS, "com.webobjects.foundation.NSMutableArray");
			_classesByPartialName.setObjectForKey(_NSThreadsafeMutableArray._CLASS, "com.webobjects.foundation._NSThreadsafeMutableArray");
			_classesByPartialName.setObjectForKey(NSMutableDictionary._CLASS, "com.webobjects.foundation.NSMutableDictionary");
			_classesByPartialName.setObjectForKey(_NSThreadsafeMutableDictionary._CLASS, "com.webobjects.foundation._NSThreadsafeMutableDictionary");
			_classesByPartialName.setObjectForKey(_NSCollectionReaderWriterLock._CLASS, "com.webobjects.foundation._NSCollectionReaderWriterLock");
			_classesByPartialName.setObjectForKey(_NSReadReentrantReaderWriterLock._CLASS, "com.webobjects.foundation._NSReadReentrantReaderWriterLock");
			_classesByPartialName.setObjectForKey(NSCoding._CLASS, "com.webobjects.foundation.NSCoding");
			_classesByPartialName.setObjectForKey(NSKeyValueCoding._CLASS, "com.webobjects.foundation.NSKeyValueCoding");
			_classesByPartialName.setObjectForKey(NSKeyValueCoding.ErrorHandling._CLASS, "com.webobjects.foundation.NSKeyValueCoding$ErrorHandling");
			_classesByPartialName.setObjectForKey(NSKeyValueCoding.UnknownKeyException._CLASS, "com.webobjects.foundation.NSKeyValueCoding$UnknownKeyException");
			_classesByPartialName.setObjectForKey(NSKeyValueCoding.Null._CLASS, "com.webobjects.foundation.NSKeyValueCoding$Null");
			_classesByPartialName.setObjectForKey(NSKeyValueCoding.Utility._CLASS, "com.webobjects.foundation.NSKeyValueCoding$Utility");
			_classesByPartialName.setObjectForKey(NSKeyValueCoding.ValueAccessor._CLASS, "com.webobjects.foundation.NSKeyValueCoding$ValueAccessor");
			_classesByPartialName.setObjectForKey(NSKeyValueCoding._KeyBindingCreation._CLASS, "com.webobjects.foundation.NSKeyValueCoding$_KeyBindingCreation");
			_classesByPartialName.setObjectForKey(NSKeyValueCoding._KeyBinding._CLASS, "com.webobjects.foundation.NSKeyValueCoding$_KeyBinding");
			_classesByPartialName.setObjectForKey(NSKeyValueCoding.DefaultImplementation._CLASS, "com.webobjects.foundation.NSKeyValueCoding$DefaultImplementation");
			_classesByPartialName.setObjectForKey(NSKeyValueCoding._ReflectionKeyBindingCreation._CLASS, "com.webobjects.foundation.NSKeyValueCoding$_ReflectionKeyBindingCreation");
			_classesByPartialName.setObjectForKey(NSKeyValueCodingAdditions._CLASS, "com.webobjects.foundation.NSKeyValueCodingAdditions");
			_classesByPartialName.setObjectForKey(NSKeyValueCodingAdditions.Utility._CLASS, "com.webobjects.foundation.NSKeyValueCodingAdditions$Utility");
			_classesByPartialName.setObjectForKey(NSKeyValueCodingAdditions.DefaultImplementation._CLASS, "com.webobjects.foundation.NSKeyValueCodingAdditions$DefaultImplementation");
			_classesByPartialName.setObjectForKey(_NSCollectionPrimitives._CLASS, "com.webobjects.foundation._NSCollectionPrimitives");
			_classesByPartialName.setObjectForKey(NSForwardException._CLASS, "com.webobjects.foundation.NSForwardException");
			_classesByPartialName.setObjectForKey(_CLASS, "com.webobjects.foundation._NSUtilities");

			_classesByPartialName.setObjectForKey(Boolean.TYPE, "boolean");
			_classesByPartialName.setObjectForKey(Short.TYPE, "short");
			_classesByPartialName.setObjectForKey(Byte.TYPE, "byte");
			_classesByPartialName.setObjectForKey(Integer.TYPE, "int");
			_classesByPartialName.setObjectForKey(Long.TYPE, "long");
			_classesByPartialName.setObjectForKey(Double.TYPE, "double");
			_classesByPartialName.setObjectForKey(Float.TYPE, "float");
			_classesByPartialName.setObjectForKey(Character.TYPE, "char");

			_classesByPartialName.setObjectForKey(_StringClass, "NSString");
			_classesByPartialName.setObjectForKey(_NumberClass, "NSNumber");
			_classesByPartialName.setObjectForKey(_BigDecimalClass, "NSDecimalNumber");
			_classesByPartialName.setObjectForKey(_BigDecimalClass, "NSDoubleNumber");
			_classesByPartialName.setObjectForKey(NSData._CLASS, "NSData");
			_classesByPartialName.setObjectForKey(NSNumberFormatter._CLASS, "NSNumberFormatter");
			_classesByPartialName.setObjectForKey(NSTimeZone._CLASS, "NSTimeZone");
			try {
				_classesByPartialName.setObjectForKey(NSTimestamp._CLASS, "NSTimestamp");
				_classesByPartialName.setObjectForKey(NSTimestamp._CLASS, "NSCalendarDate");
				_classesByPartialName.setObjectForKey(NSTimestamp._CLASS, "NSGregorianDate");
			} catch (Throwable ex) {
				NSLog.err.appendln("Error during Foundation initialization. This could be a result of NSTimeZone being incompletely initialized.  Try moving any use of NSTimeZone further into your application.");
			}

			_classesByPartialName.setObjectForKey(NSKeyValueCoding.Null._CLASS, "EONull");

			_integers = new Integer[517];

			int i = 0;
			for (int c = 516; i < c; ++i)
				_integers[i] = new Integer(i - 3);
		} catch (Throwable e) {
			NSLog.err.appendln("Exception occurred in initializer");
			if (NSLog.debugLoggingAllowedForLevel(1)) {
				NSLog.debug.appendln(e);
			}
			throw NSForwardException._runtimeExceptionForThrowable(e);
		}

		_shortFalse = new Short((short) 0);

		_shortTrue = new Short((short) 1);

		_isoLatinEncoding = _isoLatinEncoding();

		NEXTSTEPStringEncoding = _isoLatinEncoding;

		ISOLatin1StringEncoding = _isoLatinEncoding;
	}

	public static class JavaArchiveFilter
		implements
		FilenameFilter {
		private static final String JAR = ".jar";
		private static final String ZIP = ".zip";

		public boolean accept(File dir, String aName) {
			boolean result = false;

			if ((aName != null) && (((aName.endsWith(JAR)) || (aName.endsWith(ZIP))))) {
				result = true;
			}

			return result;
		}
	}

	public static class JavaClassFilter
		implements
		FilenameFilter {
		private static final String CLASS_SUFFIX = ".class";

		public boolean accept(File dir, String aName) {
			boolean result = false;

			if ((aName != null) && (aName.endsWith(CLASS_SUFFIX))) {
				result = true;
			}

			return result;
		}
	}

	private static class _NoClassUnderTheSun {
		protected static final Class<?> _CLASS = _NSUtilities._classWithFullySpecifiedName("com.webobjects.foundation._NSUtilities$_NoClassUnderTheSun");
	}

	static interface _ResourceSearcher {
		Class<?> _searchForClassWithName(String paramString);

		URL _searchPathURLForResourceWithName(Class<?> paramClass, String paramString1, String paramString2);
	}
}