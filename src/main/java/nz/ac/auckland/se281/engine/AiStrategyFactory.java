package nz.ac.auckland.se281.engine;

import java.util.List;
import nz.ac.auckland.se281.Main.Difficulty;
import nz.ac.auckland.se281.model.Colour;

/**
 * The AiStrategyFactory class provides a method to create AI strategies based on the selected
 * difficulty level.
 */
public class AiStrategyFactory {

  /**
   * Returns an AI strategy instance based on the specified difficulty level.
   *
   * @param difficulty the difficulty level of the game
   * @param playerHistory the history of colours chosen by the player
   * @return the AI strategy instance
   */
  public static DifficultyAiStrategy getDifficultyAi(
      Difficulty difficulty, List<Colour> playerHistory) {

    // Determine the AI strategy based on the difficulty level
    switch (difficulty) {
      case EASY:
        // For EASY difficulty, use the EasyAi strategy
        return new EasyAi();

      case MEDIUM:
        // For MEDIUM difficulty, use the MediumAi strategy
        return new MediumAi();

      case HARD:
        // For HARD difficulty, use the HardAi strategy, which requires player history
        return new HardAi(playerHistory);

      default:
        // Default to EasyAi if the difficulty level is not recognized
        return new EasyAi();
    }
  }
}
