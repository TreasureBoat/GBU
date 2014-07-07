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

import java.io.ObjectStreamException;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

public interface NSKeyValueCoding {
	public static final Class<?> _CLASS = _NSUtilitiesExtra._classWithFullySpecifiedNamePrime("com.webobjects.foundation.NSKeyValueCoding");

	public static final Null<Object> NullValue = new Null<Object>();

	Object valueForKey(String paramString);

	void takeValueForKey(Object paramObject, String paramString);

	public static class _ReflectionKeyBindingCreation {
		public static final Class<?> _CLASS = _NSUtilitiesExtra._classWithFullySpecifiedNamePrime("com.webobjects.foundation.NSKeyValueCoding$_ReflectionKeyBindingCreation");
		public static final int MethodLookup = 0;
		public static final int UnderbarMethodLookup = 1;
		public static final int FieldLookup = 2;
		public static final int UnderbarFieldLookup = 3;
		public static final int OtherStorageLookup = 4;
		public static final int[] _ValueForKeyLookupOrder = { 0, 1, 3, 2, 4 };

		public static final int[] _StoredValueForKeyLookupOrder = { 1, 3, 2, 4, 0 };

		private static final NSKeyValueCoding._KeyBinding _NotAvailableIndicator = new NSKeyValueCoding._KeyBinding(null, null);

		private static final _NSThreadsafeMutableDictionary<NSKeyValueCoding._KeyBinding, _BindingStorage> _bindingStorageMapTable = new _NSThreadsafeMutableDictionary<NSKeyValueCoding._KeyBinding, _BindingStorage>(new NSMutableDictionary<NSKeyValueCoding._KeyBinding, _BindingStorage>(256));

		public static void _flushCaches() {
			_bindingStorageMapTable.removeAllObjects();
		}

		public static boolean _canAccessFieldsDirectlyForClass(Class<?> objectClass) {
			return _NSReflectionUtilities._staticBooleanMethodValue("canAccessFieldsDirectly", null, null, objectClass, NSKeyValueCoding._CLASS, true);
		}

		public static NSKeyValueCoding._KeyBinding _fieldKeyBinding(Object object, String key, String fieldName) {
			Class<?> objectClass = object.getClass();
			NSKeyValueCoding.ValueAccessor valueAccessor = NSKeyValueCoding.ValueAccessor._valueAccessorForClass(objectClass);
			boolean publicFieldOnly = (valueAccessor == null);

			Field field = _NSReflectionUtilities._fieldForClass(objectClass, fieldName, publicFieldOnly);
			if (field != null) {
				Class<?> valueClass = _NSUtilities.classObjectForClass(field.getType());
				if (_NSUtilities._isClassANumber(valueClass))
					return new NSKeyValueCoding._NumberFieldBinding(objectClass, key, field, valueClass, valueAccessor);
				if (_NSUtilities._isClassABoolean(valueClass)) {
					return new NSKeyValueCoding._BooleanFieldBinding(objectClass, key, field, valueAccessor);
				}
				return new NSKeyValueCoding._FieldBinding(objectClass, key, field, valueAccessor);
			}
			return null;
		}

		public static NSKeyValueCoding._KeyBinding _methodKeyGetBinding(Object object, String key, String methodName) {
			Class<?> objectClass = object.getClass();
			NSKeyValueCoding.ValueAccessor valueAccessor = NSKeyValueCoding.ValueAccessor._valueAccessorForClass(objectClass);
			boolean publicMethodOnly = (valueAccessor == null);

			Method method = _NSReflectionUtilities._methodForClass(objectClass, methodName, null, publicMethodOnly);
			if (method != null) {
				Class<?> valueClass = _NSUtilities.classObjectForClass(method.getReturnType());
				if (_NSUtilities._isClassANumber(valueClass))
					return new NSKeyValueCoding._NumberMethodBinding(objectClass, key, method, valueClass, valueAccessor);
				if (_NSUtilities._isClassABoolean(valueClass)) {
					return new NSKeyValueCoding._BooleanMethodBinding(objectClass, key, method, valueAccessor);
				}
				return new NSKeyValueCoding._MethodBinding(objectClass, key, method, valueAccessor);
			}
			return null;
		}

		public static NSKeyValueCoding._KeyBinding _methodKeySetBinding(Object object, String key, String methodName) {
			Class<?> objectClass = object.getClass();
			NSKeyValueCoding.ValueAccessor valueAccessor = NSKeyValueCoding.ValueAccessor._valueAccessorForClass(objectClass);
			boolean publicMethodOnly = (valueAccessor == null);

			Method method = _NSReflectionUtilities._methodWithOneArgumentOfUnknownType(objectClass, methodName, key, publicMethodOnly, true, null, true);
			if (method != null) {
				Class<?> valueClass = _NSUtilities.classObjectForClass(method.getParameterTypes()[0]);
				if (_NSUtilities._isClassANumber(valueClass))
					return new NSKeyValueCoding._NumberMethodBinding(objectClass, key, method, valueClass, valueAccessor);
				if (_NSUtilities._isClassABoolean(valueClass)) {
					return new NSKeyValueCoding._BooleanMethodBinding(objectClass, key, method, valueAccessor);
				}
				return new NSKeyValueCoding._MethodBinding(objectClass, key, method, valueAccessor);
			}
			return null;
		}

