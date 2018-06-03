/**
 * 
 */
package es.wumpus.entities;

/**
 * @author Ismael Pérez Santos
 *
 */
public class Wumpus extends Characters{

	/**
	 * The wumpus state.
	 */
	Boolean state;

	/**
	 * Default constructor.
	 */
	public Wumpus() {
		// Nothing to do here.
	}

	/**
	 * Constructor with the given parameter.
	 * 
	 * @param position The position.
	 * @param state The state.
	 */
	public Wumpus(Integer row,Integer col,Boolean state) {
		super(row,col);
		
		this.state=state;
	}

	/**
	 * @return the state
	 */
	public Boolean isState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(Boolean state) {
		this.state = state;
	}
}
