package nz.ac.auckland.se281.engine;

import nz.ac.auckland.se281.model.Colour;

/**
 * The DifficultyAiStrategy interface defines the methods that all AI strategies for different
 * difficulty levels must implement. These include choosing a colour, guessing a colour, and
 * updating the strategy after each round.
 */
public interface DifficultyAiStrategy {

  /**
   * Chooses a colour for the AI's move.
   *
   * @return the chosen colour
   */
  Colour chooseColour();

  /**
   * Guesses a colour for the AI's move.
   *
   * @return the guessed colour
   */
  Colour guessColour();

  /**
   * Updates the AI's strategy after each round based on the round number, the AI's performance, and
   * the player's last chosen colour.
   *
   * @param round the current round number
   * @param lastAiPoints the points scored by the AI in the last round
   * @param lastPlayerColour the last colour chosen by the player
   */
  void updateAfterRound(int round, int lastAiPoints, Colour lastPlayerColour);
}