		private static NSKeyValueCoding._KeyBinding _createKeyBindingForKey(Object object, String key, int[] lookupOrder, boolean trueForSetAndFalseForGet) {
			if ((key == null) || (key.length() == 0)) {
				return null;
			}

			Class<?> objectClass = object.getClass();

			boolean canAccessFieldsDirectlyTestPerformed = false;
			boolean canAccessFieldsDirectly = false;

			NSKeyValueCoding._KeyBinding lookupBinding = new NSKeyValueCoding._KeyBinding(objectClass, key);
			_BindingStorage bindingStorage = _bindingStorageMapTable.objectForKey(lookupBinding);
			if (bindingStorage == null) {
				bindingStorage = new _BindingStorage();
				_bindingStorageMapTable.setObjectForKey(bindingStorage, lookupBinding);
			}

			Callback keyBindingCreationCallbackObject = (object instanceof Callback) ? (Callback) object : null;

			NSKeyValueCoding._KeyBinding[] keyBindings = (trueForSetAndFalseForGet) ? bindingStorage._keySetBindings : bindingStorage._keyGetBindings;
			for (int i = 0; i < lookupOrder.length; ++i) {
				int lookup = lookupOrder[i];
				NSKeyValueCoding._KeyBinding keyBinding = ((lookup >= 0) && (lookup <= 3)) ? keyBindings[lookup] : null;
				if (keyBinding == null) {
					switch (lookup) {
					case MethodLookup:
						StringBuffer methodNameBuffer = new StringBuffer(key.length() + 3);
						methodNameBuffer.append((trueForSetAndFalseForGet) ? "set" : "get");
						methodNameBuffer.append(Character.toUpperCase(key.charAt(0)));
						methodNameBuffer.append(key.substring(1));
						String methodName = new String(methodNameBuffer);

						if (trueForSetAndFalseForGet) {
							keyBinding = (keyBindingCreationCallbackObject != null) ? keyBindingCreationCallbackObject._methodKeySetBinding(key, methodName) : _methodKeySetBinding(object, key, methodName);
						} else {
							keyBinding = (keyBindingCreationCallbackObject != null) ? keyBindingCreationCallbackObject._methodKeyGetBinding(key, methodName) : _methodKeyGetBinding(object, key, methodName);

							if (keyBinding == null) {
								keyBinding = (keyBindingCreationCallbackObject != null) ? keyBindingCreationCallbackObject._methodKeyGetBinding(key, key) : _methodKeyGetBinding(object, key, key);
							}

							if (keyBinding == null) {
								methodNameBuffer.setLength(0);
								methodNameBuffer.append("is");
								methodNameBuffer.append(Character.toUpperCase(key.charAt(0)));
								methodNameBuffer.append(key.substring(1));
								methodName = new String(methodNameBuffer);
								keyBinding = (keyBindingCreationCallbackObject != null) ? keyBindingCreationCallbackObject._methodKeyGetBinding(key, methodName) : _methodKeyGetBinding(object, key, methodName);
							}
						}
						break;
					case UnderbarMethodLookup:
						StringBuffer underbarMethodNameBuffer = new StringBuffer(key.length() + 4);
						underbarMethodNameBuffer.append((trueForSetAndFalseForGet) ? "_set" : "_get");
						underbarMethodNameBuffer.append(Character.toUpperCase(key.charAt(0)));
						underbarMethodNameBuffer.append(key.substring(1));
						String underbarMethodName = new String(underbarMethodNameBuffer);

						if (trueForSetAndFalseForGet) {
							keyBinding = (keyBindingCreationCallbackObject != null) ? keyBindingCreationCallbackObject._methodKeySetBinding(key, underbarMethodName) : _methodKeySetBinding(object, key, underbarMethodName);
						} else {
							keyBinding = (keyBindingCreationCallbackObject != null) ? keyBindingCreationCallbackObject._methodKeyGetBinding(key, underbarMethodName) : _methodKeyGetBinding(object, key, underbarMethodName);

							if (keyBinding == null) {
								underbarMethodNameBuffer.setLength(0);
								underbarMethodNameBuffer.append("_");
								underbarMethodNameBuffer.append(key);
								underbarMethodName = new String(underbarMethodNameBuffer);
								keyBinding = (keyBindingCreationCallbackObject != null) ? keyBindingCreationCallbackObject._methodKeyGetBinding(key, underbarMethodName) : _methodKeyGetBinding(object, key, underbarMethodName);
							}

							if (keyBinding == null) {
								underbarMethodNameBuffer.setLength(0);
								underbarMethodNameBuffer.append("_is");
								underbarMethodNameBuffer.append(Character.toUpperCase(key.charAt(0)));
								underbarMethodNameBuffer.append(key.substring(1));
								underbarMethodName = new String(underbarMethodNameBuffer);
								keyBinding = (keyBindingCreationCallbackObject != null) ? keyBindingCreationCallbackObject._methodKeyGetBinding(key, underbarMethodName) : _methodKeyGetBinding(object, key, underbarMethodName);
							}
						}
						break;
					case FieldLookup:
						if (!(canAccessFieldsDirectlyTestPerformed)) {
							canAccessFieldsDirectlyTestPerformed = true;
							canAccessFieldsDirectly = _canAccessFieldsDirectlyForClass(objectClass);
						}
						if (canAccessFieldsDirectly) {
							keyBinding = (keyBindingCreationCallbackObject != null) ? keyBindingCreationCallbackObject._fieldKeyBinding(key, key) : _fieldKeyBinding(object, key, key);

							if (keyBinding == null) {
								StringBuffer fieldNameBuffer = new StringBuffer(key.length() + 2);
								fieldNameBuffer.append("is");
								fieldNameBuffer.append(Character.toUpperCase(key.charAt(0)));
								fieldNameBuffer.append(key.substring(1));
								String fieldName = new String(fieldNameBuffer);
								keyBinding = (keyBindingCreationCallbackObject != null) ? keyBindingCreationCallbackObject._fieldKeyBinding(key, fieldName) : _fieldKeyBinding(object, key, fieldName);
							}
						}
						break;
					case UnderbarFieldLookup:
						if (!(canAccessFieldsDirectlyTestPerformed)) {
							canAccessFieldsDirectlyTestPerformed = true;
							canAccessFieldsDirectly = _canAccessFieldsDirectlyForClass(objectClass);
						}
						if (canAccessFieldsDirectly) {
							StringBuffer underbarFieldNameBuffer = new StringBuffer(key.length() + 3);
							underbarFieldNameBuffer.append("_");
							underbarFieldNameBuffer.append(key);
							String underbarFieldName = new String(underbarFieldNameBuffer);
							keyBinding = (keyBindingCreationCallbackObject != null) ? keyBindingCreationCallbackObject._fieldKeyBinding(key, underbarFieldName) : _fieldKeyBinding(object, key, underbarFieldName);

							if (keyBinding == null) {
								underbarFieldNameBuffer.setLength(0);
								underbarFieldNameBuffer.append("_is");
								underbarFieldNameBuffer.append(Character.toUpperCase(key.charAt(0)));
								underbarFieldNameBuffer.append(key.substring(1));
								underbarFieldName = new String(underbarFieldNameBuffer);
								keyBinding = (keyBindingCreationCallbackObject != null) ? keyBindingCreationCallbackObject._fieldKeyBinding(key, underbarFieldName) : _fieldKeyBinding(object, key, underbarFieldName);
							}
						}
						break;
					case OtherStorageLookup:
						keyBinding = (keyBindingCreationCallbackObject != null) ? keyBindingCreationCallbackObject._otherStorageBinding(key) : null;
					}

					if (keyBinding == null) {
						keyBinding = _NotAvailableIndicator;
					}
					if ((lookup == FieldLookup) || (lookup == UnderbarFieldLookup)) {
						bindingStorage._keySetBindings[lookup] = bindingStorage._keyGetBindings[lookup] = keyBinding;
					} else if ((lookup == MethodLookup) || (lookup == UnderbarMethodLookup)) {
						keyBindings[lookup] = keyBinding;
					}
				}

				if ((keyBinding != null) && (keyBinding != _NotAvailableIndicator)) {
					return keyBinding;
				}
			}
			return null;
		}

