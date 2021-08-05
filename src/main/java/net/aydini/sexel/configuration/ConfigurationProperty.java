package net.aydini.sexel.configuration;


/**
 * 
 * @author <a href="mailto:hi@aydini.net">Aydin Nasrollahpour </a>
 *
 */
public class ConfigurationProperty {

	private Direction direction = Direction.RTL;
	
	private boolean skipHeader = false;
	
	private int startRow = 0;
	
	
	public Direction getDirection() {
		return direction;
	}



	public ConfigurationProperty setDirection(Direction direction) {
		this.direction = direction;
		return this;
	}



	public boolean isSkipHeader() {
		return skipHeader;
	}



	public ConfigurationProperty setSkipHeader(boolean skipHeader) {
		this.skipHeader = skipHeader;
		return this;
	}

	public int getStartRow() {
		return startRow;
	}



	public ConfigurationProperty setStartRow(int startRow) {
		this.startRow = startRow;
		return this;
	}





	public enum Direction
	{
		RTL,LTR;
	}
}
