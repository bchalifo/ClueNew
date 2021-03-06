package clueGame;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

import javax.swing.*;

public class Board extends JPanel {
	// constants
	private static final int MAX_NUM_ROWS = 30;
	private static final int MAX_NUM_COLUMNS = 30;
	public static final int CELL_WIDTH = 20;
	public static final int CELL_HEIGHT = 20;
	// instance variables
	private int numRows;
	private int numColumns;
	private BoardCell[][] board;
	private Map<Character, String> rooms;
	private Map<Player, BoardCell> playerLocations;
	private Map<BoardCell, LinkedList<BoardCell>> adjMtx;
	private Set<BoardCell> visited;
	private Set<BoardCell> targets;
	private String layoutFile;
	private String legendFile;
	private boolean displayTargets;

	// constructor with fields
	public Board(String layoutFile, String legendFile) {
		this.layoutFile = layoutFile;
		this.legendFile = legendFile;
		numRows = 0;
		numColumns = 0;
		// initialize containers
		board = new BoardCell[MAX_NUM_ROWS][MAX_NUM_COLUMNS];
		rooms = new HashMap<Character, String>();
		playerLocations = new HashMap<Player, BoardCell>();
		adjMtx = new HashMap<BoardCell, LinkedList<BoardCell>>();
		visited = new HashSet<BoardCell>();
		targets = new HashSet<BoardCell>();
		displayTargets = false;
	}

	// load board configuration
	public void loadBoardConfig() throws BadConfigFormatException {
		// load legend
		Scanner legend = loadLegend();
		while (legend.hasNextLine()) {
			String legendLine = legend.nextLine();
			if (legendLine.indexOf(',') > -1) {
				String temp[] = legendLine.split(", ");
				if (temp.length > 2 && temp[0].length() == 1) {
					throw new BadConfigFormatException("Bad legend file");
				}
				else {
					char key = temp[0].charAt(0);
					rooms.put(key, temp[1]);
				}

			}
			else {
				throw new BadConfigFormatException("Bad Legend file");
			}
		}
		legend.close();

		// Load board layout
		Scanner layout = loadLayout();
		while (layout.hasNextLine()) {
			// read in row
			String layoutLine = layout.nextLine();
			String temp[];
			if (layoutLine.indexOf(',') > -1) {
				temp = layoutLine.split(",");
				// get the numColumns from the first row
				if(numRows == 0) {
					numColumns = temp.length;
				}
				if (temp.length == numColumns) {
					// read each column of row
					for (int column = 0; column < numColumns; column++) {
						try {
							// determine cell type and add it to the board
							if (rooms.get(temp[column].charAt(0)).equalsIgnoreCase("walkway")) {
								board[numRows][column] = new WalkwayCell(numRows, column);
							}
							else {
								board[numRows][column] = new RoomCell(numRows, column, temp[column]);
							}
						}
						catch(NullPointerException e) {
							throw new BadConfigFormatException("Bad Layout file");
						}
					}
					numRows++;
				}
				else {
					throw new BadConfigFormatException("Bad Layout file");
				}
			}
			else {
				throw new BadConfigFormatException("Bad Layout file");
			}
		}
		layout.close();
	}

	// update player locations passed from game
	public void updatePlayerLocations(Map<Player, BoardCell> playerLocations) {
		this.playerLocations = playerLocations;
	}

	// creates a scanner for the layout file
	private Scanner loadLayout() {
		Scanner in = null;
		FileReader reader = null;
		try {
			reader = new FileReader(layoutFile);
			in = new Scanner(reader);
		}
		catch (FileNotFoundException e) {
			System.out.println(e.getLocalizedMessage());
			System.exit(0);
		}
		return in;
	}

	// creates a scanner for the legend file
	private Scanner loadLegend() {
		Scanner in = null;
		FileReader reader = null;
		try {
			reader = new FileReader(legendFile);
			in = new Scanner(reader);
		}
		catch (FileNotFoundException e) {
			System.out.println(e.getLocalizedMessage());
			System.exit(0);
		}
		return in;
	}