		public static NSKeyValueCoding._KeyBinding _createKeyGetBindingForKey(Object object, String key, int[] lookupOrder) {
			return _createKeyBindingForKey(object, key, lookupOrder, false);
		}

		public static NSKeyValueCoding._KeyBinding _createKeySetBindingForKey(Object object, String key, int[] lookupOrder) {
			return _createKeyBindingForKey(object, key, lookupOrder, true);
		}

		_ReflectionKeyBindingCreation() {
			throw new IllegalStateException("Cannot instantiate an instance of class " + super.getClass().getName());
		}

		private static class _BindingStorage {
			NSKeyValueCoding._KeyBinding[] _keyGetBindings;
			NSKeyValueCoding._KeyBinding[] _keySetBindings;

			public _BindingStorage() {
				this._keyGetBindings = new NSKeyValueCoding._KeyBinding[4];
				this._keySetBindings = new NSKeyValueCoding._KeyBinding[4];
			}

			public String toString() {
				StringBuilder aLog = new StringBuilder();
				aLog.append("( ");
				if (this._keyGetBindings != null) {
					aLog.append("Key Get Bindings: ");
					aLog.append("( ");
					for (int i = 0; i < this._keyGetBindings.length; ++i) {
						if (i > 0)
							aLog.append(",");
						aLog.append(this._keyGetBindings[i]);
					}
					aLog.append(")");
				} else {
					aLog.append("NULL");
				}
				aLog.append(", ");
				if (this._keyGetBindings != null) {
					aLog.append("Key Set Bindings: ");
					aLog.append("( ");
					for (int i = 0; i < this._keySetBindings.length; ++i) {
						if (i > 0)
							aLog.append(",");
						aLog.append(this._keySetBindings[i]);
					}
					aLog.append(")");
				} else {
					aLog.append("NULL");
				}
				aLog.append(")");

				return aLog.toString();
			}
		}

		public static interface Callback {
			NSKeyValueCoding._KeyBinding _fieldKeyBinding(String paramString1, String paramString2);

			NSKeyValueCoding._KeyBinding _methodKeyGetBinding(String paramString1, String paramString2);

			NSKeyValueCoding._KeyBinding _methodKeySetBinding(String paramString1, String paramString2);

			NSKeyValueCoding._KeyBinding _otherStorageBinding(String paramString);
		}
	}

	public static class DefaultImplementation {
		public static final Class<?> _CLASS = _NSUtilitiesExtra._classWithFullySpecifiedNamePrime("com.webobjects.foundation.NSKeyValueCoding$DefaultImplementation");

		private static final _NSThreadsafeMutableSet<NSKeyValueCoding._KeyBinding> _keyGetBindings = new _NSThreadsafeMutableSet<NSKeyValueCoding._KeyBinding>(new NSMutableSet<NSKeyValueCoding._KeyBinding>(256));

		private static final _NSThreadsafeMutableSet<NSKeyValueCoding._KeyBinding> _keySetBindings = new _NSThreadsafeMutableSet<NSKeyValueCoding._KeyBinding>(new NSMutableSet<NSKeyValueCoding._KeyBinding>(256));

		public static void _flushCaches() {
			_keyGetBindings.removeAllObjects();
			_keySetBindings.removeAllObjects();
		}

		@SuppressWarnings("unchecked")
		public static Object valueForKey(Object object, String key) {
			if (key == null) {
				return null;
			}

			if (object instanceof Map) {
				return NSKeyValueCoding.MapImplementation.valueForKey((Map<String, Object>) object, key);
			}

			NSKeyValueCoding._KeyBinding binding = (object instanceof NSKeyValueCoding._KeyBindingCreation) ? ((NSKeyValueCoding._KeyBindingCreation) object)._keyGetBindingForKey(key) : _keyGetBindingForKey(object, key);

			return binding.valueInObject(object);
		}

		@SuppressWarnings("unchecked")
		public static void takeValueForKey(Object object, Object value, String key) {
			if (key == null) {
				throw new IllegalArgumentException("Key cannot be null");
			}

			if (object instanceof Map) {
				NSKeyValueCoding.MapImplementation.takeValueForKey((Map<String, Object>) object, value, key);
				return;
			}

			NSKeyValueCoding._KeyBinding binding = (object instanceof NSKeyValueCoding._KeyBindingCreation) ? ((NSKeyValueCoding._KeyBindingCreation) object)._keySetBindingForKey(key) : _keySetBindingForKey(object, key);

			binding.setValueInObject(value, object);
		}

