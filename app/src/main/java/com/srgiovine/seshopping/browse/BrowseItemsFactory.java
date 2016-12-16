package com.srgiovine.seshopping.browse;

import com.srgiovine.seshopping.model.Category;
import com.srgiovine.seshopping.model.Gender;
import com.srgiovine.seshopping.model.Item;

import srgiovine.com.seshopping.R;

public final class BrowseItemsFactory {

    public static Item[] createBrowseItems() {
        return new Item[]{
                // HATS
                Item.builder().setName("Warm Beanie")
                        .setDescription("A knitted winter hat, or watch cap (sometimes called a beanie " +
                                "in other parts of the world); the spelling \"touque\", although not " +
                                "recognized by the Canadian Oxford Dictionary")
                        .setCategory(Category.Hats)
                        .addGender(Gender.Female)
                        .setPrice(15)
                        .setImage(R.drawable.hat_female)
                        .setIcon(R.drawable.hat_female_icon)
                        .build(),
                Item.builder().setName("Lincoln's O'l Hat")
                        .setDescription("Abraham Lincoln was our tallest president. At 6-foot-4, " +
                                "he would stand out even today, and he certainly towered over " +
                                "the men and women of his era.")
                        .setCategory(Category.Hats)
                        .addGender(Gender.Male)
                        .setPrice(54)
                        .setImage(R.drawable.hat_male)
                        .setIcon(R.drawable.hat_male_icon)
                        .build(),
                Item.builder().setName("Pokemon Trainer Essential")
                        .setDescription("Pokémon Trainer is the term commonly used for a person who catches, " +
                                "occasionally names, and trains several different kinds and types of Pokémon. " +
                                "Pokémon Trainers generally start their journey at the age of 10.")
                        .setCategory(Category.Hats)
                        .addGender(Gender.Male)
                        .addGender(Gender.Female)
                        .setPrice(19)
                        .setImage(R.drawable.hat_unisex)
                        .setIcon(R.drawable.hat_unisex_icon)
                        .build(),

                // SwEATERS
                Item.builder().setName("Anti-Xmas-Novelty Holiday Sweater")
                        .setDescription("Give the reindeer a rest: Try these elegant takes on the " +
                                "traditional for a classic and festive look.")
                        .setCategory(Category.Sweaters)
                        .addGender(Gender.Female)
                        .setPrice(199)
                        .setImage(R.drawable.sweater_female)
                        .setIcon(R.drawable.sweater_female_icon)
                        .build(),
                Item.builder().setName("Shetland Wool Sweater, Crew")
                        .setDescription("Provides years of rugged wear and midweight warmth." +
                                "Earthy colors will stay rich and true through many washings.")
                        .setCategory(Category.Sweaters)
                        .addGender(Gender.Male)
                        .setPrice(59)
                        .setImage(R.drawable.sweater_male)
                        .setIcon(R.drawable.sweater_male_icon)
                        .build(),
                Item.builder().setName("Classic Cashmere Sweater, Open Cardigan")
                        .setDescription("You’ll appreciate the exceptional warmth and softness of " +
                                "this authentic cashmere open cardigan sweater so much so, " +
                                "you’ll soon want more than one.")
                        .setCategory(Category.Sweaters)
                        .addGender(Gender.Male)
                        .addGender(Gender.Female)
                        .setPrice(160)
                        .setImage(R.drawable.sweater_male)
                        .setIcon(R.drawable.sweater_male_icon)
                        .build(),

                // T SHIRTS
                Item.builder().setName("Nasty Woman Shirt")
                        .setDescription("By getting this exclusive “Nasty Woman” shirt, you're " +
                                "joining Samantha Bee and countless other smart, fearless " +
                                "women and men in supporting Planned")
                        .setCategory(Category.Tshirts)
                        .addGender(Gender.Female)
                        .setPrice(15)
                        .setImage(R.drawable.tshirt_female)
                        .setIcon(R.drawable.tshirt_female_icon)
                        .build(),
                Item.builder().setName("Men's Cotton Poplin Field Shirt")
                        .setDescription("Made of lightweight, breathable cloth that lets heat " +
                                "escape for cool comfort, our men's long sleeve poplin field " +
                                "shirt is a go-to when temperatures go up.")
                        .setCategory(Category.Tshirts)
                        .addGender(Gender.Male)
                        .setPrice(10)
                        .setImage(R.drawable.tshirt_male)
                        .setIcon(R.drawable.tshirt_male_icon)
                        .build(),
                Item.builder().setName("Happy Orange Bee")
                        .setDescription("Honey Bee is a cozy ring spun cotton t-shirt designed " +
                                "by Humans. Pick up this tee and support one of our global artists today.")
                        .setCategory(Category.Tshirts)
                        .addGender(Gender.Male)
                        .addGender(Gender.Female)
                        .setPrice(20)
                        .setImage(R.drawable.tshirt_unisex)
                        .setIcon(R.drawable.tshirt_unisex_icon)
                        .build(),

                // JEANS
                Item.builder().setName("Mid Rise Distressed Jean Legging")
                        .setDescription("These stretch-kissed jean leggings boast super distressed " +
                                "details that give you a flirty on-trend look. ")
                        .setCategory(Category.Jeans)
                        .addGender(Gender.Female)
                        .setPrice(79)
                        .setImage(R.drawable.jeans_female)
                        .setIcon(R.drawable.jeans_female_icon)
                        .build(),
                Item.builder().setName("Wrangler Five Star Premium Denim Relaxed Fit Jean")
                        .setDescription("Of all the words we could write about this jean, " +
                                "\"perfect\" just about sums it up.")
                        .setCategory(Category.Jeans)
                        .addGender(Gender.Male)
                        .setPrice(45)
                        .setImage(R.drawable.jeans_male)
                        .setIcon(R.drawable.jeans_male_icon)
                        .build(),

                // Purses
                Item.builder().setName("MICHAEL Michael Kors Jet Set East West Top Zip Tote")
                        .setDescription("Before you jet off to Joburg, be sure to bring along this " +
                                "fabulously sleek silhouette from MICHAEL Michael Kors. Sumptuous leather, " +
                                "luxe hardware and elegant contours make it the ultimate travel " +
                                "companion for any global or local getaway.")
                        .setCategory(Category.Purses)
                        .addGender(Gender.Female)
                        .setPrice(240)
                        .setImage(R.drawable.purse_mk)
                        .setIcon(R.drawable.purse_mk_icon)
                        .build(),
                Item.builder().setName("Pattina Tessuto + Saffiano Crossbody Nero+Bianco")
                        .setDescription("Italian elegance at its best. Almost no other label manages " +
                                "to switch between modernity and tradition as successfully as Prada. " +
                                "Miuccia Prada designs timelessly classic tote bags. ")
                        .setCategory(Category.Purses)
                        .addGender(Gender.Female)
                        .setPrice(986)
                        .setImage(R.drawable.purse_prada)
                        .setIcon(R.drawable.purse_prada_icon)
                        .build(),

                // SUNGLASSES
                Item.builder().setName("Burberry Sunglasses, BE4216")
                        .setDescription("The first name in British fashion, Burberry eyewear l" +
                                "everages the strength of its 150-year heritage, balancing " +
                                "classic and modern design.")
                        .setCategory(Category.Sunglasses)
                        .addGender(Gender.Male)
                        .addGender(Gender.Female)
                        .setPrice(230)
                        .setImage(R.drawable.sunglasses_burberry)
                        .setIcon(R.drawable.sunglasses_burberry_icon)
                        .build(),
                Item.builder().setName("Michael Kors Sunglasses, MK5004 CHELSEA")
                        .setDescription("Eyewear by Michael Kors is perfect for any mood. Feel chic, " +
                                "luxurious, sleek and sophisticated in his timeless designs.")
                        .setCategory(Category.Sunglasses)
                        .addGender(Gender.Male)
                        .addGender(Gender.Female)
                        .setPrice(99)
                        .setImage(R.drawable.sunglasses_mk)
                        .setIcon(R.drawable.sunglasses_mk_icon)
                        .build(),

                // SHOES
                Item.builder().setName("Kate Middleton's Jimmy Choo Pumps")
                        .setDescription("Middleton started wearing the brand a few years ago, " +
                                "flashing a bit of the \"Vamp\" platform sandal in silver as early as 2013")
                        .setCategory(Category.Shoes)
                        .addGender(Gender.Female)
                        .setPrice(260)
                        .setImage(R.drawable.shoes_female)
                        .setIcon(R.drawable.shoes_female_icon)
                        .build(),
                Item.builder().setName("Warth Chelsea Woody Shoes")
                        .setDescription("The timeless Chelsea gets a modern update in these super-soft " +
                                "leather boots with a stripped-back design and subtle detailing. " +
                                "A tonal rivet and tough woven edge reinforce the upper.")
                        .setCategory(Category.Shoes)
                        .addGender(Gender.Male)
                        .setPrice(146)
                        .setImage(R.drawable.shoes_male)
                        .setIcon(R.drawable.shoes_male_icon)
                        .build()
        };
    }

}
