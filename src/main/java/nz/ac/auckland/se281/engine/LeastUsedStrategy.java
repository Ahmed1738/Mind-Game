package nz.ac.auckland.se281.engine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nz.ac.auckland.se281.model.Colour;

/**
 * The LeastUsedStrategy class implements an AI strategy where the AI guesses the least used colour
 * from the player's history.
 */
public class LeastUsedStrategy implements AiStrategy {

  /** The history of colours chosen by the player. */
  private List<Colour> playerHistory;

  /**
   * Constructs a LeastUsedStrategy with the specified player history.
   *
   * @param playerHistory the history of colours chosen by the player
   */
  public LeastUsedStrategy(List<Colour> playerHistory) {
    this.playerHistory = playerHistory;
  }

  /**
   * Chooses a random colour for the AI's move.
   *
   * @return a randomly chosen colour
   */
  @Override
  public Colour chooseColour() {
    return Colour.getRandomColourForAi();
  }

  /**
   * Guesses the least used colour from the player's history.
   *
   * @return the least used colour
   */
  @Override
  public Colour guessColour() {
    return getLeastUsedColour();
  }

  /**
   * Determines the least used colour from the player's history.
   *
   * @return the least used colour
   */
  private Colour getLeastUsedColour() {
    Map<Colour, Integer> freq = new HashMap<>();
    for (Colour c : Colour.values()) {
      freq.put(c, 0);
    }

    for (Colour c : playerHistory) {
      freq.put(c, freq.get(c) + 1);
    }

    // Find the least used colour
    Colour leastUsed = Colour.RED;
    for (Colour c : Colour.values()) {
      if (freq.get(c) < freq.get(leastUsed)) {
        leastUsed = c;
      }
    }

    return leastUsed;
  }
}
