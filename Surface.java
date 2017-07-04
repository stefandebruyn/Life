import static java.lang.System.out;
import java.util.Arrays;

public class Surface {
  private final String EMPTY_SPACE = " ";
  private final int CMD_WIDTH = 120;
  private final int CMD_HEIGHT = 30;
  private static Surface instance = null;
  private String[][] surface;

  private Surface() {
    surface = new String[CMD_HEIGHT][CMD_WIDTH];
    for (String[] arr : surface)
      Arrays.fill(arr,EMPTY_SPACE);
  }

  public static Surface getInstance() {
      if (instance == null)
        instance = new Surface();
      return instance;
  }

  /* Draw the current frame to the screen and then clear the surface */
  public void render() {
    String screen = "";
    for (int x = 0; x < surface.length; x++) {
      for (int y = 0; y < surface[0].length; y++) {
        screen += surface[x][y];
        surface[x][y] = EMPTY_SPACE;
      }
      screen += "\n";
    }
    out.print(screen + "\n");
  }

  /* Draw a specific sprite to the current frame */
  public void draw(int x, int y, String[][] sprite) {
    for (int dx = 0; dx < sprite[0].length; dx++) {
      for (int dy = 0; dy < sprite.length; dy++) {
        int tryX = x+dx;
        int tryY = y+dy;
        if ((tryX >= 0 && tryX < surface[0].length) && (tryY >= 0 && tryY < surface.length))
          surface[tryY][tryX] = sprite[dy][dx];
      }
    }
  }
}
