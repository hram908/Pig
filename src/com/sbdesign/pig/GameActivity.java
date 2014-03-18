package com.sbdesign.pig;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class GameActivity extends Activity implements OnClickListener {

	TextView playerOneTv, playerTwoTv, scoreOne, scoreTwo, tempScore,
			playerTurn;

	private static int SCORE_PLAYER_ONE = 0;
	private static int SCORE_PLAYER_TWO = 0;

	private static int DICE_ROLL = 0;
	private static int TEMP_SCORE = 0;

	private static String PLAYER_ONE_NAME;
	private static String PLAYER_TWO_NAME;

	private int[] mImages = new int[] { R.drawable.one, R.drawable.two,
			R.drawable.three, R.drawable.four, R.drawable.five, R.drawable.six };

	Button holdButton, rollButton;

	ImageView diceImage, playerOneIndicator, playerTwoIndicator;

	private static final Random rgenerator = new Random();

	boolean playerOne = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
		// Show the Up button in the action bar.

        PLAYER_ONE_NAME = getString(R.string.player_one_name);
        PLAYER_TWO_NAME = getString(R.string.player_two_name);
		setupActionBar();

		initializeWidgets();

		newGameSetup();

	}

	private void initializeWidgets() {
		// Initialize all widgets
		Typeface tf = Typeface.createFromAsset(getAssets(), "freckle.ttf");

		holdButton = (Button) findViewById(R.id.buttonHold);
		rollButton = (Button) findViewById(R.id.buttonRollDice);
		playerOneTv = (TextView) findViewById(R.id.textViewPlayerOneLabel);
		playerTwoTv = (TextView) findViewById(R.id.textViewPlayerTwoLabel);
		playerTurn = (TextView) findViewById(R.id.textViewPlayerTurn);
		scoreOne = (TextView) findViewById(R.id.textViewsScoreOne);
		scoreTwo = (TextView) findViewById(R.id.textViewScoreTwo);
		tempScore = (TextView) findViewById(R.id.textViewTempScore);
		diceImage = (ImageView) findViewById(R.id.imageView1);
		playerOneIndicator = (ImageView) findViewById(R.id.imageViewPlayerOne);
		playerTwoIndicator = (ImageView) findViewById(R.id.imageViewPlayerTwo);

		playerOneTv.setTypeface(tf);
		playerOneTv.setTextColor(Color.BLUE);
		playerTwoTv.setTypeface(tf);
		playerTwoTv.setTextColor(Color.RED);
		scoreOne.setTypeface(tf);
		scoreOne.setTextColor(Color.BLUE);
		scoreTwo.setTypeface(tf);
		scoreTwo.setTextColor(Color.RED);
		playerTurn.setTypeface(tf);
		tempScore.setTypeface(tf);

		holdButton.setOnClickListener(this);
		rollButton.setOnClickListener(this);
	}

	private void newGameSetup() {
		// Creates a new game
		switchPlayersIndicator();
		resetScores();
		setupPlayers();

	}

	private void switchPlayersIndicator() {
		// Switch Player Image Indicator
		if (playerOne) {
			playerOneIndicator.setVisibility(View.VISIBLE);
			playerTwoIndicator.setVisibility(View.INVISIBLE);
		} else {
			playerOneIndicator.setVisibility(View.INVISIBLE);
			playerTwoIndicator.setVisibility(View.VISIBLE);
		}
	}

	private void resetScores() {
		//

		SCORE_PLAYER_ONE = 0;
		SCORE_PLAYER_TWO = 0;
        TEMP_SCORE = 0;
        DICE_ROLL = 0;
		scoreOne.setText(String.valueOf(SCORE_PLAYER_ONE));
		scoreTwo.setText(String.valueOf(SCORE_PLAYER_TWO));
		tempScore.setText("");
		playerOne = true;

	}

	private void setupPlayers() {
		// Setup Player one and two

		final EditText playerOne = new EditText(this);
		playerOne.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        playerOne.setText(PLAYER_ONE_NAME);

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(R.string.player_one_title)
				.setMessage(R.string.enter_your_name).setView(playerOne);
		builder.setPositiveButton(android.R.string.ok,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// Sets player label name
						PLAYER_ONE_NAME = playerOne.getText().toString();
						playerOneTv.setText(PLAYER_ONE_NAME + " :");
						setupPlayerTwo();
					}

				});
		AlertDialog dialog = builder.create();
		dialog.show();

	}

	private void setupPlayerTwo() {
		// Setup player two
		final EditText playerTwo = new EditText(this);
		playerTwo.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        playerTwo.setText(PLAYER_TWO_NAME);

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(R.string.player_two_title)
				.setMessage(R.string.enter_your_name).setView(playerTwo);
		builder.setPositiveButton(android.R.string.ok,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// Set player label name
						PLAYER_TWO_NAME = playerTwo.getText().toString();
						playerTwoTv.setText(PLAYER_TWO_NAME + " :");

						playerTurn.setText(PLAYER_ONE_NAME
								+ getString(R.string.your_turn));
						playerTurn.setTextColor(Color.BLUE);

					}

				});
		AlertDialog dialog = builder.create();
		dialog.show();

	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.itemNewGame:
			newGameSetup();
			break;

		case R.id.itemRules:
			// Show the games rules in a Dialog
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle(R.string.pig_rules)
					.setMessage(R.string.game_rules);
			builder.setPositiveButton(android.R.string.ok, null);
			AlertDialog dialog = builder.create();
			dialog.show();
			break;

		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		// Handles the Hold and Roll Buttons
		switch (v.getId()) {

		// Manage the Hold Button
		case R.id.buttonHold:

			if (TEMP_SCORE == 0) {

				tempScore.setText(R.string.try_at_least_one_roll_);
				Animation anim = AnimationUtils.loadAnimation(this,
						R.anim.slideanim);
				tempScore.startAnimation(anim);

			} else {
				checkWhoHold();
				switchPlayersIndicator();
			}
			break;

		// Manage the Roll Dice Button
		case R.id.buttonRollDice:
			Integer image = mImages[rgenerator.nextInt(mImages.length)];
			diceImage.setImageResource(image);

			int position = getPosition(mImages, image);

			Animation anim = AnimationUtils.loadAnimation(this,
					R.anim.slideanim);
			diceImage.startAnimation(anim);

			DICE_ROLL = position + 1;

			checkPlayer();
			break;

		}
	}

	private void checkWhoHold() {
		// Checks which player hit the hold button

		if (playerOne) {
			manageHoldPlayerOne();
		} else {
			manageHoldPlayerTwo();
		}
	}

	private void manageHoldPlayerTwo() {
		// Manage player two hold
		SCORE_PLAYER_TWO = SCORE_PLAYER_TWO + TEMP_SCORE;

		slideOut(scoreTwo, tempScore);
		slideInTwo(scoreTwo);

		if (SCORE_PLAYER_TWO >= 100) {
			// Winner
			playerOne = true;
			switchPlayersIndicator();
            playWinSound();
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle(R.string.we_have_a_winner_).setMessage(
					PLAYER_TWO_NAME + getString(R.string.congratulations));
			builder.setPositiveButton(R.string.yes,
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface arg0, int arg1) {
							// New Game
							newGameSetup();

						}
					});
			builder.setNegativeButton(R.string.no,
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface arg0, int arg1) {
							// Quit Game
							finish();

						}
					});
			AlertDialog dialog = builder.create();
			dialog.show();

		}else{
		playerOne = true;
		TEMP_SCORE = 0;
		playerTurn.setText(PLAYER_ONE_NAME + getString(R.string.your_turn));
		playerTurn.setTextColor(Color.BLUE);
		switchPlayersIndicator();
		}
	}

	private void manageHoldPlayerOne() {
		// Manage player one hold
		SCORE_PLAYER_ONE = SCORE_PLAYER_ONE + TEMP_SCORE;

		slideOut(scoreOne, tempScore);
		slideInOne(scoreOne);

		if (SCORE_PLAYER_ONE >= 100) {
			// Winner
			playerOne = true;
			switchPlayersIndicator();
            playWinSound();
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle(R.string.we_have_a_winner_).setMessage(
					PLAYER_ONE_NAME + getString(R.string.congratulations));
			builder.setPositiveButton(R.string.yes,
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface arg0, int arg1) {
							// New Game
							newGameSetup();

						}
					});
			builder.setNegativeButton(R.string.no,
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface arg0, int arg1) {
							// Quit Game
							finish();

						}
					});
			AlertDialog dialog = builder.create();
			dialog.show();

		}else{
		playerOne = false;
		TEMP_SCORE = 0;
		playerTurn.setText(PLAYER_TWO_NAME + getString(R.string.your_turn));
		playerTurn.setTextColor(Color.RED);
		switchPlayersIndicator();
		}
	}

    private void playWinSound() {
        // Play sound when user win
        MediaPlayer player = MediaPlayer.create(this, R.raw.yahoo);
        player.start();
    }

    private void slideOut(final TextView scoreOne2, final TextView tempScore2) {
		// Animate both view

		final Animation animation = AnimationUtils.loadAnimation(this,
				android.R.anim.slide_out_right);
        playSwooshSound();
		scoreOne2.startAnimation(animation);
		tempScore2.startAnimation(animation);
		animation.setAnimationListener(new AnimationListener() {

			public void onAnimationStart(Animation arg0) {
				// TODO Auto-generated method stub

			}

			public void onAnimationRepeat(Animation arg0) {
				// TODO Auto-generated method stub

			}

			public void onAnimationEnd(Animation arg0) {
				// TODO Auto-generated method stub

				tempScore.setText("");

			}
		});

	}

    private void playSwooshSound() {
        // Play sound for the swipe animation
        MediaPlayer player = MediaPlayer.create(this,R.raw.swoosh);
        player.start();
    }

    protected void slideInOne(TextView scoreOne2) {
		// TODO Auto-generated method stub
		scoreOne.setText(String.valueOf(SCORE_PLAYER_ONE));
		final Animation animation = AnimationUtils.loadAnimation(this,
				R.anim.slideanim);
		scoreOne2.startAnimation(animation);
		animation.setAnimationListener(new AnimationListener() {

			public void onAnimationStart(Animation arg0) {
				// TODO Auto-generated method stub

			}

			public void onAnimationRepeat(Animation arg0) {
				// TODO Auto-generated method stub

			}

			public void onAnimationEnd(Animation arg0) {
				// TODO Auto-generated method stub

			}
		});
	}

	protected void slideInTwo(TextView scoreOne2) {
		// TODO Auto-generated method stub
		scoreTwo.setText(String.valueOf(SCORE_PLAYER_TWO));
		final Animation animation = AnimationUtils.loadAnimation(this,
				R.anim.slideanim);
		scoreOne2.startAnimation(animation);
		animation.setAnimationListener(new AnimationListener() {

			public void onAnimationStart(Animation arg0) {
				// TODO Auto-generated method stub

			}

			public void onAnimationRepeat(Animation arg0) {
				// TODO Auto-generated method stub

			}

			public void onAnimationEnd(Animation arg0) {
				// TODO Auto-generated method stub

			}
		});
	}

	private void checkPlayer() {
		// Check who's player turn is
		if (playerOne) {
			playerOneTurn();

		} else {
			playerTwoTurn();
		}
	}

	private void playerTwoTurn() {
		// Player Two Logic

		if (DICE_ROLL == 1 && TEMP_SCORE == 0) {
            playPigSound();
			tempScore.setText(R.string.no_points);
			playerOne = true;
			playerTurn.setText(PLAYER_ONE_NAME + getString(R.string.your_turn));
			playerTurn.setTextColor(Color.BLUE);
			switchPlayersIndicator();
		} else if (DICE_ROLL == 1) {
            playPigSound();
			tempScore.setText(R.string.lose_all_your_points);
			TEMP_SCORE = 0;
			playerOne = true;
			playerTurn.setText(PLAYER_ONE_NAME + getString(R.string.your_turn));
			playerTurn.setTextColor(Color.BLUE);
			switchPlayersIndicator();
		} else {
            playDingSound();
			TEMP_SCORE = TEMP_SCORE + DICE_ROLL;
			tempScore.setText("+" + TEMP_SCORE);
		}

	}

    private void playPigSound() {
        // Play pig sound when user fail
        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.pig);
        mediaPlayer.start();
    }

    private void playerOneTurn() {
		// Player One Logic

		if (DICE_ROLL == 1 && TEMP_SCORE == 0) {
            playPigSound();
			tempScore.setText(R.string.no_points);
			playerOne = false;
			playerTurn.setText(PLAYER_TWO_NAME + getString(R.string.your_turn));
			playerTurn.setTextColor(Color.RED);
			switchPlayersIndicator();
		} else if (DICE_ROLL == 1) {
            playPigSound();
			tempScore.setText(R.string.lose_all_your_points);
			TEMP_SCORE = 0;
			playerOne = false;
			playerTurn.setText(PLAYER_TWO_NAME + getString(R.string.your_turn));
			playerTurn.setTextColor(Color.RED);
			switchPlayersIndicator();
		} else {
            playDingSound();
			TEMP_SCORE = TEMP_SCORE + DICE_ROLL;
			tempScore.setText("+" + TEMP_SCORE);
		}

	}

    private void playDingSound() {
        // Play ding sound when user succeed
        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.ding);
        mediaPlayer.start();

    }

    public static int getPosition(int[] array, int num) {
		for (int i = 0; i < array.length; i++) {
			if (array[i] == num) {
				return (i);
			}
		}
		return (-1);
	}

}
