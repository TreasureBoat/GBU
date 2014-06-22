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

import java.io.InputStream;
import java.io.OutputStream;

public abstract class NSCoder {
	public static final Class<?> _CLASS = _NSUtilities._classWithFullySpecifiedName("com.webobjects.foundation.NSCoder");

	public abstract void encodeBoolean(boolean paramBoolean);

	public abstract void encodeByte(byte paramByte);

	public abstract void encodeBytes(byte[] paramArrayOfByte);

	public abstract void encodeChar(char paramChar);

	public abstract void encodeShort(short paramShort);

	public abstract void encodeInt(int paramInt);

	public abstract void encodeLong(long paramLong);

	public abstract void encodeFloat(float paramFloat);

	public abstract void encodeDouble(double paramDouble);

	public abstract void encodeObject(Object paramObject);

	public abstract void encodeClass(Class<?> paramClass);

	public abstract void encodeObjects(Object[] paramArrayOfObject);

	public abstract boolean decodeBoolean();

	public abstract byte decodeByte();

	public abstract byte[] decodeBytes();

	public abstract char decodeChar();

	public abstract short decodeShort();

	public abstract int decodeInt();

	public abstract long decodeLong();

	public abstract float decodeFloat();

	public abstract double decodeDouble();

	public abstract Object decodeObject();

	public abstract Class<?> decodeClass();

	public abstract Object[] decodeObjects();

	public void prepareForWriting(OutputStream stream) {
	}

	public void prepareForReading(InputStream stream) {
	}

	public void finishCoding() {
	}
}