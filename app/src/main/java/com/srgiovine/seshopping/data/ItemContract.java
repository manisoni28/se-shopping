package com.srgiovine.seshopping.data;

import android.provider.BaseColumns;

import srgiovine.com.seshopping.R;

final class ItemContract implements BaseColumns {

    private ItemContract() {
    }

    static final String TABLE_NAME = "ITEM";

    static final String ID = _ID;
    static final String NAME = "Name";
    static final String DESCRIPTION = "Description";
    static final String GENDER = "Gender";
    static final String CATEGORY = "Category";
    static final String PRICE = "Price";
    static final String IMAGE = "Image";
    static final String ICON = "Icon";

    static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +

            ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
            NAME + " TEXT NOT NULL," +
            DESCRIPTION + " TEXT NOT NULL," +
            GENDER + " TEXT NOT NULL," +
            CATEGORY + " TEXT NOT NULL," +
            PRICE + " INTEGER NOT NULL," +
            IMAGE + " INTEGER NOT NULL," +
            ICON + " INTEGER NOT NULL" +

            " )";

    static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    static final String INITIAL_INSERT = "INSERT INTO " + TABLE_NAME +
            "(" + NAME + ", " + DESCRIPTION + ", " + GENDER + ", " + CATEGORY +
            ", " + PRICE + ", " + IMAGE + ", " + ICON + ")" +
            " VALUES " +

            // HATS
            "('Warm Beanie', " +
            "'A knitted winter hat, or watch cap, sometimes called a beanie " +
            "in other parts of the world; the spelling touque, although not " +
            "recognized by the Canadian Oxford Dictionary', " +
            "'Womens', 'Hats', 15, " + R.drawable.hat_beanie + ", " + R.drawable.hat_beanie_icon + ")," +

            "('Lincolns Old Hat', " +
            "'Abraham Lincoln was our tallest president. At 6-foot-4, " +
            "he would stand out even today, and he certainly towered over " +
            "the men and women of his era.', " +
            "'Mens', 'Hats', 54, " + R.drawable.hat_lincoln + ", " + R.drawable.hat_lincoln_icon + ")," +

            "('Pokemon Trainer Cap', " +
            "'Pokemon Trainer is the term commonly used for a person who catches, " +
            "occasionally names, and trains several different kinds and types of Pokemon. " +
            "Pokemon Trainers generally start their journey at the age of 10.', " +
            "'Mens', 'Hats', 19, " + R.drawable.hat_pokemon + ", " + R.drawable.hat_pokemon_icon + ")," +

            // SwEATERS
            "('Anti-Xmas-Novelty Holiday Sweater', " +
            "'Give the reindeer a rest: Try these elegant takes on the " +
            "traditional for a classic and festive look.', " +
            "'Womens', 'Sweaters', 112, " + R.drawable.sweater_xmas + ", " + R.drawable.sweater_xmas_icon + ")," +

            "('Shetland Wool Sweater, Crew', " +
            "'Provides years of rugged wear and mid-weight warmth. " +
            "Earthy colors will stay rich and true through many washings.', " +
            "'Mens', 'Sweaters', 59, " + R.drawable.sweater_wool + ", " + R.drawable.sweater_wool_icon + ")," +

            "('Classic Cashmere Sweater, Open Cardigan', " +
            "'You will appreciate the exceptional warmth and softness of " +
            "this authentic cashmere open cardigan sweater so much so, " +
            "you will soon want more than one.', " +
            "'Womens', 'Sweaters', 160, " + R.drawable.sweater_cashmere + ", " + R.drawable.sweater_cashmere_icon + ")," +

            // T SHIRTS
            "('Nasty Woman Shirt', " +
            "'By getting this exclusive Nasty Woman shirt, you are " +
            "joining Samantha Bee and countless other smart, fearless " +
            "women and men in supporting Planned.', " +
            "'Womens', 'Tshirts', 15, " + R.drawable.tshirt_nasty_woman + ", " + R.drawable.tshirt_nasty_woman_icon + ")," +