		private static String _identityString(Object object) {
			return "<" + object.getClass().getName() + " 0x" + Integer.toHexString(System.identityHashCode(object)) + ">";
		}

		public static Object handleQueryWithUnboundKey(Object object, String key) {
			String capitalizedKey = _NSStringUtilities.capitalizedString(key);
			throw new NSKeyValueCoding.UnknownKeyException(_identityString(object) + " valueForKey(): lookup of unknown key: '" + key + "'.\nThis class does not have an instance variable of the name " + key + " or _" + key + ", nor a method of the name " + key + ", _" + key + ", get" + capitalizedKey + ", or _get" + capitalizedKey, object, key);
		}

		public static void handleTakeValueForUnboundKey(Object object, Object value, String key) {
			String capitalizedKey = _NSStringUtilities.capitalizedString(key);
			throw new NSKeyValueCoding.UnknownKeyException(_identityString(object) + " takeValueForKey(): attempt to assign value to unknown key: '" + key + "'.\nThis class does not have an instance variable of the name " + key + " or _" + key + ", nor a method of the name set" + capitalizedKey + " or _set" + capitalizedKey, object, key);
		}

		public static void unableToSetNullForKey(Object object, String key) {
			throw new IllegalArgumentException("KeyValueCoding: Failed to assign null to key '" + key + "' in object of class '" + object.getClass().getName() + "'.  If you want to handle assignments of null to properties of primitive types, implement the interface NSKeyValueCoding.ErrorHandling with 'public void unableToSetNullForKey(String key)' on your object class.");
		}

		public static NSKeyValueCoding._KeyBinding _keyGetBindingForKey(Object object, String key) {
			Class<?> objectClass = object.getClass();
			NSKeyValueCoding._KeyBinding keyBinding = (NSKeyValueCoding._KeyBinding) _keyGetBindings.member(new NSKeyValueCoding._KeyBinding(objectClass, key));
			if (keyBinding == null) {
				keyBinding = (object instanceof NSKeyValueCoding._KeyBindingCreation) ? ((NSKeyValueCoding._KeyBindingCreation) object)._createKeyGetBindingForKey(key) : _createKeyGetBindingForKey(object, key);

				if (keyBinding == null) {
					keyBinding = new NSKeyValueCoding._KeyBinding(objectClass, key);
				}
				_keyGetBindings.addObject(keyBinding);
			}
			return keyBinding;
		}

		public static NSKeyValueCoding._KeyBinding _keySetBindingForKey(Object object, String key) {
			Class<?> objectClass = object.getClass();
			NSKeyValueCoding._KeyBinding keyBinding = (NSKeyValueCoding._KeyBinding) _keySetBindings.member(new NSKeyValueCoding._KeyBinding(objectClass, key));
			if (keyBinding == null) {
				keyBinding = (object instanceof NSKeyValueCoding._KeyBindingCreation) ? ((NSKeyValueCoding._KeyBindingCreation) object)._createKeySetBindingForKey(key) : _createKeySetBindingForKey(object, key);

				if (keyBinding == null) {
					keyBinding = new NSKeyValueCoding._KeyBinding(objectClass, key);
				}
				_keySetBindings.addObject(keyBinding);
			}
			return keyBinding;
		}

		public static NSKeyValueCoding._KeyBinding _createKeyGetBindingForKey(Object object, String key) {
			return NSKeyValueCoding._ReflectionKeyBindingCreation._createKeyGetBindingForKey(object, key, NSKeyValueCoding._ReflectionKeyBindingCreation._ValueForKeyLookupOrder);
		}

		public static NSKeyValueCoding._KeyBinding _createKeySetBindingForKey(Object object, String key) {
			return NSKeyValueCoding._ReflectionKeyBindingCreation._createKeySetBindingForKey(object, key, NSKeyValueCoding._ReflectionKeyBindingCreation._ValueForKeyLookupOrder);
		}

		DefaultImplementation() {
			throw new IllegalStateException("Cannot instantiate an instance of class " + super.getClass().getName());
		}
	}

	public static class MapImplementation {
		public static final Class<?> _CLASS = _NSUtilitiesExtra._classWithFullySpecifiedNamePrime("com.webobjects.foundation.NSKeyValueCoding$MapImplementation");

		public static Object valueForKey(Map<String, Object> map, String key) {
			if (key == null) {
				return null;
			}

			Object value = map.get(key);
			if (value == null) {
				if ("values".equals(key)) {
					value = map.values();
				} else if ("keySet".equals(key)) {
					value = map.keySet();
				} else {
					if ("size".equals(key))
						return _NSUtilities.IntegerForInt(map.size());
					if ("entrySet".equals(key))
						value = map.entrySet();
					else
						try {
							NSKeyValueCoding._KeyBinding binding = (map instanceof NSKeyValueCoding._KeyBindingCreation) ? ((NSKeyValueCoding._KeyBindingCreation) map)._keyGetBindingForKey(key) : NSKeyValueCoding.DefaultImplementation._keyGetBindingForKey(map, key);

							value = binding.valueInObject(map);
						} catch (RuntimeException e) {
						}
				}
			}
			return value;
		}

		public static void takeValueForKey(Map<String, Object> map, Object value, String key) {
			if (key == null) {
				throw new IllegalArgumentException("Key cannot be null");
			}

			boolean exceptionThrown = false;
			try {
				NSKeyValueCoding._KeyBinding binding = (map instanceof NSKeyValueCoding._KeyBindingCreation) ? ((NSKeyValueCoding._KeyBindingCreation) map)._keySetBindingForKey(key) : NSKeyValueCoding.DefaultImplementation._keySetBindingForKey(map, key);

				binding.setValueInObject(value, map);
			} catch (Exception e) {
				exceptionThrown = true;
			}

			if (exceptionThrown)
				map.put(key, value);
		}

