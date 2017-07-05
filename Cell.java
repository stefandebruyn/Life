public abstract class Cell {
  protected final Game GAME;
  protected int x, y;

  public Cell(int x, int y) {
    GAME = Game.getInstance();
    this.x = x;
    this.y = y;
  }

  public String[][] getSprite()
  { return new String[][]{{"X"}}; }

  public int getX()
  { return x; }

  public int getY()
  { return y; }

  /* Returns the number of living cells in the 8 spots adjacent to a specific position */
  public int countLivingNeighborsOfPosition(int x, int y) {
    int sum = 0;
    for (int xOff = -1; xOff <= 1; xOff += 1)
      for (int yOff = -1; yOff <= 1; yOff += 1)
        if (!(xOff == 0 && yOff == 0))
          sum += GAME.getCellAt(x+xOff,y+yOff) == null? 0 : 1;
    return sum;
  }

  /* All cells will behave differently */
  public abstract void run();
}
