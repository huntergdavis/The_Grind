package com.hunterdavis.thegrind;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.net.Uri;

public class Player {

	String name;
	Uri playerUri;
	int health;
	int experience;
	int mana;
	int currentHealth;
	int currentMana;
	int currentSword;
	int currentArmor;
	int currentHelmet;
	int level;
	int gold;
	int maritalStatus;
	int speed;
	int age;
	int toughness;
	int intelligence;
	int wisdom;
	List<Integer> equipment;
	List<Integer> spells;
	int questNum;
	int subQuestNum;
	int subQuestsDefeatedThisQuest;
	int statusNum;
	
	// constant
	int numSpellDescriptions = 300;
	int maxNumItems = 100;

	// this class needs some randomness
	Random myPlayerRandom = new Random();

	Player() {
		name = "Player 1";
		playerUri = null;
		experience = 0;
		health = myPlayerRandom.nextInt(6) + 3;
		mana = myPlayerRandom.nextInt(5) + 3;
		subQuestsDefeatedThisQuest = 0;
		currentHealth = health;
		currentMana = mana;
		currentSword = 0;
		currentArmor = 0;
		currentHelmet = 0;
		level = 1;
		gold = 0;
		maritalStatus = 0;
		speed = 1;
		age = myPlayerRandom.nextInt(10) + 10;
		toughness = 1;
		intelligence = 1;
		wisdom = 1;
		equipment = new ArrayList<Integer>();
		spells = new ArrayList<Integer>();
		questNum = myPlayerRandom.nextInt(10);
		subQuestNum = myPlayerRandom.nextInt(6);
		statusNum = 0;
	}

	Player(Player p2) {
		name = p2.name;
		subQuestsDefeatedThisQuest = p2.subQuestsDefeatedThisQuest;
		experience = p2.experience;
		playerUri = p2.playerUri;
		health = p2.health;
		mana = p2.mana;
		currentHealth = p2.currentHealth;
		currentMana = p2.currentMana;
		currentSword = p2.currentSword;
		currentArmor = p2.currentArmor;
		currentHelmet = p2.currentHelmet;
		level = p2.level;
		gold = p2.gold;
		maritalStatus = p2.maritalStatus;
		speed = p2.speed;
		age = p2.age;
		toughness = p2.toughness;
		intelligence = p2.intelligence;
		wisdom = p2.wisdom;
		equipment = p2.equipment;
		spells = p2.spells;
		questNum = p2.questNum;
		subQuestNum = p2.subQuestNum;
		statusNum = p2.statusNum;
	}

	void Heal() {
		currentHealth = health;
		currentMana = mana;
	}

	void SellAllEquipment() {
		for (int i = equipment.size() - 1; i >= 0; i--) {
			int goldModify = myPlayerRandom.nextInt(10) + 1;
			gold += i * goldModify;
		}
		equipment.clear();
	}

	void newEquipmentItem(int outOfHowMany) {
		int equipmentNum = myPlayerRandom.nextInt(outOfHowMany);
		equipment.add(equipmentNum);
		
		if(equipment.size() > maxNumItems) {
			SellAllEquipment();
		}
		
		// we got a new helmet or something
		if(myPlayerRandom.nextInt(100) > 90) {
			int mycase = myPlayerRandom.nextInt(3);
			switch (mycase) {
			case 0:
				ArmorUp();
				break;
			case 1:
				HelmetUp();
				break;
			case 2:
				swordUp();
				break;
			default:
				break;
			}
		}
	}

	void newMagicSpell(int outOfHowMany) {
		int equipmentNum = myPlayerRandom.nextInt(outOfHowMany);
		spells.add(equipmentNum);

        int spellBookSize = spells.size();

        if(spellBookSize > 0) {
            if (spellBookSize > 15 && spellBookSize < 50) {
                Panel.getAnAchievementDirectly(R.string.achievement_spell1);
            } else if (spellBookSize > 49) {
                Panel.getAnAchievementDirectly(R.string.achievement_spell2);
            }
        }
	}

	void newQuestNum(int outOfHowMany) {
		int qn = myPlayerRandom.nextInt(outOfHowMany);
		questNum = qn;
	}

