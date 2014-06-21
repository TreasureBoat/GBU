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

public final class _NSBase64 {
	private static final int BASELENGTH = 255;
	private static final int LOOKUPLENGTH = 64;
	private static final int TWENTYFOURBITGROUP = 24;
	private static final int EIGHTBIT = 8;
	private static final int SIXTEENBIT = 16;
	private static final int FOURBYTE = 4;
	@SuppressWarnings("unused")
	private static final int SIGN = -128;
	private static final byte PAD = 61;
	private static byte[] base64Alphabet = new byte[BASELENGTH];

	private static byte[] lookUpBase64Alphabet = new byte[LOOKUPLENGTH];

	protected static boolean isWhiteSpace(byte octect) {
		return ((octect == 32) || (octect == 13) || (octect == 10) || (octect == 9));
	}

	protected static boolean isPad(byte octect) {
		return (octect == PAD);
	}

	protected static boolean isData(byte octect) {
		return (base64Alphabet[octect] != -1);
	}

	protected static boolean isBase64(byte octect) {
		return ((isWhiteSpace(octect)) || (isPad(octect)) || (isData(octect)));
	}

	public static byte[] encode(byte[] binaryData, int off, int len) {
		if (binaryData == null) {
			return null;
		}
		int endoff = off + len;
		if ((off < 0) || (len < 0) || (endoff > binaryData.length) || (endoff < 0)) {
			throw new IndexOutOfBoundsException();
		}

		int lengthDataBits = len * EIGHTBIT;
		int fewerThan24bits = lengthDataBits % TWENTYFOURBITGROUP;
		int numberTriplets = lengthDataBits / TWENTYFOURBITGROUP;
		byte[] encodedData = null;

		if (fewerThan24bits != 0) {
			encodedData = new byte[(numberTriplets + 1) * FOURBYTE];
		} else {
			encodedData = new byte[numberTriplets * FOURBYTE];
		}
		byte k = 0;
		byte l = 0;
		byte b1 = 0;
		byte b2 = 0;
		byte b3 = 0;

		int encodedIndex = 0;
		int dataIndex = 0;
		int i = 0;

		for (i = off; i < numberTriplets; ++i) {
			dataIndex = i * 3;
			b1 = binaryData[dataIndex];
			b2 = binaryData[(dataIndex + 1)];
			b3 = binaryData[(dataIndex + 2)];

			l = (byte) (b2 & 0xF);
			k = (byte) (b1 & 0x3);

			encodedIndex = i * FOURBYTE;
			byte val1 = ((b1 & 0xFFFFFF80) == 0) ? (byte) (b1 >> 2) : (byte) (b1 >> 2 ^ 0xC0);

			byte val2 = ((b2 & 0xFFFFFF80) == 0) ? (byte) (b2 >> FOURBYTE) : (byte) (b2 >> FOURBYTE ^ 0xF0);
			byte val3 = ((b3 & 0xFFFFFF80) == 0) ? (byte) (b3 >> 6) : (byte) (b3 >> 6 ^ 0xFC);

			encodedData[encodedIndex] = lookUpBase64Alphabet[val1];

			encodedData[(encodedIndex + 1)] = lookUpBase64Alphabet[(val2 | k << FOURBYTE)];
			encodedData[(encodedIndex + 2)] = lookUpBase64Alphabet[(l << 2 | val3)];
			encodedData[(encodedIndex + 3)] = lookUpBase64Alphabet[(b3 & 0x3F)];
		}

		dataIndex = i * 3;
		encodedIndex = i * FOURBYTE;
		if (fewerThan24bits == EIGHTBIT) {
			b1 = binaryData[dataIndex];
			k = (byte) (b1 & 0x3);

			byte val1 = ((b1 & 0xFFFFFF80) == 0) ? (byte) (b1 >> 2) : (byte) (b1 >> 2 ^ 0xC0);
			encodedData[encodedIndex] = lookUpBase64Alphabet[val1];
			encodedData[(encodedIndex + 1)] = lookUpBase64Alphabet[(k << FOURBYTE)];
			encodedData[(encodedIndex + 2)] = PAD;
			encodedData[(encodedIndex + 3)] = PAD;
		} else if (fewerThan24bits == SIXTEENBIT) {
			b1 = binaryData[dataIndex];
			b2 = binaryData[(dataIndex + 1)];
			l = (byte) (b2 & 0xF);
			k = (byte) (b1 & 0x3);

			byte val1 = ((b1 & 0xFFFFFF80) == 0) ? (byte) (b1 >> 2) : (byte) (b1 >> 2 ^ 0xC0);
			byte val2 = ((b2 & 0xFFFFFF80) == 0) ? (byte) (b2 >> FOURBYTE) : (byte) (b2 >> FOURBYTE ^ 0xF0);

			encodedData[encodedIndex] = lookUpBase64Alphabet[val1];
			encodedData[(encodedIndex + 1)] = lookUpBase64Alphabet[(val2 | k << FOURBYTE)];
			encodedData[(encodedIndex + 2)] = lookUpBase64Alphabet[(l << 2)];
			encodedData[(encodedIndex + 3)] = PAD;
		}

		return encodedData;
	}

