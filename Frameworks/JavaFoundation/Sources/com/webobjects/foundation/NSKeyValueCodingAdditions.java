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

public interface NSKeyValueCodingAdditions
	extends
	NSKeyValueCoding {
	public static final Class<?> _CLASS = _NSUtilitiesExtra._classWithFullySpecifiedNamePrime("com.webobjects.foundation.NSKeyValueCodingAdditions");
	String KeyPathSeparator = ".";
	char _KeyPathSeparatorChar = 46;

	Object valueForKeyPath(String paramString);

	void takeValueForKeyPath(Object paramObject, String paramString);

	public static class DefaultImplementation {
		public static final Class<?> _CLASS = _NSUtilitiesExtra._classWithFullySpecifiedNamePrime("com.webobjects.foundation.NSKeyValueCodingAdditions$DefaultImplementation");

		public static Object valueForKeyPath(Object object, String keyPath) {
			if (keyPath == null) {
				return null;
			}

			int index = keyPath.indexOf(_KeyPathSeparatorChar);
			if (index < 0) {
				return NSKeyValueCoding.Utility.valueForKey(object, keyPath);
			}

			String key = keyPath.substring(0, index);
			Object value = NSKeyValueCoding.Utility.valueForKey(object, key);
			return ((value == null) ? null : NSKeyValueCodingAdditions.Utility.valueForKeyPath(value, keyPath.substring(index + 1)));
		}

		public static void takeValueForKeyPath(Object object, Object value, String keyPath) {
			if (keyPath == null) {
				throw new IllegalArgumentException("Key path cannot be null");
			}

			int index = keyPath.indexOf(_KeyPathSeparatorChar);
			if (index < 0) {
				NSKeyValueCoding.Utility.takeValueForKey(object, value, keyPath);
			} else {
				String key = keyPath.substring(0, index);
				Object targetObject = NSKeyValueCoding.Utility.valueForKey(object, key);
				if (targetObject != null)
					NSKeyValueCodingAdditions.Utility.takeValueForKeyPath(targetObject, value, keyPath.substring(index + 1));
			}
		}

		DefaultImplementation() {
			throw new IllegalStateException("Cannot instantiate an instance of class " + super.getClass().getName());
		}
	}

	public static class Utility {
		public static final Class<?> _CLASS = _NSUtilitiesExtra._classWithFullySpecifiedNamePrime("com.webobjects.foundation.NSKeyValueCodingAdditions$Utility");

		public static Object valueForKeyPath(Object object, String keyPath) {
			if (object == null)
				throw new IllegalArgumentException("Object cannot be null");
			if (object instanceof NSKeyValueCodingAdditions) {
				return ((NSKeyValueCodingAdditions) object).valueForKeyPath(keyPath);
			}
			return NSKeyValueCodingAdditions.DefaultImplementation.valueForKeyPath(object, keyPath);
		}

		public static void takeValueForKeyPath(Object object, Object value, String keyPath) {
			if (object == null)
				throw new IllegalArgumentException("Object cannot be null");
			if (object instanceof NSKeyValueCodingAdditions)
				((NSKeyValueCodingAdditions) object).takeValueForKeyPath(value, keyPath);
			else
				NSKeyValueCodingAdditions.DefaultImplementation.takeValueForKeyPath(object, value, keyPath);
		}

		Utility() {
			throw new IllegalStateException("Cannot instantiate an instance of class " + super.getClass().getName());
		}
	}
}