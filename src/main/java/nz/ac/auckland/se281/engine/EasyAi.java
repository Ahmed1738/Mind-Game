package nz.ac.auckland.se281.engine;

import nz.ac.auckland.se281.model.Colour;

/**
 * The EasyAi class implements the AI logic for the "EASY" difficulty level. It uses a random
 * strategy throughout the game.
 */
public class EasyAi implements DifficultyAiStrategy {

  /** The strategy used by the AI. */
  private AiStrategy strategy = new RandomStrategy();

  /**
   * Chooses a colour for the AI's move.
   *
   * @return the chosen colour
   */
  @Override
  public Colour chooseColour() {
    return strategy.chooseColour();
  }

  /**
   * Guesses a colour for the AI's move.
   *
   * @return the guessed colour
   */
  @Override
  public Colour guessColour() {
    return strategy.guessColour();
  }

  /**
   * Updates the AI's strategy after each round. No updates are made for the EASY difficulty.
   *
   * @param round the current round number
   * @param lastAiPoints the points scored by the AI in the last round
   * @param lastPlayerColour the last colour chosen by the player
   */
  @Override
  public void updateAfterRound(int round, int lastAiPoints, Colour lastPlayerColour) {}
}