		MapImplementation() {
			throw new IllegalStateException("Cannot instantiate an instance of class " + super.getClass().getName());
		}
	}

	public static class _BooleanMethodBinding
		extends
		NSKeyValueCoding._MethodBinding {
		public _BooleanMethodBinding(
			Class<?> targetClass,
			String key,
			Method method,
			NSKeyValueCoding.ValueAccessor valueAccessor) {
			super(targetClass, key, method, valueAccessor);
		}

		public Class<?> valueType() {
			return _NSUtilities._BooleanClass;
		}

		public void setValueInObject(Object value, Object object) {
			super.setValueInObject(_convertValueIntoBoolean(value), object);
		}
	}

	public static class _NumberMethodBinding
		extends
		NSKeyValueCoding._MethodBinding {
		protected Class<?> _valueClass;

		public _NumberMethodBinding(
			Class<?> targetClass,
			String key,
			Method method,
			Class<?> valueClass,
			NSKeyValueCoding.ValueAccessor valueAccessor) {
			super(targetClass, key, method, valueAccessor);
			this._valueClass = valueClass;
		}

		public Class<?> valueType() {
			return this._valueClass;
		}

		public void setValueInObject(Object value, Object object) {
			super.setValueInObject(_convertValueIntoNumberOfValueType(value, this._valueClass), object);
		}

		public String toString() {
			return super.toString() + ", value class = " + ((this._valueClass != null) ? this._valueClass.getName() : "<NULL>");
		}
	}

	public static class _MethodBinding
		extends
		NSKeyValueCoding._KeyBinding {
		protected Method _method;
		protected boolean _isScalar;
		protected NSKeyValueCoding.ValueAccessor _valueAccessor;

		public _MethodBinding(
			Class<?> targetClass,
			String key,
			Method method,
			NSKeyValueCoding.ValueAccessor valueAccessor) {
			super(targetClass, key);
			Class<?>[] types = method.getParameterTypes();
			this._method = method;
			this._isScalar = ((types.length == 1) && (types[0].isPrimitive()));
			this._valueAccessor = ((valueAccessor != null) ? valueAccessor : NSKeyValueCoding.ValueAccessor._defaultValueAccessor);
		}

		public Class<?> valueType() {
			return this._method.getReturnType();
		}

		public boolean isScalarProperty() {
			return this._isScalar;
		}

		private RuntimeException throwMethodExceptionWithDescription(Throwable exception, Object object, Object newValue, boolean isSetMethod) {
			StringBuffer message = new StringBuffer(64);
			if (isSetMethod)
				message.append("While trying to invoke the set method \"");
			else {
				message.append("While trying to invoke the get method \"");
			}

			message.append(this._method.toString());
			message.append("\" on an object of type ");
			if (object == null)
				message.append("null");
			else {
				message.append(object.getClass().getName());
			}
			message.append(" we received ");

			if (isSetMethod) {
				message.append("an argument of type ");
				if (newValue == null)
					message.append("null");
				else
					message.append(newValue.getClass().getName());
			} else {
				message.append("no arguments");
			}

			message.append(". This often happens if you forget to use a formatter.");
			return new IllegalArgumentException(new String(message));
		}

		public Object valueInObject(Object object) {
			try {
				return this._valueAccessor.methodValue(object, this._method);
			} catch (IllegalArgumentException exception) {
				throw throwMethodExceptionWithDescription(exception, object, null, false);
			} catch (IllegalAccessException exception) {
				throw NSForwardException._runtimeExceptionForThrowable(exception);
			} catch (InvocationTargetException exception) {
				throw NSForwardException._runtimeExceptionForThrowable(exception);
			}
		}

		public void setValueInObject(Object value, Object object) {
			if ((value == null) && (this._isScalar))
				_unableToSetNull(object);
			else
				try {
					this._valueAccessor.setMethodValue(object, this._method, value);
				} catch (IllegalArgumentException exception) {
					throw throwMethodExceptionWithDescription(exception, object, value, true);
				} catch (IllegalAccessException exception) {
					throw NSForwardException._runtimeExceptionForThrowable(exception);
				} catch (InvocationTargetException exception) {
					throw NSForwardException._runtimeExceptionForThrowable(exception);
				}
		}

		public String toString() {
			return super.toString() + ", method = " + ((this._method != null) ? this._method.toString() : "<NULL>") + ", is scalar = " + this._isScalar + ", value accessor = " + ((this._valueAccessor != null) ? this._valueAccessor.toString() : "<NULL>");
		}
	}

	public static class _BooleanFieldBinding
		extends
		NSKeyValueCoding._FieldBinding {
		public _BooleanFieldBinding(
			Class<?> targetClass,
			String key,
			Field field,
			NSKeyValueCoding.ValueAccessor valueAccessor) {
			super(targetClass, key, field, valueAccessor);
		}

		public Class<?> valueType() {
			return _NSUtilities._BooleanClass;
		}

		public void setValueInObject(Object value, Object object) {
			super.setValueInObject(_convertValueIntoBoolean(value), object);
		}
	}

	public static class _NumberFieldBinding
		extends
		NSKeyValueCoding._FieldBinding {
		protected Class<?> _valueClass;

		public _NumberFieldBinding(
			Class<?> targetClass,
			String key,
			Field field,
			Class<?> valueClass,
			NSKeyValueCoding.ValueAccessor valueAccessor) {
			super(targetClass, key, field, valueAccessor);
			this._valueClass = valueClass;
		}

		public Class<?> valueType() {
			return this._valueClass;
		}

		public void setValueInObject(Object value, Object object) {
			super.setValueInObject(_convertValueIntoNumberOfValueType(value, this._valueClass), object);
		}

		public String toString() {
			return super.toString() + ", value class = " + ((this._valueClass != null) ? this._valueClass.getName() : "<NULL>");
		}
	}

