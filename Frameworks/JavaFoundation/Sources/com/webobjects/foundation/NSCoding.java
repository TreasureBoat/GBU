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

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

public interface NSCoding {
	public static final Class<?> _CLASS = _NSUtilitiesExtra._classWithFullySpecifiedNamePrime("com.webobjects.foundation.NSCoding");

	public abstract Class<?> classForCoder();

	public abstract void encodeWithCoder(NSCoder paramNSCoder);

	public static class _BigDecimalSupport
		extends
		NSCoding._BigIntegerSupport {
		private static final int _MaxMantissaSize = 16;
		private static final int _MaxByteDifference = 127;
		private static final double _LnTen = Math.log(10.0D);

		private double _logBaseTen(double value) {
			return (Math.log(value) / _LnTen);
		}

		public void encodeWithCoder(Object receiver, NSCoder coder) {
			int scale = ((BigDecimal) receiver).scale();
			BigInteger mantissa = ((BigDecimal) receiver).movePointRight(scale).toBigInteger();

			int byteLength = mantissa.bitLength();

			while (++byteLength / 8 > _MaxMantissaSize) {
				int byteDifference = byteLength - _MaxMantissaSize;

				if (byteDifference > _MaxByteDifference) {
					byteDifference = _MaxByteDifference;
				}

				int exponentDelta = (int) _logBaseTen(Math.pow(2.0D, 8 * byteDifference));
				BigInteger divisor = new BigInteger("10").pow(exponentDelta);

				scale -= exponentDelta;
				mantissa = mantissa.divide(divisor);
			}

			coder.encodeInt(-scale);
			_encodeBigInteger(coder, mantissa);
		}

		public Object decodeObject(NSCoder coder) {
			int exponent = coder.decodeInt();
			BigInteger mantissa = _decodeBigInteger(coder, (exponent > 0) ? exponent : 0);

			return new BigDecimal(mantissa, (exponent > 0) ? 0 : -exponent);
		}
	}

	public static class _BigIntegerSupport
		extends
		NSCoding.Support {
		private static final int _MaxShortArrayLength = 8;

		public void encodeWithCoder(Object receiver, NSCoder coder) {
			coder.encodeInt(0);
			_encodeBigInteger(coder, (BigInteger) receiver);
		}

		public Object decodeObject(NSCoder coder) {
			int exponent = coder.decodeInt();
			return _decodeBigInteger(coder, exponent);
		}

		private static void _copyIntToByteArray(int anInt, byte[] bytes, int offset) {
			bytes[offset] = (byte) (anInt >>> 24 & 0xFF);
			bytes[(offset + 1)] = (byte) (anInt >>> 16 & 0xFF);
			bytes[(offset + 2)] = (byte) (anInt >>> 8 & 0xFF);
			bytes[(offset + 3)] = (byte) (anInt & 0xFF);
		}

		private static void _copyShortToByteArray(short aShort, byte[] bytes, int offset) {
			bytes[offset] = (byte) (aShort >>> 8);
			bytes[(offset + 1)] = (byte) aShort;
		}

		private static short _shortFromByteArray(byte[] bytes, int offset) {
			short high = (offset >= 0) ? (short) bytes[offset] : 0;
			short low = (short) bytes[(offset + 1)];
			if (low < 0) {
				low = (short) (low + 256);
			}
			return (short) ((high << 8) + low);
		}

		protected static void _encodeBigInteger(NSCoder coder, BigInteger bigInteger) {
			BigInteger integer = bigInteger;
			boolean isNegative = integer.signum() < 0;
			if (isNegative) {
				integer = integer.negate();
			}

			byte[] bytes = integer.toByteArray();
			int length = bytes.length;
			short shortCount = (short) (length / 2 + length % 2);

			coder.encodeShort(shortCount);
			coder.encodeBoolean(isNegative);
			coder.encodeBoolean(false);
			coder.encodeInt(8);

			for (int i = 0; i < _MaxShortArrayLength; ++i)
				if (i < shortCount)
					coder.encodeShort(_shortFromByteArray(bytes, length - (2 * (i + 1))));
				else
					coder.encodeShort((short) 0);
		}

		protected static BigInteger _decodeBigInteger(NSCoder coder, int exponent) {
			short length = coder.decodeShort();
			boolean isNegative = coder.decodeBoolean();
			coder.decodeBoolean();
			int shortCount = coder.decodeInt();
			byte[] bytes = new byte[length * 2];
			int i;
			for (i = 0; i < shortCount; ++i) {
				short part = coder.decodeShort();
				if (i < length) {
					_copyShortToByteArray(part, bytes, (length - (i + 1)) * 2);
				}
			}

			BigInteger result = new BigInteger((isNegative) ? -1 : 1, bytes);

			if (exponent != 0) {
				bytes = new byte[4];
				int powerOfTen = 10;

				for (i = 1; i < exponent; ++i) {
					powerOfTen *= 10;
				}

				_copyIntToByteArray(powerOfTen, bytes, 0);
				BigInteger factor = new BigInteger(bytes);

				result = (exponent > 0) ? result.multiply(factor) : result.divide(factor);
			}

			return result;
		}
	}

