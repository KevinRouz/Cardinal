import static java.lang.System.out;

// Keeps track of ship data for each player.
public class Ship 
{
    //--- Private Instance Variables, aka PIVS or "traits" ---//
    // Length of ship
    private int size;
    // Status of ship: Sunk or Operational
    private String status;
    // Orientation of Ship: H (Horizontal) or V (Vertical)
    private char orientation;
    // Holds all Points on the gameboard that the ship fills
    private Point[] positions;

    // Constructor
    public Ship(int size, char o) {
        this.size = size;
        status = "Operational";
        orientation = o;
        positions = new Point[size];
    }

    //--- Getters ---//
    public int getSize()
    {
        return size;
    }

    public String getStatus()
    {
        return status;
    }

    public char getOrientation()
    {
        return orientation;
    }

    public Point[] getPositions()
    {
        return positions;
    }

    //--- Setters ---//
    public void setSize(int s)
    {
        size = s;
    }

    public void setStatus(String s)
    {
        status = s;
    }

    public void setOrientation(char o)
    {
        orientation = o;
    }

    //--- Other Ship methods ---//
    // "Builds" ship cell by replacing the value at array position "cellNum" with Point "p"
    public void buildShipCell(Point p, int cellNum)
    {
        positions[cellNum] = p;
    }

    // Shows functional and damaged positions of the ship, as well as its size
    public void showShip()
    {
        out.println("Ship " + size + ":");
        // Goes through the array of Points making up the ship and prints their states ('S' or 'X')
        for(Point p : positions)
        {
            out.print(p.getState());
        }
        out.println();
    }

    // Shows only the state of a Ship's cells
    public String toString()
    {
        String cells = "";
        
        for(Point p : positions)
        {
            cells += p.getState();
        }

        return cells;
    }
}