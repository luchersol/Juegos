package TresEnRaya;

import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class TresEnRayaCustom {
	
	private static final int BOARD_SIZE = 10;
	private static final int TRACES_TO_WIN = 5;
    private static final char EMPTY_SQUARE = '-';
    private static final int MAX_TURNS = BOARD_SIZE * BOARD_SIZE;
    private char[][] board;
	private static char player;
	private static char winner;
	private static int turn;
	private static Scanner scan = new Scanner(System.in);
	
	public TresEnRayaCustom() {
		board = new char[BOARD_SIZE][BOARD_SIZE];
		winner = EMPTY_SQUARE;
		player = 'X';
		turn = 0;
	}
	
	public void initializeBoard() {
		for(int i = 0; i < BOARD_SIZE; i++) {
			for(int j = 0; j < BOARD_SIZE; j++) {
				board[i][j] = EMPTY_SQUARE;
			}
		}
	}
	
	public void printBoard() {
		for(int i = BOARD_SIZE - 1; i >= 0; i--) {
			System.out.printf("%3d |", i);
			for(int j = 0; j < BOARD_SIZE; j++) {
				System.out.printf("%s ",board[i][j]);
			}
			System.out.println();
		}
		System.out.printf("     %s\n     ", "_ ".repeat(BOARD_SIZE));
		for(int k = 0; k < BOARD_SIZE; k++) {
			System.out.printf("%d ", k);
		}
		System.out.println();
	}
	
	public boolean isValidMovement(int i, int j) {
		return i >= 0 && i < BOARD_SIZE 
				&& j >= 0 && j < BOARD_SIZE 
				&& board[i][j] == EMPTY_SQUARE;
	}
	
	public int[] introducePosition() throws Exception {
		int[] res = new int[2];
		List<String> patterns = List.of("\\d \\d", "\\d,\\d", "\\d:\\d", "\\d-\\d");
		String data = scan.nextLine();
		String formatt = "";
		
		for(String pattern: patterns) {
			if(Pattern.matches(pattern, data)) {
				formatt = pattern;
				break;
			}
		}
		
		String sep = switch(formatt) {
			case "\\d \\d" -> " ";
			case "\\d,\\d" -> ",";
			case "\\d:\\d" -> ":"; 
			case "\\d-\\d" -> "-";
			default -> throw new Exception("Se debe de introducir un formato correcto");
		};
		
		String[] move = data.split(sep);
		res[0] = Integer.valueOf(move[0]); //columna
		res[1] = Integer.valueOf(move[1]); //fila
		return res;
	}
	
	public void putTrace() throws Exception {
		do {
			System.out.printf("Posicion de movimineto %s: ", player);
			int[] move = introducePosition();
			int i = move[1], // fila
				j = move[0]; // column
			if(isValidMovement(i, j)) {
				board[i][j] = player;
				break;
			}
			else System.out.printf("No se puede poner en la posicion [%d,%d], intente de nuevo\n", j, i);
		} while (true);
		
	}
	
	public void isWinner() {
		if(somaTraceWinner()) {
			winner = player;
			System.out.println("El gamador es " + winner);
		}
	}
	
	public void play() throws Exception {
		while(winner == EMPTY_SQUARE && MAX_TURNS != turn) {
			putTrace();
			printBoard();
			isWinner();
			nextPlayer();
			turn++;
		}
		if(MAX_TURNS == turn) System.out.println("EMPATE");
		
	}
	
	public void nextPlayer() {
		player = player == 'X' ? 'O' : 'X';
	}
	
	public boolean somaTraceWinner() {
		return countTracesInRow()
				|| countTracesInColumn()
				|| countTracesInPrimaryDiagonal()
				|| countTracesInSecondaryDiagonal();
	}
	
	public boolean countTracesInRow() {
		int cont;
		boolean flat = false;
bucle:	for(int i = 0; i < BOARD_SIZE; i++) {
			cont = 0;
			for(int j = 0; j < BOARD_SIZE; j++) {
				if(board[i][j] == player) {
					cont++;
					if(cont == TRACES_TO_WIN) {
						flat = true;
						break bucle;
					}
				} else {
					break;
				}
			}
		}
		if(flat) System.out.println("GANA POR LINEA");
		return flat;
	}
	
	public boolean countTracesInColumn() {
		int cont;
		boolean flat = false;
bucle:	for(int j = 0; j < BOARD_SIZE; j++) {
			cont = 0;
			for(int i = 0; i < BOARD_SIZE; i++) {
				if(board[i][j] == player) {
					cont++;
					if(cont == TRACES_TO_WIN) {
						flat = true;
						break bucle;
					}
				} else {
					break;
				}
			}
		}
	if(flat) System.out.println("GANA POR COLUMNA");
		return flat;
	}
	
	public boolean countTracesInSecondaryDiagonal() {
		boolean flat = false;
bucle:	for(int i = 0; i < BOARD_SIZE - TRACES_TO_WIN + 1; i++) {
			for(int j = 0; j < BOARD_SIZE - TRACES_TO_WIN + 1; j++) {
				if(board[i][j] == player) {
					int cont = 1;
					for(int k = 1; k < TRACES_TO_WIN; k++) {
						if(board[i+k][j+k] == player) {
							cont++;
							if(cont == TRACES_TO_WIN) {
								flat = true;
								break bucle;
							}
						} else {
							break;
						}
					}
				}
			}
		}
	if(flat) System.out.println("GANA POR DIAGONAL SECUNDARIA");
		return flat;
	}
	
	public boolean countTracesInPrimaryDiagonal() {
		boolean flat = false;
bucle:	for(int i = 0; i < BOARD_SIZE - TRACES_TO_WIN + 1; i++) {
			for(int j = TRACES_TO_WIN - 1; j < BOARD_SIZE ; j++) {
				if(board[i][j] == player) {
					int cont = 1;
					for(int k = 1; k < TRACES_TO_WIN; k++) {
						if(board[i+k][j-k] == player) {
							cont++;
							if(cont == TRACES_TO_WIN) {
							 	flat = true;
								break bucle;
							}
						} else {
							break;
						}
					}
				}
			}
		}
	if(flat) System.out.println("GANA POR DIAGONAL PRINCIPAL");
		return flat;
	}
	
	public static void main(String[] args) throws Exception {
		TresEnRayaCustom game = new TresEnRayaCustom();
		game.initializeBoard();
		game.printBoard();
		game.play();
	}
}
