package com.srgiovine.seshopping.data;

import com.srgiovine.seshopping.model.Category;
import com.srgiovine.seshopping.model.Gender;
import com.srgiovine.seshopping.model.Item;
import com.srgiovine.seshopping.task.BackgroundAsyncTask;
import com.srgiovine.seshopping.task.BackgroundTask;
import com.srgiovine.seshopping.task.Callback;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import srgiovine.com.seshopping.R;

// TODO replace with real implementation
class FakeItemRepository implements ItemRepository {

    @Override
    public BackgroundTask getItemById(long itemId, Callback<Item> callback) {
        GetItemByIdBackgroundTask backgroundTask = new GetItemByIdBackgroundTask(callback, itemId);
        backgroundTask.execute();
        return backgroundTask;
    }

    @Override
    public BackgroundTask getItemsWithIds(Set<Long> itemIds, Callback<List<Item>> callback) {
        GetItemWithIdsBackgroundTask backgroundTask = new GetItemWithIdsBackgroundTask(callback, itemIds);
        backgroundTask.execute();
        return backgroundTask;
    }

    @Override
    public BackgroundTask getItemsWithName(String name, Callback<List<Item>> callback) {
        GetItemsWithTitleBackgroundTask backgroundTask = new GetItemsWithTitleBackgroundTask(callback, name);
        backgroundTask.execute();
        return backgroundTask;
    }

    @Override
    public BackgroundTask getItemsWithGendersAndCategories(Set<Gender> genders, Set<Category> categories,
                                                           Callback<List<Item>> callback) {
        GetItemsWithGendersAndCategoriesBackgroundTask backgroundTask
                = new GetItemsWithGendersAndCategoriesBackgroundTask(callback, categories, genders);
        backgroundTask.execute();
        return backgroundTask;
    }

    private static class GetItemByIdBackgroundTask extends BackgroundAsyncTask<Item> {

        private final long itemId;

        private GetItemByIdBackgroundTask(Callback<Item> callback, long itemId) {
            super(callback);
            this.itemId = itemId;
        }

        @Override
        protected Item doInBackground() {
            return ITEMS.get(itemId);
        }

        @Override
        protected void onPostExecute(Item item) {
            super.onPostExecute(item);
            callback.onSuccess(item);
        }
    }

    private static class GetItemWithIdsBackgroundTask extends BackgroundAsyncTask<List<Item>> {

        private final Set<Long> itemIds;

        private GetItemWithIdsBackgroundTask(Callback<List<Item>> callback, Set<Long> itemIds) {
            super(callback);
            this.itemIds = itemIds;
        }

        @Override
        protected List<Item> doInBackground() {
            List<Item> items = new ArrayList<>();
            for (Long itemId : itemIds) {
                items.add(ITEMS.get(itemId));
            }
            return items;
        }

        @Override
        protected void onPostExecute(List<Item> items) {
            super.onPostExecute(items);
            callback.onSuccess(items);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            callback.onCancelled();
        }
    }

    private static class GetItemsWithTitleBackgroundTask extends BackgroundAsyncTask<List<Item>> {

        private final String name;

        private GetItemsWithTitleBackgroundTask(Callback<List<Item>> callback, String name) {
            super(callback);
            this.name = name;
        }

        @Override
        protected List<Item> doInBackground() {
            if (name.isEmpty()) {
                return Collections.emptyList();
            }

            List<Item> items = new ArrayList<>();

            String name = this.name.toLowerCase();
            for (Item item : ITEMS.values()) {
                if (item.name().toLowerCase().contains(name)) {
                    items.add(item);
                }
            }

            return items;
        }

        @Override
        protected void onPostExecute(List<Item> items) {
            super.onPostExecute(items);
            callback.onSuccess(items);
        }
    }

    private static class GetItemsWithGendersAndCategoriesBackgroundTask extends BackgroundAsyncTask<List<Item>> {

        private final Set<Gender> genders;
        private final Set<Category> categories;

        private GetItemsWithGendersAndCategoriesBackgroundTask(Callback<List<Item>> callback,
                                                               Set<Category> categories, Set<Gender> genders) {
            super(callback);
            this.categories = categories;
            this.genders = genders;
        }

        @Override
        protected List<Item> doInBackground() {
            if (genders.isEmpty() && categories.isEmpty()) {
                return allItems();
            }

            if (categories.isEmpty()) {
                return itemsInGenders();
            }

            if (genders.isEmpty()) {
                return itemsInCategories();
            }

            return itemsInCategoriesAndGenders();
        }

        @Override
        protected void onPostExecute(List<Item> items) {
            super.onPostExecute(items);
            callback.onSuccess(items);
        }

        private List<Item> allItems() {
            return new ArrayList<>(ITEMS.values());
        }

