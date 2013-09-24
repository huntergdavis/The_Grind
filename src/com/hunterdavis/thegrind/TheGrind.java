package com.hunterdavis.thegrind;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;
import com.crittercism.app.Crittercism;

import com.google.ads.AdRequest;
import com.google.ads.AdView;
import com.google.example.games.basegameutils.BaseGameActivity;

public class TheGrind extends BaseGameActivity {

	Panel mypanel = null;
	InventorySQLHelper scoreData = new InventorySQLHelper(this);
	ArrayAdapter<String> m_adapterForHighScores;
	int currentScore;
	String lastHighScoreName;
	int SELECT_PICTURE = 22;
	int SELECT_SHIP = 23;

    private final String LEADERBOARD_ID = "CgkIqbeC29YJEAIQAg";
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// crittercism
		Crittercism.init(getApplicationContext(), "50c21a157e69a3763c000002");

		setContentView(R.layout.main);
		mypanel = (Panel) findViewById(R.id.SurfaceView01); 
		m_adapterForHighScores = new ArrayAdapter<String>(this,
				android.R.layout.simple_dropdown_item_1line);

		mypanel.setScoreData(scoreData);
		

		// Button pauseButton = (Button) findViewById(R.id.pauseButton);
		// pauseButton.setOnClickListener(pauseButtonListner);

		// Toast.makeText(getBaseContext(),
		// "Draw a Line Around Everything Without Touching",
		// Toast.LENGTH_LONG).show();

