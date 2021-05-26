package minicraft.screen;

import minicraft.core.Game;
import minicraft.core.io.InputHandler;
import minicraft.core.io.Sound;
import minicraft.entity.mob.Player;
import minicraft.gfx.Point;
import minicraft.gfx.SpriteSheet;
import minicraft.item.Item;
import minicraft.item.Items;
import minicraft.item.Recipe;
import minicraft.screen.entry.ItemListing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MeltingDisplay extends Display {
	private final Player player;
	private final Recipe[] recipes;

	private final RecipeMenu recipeMenu;
	private final Menu.Builder itemCountMenu, costsMenu;

	public MeltingDisplay(List<Recipe> recipes, String title, Player player) {
		for (Recipe recipe : recipes)
			recipe.checkCanCraft(player);

		recipeMenu = new RecipeMenu(recipes, title, player);

		this.player = player;
		this.recipes = recipes.toArray(new Recipe[0]);

		itemCountMenu = new Menu.Builder(true, 0, RelPos.LEFT)
				.setTitle("Have:")
				.setTitlePos(RelPos.TOP_LEFT)
				.setPositioning(new Point(recipeMenu.getBounds().getRight() + SpriteSheet.boxWidth, recipeMenu.getBounds().getTop()), RelPos.BOTTOM_RIGHT);

		costsMenu = new Menu.Builder(true, 0, RelPos.LEFT)
				.setTitle("Cost:")
				.setTitlePos(RelPos.TOP_LEFT)
				.setPositioning(new Point(itemCountMenu.createMenu().getBounds().getLeft(), recipeMenu.getBounds().getBottom()), RelPos.TOP_RIGHT);

		menus = new Menu[]{recipeMenu, itemCountMenu.createMenu(), costsMenu.createMenu()};

		refreshData();
	}

	private void refreshData() {
		Menu prev = menus[2];
		menus[2] = costsMenu
				.setEntries(getCurItemCosts())
				.createMenu();
		menus[2].setColors(prev);

		menus[1] = itemCountMenu
				.setEntries(new ItemListing(recipes[recipeMenu.getSelection()].getProduct(), String.valueOf(getCurItemCount())))
				.createMenu();
		menus[1].setColors(prev);
	}

	private int getCurItemCount() {
		return player.getInventory().count(recipes[recipeMenu.getSelection()].getProduct());
	}

	private ItemListing[] getCurItemCosts() {
		ArrayList<ItemListing> costList = new ArrayList<>();
		HashMap<String, Integer> costMap = recipes[recipeMenu.getSelection()].getCosts();
		for (String itemName : costMap.keySet()) {
			Item cost = Items.get(itemName);
			costList.add(new ItemListing(cost, costMap.get(itemName) + "/" + player.getInventory().count(cost)));
		}

		return costList.toArray(new ItemListing[0]);
	}

	@Override
	public void tick(InputHandler input) {
		if (input.getKey("menu").clicked) {
			Game.exitMenu();
			return;
		}

		int prevSel = recipeMenu.getSelection();
		super.tick(input);
		if (prevSel != recipeMenu.getSelection())
			refreshData();

		if ((input.getKey("select").clicked || input.getKey("attack").clicked) && recipeMenu.getSelection() >= 0) {
			// check the selected recipe
			Recipe r = recipes[recipeMenu.getSelection()];
			if (r.getCanCraft()) {
				r.craft(player);

				Sound.craft.play();

				refreshData();
				for (Recipe recipe : recipes)
					recipe.checkCanCraft(player);
			}
		}
	}
}
