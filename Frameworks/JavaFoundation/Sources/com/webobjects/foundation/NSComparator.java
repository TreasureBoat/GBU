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

import java.lang.reflect.InvocationTargetException;

public abstract class NSComparator<T extends Object> {
	public static final Class<?> _CLASS = _NSUtilities._classWithFullySpecifiedName("com.webobjects.foundation.NSComparator");

	public static final NSComparator<String> AscendingStringComparator = new _StringComparator(true, false);

	public static final NSComparator<String> DescendingStringComparator = new _StringComparator(false, false);

	public static final NSComparator<String> AscendingCaseInsensitiveStringComparator = new _StringComparator(true, true);

	public static final NSComparator<String> DescendingCaseInsensitiveStringComparator = new _StringComparator(false, true);

	public static final NSComparator<Number> AscendingNumberComparator = new _NumberComparator(true);

	public static final NSComparator<Number> DescendingNumberComparator = new _NumberComparator(false);

	public static final NSComparator<NSTimestamp> AscendingTimestampComparator = new _TimestampComparator(true);

	public static final NSComparator<NSTimestamp> DescendingTimestampComparator = new _TimestampComparator(false);
	public static final int OrderedAscending = -1;
	public static final int OrderedSame = 0;
	public static final int OrderedDescending = 1;

	public abstract int compare(T paramObject1, T paramObject2)
		throws NSComparator.ComparisonException;

	public static int _compareObjects(Comparable<Object> object1, Comparable<Object> object2) {
		if (object1 == object2)
			return 0;
		if (object1 == null)
			return -1;
		if (object2 == null) {
			return 1;
		}

		int comparison = object1.compareTo(object2);
		if (comparison < 0)
			return -1;
		if (comparison > 0) {
			return 1;
		}
		return 0;
	}

	protected static class _NSSelectorComparator<T extends Object>
		extends
		NSComparator<T> {
		NSSelector<T> _selector;
		Class<?> _objectsClass;

		public _NSSelectorComparator(
			NSSelector<T> selector) {
			if (selector == null) {
				throw new IllegalArgumentException("Selector not specified");
			}
			this._selector = selector;

			Class<?>[] types = this._selector.parameterTypes();
			if (types.length != 0)
				this._objectsClass = types[0];
			else
				this._objectsClass = null;
		}

		private void _computeObjectsClassFromObjects(T object1, T object2) {
			Class<?> object1Class = object1.getClass();
			Class<?> object2Class = object2.getClass();
			while (object1Class != object2Class) {
				if (object2Class != _NSUtilities._ObjectClass)
					;
				object2Class = object2Class.getSuperclass();
			}

			if (object1Class != object2Class) {
				object2Class = object2.getClass();
				while (object1Class != object2Class) {
					if (object1Class != _NSUtilities._ObjectClass)
						;
					object1Class = object1Class.getSuperclass();
				}
			}

			this._objectsClass = object1Class;
			this._selector = new NSSelector<T>(this._selector.name(), new Class[] { this._objectsClass });
		}

		public int compare(T object1, T object2)
			throws NSComparator.ComparisonException {
			if (object1 == object2) {
				return 0;
			}

			if ((object1 == null) || (object2 == null)) {
				throw new NSComparator.ComparisonException("Unable to compare objects as one of the is null.");
			}

			if (this._objectsClass == null) {
				_computeObjectsClassFromObjects(object1, object2);
			}

			if ((!(this._objectsClass.isInstance(object1))) || (!(this._objectsClass.isInstance(object2)))) {
				throw new NSComparator.ComparisonException("Unable to compare objects. Objects should be instance of class " + this._objectsClass + ". Comparison was made with " + object1 + " and " + object2 + ".");
			}

			int result = 0;
			try {
				result = ((Number) this._selector.invoke(object1, object2)).intValue();
			} catch (IllegalAccessException e) {
				throw new NSComparator.ComparisonException("<" + super.getClass().getName() + "> Could not invoke comparison selector " + this._selector.name() + "(" + object1.getClass().getName() + ") on object \n" + object1.toString() + "\nas it is not public.");
			} catch (IllegalArgumentException e) {
				throw new NSComparator.ComparisonException("<" + super.getClass().getName() + "> Could not invoke comparison selector " + this._selector.name() + "(" + object1.getClass().getName() + ") on object \n" + object1.toString() + "\nhas wrong number of arguments, or the argument's class is incorrect.");
			} catch (InvocationTargetException e) {
				throw new NSComparator.ComparisonException("<" + super.getClass().getName() + "> Invoking comparison selector " + this._selector.name() + "(" + object1.getClass().getName() + ") on object \n" + object1.toString() + "\n raised an Exception:\n" + e.getTargetException().toString());
			} catch (NoSuchMethodException e) {
				throw new NSComparator.ComparisonException("<" + super.getClass().getName() + "> Could not invoke comparison selector " + this._selector.name() + "(" + object1.getClass().getName() + ") on object \n" + object1.toString() + "\nas it does not exist.");
			}

			if (result < 0)
				return -1;
			if (result > 0) {
				return 1;
			}
			return 0;
		}
	}