	public static class _FieldBinding
		extends
		NSKeyValueCoding._KeyBinding {
		protected Field _field;
		protected boolean _isScalar;
		protected NSKeyValueCoding.ValueAccessor _valueAccessor;

		public _FieldBinding(
			Class<?> targetClass,
			String key,
			Field field,
			NSKeyValueCoding.ValueAccessor valueAccessor) {
			super(targetClass, key);
			this._field = field;
			this._isScalar = this._field.getType().isPrimitive();
			this._valueAccessor = ((valueAccessor != null) ? valueAccessor : NSKeyValueCoding.ValueAccessor._defaultValueAccessor);
		}

		public Class<?> valueType() {
			return this._field.getType();
		}

		public boolean isScalarProperty() {
			return this._isScalar;
		}

		public Object valueInObject(Object object) {
			try {
				return this._valueAccessor.fieldValue(object, this._field);
			} catch (IllegalAccessException exception) {
				throw NSForwardException._runtimeExceptionForThrowable(exception);
			}
		}

		protected void _setValidatedValueInObject(Object value, Object object)
			throws IllegalAccessException {
			this._valueAccessor.setFieldValue(object, this._field, value);
		}

		public void setValueInObject(Object value, Object object) {
			if ((value == null) && (this._isScalar))
				_unableToSetNull(object);
			else
				try {
					_setValidatedValueInObject(value, object);
				} catch (IllegalAccessException exception) {
					throw new NSForwardException(exception, "Could not set the field \"" + this._field.getName() + "\" on an object of type " + object.getClass().getName() + ".");
				} catch (IllegalArgumentException exception) {
					throw new IllegalArgumentException("While trying to set the field \"" + this._field.getName() + "\" on an object of type " + object.getClass().getName() + " we expected a " + this._field.getType().getName() + " but received a " + ((value == null) ? "null" : value.getClass().getName()) + " with a value of " + value + ". This often happens if you forget to use a formatter.");
				}
		}

		public String toString() {
			return super.toString() + ", field = " + ((this._field != null) ? this._field.toString() : "<NULL>") + ", is scalar = " + this._isScalar + ", value accessor = " + ((this._valueAccessor != null) ? this._valueAccessor.toString() : "<NULL>");
		}
	}

	public static class _ForwardingBinding
		extends
		NSKeyValueCoding._KeyBinding {
		public _ForwardingBinding(
			Class<?> targetClass,
			String key) {
			super(targetClass, key);
		}

		public Object valueInObject(Object object) {
			return ((NSKeyValueCoding) object).valueForKey(this._key);
		}

		public void setValueInObject(Object value, Object object) {
			((NSKeyValueCoding) object).takeValueForKey(value, this._key);
		}
	}

	public static class _KeyBinding {
		public static final Class<?> _CLASS = _NSUtilitiesExtra._classWithFullySpecifiedNamePrime("com.webobjects.foundation.NSKeyValueCoding$_KeyBinding");

		protected static final Short _shortFalse = new Short((short) 0);

		protected static final Short _shortTrue = new Short((short) 1);
		protected Class<?> _targetClass;
		protected String _key;
		private int _hashCode;

		public _KeyBinding(
			Class<?> targetClass,
			String key) {
			this._targetClass = targetClass;
			this._key = key;
			this._hashCode = (((targetClass != null) && (this._key != null)) ? this._targetClass.hashCode() ^ this._key.hashCode() : 0);
		}

		public final Class<?> targetClass() {
			return this._targetClass;
		}

		public final String key() {
			return this._key;
		}

		public final int hashCode() {
			return this._hashCode;
		}

		public final boolean isEqualToKeyBinding(_KeyBinding otherKeyBinding) {
			if (otherKeyBinding == null) {
				return false;
			}
			if (otherKeyBinding == this) {
				return true;
			}
			return ((this._targetClass == otherKeyBinding._targetClass) && (((this._key == otherKeyBinding._key) || (this._key.equals(otherKeyBinding._key)))));
		}

		public final boolean equals(Object object) {
			return ((object instanceof _KeyBinding) ? isEqualToKeyBinding((_KeyBinding) object) : false);
		}

		public Class<?> valueType() {
			return _NSUtilities._ObjectClass;
		}

		public boolean isScalarProperty() {
			return false;
		}

		public Object valueInObject(Object object) {
			return NSKeyValueCoding.Utility.handleQueryWithUnboundKey(object, this._key);
		}

		public void setValueInObject(Object value, Object object) {
			NSKeyValueCoding.Utility.handleTakeValueForUnboundKey(object, value, this._key);
		}

		protected void _unableToSetNull(Object object) {
			NSKeyValueCoding.Utility.unableToSetNullForKey(object, this._key);
		}

		protected Constructor<?> _numberStringConstructorForValueClass(Class<?> valueClass) {
			Class<?> aValueClass = _NSUtilities.classObjectForClass(valueClass);
			if (_NSUtilities._isClassANumber(aValueClass)) {
				try {
					return _NSUtilities.classObjectForClass(aValueClass).getConstructor(_NSUtilities._StringClassArray);
				} catch (NoSuchMethodException otherException) {
					if (NSLog.debugLoggingAllowedForLevelAndGroups(2, 16777216L)) {
						NSLog.debug.appendln("Exception while getting constructor: " + aValueClass.getName());
						NSLog.debug.appendln(otherException);
					}
				}
			}
			return null;
		}

		protected Object _convertValueIntoBoolean(Object value) {
			if (value instanceof Number) {
				return _NSUtilities.convertNumberIntoBooleanValue((Number) value);
			}

			return value;
		}

		protected Object _convertValueIntoNumberOfValueType(Object value, Class<?> numberType) {
			if (value instanceof Number) {
				return _NSUtilities.convertNumberIntoCompatibleValue((Number) value, numberType);
			}
			if (value instanceof Boolean) {
				return _NSUtilities.convertBooleanIntoCompatibleNumberValue((Boolean) value, numberType);
			}

			return value;
		}

