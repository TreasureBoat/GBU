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

import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.TimeZone;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * This should be re-implemented using ANTLR v3 or above.
 */
public class NSPropertyListSerialization {
	public static final Class<?> _CLASS = _NSUtilities._classWithFullySpecifiedName("com.webobjects.foundation.NSPropertyListSerialization");
	@SuppressWarnings("unused")
	private static final int EOT = -1;
	public static final boolean Indents = true;
	public static final boolean NoIndents = false;
	public static final boolean ForceXML = true;

	private NSPropertyListSerialization() {
		throw new IllegalStateException("Can't instantiate an instance of class " + super.getClass().getName());
	}

	public static String stringFromPropertyList(Object plist) {
		return stringFromPropertyList(plist, true);
	}

	public static String stringFromPropertyList(Object plist, boolean indents) {
		return new _ApplePList(indents).stringFromPropertyList(plist);
	}

	public static String xmlStringFromPropertyList(Object plist) {
		return xmlStringFromPropertyList(plist, true);
	}

	public static String xmlStringFromPropertyList(Object plist, boolean indents) {
		return new _XML(indents).stringFromPropertyList(plist);
	}

	static boolean startsWithXMLDeclaration(String string) {
		return ((string != null) && (string.trim().startsWith("<?xml")));
	}

	public static Object propertyListFromString(String string) {
		return propertyListFromString(string, false);
	}

	public static Object propertyListFromString(String string, boolean forceXML) {
		if ((forceXML) || (startsWithXMLDeclaration(string))) {
			return new _XML().parseStringIntoPlist(string);
		}
		return new _ApplePList().parseStringIntoPlist(string);
	}

	public static Object propertyListWithPathURL(URL url) {
		return propertyListWithPathURL(url, false);
	}

	public static Object propertyListWithPathURL(URL url, boolean forceXML) {
		if (url == null)
			return null;
		return propertyListFromString(_NSStringUtilities.stringFromPathURL(url), forceXML);
	}

	@Deprecated
	public static NSData dataFromPropertyList(Object plist) {
		return dataFromPropertyList(plist, null);
	}

	public static NSData dataFromPropertyList(Object plist, String encoding) {
		if (plist == null)
			return null;
		return new NSData(_NSStringUtilities.bytesForString(stringFromPropertyList(plist), encoding));
	}

	@Deprecated
	public static Object propertyListFromData(NSData data) {
		return propertyListFromData(data, null);
	}

	public static Object propertyListFromData(NSData data, String encoding) {
		if (data == null)
			return null;
		return propertyListFromString(_NSStringUtilities.stringForBytes(data.bytes(), encoding));
	}

	public static boolean booleanForString(String value) {
		if (value != null) {
			String testValue = value.toLowerCase().trim();
			return ((testValue.equals("yes")) || (testValue.equals("true")));
		}
		return false;
	}

	public static int intForString(String value) {
		if (value != null) {
			try {
				return Integer.parseInt(value.trim());
			} catch (Exception exception) {
				throw NSForwardException._runtimeExceptionForThrowable(exception);
			}
		}
		return 0;
	}

	public static NSArray<Object> arrayWithPathURL(URL url) {
		return arrayWithPathURL(url, false);
	}

	@SuppressWarnings("unchecked")
	public static NSArray<Object> arrayWithPathURL(URL url, boolean forceXML) {
		Object result = propertyListWithPathURL(url, forceXML);
		return ((result instanceof NSArray) ? (NSArray<Object>) result : NSArray.emptyArray());
	}

	public static NSArray<Object> arrayForString(String value) {
		return arrayForString(value, false);
	}

	@SuppressWarnings("unchecked")
	public static NSArray<Object> arrayForString(String value, boolean forceXML) {
		Object result = propertyListFromString(value, forceXML);
		return ((result instanceof NSArray) ? (NSArray<Object>) result : NSArray.EmptyArray);
	}

	public static NSDictionary<String, Object> dictionaryWithPathURL(URL url) {
		return dictionaryWithPathURL(url, false);
	}

	@SuppressWarnings("unchecked")
	public static NSDictionary<String, Object> dictionaryWithPathURL(URL url, boolean forceXML) {
		Object result = propertyListWithPathURL(url, forceXML);
		return ((result instanceof NSDictionary) ? (NSDictionary<String, Object>) result : NSDictionary.EmptyDictionary);
	}

	public static NSDictionary<String, Object> dictionaryForString(String value) {
		return dictionaryForString(value, false);
	}

	@SuppressWarnings("unchecked")
	public static NSDictionary<String, Object> dictionaryForString(String value, boolean forceXML) {
		Object result = propertyListFromString(value, forceXML);
		return ((result instanceof NSDictionary) ? (NSDictionary<String, Object>) result : NSDictionary.EmptyDictionary);
	}