	public static class _DoubleSupport
		extends
		NSCoding._NumberSupport {
		public void encodeWithCoder(Object receiver, NSCoder coder) {
			_encodeUTF8("d", coder);
			coder.encodeDouble(((Double) receiver).doubleValue());
		}
	}

	public static class _FloatSupport
		extends
		NSCoding._NumberSupport {
		public void encodeWithCoder(Object receiver, NSCoder coder) {
			_encodeUTF8("f", coder);
			coder.encodeFloat(((Float) receiver).floatValue());
		}
	}

	public static class _LongSupport
		extends
		NSCoding._NumberSupport {
		public void encodeWithCoder(Object receiver, NSCoder coder) {
			_encodeUTF8("l", coder);
			coder.encodeLong(((Long) receiver).longValue());
		}
	}

	public static class _IntegerSupport
		extends
		NSCoding._NumberSupport {
		public void encodeWithCoder(Object receiver, NSCoder coder) {
			_encodeUTF8("i", coder);
			coder.encodeInt(((Integer) receiver).intValue());
		}
	}

	public static class _ShortSupport
		extends
		NSCoding._NumberSupport {
		public void encodeWithCoder(Object receiver, NSCoder coder) {
			_encodeUTF8("s", coder);
			coder.encodeShort(((Short) receiver).shortValue());
		}
	}

	public static class _ByteSupport
		extends
		NSCoding._NumberSupport {
		public void encodeWithCoder(Object receiver, NSCoder coder) {
			_encodeUTF8("c", coder);
			coder.encodeByte(((Byte) receiver).byteValue());
		}
	}

	public static class _NumberSupport
		extends
		NSCoding.Support {
		public Class<?> classForCoder(Object receiver) {
			return _NSUtilities._NumberClass;
		}

		public void encodeWithCoder(Object receiver, NSCoder coder) {
		}

		public Object decodeObject(NSCoder coder) {
			String type = _decodeUTF8(coder);

			switch (type.charAt(0)) {
			case 'c':
				return new Byte(coder.decodeByte());
			case 's':
				return new Short(coder.decodeShort());
			case 'i':
				return new Integer(coder.decodeInt());
			case 'l':
				return new Long(coder.decodeLong());
			case 'f':
				return new Float(coder.decodeFloat());
			case 'd':
				return new Double(coder.decodeDouble());
			case 'e':
			case 'g':
			case 'h':
			case 'j':
			case 'k':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			}
			throw new IllegalStateException("decodeObject: unsupported number type: " + type);
		}
	}

	public static class _CharacterSupport
		extends
		NSCoding.Support {
		public void encodeWithCoder(Object receiver, NSCoder coder) {
			coder.encodeChar(((Character) receiver).charValue());
		}

		public Object decodeObject(NSCoder coder) {
			return new Character(coder.decodeChar());
		}
	}

