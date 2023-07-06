package Ajedrez;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import Utils.List2;

public class ChessGame {
	
	private record Position(int row, int column){
		public static Position of(int row, int column) {
			return new Position(row, column);
		}
	}
	private static final List<String> WHITE_PIECES = List.of("R","N","B","Q","K","P");
	private static final List<String> BLACK_PIECES = List.of("r","n","b","q","k","p");
    private static final int BOARD_SIZE = 8;
    private static final String EMPTY_SQUARE = "-";
    private static String player = "W";
    private String[][] board;
	private Scanner scan;

    public ChessGame() {
        board = new String[BOARD_SIZE][BOARD_SIZE];
        initializeBoard();
    }

    private void initializeBoard() {
        // Inicializa el tablero con las piezas en sus posiciones iniciales
        // Utiliza convenciones de notación algebraica para identificar las piezas:
        // "R" para las torres, "N" para los caballos, "B" para los alfiles, "Q" para la reina, "K" para el rey, "P" para los peones.
        // Utiliza mayúsculas para las piezas blancas y minúsculas para las piezas negras.
        // Utiliza "-" para los espacios vacíos.
    	// Inicializar piezas blancas
        board[0][0] = "R";
        board[0][1] = "N";
        board[0][2] = "B";
        board[0][3] = "Q";
        board[0][4] = "K";
        board[0][5] = "B";
        board[0][6] = "N";
        board[0][7] = "R";

        for (int col = 0; col < BOARD_SIZE; col++) {
            board[1][col] = "P";
        }

        // Inicializar espacios vacíos
        for (int row = 2; row < 6; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                board[row][col] = EMPTY_SQUARE;
            }
        }

        // Inicializar piezas negras
        for (int col = 0; col < BOARD_SIZE; col++) {
            board[6][col] = "p";
        }