	public static class _ApplePList
		extends
		NSPropertyListSerialization._PListParser {
		private int _lineNumber;
		private int _startOfLineCharIndex;
		private int _savedIndex;
		private int _savedLineNumber;
		private int _savedStartOfLineCharIndex;
		private static final int _C_NON_COMMENT_OR_SPACE = 1;
		private static final int _C_WHITESPACE = 2;
		private static final int _C_SINGLE_LINE_COMMENT = 3;
		private static final int _C_MULTI_LINE_COMMENT = 4;
		private static final int[] NSToPrecompUnicodeTable = { 160, 192, 193, 194, 195, 196, 197, 199, 200, 201, 202, 203, 204, 205, 206, 207, 208, 209, 210, 211, 212, 213, 214, 217, 218, 219, 220, 221, 222, 181, 215, 247, 169, 161, 162, 163, 8260, 165, 402, 167, 164, 8217, 8220, 171, 8249, 8250, 64257, 64258, 174, 8211, 8224, 8225, 183, 166, 182, 8226, 8218, 8222, 8221, 187, 8230, 8240, 172, 191, 185, 715, 180, 710, 732, 175, 728, 729, 168, 178, 730, 184, 179, 733, 731, 711, 8212, 177, 188, 189, 190, 224, 225, 226, 227, 228, 229, 231, 232, 233, 234, 235, 236, 198, 237, 170, 238, 239, 240, 241, 321, 216, 338, 186, 242, 243, 244, 245, 246, 230, 249, 250, 251, 305, 252, 253, 322, 248, 339, 223, 254, 255, 65533, 65533 };

		public _ApplePList() {
			super(true);
		}

		public _ApplePList(
			boolean indents) {
			super(indents);
		}

		private void _saveIndexes(int i, int j, int k) {
			this._savedIndex = i;
			this._savedLineNumber = j;
			this._savedStartOfLineCharIndex = k;
		}

		private String _savedIndexesAsString() {
			return "line number: " + this._savedLineNumber + ", column: " + (this._savedIndex - this._savedStartOfLineCharIndex);
		}

		public static boolean propertyListsAreEqual(Object obj, Object obj1) {
			if ((obj == null) && (obj1 == null))
				return true;
			if ((((obj instanceof String) || (obj instanceof StringBuffer))) && (((obj1 instanceof String) || (obj1 instanceof StringBuffer))))
				return obj.toString().equals(obj1.toString());
			if ((obj instanceof NSData) && (obj1 instanceof NSData))
				return ((NSData) obj).isEqualToData((NSData) obj1);
			if ((obj instanceof NSArray) && (obj1 instanceof NSArray)) {
				NSArray<?> nsarray = (NSArray<?>) obj;
				NSArray<?> nsarray1 = (NSArray<?>) obj1;
				int i = nsarray.count();
				int k = nsarray1.count();
				if (i != k)
					return false;
				for (int i1 = 0; i1 < i; ++i1) {
					if (!(propertyListsAreEqual(nsarray.objectAtIndex(i1), nsarray1.objectAtIndex(i1))))
						return false;
				}
				return true;
			}
			if ((obj instanceof NSDictionary) && (obj1 instanceof NSDictionary)) {
				NSDictionary<?, ?> nsdictionary = (NSDictionary<?, ?>) obj;
				NSDictionary<?, ?> nsdictionary1 = (NSDictionary<?, ?>) obj1;
				int j = nsdictionary.count();
				int l = nsdictionary1.count();
				if (j != l)
					return false;
				for (Enumeration<?> enumeration = nsdictionary.keyEnumerator(); enumeration.hasMoreElements();) {
					Object obj2 = enumeration.nextElement();
					Object obj3 = nsdictionary1.objectForKey(obj2);
					if (obj3 == null)
						return false;
					Object obj4 = nsdictionary.objectForKey(obj2);
					if (!(propertyListsAreEqual(obj4, obj3))) {
						return false;
					}
				}
				return true;
			}
			return false;
		}

		public static Object copyPropertyList(Object obj) {
			if (obj == null)
				return null;
			if (obj instanceof String)
				return obj;
			if (obj instanceof StringBuffer)
				return new String((StringBuffer) obj);
			if (obj instanceof NSData)
				return ((NSData) obj).clone();
			if (obj instanceof NSArray) {
				NSArray<?> array = (NSArray<?>) obj;
				int i = array.count();
				NSMutableArray<?> newArray = new NSMutableArray<Object>(i);
				for (int j = 0; j < i; ++j) {
					newArray.addObject(copyPropertyList(array.objectAtIndex(j)));
				}
				return newArray;
			}
			if (obj instanceof NSDictionary) {
				NSDictionary<?, ?> dictionary = (NSDictionary<?, ?>) obj;
				NSMutableDictionary<?, ?> newDictionary = new NSMutableDictionary<Object, Object>(dictionary.count());
				Object key = null;
				Object value = null;
				for (Enumeration<?> enumeration = dictionary.keyEnumerator(); enumeration.hasMoreElements(); newDictionary.setObjectForKey(copyPropertyList(value), copyPropertyList(key))) {
					key = enumeration.nextElement();
					value = dictionary.objectForKey(key);
				}

				return newDictionary;
			}
			throw new IllegalArgumentException("Property list copying failed while attempting to copy non property list type: " + obj.getClass().getName());
		}

		public String stringFromPropertyList(Object plist) {
			if (plist == null)
				return null;
			StringBuffer buffer = new StringBuffer(128);
			_appendObjectToStringBuffer(plist, buffer, 0);
			return _NSStringUtilities.stringFromBuffer(buffer);
		}

		public Object parseStringIntoPlist(String string) {
			if (string == null)
				return null;
			char[] charArray = string.toCharArray();
			Object[] aobj = new Object[1];
			this._lineNumber = 1;
			this._startOfLineCharIndex = 0;
			aobj[0] = null;
			int i = 0;
			i = _readObjectIntoObjectReference(charArray, i, aobj);
			i = _skipWhitespaceAndComments(charArray, i);
			if (i != -1) {
				throw new IllegalArgumentException("parseStringIntoPlist parsed an object, but there's still more text in the string. A plist should contain only one top-level object. Line number: " + this._lineNumber + ", column: " + (i - this._startOfLineCharIndex) + ".");
			}

			return aobj[0];
		}

		private void _appendObjectToStringBuffer(Object obj, StringBuffer stringbuffer, int i) {
			if (obj instanceof String) {
				_appendStringToStringBuffer((String) obj, stringbuffer, i);
			} else if (obj instanceof StringBuffer) {
				_appendStringToStringBuffer(((StringBuffer) obj).toString(), stringbuffer, i);
			} else if (obj instanceof NSData) {
				_appendDataToStringBuffer((NSData) obj, stringbuffer, i);
			} else if (obj instanceof NSArray) {
				_appendArrayToStringBuffer((NSArray<?>) obj, stringbuffer, i);
			} else if (obj instanceof NSDictionary) {
				_appendDictionaryToStringBuffer((NSDictionary<?, ?>) obj, stringbuffer, i);
			} else if (obj instanceof Boolean) {
				String s = (((Boolean) obj).booleanValue()) ? "true" : "false";
				_appendStringToStringBuffer(s, stringbuffer, i);
			} else {
				_appendStringToStringBuffer(obj.toString(), stringbuffer, i);
			}
		}

		private void _appendStringToStringBuffer(String s, StringBuffer stringbuffer, int i) {
			stringbuffer.append('"');
			char[] ac = s.toCharArray();
			for (int j = 0; j < ac.length; ++j) {
				if (ac[j] < 128) {
					if (ac[j] == '\n') {
						stringbuffer.append("\\n");
					} else if (ac[j] == '\r') {
						stringbuffer.append("\\r");
					} else if (ac[j] == '\t') {
						stringbuffer.append("\\t");
					} else if (ac[j] == '"') {
						stringbuffer.append('\\');
						stringbuffer.append('"');
					} else if (ac[j] == '\\') {
						stringbuffer.append("\\\\");
					} else if (ac[j] == '\f') {
						stringbuffer.append("\\f");
					} else if (ac[j] == '\b') {
						stringbuffer.append("\\b");
					} else if (ac[j] == '\7') {
						stringbuffer.append("\\a");
					} else if (ac[j] == '\11') {
						stringbuffer.append("\\v");
					} else {
						stringbuffer.append(ac[j]);
					}
				} else {
					char c = ac[j];
					byte byte0 = (byte) (c & 0xF);
					c = (char) (c >> '\4');
					byte byte1 = (byte) (c & 0xF);
					c = (char) (c >> '\4');
					byte byte2 = (byte) (c & 0xF);
					c = (char) (c >> '\4');
					byte byte3 = (byte) (c & 0xF);
					c = (char) (c >> '\4');
					stringbuffer.append("\\U");
					stringbuffer.append(_hexDigitForNibble(byte3));
					stringbuffer.append(_hexDigitForNibble(byte2));
					stringbuffer.append(_hexDigitForNibble(byte1));
					stringbuffer.append(_hexDigitForNibble(byte0));
				}
			}
			stringbuffer.append('"');
		}

		private void _appendDataToStringBuffer(NSData nsdata, StringBuffer stringbuffer, int i) {
			stringbuffer.append('<');
			byte[] abyte0 = nsdata.bytes();
			for (int j = 0; j < abyte0.length; ++j) {
				byte byte0 = abyte0[j];
				byte byte1 = (byte) (byte0 & 0xF);
				byte0 = (byte) (byte0 >> 4);
				byte byte2 = (byte) (byte0 & 0xF);
				stringbuffer.append(_hexDigitForNibble(byte2));
				stringbuffer.append(_hexDigitForNibble(byte1));
			}

			stringbuffer.append('>');
		}

		private void _appendArrayToStringBuffer(NSArray<?> nsarray, StringBuffer stringbuffer, int i) {
			stringbuffer.append('(');
			int j = nsarray.count();
			if (j > 0) {
				for (int k = 0; k < j; ++k) {
					if (k > 0)
						stringbuffer.append(',');
					_appendNewLineToStringBuffer(stringbuffer, i);
					_appendIndentationToStringBuffer(stringbuffer, i + 1);
					_appendObjectToStringBuffer(nsarray.objectAtIndex(k), stringbuffer, i + 1);
				}

				_appendNewLineToStringBuffer(stringbuffer, i);
				_appendIndentationToStringBuffer(stringbuffer, i);
			}
			stringbuffer.append(')');
		}

		private void _appendDictionaryToStringBuffer(NSDictionary<?, ?> nsdictionary, StringBuffer stringbuffer, int i) {
			stringbuffer.append('{');
			int j = nsdictionary.count();
			if (j > 0) {
				for (Enumeration<?> enumeration = nsdictionary.keyEnumerator(); enumeration.hasMoreElements(); stringbuffer.append(';')) {
					Object obj = enumeration.nextElement();
					if (!(obj instanceof String)) {
						throw new IllegalArgumentException("Property list generation failed while attempting to write hashtable. Non-String key found in Hashtable. Property list dictionaries must have String's as keys.");
					}
					_appendNewLineToStringBuffer(stringbuffer, i);
					_appendIndentationToStringBuffer(stringbuffer, i + 1);
					_appendStringToStringBuffer((String) obj, stringbuffer, i + 1);
					stringbuffer.append(" = ");
					_appendObjectToStringBuffer(nsdictionary.objectForKey(obj), stringbuffer, i + 1);
				}

				_appendNewLineToStringBuffer(stringbuffer, i);
				_appendIndentationToStringBuffer(stringbuffer, i);
			}
			stringbuffer.append('}');
		}

		private final char _hexDigitForNibble(byte nibble) {
			char c = '\0';
			if ((nibble >= 0) && (nibble <= 9))
				c = (char) ('0' + (char) nibble);
			else if ((nibble >= 10) && (nibble <= 15)) {
				c = (char) ('a' + (char) (nibble - 10));
			}
			return c;
		}

		private int _readObjectIntoObjectReference(char[] ac, int index, Object[] aobj) {
			int aBufferIndex = index;
			aBufferIndex = _skipWhitespaceAndComments(ac, aBufferIndex);
			if ((aBufferIndex == -1) || (aBufferIndex >= ac.length)) {
				aobj[0] = null;
			} else if (ac[aBufferIndex] == '"') {
				StringBuffer buffer = new StringBuffer(64);
				aBufferIndex = _readQuotedStringIntoStringBuffer(ac, aBufferIndex, buffer);
				aobj[0] = _NSStringUtilities.stringFromBuffer(buffer);
			} else if (ac[aBufferIndex] == '<') {
				NSMutableData data = new NSMutableData(_lengthOfData(ac, aBufferIndex));
				aBufferIndex = _readDataContentsIntoData(ac, aBufferIndex, data);
				aobj[0] = data;
			} else if (ac[aBufferIndex] == '(') {
				NSMutableArray<?> array = new NSMutableArray<Object>();
				aBufferIndex = _readArrayContentsIntoArray(ac, aBufferIndex, array);
				aobj[0] = array;
			} else if (ac[aBufferIndex] == '{') {
				NSMutableDictionary<?, ?> dictionary = new NSMutableDictionary<Object, Object>();
				aBufferIndex = _readDictionaryContentsIntoDictionary(ac, aBufferIndex, dictionary);
				aobj[0] = dictionary;
			} else {
				StringBuffer buffer = new StringBuffer(64);
				aBufferIndex = _readUnquotedStringIntoStringBuffer(ac, aBufferIndex, buffer);
				aobj[0] = _NSStringUtilities.stringFromBuffer(buffer);
			}
			return ((aBufferIndex < ac.length) ? aBufferIndex : -1);
		}

		private int _readUnquotedStringIntoStringBuffer(char[] ac, int index, StringBuffer buffer) {
			int aBufferIndex = index;
			int j = aBufferIndex;
			buffer.setLength(0);

			while ((aBufferIndex < ac.length) && ((((ac[aBufferIndex] >= 'a') && (ac[aBufferIndex] <= 'z')) || ((ac[aBufferIndex] >= 'A') && (ac[aBufferIndex] <= 'Z')) || ((ac[aBufferIndex] >= '0') && (ac[aBufferIndex] <= '9')) || (ac[aBufferIndex] == '_') || (ac[aBufferIndex] == '$') || (ac[aBufferIndex] == ':') || (ac[aBufferIndex] == '.') || (ac[aBufferIndex] == '/') || (ac[aBufferIndex] == '-'))))
				++aBufferIndex;
			if (j < aBufferIndex)
				buffer.append(ac, j, aBufferIndex - j);
			else {
				throw new IllegalArgumentException("Property list parsing failed while attempting to read unquoted string. No allowable characters were found. At line number: " + this._lineNumber + ", column: " + (aBufferIndex - this._startOfLineCharIndex) + ".");
			}
			return ((aBufferIndex < ac.length) ? aBufferIndex : -1);
		}

		private int _readQuotedStringIntoStringBuffer(char[] ac, int index, StringBuffer stringbuffer) {
			int aBufferIndex = index;
			_saveIndexes(aBufferIndex, this._lineNumber, this._startOfLineCharIndex);
			int j = ++aBufferIndex;
			while ((aBufferIndex < ac.length) && (ac[aBufferIndex] != '"')) {
				if (ac[aBufferIndex] == '\\') {
					if (j < aBufferIndex)
						stringbuffer.append(ac, j, aBufferIndex - j);
					if (++aBufferIndex >= ac.length) {
						throw new IllegalArgumentException("Property list parsing failed while attempting to read quoted string. Input exhausted before closing quote was found. Opening quote was at " + _savedIndexesAsString() + ".");
					}
					if (ac[aBufferIndex] == 'n') {
						stringbuffer.append('\n');
						++aBufferIndex;
					} else if (ac[aBufferIndex] == 'r') {
						stringbuffer.append('\r');
						++aBufferIndex;
					} else if (ac[aBufferIndex] == 't') {
						stringbuffer.append('\t');
						++aBufferIndex;
					} else if (ac[aBufferIndex] == 'f') {
						stringbuffer.append('\f');
						++aBufferIndex;
					} else if (ac[aBufferIndex] == 'b') {
						stringbuffer.append('\b');
						++aBufferIndex;
					} else if (ac[aBufferIndex] == 'a') {
						stringbuffer.append('\7');
						++aBufferIndex;
					} else if (ac[aBufferIndex] == 'v') {
						stringbuffer.append('\11');
						++aBufferIndex;
					} else if ((ac[aBufferIndex] == 'u') || (ac[aBufferIndex] == 'U')) {
						if (aBufferIndex + 4 >= ac.length) {
							throw new IllegalArgumentException("Property list parsing failed while attempting to read quoted string. Input exhausted before escape sequence was completed. Opening quote was at " + _savedIndexesAsString() + ".");
						}

						++aBufferIndex;
						if ((!(_isHexDigit(ac[aBufferIndex]))) || (!(_isHexDigit(ac[(aBufferIndex + 1)]))) || (!(_isHexDigit(ac[(aBufferIndex + 2)]))) || (!(_isHexDigit(ac[(aBufferIndex + 3)])))) {
							throw new IllegalArgumentException("Property list parsing failed while attempting to read quoted string. Improperly formed \\U type escape sequence. At line number: " + this._lineNumber + ", column: " + (aBufferIndex - this._startOfLineCharIndex) + ".");
						}
						byte byte0 = _nibbleForHexDigit(ac[aBufferIndex]);
						byte byte1 = _nibbleForHexDigit(ac[(aBufferIndex + 1)]);
						byte byte2 = _nibbleForHexDigit(ac[(aBufferIndex + 2)]);
						byte byte3 = _nibbleForHexDigit(ac[(aBufferIndex + 3)]);
						stringbuffer.append((char) ((byte0 << 12) + (byte1 << 8) + (byte2 << 4) + byte3));
						aBufferIndex += 4;
					} else if ((ac[aBufferIndex] >= '0') && (ac[aBufferIndex] <= '7')) {
						int k = 0;
						int l = 1;
						int[] ai = new int[3];
						ai[0] = (ac[aBufferIndex] - '0');
						for (++aBufferIndex; (l < 3) && (aBufferIndex < ac.length) && (ac[aBufferIndex] >= '0') && (ac[aBufferIndex] <= '7'); ++aBufferIndex) {
							ai[(l++)] = (ac[aBufferIndex] - '0');
						}
						if ((l == 3) && (ai[0] > 3)) {
							throw new IllegalArgumentException("Property list parsing failed while attempting to read quoted string. Octal escape sequence too large (bigger than octal 377). At line number: " + this._lineNumber + ", column: " + (aBufferIndex - this._startOfLineCharIndex) + ".");
						}

						for (int i1 = 0; i1 < l; ++i1) {
							k *= 8;
							k += ai[i1];
						}

						stringbuffer.append(_nsToUnicode(k));
					} else {
						stringbuffer.append(ac[aBufferIndex]);
						if (ac[aBufferIndex] == '\n') {
							this._lineNumber += 1;
							this._startOfLineCharIndex = (aBufferIndex + 1);
						}
						++aBufferIndex;
					}
					j = aBufferIndex;
				}
				if (ac[aBufferIndex] == '\n') {
					this._lineNumber += 1;
					this._startOfLineCharIndex = (aBufferIndex + 1);
				}
				++aBufferIndex;
			}
			if (j < aBufferIndex)
				stringbuffer.append(ac, j, aBufferIndex - j);
			if (aBufferIndex >= ac.length) {
				throw new IllegalArgumentException("Property list parsing failed while attempting to read quoted string. Input exhausted before closing quote was found. Opening quote was at " + _savedIndexesAsString() + ".");
			}

			return ((++aBufferIndex < ac.length) ? aBufferIndex : -1);
		}

		private int _lengthOfData(char[] ac, int index) {
			int aBufferIndex = index;
			int j = 0;
			boolean isHexDigit;
			for (++aBufferIndex; (aBufferIndex < ac.length) && ((((isHexDigit = _isHexDigit(ac[aBufferIndex]))) || (_isWhitespace(ac[aBufferIndex])))); ++aBufferIndex) {
				if (isHexDigit)
					++j;
			}
			if (aBufferIndex >= ac.length) {
				throw new IllegalArgumentException("Property list parsing failed while attempting to read data. Input exhausted before data was terminated with '>'. At line number: " + this._lineNumber + ", column: " + (aBufferIndex - this._startOfLineCharIndex) + ".");
			}
			if (ac[aBufferIndex] != '>') {
				throw new IllegalArgumentException("Property list parsing failed while attempting to read data. Illegal character encountered in data: '" + ac[aBufferIndex] + "'. At line number: " + this._lineNumber + ", column: " + (aBufferIndex - this._startOfLineCharIndex) + ".");
			}
			if (j % 2 != 0) {
				throw new IllegalArgumentException("Property list parsing failed while attempting to read data. An odd number of half-bytes were specified. At line number: " + this._lineNumber + ", column: " + (aBufferIndex - this._startOfLineCharIndex) + ".");
			}

			return (j / 2);
		}

		private int _readDataContentsIntoData(char[] ac, int index, NSMutableData nsmutabledata) {
			int aBufferIndex = index;
			++aBufferIndex;

			while (ac[aBufferIndex] != '>') {
				aBufferIndex = _skipWhitespaceAndComments(ac, aBufferIndex);
				if (ac[aBufferIndex] == '>')
					break;
				byte byte0 = _nibbleForHexDigit(ac[aBufferIndex]);
				++aBufferIndex;
				aBufferIndex = _skipWhitespaceAndComments(ac, aBufferIndex);
				byte byte1 = _nibbleForHexDigit(ac[aBufferIndex]);
				++aBufferIndex;
				nsmutabledata.appendByte((byte) ((byte0 << 4) + byte1));
			}
			return ((++aBufferIndex < ac.length) ? aBufferIndex : -1);
		}

		private int _readArrayContentsIntoArray(char[] ac, int index, NSMutableArray<?> nsmutablearray) {
			int aBufferIndex = index;
			Object[] aobj = new Object[1];
			++aBufferIndex;
			nsmutablearray.removeAllObjects();
			aBufferIndex = _skipWhitespaceAndComments(ac, aBufferIndex);

			while (aBufferIndex != -1) {
				if (ac[aBufferIndex] == ')')
					break;
				if (nsmutablearray.count() > 0) {
					if (ac[aBufferIndex] != ',') {
						throw new IllegalArgumentException("Property list parsing failed while attempting to read array. No comma found between array elements. At line number: " + this._lineNumber + ", column: " + (aBufferIndex - this._startOfLineCharIndex) + ".");
					}
					++aBufferIndex;
					aBufferIndex = _skipWhitespaceAndComments(ac, aBufferIndex);
					if (aBufferIndex == -1) {
						throw new IllegalArgumentException("Property list parsing failed while attempting to read array. Input exhausted before end of array was found. At line number: " + this._lineNumber + ", column: " + (aBufferIndex - this._startOfLineCharIndex) + ".");
					}
				}
				if (ac[aBufferIndex] != ')')
					;
				aobj[0] = null;
				aBufferIndex = _readObjectIntoObjectReference(ac, aBufferIndex, aobj);
				if (aobj[0] == null) {
					throw new IllegalArgumentException("Property list parsing failed while attempting to read array. Failed to read content object. At line number: " + this._lineNumber + ", column: " + (aBufferIndex - this._startOfLineCharIndex) + ".");
				}
				aBufferIndex = _skipWhitespaceAndComments(ac, aBufferIndex);
				nsmutablearray.addObject(aobj[0]);
			}

			if (aBufferIndex == -1) {
				throw new IllegalArgumentException("Property list parsing failed while attempting to read array. Input exhausted before end of array was found. At line number: " + this._lineNumber + ", column: " + (aBufferIndex - this._startOfLineCharIndex) + ".");
			}

			return ((++aBufferIndex < ac.length) ? aBufferIndex : -1);
		}

		private int _readDictionaryContentsIntoDictionary(char[] ac, int index, NSMutableDictionary<?, ?> nsmutabledictionary) {
			int aBufferIndex = index;
			Object[] aobj = new Object[1];
			Object[] aobj1 = new Object[1];
			++aBufferIndex;
			if (nsmutabledictionary.count() != 0) {
				for (Enumeration<?> enumeration = nsmutabledictionary.keyEnumerator(); enumeration.hasMoreElements(); nsmutabledictionary.removeObjectForKey(enumeration.nextElement()))
					;
			}
			for (aBufferIndex = _skipWhitespaceAndComments(ac, aBufferIndex); (aBufferIndex != -1) && (ac[aBufferIndex] != '}');) {
				aBufferIndex = _readObjectIntoObjectReference(ac, aBufferIndex, aobj);
				if ((aobj[0] == null) || (!(aobj[0] instanceof String))) {
					throw new IllegalArgumentException("Property list parsing failed while attempting to read dictionary. Failed to read key or key is not a String. At line number: " + this._lineNumber + ", column: " + (aBufferIndex - this._startOfLineCharIndex) + ".");
				}
				aBufferIndex = _skipWhitespaceAndComments(ac, aBufferIndex);
				if ((aBufferIndex == -1) || (ac[aBufferIndex] != '=')) {
					throw new IllegalArgumentException("Property list parsing failed while attempting to read dictionary. Read key " + aobj[0] + " with no value. At line number: " + this._lineNumber + ", column: " + (aBufferIndex - this._startOfLineCharIndex) + ".");
				}
				++aBufferIndex;
				aBufferIndex = _skipWhitespaceAndComments(ac, aBufferIndex);
				if (aBufferIndex == -1) {
					throw new IllegalArgumentException("Property list parsing failed while attempting to read dictionary. Read key " + aobj[0] + " with no value. At line number: " + this._lineNumber + ", column: " + (aBufferIndex - this._startOfLineCharIndex) + ".");
				}
				aBufferIndex = _readObjectIntoObjectReference(ac, aBufferIndex, aobj1);
				if (aobj1[0] == null) {
					throw new IllegalArgumentException("Property list parsing failed while attempting to read dictionary. Failed to read value. At line number: " + this._lineNumber + ", column: " + (aBufferIndex - this._startOfLineCharIndex) + ".");
				}
				aBufferIndex = _skipWhitespaceAndComments(ac, aBufferIndex);
				if ((aBufferIndex == -1) || (ac[aBufferIndex] != ';')) {
					throw new IllegalArgumentException("Property list parsing failed while attempting to read dictionary. Read key and value with no terminating semicolon. At line number: " + this._lineNumber + ", column: " + (aBufferIndex - this._startOfLineCharIndex) + ".");
				}
				++aBufferIndex;
				aBufferIndex = _skipWhitespaceAndComments(ac, aBufferIndex);
				nsmutabledictionary.setObjectForKey(aobj1[0], aobj[0]);
			}

			if (aBufferIndex >= ac.length) {
				throw new IllegalArgumentException("Property list parsing failed while attempting to read dictionary. Exhausted input before end of dictionary was found. At line number: " + this._lineNumber + ", column: " + (aBufferIndex - this._startOfLineCharIndex) + ".");
			}

			return ((++aBufferIndex < ac.length) ? aBufferIndex : -1);
		}

		private int _checkForWhitespaceOrComment(char[] ac, int index) {
			if ((index == -1) || (index >= ac.length))
				return _C_NON_COMMENT_OR_SPACE;
			if (_isWhitespace(ac[index]))
				return _C_WHITESPACE;
			if (index + 1 < ac.length) {
				if ((ac[index] == '/') && (ac[(index + 1)] == '/'))
					return _C_SINGLE_LINE_COMMENT;
				if ((ac[index] == '/') && (ac[(index + 1)] == '*'))
					return _C_MULTI_LINE_COMMENT;
			}
			return 1;
		}

		private int _skipWhitespaceAndComments(char[] ac, int index) {
			int aBufferIndex = index;
			for (int j = _checkForWhitespaceOrComment(ac, aBufferIndex); j != 1; j = _checkForWhitespaceOrComment(ac, aBufferIndex)) {
				switch (j) {
				case 2:
					aBufferIndex = _processWhitespace(ac, aBufferIndex);
					break;
				case 3:
					aBufferIndex = _processSingleLineComment(ac, aBufferIndex);
					break;
				case 4:
					aBufferIndex = _processMultiLineComment(ac, aBufferIndex);
				}
			}

			return ((aBufferIndex < ac.length) ? aBufferIndex : -1);
		}

		private int _processWhitespace(char[] ac, int index) {
			int aBufferIndex = index;
			for (; (aBufferIndex < ac.length) && (_isWhitespace(ac[aBufferIndex])); ++aBufferIndex) {
				if (ac[aBufferIndex] == '\n') {
					this._lineNumber += 1;
					this._startOfLineCharIndex = (aBufferIndex + 1);
				}
			}
			return ((aBufferIndex < ac.length) ? aBufferIndex : -1);
		}

		private int _processSingleLineComment(char[] ac, int index) {
			int aBufferIndex = index;
			for (aBufferIndex += 2; (aBufferIndex < ac.length) && (ac[aBufferIndex] != '\n'); ++aBufferIndex)
				;
			return ((aBufferIndex < ac.length) ? aBufferIndex : -1);
		}

		private int _processMultiLineComment(char[] ac, int index) {
			int aBufferIndex = index;
			_saveIndexes(aBufferIndex, this._lineNumber, this._startOfLineCharIndex);
			for (aBufferIndex += 2; (aBufferIndex + 1 < ac.length) && (((ac[aBufferIndex] != '*') || (ac[(aBufferIndex + 1)] != '/'))); ++aBufferIndex) {
				if ((ac[aBufferIndex] == '/') && (ac[(aBufferIndex + 1)] == '*')) {
					throw new IllegalArgumentException("Property list parsing does not support embedded multi line comments.The first opening comment was at " + _savedIndexesAsString() + ". A second opening comment was found at line " + this._lineNumber + ", column: " + (aBufferIndex - this._startOfLineCharIndex) + ".");
				}

				if (ac[aBufferIndex] == '\n') {
					this._lineNumber += 1;
					this._startOfLineCharIndex = (aBufferIndex + 1);
				}
			}

			if ((aBufferIndex + 1 < ac.length) && (ac[aBufferIndex] == '*') && (ac[(aBufferIndex + 1)] == '/'))
				aBufferIndex += 2;
			else {
				throw new IllegalArgumentException("Property list parsing failed while attempting to find closing to comment that began at " + _savedIndexesAsString() + ".");
			}
			return ((aBufferIndex < ac.length) ? aBufferIndex : -1);
		}

		private final byte _nibbleForHexDigit(char c) {
			int i = 0;
			if ((c >= '0') && (c <= '9'))
				i = (byte) (c - '0');
			else if ((c >= 'a') && (c <= 'f'))
				i = (byte) (c - 'a' + 10);
			else if ((c >= 'A') && (c <= 'F'))
				i = (byte) (c - 'A' + 10);
			else
				throw new IllegalArgumentException("Non-hex digit passed to _nibbleForHexDigit()");
			return (byte) i;
		}

		private final boolean _isHexDigit(char c) {
			return (((c >= '0') && (c <= '9')) || ((c >= 'a') && (c <= 'f')) || ((c >= 'A') && (c <= 'F')));
		}

		private final boolean _isWhitespace(char c) {
			return Character.isWhitespace(c);
		}

		private char _nsToUnicode(int i) {
			return ((i >= 128) ? (char) NSToPrecompUnicodeTable[(i - 128)] : (char) i);
		}
	}

