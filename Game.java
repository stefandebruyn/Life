import static java.lang.System.out;
import java.io.IOException;
import java.util.ArrayList;

public class Game implements CellPatterns {
  private static Game instance = null;
  private final Surface GAME_SURFACE;
  private final int SIMULATION_FPS = 60;
  private ArrayList<Cell> cells;
  private ArrayList<Cell> cellsToKill;
  private ArrayList<Cell> cellsToCreate;

  /* Set up a new game -- once set up, the instance's run() method must be manually called for it to play */
  private Game() {
    GAME_SURFACE = Surface.getInstance();
    cells = new ArrayList<Cell>();
    cellsToKill = new ArrayList<Cell>();
    cellsToCreate = new ArrayList<Cell>();
  }

  /* Run x amount of cycles in the game, and then terminate */
  public void run(int cycles) {
    // Initial state
    addCellPattern(60,15,ACORN);
    // For each game cycle
    while (cycles > 0) {
      // Run each cell's behavior and draw it to the surface
      for (int i = cells.size()-1; i >= 0; i--) {
        cells.get(i).run();
        GAME_SURFACE.draw(cells.get(i).getX(),cells.get(i).getY(),cells.get(i).getSprite());
      }
      // Kill cells that need to be killed and create cells that need to be created -- performed by the game rather than the cells to maintain concurrency
      for (Cell c : cellsToKill)
        killCell(c);
      for (Cell c : cellsToCreate)
        addCell(c);
      cellsToKill.clear();
      cellsToCreate.clear();
      // Finish this cycle by drawing the frame
      GAME_SURFACE.render();
      try { Thread.sleep((int)(1000/SIMULATION_FPS)); } catch (InterruptedException e) { out.println(e); }
      cycles--;
    }
    // Conclude simulation
    out.println("Simulation finished.");
  }

  /* Returns the id of a cell at a specific position, or null if the position is unoccupied */
  public Cell getCellAt(int x, int y) {
    for (Cell c : cells)
      if (c.getX() == x && c.getY() == y)
        return c;
    return null;
  }

  /* Removes a specific cell from the game's memory to be taken care of by the JGC */
  public void killCell(Cell c) {
    for (int i = 0; i < cells.size(); i++)
      if (cells.get(i) == c)
        cells.remove(i);
  }

  /* Any and all cells must be added to the game through this method */
  public void addCell(Cell c) {
    if (getCellAt(c.getX(),c.getY()) == null)
      cells.add(c);
  }

  /* Add a cell to be killed at the end of the current cycle */
  public void addCellToKill(Cell c)
  { cellsToKill.add(c); }

  /* Add a cell to be created at the end of the current cycle */
  public void addCellToCreate(Cell c)
  { cellsToCreate.add(c); }

  /* Add cells in a pattern built from binary strings, relative to a specific position
   * e.g. A 3x3 checkerboard pattern in the top-left could be achieved like this:
   *      addCellPattern(0,0,["101",
   *                          "010",
   *                          "101"]);
   * Several well-known patterns are pre-written for quick access in CellPatterns.java
   */
  public void addCellPattern(int x, int y, String[] bin) {
    for (int yOff = 0; yOff < bin.length; yOff++)
      for (int xOff = 0; xOff < bin[yOff].length(); xOff++)
        if (bin[yOff].charAt(xOff) == '1')
          addCell(new ConwayCell(x+xOff,y+yOff));
  }

  public static void main(String[] args) throws InterruptedException, IOException {
    // Verify usage
    if (args.length != 1)
      throw new IllegalArgumentException("Usage: Game <cycles>");
    // Launch
    new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
    getInstance();
    getInstance().run(Integer.parseInt(args[0]));
  }

  public static Game getInstance() {
      if (instance == null)
        instance = new Game();
      return instance;
  }
}
