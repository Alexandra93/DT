package dt.egf.utils.helpers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationTextField {
	
	public boolean validateTextField(String fieldContent, String regex)
	{
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(fieldContent);
		if (!matcher.matches()) 
			return false;
		return true;
	}
}