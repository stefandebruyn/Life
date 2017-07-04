public class ConwayCell extends Cell {
  public ConwayCell(int x, int y)
  { super(x,y); }

  @ Override
  public String[][] getSprite()
  { return new String[][]{{"C"}}; }

  /* Each cell runs its behavior once per game cycle */
  @ Override
  public void run() {
    int livingNeighbors = countLivingNeighborsOfPosition(x,y);

    // Rules 1 through 3 -- cell death
    if (livingNeighbors < 2 || livingNeighbors > 3)
      GAME.addCellToKill(this);

    // Rule 4 -- cell birth
    for (int xOff = -1; xOff <= 1; xOff++)
      for (int yOff = -1; yOff <= 1; yOff++)
        if (!(xOff == 0 && yOff == 0))
          if (GAME.getCellAt(x+xOff,y+yOff) == null && countLivingNeighborsOfPosition(x+xOff,y+yOff) == 3)
            GAME.addCellToCreate(new ConwayCell(x+xOff,y+yOff));
  }
}
