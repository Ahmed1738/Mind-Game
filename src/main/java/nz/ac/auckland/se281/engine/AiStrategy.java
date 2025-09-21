package nz.ac.auckland.se281.engine;

import nz.ac.auckland.se281.model.Colour;

/**
 * The AiStrategy interface defines the methods that all AI strategies must implement. These include
 * choosing a colour and guessing a colour.
 */
public interface AiStrategy {

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
}
