package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class Board {
	// instance variables
	private int numRows;
	private int numColumns;
	private BoardCell[][] board;
	private Map<Character, String> rooms;
	private Map<BoardCell, LinkedList<BoardCell>> adjMtx;
	private Set<BoardCell> visited;
	private Set<BoardCell> targets;
	private String layoutFile;
	private String legendFile;

	// constructor with fields
	public Board(String layoutFile, String legendFile, int rows, int cols) {
		this.layoutFile = layoutFile;
		this.legendFile = legendFile;
		setSize(rows, cols);
		// init containers
		board = new BoardCell[numRows][numColumns];
		rooms = new HashMap<Character, String>();
		adjMtx = new HashMap<BoardCell, LinkedList<BoardCell>>();
		visited = new HashSet<BoardCell>();
		targets = new HashSet<BoardCell>();
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
		int row = 0;
		while (layout.hasNextLine()) {
			// read in row
			String layoutLine = layout.nextLine();
			String temp[];
			if (layoutLine.indexOf(',') > -1) {
				temp = layoutLine.split(",");
				if (temp.length == numColumns) {
					// read each column of row
					for (int column = 0; column < temp.length; column++) {
						// *****NEEDS FIXING**** //
						board[row][column] = new RoomCell(row, column, temp[column]);
						try {
							if (rooms.get(temp[column].charAt(0)).equalsIgnoreCase("walkway")) {
								// NOOOO
								((RoomCell) board[row][column]).makeWalkway();
							}
						}
						// ********************* //
						catch(NullPointerException e) {
							throw new BadConfigFormatException("Bad Layout file");
						}
					}
					row++;
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
		LinkedList<BoardCell> tempList = new LinkedList<BoardCell>();
		for (int r = 0; r < numRows; r++) {
			for (int c = 0; c < numColumns; c++) {
				if (c + 1 < numColumns) {
					if (getRoomCellAt(r, c + 1).isWalkway()) {
						tempList.add(getRoomCellAt(r, c + 1));
					}
					else if (getRoomCellAt(r, c + 1).getDoorDirection() == RoomCell.DoorDirection.LEFT) {
						tempList.add(getRoomCellAt(r, c + 1));
					}
				}
				if (r + 1 < numRows) {
					if (getRoomCellAt(r + 1, c).isWalkway()) {
						tempList.add(getRoomCellAt(r + 1, c));
					}
					else if (getRoomCellAt(r + 1, c).getDoorDirection() == RoomCell.DoorDirection.UP) {
						tempList.add(getRoomCellAt(r + 1, c));
					}
				}
				if (c - 1 >= 0) {
					if (getRoomCellAt(r, c - 1).isWalkway()) {
						tempList.add(getRoomCellAt(r, c - 1));
					}
					else if (getRoomCellAt(r, c - 1).getDoorDirection() == RoomCell.DoorDirection.RIGHT) {
						tempList.add(getRoomCellAt(r, c - 1));
					}
				}
				if (r - 1 >= 0) {
					if (getRoomCellAt(r - 1, c).isWalkway()) {
						tempList.add(getRoomCellAt(r - 1, c));
					}
					else if (getRoomCellAt(r - 1, c).getDoorDirection() == RoomCell.DoorDirection.DOWN) {
						tempList.add(getRoomCellAt(r - 1, c));
					}
				}

				if (!getRoomCellAt(r, c).isWalkway()
						&& !getRoomCellAt(r, c).isDoorway()) {
					adjMtx.put(getCellAt(r, c), new LinkedList<BoardCell>());
				}
				else {
					adjMtx.put(getCellAt(r, c), new LinkedList<BoardCell>(tempList));
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

	// Get Rooms
	public Map<Character, String> getRooms() {
		return rooms;
	}

	public int getNumRows() {
		return numRows;
	}

	public int getNumColumns() {
		return numColumns;
	}

	// Get RoomCell
	public RoomCell getRoomCellAt(int row, int col) {
		if (board[row][col].isRoom()) {
			return (RoomCell) board[row][col];
		}
		return null;
	}

	// Get BoardCell
	public BoardCell getCellAt(int row, int col) {
		return board[row][col];
	}

	// Get targets
	public Set<BoardCell> getTargets() {
		return targets;
	}

	// Get adjacency list
	public LinkedList<BoardCell> getAdjList(int row, int col) {
		return adjMtx.get(board[row][col]);
	}

	private void setSize(int rows, int cols) {
		numRows = rows;
		numColumns = cols;

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