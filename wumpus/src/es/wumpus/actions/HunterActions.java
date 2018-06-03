/**
 * 
 */
package es.wumpus.actions;

import java.util.List;

import es.wumpus.entities.Hunter;
import es.wumpus.entities.Wumpus;

/**
 * @author Ismael Pérez Santos
 *
 */
public class HunterActions {

	/**
	 * The wumpus state.
	 */
	public HunterActions() {
		// Nothing to do here.
	}
	
	/**
	 * Hunter advance.
	 * 
	 * @param hunter The hunter.
	 * @param perceptions The perceptions.
	 * @param cells The number of cells.
	 * @param wumpus The wumpus.
	 */
	public void up(Hunter hunter,List<String> perceptions, Integer cells,Wumpus wumpus) {
		Integer direction=hunter.getDirection();
		Integer currentCol=hunter.getCol();
		Integer currentRow=hunter.getRow();
		
		switch (direction){
		// The hunter advance to right direction.
		case 1:
			if (currentCol==cells-1) {
				perceptions.add("Te has chocado contra el muro");
				break;
			}
			hunter.setCol(currentCol+1);
			break;
		// The hunter advance to up direction.
		case 2:
			if (currentRow==cells-1) {
				perceptions.add("Te has chocado contra el muro");
				break;
			}
			hunter.setRow(currentRow+1);
			break;
		// The hunter advance to left direction.
		case 3:
			if (currentCol==0) {
				perceptions.add("Te has chocado contra el muro");
				break;
			}
			hunter.setCol(currentCol-1);
			break;
		// The hunter advance to down direction.
		case 4:
			if (currentRow==0) {
				perceptions.add("Te has chocado contra el muro");
				break;
			}
			hunter.setRow(currentRow-1);
			break;
		}
	}
	
	/**
	 * The hunter turn left.
	 * 
	 * @param hunter The hunter.
	 * @param perceptions The perceptions.
	 * @param cells The number of cells.
	 * @param wumpus The wumpus.
	 */
	public void left(Hunter hunter,List<String> perceptions, Integer cells,Wumpus wumpus) {
		Integer direction=hunter.getDirection();
		
		// If direction is the last position (4-down), the next direction is the first position (1-right). 
		if (direction==4) {
			direction=1;
		}else {
			direction=direction+1;
		}
		
		hunter.setDirection(direction);
	}
	
	/**
	 * The hunter turn right.
	 * 
	 * @param hunter The hunter.
	 * @param perceptions The perceptions.
	 * @param cells The number of cells.
	 * @param wumpus The wumpus.
	 */
	public void right(Hunter hunter,List<String> perceptions, Integer cells,Wumpus wumpus) {
		Integer direction=hunter.getDirection();
		
		// If direction is the first position (1-right), the next direction is the last position (4-down).
		if (direction==1) {
			direction=4;
		}else {
			direction=direction-1;
		}
		
		hunter.setDirection(direction);
	}
	
	/**
	 * The hunter shoot.
	 * 
	 * @param hunter The hunter.
	 * @param perceptions The perceptions.
	 * @param cells The number of cells.
	 * @param wumpus The wumpus.
	 */
	public void shoot(Hunter hunter,List<String> perceptions, Integer cells,Wumpus wumpus) {
		Integer arrows=hunter.getArrows();
		if (arrows==0) {
			perceptions.add("Te has quedado sin flechas");
			return;
		}
		
		Integer direction=hunter.getDirection();
		switch (direction) {
		// The hunter shoot an arrow to right direction.
		case 1: 
			if (wumpus.getCol()<hunter.getCol()) {
				perceptions.add("La flecha choca contra la pared.");
				return;
			}
			for (Integer i=hunter.getCol();i<cells;i++) {
				if (wumpus.getCol()==i && wumpus.getRow()==hunter.getRow() && wumpus.isState()) {
					perceptions.add("El wumpus grita.");
					wumpus.setState(Boolean.FALSE);
					return;
				}
			}
			perceptions.add("La flecha choca contra la pared.");
			break;
		// The hunter shoot an arrow to up direction.
		case 2:
			if (wumpus.getRow()<hunter.getRow()) {
				perceptions.add("La flecha choca contra la pared.");
				return;
			}
			for (Integer i=hunter.getRow();i<cells;i++) {
				if (wumpus.getRow()==i && wumpus.getCol()==hunter.getCol() && wumpus.isState()) {
					perceptions.add("El wumpus grita.");
					wumpus.setState(Boolean.FALSE);
					return;
				}
			}
			perceptions.add("La flecha choca contra la pared.");
			break;
		// The hunter shoot an arrow to left direction.
		case 3:
			if (wumpus.getCol()>hunter.getCol()) {
				perceptions.add("La flecha choca contra la pared.");
				return;
			}
			for (Integer i=hunter.getCol();i==0;i--) {
				if (wumpus.getCol()==i && wumpus.getRow()==hunter.getRow() && wumpus.isState()) {
					perceptions.add("El wumpus grita.");
					wumpus.setState(Boolean.FALSE);
					return;
				}
			}
			perceptions.add("La flecha choca contra la pared.");
			break;
		// The hunter shoot an arrow to down direction.
		case 4:
			if (wumpus.getRow()>hunter.getRow()) {
				perceptions.add("La flecha choca contra la pared.");
				return;
			}
			for (Integer i=hunter.getRow();i==0;i--) {
				if (wumpus.getRow()==i && wumpus.getCol()==hunter.getCol() && wumpus.isState()) {
					perceptions.add("El wumpus grita.");
					wumpus.setState(Boolean.FALSE);
					return;
				}
			}
			perceptions.add("La flecha choca contra la pared.");
			break;
			
		}
		
		hunter.setArrows(arrows-1);
	}
	
	/**
	 * The hunter exit.
	 * 
	 * @param hunter The hunter.
	 * @param perceptions The perceptions.
	 * @param cells The number of cells.
	 * @param wumpus The wumpus.
	 */
	public void exit(Hunter hunter,List<String> perceptions, Integer cells,Wumpus wumpus) {
		if (hunter.getCol()==0 && hunter.getRow()==0 && hunter.isFindGold()) {
			System.out.println("Enhorabuena has ganado!!!");
			return;
		}
		
		System.out.println("Sales sin finalizar la partida. Bye");
		return;
	}
}
