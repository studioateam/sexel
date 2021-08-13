package net.aydini.sexel.converter;

import org.apache.commons.lang3.StringUtils;

import net.aydini.mom.common.service.maper.Maper;

/**
 * 
 * @author <a href="mailto:hi@aydini.net">Aydin Nasrollahpour </a>
 *
 */
public class ObjectToIntegerMaper implements Maper<Object, Integer> {

	@Override
	public Integer map(Object input) {
		if (input == null)
			return null;
		String inputString = input.toString();
		if(StringUtils.isNumeric(inputString))
			return Integer.parseInt(inputString);
		return 0;
	}
}
