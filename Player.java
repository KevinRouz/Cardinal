import java.util.*;
import static java.lang.System.out;

public class Player 
{
    // Holds ships for the player
    private ArrayList<Ship> ships;
    // Holds player name & number
    private int playerNum;
    private String playerName;
    // # of ships remaining, if zero, player is out of game
    private int shipsLeft;

    // Constructor
    public Player(int num, String name)
    {
        playerNum = num;
        playerName = name;
        ships = new ArrayList<>();
        shipsLeft = 5; // Ships of size 6, 5, 4, 3, and 2
    }

    //--- Getters ---//
    public ArrayList<Ship> getShips()
    {
        return ships;
    }

    public int getShipsLeft()
    {
        return shipsLeft;
    }

    public int getPlayerNum()
    {
        return playerNum;
    }

    public String getPlayerName()
    {
        return playerName;
    }

    //--- Setters ---//
    public void setPlayerName(String name)
    {
        playerName = name;
    }

    public void setPlayerNum(int num)
    {
        playerNum = num;
    }

    public void setDetails(String name, int num)
    {
        playerName = name;
        playerNum = num;
    }

    //--- Other player methods ---//
    // Decrements number of ships left for when one of the player's ships sinks
    public void decreaseShipsLeft()
    {
        shipsLeft--;
    }

    // Shows a player's name, number, and status for each ship
    public void showDetails()
    {
        out.println("P" + playerNum + ": " + playerName);
        for(Ship s : ships)
        {
            out.println("Ship " + s.getSize() + ": " + s.getStatus());
        }
    }

    // Indicates whether the player is still in the game
    public boolean isActive()
    {
        // Player is in game if they have more than 0 ships left
        if(shipsLeft > 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