	// load the adjacency map for the board cells
	public void calcAdjacencies() {
		// stores the adjacencies
		LinkedList<BoardCell> tempList = new LinkedList<BoardCell>();
		// loop through each board cell
		for (int row = 0; row < numRows; row++) {
			for (int col = 0; col < numColumns; col++) {
				// adjacency right
				if (col + 1 < numColumns) {
					if (getCellAt(row, col + 1).isWalkway()) {
						tempList.add(getCellAt(row, col + 1));
					}
					else if (getRoomCellAt(row, col + 1).getDoorDirection() == RoomCell.DoorDirection.LEFT) {
						tempList.add(getRoomCellAt(row, col + 1));
					}
				}
				// adjacency down
				if (row + 1 < numRows) {
					if (getCellAt(row + 1, col).isWalkway()) {
						tempList.add(getCellAt(row + 1, col));
					}
					else if (getRoomCellAt(row + 1, col).getDoorDirection() == RoomCell.DoorDirection.UP) {
						tempList.add(getRoomCellAt(row + 1, col));
					}
				}
				// adjacency left
				if (col - 1 >= 0) {
					if (getCellAt(row, col - 1).isWalkway()) {
						tempList.add(getCellAt(row, col - 1));
					}
					else if (getRoomCellAt(row, col - 1).getDoorDirection() == RoomCell.DoorDirection.RIGHT) {
						tempList.add(getRoomCellAt(row, col - 1));
					}
				}
				// adjacency up
				if (row - 1 >= 0) {
					if (getCellAt(row - 1, col).isWalkway()) {
						tempList.add(getCellAt(row - 1, col));
					}
					else if (getRoomCellAt(row - 1, col).getDoorDirection() == RoomCell.DoorDirection.DOWN) {
						tempList.add(getRoomCellAt(row - 1, col));
					}
				}
				// if not walkway or doorway, do not add adjacencies
				if (!getCellAt(row, col).isWalkway()
						&& !getRoomCellAt(row, col).isDoorway()) {
					adjMtx.put(getCellAt(row, col), new LinkedList<BoardCell>());
				}
				// else add adjacencies
				else {
					adjMtx.put(getCellAt(row, col), new LinkedList<BoardCell>(tempList));
				}
				tempList.clear();
			}
		}
	}

	// calculate targets for given roll
	public void calcTargets(int row, int col, int moves) {
		targets.clear();
		visited.clear();
		BoardCell start = getCellAt(row, col);
		recursion(start, moves, start);
	}
	// recursive call for calculating targets
	private void recursion(BoardCell cell, int moves, BoardCell start) {		
		if (moves == 0 || (cell.isDoorway() && !start.isDoorway())) {
			if (!cell.equals(start)) {
				targets.add(cell);
			}
			visited.clear();
			visited.add(start);
			return;
		}
		visited.add(cell);
		for (BoardCell c : adjMtx.get(cell)) {
			if (!visited.contains(c)) {
				recursion(c, moves - 1, start);
			}
		}
	}
	
	// paint the board
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		// paint board cells
		for (int row = 0; row < numRows; row++) {
			for (int col = 0; col < numColumns; col++) {
				board[row][col].draw(g, this);
			}
		}
		for (int row = 0; row < numRows; row++) {
			for (int col = 0; col < numColumns; col++) {
				board[row][col].drawLabel(g, this);
			}
		}
		// paint players
		for (Player player : playerLocations.keySet()) {
			player.draw(g, playerLocations.get(player), this);
		}
		if(displayTargets) {
			for(BoardCell b : targets) {
				b.drawTarget(g, this);
			}
		}
	}
	
	
	
	// check to see if human player made a valid move
	public boolean checkValidity(){
		int row, col;  // x,y
		
		
		return false;
	}

	// get number of rows
	public int getNumRows() {
		return numRows;
	}

	// get number of columns
	public int getNumColumns() {
		return numColumns;
	}
	
	// get RoomCell
	public RoomCell getRoomCellAt(int row, int col) {
		if (board[row][col].isRoom()) {
			return (RoomCell) board[row][col];
		}
		return null;
	}

	// get BoardCell
	public BoardCell getCellAt(int row, int col) {
		return board[row][col];
	}

	// get Rooms
	public Map<Character, String> getRooms() {
		return rooms;
	}

	// get adjacency list
	public LinkedList<BoardCell> getAdjList(int row, int col) {
		return adjMtx.get(board[row][col]);
	}

	// get targets
	public Set<BoardCell> getTargets() {
		return targets;
	}
	
	public void displayTargets() {
		displayTargets = true;
	}
	
	public void removeTargets() {
		displayTargets = false;
	}

	@Override
	public String toString() {
		String sBoard = "";
		for (int r = 0; r < numRows; r++) {
			for (int c = 0; c < numColumns; c++) {
				sBoard += "[ " + board[r][c] + " ]\n ";
			}
		}
		return sBoard;
	}

	
}