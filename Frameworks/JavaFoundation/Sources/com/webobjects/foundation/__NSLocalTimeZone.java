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
import java.util.Locale;

final class __NSLocalTimeZone
	extends
	NSTimeZone {
	static final long serialVersionUID = -7337583402474807484L;
	public static final Class<?> _CLASS = _NSUtilitiesExtra._classWithFullySpecifiedNamePrime("com.webobjects.foundation.__NSLocalTimeZone");
	private static final String __NSLOCALTIMEZONE_TOSTRING_1 = "Local Time Zone [";
	private static final String __NSLOCALTIMEZONE_TOSTRING_2 = "]";

	public static Object decodeObject(NSCoder aDecoder) {
		return NSTimeZone.localTimeZone();
	}

	public String abbreviationForTimestamp(NSTimestamp aTimestamp) {
		return NSTimeZone.defaultTimeZone().abbreviationForTimestamp(aTimestamp);
	}

	public Class<?> classForCoder() {
		return _CLASS;
	}

	public Object clone() {
		return this;
	}

	public NSData data() {
		return NSTimeZone.defaultTimeZone().data();
	}

	public void encodeWithCoder(NSCoder encoder) {
	}

	public boolean equals(Object anObject) {
		return NSTimeZone.defaultTimeZone().equals(anObject);
	}

	public String getDisplayName(boolean inDaylightSavingTime, int aTZStyle, Locale aLocale) {
		return NSTimeZone.defaultTimeZone().getDisplayName(inDaylightSavingTime, aTZStyle, aLocale);
	}

	public String getID() {
		return NSTimeZone.defaultTimeZone().name();
	}

	public int getRawOffset() {
		return NSTimeZone.defaultTimeZone().getRawOffset();
	}

	public synchronized int hashCode() {
		return NSTimeZone.defaultTimeZone().hashCode();
	}

	public boolean isDaylightSavingTimeForTimestamp(NSTimestamp aTimestamp) {
		return NSTimeZone.defaultTimeZone().isDaylightSavingTimeForTimestamp(aTimestamp);
	}

	protected Object readResolve()
		throws ObjectStreamException {
		return NSTimeZone.localTimeZone();
	}

	int secondsFromGMTForOffsetInSeconds(long offset) {
		return NSTimeZone.defaultTimeZone().secondsFromGMTForOffsetInSeconds(offset);
	}

	public String toString() {
		return __NSLOCALTIMEZONE_TOSTRING_1 + NSTimeZone.defaultTimeZone().toString() + __NSLOCALTIMEZONE_TOSTRING_2;
	}

	public boolean useDaylightTime() {
		return NSTimeZone.defaultTimeZone().useDaylightTime();
	}
}