	void newSubQuestNum(int outOfHowMany) {
		int sqn = myPlayerRandom.nextInt(outOfHowMany);
		subQuestNum = sqn;
	}

	int swordUp() {
		currentSword++;

        if (currentSword > 3 && currentSword < 5) {
            Panel.getAnAchievementDirectly(R.string.achievement_sword1);
        } else if (currentSword > 4 && currentSword < 8) {
            Panel.getAnAchievementDirectly(R.string.achievement_sword2);
        } else if (currentSword > 7) {
            Panel.getAnAchievementDirectly(R.string.achievement_sword3);
        }

		return currentSword;
	}

	int ArmorUp() {
		currentArmor++;

        Panel.getAnAchievementDirectly(R.string.achievement_armor1);
        if(currentArmor > 5) {
            Panel.getAnAchievementDirectly(R.string.achievement_armor2);
        }
		return currentArmor;
	}

	int HelmetUp() {
		currentHelmet++;
        if(currentHelmet > 5) {
            Panel.getAnAchievementDirectly(R.string.achievement_helmet);
        }

		return currentHelmet;
	}

	int experienceUp(int up) {
		experience += up;

		if (level < experienceLevel()) {
			LevelUp();
		}
		return experience;
	}

	int experienceLevel() {
		if (experience < 20) {
			return 1;
		} else if (experience < 40) {
			return 2;
		} else if (experience < 80) {
			return 3;
		} else if (experience < 160) {
			return 4;
		} else if (experience < 210) {
			return 5;
		}  else if (experience < 260) {
			return 6;
		}  else if (experience < 300) {
			return 7;
		}  else if (experience < 350) {
			return 8;
		}  else if (experience < 450) {
			return 9;
		}  else if (experience < 1000) {
			return 10;
		}  else {
			return (10 + (experience / 1000));
		}
	}

	void changeName(String newName) {
		name = newName;
	}

	void changeUri(Uri newUri) {
		playerUri = newUri;
	}

	int LevelUp() {
		level++;

        if (level > 9 && level < 100) {
            Panel.getAnAchievementDirectly(R.string.achievement_levelten);
        } else if (level > 99 && level < 1000) {
            Panel.getAnAchievementDirectly(R.string.achievement_levelonehundred);
        } else if (level > 1000) {
            Panel.getAnAchievementDirectly(R.string.achievement_levelonethousand);
        }

        health += myPlayerRandom.nextInt(6);

        if (health > 30 && health <= 100) {
            Panel.getAnAchievementDirectly(R.string.achievement_health1);
        } else if (health > 100) {
            Panel.getAnAchievementDirectly(R.string.achievement_health2);
        }

		mana += myPlayerRandom.nextInt(5);

        if (mana > 15 && mana < 50) {
            Panel.getAnAchievementDirectly(R.string.achievement_magic1);
        } else if (mana > 50) {
            Panel.getAnAchievementDirectly(R.string.achievement_magic2);
        }

		currentHealth = health;
		currentMana = mana;
		speed += myPlayerRandom.nextInt(2);

        if (speed > 9 && speed < 50) {
            Panel.getAnAchievementDirectly(R.string.achievement_speed1);
        } else if (speed > 49 && speed < 100) {
            Panel.getAnAchievementDirectly(R.string.achievement_speed2);
        } else if (speed > 99) {
            Panel.getAnAchievementDirectly(R.string.achievement_speed3);
        }


		age++;

        if(age > 1000) {
            Panel.getAnAchievementDirectly(R.string.achievement_veryold);
        }

		toughness += myPlayerRandom.nextInt(2);

        if (toughness > 9 && toughness < 30) {
            Panel.getAnAchievementDirectly(R.string.achievement_strength1);
        } else if (toughness > 29 && toughness < 50) {
            Panel.getAnAchievementDirectly(R.string.achievement_strength2);
        } else if (toughness > 49) {
            Panel.getAnAchievementDirectly(R.string.achievement_strength3);
        }


        intelligence += myPlayerRandom.nextInt(2);
		wisdom += myPlayerRandom.nextInt(2);
		newMagicSpell(numSpellDescriptions);
		return level;
	}

}