        board[7][0] = "r";
        board[7][1] = "n";
        board[7][2] = "b";
        board[7][3] = "q";
        board[7][4] = "k";
        board[7][5] = "b";
        board[7][6] = "n";
        board[7][7] = "r";
    }

    public void printBoard() {
        // Imprime el estado actual del tablero
        for (int row = 0; row < BOARD_SIZE; row++) {
        	System.out.print((BOARD_SIZE - row) + " | ");
            for (int col = 0; col < BOARD_SIZE; col++) {
                System.out.print(board[row][col] + " ");
            }
            System.out.println();
        }
        System.out.println("    _ _ _ _ _ _ _ _");
        System.out.println("    a b c d e f g h\n");
    }
    
    public int traductRow(char c) {
    	return switch (c) {
			case '1' -> 7;
			case '2' -> 6;
			case '3' -> 5;
			case '4' -> 4;
			case '5' -> 3;
			case '6' -> 2;
			case '7' -> 1;
			case '8' -> 0;
			default -> -1;
    	};
    }
    
    public int traductColumn(char c) {
    	return switch (c) {
			case 'a' -> 0;
			case 'b' -> 1;
			case 'c' -> 2;
			case 'd' -> 3;
			case 'e' -> 4;
			case 'f' -> 5;
			case 'g' -> 6;
			case 'h' -> 7;
			default -> -1;
		};
    }
    
    public Position traductPosition(String position) {
    	int col = traductColumn(position.charAt(0));
    	int row = traductRow(position.charAt(1));
    	return Position.of(row, col);
    }
    
    public boolean isValidPosition(Position position, String color) {    	
    	return position.row >= 0 && position.row < BOARD_SIZE
    			&& position.column >= 0 && position.column < BOARD_SIZE
    			&& !getColor(position).equals(color);
    }
    
    public boolean isValidMove(Position source, Position destination) {
        // Valida si un movimiento es válido en base a las reglas del ajedrez
        // El parámetro 'move' debe estar en notación algebraica, por ejemplo: "e2 e4"
        // Retorna true si el movimiento es válido, false en caso contrario
    	String chessPiece = board[source.row][source.column];
    	String color = getColor(source);
    	if(color.equals(player)) {
    		if(isValidPosition(source, EMPTY_SQUARE) && isValidPosition(destination, color)){
        		List<Position> moves = switch (chessPiece.toUpperCase()) {
						    				case "P" -> getPawnMoves(source, color);
						    				case "R" -> getRookMoves(source, color);
						    				case "N" -> getKnightMoves(source, color);
						    				case "B" -> getBishopMoves(source, color);
						    				case "Q" -> getQueenMoves(source, color);
						    				case "K" -> getKingMoves(source, color);
						    				default -> throw new IllegalArgumentException("Unexpected value: " + chessPiece.toUpperCase());
						        		};
        		return moves.contains(destination);
    		}
    		System.out.println("Posicionamiento no valido");
    	}
    	System.out.println("No puedes tocar piezas de otro jugador");
    	return false;
    }
    
    
    public String getColor(Position position) {
    	String chessPiece = board[position.row][position.column];
    	String color = EMPTY_SQUARE;
    	if(BLACK_PIECES.contains(chessPiece)) {
    		color = "B";
    	} else if(WHITE_PIECES.contains(chessPiece)) {
    		color = "W";
    	} 
    	return color;
    }
    
    public String getPiece(Position position) {
    	return board[position.row][position.column];
    }
    
    public void promotePawn(Position position) {
    	System.out.println("Peón puede ser promovido, elige:\n1.Torre\t2.Caballo\t3.Alfil\tOtro.Reina");
    	switch (scan.nextInt()) {
			case 1 -> board[position.row][position.column] = getColor(position).equals("W") ? "R" : "r";
			case 2 -> board[position.row][position.column] = getColor(position).equals("W") ? "N" : "n";
			case 3 -> board[position.row][position.column] = getColor(position).equals("W") ? "B" : "b";
			default -> board[position.row][position.column] = getColor(position).equals("W") ? "Q" : "q";
		}
    }

    //Movimientos del peón
    public List<Position> getPawnMoves(Position source, String color) {
    	List<Position> positions = new ArrayList<>();
    	
    	addPawnMovements(positions, source, color);
    	
    	return positions.stream()
    			.filter(p -> isValidPosition(p, color))
    			.collect(Collectors.toList());
    }
    
    public void addPawnMovements(List<Position> positions, Position source, String color) {
    	Integer op = color.equals("W") ? 1 : -1;
    	Integer firstRow = color.equals("W") ? 1 : 6;
    	Boolean firstMovement = source.row == firstRow;
    	Boolean freeFront = getPiece(Position.of(source.row + op, source.column)).equals(EMPTY_SQUARE);
		Boolean eatDiagonalRight = isValidPosition(Position.of(source.row + op, source.column + 1), "") && !getColor(Position.of(source.row + op, source.column + 1)).equals(color);
		Boolean eatDiagonalLeft = isValidPosition(Position.of(source.row + op, source.column - 1), "") && !getColor(Position.of(source.row + op, source.column - 1)).equals(color);
		if(freeFront) positions.add(Position.of(source.row + op, source.column));
		if(firstMovement) positions.add(Position.of(source.row + 2*op, source.column));
		if(eatDiagonalRight) positions.add(Position.of(source.row + op, source.column + 1));
		if(eatDiagonalLeft) positions.add(Position.of(source.row + op, source.column - 1));
    }
    
    //Movimientos del torre
    public List<Position> getRookMoves(Position source, String color) {
    	List<Position> positions = new ArrayList<>();
    	
    	addLinealMovements(positions, source, color, "row", "right");
    	addLinealMovements(positions, source, color, "row", "left");
    	addLinealMovements(positions, source, color, "column", "down");
    	addLinealMovements(positions, source, color, "column", "up");
    	
    	return positions.stream()
    			.filter(p -> isValidPosition(p, color))
    			.collect(Collectors.toList());
    }
    
    public void addLinealMovements(List<Position> positions, Position source, String color, String pos, String strOp) {
    	Predicate<Integer> cond = i -> pos.equals("row") ? 
    										strOp.equals("right")?
							    					i < BOARD_SIZE 
							    					: i >= 0
							    			: strOp.equals("down") ? 
							    					i < BOARD_SIZE
							    					: i >= 0;
		Integer initialCord = pos.equals("row") ? source.column : source.row;
    	Integer op = strOp.equals("right") || strOp.equals("down") ? 1 : -1;
    	for(int i = initialCord + op; cond.test(i); i+=op) {
    		Position p = pos.equals("row") ? Position.of(source.row, i) : Position.of(i, source.column);
    		String colorDest = getColor(p);
    		if(colorDest.equals(EMPTY_SQUARE)) {
    			positions.add(p);
    		} else {
    			if(!colorDest.equals(color)) {
    				positions.add(p);
        		}
        		break;
    		}
    	}
    }
    
    //Movimientos del alfil
    public List<Position> getBishopMoves(Position source, String color) {
    	List<Position> positions = new ArrayList<>();
    	
    	addDiagonalMovements(positions, source, color, "right", "up");
    	addDiagonalMovements(positions, source, color, "right", "down");
    	addDiagonalMovements(positions, source, color, "left", "up");
    	addDiagonalMovements(positions, source, color, "left", "down");
    	
    	return positions.stream()
    			.filter(p -> isValidPosition(p, color))
    			.collect(Collectors.toList());
    }
    
    public void addDiagonalMovements(List<Position> positions, Position source, String color, String dirRow, String dirColumn) {
    	Predicate<Integer> condRow = row -> dirRow.equals("right") ? row < BOARD_SIZE : row >= 0;
    	Predicate<Integer> condColumn = col -> dirColumn.equals("down") ? col < BOARD_SIZE : col >= 0;
    	Integer opRow = dirRow.equals("right")? 1 : -1;
    	Integer opColumn = dirColumn.equals("down")? 1 : -1;
    	
    	for(int row = source.row + opRow; condRow.test(row); row += opRow) {
    		for(int col = source.column + opColumn; condColumn.test(col); col += opColumn) {
	    		Position p = Position.of(row, col);
	    		if(isValidPosition(p, "")) {
	    			String colorDest = getColor(p);
	        		if(colorDest.equals(EMPTY_SQUARE)) {
	        			positions.add(p);
	        		} else {
	        			if(!colorDest.equals(color)) {
	        				positions.add(p);
	        			}
	        			break;
	        		}
	    		}
    		}
    	}
    }
    
    //Movimientos del caballo
    public List<Position> getKnightMoves(Position source, String color) {
    	List<Position> posiciones = new ArrayList<>();
    	posiciones.add(Position.of(source.row + 2, source.column + 1));
    	posiciones.add(Position.of(source.row + 2, source.column - 1));
    	posiciones.add(Position.of(source.row - 2, source.column + 1));
    	posiciones.add(Position.of(source.row - 2, source.column - 1));
    	posiciones.add(Position.of(source.row + 1, source.column + 2));
    	posiciones.add(Position.of(source.row + 1, source.column - 2));
    	posiciones.add(Position.of(source.row - 1, source.column + 2));
    	posiciones.add(Position.of(source.row - 1, source.column - 2));
    	return posiciones.stream()
    			.filter(p -> isValidPosition(p, color))
    			.collect(Collectors.toList());
    }
    
    //Movimientos del rey
    public List<Position> getKingMoves(Position source, String color) {
    	List<Position> posiciones = new ArrayList<>();
    	posiciones.add(Position.of(source.row - 1, source.column));
    	posiciones.add(Position.of(source.row - 1, source.column + 1));
    	posiciones.add(Position.of(source.row - 1, source.column - 1));
    	posiciones.add(Position.of(source.row + 1, source.column));
    	posiciones.add(Position.of(source.row + 1, source.column + 1));
    	posiciones.add(Position.of(source.row + 1, source.column - 1));
    	posiciones.add(Position.of(source.row, source.column + 1));
    	posiciones.add(Position.of(source.row, source.column - 1));
    	
    	return posiciones.stream()
    			.filter(p -> isValidPosition(p, color))
    			.collect(Collectors.toList());
    }
    
    //Movimientos del reina
    public List<Position> getQueenMoves(Position source, String color) {
    	return List2.union(getBishopMoves(source, color), getRookMoves(source, color));
    }

    public void makeMove(String move) {
        // Realiza un movimiento en el tablero
        // El parámetro 'move' debe estar en notación algebraica, por ejemplo: "e2 e4"
        // Actualiza el estado del tablero con el movimiento realizado
    	String[] positions = move.split(" ");
    	Position source = traductPosition(positions[0]),
    							destination = traductPosition(positions[1]);
    	if(isValidMove(source, destination)) {
    		board[destination.row][destination.column] = getPiece(source);
    		Boolean pawnToPromote = getPiece(source).toUpperCase().equals("P") && (getColor(source).equals("W") ? 7 : 0) == destination.row;
    		if(pawnToPromote) promotePawn(destination);
        	board[source.row][source.column] = EMPTY_SQUARE; 
        	player = player.equals("W") ? "B": "W";
    	}
    	
    }
    
    public void play() {
    	while(true) {
    		System.out.println(String.format("TURNO DE %s, elige movimiento:", player));
    		scan = new Scanner(System.in);
    		String move = scan.nextLine();
    		makeMove(move);
    		printBoard();
    	}
    }

    public static void main(String[] args) {
        ChessGame game = new ChessGame();
        game.printBoard();
        game.play();
    }
}
