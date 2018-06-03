/**
 * 
 */
package es.wumpus.main;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import es.wumpus.actions.HunterActions;
import es.wumpus.entities.Characters;
import es.wumpus.entities.Gold;
import es.wumpus.entities.Hole;
import es.wumpus.entities.Hunter;
import es.wumpus.entities.Wumpus;

/**
 * @author Ismael Pérez Santos
 *
 */
public class Main {
	
	private static final Integer ROWINI=0;
	private static final Integer COLINI=0;
	private static final Integer FREE=0;
	private static final Integer BUSY=1;

	/**
	 * @param args
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 */
	public static void main(String[] args) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		// Initialize the variables.
		Scanner lector=new Scanner(System.in);
		String readLine;
		final Integer arrows;
		final Integer holes;
		final Integer cells;
		final Integer maxHolds;

		// Initialize the characters.
		Hunter hunter=new Hunter();
		Wumpus wumpus=new Wumpus();
		Gold gold=new Gold();
		List<Hole> holesList=new ArrayList<>();
		
		//Initialize the positions.
		Characters [][] positions;
			
		System.out.println("Bienvenido al juego de WUMPUS");
		System.out.println("Para empiezar configuremos la partida:");
		
		System.out.println("\t+ Introduzca el número de celdas del juego:");
		readLine=lector.nextLine();
		while (!isNumeric(readLine) || Integer.parseInt(readLine)<0 ) {
			System.out.println("\t+ Debe introducir un número. Por favor, introduzca el número de celdas:");
			readLine=lector.nextLine();
		}
		
		cells=Integer.parseInt(readLine);
		positions=new Characters [cells][cells];
		
		System.out.println("\t+ Introduzca el número de flechas del cazador:");
		readLine=lector.nextLine();
		while (!isNumeric(readLine) || Integer.parseInt(readLine)<0) {
			System.out.println("\t+ Debe introducir un número. Por favor, introduzca el número de flechas del cazador:");
			readLine=lector.nextLine();
		}
		
		arrows=Integer.parseInt(readLine);
		
		maxHolds=(cells*cells)-3;
		System.out.println("\t+ Introduzca el número de agujeros (mínimo 0, máximo "+maxHolds+"):");
		readLine=lector.nextLine();
		while (!isNumeric(readLine) || Integer.parseInt(readLine)>maxHolds || Integer.parseInt(readLine)<0) {
			System.out.println("\t+ Debe introducir un número. Por favor, introduzca el número de agujeros (mínimo 0, máximo "+maxHolds+"):");
			readLine=lector.nextLine();
		}
		
		holes=Integer.parseInt(readLine);
		
		inicialiceCharacters(hunter,wumpus,gold,holesList,arrows,cells,holes,positions);
		
		startGame(hunter,wumpus,gold,holesList,cells,lector,positions);
		
