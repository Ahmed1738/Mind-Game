package nz.ac.auckland.se281.engine;

import nz.ac.auckland.se281.model.Colour;

/**
 * The AvoidLastStrategy class implements an AI strategy where the AI avoids guessing the player's
 * last chosen colour.
 */
public class AvoidLastStrategy implements AiStrategy {

  /** The last colour chosen by the player. */
  private Colour lastPlayerColour;

  /**
   * Constructs an AvoidLastStrategy with the specified last player colour.
   *
   * @param lastPlayerColour the last colour chosen by the player
   */
  public AvoidLastStrategy(Colour lastPlayerColour) {
    this.lastPlayerColour = lastPlayerColour;
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
   * Guesses a colour, excluding the player's last chosen colour.
   *
   * @return a randomly chosen colour excluding the last player colour
   */
  @Override
  public Colour guessColour() {
    return Colour.getRandomColourExcluding(lastPlayerColour);
  }
}
