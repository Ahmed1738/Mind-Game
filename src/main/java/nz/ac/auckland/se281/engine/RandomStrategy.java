package nz.ac.auckland.se281.engine;

import nz.ac.auckland.se281.model.Colour;

/**
 * The RandomStrategy class implements an AI strategy where the AI chooses and guesses colours
 * randomly.
 */
public class RandomStrategy implements AiStrategy {

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
   * Guesses a random colour for the AI's move.
   *
   * @return a randomly guessed colour
   */
  @Override
  public Colour guessColour() {
    return Colour.getRandomColourForAi();
  }
}
