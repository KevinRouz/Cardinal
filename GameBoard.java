import java.util.*;
import static java.lang.System.out;

public class GameBoard
{
    //--- Instance Variables ("traits") ---//
    // Holds list of players
    private ArrayList<Player> players;

    // 2D Array of Points representing master gameboard for all players
    private Point[][] gameboard;

    // Sets max length & width of master gameboard
    private static final int SIZE = 20;
    
    //--- Constructor ---//
    public GameBoard(int numPlayers)
    {
        players = new ArrayList<>();
        
        // Adjusts shape of the master gameboard to the number of players (3-player looks like an upside-down L)
        if(numPlayers == 2)
        {
            gameboard = new Point[SIZE/2][SIZE];
        }
        else if(numPlayers == 3)
        {
            gameboard = new Point[SIZE][];
            
            for(int r = 0; r < gameboard.length; r++)
            {
                if(r < SIZE/2)
                {
                    gameboard[r] = new Point[SIZE];
                }
                else
                {
                    gameboard[r] = new Point[SIZE/2];
                }
            }
        }
        else
        {
            gameboard = new Point[SIZE][SIZE];
        }
    }
    
    //--- Getters ---//
    public ArrayList<Player> getPlayers()
    {
        return players;
    }

    public Point[][] getGameBoard()
    {
        return gameboard;
    }

    //--- Setters ---//
    // Change player name (not used, but available if needed)
    public void changePlayerName(int playerNum)
    {
        Scanner in = new Scanner(System.in);
        String name;
        
        // Checks for valid player number. Can't be < 1 or greater than the number of players.
        if(playerNum < 1 || playerNum > players.size())
        {
            out.println("Invalid player number. Canceling player change.");
        }
        else
        {
            out.println("Enter new name for player " + playerNum);
            name = in.nextLine();
            // Searches Player ArrayList for the player of the given number and changes their name
            for(Player p : players)
            {
                if(playerNum == p.getPlayerNum())
                {
                    Player changedPlayer = p;
                    out.println("P" + playerNum + ": " + changedPlayer.getPlayerName() + " --> " + name);
                    changedPlayer.setPlayerName(name);
                    break;
                }
            }
        }
        
    }

    //--- Other GameBoard methods ---//

    // Populates entire GameBoard with Points representing empty spaces '~'
    public void initialize() 
    {
        for(int row = 0; row < gameboard.length; row++)
        {
            for(int col = 0; col < gameboard[row].length; col++)
            {
                gameboard[row][col] = new Point(col, row, '~');
            }
        }
    }

    // Adds player to game
    public void createPlayer(int playerNum)
    {
        Scanner in = new Scanner(System.in);
        String name;

        if(playerNum < 1 || playerNum > 4)
        {
            // Error handling for impossible player numbers (not 1-4)
            out.println("Invalid player entry. Canceling player creation.");
        }
        else
        {
            // "Enter Player X's name: (Name Input)"
            out.print("Enter Player " + playerNum + "'s name: ");
            name = in.nextLine();

            // Adds player with inputted name and given number to the ArrayList of players
            players.add(new Player(playerNum, name));
            out.println("Welcome aboard, " + name + "!");
        }
    }

