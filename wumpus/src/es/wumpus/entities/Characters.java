/**
 * The general entity
 */
package es.wumpus.entities;

/**
 * @author Ismael Pérez Santos
 *
 */
public class Characters {
	
	/**
	 * The character position row.
	 */
	Integer row;
	
	/**
	 * The character position column.
	 */
	Integer col;
	
	/**
	 * Default constructor.
	 */
	public Characters() {
		// Nothing to do here.
	}

	/**
	 * Constructor with the given parameter.
	 * 
	 * @param position
	 */
	public Characters(Integer row,Integer col) {
		this.row=row;
		this.col=col;
	}

	/**
	 * @return the row
	 */
	public Integer getRow() {
		return row;
	}

	/**
	 * @param row the row to set
	 */
	public void setRow(Integer row) {
		this.row = row;
	}

	/**
	 * @return the col
	 */
	public Integer getCol() {
		return col;
	}

	/**
	 * @param col the col to set
	 */
	public void setCol(Integer col) {
		this.col = col;
	}
}