	public static byte[] encode(byte[] binaryData) {
		return encode(binaryData, 0, binaryData.length);
	}

	public static byte[] decode(byte[] base64Data) {
		if (base64Data == null) {
			return null;
		}
		if (base64Data.length % FOURBYTE != 0) {
			return null;
		}

		int numberQuadruple = base64Data.length / FOURBYTE;

		if (numberQuadruple == 0) {
			return new byte[0];
		}
		byte[] decodedData = null;
		byte b1 = 0;
		byte b2 = 0;
		byte b3 = 0;
		byte b4 = 0;
		byte d1 = 0;
		byte d2 = 0;
		byte d3 = 0;
		byte d4 = 0;

		int i = 0;
		int encodedIndex = 0;
		int dataIndex = 0;
		decodedData = new byte[numberQuadruple * 3];

		for (; i < numberQuadruple - 1; ++i) {
			if ((!(isData(d1 = base64Data[(dataIndex++)]))) || (!(isData(d2 = base64Data[(dataIndex++)]))) || (!(isData(d3 = base64Data[(dataIndex++)]))) || (!(isData(d4 = base64Data[(dataIndex++)])))) {
				return null;
			}
			b1 = base64Alphabet[d1];
			b2 = base64Alphabet[d2];
			b3 = base64Alphabet[d3];
			b4 = base64Alphabet[d4];

			decodedData[(encodedIndex++)] = (byte) (b1 << 2 | b2 >> FOURBYTE);
			decodedData[(encodedIndex++)] = (byte) ((b2 & 0xF) << FOURBYTE | b3 >> 2 & 0xF);
			decodedData[(encodedIndex++)] = (byte) (b3 << 6 | b4);
		}

		if ((!(isData(d1 = base64Data[(dataIndex++)]))) || (!(isData(d2 = base64Data[(dataIndex++)])))) {
			return null;
		}

		b1 = base64Alphabet[d1];
		b2 = base64Alphabet[d2];

		d3 = base64Data[(dataIndex++)];
		d4 = base64Data[(dataIndex++)];
		if ((!(isData(d3))) || (!(isData(d4)))) {
			if ((isPad(d3)) && (isPad(d4))) {
				if ((b2 & 0xF) != 0)
					return null;
				byte[] tmp = new byte[i * 3 + 1];
				System.arraycopy(decodedData, 0, tmp, 0, i * 3);
				tmp[encodedIndex] = (byte) (b1 << 2 | b2 >> FOURBYTE);
				return tmp;
			}
			if ((!(isPad(d3))) && (isPad(d4))) {
				b3 = base64Alphabet[d3];
				if ((b3 & 0x3) != 0)
					return null;
				byte[] tmp = new byte[i * 3 + 2];
				System.arraycopy(decodedData, 0, tmp, 0, i * 3);
				tmp[(encodedIndex++)] = (byte) (b1 << 2 | b2 >> FOURBYTE);
				tmp[encodedIndex] = (byte) ((b2 & 0xF) << FOURBYTE | b3 >> 2 & 0xF);
				return tmp;
			}
			return null;
		}

		b3 = base64Alphabet[d3];
		b4 = base64Alphabet[d4];
		decodedData[(encodedIndex++)] = (byte) (b1 << 2 | b2 >> FOURBYTE);
		decodedData[(encodedIndex++)] = (byte) ((b2 & 0xF) << FOURBYTE | b3 >> 2 & 0xF);
		decodedData[(encodedIndex++)] = (byte) (b3 << 6 | b4);

		return decodedData;
	}

	public static String decode(String base64Data) {
		String retVal = null;

		if (base64Data == null) {
			return null;
		}
		byte[] decoded = null;
		try {
			decoded = decode(base64Data.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
		} finally {
			if (decoded != null) {
				retVal = new String(decoded);
			}
		}

		return retVal;
	}

	static {
		int i;
		for (i = 0; i < 255; ++i) {
			base64Alphabet[i] = -1;
		}
		for (i = 90; i >= 65; --i) {
			base64Alphabet[i] = (byte) (i - 65);
		}
		for (i = 122; i >= 97; --i) {
			base64Alphabet[i] = (byte) (i - 97 + 26);
		}

		for (i = 57; i >= 48; --i) {
			base64Alphabet[i] = (byte) (i - 48 + 52);
		}

		base64Alphabet[43] = 62;
		base64Alphabet[47] = 63;

		for (i = 0; i <= 25; ++i) {
			lookUpBase64Alphabet[i] = (byte) (65 + i);
		}
		i = 26;
		for (int j = 0; i <= 51; ++j) {
			lookUpBase64Alphabet[i] = (byte) (97 + j);

			++i;
		}

		i = 52;
		for (int j = 0; i <= PAD; ++j) {
			lookUpBase64Alphabet[i] = (byte) (48 + j);

			++i;
		}
		lookUpBase64Alphabet[62] = 43;
		lookUpBase64Alphabet[63] = 47;
	}
}