    // Displays entire gameboard for all players
    // "hide" determines whether ship cells are visible
    public void displayBoard(boolean hide)
    {
        // Holds the state of a cell/Point on the GameBoard for determining how to print it
        char cellState;

        out.println();

        // Prints Player 1's and 2's ship sizes and statuses across the top of the GameBoard
        fPrintPlayers(players.get(0), players.get(1));
        // Pads column labels with spaces for formatting
        if(players.size() < 3)
        {
            // 2 spaces since 2 players = 1-digit row labels max
            out.print("  ");
        }
        else
        {
            // 3 spaces since 3+ players = 2-digit row labels max
            out.print("   ");
        }

        // Labels columns
        for(int c = 0; c < gameboard[0].length; c++)
        {
            // Spacing to accommodate for vertical line separator
            if(c == gameboard[0].length/2)
            {
                out.print("  ");
            }

            out.print(c + " ");
        }
        out.println();

        if(hide)
        {
            // If "hide" is true, print the states of all cells of the 2D Point array "gameboard", adding one space after each cell in one-digit column positions
            // and 2 spaces after each cell in two-digit column positions. Cell states that are ships "S" and empty spots "~" show the same state character, "~".
            for(int row = 0; row < gameboard.length; row++)
            {
                // Labels rows (2 spaces after 1-digit labels since later labels have 2 digits and thus take up one extra space)
                out.print(row + " ");
                if(row < 10 && players.size() > 2)
                {
                    out.print(" ");
                }
                
                for(int col = 0; col < gameboard[row].length; col++)
                {
                    // At halfway mark, create the vertical line separator to separate halves of the gameboard
                    if(col == SIZE/2)
                    {
                        out.print("| ");
                    }

                    cellState = gameboard[row][col].getState();
                    if(cellState == 'S' || cellState == '~')
                    {
                        // Hides ship cells as empty cells
                        out.print("~ ");
                    }
                    else
                    {
                        out.print(cellState + " ");
                    }

                    // Add one extra space for 2-digit column labels
                    if(col > 9)
                    {
                        out.print(" ");
                    }
                }
                out.println();

                // Horizontal line separating halves of the gameboard
                if(players.size() > 2 && row == SIZE/2 - 1)
                {
                    // Starts with 3 underscores since row labels have a max of 2 digits with 3+ players
                    out.print("___");
                    for(int i = 0; i < 5*gameboard[0].length/2 + 1; i++)
                    {
                        out.print("_");
                    }
                    out.println();
                }
            }
        }
        else
        {
            // If "hide" is false, perform the same operations as when "hide" is true, but show ship cell states "S" instead of disguising them as empty spots "~".
            for(int row = 0; row < gameboard.length; row++)
            {
                // Labels rows (2 spaces after 1-digit labels since later labels have 2 digits and thus take up one extra space)
                out.print(row + " ");
                if(row < 10 && players.size() > 2)
                {
                    out.print(" ");
                }

                for(int col = 0; col < gameboard[row].length; col++)
                {
                    // At halfway mark, create the vertical line separator to separate halves of the gameboard
                    if(col == SIZE/2)
                    {
                        out.print("| ");
                    }
                    
                    // Prints the state of the cell directly, adding a space afterwards
                    out.print(gameboard[row][col].getState() + " ");
                    
                    // Add one extra space after the cell state for 2-digit column label positions
                    if(col > 9)
                    {
                        out.print(" ");
                    }
                }
                out.println();

                // Horizontal line separating halves of the gameboard
                if(players.size() > 2 && row == SIZE/2 - 1)
                {
                    // 3 extra starting spaces to adjust formatting for 2-digit row labels
                    out.print("___");
                    for(int i = 0; i < 5*gameboard[0].length/2 + 1; i++)
                    {
                        out.print("_");
                    }
                    out.println();
                }
            }
        }

        if(players.size() == 3)
        {
            // If 3 players, shows ship sizes and statuses for Player 3 in bottom left corner only
            out.println();
            players.get(2).showDetails();
            out.println();
        }
        else if(players.size() == 4)
        {
            // If 4 players, shows ship sizes and statuses of Player 3 AND 4, with P3's data at the
            // left-bottom of the GameBoard and P4's at the right-bottom
            fPrintPlayers(players.get(2), players.get(3));
        }
    }

