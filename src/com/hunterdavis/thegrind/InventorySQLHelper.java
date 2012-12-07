package com.hunterdavis.thegrind;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public class InventorySQLHelper extends
		android.database.sqlite.SQLiteOpenHelper {
	private static final String DATABASE_NAME = "thegrind.db";
	private static final int DATABASE_VERSION = 2;

	// Table name
	public static final String TABLE = "thegrindplayers";

	// Columns

	public static final String LEVEL = "level";
	public static final String NAME = "name";
	public static final String EXP = "exp";
	public static final String HEALTH = "health";
	public static final String MANA = "mana";
	public static final String SWORD = "sword";
	public static final String ARMOR = "armor";
	public static final String HELMET = "helmet";
	public static final String GOLD = "gold";
	public static final String MARRIED = "married";
	public static final String SPEED = "speed";
	public static final String AGE = "age";
	public static final String TOUGHNESS = "toughness";
	public static final String INTELLIGENCE = "intelligence";
	public static final String WISDOM = "wisdom";
	public static final String EQUIPMENT = "equipment";
	public static final String SPELLS = "spells";
	public static final String QUESTNUM = "questnum";
	public static final String SUBQUESTNUM = "subquestnum";
	public static final String STATUSNUM = "statusnum";
	public static final String URI = "uri";

	public InventorySQLHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "create table " + TABLE + "( " + BaseColumns._ID
				+ " integer primary key autoincrement, " + NAME
				+ " text not null, " + EXP + " integer not null," + HEALTH
				+ " integer not null," + MANA + " integer not null," + SWORD
				+ " integer not null," + ARMOR + " integer not null," + HELMET
				+ " integer not null," + GOLD + " integer not null," + MARRIED
				+ " integer not null," + SPEED + " integer not null," + AGE
				+ " integer not null," + TOUGHNESS + " integer not null,"
				+ INTELLIGENCE + " integer not null," + WISDOM
				+ " integer not null," + EQUIPMENT + " text," + SPELLS
				+ " text," + QUESTNUM + " integer not null," + SUBQUESTNUM
				+ " integer not null," + STATUSNUM + " integer not null,"
				+ LEVEL + " integer not null  ," + URI + " string);";
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		if (oldVersion >= newVersion)
			return;

		String sql = null;
		if (oldVersion == 1)
			sql = "alter table " + TABLE + " add column uri string;";
		if (oldVersion == 2)
			sql = "";

		if (sql != null)
			db.execSQL(sql);
	}

}