            "('Mens Cotton Poplin Field Shirt', " +
            "'Made of lightweight, breathable cloth that lets heat " +
            "escape for cool comfort, our mens long sleeve poplin field " +
            "shirt is a go-to when temperatures go up.', " +
            "'Mens', 'Tshirts', 10, " + R.drawable.tshirt_cotton + ", " + R.drawable.tshirt_cotton_icon + ")," +

            "('Happy Orange Bee', " +
            "'Honey Bee is a cozy ring spun cotton t-shirt designed " +
            "by Humans. Pick up this tee and support one of our global artists today.', " +
            "'Womens', 'Tshirts', 20, " + R.drawable.tshirt_bee + ", " + R.drawable.tshirt_bee_icon + ")," +

            // JEANS
            "('Mid Rise Distressed Jean Legging', " +
            "'These stretch-kissed jean leggings boast super distressed " +
            "details that give you a flirty on-trend look.', " +
            "'Womens', 'Jeans', 79, " + R.drawable.jeans_female + ", " + R.drawable.jeans_female_icon + ")," +

            "('Wrangler Five Star Premium Denim Relaxed Fit Jean', " +
            "'Of all the words we could write about this jean, " +
            "perfect just about sums it up.', " +
            "'Mens', 'Jeans', 45, " + R.drawable.jeans_male + ", " + R.drawable.jeans_male_icon + ")," +

            // PURSES
            "('MICHAEL Michael Kors Jet Set East West Top Zip Tote', " +
            "'Before you jet off to Joburg, be sure to bring along this " +
            "fabulously sleek silhouette from MICHAEL Michael Kors. Sumptuous leather, " +
            "luxe hardware and elegant contours make it the ultimate travel " +
            "companion for any global or local getaway.', " +
            "'Womens', 'Purses', 240, " + R.drawable.purse_mk + ", " + R.drawable.purse_mk_icon + ")," +

            "('Pattina Tessuto + Saffiano Crossbody Nero+Bianco', " +
            "'Italian elegance at its best. Almost no other label manages " +
            "to switch between modernity and tradition as successfully as Prada. " +
            "Miuccia Prada designs timelessly classic tote bags.', " +
            "'Womens', 'Purses', 986, " + R.drawable.purse_prada + ", " + R.drawable.purse_prada_icon + ")," +

            // SUNGLASSES
            "('Burberry Sunglasses, BE4216', " +
            "'The first name in British fashion, Burberry eyewear leverages " +
            "the strength of its 150-year heritage, balancing classic and modern design.', " +
            "'Womens', 'Sunglasses', 230, " + R.drawable.sunglasses_burberry + ", " + R.drawable.sunglasses_burberry_icon + ")," +

            "('Michael Kors Sunglasses, MK5004 CHELSEA', " +
            "'Eyewear by Michael Kors is perfect for any mood. Feel chic, " +
            "luxurious, sleek and sophisticated in his timeless designs.', " +
            "'Womens', 'Sunglasses', 99, " + R.drawable.sunglasses_mk + ", " + R.drawable.sunglasses_mk_icon + ")," +

            // SHOES
            "('Kate Middletons Jimmy Choo Pumps', " +
            "'Middleton started wearing the brand a few years ago, " +
            "flashing a bit of the Vamp platform sandal in silver as early as 2013', " +
            "'Womens', 'Shoes', 360, " + R.drawable.shoes_female + ", " + R.drawable.shoes_female_icon + ")," +

            "('Warth Chelsea Woody Shoes', " +
            "'The timeless Chelsea gets a modern update in these super-soft " +
            "leather boots with a stripped-back design and subtle detailing. " +
            "A tonal rivet and tough woven edge reinforce the upper.', " +
            "'Mens', 'Shoes', 146, " + R.drawable.shoes_male + ", " + R.drawable.shoes_male_icon + ")";
}
