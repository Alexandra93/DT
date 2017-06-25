package dt.egf.utils.workflow;

import java.util.HashMap;
import java.util.Set;

public class ValueComposer {

	private HashMap<String, Object> keymap = new HashMap<String, Object>();
	
	public Set<String> keySet() {
		return keymap.keySet();
	}
	protected Object get(String key) {
		return keymap.get(key);
	}
	protected void put(String key, Object v) {
		keymap.put(key, v);
	}
	
	// wrapping to variable and unwrapping by regexpression
	public static String wrapKeyToPattern(String key) {
		return "${" + key + "}";
	}
	public static String replaceAll(String subject, String key, String replacement) {
		if (replacement == null) return subject;
		return subject.replace(wrapKeyToPattern(key) , replacement); // don't use regexp replaceAll...
	}
	
	public void setValue(String key, Object v) {
		putValue(key, composedValue(v));
	}
	
	public Object getValue(String key) {
		return get(key);
	}
	
	public Object getComposedValue(String key) {
		Object v = get(key);
		return composedValue(v);
	}
	protected Object composedValue(Object v) {
		if (v instanceof String) {
			// do pattern replacement conditionally:
			if (((String) v).contains("${")) {
				// assume that it is a pattern
				v = constructValue((String)v);
			}
		}
		return v;
	}
	
	public void putValue(String key, Object v) {
		put(key, v);
	}

	public Set<String> getKeySet() {
		return keySet();
	}
	
	protected Object getValueToApply(String key) {
		return getValue(key);
	}

	public String constructValue(String pattern) {
		String construction = pattern;
		for (String key : keySet()) {
			Object value = getValueToApply(key);
			String replacement = "";
			// HAMI:
			// seems unnecessary, using toString() may be enough:
			// wanted to prepare for handling more difficult situations
			// e.g. lists
			if (value instanceof String) {
				replacement = (String)value;
			} else if (value != null) {
				replacement = value.toString();
			}
			construction = replaceAll(construction, key, replacement);
		}
		return construction;
	}

}
