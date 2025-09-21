package nz.ac.auckland.se281.engine;

import java.util.List;
import nz.ac.auckland.se281.model.Colour;

/**
 * The HardAi class implements the AI logic for the "HARD" difficulty level. It dynamically switches
 * strategies based on game performance.
 */
public class HardAi implements DifficultyAiStrategy {

  /** The current strategy being used by the AI. */
  private AiStrategy strategy;

  /** The history of colours chosen by the player. */
  private final List<Colour> playerHistory;

  /** Indicates whether the AI is currently using the LeastUsedStrategy. */
  private boolean isUsingLeastUsed = false;

  /**
   * Constructs a HardAi instance with the specified player history.
   *
   * @param playerHistory the history of colours chosen by the player
   */
  public HardAi(List<Colour> playerHistory) {
    this.playerHistory = playerHistory;
    this.strategy = new RandomStrategy();
  }

  /**
   * Chooses a colour for the AI's move based on the current strategy.
   *
   * @return the chosen colour
   */
  @Override
  public Colour chooseColour() {
    return strategy.chooseColour();
  }

  /**
   * Guesses a colour for the AI's move based on the current strategy.
   *
   * @return the guessed colour
   */
  @Override
  public Colour guessColour() {
    return strategy.guessColour();
  }

  /**
   * Updates the AI's strategy after each round based on the round number and performance.
   *
   * @param round the current round number
   * @param lastAiPoints the points scored by the AI in the last round
   * @param lastPlayerColour the last colour chosen by the player
   */
  @Override
  public void updateAfterRound(int round, int lastAiPoints, Colour lastPlayerColour) {
    // For the first two rounds, use the RandomStrategy
    if (round <= 2) {
      strategy = new RandomStrategy();
      isUsingLeastUsed = false;

    } else if (round == 3) {
      // In round 3, switch to the LeastUsedStrategy
      strategy = new LeastUsedStrategy(playerHistory);
      isUsingLeastUsed = true;

    } else {
      // After round 3, switch strategies based on performance
      if (lastAiPoints == 0) {
        // If the AI scored 0 points in the last round
        if (isUsingLeastUsed) {
          // Switch to AvoidLastStrategy if currently using LeastUsedStrategy
          strategy = new AvoidLastStrategy(lastPlayerColour);
          isUsingLeastUsed = false;
        } else {
          // Switch back to LeastUsedStrategy if not using it
          strategy = new LeastUsedStrategy(playerHistory);
          isUsingLeastUsed = true;
        }

      } else {
        // If the AI scored points in the last round
        if (isUsingLeastUsed) {
          // Continue using LeastUsedStrategy
          strategy = new LeastUsedStrategy(playerHistory);
        } else {
          // Continue using AvoidLastStrategy
          strategy = new AvoidLastStrategy(lastPlayerColour);
        }
      }
    }
  }
}
