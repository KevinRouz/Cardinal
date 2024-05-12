// Reoresents a coordinate on a gameboard/map
public class Point 
{
    // Stores X and Y coordinates. 
    // X = horizontal (column) position
    // Y = vertical (row) position
    // (x, y) = (0, 0) = top-left spot
    private int x;
    private int y;

    // Stores the spot's state
    // 'S' - ship cell
    // 'X' - hit ship cell
    // 'O' - missed shot
    // '~' - empty spot
    private char state;

    // Indicates size of ship the Point belongs to
    private int shipID;

    //--- Constructors ---//
    public Point()
    {
        x = 0;
        y = 0;
        state = '~';
        shipID = 0;
    }

    public Point(int xCoordinate, int yCoordinate, char state)
    {
        x = xCoordinate;
        y = yCoordinate;
        this.state = state;
    }

    public Point(int xCoordinate, int yCoordinate, char state, int shipID)
    {
        x = xCoordinate;
        y = yCoordinate;
        this.state = state;
        this.shipID = shipID;
    }

    //--- Getters ---//
    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }
    
    public char getState()
    {
        return state;
    }

    public int getShipID()
    {
        return shipID;
    }

    //--- Setters ---//
    public void setX(int xCoordinate)
    {
        x = xCoordinate;
    }

    public void setY(int yCoordinate)
    {
        y = yCoordinate;
    }

    public void setState(char s)
    {
        state = s;
    }

    public void setShipID(int ID)
    {
        shipID = ID;
    }
}
