package net.aydini.sexel.reader;

import net.aydini.sexel.configuration.ConfigurationProperty;
import net.aydini.sexel.exception.SexelException;

/**
 * template method for reader
 * 
 * @author <a href="mailto:hi@aydini.net">Aydin Nasrollahpour </a>
 *
 */
public abstract class AbstractReader<T> {

	private final ConfigurationProperty configurationProperty;

	AbstractReader(ConfigurationProperty configurationProperty) {
		this.configurationProperty = configurationProperty;
	}

	public final T read() {
		try {
			return doRead();
		} catch (Exception e) {
			onError(e);
		}
		return null;
	}

	protected void onError(Throwable throwable) {
		if (configurationProperty.isFailOnError())
			throw new SexelException(throwable.getMessage(), throwable);
	}

	protected abstract T doRead();

	protected ConfigurationProperty getConfigurationProperty() {
		return configurationProperty;
	}
}