	private static class _TimestampComparator
		extends
		NSComparator<NSTimestamp> {
		private boolean _ascending;

		@SuppressWarnings("unused")
		public _TimestampComparator() {
			this(true);
		}

		public _TimestampComparator(
			boolean ascending) {
			this._ascending = ascending;
		}

		public int compare(NSTimestamp timestamp1, NSTimestamp timestamp2)
			throws NSComparator.ComparisonException {
			if ((timestamp1 == null) || (timestamp2 == null)) {
				throw new NSComparator.ComparisonException("Unable to compare objects. Objects should be instance of class NSTimestamp. Comparison was made with " + timestamp1 + " and " + timestamp2 + ".");
			}

			if ((timestamp1 == timestamp2) || (timestamp1.equals(timestamp2))) {
				return 0;
			}

			if (timestamp1.before(timestamp2)) {
				return ((this._ascending) ? -1 : 1);
			}
			return ((this._ascending) ? 1 : -1);
		}
	}

	private static class _NumberComparator
		extends
		NSComparator<Number> {
		private boolean _ascending;

		@SuppressWarnings("unused")
		public _NumberComparator() {
			this(true);
		}

		public _NumberComparator(
			boolean ascending) {
			this._ascending = ascending;
		}

		public int compare(Number number1, Number number2)
			throws NSComparator.ComparisonException {
			if ((number1 == null) || (number2 == null)) {
				throw new NSComparator.ComparisonException("Unable to compare objects. Objects should be instance of class Number. Comparison was made with " + number1 + " and " + number2 + ".");
			}

			if ((number1 == number2) || (number1.equals(number2))) {
				return 0;
			}

			double value1 = number1.doubleValue();
			double value2 = number2.doubleValue();
			if (value1 < value2) {
				return ((this._ascending) ? -1 : 1);
			}
			return ((this._ascending) ? 1 : -1);
		}
	}

	private static class _StringComparator
		extends
		NSComparator<String> {
		private boolean _ascending;
		private boolean _caseInsensitive;

		@SuppressWarnings("unused")
		public _StringComparator() {
			this(true, false);
		}

		public _StringComparator(
			boolean ascending,
			boolean caseInsensitive) {
			this._ascending = ascending;
			this._caseInsensitive = caseInsensitive;
		}

		public int compare(String string1, String string2)
			throws NSComparator.ComparisonException {
			if ((string1 == null) || (string2 == null)) {
				throw new NSComparator.ComparisonException("Unable to compare objects. Objects should be instance of class String. Comparison was made with " + string1 + " and " + string2 + ".");
			}

			if (string1 == string2) {
				return 0;
			}

			int result = 0;
			if (this._caseInsensitive)
				result = string1.toUpperCase().compareTo(string2.toUpperCase());
			else {
				result = string1.compareTo(string2);
			}
			if (result < 0)
				return ((this._ascending) ? -1 : 1);
			if (result > 0) {
				return ((this._ascending) ? 1 : -1);
			}
			return 0;
		}
	}

	public static class ComparisonException
		extends
		Exception {
		private static final long serialVersionUID = -2282920745493922488L;

		public ComparisonException(
			String message) {
			super(message);
		}
	}
}