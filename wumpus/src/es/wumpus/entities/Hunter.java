/**
 * 
 */
package es.wumpus.entities;

/**
 * @author Ismael Pérez Santos
 *
 */
public class Hunter  extends Characters{
	
	/**
	 * The arrows.
	 */
	Integer arrows;
	
	/**
	 * Find gold?.
	 */
	Boolean findGold;
	
	/**
	 * The direction
	 */
	Integer direction;
	
	/**
	 * Is live?
	 */
	Boolean live;

	/**
	 * Default constructor.
	 */
	public Hunter() {
		// Nothing to do here.
	}

	/**
	 * @param position The position.
	 * @param arrows The arrows.
	 * @param findGold is find gold?.
	 * @param direction The direction.
	 */
	public Hunter(Integer row,Integer col, Integer arrows,Boolean findGold,Integer direction) {
		super(row,col);
		
		this.arrows=arrows;
		this.findGold=findGold;
		this.direction=direction;
	}

	/**
	 * @return the arrows
	 */
	public Integer getArrows() {
		return arrows;
	}

	/**
	 * @param arrows the arrows to set
	 */
	public void setArrows(Integer arrows) {
		this.arrows = arrows;
	}

	/**
	 * @return the findGold
	 */
	public Boolean isFindGold() {
		return findGold;
	}

	/**
	 * @param findGold the findGold to set
	 */
	public void setFindGold(Boolean findGold) {
		this.findGold = findGold;
	}

	/**
	 * @return the direction
	 */
	public Integer getDirection() {
		return direction;
	}

	/**
	 * @param direction the direction to set
	 */
	public void setDirection(Integer direction) {
		this.direction = direction;
	}

	/**
	 * @return the live
	 */
	public Boolean isLive() {
		return live;
	}

	/**
	 * @param live the live to set
	 */
	public void setLive(Boolean live) {
		this.live = live;
	}
}
