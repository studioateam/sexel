package net.aydini.sexel.converter;

import net.aydini.mom.common.service.maper.Maper;

/**
 * 
 * @author <a href="mailto:hi@aydini.net">Aydin Nasrollahpour </a>
 *
 */
public class ObjectToStringMaper implements Maper<Object, String>{

	@Override
	public String map(Object input) {
		if(input ==null)
		return null;
		return input.toString();
	}

}