    // Displays all cells of a player's section of the board, including ship cells
    // - P1: Top left
    // - P2: Top right
    // - P3: Bottom left
    // - P4: Bottom right
    // masterCoordinates determines whether to show row & column labels as they are on the big GameBoard or act like
    // the player's section is its own mini-gameboard
    public void displayBoard(Player player, boolean masterCoordinates) 
    {
        int playerNum = player.getPlayerNum();

        if(playerNum < 1 || playerNum > players.size())
        {
            out.println("Invalid player entry. Canceling display.");
        }
        else
        {
            // Pads front of column labels with spaces for formatting
            if(masterCoordinates)
            {
                if(player.getPlayerNum() < 3)
                {
                    // 2 spaces since 2 players = 1-digit row labels max
                    out.print("  ");
                }
                else
                {
                    // 3 spaces since 3+ players = 2-digit row labels max
                    out.print("   ");
                }
            }
            else
            {
                out.print("  ");
            }

            if(playerNum == 1)
            {
                // Labels columns
                for(int c = 0; c < SIZE/2; c++)
                {
                    out.print(c + " ");
                }
                out.println();

                for(int row = 0; row < SIZE/2; row++)
                {
                    // Labels rows
                    out.print(row + " ");

                    for(int col = 0; col < SIZE/2; col++)
                    {
                        out.print(gameboard[row][col].getState() + " ");
                    }
                    out.println();
                }
            }
            else if(playerNum == 2)
            {
                // Labels columns
                if(masterCoordinates)
                {
                    for(int c = SIZE/2; c < SIZE; c++)
                    {
                        out.print(c + " ");
                    }
                }
                else
                {
                    for(int c = 0; c < SIZE/2; c++)
                    {
                        out.print(c + " ");
                    }
                }
                out.println();

                for(int row = 0; row < SIZE/2; row++)
                {
                    // Labels rows
                    out.print(row + " ");

                    for(int col = SIZE/2; col < SIZE; col++)
                    {
                        out.print(gameboard[row][col].getState() + " ");
                        if(masterCoordinates)
                        {
                            out.print(" "); // Extra space for 2-digit column labels
                        }
                    }
                    out.println();
                }
            }
            else if(playerNum == 3)
            {
                // Labels columns
                for(int c = 0; c < SIZE/2; c++)
                {
                    out.print(c + " ");
                }
                out.println();
                
                for(int row = SIZE/2; row < SIZE; row++)
                {
                    // Labels rows
                    if(masterCoordinates)
                    {
                        out.print(row + " ");
                    }
                    else
                    {
                        out.print((row % (SIZE/2)) + " ");
                    }

                    for(int col = 0; col < SIZE/2; col++)
                    {
                        out.print(gameboard[row][col].getState() + " ");
                    }
                    out.println();
                }
            }
            else
            {
                // Labels columns
                if(masterCoordinates)
                {
                    for(int c = SIZE/2; c < SIZE; c++)
                    {
                        out.print(c + " ");
                    }
                }
                else
                {
                    for(int c = 0; c < SIZE/2; c++)
                    {
                        out.print(c + " ");
                    }
                }
                out.println();

                for(int row = SIZE/2; row < SIZE; row++)
                {
                    // Labels rows
                    if(masterCoordinates)
                    {
                        out.print(row + " ");
                    }
                    else
                    {
                        out.print((row % (SIZE/2)) + " ");
                    }

                    for(int col = SIZE/2; col < SIZE; col++)
                    {
                        out.print(gameboard[row][col].getState() + " ");
                        if(masterCoordinates)
                        {
                            out.print(" "); // Extra space for 2-digit column labels
                        }
                    }
                    out.println();
                }
            }
        }
    }

    // Prints the names and ship statuses of two Players A and B at opposite ends of the GameBoard
    // A is on the left, B is on the right
    public void fPrintPlayers(Player a, Player b)
    {
        // Number of spaces to put between two lines in each player's data block
        int numSpaces;
        // Stores top lines of each player's printed data blocks, which show their numbers and names
        String playerALine = "P" + a.getPlayerNum() +": " + a.getPlayerName();
        String playerBLine = "P" + b.getPlayerNum() +": " + b.getPlayerName();

        //----- Calculate number of spaces to put between the top lines
        if(players.size() < 3)
        {
            // 2 players = 1-digit row columns, start with 2 spaces on left
            numSpaces = 2;
        }
        else
        {
            // 3+ players = 2-digit row columns max, start with 3 spaces on left
            numSpaces = 3;
        }

        // 2 spaces per 1-digit column label, 2 spaces for "| " separator 
        // 3 spaces per 2-digit column label, minus one space for the last column
        // 2*(SIZE/2) + 2 + 3*(SIZE/2) - 1 = 1 + 5*(SIZE/2) spaces across the top of the GameBoard
        numSpaces += 1 + 5*(SIZE/2);

        out.println();
        // Subtracts combined length of the two player's info block lines from the total number of
        // spaces across the top of the board to find the number of spaces between the lines. 
        // Prints the lines with the appropriate number of spaces between them.
        out.print(playerALine);
        for(int i = 0; i < numSpaces - (playerALine.length() + playerBLine.length()); i++)
        {
            out.print(" ");
        }
        out.println(playerBLine);

        // For each ship in both player's Ship ArrayLists, print their ship sizes and statuses,
        // separated from each other with enough spaces to put one player's data on the left 
        // side of the board and the other's on the right side of the board
        Ship shipA, shipB;
        for(int i = 0; i < a.getShips().size(); i++)
        {
            shipA = a.getShips().get(i);
            shipB = b.getShips().get(i);
            playerALine = "Ship " + shipA.getSize() + ": " + shipA.getStatus();
            playerBLine = "Ship " + shipB.getSize() + ": " + shipB.getStatus();

            // Puts calculated number of spaces between the two player's data block lines
            // (Total number of spaces across the top of the GameBoard was already calculated 
            //  from the top lines)
            out.print(playerALine);
            for(int q = 0; q < numSpaces - (playerALine.length() + playerBLine.length()); q++)
            {
                out.print(" ");
            }
            out.println(playerBLine);
        }
        out.println();
    }

