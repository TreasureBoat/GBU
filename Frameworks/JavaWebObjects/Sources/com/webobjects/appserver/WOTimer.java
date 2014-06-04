
package com.webobjects.appserver;

import com.webobjects.foundation.NSComparator;
import com.webobjects.foundation.NSTimestamp;
import com.webobjects.foundation.NSSelector;

import java.lang.reflect.Method;

import java.util.Arrays;

/** Clean implementation. Does not fire yet, but it implements the API as a stub. **/
public class WOTimer {

    private double _timeInterval = (double)-1;

    private Object _targetObject = null;

    private Object _userInfo = null;

    private Class _userInfoClass = null;

    private boolean _repeats = false;

    private Object _argument = null;

    private Class _argumentClass = null;

    private String _selectorName = null;

    private NSSelector _selector = null;

    private boolean _isValid = false;

    private int _hashCode = -1;
    private boolean _hashIsValid = false;

    private NSTimestamp _fireDate = null;

    private WOTimer() {
        super();
    }

    public WOTimer(long tInterval, Object aTarget, String aSelectorName, Object userInfo, Class userInfoClass, boolean repeats) {

         super();

         _timeInterval = (double)tInterval;
         _targetObject = aTarget;
         _selectorName = aSelectorName;
         _userInfo = userInfo;
         _userInfoClass = userInfoClass;
         _repeats = repeats;

         _selector = new NSSelector(_selectorName, new Class [] { WOTimer.class });

         _isValid = true;
    }

    public WOTimer(NSTimestamp fireDate, long tInterval, Object aTarget, String aSelectorName, Object userInfo, Class userInfoClass, boolean repeats) {

        this(tInterval, aTarget, aSelectorName, userInfo, userInfoClass, repeats);

        _fireDate = fireDate;
    }

    public static WOTimer scheduledTimer(long tInterval, Object aTarget, String aSelectorName, Object userInfo, boolean repeats) {

        WOTimer aTimer = new WOTimer(tInterval, aTarget, aSelectorName, userInfo, userInfo.getClass(), repeats);

        aTimer.schedule();

        return aTimer;
    }

    public static WOTimer scheduledTimer(long tInterval, Object aTarget, String aSelectorName, Object anArgument, Class anArgumentClass, boolean repeats) {

         WOTimer aTimer = new WOTimer(tInterval, aTarget, aSelectorName, null, null, repeats);
    
         aTimer._argument = anArgument;
         aTimer._argumentClass = anArgumentClass;

         aTimer._selector = new NSSelector(aSelectorName, new Class[] { anArgumentClass } );

         aTimer.schedule();

         return aTimer;
    }

    public void fire() {
    }

    public NSTimestamp fireDate() {
        if (_fireDate != null) return _fireDate;

        // else figure out the next fireDate and return that.
        //
        return null;
    }

    public double timeInterval() {
        return _timeInterval;
    }

    public void invalidate() {
        _isValid = false;
    }

    public boolean isValid() {
        return _isValid;
    }

    public Object userInfo() {
        return _userInfo;
    }

    public Object target() {
        return _targetObject;
    }

    public NSSelector selector() {
        return _selector;
    }

    public void schedule() {
        _isValid = false;

        if (_timeInterval <= 0.0) {
            System.err.println("Given timeInterval supplied to WOTimer cannot be less than zero.");
            return;
        }

        if (_targetObject == null || _selectorName == null) {
            System.err.println("Neither the targetObject or selectorName support to WOTimer can be null.");
            return;
        }

        Method methods[] = _targetObject.getClass().getMethods();
        boolean methodFound = false;

        for (int idx = 0; idx < methods.length && ! methodFound; idx++) {
            if (methods[idx].getName().equals(_selectorName)) {
                if (_argumentClass == null && methods[idx].getParameterTypes().equals(new Class<?>[] { WOTimer.class }))
                    methodFound = true;
                if (_argumentClass != null && methods[idx].getParameterTypes().equals(new Class<?>[] { _argumentClass }))
                    methodFound = true;
            }
        }

        if ( ! methodFound) {
            System.err.println("Cannot find method with name \""+_selectorName+"\" and proper argument type in class \""+_targetObject.getClass()+"\"");
            return;
        }

        if (_fireDate != null && _fireDate.compare(new NSTimestamp()) == NSComparator.OrderedAscending) {
            System.err.println("If fireDate for WOTimer is set, it cannot be set for past time.");
            return;
        }

        _isValid = true;
    }

    public String toString() {
        return "WOTimer: "+super.toString();
    }

    public boolean equals(Object otherTimer) {

        if ( ! otherTimer.getClass().getName().equals("com.webobjects.appserver.WOTimer")) return false;

        WOTimer other = (WOTimer)otherTimer;

        if (_hashIsValid && other._hashIsValid && this._hashCode != other._hashCode) return false;

        if (_isValid != other._isValid) return false;

        if (_timeInterval != other._timeInterval) return false;

        if (_targetObject == null && other._targetObject != null) return false;
        if (_targetObject != null && other._targetObject == null) return false;

        if (_targetObject != null && ! _targetObject.getClass().equals(other._targetObject.getClass())) return false;

        if (_userInfoClass == null && other._userInfoClass != null) return false;
        if (_userInfoClass != null && other._userInfoClass == null) return false;

        if (_userInfoClass != null && ! _userInfoClass.equals(other._userInfoClass)) return false;

        if (_repeats != other._repeats) return false;

        if (_argumentClass == null && other._argumentClass != null) return false;
        if (_argumentClass != null && other._argumentClass == null) return false;

        if (_argumentClass != null && ! _argumentClass.equals(other._argumentClass)) return false;

        if (_selectorName == null && other._selectorName != null) return false;
        if (_selectorName != null && other._selectorName == null) return false;

        if (_selectorName != null && ! _selectorName.equals(other._selectorName)) return false;

        return true;
    } 

    public int hashCode() {

        if ( ! _hashIsValid) {

            Object objects[] = new Object[7];

            objects[0] = new Double(_timeInterval);
            objects[1] = _targetObject;
            objects[2] = _userInfo;
            objects[3] = new Boolean(_repeats);
            objects[4] = _argument;
            objects[5] = _selector;
            objects[6] = _fireDate;

            _hashCode = Arrays.deepHashCode(objects);

            _hashIsValid = true;
        }
        return _hashCode;
    }
}
