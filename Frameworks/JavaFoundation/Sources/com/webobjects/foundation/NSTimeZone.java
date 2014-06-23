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
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;
import java.io.ObjectStreamField;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.AccessController;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import sun.security.action.GetPropertyAction;

public class NSTimeZone
	extends
	TimeZone
	implements
	Cloneable,
	Serializable,
	NSCoding {

	@Deprecated
	public static final String SystemTimeZoneDidChangeNotification = "NSSystemTimeZoneDidChangeNotification";
	public static final Class<?> _CLASS = _NSUtilitiesExtra._classWithFullySpecifiedNamePrime("com.webobjects.foundation.NSTimeZone");
	private static final String __ABBR_TABLE_NAME = "com/webobjects/foundation/TimeZoneInfo/Abbreviations.table";
	private static final int __ABBR_TABLE_SIZE_IN_BYTES = 9932;
	private static final String __ALIAS_TABLE_NAME = "com/webobjects/foundation/TimeZoneInfo/Aliases.table";
	private static final int __ALIAS_TABLE_SIZE_IN_BYTES = 3092;
	private static final String __GENERIC_TZ_NAME_STEM = "Etc/GMT";
	private static final int __GENERIC_TZ_NAME_STEM_LENGTH = __GENERIC_TZ_NAME_STEM.length();
	private static final String __GMT = "GMT";
	private static final int __GMT_LENGTH = __GMT.length();
	private static final String __MINUS = "-";
	private static final String __PLUS = "+";
	private static final String __UTF8 = "UTF8";
	private static final String __ZONE_ARCHIVE_NAME = "com/webobjects/foundation/TimeZoneInfo/zoneinfo.zip";
	static final long serialVersionUID = -3697199310879546788L;
	private static final String SerializationNameFieldKey = "name";
	private static final String SerializationDataFieldKey = "timeZoneData";
	private static NSDictionary<String, String> __abbreviations;
	private static NSDictionary<String, String> __aliases;
	private static NSTimeZone __defaultTimeZone;
	private static NSTimeZone __gmt;
	private static final NSNumberFormatter __hourFormatter = new NSNumberFormatter("#0;#0");

	private static final NSNumberFormatter __gmtHourFormatter = new NSNumberFormatter("00;00");
	private static NSSet<String> __knownTimeZoneNames;
	private static final NSMutableDictionary<String, NSTimeZone> __knownTimeZones = new NSMutableDictionary<String, NSTimeZone>();

	private static final __NSLocalTimeZone __localTimeZone = new __NSLocalTimeZone();

	private static final NSNumberFormatter __gmtMinuteFormatter = new NSNumberFormatter(":00;:00");

	private static final NSMutableDictionary<String, NSData> __namesDataTable = new NSMutableDictionary<String, NSData>(200);
	private static NSTimeZone __systemTimeZone;
	private static final __NSTZPeriodComparator __tzPeriodComparator = new __NSTZPeriodComparator();

	protected NSData _data = null;

	protected transient int _hashCode = 0;

	protected transient boolean _initialized = false;

	protected String _name = null;

	protected transient int _rawOffset = 0;

	protected transient NSMutableArray<__NSTZPeriod> _timeZonePeriods = null;

	protected transient int _timeZonePeriodsCount = 0;

	protected transient boolean _useDaylightTime = false;
	private static final ObjectStreamField[] serialPersistentFields;

	public NSTimeZone() {
	}

	protected NSTimeZone(
		String aName,
		NSData aData) {
		if ((aName == null) || (aData == null)) {
			throw new IllegalArgumentException("Both parameters must be non-null");
		}
		this._data = aData;
		this._name = aName;
	}

	private static int __bSearchTZPeriods(NSMutableArray<__NSTZPeriod> tzPeriods, int count, double time) {
		if (count < 4) {
			switch (count) {
			case 3:
				if (((__NSTZPeriod) tzPeriods.objectAtIndex(2))._startTime <= time)
					return 3;
			case 2:
				if (((__NSTZPeriod) tzPeriods.objectAtIndex(1))._startTime <= time)
					return 2;
			case 1:
				if (((__NSTZPeriod) tzPeriods.objectAtIndex(0))._startTime <= time)
					return 1;
			}
			return 0;
		}
		if (((__NSTZPeriod) tzPeriods.objectAtIndex(count - 1))._startTime <= time)
			return count;
		if (time <= ((__NSTZPeriod) tzPeriods.objectAtIndex(0))._startTime)
			return 0;
		int lg = __log2(count);
		int idx = (((__NSTZPeriod) tzPeriods.objectAtIndex(-1 + (1 << lg)))._startTime <= time) ? count - (1 << lg) : -1;
		switch (--lg) {
		case 30:
			if (((__NSTZPeriod) tzPeriods.objectAtIndex(idx + 1073741824))._startTime > time)
				break;
			idx += 1073741824;
		case 29:
			if (((__NSTZPeriod) tzPeriods.objectAtIndex(idx + 536870912))._startTime > time)
				break;
			idx += 536870912;
		case 28:
			if (((__NSTZPeriod) tzPeriods.objectAtIndex(idx + 268435456))._startTime > time)
				break;
			idx += 268435456;
		case 27:
			if (((__NSTZPeriod) tzPeriods.objectAtIndex(idx + 134217728))._startTime > time)
				break;
			idx += 134217728;
		case 26:
			if (((__NSTZPeriod) tzPeriods.objectAtIndex(idx + 67108864))._startTime > time)
				break;
			idx += 67108864;
		case 25:
			if (((__NSTZPeriod) tzPeriods.objectAtIndex(idx + 33554432))._startTime > time)
				break;
			idx += 33554432;
		case 24:
			if (((__NSTZPeriod) tzPeriods.objectAtIndex(idx + 16777216))._startTime > time)
				break;
			idx += 16777216;
		case 23:
			if (((__NSTZPeriod) tzPeriods.objectAtIndex(idx + 8388608))._startTime > time)
				break;
			idx += 8388608;
		case 22:
			if (((__NSTZPeriod) tzPeriods.objectAtIndex(idx + 4194304))._startTime > time)
				break;
			idx += 4194304;
		case 21:
			if (((__NSTZPeriod) tzPeriods.objectAtIndex(idx + 2097152))._startTime > time)
				break;
			idx += 2097152;
		case 20:
			if (((__NSTZPeriod) tzPeriods.objectAtIndex(idx + 1048576))._startTime > time)
				break;
			idx += 1048576;
		case 19:
			if (((__NSTZPeriod) tzPeriods.objectAtIndex(idx + 524288))._startTime > time)
				break;
			idx += 524288;
		case 18:
			if (((__NSTZPeriod) tzPeriods.objectAtIndex(idx + 262144))._startTime > time)
				break;
			idx += 262144;
		case 17:
			if (((__NSTZPeriod) tzPeriods.objectAtIndex(idx + 131072))._startTime > time)
				break;
			idx += 131072;
		case 16:
			if (((__NSTZPeriod) tzPeriods.objectAtIndex(idx + 65536))._startTime > time)
				break;
			idx += 65536;
		case 15:
			if (((__NSTZPeriod) tzPeriods.objectAtIndex(idx + 32768))._startTime > time)
				break;
			idx += 32768;
		case 14:
			if (((__NSTZPeriod) tzPeriods.objectAtIndex(idx + 16384))._startTime > time)
				break;
			idx += 16384;
		case 13:
			if (((__NSTZPeriod) tzPeriods.objectAtIndex(idx + 8192))._startTime > time)
				break;
			idx += 8192;
		case 12:
			if (((__NSTZPeriod) tzPeriods.objectAtIndex(idx + 4096))._startTime > time)
				break;
			idx += 4096;
		case 11:
			if (((__NSTZPeriod) tzPeriods.objectAtIndex(idx + 2048))._startTime > time)
				break;
			idx += 2048;
		case 10:
			if (((__NSTZPeriod) tzPeriods.objectAtIndex(idx + 1024))._startTime > time)
				break;
			idx += 1024;
		case 9:
			if (((__NSTZPeriod) tzPeriods.objectAtIndex(idx + 512))._startTime > time)
				break;
			idx += 512;
		case 8:
			if (((__NSTZPeriod) tzPeriods.objectAtIndex(idx + 256))._startTime > time)
				break;
			idx += 256;
		case 7:
			if (((__NSTZPeriod) tzPeriods.objectAtIndex(idx + 128))._startTime > time)
				break;
			idx += 128;
		case 6:
			if (((__NSTZPeriod) tzPeriods.objectAtIndex(idx + 64))._startTime > time)
				break;
			idx += 64;
		case 5:
			if (((__NSTZPeriod) tzPeriods.objectAtIndex(idx + 32))._startTime > time)
				break;
			idx += 32;
		case 4:
			if (((__NSTZPeriod) tzPeriods.objectAtIndex(idx + 16))._startTime > time)
				break;
			idx += 16;
		case 3:
			if (((__NSTZPeriod) tzPeriods.objectAtIndex(idx + 8))._startTime > time)
				break;
			idx += 8;
		case 2:
			if (((__NSTZPeriod) tzPeriods.objectAtIndex(idx + 4))._startTime > time)
				break;
			idx += 4;
		case 1:
			if (((__NSTZPeriod) tzPeriods.objectAtIndex(idx + 2))._startTime > time)
				break;
			idx += 2;
		case 0:
			if (((__NSTZPeriod) tzPeriods.objectAtIndex(idx + 1))._startTime > time)
				break;
			++idx;
		}
		return (++idx);
	}

	private static NSTimeZone __concoctFixedTimeZone(int seconds, String abbr, int isDST) {
		int abbrLen = abbr.length();

		byte[] dataBytes = new byte[52 + abbrLen * 2 + 1];

		NSTimeZone result = null;

		__entzcode(1, dataBytes, 20);
		__entzcode(1, dataBytes, 24);

		__entzcode(1, dataBytes, 36);
		__entzcode(abbrLen + 1, dataBytes, 40);

		__entzcode(seconds, dataBytes, 44);
		dataBytes[48] = (byte) ((isDST > 0) ? 1 : 0);

		for (int i = 0; i < abbrLen; ++i) {
			char c = abbr.charAt(i);

			dataBytes[(50 + i * 2)] = (byte) (c >>> '\b');
			dataBytes[(50 + i * 2 + 1)] = (byte) (c << '\b' >>> 8);
		}

		result = timeZoneWithNameAndData(abbr, new NSData(dataBytes));

		return result;
	}

	private static int __detzcode(byte[] buffer, int offset) {
		int result = ((buffer[(offset + 0)] & 0x80) > 0) ? -1 : 0;

		result = result << 8 | buffer[(offset + 0)] & 0xFF;
		result = result << 8 | buffer[(offset + 1)] & 0xFF;
		result = result << 8 | buffer[(offset + 2)] & 0xFF;
		result = result << 8 | buffer[(offset + 3)] & 0xFF;

		return result;
	}

	private static void __entzcode(int value, byte[] buffer, int offset) {
		buffer[(offset + 0)] = (byte) (value >> 24 & 0xFF);
		buffer[(offset + 1)] = (byte) (value >> 16 & 0xFF);
		buffer[(offset + 2)] = (byte) (value >> 8 & 0xFF);
		buffer[(offset + 3)] = (byte) (value >> 0 & 0xFF);
	}

	private static void __initTimeZoneVariables() {
		InputStream abbrTable = NSTimeZone.class.getClassLoader().getResourceAsStream(__ABBR_TABLE_NAME);

		if (abbrTable == null) {
			throw new IllegalStateException("Unable to load timezone abbreviations table, " + __ABBR_TABLE_NAME + ".");
		}
		try {
			NSData abbrData = new NSData(abbrTable, __ABBR_TABLE_SIZE_IN_BYTES);
			abbrTable.close();
			if (abbrData.length() <= 0) {
				__abbreviations = NSDictionary.emptyDictionary();
			} else {
				Object plistObject = NSPropertyListSerialization.propertyListFromData(abbrData, __UTF8);
				if (plistObject instanceof NSDictionary<?, ?>) {
					NSDictionary<?, ?> plist = (NSDictionary<?, ?>) plistObject;
					__abbreviations = new NSMutableDictionary<String, String>();
					for (Object _key : plist.keySet()) {
						String key = _key.toString();
						Object _value = plist.get(key);
						if (_value != null) {
							String value = _value.toString();
							__abbreviations.put(key, value);
						}
					}
					__abbreviations = __abbreviations.immutableClone();
				} else {
					__abbreviations = NSDictionary.emptyDictionary();
				}
			}
		} catch (IOException e) {
			throw new NSForwardException(e, "Unable to parse data from timezone abbreviations table, " + __ABBR_TABLE_NAME + ".");
		}

		InputStream aliasTable = _CLASS.getClassLoader().getResourceAsStream(__ALIAS_TABLE_NAME);

		if (aliasTable == null) {
			throw new IllegalStateException("Unable to load timezone aliases table, " + __ALIAS_TABLE_NAME + ".");
		}
		try {
			NSData aliasData = new NSData(aliasTable, __ALIAS_TABLE_SIZE_IN_BYTES);
			aliasTable.close();
			if (aliasData.length() <= 0) {
				__aliases = NSDictionary.emptyDictionary();
			} else {
				Object plistObject = NSPropertyListSerialization.propertyListFromData(aliasData, __UTF8);
				if (plistObject instanceof NSDictionary<?, ?>) {
					NSDictionary<?, ?> plist = (NSDictionary<?, ?>) plistObject;
					__aliases = new NSMutableDictionary<String, String>();
					for (Object _key : plist.keySet()) {
						String key = _key.toString();
						Object _value = plist.get(key);
						if (_value != null) {
							String value = _value.toString();
							__aliases.put(key, value);
						}
					}
					__aliases = __aliases.immutableClone();
				} else {
					__aliases = NSDictionary.emptyDictionary();
				}
			}
		} catch (IOException e) {
			throw new NSForwardException(e, "Unable to parse data from aliases table, \"com/webobjects/foundation/TimeZoneInfo/Aliases.table\".");
		}

		NSMutableSet<String> knownTimeZoneNames = new NSMutableSet<String>(__abbreviations.count() + __aliases.count() + __namesDataTable.count());
		knownTimeZoneNames.addObjectsFromArray(__abbreviations.allKeys());
		knownTimeZoneNames.addObjectsFromArray(__aliases.allKeys());
		knownTimeZoneNames.addObjectsFromArray(__namesDataTable.allKeys());
		__knownTimeZoneNames = knownTimeZoneNames;

		__gmt = timeZoneWithName(__GMT, true);
	}

	private static int __less2(int A, int W) {
		int result;
		if (A < 1 << W)
			result = __less3(A, W - 4);
		else {
			result = __less3(A, W + 4);
		}

		return result;
	}

	private static int __less3(int A, int X) {
		int result;
		if (A < 1 << X)
			result = __less4(A, X - 2);
		else {
			result = __less4(A, X + 2);
		}

		return result;
	}

	private static int __less4(int A, int Y) {
		int result;
		if (A < 1 << Y)
			result = __less5(A, Y - 1);
		else {
			result = __less5(A, Y + 1);
		}

		return result;
	}

	private static int __less5(int A, int Z) {
		int result;
		if (A < 1 << Z)
			result = Z - 1;
		else {
			result = Z;
		}

		return result;
	}

	private static void __loadZipEntriesFromZoneArchive() {
		InputStream is = null;

		ZipInputStream zis = null;

		is = NSTimeZone.class.getClassLoader().getResourceAsStream(__ZONE_ARCHIVE_NAME);

		if (is == null) {
			is = ClassLoader.getSystemClassLoader().getResourceAsStream(__ZONE_ARCHIVE_NAME);
		}

		if (is == null)
			throw new IllegalStateException("Unable to get input stream for the timezone archive, " + __ZONE_ARCHIVE_NAME + ".");
		if (is instanceof ZipInputStream)
			zis = (ZipInputStream) is;
		else {
			try {
				zis = new ZipInputStream(is);
			} catch (Throwable e) {
				try {
					is.close();
				} catch (IOException exception) {
					if (NSLog.debugLoggingAllowedForLevelAndGroups(2, 8192L)) {
						NSLog.debug.appendln("Exception while closing input stream: " + exception.getMessage());
						NSLog.debug.appendln(exception);
					}
				}
				throw new NSForwardException(e, "Unable to create a ZipInputStream for the timezone archive, " + __ZONE_ARCHIVE_NAME + ".");
			}

		}

		try {
			ZipEntry ze = zis.getNextEntry();
			while (ze != null) {
				if (!(ze.isDirectory())) {
					long zeSize = ze.getSize();
					if (zeSize < 2147483647L) {
						byte[] buffer = new byte[(int) zeSize];
						int numRead = zis.read(buffer);

						if (numRead == -1) {
							throw new IllegalStateException("Entry \"" + ze.getName() + "\" in the timezone archive, \"" + __ZONE_ARCHIVE_NAME + "\", is empty.");
						}
						while (numRead < zeSize) {
							int i = zis.read(buffer, numRead, (int) zeSize - numRead);
							if (i == -1) {
								break;
							}
							numRead += i;
						}

						if (numRead != zeSize) {
							throw new IllegalStateException("Entry \"" + ze.getName() + "\" in the timezone archive, \"" + __ZONE_ARCHIVE_NAME + "\", is not the size recorded in the zip entry. (number of bytes read = " + numRead + ";  entry size = " + zeSize + ").");
						}

						__namesDataTable.setObjectForKey(new NSData(buffer, new NSRange(0, numRead), true), ze.getName());
					} else {
						throw new IllegalStateException("Entry \"" + ze.getName() + "\" in the timezone archive, \"" + __ZONE_ARCHIVE_NAME + "\", is too large to be used.");
					}
				}
				ze = zis.getNextEntry();
			}

			try {
				zis.close();
			} catch (IOException e) {
				throw NSForwardException._runtimeExceptionForThrowable(e);
			}
		} catch (IOException e) {
		} finally {
			try {
				zis.close();
			} catch (IOException e) {
				throw NSForwardException._runtimeExceptionForThrowable(e);
			}
		}

		if (__namesDataTable.count() == 0) {
			throw new IllegalStateException("Unable to find any zoneinfo files in the timezone archive, " + __ZONE_ARCHIVE_NAME + ".");
		}

		__namesDataTable.removeObjectForKey("Factory");
	}

	private static int __log2(int x) {
		int result = 0;

		if (x < 65536)
			result = __less2(x, 8);
		else {
			result = __less2(x, 24);
		}

		return result;
	}

	private static synchronized NSTimeZone __lookupOrCreateTimeZone(String aName) {
		NSData tzData = null;
		NSTimeZone result = __knownTimeZones.objectForKey(aName);

		if ((result == null) && (aName.startsWith(__GMT))) {
			int len = aName.length();
			try {
				int seconds = -1;
				if ((len > __GMT_LENGTH + 1) && (len < __GMT_LENGTH + 4))
					seconds = new Integer(aName.substring(__GMT_LENGTH + 1, __GMT_LENGTH + 3)).intValue() * 3600;
				else if ((len > __GMT_LENGTH + 1) && (len < __GMT_LENGTH + 7)) {
					seconds = new Integer(aName.substring(__GMT_LENGTH + 1, __GMT_LENGTH + 3)).intValue() * 3600 + new Integer(aName.substring(__GMT_LENGTH + 4, __GMT_LENGTH + 6)).intValue() * 60;
				}
				if (seconds >= 0) {
					switch (aName.charAt(__GMT_LENGTH)) {
					case '+':
						result = timeZoneForSecondsFromGMT(seconds);
						__knownTimeZones.setObjectForKey(result, aName);
						break;
					case '-':
						result = timeZoneForSecondsFromGMT(seconds * -1);
						__knownTimeZones.setObjectForKey(result, aName);
					}
				}

			} catch (Exception e) {
			}

		}

		if (result == null) {
			tzData = __namesDataTable.objectForKey(aName);
			if (tzData != null) {
				result = new NSTimeZone(aName, tzData);
				__knownTimeZones.setObjectForKey(result, aName);
			}
		}

		return result;
	}

	private static NSMutableArray<__NSTZPeriod> __parseTimeZoneData(NSData aData) {
		byte[] p = aData.bytes();
		int pidx = 0;
		int len = p.length;
		double distantTime = NSTimestamp.DistantPast.getTime() / 1000.0D;

		NSMutableArray<__NSTZPeriod> tzpp = null;

		if (len >= 44) {
			pidx += 20;
			pidx += 4;
			pidx += 4;
			pidx += 4;
			int timecnt = __detzcode(p, pidx);
			pidx += 4;
			int typecnt = __detzcode(p, pidx);
			pidx += 4;
			int charcnt = __detzcode(p, pidx);
			pidx += 4;
			if ((typecnt > 0) && (timecnt >= 0) && (charcnt >= 0) && (len - 44 >= 5 * timecnt + 6 * typecnt + charcnt)) {
				int cnt = (timecnt > 0) ? timecnt : 1;
				tzpp = new NSMutableArray<__NSTZPeriod>(cnt);

				String[] abbrs = new String[charcnt + 1];
				int timep = pidx;
				int typep = timep + 4 * timecnt;
				int ttisp = typep + timecnt;
				int charp = ttisp + 6 * typecnt;
				for (int idx = 0; idx < cnt; ++idx) {
					__NSTZPeriod tzp = new __NSTZPeriod();
					tzpp.addObject(tzp);
					tzp._startTime = ((timecnt > 0) ? __detzcode(p, timep) : distantTime);
					timep += 4;
					byte type = (timecnt > 0) ? p[(typep++)] : 0;
					if (typecnt <= type) {
						tzpp = null;
						break;
					}
					tzp._offset = __detzcode(p, ttisp + 6 * type);
					byte dst = p[(ttisp + 6 * type + 4)];
					if ((dst != 0) && (dst != 1)) {
						tzpp = null;
						break;
					}
					tzp._isdst = ((dst > 0) ? 1 : 0);
					byte abbridx = p[(ttisp + 6 * type + 5)];
					if ((abbridx < 0) || (charcnt < abbridx)) {
						tzpp = null;
						break;
					}
					if (abbrs[abbridx] == null) {
						int i;
						for (i = 0; (i + charp + abbridx < p.length) && (p[(i + charp + abbridx)] != 0); ++i)
							;
						try {
							abbrs[abbridx] = new String(p, charp + abbridx, i, "UTF8");
						} catch (UnsupportedEncodingException e) {
							throw new NSForwardException(e, "Unable to parse timezone period abbreviation.");
						}
					}
					tzp._abbreviation = abbrs[abbridx];
				}

				if (tzpp != null) {
					try {
						tzpp.sortUsingComparator(__tzPeriodComparator);
					} catch (NSComparator.ComparisonException e) {
						throw NSForwardException._runtimeExceptionForThrowable(e);
					}
				}
			}
		}

		return tzpp;
	}

	private static String __replacementTimeZoneNameForName(String aName, boolean tryAbbreviation) {
		String abbrName = null;
		String aliasName = null;
		String result = null;

		if (tryAbbreviation) {
			abbrName = (aName == null) ? null : (String) __abbreviations.objectForKey(aName);
		}

		aliasName = ((abbrName == null) && (aName != null)) ? (String) __aliases.objectForKey(aName) : null;

		if (abbrName == null)
			result = (aliasName == null) ? aName : aliasName;
		else {
			result = abbrName;
		}

		return result;
	}

	public static NSDictionary<String, String> abbreviationDictionary() {
		return __abbreviations;
	}

	public Class<?> classForCoder() {
		return _CLASS;
	}

	public Object clone() {
		return this;
	}

	public static Object decodeObject(NSCoder aDecoder) {
		Object name = aDecoder.decodeObject();
		Object data = aDecoder.decodeObject();
		Object result = null;

		if ((name == null) || (data == null)) {
			throw new IllegalStateException("Unable to decode object.");
		}
		if (name instanceof String) {
			result = timeZoneWithName((String) name, true);

			if ((((result == null) || (!(((NSTimeZone) result)._data.equals(data))))) && (data instanceof NSData)) {
				result = timeZoneWithNameAndData((String) name, (NSData) data);
			}

		}

		return result;
	}

	public static synchronized NSTimeZone defaultTimeZone() {
		if (__defaultTimeZone == null) {
			__defaultTimeZone = systemTimeZone();
		}

		return __defaultTimeZone;
	}

	public static String[] getAvailableIDs() {
		NSArray<String> kTZNArray = __knownTimeZoneNames.allObjects();
		int count = kTZNArray.count();
		String[] availableIDs = new String[count];

		for (int i = 0; i < count; ++i) {
			availableIDs[i] = kTZNArray.objectAtIndex(i);
		}

		return availableIDs;
	}

	public static NSTimeZone getGMT() {
		return __gmt;
	}

	public static TimeZone getDefault() {
		return defaultTimeZone();
	}

	public static NSArray<String> knownTimeZoneNames() {
		return __knownTimeZoneNames.allObjects();
	}

	@Deprecated
	public static NSTimeZone localTimeZone() {
		return __localTimeZone;
	}

	@Deprecated
	public static synchronized void resetSystemTimeZone() {
		NSNotificationCenter.defaultCenter().postNotification("NSSystemTimeZoneDidChangeNotification", null, null);
		__systemTimeZone = null;
	}

	public static synchronized void setDefault(TimeZone aTZ) {
		if (aTZ instanceof NSTimeZone)
			setDefaultTimeZone((NSTimeZone) aTZ);
		else
			setDefaultTimeZone(_nstimeZoneWithTimeZone(aTZ));
	}

	public static synchronized void setDefaultTimeZone(NSTimeZone aTZ) {
		if (aTZ instanceof __NSLocalTimeZone) {
			throw new IllegalArgumentException("Cannot set default timezone to a localTimeZone object");
		}
		__defaultTimeZone = aTZ;
	}

	public void setID(String anID) {
		throw new IllegalStateException(_CLASS.getName() + " objects are not mutable.");
	}

	public void setRawOffset(int offsetMillis) {
		throw new IllegalStateException(_CLASS.getName() + " objects are not mutable.");
	}

	public static synchronized NSTimeZone systemTimeZone() {
		if (__systemTimeZone == null) {
			String javaTimeZoneName = null;
			try {
				javaTimeZoneName = (String) AccessController.doPrivileged(new GetPropertyAction("user.timezone"));
			} catch (Exception e) {
			}

			if ((javaTimeZoneName == null) || (javaTimeZoneName.length() == 0)) {
				javaTimeZoneName = TimeZone.getDefault().getID();

				if ((javaTimeZoneName == null) || (javaTimeZoneName.length() == 0)) {
					__systemTimeZone = __gmt;
					NSLog.err.appendln("Couldn't find the system timezone (no java default). Using GMT.");
				}
			}
			__systemTimeZone = timeZoneWithName(javaTimeZoneName, true);

			if (__systemTimeZone == null) {
				__systemTimeZone = __gmt;
				NSLog.err.appendln("Couldn't find the system timezone. Using GMT.");
			}
		}

		return __systemTimeZone;
	}

	public static synchronized NSTimeZone timeZoneForSecondsFromGMT(int secondsOffsetFromGMT) {
		NSTimeZone result = null;

		if (secondsOffsetFromGMT == 0) {
			result = __gmt;
		} else {
			StringBuffer abbrev = null;
			int hour = secondsOffsetFromGMT / 3600;

			if ((secondsOffsetFromGMT % 3600 != 0) || (12 < Math.abs(hour))) {
				int minutes = 0;
				if (0 < secondsOffsetFromGMT) {
					minutes = (secondsOffsetFromGMT - (hour * 3600) + 30) / 60;
					abbrev = new StringBuffer(__GMT).append(__PLUS).append(__gmtHourFormatter.stringForObjectValue(_NSUtilities.IntegerForInt(hour))).append(__gmtMinuteFormatter.stringForObjectValue(_NSUtilities.IntegerForInt(minutes)));
				} else {
					minutes = (secondsOffsetFromGMT - (hour * 3600) - 30) / 60;
					abbrev = new StringBuffer(__GMT).append(__MINUS).append(__gmtHourFormatter.stringForObjectValue(_NSUtilities.IntegerForInt(Math.abs(hour)))).append(__gmtMinuteFormatter.stringForObjectValue(_NSUtilities.IntegerForInt(Math.abs(minutes))));
				}

				result = __concoctFixedTimeZone(hour * 3600 + minutes * 60, abbrev.toString(), 0);
			} else {
				result = timeZoneWithName(__GENERIC_TZ_NAME_STEM + ((0 < secondsOffsetFromGMT) ? __MINUS : __PLUS) + __hourFormatter.stringForObjectValue(_NSUtilities.IntegerForInt(Math.abs(hour))), true);
			}

		}

		return result;
	}

	public static synchronized NSTimeZone timeZoneWithName(String aName, boolean tryAbbreviation) {
		NSTimeZone result = null;

		if (aName == null) {
			throw new IllegalArgumentException("String parameter must be non-null");
		}
		String tzName = __replacementTimeZoneNameForName(aName, tryAbbreviation);
		if (tzName == null) {
			NSLog.debug.appendln("Cannot find NSTimeZone with name " + aName);
			return null;
		}
		result = __lookupOrCreateTimeZone(tzName);

		return result;
	}

	public static synchronized NSTimeZone timeZoneWithNameAndData(String aName, NSData aData) {
		NSTimeZone result = null;

		if ((aName == null) || (aData == null)) {
			throw new IllegalArgumentException("Both parameters must be non-null");
		}
		String tzName = __replacementTimeZoneNameForName(aName, false);
		result = new NSTimeZone(tzName, aData);

		return result;
	}

	public static NSTimeZone _nstimeZoneWithTimeZone(TimeZone aZone) {
		NSTimeZone result = null;

		if (aZone instanceof NSTimeZone) {
			result = (NSTimeZone) aZone;
		} else {
			result = timeZoneWithName(aZone.getID(), true);

			if (result == null) {
				result = timeZoneForSecondsFromGMT(aZone.getRawOffset() / 1000);
				if (result == null) {
					throw new IllegalArgumentException("Can not construct an NSTimeZone for zone: " + aZone.toString());
				}
			}
		}
		return result;
	}

	private synchronized void _initialize() {
		if (this._initialized) {
			return;
		}
		this._timeZonePeriods = __parseTimeZoneData(this._data);
		this._timeZonePeriodsCount = this._timeZonePeriods.count();

		for (int i = this._timeZonePeriodsCount - 1; i >= 0; --i) {
			if (((__NSTZPeriod) this._timeZonePeriods.objectAtIndex(i))._isdst > 0) {
				this._useDaylightTime = true;
				break;
			}

		}

		if ((this._name.startsWith(__GMT)) && (this._name.length() > __GMT_LENGTH + 1)) {
			int seconds = 0;
			switch (this._name.charAt(__GMT_LENGTH)) {
			case '+':
				seconds = new Integer(this._name.substring(__GMT_LENGTH + 1, __GMT_LENGTH + 3)).intValue() * 3600 + new Integer(this._name.substring(__GMT_LENGTH + 4, __GMT_LENGTH + 6)).intValue() * 60;

				break;
			case '-':
				seconds = (new Integer(this._name.substring(__GMT_LENGTH + 1, __GMT_LENGTH + 3)).intValue() * 3600 + new Integer(this._name.substring(__GMT_LENGTH + 4, __GMT_LENGTH + 6)).intValue() * 60) * -1;
			}

			this._rawOffset = (seconds * 1000);
		}

		if (this._name.startsWith(__GENERIC_TZ_NAME_STEM)) {
			int stemLength = __GENERIC_TZ_NAME_STEM_LENGTH;
			int nameLength = this._name.length();
			if (nameLength > stemLength + 1) {
				int seconds = 0;
				switch (this._name.charAt(stemLength)) {
				case '+':
					seconds = new Integer(this._name.substring(stemLength + 1, nameLength)).intValue() * 3600 * -1;
					break;
				case '-':
					seconds = new Integer(this._name.substring(stemLength + 1, nameLength)).intValue() * 3600;
				}

				this._rawOffset = (seconds * 1000);
			}
		}
		this._initialized = true;
	}

	public String abbreviation() {
		return abbreviationForTimestamp(new NSTimestamp());
	}

	public String abbreviationForTimestamp(NSTimestamp aTimestamp) {
		String result = null;

		if (!(this._initialized)) {
			_initialize();
		}

		int idx = __bSearchTZPeriods(this._timeZonePeriods, this._timeZonePeriodsCount, aTimestamp.getTime() / 1000L);

		if (this._timeZonePeriodsCount < idx)
			idx = this._timeZonePeriodsCount;
		else if (idx == 0) {
			idx = 1;
		}

		result = ((__NSTZPeriod) this._timeZonePeriods.objectAtIndex(idx - 1))._abbreviation;

		if ((result.startsWith(__GMT)) && (result.length() > __GMT_LENGTH + 1)) {
			String nonPOSIXabbr = null;
			switch (result.charAt(__GMT_LENGTH)) {
			case '+':
				nonPOSIXabbr = __GMT + __MINUS + result.substring(__GMT_LENGTH + 1);
				break;
			case '-':
				nonPOSIXabbr = __GMT + __PLUS + result.substring(__GMT_LENGTH + 1);
			}
			result = nonPOSIXabbr;
		}

		return result;
	}

	public NSData data() {
		return this._data;
	}

	public void encodeWithCoder(NSCoder aCoder) {
		aCoder.encodeObject(this._name);
		aCoder.encodeObject(this._data);
	}

	public boolean equals(Object anObject) {
		return ((anObject instanceof NSTimeZone) ? this._data.equals(((NSTimeZone) anObject).data()) : false);
	}

	public String getDisplayName(boolean inDaylightSavingTime, int aTZStyle, Locale aLocale) {
		return this._name;
	}

	public String getID() {
		return this._name;
	}

	public int getOffset(int anEra, int aYear, int aMonth, int aDayOfMonth, int aDayOfWeek, int milliseconds) {
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone(__GMT));
		int timeUnits = milliseconds;

		cal.clear();

		cal.set(0, anEra);
		cal.set(aYear, aMonth, aDayOfMonth);
		cal.set(7, aDayOfWeek);
		cal.set(14, timeUnits % 1000);
		timeUnits /= 1000;
		cal.set(13, timeUnits % 60);
		timeUnits /= 60;
		cal.set(12, timeUnits % 60);
		timeUnits /= 60;
		if (timeUnits > 23) {
			throw new IllegalArgumentException("too many milliseconds for a single day" + milliseconds);
		}
		cal.set(11, timeUnits);

		return (secondsFromGMTForTimestamp(new NSTimestamp(cal.getTime())) * 1000);
	}

	int getOffset(NSTimestamp ts) {
		return (secondsFromGMTForTimestamp(ts) * 1000);
	}

	public int getRawOffset() {
		if (!(this._initialized)) {
			_initialize();
		}
		return this._rawOffset;
	}

	public synchronized int hashCode() {
		if (this._hashCode == 0) {
			this._hashCode = this._data.hashCode();
		}

		return this._hashCode;
	}

	public boolean hasSameRules(TimeZone aTZ) {
		return equals(aTZ);
	}

	public boolean inDaylightTime(Date aDate) {
		return isDaylightSavingTimeForTimestamp(new NSTimestamp(aDate));
	}

	public boolean isDaylightSavingTime() {
		return isDaylightSavingTimeForTimestamp(new NSTimestamp());
	}

	public boolean isDaylightSavingTimeForTimestamp(NSTimestamp aTimestamp) {
		boolean result = false;

		if (!(this._initialized)) {
			_initialize();
		}

		int idx = __bSearchTZPeriods(this._timeZonePeriods, this._timeZonePeriodsCount, aTimestamp.getTime() / 1000L);

		if (this._timeZonePeriodsCount < idx)
			idx = this._timeZonePeriodsCount;
		else if (idx == 0) {
			idx = 1;
		}

		result = ((__NSTZPeriod) this._timeZonePeriods.objectAtIndex(idx - 1))._isdst != 0;

		return result;
	}

	@Deprecated
	public boolean isEqualToTimeZone(NSTimeZone aTimeZone) {
		return equals(aTimeZone);
	}

	@Deprecated
	public String name() {
		return getID();
	}

	public int secondsFromGMT() {
		int result = secondsFromGMTForTimestamp(new NSTimestamp());
		return result;
	}

	public int secondsFromGMTForTimestamp(NSTimestamp aTimestamp) {
		return secondsFromGMTForOffsetInSeconds(aTimestamp.getTime() / 1000L);
	}

	int secondsFromGMTForOffsetInSeconds(long offset) {
		int result = 0;

		if (!(this._initialized)) {
			_initialize();
		}

		int idx = __bSearchTZPeriods(this._timeZonePeriods, this._timeZonePeriodsCount, offset);

		if (this._timeZonePeriodsCount < idx)
			idx = this._timeZonePeriodsCount;
		else if (idx == 0) {
			idx = 1;
		}

		result = ((__NSTZPeriod) this._timeZonePeriods.objectAtIndex(idx - 1))._offset;

		return result;
	}

	public String toString() {
		if (!(this._initialized)) {
			_initialize();
		}

		return this._name + " (" + abbreviation() + ") offset " + secondsFromGMT() + ((isDaylightSavingTime()) ? " (Daylight)" : "");
	}

	public boolean useDaylightTime() {
		if (!(this._initialized)) {
			_initialize();
		}

		return this._useDaylightTime;
	}

	private void writeObject(ObjectOutputStream s)
		throws IOException {
		ObjectOutputStream.PutField fields = s.putFields();
		synchronized (this) {
			if (this instanceof __NSLocalTimeZone) {
				fields.put(SerializationNameFieldKey, null);
				fields.put(SerializationDataFieldKey, null);
			} else {
				fields.put(SerializationNameFieldKey, this._name);
				fields.put(SerializationDataFieldKey, this._data);
			}
		}
		s.writeFields();
	}

	private void readObject(ObjectInputStream s)
		throws IOException,
		ClassNotFoundException {
		ObjectInputStream.GetField fields = null;
		fields = s.readFields();

		this._name = ((String) fields.get(SerializationNameFieldKey, null));
		this._data = ((NSData) fields.get(SerializationDataFieldKey, null));
	}

	protected Object readResolve()
		throws ObjectStreamException {
		Object result = null;

		if ((this._name != null) && (this._data != null)) {
			result = timeZoneWithName(this._name, true);

			if ((result == null) || (!(((NSTimeZone) result)._data.equals(this._data)))) {
				result = timeZoneWithNameAndData(this._name, this._data);
			}
			return result;
		}
		return this;
	}

	static {
		try {
			__loadZipEntriesFromZoneArchive();
		} catch (Throwable e) {
			System.err.println("Exception encountered while loading zoneinfo from archive during NSTimeZone class initialization: " + e.getMessage());
			e.printStackTrace(System.err);
		}
		try {
			__initTimeZoneVariables();
		} catch (Throwable e) {
			System.err.println("Exception encountered while initializing NSTimeZone class: " + e.getMessage());
			e.printStackTrace(System.err);
		}

		serialPersistentFields = new ObjectStreamField[] { new ObjectStreamField(SerializationNameFieldKey, _NSUtilities._StringClass), new ObjectStreamField(SerializationDataFieldKey, NSData._CLASS) };
	}

	protected static class __NSTZPeriodComparator
		extends
		NSComparator<__NSTZPeriod> {
		protected boolean _ascending;

		public __NSTZPeriodComparator() {
			this(true);
		}

		public __NSTZPeriodComparator(
			boolean ascending) {
			this._ascending = ascending;
		}

		public int compare(__NSTZPeriod object1, __NSTZPeriod object2)
			throws NSComparator.ComparisonException {
			if ((object1 == null) || (object2 == null)) {
				throw new NSComparator.ComparisonException("Unable to compare objects.  Objects should be instance of class __NSTZPeriod.  Comparison was made with " + object1 + " and " + object2 + ".");
			}

			if ((object1 == object2) || (object1.equals(object2))) {
				return 0;
			}

			if ((object1).before(object2)) {
				return ((this._ascending) ? -1 : 1);
			}

			return ((this._ascending) ? 1 : -1);
		}
	}

	protected static class __NSTZPeriod {
		protected String _abbreviation;
		protected int _isdst;
		protected int _offset;
		protected double _startTime;

		protected boolean before(__NSTZPeriod aNSTZP) {
			boolean result = false;

			if (this._startTime < aNSTZP._startTime) {
				result = true;
			}

			return result;
		}

		protected boolean equals(__NSTZPeriod aNSTZP) {
			boolean result = false;

			if (this._startTime == aNSTZP._startTime) {
				result = true;
			}

			return result;
		}
	}
}