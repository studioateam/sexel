package net.aydini.sexel.converter;

import net.aydini.mom.common.service.maper.Maper;

/**
 * 
 * @author <a href="mailto:hi@aydini.net">Aydin Nasrollahpour </a>
 *
 */
public class VoidMaper implements Maper<Object, Object>{

	@Override
	public Object map(Object input) {
		return input;
	}

}
