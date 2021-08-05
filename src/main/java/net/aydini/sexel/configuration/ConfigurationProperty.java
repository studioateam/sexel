package net.aydini.sexel.configuration;


/**
 * 
 * @author <a href="mailto:hi@aydini.net">Aydin Nasrollahpour </a>
 *
 */
public class ConfigurationProperty {

	private Direction direction = Direction.RTL;
	
	private boolean skipHeader = false;
	
	
	
	public Direction getDirection() {
		return direction;
	}



	public void setDirection(Direction direction) {
		this.direction = direction;
	}



	public boolean isSkipHeader() {
		return skipHeader;
	}



	public void setSkipHeader(boolean skipHeader) {
		this.skipHeader = skipHeader;
	}



	public enum Direction
	{
		RTL,LTR;
	}
}