		lector.close();
		
	}
	
	/**
	 * The start game.
	 * 
	 * @param hunter The hunter.
	 * @param wumpus The wumpus.
	 * @param gold The gold.
	 * @param holesList The holes list.
	 * @param cells The number of cells.
	 * @param lector The lector of line.
	 * @param positions The positions vector.
	 * @throws NoSuchMethodException The exceptions.
	 * @throws SecurityException The exceptions.
	 * @throws IllegalAccessException The exceptions.
	 * @throws IllegalArgumentException The exceptions.
	 * @throws InvocationTargetException The exceptions.
	 */
	private static void startGame(Hunter hunter,Wumpus wumpus,Gold gold,List<Hole> holesList,Integer cells,Scanner lector,Characters [][] positions) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		String action=null;
		
		final HashMap<String,String> actions=new HashMap<>();
		actions.put("q", "up");
		actions.put("o", "left");
		actions.put("p", "right");
		actions.put(" ", "shoot");
		actions.put("s","exit");
		
		final HashMap<Integer,String> directions=new HashMap<>();
		directions.put(1, ">");
		directions.put(2, "^");
		directions.put(3, "<");
		directions.put(4, "v");
		
		String readLine;
		
		HunterActions hunterActions=new HunterActions();
		
		List<String> perceptions=new ArrayList<>();
	
		System.out.println("Comencemos la partida");
		messages();
		
		// Print the table.
		printTable(hunter,positions,directions);
		
		// Check the perceptions of movement.
		checkPerceptions(hunter.getRow(),hunter.getCol(),positions,perceptions);
		
		printPerceptions(perceptions);
		
		System.out.println("Introduzca su primer movimiento:");
		readLine=lector.nextLine();
		
		// While not exit and hunter is live.
		while (!"s".equals(action) && hunter.isLive()) {

		// Validate the movement.
		action=validateMovement(readLine,actions,lector);
		
		Method method=HunterActions.class.getMethod(actions.get(action),Hunter.class,List.class, Integer.class,Wumpus.class);
		
		method.invoke(hunterActions, hunter,perceptions,cells,wumpus);
		
		// Check the movement whit other characters. 
		checkCharacter(hunter,positions,perceptions);
		
		// Check the perceptions of movement.
		checkPerceptions(hunter.getRow(),hunter.getCol(),positions,perceptions);
		
		if ("s".equals(action) || !hunter.isLive()) {
			return;
		}
		
		// Print the table.
		printTable(hunter,positions,directions);
		
		printPerceptions(perceptions);
		
		System.out.println("Introduzca un movimiento:");
		messages();
		
		readLine=lector.nextLine();
		}
	}
	
	/**
	 * Check the perfections around the hunter.
	 * 
	 * @param row The row of hunter position.
	 * @param col The column of hunter position.
	 * @param positions The positions vector.
	 * @param perceptions The perceptions.
	 */
	private static void checkPerceptions(Integer row,Integer col,Characters [][] positions,List<String> perceptions) {
		if (row<positions.length-1) {
		checkPerception(row+1,col,positions,perceptions);
		}
		if (row>0) {
		checkPerception(row-1,col,positions,perceptions);
		}
		
		if (col<positions.length-1) {
		checkPerception(row,col+1,positions,perceptions);
		}
		if (col>0) {
		checkPerception(row,col-1,positions,perceptions);
		}
	}
	
	/**
	 * Check perception.
	 * 
	 * @param row The row of perception.
	 * @param col The column of perception.
	 * @param positions The position vector.
	 * @param perceptions The perceptions.
	 */
	private static void checkPerception(Integer row,Integer col,Characters [][] positions,List<String> perceptions) {
		Characters character=positions[row][col];
		if (character!=null) {
			if (character instanceof Hole) {
				perceptions.add("Se nota la brisa de un agujero negro...");
			}
			if (character instanceof Wumpus) {
				perceptions.add("Se nota el hedor del wumpus...");
			}
		}
	}
	
	/**
	 * Check if the hunter is in other character cell.
	 * 
	 * @param hunter The hunter.
	 * @param positions The position vector.
	 * @param perceptions The perceptions.
	 */
	private static void checkCharacter(Hunter hunter,Characters [][] positions,List<String> perceptions) {
		Characters character=positions[hunter.getRow()][hunter.getCol()];
		if (character!=null) {
			if (character instanceof Gold) {
				if (!hunter.isFindGold()) {
				hunter.setFindGold(Boolean.TRUE);
				perceptions.add("Has encontrado el oro!!! Vuelve a la casilla de salida para salir.");
				}
				return;
			}
			if (character instanceof Hole) {
				System.out.println("Lo siento, pero has caido en un agujero y has perdido.");
				hunter.setLive(Boolean.FALSE);
				return;
			}
			if (character instanceof Wumpus) {
				if (((Wumpus) character).isState()){
					System.out.println("Lo siento, pero has caido en la trampa del Wumpus y has perdido.");
					hunter.setLive(Boolean.FALSE);
					return;
				}
				perceptions.add("Estas en la casa del Wumpus, menos mal que lo has matado.");
			}
		}
	}
	
	/**
	 * The messages to movements options.
	 */
	private static void messages() {
		System.out.println("Las teclas de movimientos son:");
		System.out.println("\t+ Avanzar: q");
		System.out.println("\t+ Izquierda: o");
		System.out.println("\t+ Derecha: p");
		System.out.println("\t+ Salir: s");
		System.out.println("\t+ Lanzar flecha: [Barra espaciadora]");
	}
	
	/**
	 * To print the table.
	 * 
	 * @param hunter The hunter.
	 * @param positions The position vector.
	 * @param directions The directions map.
	 */
	private static void printTable(Hunter hunter, Characters [][] positions,HashMap<Integer,String> directions) {
		StringBuffer buffer=new StringBuffer();;
		buffer.append("(==)");
		// Print rows.
		for (Integer i=0;i<positions.length;i++) {
			buffer.append("(==)(==)");
		}
		System.out.println(buffer);
		//Print columns.
		for (Integer j=positions.length;j>0;j--) {
			buffer=new StringBuffer();
			buffer.append("(==)");
				for (Integer i=0;i<positions.length;i++) {
					if (j-1==hunter.getRow() && i==hunter.getCol()){
						buffer.append("(H"+directions.get(hunter.getDirection())+")(==)");
					}else {
					buffer.append("(  )(==)");
					}
				}
			System.out.println(buffer);
			buffer=new StringBuffer();
			buffer.append("(==)");
			for (Integer i=0;i<positions.length;i++) {
					buffer.append("(==)(==)");
			}
			System.out.println(buffer);
			}
		System.out.println("     |");
		System.out.println("    EXIT");
	}
	
	/**
	 * Print the perceptions.
	 * 
	 * @param perceptions The perceptions.
	 */
	private static void printPerceptions(List<String> perceptions) {
		if (!perceptions.isEmpty()) {
			System.out.println("Percepciones recibidad:\n");
			perceptions.forEach(perception->System.out.println(perception));
			perceptions.clear();
			System.out.println("\n");
		}

	}
	
	/**
	 * Validate the movements of hunter.
	 * 
	 * @param readLine The movement.
	 * @param actions The movement actions map.
	 * @param lector The lector to line.
	 * 
	 * @return The action.
	 */
	private static String validateMovement(String readLine,HashMap<String,String> actions,Scanner lector) {
		while (!actions.containsKey(readLine)) {
			System.out.println("El movimiento introducido no existe vuelva a intentarlo.");
			System.out.println("Las teclas de movimientos son:");
			System.out.println("\t+ Avanzar: q");
			System.out.println("\t+ Izquierda: o");
			System.out.println("\t+ Derecha: p");
			System.out.println("\t+ Salir: s");
			System.out.println("\t+ Lanzar flecha: [Barra espaciadora]");
			System.out.println("Introduzca un movimiento:");
			readLine=lector.nextLine();
		}
		
		return readLine;
	}
	
	/**
	 * Initialize the characters of the game.
	 * 
	 * @param hunter The hunter.
	 * @param wumpus The wumpus.
	 * @param gold The gold.
	 * @param holesList The hole list.
	 * @param arrows The arrows.
	 * @param cells The number of cells.
	 * @param holes The number of holes.
	 * @param positions The position vector.
	 */
	private static void inicialiceCharacters(Hunter hunter,Wumpus wumpus,Gold gold,List<Hole> holesList,Integer arrows,Integer cells,Integer holes,Characters [][] positions) {
		hunter.setCol(COLINI);
		hunter.setArrows(arrows);
		hunter.setDirection(1);
		hunter.setFindGold(Boolean.FALSE);
		hunter.setRow(ROWINI);
		hunter.setLive(Boolean.TRUE);
		
		positions[ROWINI][COLINI]=hunter;

		setPositions(wumpus, cells,positions);
		wumpus.setState(Boolean.TRUE);

		setPositions(gold, cells,positions);
		
		for (Integer i=0;i<holes;i++) {
			Hole hole=new Hole();
			setPositions(hole, cells,positions);
			holesList.add(hole);
		}
	}
	
	/**
	 * Set the position of a character.
	 * 
	 * @param character The character.
	 * @param cells The number of cells.
	 * @param positions The position vector.
	 */
	private static void setPositions(Characters character,Integer cells, Characters [][] positions) {
		Integer row;
		Integer col;
		Boolean find=Boolean.FALSE;
		Random rnd=new Random();
		while (Boolean.FALSE.equals(find)) {
			row=rnd.nextInt(cells);
			col=rnd.nextInt(cells);
			Characters chara=positions[row][col];
			if (chara==null) {
				character.setRow(row);
				character.setCol(col);
				positions[row][col]=character;
				return;
			}
		}
	}
	
	/**
	 * Check if the string is a numeric.
	 * 
	 * @param string The string.
	 * @return It's a numeric?.
	 */
	private static Boolean isNumeric(String string) {
		try {
			Integer.parseInt(string);
			return Boolean.TRUE;
		}catch (NumberFormatException excepcion){
			return Boolean.FALSE;
		}
	}
}