        private List<Item> itemsInCategoriesAndGenders() {
            List<Item> items = new ArrayList<>();
            for (Item item : items) {
                if (genders.contains(item.gender()) && categories.contains(item.category())) {
                    items.add(item);
                }
            }
            return items;
        }

        private List<Item> itemsInCategories() {
            List<Item> items = new ArrayList<>();
            for (Item item : ITEMS.values()) {
                if (categories.contains(item.category())) {
                    items.add(item);
                }
            }
            return items;
        }

        private List<Item> itemsInGenders() {
            List<Item> items = new ArrayList<>();
            for (Item item : ITEMS.values()) {
                if (genders.contains(item.gender())) {
                    items.add(item);
                }
            }
            return items;
        }
    }

    private static final Map<Long, Item> ITEMS = new LinkedHashMap<Long, Item>() {{
        // HATS
        put(1L, Item.builder().setId(1L)
                .setName("Warm Beanie")
                .setDescription("A knitted winter hat, or watch cap (sometimes called a beanie " +
                        "in other parts of the world); the spelling \"touque\", although not " +
                        "recognized by the Canadian Oxford Dictionary")
                .setGender(Gender.Womens)
                .setCategory(Category.Hats)
                .setPrice(15)
                .setImage(R.drawable.hat_female)
                .setIcon(R.drawable.hat_female_icon)
                .build());
        put(2L, Item.builder().setId(2L)
                .setName("Lincoln's O'l Hat")
                .setDescription("Abraham Lincoln was our tallest president. At 6-foot-4, " +
                        "he would stand out even today, and he certainly towered over " +
                        "the men and women of his era.")
                .setGender(Gender.Mens)
                .setCategory(Category.Hats)
                .setPrice(54)
                .setImage(R.drawable.hat_male)
                .setIcon(R.drawable.hat_male_icon)
                .build());
        put(3L, Item.builder().setId(3L)
                .setName("Pokemon Trainer Essential")
                .setDescription("Pokémon Trainer is the term commonly used for a person who catches, " +
                        "occasionally names, and trains several different kinds and types of Pokémon. " +
                        "Pokémon Trainers generally start their journey at the age of 10.")
                .setGender(Gender.Mens)
                .setCategory(Category.Hats)
                .setPrice(19)
                .setImage(R.drawable.hat_unisex)
                .setIcon(R.drawable.hat_unisex_icon)
                .build());

        // SwEATERS
        put(4L, Item.builder().setId(4L)
                .setName("Anti-Xmas-Novelty Holiday Sweater")
                .setDescription("Give the reindeer a rest: Try these elegant takes on the " +
                        "traditional for a classic and festive look.")
                .setGender(Gender.Womens)
                .setCategory(Category.Sweaters)
                .setPrice(199)
                .setImage(R.drawable.sweater_female)
                .setIcon(R.drawable.sweater_female_icon)
                .build());
        put(5L, Item.builder().setId(5L)
                .setName("Shetland Wool Sweater, Crew")
                .setDescription("Provides years of rugged wear and midweight warmth." +
                        "Earthy colors will stay rich and true through many washings.")
                .setGender(Gender.Mens)
                .setCategory(Category.Sweaters)
                .setPrice(59)
                .setImage(R.drawable.sweater_male)
                .setIcon(R.drawable.sweater_male_icon)
                .build());
        put(6L, Item.builder().setId(6L)
                .setName("Classic Cashmere Sweater, Open Cardigan")
                .setDescription("You’ll appreciate the exceptional warmth and softness of " +
                        "this authentic cashmere open cardigan sweater so much so, " +
                        "you’ll soon want more than one.")
                .setGender(Gender.Womens)
                .setCategory(Category.Sweaters)
                .setPrice(160)
                .setImage(R.drawable.sweater_unisex)
                .setIcon(R.drawable.sweater_unisex_icon)
                .build());

        // T SHIRTS
        put(7L, Item.builder().setId(7L)
                .setName("Nasty Woman Shirt")
                .setDescription("By getting this exclusive “Nasty Woman” shirt, you're " +
                        "joining Samantha Bee and countless other smart, fearless " +
                        "women and men in supporting Planned")
                .setGender(Gender.Womens)
                .setCategory(Category.Tshirts)
                .setPrice(15)
                .setImage(R.drawable.tshirt_female)
                .setIcon(R.drawable.tshirt_female_icon)
                .build());
        put(8L, Item.builder().setId(8L)
                .setName("Men's Cotton Poplin Field Shirt")
                .setDescription("Made of lightweight, breathable cloth that lets heat " +
                        "escape for cool comfort, our men's long sleeve poplin field " +
                        "shirt is a go-to when temperatures go up.")
                .setGender(Gender.Mens)
                .setCategory(Category.Tshirts)
                .setPrice(10)
                .setImage(R.drawable.tshirt_male)
                .setIcon(R.drawable.tshirt_male_icon)
                .build());
        put(9L, Item.builder().setId(9L)
                .setName("Happy Orange Bee")
                .setDescription("Honey Bee is a cozy ring spun cotton t-shirt designed " +
                        "by Humans. Pick up this tee and support one of our global artists today.")
                .setGender(Gender.Womens)
                .setCategory(Category.Tshirts)
                .setPrice(20)
                .setImage(R.drawable.tshirt_unisex)
                .setIcon(R.drawable.tshirt_unisex_icon)
                .build());

        // JEANS
        put(10L, Item.builder().setId(10L)
                .setName("Mid Rise Distressed Jean Legging")
                .setDescription("These stretch-kissed jean leggings boast super distressed " +
                        "details that give you a flirty on-trend look. ")
                .setGender(Gender.Womens)
                .setCategory(Category.Jeans)
                .setPrice(79)
                .setImage(R.drawable.jeans_female)
                .setIcon(R.drawable.jeans_female_icon)
                .build());
        put(11L, Item.builder().setId(11L)
                .setName("Wrangler Five Star Premium Denim Relaxed Fit Jean")
                .setDescription("Of all the words we could write about this jean, " +
                        "\"perfect\" just about sums it up.")
                .setGender(Gender.Mens)
                .setCategory(Category.Jeans)
                .setPrice(45)
                .setImage(R.drawable.jeans_male)
                .setIcon(R.drawable.jeans_male_icon)
                .build());

        // Purses
        put(12L, Item.builder().setId(12L)
                .setName("MICHAEL Michael Kors Jet Set East West Top Zip Tote")
                .setDescription("Before you jet off to Joburg, be sure to bring along this " +
                        "fabulously sleek silhouette from MICHAEL Michael Kors. Sumptuous leather, " +
                        "luxe hardware and elegant contours make it the ultimate travel " +
                        "companion for any global or local getaway.")
                .setGender(Gender.Womens)
                .setCategory(Category.Purses)
                .setPrice(240)
                .setImage(R.drawable.purse_mk)
                .setIcon(R.drawable.purse_mk_icon)
                .build());
        put(13L, Item.builder().setId(13L)
                .setName("Pattina Tessuto + Saffiano Crossbody Nero+Bianco")
                .setDescription("Italian elegance at its best. Almost no other label manages " +
                        "to switch between modernity and tradition as successfully as Prada. " +
                        "Miuccia Prada designs timelessly classic tote bags.")
                .setGender(Gender.Womens)
                .setCategory(Category.Purses)
                .setPrice(986)
                .setImage(R.drawable.purse_prada)
                .setIcon(R.drawable.purse_prada_icon)
                .build());

        // SUNGLASSES
        put(14L, Item.builder().setId(14L)
                .setName("Burberry Sunglasses, BE4216")
                .setDescription("The first name in British fashion, Burberry eyewear l" +
                        "everages the strength of its 150-year heritage, balancing " +
                        "classic and modern design.")
                .setGender(Gender.Womens)
                .setCategory(Category.Sunglasses)
                .setPrice(230)
                .setImage(R.drawable.sunglasses_burberry)
                .setIcon(R.drawable.sunglasses_burberry_icon)
                .build());
        put(15L, Item.builder().setId(15L)
                .setName("Michael Kors Sunglasses, MK5004 CHELSEA")
                .setDescription("Eyewear by Michael Kors is perfect for any mood. Feel chic, " +
                        "luxurious, sleek and sophisticated in his timeless designs.")
                .setGender(Gender.Womens)
                .setCategory(Category.Sunglasses)
                .setPrice(99)
                .setImage(R.drawable.sunglasses_mk)
                .setIcon(R.drawable.sunglasses_mk_icon)
                .build());

        // SHOES
        put(16L, Item.builder().setId(16L)
                .setName("Kate Middleton's Jimmy Choo Pumps")
                .setDescription("Middleton started wearing the brand a few years ago, " +
                        "flashing a bit of the \"Vamp\" platform sandal in silver as early as 2013")
                .setGender(Gender.Womens)
                .setCategory(Category.Shoes)
                .setPrice(260)
                .setImage(R.drawable.shoes_female)
                .setIcon(R.drawable.shoes_female_icon)
                .build());
        put(17L, Item.builder().setId(17L)
                .setName("Warth Chelsea Woody Shoes")
                .setDescription("The timeless Chelsea gets a modern update in these super-soft " +
                        "leather boots with a stripped-back design and subtle detailing. " +
                        "A tonal rivet and tough woven edge reinforce the upper.")
                .setGender(Gender.Mens)
                .setCategory(Category.Shoes)
                .setPrice(146)
                .setImage(R.drawable.shoes_male)
                .setIcon(R.drawable.shoes_male_icon)
                .build());
    }};

}