	public static class _DateSupport
		extends
		NSCoding.Support {
		public void encodeWithCoder(Object receiver, NSCoder coder) {
			long milliseconds = ((Date) receiver).getTime();
			double seconds = milliseconds / 1000L;

			coder.encodeDouble(seconds);
		}

		public Object decodeObject(NSCoder coder) {
			double seconds = coder.decodeDouble();
			long milliseconds = (long) seconds * 1000L;

			return new Date(milliseconds);
		}
	}

	public static class _StringSupport
		extends
		NSCoding.Support {
		public void encodeWithCoder(Object receiver, NSCoder coder) {
			_encodeUTF8((String) receiver, coder);
		}

		public Object decodeObject(NSCoder coder) {
			return _decodeUTF8(coder);
		}
	}

	public static class _BooleanSupport
		extends
		NSCoding.Support {
		public void encodeWithCoder(Object receiver, NSCoder coder) {
			coder.encodeBoolean(((Boolean) receiver).booleanValue());
		}

		public Object decodeObject(NSCoder coder) {
			return ((coder.decodeBoolean()) ? Boolean.TRUE : Boolean.FALSE);
		}
	}

	public static abstract class Support {
		private static NSMutableDictionary<Class<?>, Support> _supportByClass = new NSMutableDictionary<Class<?>, Support>(16);
		private static final String _UTF8StringEncoding = "UTF8";

		public static Support supportForClass(Class<?> aClass) {
			Support result = null;
			Class<?> currentClass = aClass;

			while ((result == null) && (currentClass != null)) {
				result = (Support) _supportByClass.objectForKey(currentClass);

				if (result == null)
					currentClass = currentClass.getSuperclass();
				if (currentClass != aClass)
					;
				_supportByClass.setObjectForKey(result, aClass);
			}

			return result;
		}

		public static void setSupportForClass(Support support, Class<?> aClass) {
			_supportByClass.setObjectForKey(support, aClass);
		}

		public Class<?> classForCoder(Object receiver) {
			return receiver.getClass();
		}

		public abstract void encodeWithCoder(Object paramObject, NSCoder paramNSCoder);

		public abstract Object decodeObject(NSCoder paramNSCoder);

		protected static void _encodeUTF8(String string, NSCoder coder) {
			byte[] bytes = null;
			try {
				bytes = string.getBytes(_UTF8StringEncoding);
			} catch (UnsupportedEncodingException exception) {
				throw NSForwardException._runtimeExceptionForThrowable(exception);
			}

			coder.encodeBytes(bytes);
		}

		protected static String _decodeUTF8(NSCoder coder) {
			byte[] bytes = coder.decodeBytes();
			String result;
			try {
				result = new String(bytes, _UTF8StringEncoding);
			} catch (UnsupportedEncodingException exception) {
				throw NSForwardException._runtimeExceptionForThrowable(exception);
			}

			return result;
		}

		static {
			setSupportForClass(new NSCoding._StringSupport(), _NSUtilities._StringClass);
			setSupportForClass(new NSCoding._BooleanSupport(), _NSUtilities._BooleanClass);
			setSupportForClass(new NSCoding._NumberSupport(), _NSUtilities._NumberClass);
			setSupportForClass(new NSCoding._ByteSupport(), _NSUtilities._ByteClass);
			setSupportForClass(new NSCoding._ShortSupport(), _NSUtilities._ShortClass);
			setSupportForClass(new NSCoding._IntegerSupport(), _NSUtilities._IntegerClass);
			setSupportForClass(new NSCoding._LongSupport(), _NSUtilities._LongClass);
			setSupportForClass(new NSCoding._FloatSupport(), _NSUtilities._FloatClass);
			setSupportForClass(new NSCoding._DoubleSupport(), _NSUtilities._DoubleClass);
			setSupportForClass(new NSCoding._BigIntegerSupport(), _NSUtilities._BigIntegerClass);
			setSupportForClass(new NSCoding._BigDecimalSupport(), _NSUtilities._BigDecimalClass);
			setSupportForClass(new NSCoding._DateSupport(), _NSUtilities._DateClass);
			setSupportForClass(new NSCoding._CharacterSupport(), _NSUtilities._CharacterClass);
		}
	}
}