		public String toString() {
			return super.getClass().getName() + ": target class = " + ((this._targetClass != null) ? this._targetClass.getName() : "<NULL>") + ", key = " + ((this._key != null) ? this._key : "<NULL>");
		}
	}

	public static interface _KeyBindingCreation {
		public static final Class<?> _CLASS = _NSUtilitiesExtra._classWithFullySpecifiedNamePrime("com.webobjects.foundation.NSKeyValueCoding$_KeyBindingCreation");

		NSKeyValueCoding._KeyBinding _createKeyGetBindingForKey(String paramString);

		NSKeyValueCoding._KeyBinding _createKeySetBindingForKey(String paramString);

		NSKeyValueCoding._KeyBinding _keyGetBindingForKey(String paramString);

		NSKeyValueCoding._KeyBinding _keySetBindingForKey(String paramString);
	}

	public static abstract class ValueAccessor {
		public static final Class<?> _CLASS = _NSUtilitiesExtra._classWithFullySpecifiedNamePrime(ValueAccessor.class.getName());

		private static final _NSThreadsafeMutableDictionary<String, ValueAccessor> _packageNameToValueAccessorMapTable = new _NSThreadsafeMutableDictionary<String, ValueAccessor>(new NSMutableDictionary<String, ValueAccessor>(256));
		private static final String _PackageProtectedAccessorClassName = "KeyValueCodingProtectedAccessor";
		static final ValueAccessor _defaultValueAccessor = new ValueAccessor() {
			public Object fieldValue(Object object, Field field)
				throws IllegalArgumentException,
				IllegalAccessException {
				return field.get(object);
			}

			public void setFieldValue(Object object, Field field, Object value)
				throws IllegalArgumentException,
				IllegalAccessException {
				field.set(object, value);
			}

			public Object methodValue(Object object, Method method)
				throws IllegalArgumentException,
				IllegalAccessException,
				InvocationTargetException {
				return method.invoke(object, new Object[0]);
			}

			public void setMethodValue(Object object, Method method, Object value)
				throws IllegalArgumentException,
				IllegalAccessException,
				InvocationTargetException {
				method.invoke(object, new Object[] { value });
			}

			public String toString() {
				return "<DEFAULT VALUE ACCESSOR>";
			}
		};

		public static void _flushCaches() {
			_packageNameToValueAccessorMapTable.removeAllObjects();
		}

		public static void setProtectedAccessorForPackageNamed(ValueAccessor valueAccessor, String packageName) {
			if (packageName == null) {
				throw new IllegalArgumentException("No package name specified");
			}
			if (valueAccessor == null) {
				throw new IllegalArgumentException("No value accessor specified for package " + packageName);
			}
			_packageNameToValueAccessorMapTable.setObjectForKey(valueAccessor, packageName);
		}

		public static ValueAccessor protectedAccessorForPackageNamed(String packageName) {
			ValueAccessor valueAccessor = (ValueAccessor) _packageNameToValueAccessorMapTable.objectForKey(packageName);

			if (valueAccessor == null) {
				String valueAccessorClassName = _PackageProtectedAccessorClassName;
				if (packageName.length() > 0) {
					StringBuffer temp = new StringBuffer(64);
					temp.append(packageName);
					temp.append(".");
					temp.append(valueAccessorClassName);
					valueAccessorClassName = temp.toString();
				}
				Class<?> valueAccessorClass;
				try {
					valueAccessorClass = Class.forName(valueAccessorClassName);
					if (!(ValueAccessor.class.isAssignableFrom(valueAccessorClass)))
						valueAccessorClass = null;
				} catch (ClassNotFoundException exception) {
					NSLog._conditionallyLogPrivateException(exception);
					valueAccessorClass = null;
				} catch (ClassFormatError exception) {
					NSLog._conditionallyLogPrivateException(exception);
					valueAccessorClass = null;
				} catch (SecurityException exception) {
					NSLog._conditionallyLogPrivateException(exception);
					valueAccessorClass = null;
				}

				if (valueAccessorClass != null) {
					try {
						valueAccessor = (ValueAccessor) (ValueAccessor) valueAccessorClass.newInstance();
					} catch (Throwable throwable) {
						throw new IllegalStateException("Cannot instantiate protected accessor of class " + valueAccessorClassName + " (make sure that it is a subclass of NSKeyValueCoding.ValueAccessor and has a constructor without arguments)");
					}

				}

				if (valueAccessor == null) {
					valueAccessor = _defaultValueAccessor;
				}
				_packageNameToValueAccessorMapTable.setObjectForKey(valueAccessor, packageName);
			}
			return ((valueAccessor == _defaultValueAccessor) ? null : valueAccessor);
		}

		public static void removeProtectedAccessorForPackageNamed(String packageName) {
			if (packageName != null)
				_packageNameToValueAccessorMapTable.setObjectForKey(_defaultValueAccessor, packageName);
		}

		public static ValueAccessor _valueAccessorForClass(Class<?> objectClass) {
			String className = objectClass.getName();
			int index = className.lastIndexOf(46);
			String packageName = (index > 0) ? className.substring(0, index) : "";

			return protectedAccessorForPackageNamed(packageName);
		}

		public abstract Object fieldValue(Object paramObject, Field paramField)
			throws IllegalArgumentException,
			IllegalAccessException;

		public abstract void setFieldValue(Object paramObject1, Field paramField, Object paramObject2)
			throws IllegalArgumentException,
			IllegalAccessException;

		public abstract Object methodValue(Object paramObject, Method paramMethod)
			throws IllegalArgumentException,
			IllegalAccessException,
			InvocationTargetException;

		public abstract void setMethodValue(Object paramObject1, Method paramMethod, Object paramObject2)
			throws IllegalArgumentException,
			IllegalAccessException,
			InvocationTargetException;
	}