	public static class _XML
		extends
		NSPropertyListSerialization._PListParser {
		protected static SAXParserFactory _parserFactory;
		protected SimpleDateFormat _dateFormat;

		public _XML() {
			this(true);
		}

		public _XML(
			boolean indents) {
			super(indents);

			this._dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
			this._dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
			this._dateFormat.setLenient(true);
		}

		public static SAXParserFactory parserFactory() {
			if (_parserFactory == null) {
				try {
					_parserFactory = SAXParserFactory.newInstance();
				} catch (Exception exception) {
					NSLog._conditionallyLogPrivateException(exception);
				}
			}
			return _parserFactory;
		}

		public SAXParser newSAXParser() {
			if (parserFactory() != null) {
				try {
					return parserFactory().newSAXParser();
				} catch (Exception exception) {
					NSLog._conditionallyLogPrivateException(exception);
				}
			}
			return null;
		}

		public Object parseStringIntoPlist(String string) {
			DictionaryParser dictionaryParser = new DictionaryParser();
			try {
				SAXParser parser = newSAXParser();
				if (parser != null)
					parser.parse(new InputSource(new StringReader(string)), dictionaryParser);
			} catch (SAXException exception) {
				NSLog._conditionallyLogPrivateException(exception);
				if (exception instanceof SAXParseException) {
					throw new NSForwardException(exception, "Parsing failed in line " + ((SAXParseException) exception).getLineNumber() + ", column " + ((SAXParseException) exception).getColumnNumber());
				}

				throw new NSForwardException(exception);
			} catch (IOException ioexception) {
				throw NSForwardException._runtimeExceptionForThrowable(ioexception);
			}
			return dictionaryParser.plist();
		}

		public String stringFromPropertyList(Object plist) {
			if (plist == null)
				return null;
			StringBuffer stringbuffer = new StringBuffer(128);
			stringbuffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>");
			_appendNewLineToStringBuffer(stringbuffer, 0);
			stringbuffer.append("<!DOCTYPE plist PUBLIC \"" + DictionaryParser.PUBLIC_APPLE_PLIST_1_0 + "\" \"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">");
			_appendNewLineToStringBuffer(stringbuffer, 0);
			stringbuffer.append("<plist version=\"1.0\">");
			_appendNewLineToStringBuffer(stringbuffer, 0);
			_appendObjectToStringBuffer(plist, stringbuffer, 1);
			stringbuffer.append("</plist>");
			return stringbuffer.toString();
		}

		private void _appendObjectToStringBuffer(Object obj, StringBuffer stringbuffer, int i) {
			if ((obj instanceof String) || (obj instanceof StringBuffer))
				_appendStringToStringBuffer(obj.toString(), stringbuffer, i);
			else if ((obj instanceof Integer) || (obj instanceof Long) || (obj instanceof BigInteger))
				_appendIntegerToStringBuffer((Number) obj, stringbuffer, i);
			else if ((obj instanceof Float) || (obj instanceof Double) || (obj instanceof BigDecimal))
				_appendFloatToStringBuffer((Number) obj, stringbuffer, i);
			else if (obj instanceof Date)
				_appendDateToStringBuffer((Date) obj, stringbuffer, i);
			else if (obj instanceof Boolean)
				_appendBooleanToStringBuffer((Boolean) obj, stringbuffer, i);
			else if (obj instanceof NSData)
				_appendDataToStringBuffer((NSData) obj, stringbuffer, i);
			else if (obj instanceof List)
				_appendArrayToStringBuffer((List<?>) obj, stringbuffer, i);
			else if (obj instanceof Map)
				_appendDictionaryToStringBuffer((Map<?, ?>) obj, stringbuffer, i);
			else
				_appendStringToStringBuffer(obj.toString(), stringbuffer, i);
		}

		private void _appendStringToStringBuffer(String s, StringBuffer stringbuffer, int i) {
			_appendIndentationToStringBuffer(stringbuffer, i);
			stringbuffer.append(DictionaryParser.XMLNode.Type.STRING.openTag());
			stringbuffer.append(escapeString(s));
			stringbuffer.append(DictionaryParser.XMLNode.Type.STRING.closeTag());
			_appendNewLineToStringBuffer(stringbuffer, i);
		}

		private void _appendIntegerToStringBuffer(Number s, StringBuffer stringbuffer, int i) {
			_appendIndentationToStringBuffer(stringbuffer, i);
			stringbuffer.append(DictionaryParser.XMLNode.Type.INTEGER.openTag());
			stringbuffer.append(s.toString());
			stringbuffer.append(DictionaryParser.XMLNode.Type.INTEGER.closeTag());
			_appendNewLineToStringBuffer(stringbuffer, i);
		}

		private void _appendFloatToStringBuffer(Number s, StringBuffer stringbuffer, int i) {
			_appendIndentationToStringBuffer(stringbuffer, i);
			stringbuffer.append(DictionaryParser.XMLNode.Type.REAL.openTag());
			stringbuffer.append(s.toString());
			stringbuffer.append(DictionaryParser.XMLNode.Type.REAL.closeTag());
			_appendNewLineToStringBuffer(stringbuffer, i);
		}

		private void _appendBooleanToStringBuffer(Boolean s, StringBuffer stringbuffer, int i) {
			_appendIndentationToStringBuffer(stringbuffer, i);
			if (s.booleanValue()) {
				stringbuffer.append(DictionaryParser.XMLNode.Type.TRUE.openTag());
				stringbuffer.append(DictionaryParser.XMLNode.Type.TRUE.closeTag());
			} else {
				stringbuffer.append(DictionaryParser.XMLNode.Type.FALSE.openTag());
				stringbuffer.append(DictionaryParser.XMLNode.Type.FALSE.closeTag());
			}
			_appendNewLineToStringBuffer(stringbuffer, i);
		}

		private void _appendDateToStringBuffer(Date s, StringBuffer stringbuffer, int i) {
			_appendIndentationToStringBuffer(stringbuffer, i);
			stringbuffer.append(DictionaryParser.XMLNode.Type.DATE.openTag());
			stringbuffer.append(this._dateFormat.format(s));
			stringbuffer.append(DictionaryParser.XMLNode.Type.DATE.closeTag());
			_appendNewLineToStringBuffer(stringbuffer, i);
		}

		private void _appendDataToStringBuffer(NSData s, StringBuffer stringbuffer, int i) {
			_appendIndentationToStringBuffer(stringbuffer, i);
			stringbuffer.append(DictionaryParser.XMLNode.Type.DATA.openTag());
			stringbuffer.append(_NSBase64.encode(s.bytes()));
			stringbuffer.append(DictionaryParser.XMLNode.Type.DATA.closeTag());
			_appendNewLineToStringBuffer(stringbuffer, i);
		}

		private void _appendArrayToStringBuffer(List<?> vector, StringBuffer stringbuffer, int i) {
			_appendIndentationToStringBuffer(stringbuffer, i);
			stringbuffer.append(DictionaryParser.XMLNode.Type.ARRAY.openTag());
			_appendNewLineToStringBuffer(stringbuffer, i);
			for (Iterator<?> iterator = vector.iterator(); iterator.hasNext();) {
				_appendObjectToStringBuffer(iterator.next(), stringbuffer, i + 1);
			}
			_appendIndentationToStringBuffer(stringbuffer, i);
			stringbuffer.append(DictionaryParser.XMLNode.Type.ARRAY.closeTag());
			_appendNewLineToStringBuffer(stringbuffer, i);
		}

		private void _appendDictionaryToStringBuffer(Map<?, ?> table, StringBuffer stringbuffer, int i) {
			_appendIndentationToStringBuffer(stringbuffer, i);
			stringbuffer.append(DictionaryParser.XMLNode.Type.DICTIONARY.openTag());
			_appendNewLineToStringBuffer(stringbuffer, i);
			for (Iterator<?> iterator = table.keySet().iterator(); iterator.hasNext();) {
				Object key = iterator.next();

				_appendIndentationToStringBuffer(stringbuffer, i + 1);
				stringbuffer.append(DictionaryParser.XMLNode.Type.KEY.openTag());
				stringbuffer.append(escapeString(key.toString()));
				stringbuffer.append(DictionaryParser.XMLNode.Type.KEY.closeTag());
				_appendNewLineToStringBuffer(stringbuffer, i + 1);
				_appendObjectToStringBuffer(table.get(key), stringbuffer, i + 1);
				_appendNewLineToStringBuffer(stringbuffer, i + 1);
			}
			_appendIndentationToStringBuffer(stringbuffer, i);
			stringbuffer.append(DictionaryParser.XMLNode.Type.DICTIONARY.closeTag());
			_appendNewLineToStringBuffer(stringbuffer, i);
		}

		protected String escapeString(String toValidate) {
			int length = toValidate.length();
			StringBuilder cleanString = new StringBuilder(length);

			for (int i = 0; i < length; ++i) {
				char currentChar = toValidate.charAt(i);
				switch (currentChar) {
				case '&':
					cleanString.append("&amp;");
					break;
				case '<':
					cleanString.append("&lt;");
					break;
				case '>':
					cleanString.append("&gt;");
					break;
				case '\'':
					cleanString.append("&apos;");
					break;
				case '"':
					cleanString.append("&quot;");
					break;
				default:
					cleanString.append(currentChar);
				}
			}

			return cleanString.toString();
		}

		public static class DictionaryParser
			extends
			DefaultHandler {
			static String PUBLIC_APPLE_COMPUTER_PLIST_1_0 = "-//Apple Computer//DTD PLIST 1.0//EN";

			static String PUBLIC_APPLE_PLIST_1_0 = "-//Apple//DTD PLIST 1.0//EN";
			protected SimpleDateFormat _dateFormat;
			protected Stack<XMLNode> _stack;
			protected Object _plist;
			protected StringBuffer _curChars;

			public DictionaryParser() {
				this._dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
				this._dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
				this._dateFormat.setLenient(true);
			}

			public Object plist() {
				return this._plist;
			}

			public void characters(char[] ch, int start, int length)
				throws SAXException {
				this._curChars.append(ch, start, length);
			}

			public void endDocument()
				throws SAXException {
				if (!(this._stack.empty()))
					if (this._stack.size() == 1) {
						XMLNode lastNode = this._stack.pop();
						if (!(lastNode.tagOpen()))
							this._plist = lastNode.value();
						else
							throw new SAXException("Starting <" + lastNode.type() + "> node was never ended.");
					} else {
						throw new SAXException("A plist may only contain one top-level node.");
					}
			}

			public void endElement(String uri, String localName, String qName)
				throws SAXException {
				XMLNode.Type aType = XMLNode.Type.typeForName(qName);

				if (XMLNode.Type.PLIST.equals(aType)) {
					if (this._stack.size() != 2)
						throw new SAXException("A plist may only contain one top-level node, and all tags must have end tags.");
					XMLNode lastNode = this._stack.pop();
					if (!(lastNode.tagOpen())) {
						this._plist = lastNode.value();
						this._stack.pop();
					} else {
						throw new SAXException("Starting <" + lastNode.type() + "> tag was never ended.");
					}
					return;
				}
				saveCharContent();
				boolean foundOpenTag;
				XMLNode lastNode;
				switch (aType) {
				case ARRAY:
					NSMutableArray<Object> array = new NSMutableArray<Object>();
					foundOpenTag = false;
					while (!(this._stack.isEmpty())) {
						XMLNode currentNode = this._stack.peek();
						if (currentNode.tagOpen()) {
							if (XMLNode.Type.ARRAY.equals(currentNode.type())) {
								foundOpenTag = true;
								currentNode.setValue(array);
								currentNode.setTagOpen(false);
								break;
							}
							throw new SAXException("Ending <" + qName + "> tag does not match starting <" + currentNode.type() + "> tag.");
						}

						if (currentNode.value() != null)
							array.add(0, currentNode.value());
						this._stack.pop();
					}
					if (foundOpenTag)
						return;
					throw new SAXException("No starting <array> tag.");
				case DICT:
				case DICTIONARY:
					NSMutableDictionary<Object, Object> dictionary = new NSMutableDictionary<Object, Object>();
					foundOpenTag = false;
					while (!(this._stack.isEmpty())) {
						XMLNode currentNode = this._stack.peek();
						if (currentNode.tagOpen()) {
							if (XMLNode.Type.DICT.equals(currentNode.type()) || XMLNode.Type.DICTIONARY.equals(currentNode.type())) {
								foundOpenTag = true;
								currentNode.setValue(dictionary);
								currentNode.setTagOpen(false);
								break;
							}
							throw new SAXException("Ending <" + qName + "> tag does not match starting <" + currentNode.type() + "> tag.");
						}

						if (this._stack.size() > 1) {
							Object obj = currentNode.value();
							this._stack.pop();
							currentNode = this._stack.peek();
							if (XMLNode.Type.KEY.equals(currentNode.type())) {
								if ((obj != null) && (currentNode.value() != null))
									dictionary.put(currentNode.value(), obj);
								this._stack.pop();
							} else {
								throw new SAXException("Key must be before the value.");
							}
						} else {
							throw new SAXException("All values in a dictionary must have corresponding keys.");
						}
					}
					if (foundOpenTag)
						return;
					throw new SAXException("No starting <" + qName + "> tag.");
				case STRING:
					lastNode = this._stack.peek();
					if (aType.equals(lastNode.type())) {
						if (lastNode.value() != null)
							return;
						lastNode.setValue(new String(""));
						lastNode.setTagOpen(false);
						return;
					}

					throw new SAXException("Ending <" + qName + "> tag does not match starting <" + lastNode.type() + "> tag.");
				case DATE:
					lastNode = this._stack.peek();
					if (aType.equals(lastNode.type())) {
						if (lastNode.value() != null)
							return;
						lastNode.setValue(new NSTimestamp());
						lastNode.setTagOpen(false);
						return;
					}

					throw new SAXException("Ending <" + qName + "> tag does not match starting <" + lastNode.type() + "> tag.");
				case INTEGER:
					lastNode = this._stack.peek();
					if (aType.equals(lastNode.type())) {
						if (lastNode.value() != null)
							return;
						lastNode.setValue(BigInteger.ZERO);
						lastNode.setTagOpen(false);
						return;
					}

					throw new SAXException("Ending <" + qName + "> tag does not match starting <" + lastNode.type() + "> tag.");
				case REAL:
					lastNode = this._stack.peek();
					if (aType.equals(lastNode.type())) {
						if (lastNode.value() != null)
							return;
						lastNode.setValue(BigDecimal.ZERO);
						lastNode.setTagOpen(false);
						return;
					}

					throw new SAXException("Ending <" + qName + "> tag does not match starting <" + lastNode.type() + "> tag.");
				case FALSE:
					lastNode = this._stack.peek();
					if (aType.equals(lastNode.type())) {
						if (lastNode.value() != null)
							return;
						lastNode.setValue(Boolean.FALSE);
						lastNode.setTagOpen(false);
						return;
					}

					throw new SAXException("Ending <" + qName + "> tag does not match starting <" + lastNode.type() + "> tag.");
				case TRUE:
					lastNode = this._stack.peek();
					if (aType.equals(lastNode.type())) {
						if (lastNode.value() != null)
							return;
						lastNode.setValue(Boolean.TRUE);
						lastNode.setTagOpen(false);
						return;
					}

					throw new SAXException("Ending <" + qName + "> tag does not match starting <" + lastNode.type() + "> tag.");
				case DATA:
					lastNode = this._stack.peek();
					if (aType.equals(lastNode.type())) {
						if (lastNode.value() != null)
							return;
						lastNode.setValue(new NSData());
						lastNode.setTagOpen(false);
						return;
					}

					throw new SAXException("Ending <" + qName + "> tag does not match starting <" + lastNode.type() + "> tag.");
				default:
					break;
				}
			}

			public void error(SAXParseException exception)
				throws SAXException {
				NSLog.err.appendln("Parse error : " + exception);
			}

			public void fatalError(SAXParseException exception)
				throws SAXException {
				NSLog.err.appendln("Parse fatal error : " + exception);
				throw exception;
			}

			public void ignorableWhitespace(char[] ch, int start, int length)
				throws SAXException {
			}

			public void processingInstruction(String target, String data)
				throws SAXException {
			}

			public InputSource resolveEntity(String publicId, String systemId)
				throws SAXException {
				InputSource inputsource = null;
				if ((PUBLIC_APPLE_PLIST_1_0.equals(publicId)) || (PUBLIC_APPLE_COMPUTER_PLIST_1_0.equals(publicId)))
					inputsource = new InputSource(new StringReader(""));
				return inputsource;
			}

			public void startElement(String uri, String localName, String qName, Attributes attributes) {
				XMLNode.Type aType = XMLNode.Type.typeForName(qName);
				switch (aType) {
				case TRUE:
					this._stack.push(new XMLNode(aType, Boolean.TRUE, false));
					break;
				case FALSE:
					this._stack.push(new XMLNode(aType, Boolean.FALSE, false));
					break;
				default:
					this._stack.push(new XMLNode(aType));
				}

				this._curChars = new StringBuffer();
			}

			public void startDocument()
				throws SAXException {
				this._stack = new Stack<XMLNode>();
				this._plist = null;
				this._curChars = new StringBuffer();
			}

			private void saveCharContent()
				throws SAXException {
				if (this._curChars.length() == 0)
					return;
				XMLNode lastNode = this._stack.peek();
				if (lastNode.tagOpen())
					switch (lastNode.type()) {
					case UNKNOWN:
						lastNode.setTagOpen(false);
						break;
					case KEY:
					case STRING:
						lastNode.setValue(unescapeString(this._curChars.toString()));
						lastNode.setTagOpen(false);
						break;
					case DATE:
						try {
							lastNode.setValue(new NSTimestamp(this._dateFormat.parse(this._curChars.toString())));
						} catch (Exception exception) {
							throw new SAXException("Unable to convert value <" + this._curChars.toString() + "> to timestamp.");
						}
						lastNode.setTagOpen(false);
						break;
					case INTEGER:
						try {
							lastNode.setValue(new BigInteger(this._curChars.toString()));
						} catch (Exception exception) {
							throw new SAXException("Unable to convert value <" + this._curChars.toString() + "> to integer.");
						}
						lastNode.setTagOpen(false);
						break;
					case REAL:
						try {
							lastNode.setValue(new BigDecimal(this._curChars.toString()));
						} catch (Exception exception) {
							throw new SAXException("Unable to convert value <" + this._curChars.toString() + "> to float.");
						}
						lastNode.setTagOpen(false);
						break;
					case BOOLEAN:
						lastNode.setValue(new Boolean(this._curChars.toString()));
						lastNode.setTagOpen(false);
						break;
					case TRUE:
						lastNode.setValue(Boolean.TRUE);
						lastNode.setTagOpen(false);
						break;
					case FALSE:
						lastNode.setValue(Boolean.FALSE);
						lastNode.setTagOpen(false);
						break;
					case DATA:
						try {
							StringBuffer stringbuffer = new StringBuffer(this._curChars.length());
							for (int i = 0; i < this._curChars.length(); ++i) {
								if (!(Character.isWhitespace(this._curChars.charAt(i))))
									stringbuffer.append(this._curChars.charAt(i));
							}
							byte[] abyte0 = stringbuffer.toString().getBytes("US-ASCII");
							byte[] abyte64 = _NSBase64.decode(abyte0);
							if ((abyte64 != null) && (abyte64.length > 0))
								lastNode.setValue(new NSData(abyte64));
							else {
								lastNode.setValue(new NSData());
							}
							lastNode.setTagOpen(false);
						} catch (UnsupportedEncodingException unsupportedencodingexception) {
							throw new SAXException(unsupportedencodingexception.getMessage());
						}
						lastNode.setTagOpen(false);
					default:
						break;
					}
			}

			public void warning(SAXParseException exception)
				throws SAXException {
				NSLog.out.appendln("Parse warning : " + exception);
			}

			protected String unescapeString(String toRestore) {
				String result = toRestore.replace("&amp;", "&");
				result = toRestore.replace("&lt;", "<");
				result = toRestore.replace("&gt;", ">");
				result = toRestore.replace("&apos;", "'");
				result = toRestore.replace("&quot;", "\"");
				result = toRestore.replace("&lt;", "<");
				return result;
			}

			public static class XMLNode {
				protected Type _type;
				protected Object _value;
				protected boolean _tag_open;

				public XMLNode(
					Type type,
					Object value,
					boolean tag_open) {
					this._type = type;
					this._value = value;
					this._tag_open = tag_open;
				}

				public XMLNode(
					Type type,
					Object value) {
					this(type, value, true);
				}

				public XMLNode(
					Type type) {
					this(type, null, true);
				}

				public XMLNode(
					String type,
					Object value,
					boolean tag_open) {
					this(Type.typeForName(type), value, tag_open);
				}

				public XMLNode(
					String type,
					Object value) {
					this(type, value, true);
				}

				public XMLNode(
					String type) {
					this(type, null, true);
				}

				public Type type() {
					return this._type;
				}

				public Object value() {
					return this._value;
				}

				public void setValue(Object value) {
					this._value = value;
				}

				public boolean tagOpen() {
					return this._tag_open;
				}

				public void setTagOpen(boolean value) {
					this._tag_open = value;
				}

				public String toString() {
					return "type = " + this._type + "; object = " + this._value + "; open = " + this._tag_open;
				}

				public static enum Type {
						PLIST,
						STRING,
						KEY,
						DATA,
						DATE,
						INTEGER,
						REAL,
						BOOLEAN,
						ARRAY,
						DICTIONARY,
						DICT,
						TRUE,
						FALSE,
						UNKNOWN;

					public static Type typeForName(String qName) {
						if (qName != null) {
							for (Type aType : Type.values()) {
								if (aType.toString().equalsIgnoreCase(qName)) {
									return aType;
								}
							}
						}
						return UNKNOWN;
					}

					public String openTag() {
						return "<" + name().toLowerCase() + ">";
					}

					public String closeTag() {
						return "</" + name().toLowerCase() + ">";
					}

				}
			}
		}
	}

	public static abstract class _PListParser {
		private boolean _indents;

		public _PListParser(
			boolean indents) {
			this._indents = indents;
		}

		protected void _appendIndentationToStringBuffer(StringBuffer buffer, int i) {
			if (this._indents)
				for (int j = 0; j < i; ++j)
					buffer.append('\t');
		}

		protected void _appendNewLineToStringBuffer(StringBuffer buffer, int i) {
			if (this._indents)
				buffer.append('\n');
		}

		public abstract Object parseStringIntoPlist(String paramString);
	}
}