    // User places their 5 ships on their GameBoard.
    public void placeShips(Player p) 
    {
        Scanner input = new Scanner(System.in);			
        out.println("P" + p.getPlayerNum() + ": " + p.getPlayerName() + ", you have a 6-cell ship, a 5-cell ship, a 4-cell ship, a 3-cell ship, and a 2-cell ship.");
        
        // Repeat ship placement for sizes 6, 5, 4, 3, and 2.
        for(int shipSize = 6; shipSize >=2; shipSize--) 
        {
            // Check orientation of ship placed.
            out.println("Do you want to place your " + shipSize + "-cell ship horizontally or vertically? (H/V)");
            char placement = input.next().charAt(0);
            placement = Character.toUpperCase(placement);
            while(placement != 'H' && placement != 'V') 
            {
                out.println("Invalid input. Do you want to place your " + shipSize + "-cell ship horizontally or vertically? (H/V)");
                placement = input.next().charAt(0);
                placement = Character.toUpperCase(placement);
            }

            out.print("Place your " + shipSize + "-cell ship. ");
            if(placement == 'H')
            {
                out.println("Ship cells will build rightwards from your chosen starting cell.");
            }
            else
            {
                out.println("Ship cells will build downwards from your chosen starting cell.");
            }
            out.println("Enter the row and column to place the " + shipSize + "-cell ship (No overlapping ships): ");
            int row = -1, col = -1;
            boolean isValidPlacement;
            
            do 
            {
                displayBoard(p, false);
                isValidPlacement = true;
                
                // Takes input for row coordinate
                out.print("Row (0-" + (SIZE/2 - 1) + "): ");
                do
                {
                    if(input.hasNextInt()) 
                    { 
                        // Error handling for negative integer input.
                        row = input.nextInt();
                        if(row < 0)
                        {
                            row = -1;
                            out.print("Negative integer input invalid. Please enter an integer (0-" + (SIZE/2 - 1) + "): ");
                            continue;
                        }
                        out.println("You entered: " + row);
                    } 
                    else 
                    {
                        // Error handling for non-integer input
                        out.println("Please enter a valid integer.");
                        input.next();
                    }
                }while(row == -1);

                // Takes input for column coordinate
                out.print("Column (0-" + (SIZE/2 - 1) + "): ");
                do
                {
                    if (input.hasNextInt()) 
                    { 
                        // Error handling for negative integer input.
                        col = input.nextInt();
                        if(col < 0)
                        {
                            col = -1;
                            out.print("Negative integer input invalid. Please enter an integer (0-" + (SIZE/2 - 1) + "): ");
                            continue;
                        }
                        out.println("You entered: " + col);
                        out.println();
                    } 
                    else 
                    {
                        // Error handling for non-integer input
                        out.println("Please enter a valid integer.");
                        input.next();
                    }
                }while(col == -1);

                // Adjusts row and column input for players 2-4
                int adjustRow = row, adjustCol = col;
                if(p.getPlayerNum() == 2)
                {
                    adjustCol = col + SIZE/2;
                }
                else if(p.getPlayerNum() == 3)
                {
                    adjustRow = row + SIZE/2;
                }
                else if(p.getPlayerNum() == 4)
                {
                    adjustCol = col + SIZE/2;
                    adjustRow = row + SIZE/2;
                }

                // Checks if the entire ship is fully on the GameBoard.
                if (row < 0 || row >= SIZE/2 || col < 0 || col >= SIZE/2 || gameboard[adjustRow][adjustCol].getState() == 'S') 
                {
                    System.out.println("Invalid placement. Try again.");
                    isValidPlacement = false;
                }
                // Checks validity of horizontal ship placement
                else if(placement == 'H') 
                {
                    // Checks if ship is completely on the player's section of the GameBoard
                    if(col + shipSize > SIZE/2)
                    {
                        System.out.println("Invalid placement. Try again.");
                        isValidPlacement = false;
                    }
                    else
                    {
                        // Checks if ship overlaps with another ship (all cells are free)
                        for (int i = 0; i < shipSize; i++) 
                        {
                            if(gameboard[adjustRow][adjustCol + i].getState() == 'S') 
                            {
                                System.out.println("Invalid placement. Try again.");
                                isValidPlacement = false;
                            }
                        }
                    }
                }
                // Checks validity of vertical ship placement
                else if(placement == 'V') 
                {
                    // Checks if ship is completely on the player's section of the GameBoard
                    if(row + shipSize > SIZE/2)
                    {
                        System.out.println("Invalid placement. Try again.");
                        isValidPlacement = false;
                    }
                    else
                    {
                        // Checks if ship overlaps with another ship (all cells are free)
                        for (int i = 0; i < shipSize; i++) 
                        {
                            if(gameboard[adjustRow + i][adjustCol].getState() == 'S'){
                                System.out.println("Invalid placement. Try again.");
                                isValidPlacement = false;
                            }
                        }
                    }
                }
                else
                    isValidPlacement = true;
                
                // Populates chosen cells on Gameboard with "S" Points and adds each Point to a new Ship Object. Adds the Ship Object to the list of Ships for the player.
                if (isValidPlacement) 
                {
                    // Initialize new ship
                    Ship newShip = new Ship(shipSize, placement);
                    int i = 0;

                    // Horizontal ship placement
                    if(placement == 'H')
                    {
                        for(int c = adjustCol; c < adjustCol + shipSize; c++)
                        {
                            // Set Point's state to ship 'S' and define the length of the ship it belongs to
                            gameboard[adjustRow][c].setState('S');
                            gameboard[adjustRow][c].setShipID(shipSize);
                            // Build the Point on the Ship's Point array
                            newShip.getPositions()[i] = gameboard[adjustRow][c];
                            i++;
                        }
                    }
                    // Vertical ship placement
                    else
                    {
                        for(int r = adjustRow; r < adjustRow + shipSize; r++)
                        {
                            // Set Point's state to ship 'S' and define the length of the ship it belongs to
                            gameboard[r][adjustCol].setState('S');
                            gameboard[r][adjustCol].setShipID(shipSize);
                            // Build the Point on the Ship's Point array
                            newShip.getPositions()[i] = gameboard[r][adjustCol];
                            i++;
                        }
                    }
                    
                    // Adds the newly built Ship Object full of "S" Points to the player's list of ships
                    p.getShips().add(newShip);
                    displayBoard(p, false);
                }
            }while(!isValidPlacement);
        }
    }
    