	/**
	 * Removed unnecessary generics from this class since it can never be instantiated.
	 * 
	 * @author fijaz
	 */
	public static class Utility {
		public static final Class<?> _CLASS = _NSUtilitiesExtra._classWithFullySpecifiedNamePrime("com.webobjects.foundation.NSKeyValueCoding$Utility");

		/**
		 * Method signature changed since it doesn't make any sense to use generics for static methods. Also, NSKeyValueCoding.NullValue is a final Null object
		 * which cannot be made generic.
		 * 
		 * <pre>
		 * public static final <T> T nullValue()
		 * </pre>
		 * 
		 * @return
		 */
		public static final Object nullValue() {
			return NSKeyValueCoding.NullValue;
		}

		public static Object valueForKey(Object object, String key) {
			if (object == null)
				throw new IllegalArgumentException("Object cannot be null");
			if (object instanceof NSKeyValueCoding) {
				return ((NSKeyValueCoding) object).valueForKey(key);
			}
			return NSKeyValueCoding.DefaultImplementation.valueForKey(object, key);
		}

		public static void takeValueForKey(Object object, Object value, String key) {
			if (object == null)
				throw new IllegalArgumentException("Object cannot be null");
			if (object instanceof NSKeyValueCoding)
				((NSKeyValueCoding) object).takeValueForKey(value, key);
			else
				NSKeyValueCoding.DefaultImplementation.takeValueForKey(object, value, key);
		}

		public static Object handleQueryWithUnboundKey(Object object, String key) {
			if (object == null)
				throw new IllegalArgumentException("Object cannot be null");
			if (object instanceof NSKeyValueCoding.ErrorHandling) {
				return ((NSKeyValueCoding.ErrorHandling) object).handleQueryWithUnboundKey(key);
			}
			return NSKeyValueCoding.DefaultImplementation.handleQueryWithUnboundKey(object, key);
		}

		public static void handleTakeValueForUnboundKey(Object object, Object value, String key) {
			if (object == null)
				throw new IllegalArgumentException("Object cannot be null");
			if (object instanceof NSKeyValueCoding.ErrorHandling)
				((NSKeyValueCoding.ErrorHandling) object).handleTakeValueForUnboundKey(value, key);
			else
				NSKeyValueCoding.DefaultImplementation.handleTakeValueForUnboundKey(object, value, key);
		}

		public static void unableToSetNullForKey(Object object, String key) {
			if (object == null)
				throw new IllegalArgumentException("Object cannot be null");
			if (object instanceof NSKeyValueCoding.ErrorHandling)
				((NSKeyValueCoding.ErrorHandling) object).unableToSetNullForKey(key);
			else
				NSKeyValueCoding.DefaultImplementation.unableToSetNullForKey(object, key);
		}

		Utility() {
			throw new IllegalStateException("Cannot instantiate an instance of class " + super.getClass().getName());
		}
	}

	public static final class Null<T extends Object>
		implements
		Serializable,
		Cloneable,
		NSCoding {
		private static final long serialVersionUID = 8311844716729190443L;
		public static final Class<?> _CLASS = _NSUtilitiesExtra._classWithFullySpecifiedNamePrime("com.webobjects.foundation.NSKeyValueCoding$Null");

		public String toString() {
			return "<" + super.getClass().getName() + ">";
		}

		public Object clone() {
			return this;
		}

		public Class<?> classForCoder() {
			return _CLASS;
		}

		public static Object decodeObject(NSCoder coder) {
			return NSKeyValueCoding.NullValue;
		}

		public void encodeWithCoder(NSCoder coder) {
		}

		Object readResolve()
			throws ObjectStreamException {
			return NSKeyValueCoding.NullValue;
		}
	}

	public static class UnknownKeyException
		extends
		RuntimeException {
		private static final long serialVersionUID = 1685186287717318279L;
		public static final Class<?> _CLASS = _NSUtilitiesExtra._classWithFullySpecifiedNamePrime("com.webobjects.foundation.NSKeyValueCoding$UnknownKeyException");

		@Deprecated
		public static final String TargetObjectUserInfoKey = "NSTargetObjectUserInfoKey";

		@Deprecated
		public static final String UnknownUserInfoKey = "NSUnknownUserInfoKey";
		private NSDictionary<String, Object> _userInfo;

		public UnknownKeyException(
			String message,
			Object object,
			String key) {
			super(message);
			NSMutableDictionary<String, Object> dictionary = new NSMutableDictionary<String, Object>(2);
			if (object != null) {
				dictionary.setObjectForKey(object, "NSTargetObjectUserInfoKey");
			}
			if (key != null) {
				dictionary.setObjectForKey(key, "NSUnknownUserInfoKey");
			}
			this._userInfo = dictionary;
		}

		@Deprecated
		@SuppressWarnings("unchecked")
		public UnknownKeyException(
			String message,
			NSDictionary<String, Object> userInfo) {
			super(message);
			this._userInfo = (NSDictionary<String, Object>) (userInfo != null ? userInfo.clone() : NSDictionary.emptyDictionary());

		}

		public Object object() {
			return userInfo().objectForKey("NSTargetObjectUserInfoKey");
		}

		public String key() {
			return ((String) (String) userInfo().objectForKey("NSUnknownUserInfoKey"));
		}

		@Deprecated
		public NSDictionary<String, Object> userInfo() {
			return this._userInfo;
		}

		public String toString() {
			return "<" + super.getClass().getName() + " message '" + getMessage() + "' object '" + object() + "' key '" + key() + "'>";
		}
	}

	public static interface ErrorHandling {
		public static final Class<?> _CLASS = _NSUtilitiesExtra._classWithFullySpecifiedNamePrime("com.webobjects.foundation.NSKeyValueCoding$ErrorHandling");

		Object handleQueryWithUnboundKey(String paramString);

		void handleTakeValueForUnboundKey(Object paramObject, String paramString);

		void unableToSetNullForKey(String paramString);
	}

}