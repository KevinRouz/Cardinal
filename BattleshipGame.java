import java.util.Scanner;
import static java.lang.System.out;

public class BattleshipGame
{
	
	public static void main(String args[]) throws Exception
	{
		int choice = 0, playerNum = 0;
		// Indicates whether players can shoot again after hitting a ship
		boolean consecutiveShots = false;
		// Indicates whether the master gameboard will show ship spots
		boolean testMode = false;
		// Player whose turn it is
		Player turnPlayer;

		// Picks program mode
		out.println("Enter program mode (1 or 2):");
		out.println("1. Test (Ship cells shown)");
		out.println("2. Normal Game");
		choice = validInput(1, 2);
		if(choice == 1)
		{
			out.println("Testing mode chosen.\n");
			testMode = true;
		}
		else
		{
			out.println("Normal mode chosen.\n");
		}

		// Number of players
		int numOfPlayers = startGame();

		// Toggles consecutive shots based on user input
		out.println("\nPlease pick a gamemode (1-2):");
		out.println("1. Classic\nYour run-of-the-mill game of battleship.");
		out.println("2. Panic\nNot for the faint-hearted! Players can keep shooting as long as they hit a ship.");
		choice = validInput(1, 2);
		if(choice == 1)
		{
			out.println("Classic mode chosen.\n");
		}
		if(choice == 2)
		{
			out.println("Panic mode chosen.\n");
			consecutiveShots = true;
		}

		// Creates a new, blank GameBoard for the number of players, adds player data, and builds ships for each player
		GameBoard newGame = new GameBoard(numOfPlayers);
		newGame.initialize();
		for(int i = 1; i <= numOfPlayers; i++)
		{
			newGame.createPlayer(i);
			newGame.placeShips(newGame.getPlayers().get(i-1));
		}
		
		while(true)
		{
			// Determines which player in the list is shooting
			// Modulus loops player number from 0 to (n-1) players for "n" players since ArrayLists are indexed from 0
			playerNum %= numOfPlayers;
			// Goes to GameBoard newGame's ArrayList of Players and gets the player at the position "playerNum"
			turnPlayer = newGame.getPlayers().get(playerNum);

			// If player is out (all ships destroyed, zero ships left), skip to next player
			if(!turnPlayer.isActive())
			{
				playerNum++;
				continue;
			}
			
			// Registers the shot to the GameBoard and displays the board, hides ship cells if not in testing mode
			newGame.shoot(newGame.getPlayers().get(playerNum), consecutiveShots, !testMode);

			// If this game has a winner, print victory message and number of ships they have left, then end the game by breaking the loop
			if(newGame.getWinner() != null)
			{
				out.println();
				// "playerName (PX) won with X ships left!"
				out.println(turnPlayer.getPlayerName() + " (P" + turnPlayer.getPlayerNum() + ") won with " + turnPlayer.getShipsLeft() + " ship(s) left!");
				break;
			}

			// Moves to next player
			playerNum++;
		}

		// Prints final results of game, showing the final look of the GameBoard (ship cells shown)
		out.println("\nFinal Results:");
		newGame.displayBoard(false);
	}

	// Introduction and selection of player count.
	public static int startGame()
	{
		out.println("Welcome to Cardinal! This is a Java implementation of the classic game, battleship.");
		out.println("How many players (2 to 4)?");
		int numOfPlayers = 0;
		
		numOfPlayers = validInput(2, 4);
		
		return numOfPlayers;
	}

	// Ensures an input is an integer value from "a" to "b" inclusive
	public static int validInput(int a, int b)
	{
		Scanner in = new Scanner(System.in);
		// response = -1 means input is invalid
		int response = -1;
		
		do
		{
			if(in.hasNextInt()) 
			{ 
				// Error handling for input out of desired bounds
				response = in.nextInt();
				if(response < a || response > b)
				{
					response = -1;
					out.print("Invalid input. Please enter a choice (" + a + "-" + b + "): ");
					continue;
				}
			} 
			else 
			{
				// Error handling for non-integer input
				out.println("Please enter a valid integer (" + a + "-" + b + "): ");
				in.next(); // Absorbs remaining characters in input stream
			}
		}while(response == -1);

		return response;
	}
}