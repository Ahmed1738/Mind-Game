package nz.ac.auckland.se281.engine;

import java.util.ArrayList;
import java.util.List;
import nz.ac.auckland.se281.Main.Difficulty;
import nz.ac.auckland.se281.cli.MessageCli;
import nz.ac.auckland.se281.cli.Utils;
import nz.ac.auckland.se281.model.Colour;

/**
 * The Game class manages the core gameplay logic, including player and AI moves, scoring, and round
 * progression. It supports multiple difficulty levels and tracks game state.
 */
public class Game {

  /** The name of the AI player. */
  public static final String AI_NAME = "HAL-9000";

  /** The name of the human player. */
  private String playerName;

  /** The total number of rounds in the game. */
  private int totalRounds;

  /** The current round number. */
  private int currentRound = 0;

  /** The player's total score. */
  private int playerScore = 0;

  /** The AI's total score. */
  private int aiScore = 0;

  /** The strategy used by the AI based on the difficulty level. */
  private DifficultyAiStrategy aiStrategy;

  /** The last colour chosen by the player. */
  private Colour lastPlayerColour;

  /** The points scored by the AI in the last round. */
  private int lastAiRoundPoints = 0;

  /** A history of the player's chosen colours. */
  private final List<Colour> playerHistory = new ArrayList<>();

  /** Indicates whether a game is currently in progress. */
  private boolean gameInProgress = false;

  /**
   * Starts a new game with the specified difficulty, number of rounds, and player options.
   *
   * @param difficulty the difficulty level of the game
   * @param numRounds the total number of rounds
   * @param options an array containing player-specific options (e.g., name)
   */
  public void newGame(Difficulty difficulty, int numRounds, String[] options) {
    // Initialize game state
    this.playerName = options[0];
    this.totalRounds = numRounds;
    this.currentRound = 0;
    this.playerScore = 0;
    this.aiScore = 0;
    this.lastPlayerColour = null;
    this.lastAiRoundPoints = 0;
    this.playerHistory.clear();
    this.gameInProgress = true;

    // Welcome the player and set the AI strategy
    MessageCli.WELCOME_PLAYER.printMessage(playerName);
    aiStrategy = AiStrategyFactory.getDifficultyAi(difficulty, playerHistory);
  }

  /**
   * Executes a single round of the game, including player and AI moves, scoring, and strategy
   * updates.
   */
  public void play() {
    // Ensure the game is in progress
    if (!gameInProgress) {
      MessageCli.GAME_NOT_STARTED.printMessage();
      return;
    }

    // End the game if all rounds are completed
    if (currentRound >= totalRounds) {
      return;
    }

    currentRound++;

    // Update the AI's strategy based on the previous round
    aiStrategy.updateAfterRound(currentRound, lastAiRoundPoints, lastPlayerColour);

    // Determine the power colour for this round
    Colour powerColour = null;
    if (currentRound % 3 == 0) {
      powerColour = Colour.getRandomColourForPowerColour();
    }

    // Announce the start of the round
    MessageCli.START_ROUND.printMessage(String.valueOf(currentRound), String.valueOf(totalRounds));

    // Get the player's input
    Colour playerColour;
    Colour playerGuess;
    while (true) {
      MessageCli.ASK_HUMAN_INPUT.printMessage();
      String[] tokens = Utils.scanner.nextLine().trim().split("\\s+");
      if (tokens.length != 2) {
        MessageCli.INVALID_HUMAN_INPUT.printMessage();
        continue;
      }
      playerColour = Colour.fromInput(tokens[0]);
      playerGuess = Colour.fromInput(tokens[1]);
      if (playerColour == null || playerGuess == null) {
        MessageCli.INVALID_HUMAN_INPUT.printMessage();
        continue;
      }
      break;
    }

    // Get the AI's move and guess
    Colour aiColour = aiStrategy.chooseColour();
    Colour aiGuess = aiStrategy.guessColour();

    // Print the moves of both players
    MessageCli.PRINT_INFO_MOVE.printMessage(AI_NAME, aiColour.name(), aiGuess.name());
    MessageCli.PRINT_INFO_MOVE.printMessage(playerName, playerColour.name(), playerGuess.name());

    // Announce the power colour if applicable
    if (powerColour != null) {
      MessageCli.PRINT_POWER_COLOUR.printMessage(powerColour.name());
    }

    // Calculate points for the round
    int pointsPlayer = 0;
    int pointsAi = 0;
    if (playerGuess == aiColour) {
      pointsPlayer += 1;
      if (powerColour != null && playerGuess == powerColour) {
        pointsPlayer += 2;
      }
    }
    if (aiGuess == playerColour) {
      pointsAi += 1;
      if (powerColour != null && aiGuess == powerColour) {
        pointsAi += 2;
      }
    }

    // Update scores
    playerScore += pointsPlayer;
    aiScore += pointsAi;

    // Print the outcome of the round
    MessageCli.PRINT_OUTCOME_ROUND.printMessage(playerName, pointsPlayer);
    MessageCli.PRINT_OUTCOME_ROUND.printMessage(AI_NAME, pointsAi);

    // Update game state
    playerHistory.add(playerColour);
    lastPlayerColour = playerColour;
    lastAiRoundPoints = pointsAi;

    // End the game if all rounds are completed
    if (currentRound == totalRounds) {
      MessageCli.PRINT_PLAYER_POINTS.printMessage(playerName, playerScore);
      MessageCli.PRINT_PLAYER_POINTS.printMessage(AI_NAME, aiScore);
      MessageCli.PRINT_END_GAME.printMessage();

      // Announce the winner
      if (playerScore > aiScore) {
        MessageCli.PRINT_WINNER_GAME.printMessage(playerName);
      } else if (aiScore > playerScore) {
        MessageCli.PRINT_WINNER_GAME.printMessage(AI_NAME);
      } else {
        MessageCli.PRINT_TIE_GAME.printMessage();
      }

      gameInProgress = false;
    }
  }

  /** Displays the current statistics of the game, including player and AI scores. */
  public void showStats() {
    if (!gameInProgress) {
      MessageCli.GAME_NOT_STARTED.printMessage();
      return;
    }

    MessageCli.PRINT_PLAYER_POINTS.printMessage(playerName, playerScore);
    MessageCli.PRINT_PLAYER_POINTS.printMessage(AI_NAME, aiScore);
  }
}
