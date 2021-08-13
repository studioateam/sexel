package net.aydini.sexel.writer;

import net.aydini.sexel.configuration.ConfigurationProperty;
import net.aydini.sexel.exception.SexelException;

public abstract class AbstractWriter {

	private final ConfigurationProperty configurationProperty;

	AbstractWriter(ConfigurationProperty configurationProperty) {
		this.configurationProperty = configurationProperty;
	}

	public final void write() {
		try {
			doWrite();
		} catch (Exception e) {
			onError(e);
		}
	}

	protected void onError(Throwable throwable) {
		if (configurationProperty.isFailOnError())
			throw new SexelException(throwable.getMessage(), throwable);
	}

	protected abstract void doWrite() throws Exception;

	protected ConfigurationProperty getConfigurationProperty() {
		return configurationProperty;
	}
}