    // Shoots at a cell.
    // When set to True, "consecutive" enables players to take another shot if they hit a ship
    // When set to False, "hide" shows ship cells on the board after shooting
    public void shoot(Player p, boolean consecutive, boolean hide) 
    {
        // Shooting player
        int playerNum = p.getPlayerNum();
        // Number of player that was hit
        int playerHit = -1;
        // Row and column coordinates at which shot is made
        int row, col;
        // Flags whether the shot is valid or not
        boolean isValidShot;
        // Point at which the player will shoot
        Point target = null;
        // Ship that was hit
        Ship shipHit = null;
        // Indicates whether player can shoot again
        boolean shootAgain = true;

        while(shootAgain)
        {
            // Shows board, takes row-column input to determine which Point the player will shoot at, and validates the shot.
            displayBoard(hide);
            out.println("(P" + playerNum + ") " + p.getPlayerName() +", enter the row and column to shoot: ");
            do 
            {
                isValidShot = true;

                // Takes row-column coordinate input to shoot
                out.print("Row (0-" + (SIZE - 1) + "): ");
                row = BattleshipGame.validInput(0, SIZE-1);
                out.print("Column (0-" + (SIZE - 1) + "): ");
                col = BattleshipGame.validInput(0, SIZE-1);

                // Checks if the cell is on the GameBoard and if it's already been shot at.
                if (row < 0 || row >= gameboard.length) 
                {
                    out.println("Invalid shot. Try again.");
                    isValidShot = false;
                }
                else if(col < 0 || col >= gameboard[row].length)
                {
                    out.println("Invalid shot. Try again.");
                    isValidShot = false;
                }
                else if(gameboard[row][col].getState() == 'X' || gameboard[row][col].getState() == 'O')
                {
                    out.println("Invalid shot. Try again.");
                    isValidShot = false;
                }
                // Checks if player shot outside of their section (players can't shoot inside their own regions)
                else
                {
                    switch(playerNum)
                    {
                        case 1:
                            if(row < SIZE/2 && col < SIZE/2)
                            {
                                out.println("Shooting inside your own territory is highly unadvised. Try again.");
                                isValidShot = false;
                            }
                            break;
                        case 2:
                            if(row < SIZE/2 && col >= SIZE/2)
                            {
                                out.println("Shooting inside your own territory is highly unadvised. Try again.");
                                isValidShot = false;
                            }
                            break;
                        case 3:
                            if(row >= SIZE/2 && col < SIZE/2)
                            {
                                out.println("Shooting inside your own territory is highly unadvised. Try again.");
                                isValidShot = false;
                            }
                            break;
                        default:
                            if(row >= SIZE/2 && col >= SIZE/2)
                            {
                                out.println("Shooting inside your own territory is highly unadvised. Try again.");
                                isValidShot = false;
                            }
                    }
                }  
            }while(!isValidShot);
            
            target = gameboard[row][col];
            // Marks the cell, shows the board
            if(target.getState() == '~')
            {
                target.setState('O');
                displayBoard(hide);
                out.println("\nMiss!");
                // Missing a shot means player can't shoot again until their next turn
                shootAgain = false;
            }
            else if(target.getState() == 'S')
            {
                // Since each Point was already added to a Ship object during the players' building stage, changing the Point
                // on the gameboard cell also changes that point in the player's ship.
                target.setState('X');
                displayBoard(hide);
                out.println("\nHit!");

                // Declares whether the Ship the Point belonged to has sunk.
                boolean sunk = true;
                
                // Finds which player was hit
                if(row < SIZE/2 && col < SIZE/2)
                {
                    // Indicates P1 was hit
                    playerHit = 0;
                }
                else if(row < SIZE/2 && col >= SIZE/2)
                {
                    // Indicates P2 was hit
                    playerHit = 1;
                }
                else if(row >= SIZE/2 && col < SIZE/2)
                {
                    // Indicates P3 was hit
                    playerHit = 2;
                }
                else if(row >= SIZE/2 && col >= SIZE/2)
                {
                    // Indicates P4 was hit
                    playerHit = 3;
                }

                // Goes through the hit player's list of ships, finds the one with the same length as the ship the Point says it belongs to, 
                // and flags whether the that ship is entirely destroyed.
                for(Ship s : players.get(playerHit).getShips())
                {
                    if(target.getShipID() == s.getSize())
                    {
                        shipHit = s;

                        // Checks that all Points of the Ship are destroyed. If not, indicate that Ship has not sunk.
                        for(Point point : shipHit.getPositions())
                        {
                            if(point.getState() != 'X')
                            {
                                sunk = false;
                            }
                        }
                    }
                }

                // Changes Ship status to "Sunk" if it's completely destroyed, declares the size of the ship and whose ship it was
                // Decrements the number of ships the player hit has left
                if(sunk)
                {
                    shipHit.setStatus("Sunk");
                    out.println(players.get(playerHit).getPlayerName() + "'s Ship " + target.getShipID() + " has sunk!");
                    players.get(playerHit).decreaseShipsLeft();
                    out.println(players.get(playerHit).getPlayerName() + " has " + players.get(playerHit).getShipsLeft() + " ship(s) left.");
                    
                    // Stops (consecutive) firing if there's one player remaining
                    if(getWinner() != null)
                    {
                        shootAgain = false;
                    }
                }

                // Allows player to shoot again if consecutive shots are enabled, stops shooting if not enabled
                if(!consecutive)
                {
                    shootAgain = false;
                }
            }
        }				
    }

    // Returns the winning player OR null if no winner has been determined
    public Player getWinner()
    {
        Player winner = null;
        // Counts number of players still in the game
        int activePlayers = players.size();

        // Goes through all players in the GameBoard's ArrayList, counts how many are still in the game
        for(Player p : players)
        {
            // If all ships of player are destroyed, decrement number of active players
            if(!p.isActive())
            {
                activePlayers--;
            }
        }

        // Finds which player won if only one player is left
        if(activePlayers == 1)
        {
            for(Player p : players)
            {
                // For one remaining player, the only active one must be the winner
                if(p.isActive())
                {
                    winner = p;
                }
            }
        }

        return winner;
    }
}