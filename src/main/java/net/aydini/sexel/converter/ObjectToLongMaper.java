package net.aydini.sexel.converter;

import org.apache.commons.lang3.StringUtils;

import net.aydini.mom.common.service.maper.Maper;

/**
 * 
 * @author <a href="mailto:hi@aydini.net">Aydin Nasrollahpour </a>
 *
 */
public class ObjectToLongMaper implements Maper<Object, Long> {

	@Override
	public Long map(Object input) {
		if (input == null)
			return null;
		String inputString = input.toString();
		if(StringUtils.isNumeric(inputString))
			return Long.parseLong(inputString);
		return 0l;
	}
}