		// Look up the AdView as a resource and load a request.
		AdView adView = (AdView) this.findViewById(R.id.adView);
		adView.loadAd(new AdRequest());

	} // end of oncreate

	protected void onPause() {
		super.onPause();
		mypanel.terminateThread();
		System.gc();
	}

	protected void onResume() {
		super.onResume();
		if (mypanel.surfaceCreated == true) {
			mypanel.createThread(mypanel.getHolder());
		}
	}

	// this is called when the screen rotates.
	// (onCreate is no longer called when screen rotates due to manifest, see:
	// android:configChanges)
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// setContentView(R.layout.main);

		// InitializeUI();
	}

	private Cursor getScoresCursor() {
		SQLiteDatabase db = scoreData.getReadableDatabase();
		Cursor cursor = db.query(InventorySQLHelper.TABLE, null, null, null,
				null, null, InventorySQLHelper.LEVEL + " desc");
		startManagingCursor(cursor);
		return cursor;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			if (requestCode == SELECT_SHIP) {
				Uri selectedImageUri = data.getData();
				mypanel.setPlayerUri(selectedImageUri);
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

			menu.add(5060, 6969, 2, "Leaderboard");
			menu.add(5060, 2625, 2, "Spells");
			menu.add(5060, 2726, 2, "Equipment");
			SubMenu playerOptions = menu.addSubMenu("Player Options");

			int playeropts = 2825;
			playerOptions.add(playeropts, 2727, 0, "Player Image");

			playerOptions.add(5050, 5051, 1, "Toggle AutoGrind");

			
		
		
		menu.add(5060, 5052, 2, "New Game"); 
		SubMenu loadGames = menu.addSubMenu("Load Game");

		int saveGameNumbers = 2929;
		Cursor cursor = getScoresCursor();
		int iterator = 0;
		if (cursor.getCount() > 0) {
			while (cursor.moveToNext()) {
				saveGameNumbers++;
				String highscore = cursor.getString(1) + " - "
						+ cursor.getInt(20);
				loadGames.add(2929, saveGameNumbers, iterator, highscore);
				iterator++;

			}
		}

		
		return super.onCreateOptionsMenu(menu);

	}

	String getSpellName(int spellNumber) {
		Resources res = getResources();

		String[] spellsArray = res.getStringArray(R.array.spells);
		if (spellNumber > (spellsArray.length - 1)) {
			return "Unknown Spell";
		}
		return spellsArray[spellNumber];
	}

	String getEquipmentName(int spellNumber) {
		Resources res = getResources();

		String[] eqArray = res.getStringArray(R.array.equipment);
		if (spellNumber > (eqArray.length - 1)) {
			return "Unknown Item";
		}
		return eqArray[spellNumber];
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int i = item.getItemId();

		if (i == 2727) {
			changeImage();
			return true;
		}else if (i == 6969) {
            startActivityForResult(mGamesClient.getLeaderboardIntent(LEADERBOARD_ID), 187);
        } else if (i == 2625) {
			ArrayAdapter<String> m_adapterForSpells = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line);;
			
			for (int j = 0; j < mypanel.player1.spells.size(); j++) {
				m_adapterForSpells.add((String)getSpellName(mypanel.player1.spells.get(j)));
			}

			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Spells");
			builder.setAdapter(m_adapterForSpells, new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int item) {
							// Do something with the selection
							dialog.dismiss();
						}
					});

			
			AlertDialog alert = builder.create();
			alert.show();
			
		} 
		else if (i == 2726) {

			ArrayAdapter<String> m_adapterForEq = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line);;
			
			for (int j = 0; j < mypanel.player1.equipment.size(); j++) {

				m_adapterForEq.add(getEquipmentName(mypanel.player1.equipment.get(j)));
			}
			
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Equipment");
			builder.setAdapter(m_adapterForEq, new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int item) {
							// Do something with the selection
							dialog.dismiss();
						}
					});

			
			AlertDialog alert = builder.create();
			alert.show();
			
			
		}
		else if ((i > 2929) && (i < 5050)) {
			int newitem = i - 2930;
			if (i > 0) {
				loadGame(newitem);
				return true;
			}
		} else if (i == 5051) {
			if (mypanel.autoGrind == true) {
				mypanel.autoGrind = false;
			} else {
				mypanel.autoGrind = true;
			}
		} else if (i == 5052) {
			newGame();
		}
		return false;
	}

	public void newGame() {
		AlertDialog.Builder alert = new AlertDialog.Builder(this);

		alert.setTitle("Your Name?");
		alert.setMessage("Please Enter Your Name For the High Score List");

		// Set an EditText view to get user input
		final EditText input = new EditText(this);
		input.setText(lastHighScoreName);
		alert.setView(input);

		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				String tempName = input.getText().toString().trim();

				if (tempName.length() < 1) {
					tempName = "Unnamed Player";
				}
				lastHighScoreName = tempName;
				mypanel.reset();
				mypanel.changeName(lastHighScoreName);
				Toast.makeText(getBaseContext(), "Spin the Wheel to Grind",
						Toast.LENGTH_LONG).show();
			}

		});

		alert.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						// Canceled.
						mypanel.reset();
						Toast.makeText(getBaseContext(),
								"Spin the Wheel to Grind", Toast.LENGTH_LONG)
								.show();
					}
				});

		
		alert.show();
	}

	public void loadGame(int item) {
		// Do something with the selection
		Player player = new Player();
		Cursor cursor = getScoresCursor();
		if (cursor.getCount() > item) {
			cursor.moveToPosition(item);
		} else {
			return;
		}
		player.name = cursor.getString(1);
		player.experience = cursor.getInt(2);
		player.health = cursor.getInt(3);
		player.currentHealth = player.health;
		player.mana = cursor.getInt(4);
		player.currentMana = player.mana;
		player.currentSword = cursor.getInt(5);
		player.currentArmor = cursor.getInt(6);
		player.currentHelmet = cursor.getInt(7);
		player.gold = cursor.getInt(8);
		player.maritalStatus = cursor.getInt(9);
		player.speed = cursor.getInt(10);
		player.age = cursor.getInt(11);
		player.toughness = cursor.getInt(12);
		player.intelligence = cursor.getInt(13);
		player.wisdom = cursor.getInt(14);

		String allequipment = cursor.getString(15);
		String[] allequiparray = allequipment.split(" ");
		for (int i = 0; i < allequiparray.length; i++) {
			String equip = allequiparray[i];
			if (equip.equalsIgnoreCase(" ")) {

			} else {
				if (equip.length() > 0) {
					player.equipment.add(Integer.valueOf(equip));
				}
			}
		}

		String allspells = cursor.getString(16);
		String[] allspellsarray = allspells.split(" ");
		for (int i = 0; i < allspellsarray.length; i++) {
			String spell = allspellsarray[i];
			if (spell.equalsIgnoreCase(" ")) {

			} else {
				if (spell.length() > 0) {
					player.spells.add(Integer.valueOf(spell));
				}
			}
		}

		player.questNum = cursor.getInt(17);
		player.subQuestNum = cursor.getInt(18);
		player.statusNum = cursor.getInt(19);
		player.level = cursor.getInt(20);

		try {
			player.playerUri = Uri.parse(cursor.getString(21));
		} catch (Exception e) {
			player.playerUri = null;
		}

		mypanel.loadGame(player);
	}

	public void changeImage() {
		if (mypanel.introScreenOver == false) {
			return;
		}
		// in onCreate or any event where your want the user to
		// select a file
		Intent intent = new Intent();
		intent.setType("image/*");
		intent.setAction(Intent.ACTION_GET_CONTENT);
		startActivityForResult(
				Intent.createChooser(intent, "Select Source Photo"),
				SELECT_SHIP);
	}

}