package com.mojang.ld22.level;

import com.mojang.ld22.Game;
import com.mojang.ld22.entity.AirWizard;
import com.mojang.ld22.entity.Anvil;
import com.mojang.ld22.entity.Chest;
import com.mojang.ld22.entity.Cow;
import com.mojang.ld22.entity.Creeper;
import com.mojang.ld22.entity.DungeonChest;
import com.mojang.ld22.entity.Entity;
import com.mojang.ld22.entity.Inventory;
import com.mojang.ld22.entity.Knight;
import com.mojang.ld22.entity.Lantern;
import com.mojang.ld22.entity.Mob;
import com.mojang.ld22.entity.Pig;
import com.mojang.ld22.entity.Player;
import com.mojang.ld22.entity.Sheep;
import com.mojang.ld22.entity.Skeleton;
import com.mojang.ld22.entity.Slime;
import com.mojang.ld22.entity.Snake;
import com.mojang.ld22.entity.Spawner;
import com.mojang.ld22.entity.Tnt;
import com.mojang.ld22.entity.Zombie;
import com.mojang.ld22.gfx.Screen;
import com.mojang.ld22.item.FurnitureItem;
import com.mojang.ld22.item.ResourceItem;
import com.mojang.ld22.item.ToolItem;
import com.mojang.ld22.item.ToolType;
import com.mojang.ld22.item.resource.Resource;
import com.mojang.ld22.level.levelgen.LevelGen;
import com.mojang.ld22.level.tile.DirtTile;
import com.mojang.ld22.level.tile.Tile;
import com.mojang.ld22.screen.WorldSelectMenu;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class Level {
	private Random random = new Random();

	public int w, h;
	public int sux, suy;

	public byte[] tiles;
	public byte[] data;
	public List<Entity>[] entitiesInTiles;

	public int grassColor = 141;
	public int dirtColor = 322;
	public int cl = 0;
	public int woolColor = 444;
	public int redwoolColor = 500;
	public int yellowwoolColor = 550;
	public int sandColor = 550;
	public int depth;
	public int monsterDensity = 8;
	public static int depthlvl;
	public int chestcount;

	public static List<String> ls = new ArrayList<String>();

	public List<Entity> entities = new ArrayList<Entity>();
	private Comparator<Entity> spriteSorter =
			new Comparator<Entity>() {
				public int compare(Entity e0, Entity e1) {
					if (e1.y < e0.y) return +1;
					if (e1.y > e0.y) return -1;
					return 0;
				}
			};

	public static String c(int i) {
		return "" + i;
	}

	@SuppressWarnings("unchecked")
	public Level(int w, int h, int level, Level parentLevel) {
		this.depth = level;
		cl = level;
		this.w = w;
		this.h = h;
		byte[][] maps;
		int saveTile;
		if (level != 0) {
			if (DirtTile.dirtc == 0) {
				dirtColor = 222;
				DirtTile.dirtc++;
			}
			if (DirtTile.dirtc == 1) {
				dirtColor = 222;
			}
		}
		if (level == 0) {
			if (DirtTile.dirtc == 0) {
				dirtColor = 322;
			}
			if (DirtTile.dirtc == 0) {
				if (level == 0) {
					dirtColor = 322;
				}
				if (level != 0) {
					dirtColor = 222;
				}
				DirtTile.dirtc++;
			}
		}
		
		if(Game.debug) System.out.println("Making level " + level);

		if (level == 1) {
			dirtColor = 444;
		}
		if (level == 0) maps = LevelGen.createAndValidateTopMap(w, h);
		else if (level < 0 && level > -4) {
			maps = LevelGen.createAndValidateUndergroundMap(w, h, -level);
			monsterDensity = 4;

		} else if (level == -4) {
			//if(Game.debug) System.out.println("making dungeon...");
			maps = LevelGen.createAndValidateDungeon(w, h);

		} else {
			maps = LevelGen.createAndValidateSkyMap(w, h); // Sky level
			monsterDensity = 4;
		}

		tiles = maps[0];
		data = maps[1];

		if (parentLevel != null) {
			for (int y = 0; y < h; y++)
				for (int x = 0; x < w; x++) {
					if (parentLevel.getTile(x, y) == Tile.stairsDown) {
						
						setTile(x, y, Tile.stairsUp, 0);
						if (level == -4) {
							setTile(x - 1, y, Tile.o, 0);
							setTile(x + 1, y, Tile.o, 0);
							setTile(x + 2, y, Tile.odc, 0);
							setTile(x - 2, y, Tile.odc, 0);
							setTile(x, y - 1, Tile.o, 0);
							setTile(x, y + 1, Tile.o, 0);
							setTile(x, y + 2, Tile.odc, 0);
							setTile(x, y - 2, Tile.odc, 0);
							setTile(x - 1, y - 1, Tile.o, 0);
							setTile(x - 1, y + 1, Tile.o, 0);
							setTile(x + 1, y - 1, Tile.o, 0);
							setTile(x + 1, y + 1, Tile.o, 0);
							setTile(x + 3, y, Tile.o, 0);
							setTile(x - 3, y, Tile.o, 0);
							setTile(x + 3, y - 1, Tile.o, 0);
							setTile(x - 3, y - 1, Tile.o, 0);
							setTile(x + 3, y + 1, Tile.o, 0);
							setTile(x - 3, y + 1, Tile.o, 0);
							setTile(x + 4, y, Tile.o, 0);
							setTile(x - 4, y, Tile.o, 0);
							setTile(x + 4, y - 1, Tile.o, 0);
							setTile(x - 4, y - 1, Tile.o, 0);
							setTile(x + 4, y + 1, Tile.o, 0);
							setTile(x - 4, y + 1, Tile.o, 0);
							setTile(x, y + 3, Tile.o, 0);
							setTile(x, y - 3, Tile.o, 0);
							setTile(x + 1, y - 3, Tile.o, 0);
							setTile(x - 1, y - 3, Tile.o, 0);
							setTile(x + 1, y + 3, Tile.o, 0);
							setTile(x - 1, y + 3, Tile.o, 0);
							setTile(x, y + 4, Tile.o, 0);
							setTile(x, y - 4, Tile.o, 0);
							setTile(x + 1, y - 4, Tile.o, 0);
							setTile(x - 1, y - 4, Tile.o, 0);
							setTile(x + 1, y + 4, Tile.o, 0);
							setTile(x - 1, y + 4, Tile.o, 0);
							setTile(x - 2, y - 2, Tile.ow, 0);
							setTile(x - 3, y - 2, Tile.ow, 0);
							setTile(x - 3, y + 2, Tile.ow, 0);
							setTile(x - 2, y + 1, Tile.ow, 0);
							setTile(x + 2, y - 2, Tile.ow, 0);
							setTile(x + 4, y - 2, Tile.ow, 0);
							setTile(x + 4, y + 2, Tile.ow, 0);
							setTile(x - 4, y - 2, Tile.ow, 0);
							setTile(x - 4, y + 2, Tile.ow, 0);
							setTile(x + 1, y - 2, Tile.ow, 0);
							setTile(x - 2, y + 2, Tile.ow, 0);
							setTile(x + 2, y + 3, Tile.ow, 0);
							setTile(x + 2, y + 4, Tile.ow, 0);
							setTile(x - 2, y - 3, Tile.ow, 0);
							setTile(x - 2, y - 4, Tile.ow, 0);
							setTile(x + 2, y - 3, Tile.ow, 0);
							setTile(x + 2, y - 4, Tile.ow, 0);
							setTile(x - 2, y + 3, Tile.ow, 0);
							setTile(x - 2, y + 4, Tile.ow, 0);
							setTile(x + 3, y - 2, Tile.ow, 0);
							setTile(x + 3, y + 2, Tile.ow, 0);
							setTile(x + 2, y + 2, Tile.ow, 0);
							setTile(x - 1, y + 2, Tile.ow, 0);
							setTile(x + 2, y - 1, Tile.ow, 0);
							setTile(x + 2, y + 1, Tile.ow, 0);
							setTile(x + 1, y + 2, Tile.ow, 0);
							setTile(x - 2, y - 1, Tile.ow, 0);
							setTile(x - 1, y - 2, Tile.ow, 0);
						}
						if (level == 0) {
							sux = x;
							suy = y;
							if(Game.debug) System.out.println("X = " + sux + " " + "Y = " + suy + " ");
							setTile(x - 1, y, Tile.hardRock, 0);
							setTile(x + 1, y, Tile.hardRock, 0);
							setTile(x, y - 1, Tile.hardRock, 0);
							setTile(x, y + 1, Tile.hardRock, 0);
							setTile(x - 1, y - 1, Tile.hardRock, 0);
							setTile(x - 1, y + 1, Tile.hardRock, 0);
							setTile(x + 1, y - 1, Tile.hardRock, 0);
							setTile(x + 1, y + 1, Tile.hardRock, 0);
						}

						if (level != 0 && level != -4) {
							setTile(x - 1, y, Tile.dirt, 0);
							setTile(x + 1, y, Tile.dirt, 0);
							setTile(x, y - 1, Tile.dirt, 0);
							setTile(x, y + 1, Tile.dirt, 0);
							setTile(x - 1, y - 1, Tile.dirt, 0);
							setTile(x - 1, y + 1, Tile.dirt, 0);
							setTile(x + 1, y - 1, Tile.dirt, 0);
							setTile(x + 1, y + 1, Tile.dirt, 0);
						}
					}
				}
		}

		entitiesInTiles = new ArrayList[w * h];
		for (int i = 0; i < w * h; i++) {
			entitiesInTiles[i] = new ArrayList<Entity>();
		}

		if (level == -4 && !WorldSelectMenu.loadworld) {
			for (int i = 0; i < (Game.debug?1:10 * (w / 128)); i++) {
				final DungeonChest d = new DungeonChest();
				boolean addedchest = false;
				while(!addedchest){final int x2 = this.random.nextInt(16 * w) / 16;
				final int y2 = this.random.nextInt(16 * h) / 16;
				if (this.getTile(x2, y2) == Tile.o) {
					final boolean xaxis = this.random.nextBoolean();
					if (xaxis) {
						for (int s = x2; s < w - s; s++) {
							if (this.getTile(s, y2) == Tile.ow) {
								d.x = s * 16 - 24;
								d.y = y2 * 16 - 24;
							}
						}
					} else if (!xaxis) {
						for (int s = y2; s < y2 - s; s++) {
							if (this.getTile(x2, s) == Tile.ow) {
								d.x = x2 * 16 - 24;
								d.y = s * 16 - 24;
							}
						}
					}
					if (d.x == 0 && d.y == 0) {
						d.x = x2 * 16 - 8;
						d.y = y2 * 16 - 8;
					}
					if (this.getTile(d.x / 16, d.y / 16) == Tile.ow) {
						this.setTile(d.x / 16, d.y / 16, Tile.o, 0);
					}
					this.add(d);
					this.chestcount++;
					addedchest = true;}
					if (Game.debug) System.out.println("Added dungeon chest: x="+x2+" y="+y2);
				}
			}
		}
		if (level < 0 && !WorldSelectMenu.loadworld) {
			for (int i = 0; i < 18 / -level * (w / 128); i++) {
				Mob m = new Mob();
				int r = this.random.nextInt(5);
				if (r == 1) {
					m = new Skeleton(-level);
				} else if (r == 2 || r == 0) {
					m = new Slime(-level);
				} else {
					m = new Zombie(-level);
				}
				
				Spawner sp = new Spawner(m, -level);
				int x3 = this.random.nextInt(16 * w) / 16;
				int y3 = this.random.nextInt(16 * h) / 16;
				if (this.getTile(x3, y3) == Tile.dirt) {
					boolean xaxis2 = this.random.nextBoolean();
					
					if (xaxis2) {
						for (int s2 = x3; s2 < w - s2; s2++) {
							if (this.getTile(s2, y3) == Tile.rock) {
							sp.x = s2 * 16 - 24;
							sp.y = y3 * 16 - 24;
							}
						}
					} else {
						for (int s2 = y3; s2 < y3 - s2; s2++) {
							if (this.getTile(x3, s2) == Tile.rock) {
							sp.x = x3 * 16 - 24;
							sp.y = s2 * 16 - 24;
							}
						}
					}
					
					if (sp.x == 0 && sp.y == 0) {
							sp.x = x3 * 16 - 8;
							sp.y = y3 * 16 - 8;
					}

					if (this.getTile(sp.x / 16, sp.y / 16) == Tile.rock) {
						this.setTile(sp.x / 16, sp.y / 16, Tile.dirt, 0);
					}

					for (int xx = 0; xx < 5; xx++) {
						for (int yy = 0; yy < 5; yy++) {
							if (this.noStairs(sp.x / 16 - 2 + xx, sp.y / 16 - 2 + yy)) {
								this.setTile(sp.x / 16 - 2 + xx, sp.y / 16 - 2 + yy, Tile.sbrick, 0);

								if((xx < 1 || yy < 1 || xx > 3 || yy > 3) && (xx != 2 || yy != 0) && (xx != 2 || yy != 4) && (xx != 0 || yy != 2) && (xx != 4 || yy != 2)) {
									this.setTile(sp.x / 16 - 2 + xx, sp.y / 16 - 2 + yy, Tile.stonewall, 0);
								}
							}
						}
					}

					this.add(sp);
					if (Game.debug) System.out.println("Spawner added to level "+level+": x="+sp.x+" y="+sp.y);
					for(int rpt = 1; rpt <= 2; rpt++) {
						if (this.random.nextInt(2) == 0) {
							Chest c = new Chest();
							this.addtoinv(c.inventory, -level);
							c.x = sp.x - 16;
							c.y = sp.y - 16;
							if(Game.debug) System.out.println("Added Chest to level "+level+": X = " + c.x / 16 + ", Y = " + c.y / 16);
							this.add(c);
						}
					}
				}
			}
		}

		if (level == 1 && !WorldSelectMenu.loadworld) {
			AirWizard aw = new AirWizard(false);
			aw.x = w * 8;
			aw.y = h * 8;
			add(aw);
			if(Game.debug) System.out.println("Added Air Wizard! X = " + aw.x + ", Y = " + aw.y);
		}
	}

	public void tick() {
		trySpawn(1);

		depthlvl = depth;

		for (int i = 0; i < w * h / 50; i++) {
			int xt = random.nextInt(w);
			int yt = random.nextInt(w);
			getTile(xt, yt).tick(this, xt, yt);
		}
		for (int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			int xto = e.x >> 4;
			int yto = e.y >> 4;

			e.tick();

			if (e.removed) {
				entities.remove(i--);
				removeEntity(xto, yto, e);
			} else {
				int xt = e.x >> 4;
				int yt = e.y >> 4;

				if (xto != xt || yto != yt) {
					removeEntity(xto, yto, e);
					insertEntity(xt, yt, e);
				}
			}
		}
	}
	
	public void addtoinv(Inventory inventory, int chance) {
  		if (random.nextInt(9 / chance) == 0) {
			inventory.add(new FurnitureItem(new Tnt()));
  		}
  		if (random.nextInt(10 / chance) == 0) {
			inventory.add(new FurnitureItem(new Anvil()));
  		}
  		if (random.nextInt(7 / chance) == 0) {
			inventory.add(new FurnitureItem(new Lantern()));
  		}
  		if (random.nextInt(3 / chance) == 0) {
			inventory.add(new ResourceItem(Resource.bread, 2));
  		}
  		if (random.nextInt(4 / chance) == 0) {
			inventory.add(new ResourceItem(Resource.bread, 3));
  		}
  		if (random.nextInt(7 / chance) == 0) {
			inventory.add(new ResourceItem(Resource.larmor, 1));
  		}
  		if (random.nextInt(50 / chance) == 0) {
			inventory.add(new ResourceItem(Resource.goldapple, 1));
  		}
  		if (random.nextInt(3 / chance) == 0) {
			inventory.add(new ResourceItem(Resource.lapisOre, 2));
  		}
  		if (random.nextInt(4 / chance) == 0) {
			inventory.add(new ResourceItem(Resource.glass, 2));
  		}
  		if (random.nextInt(4 / chance) == 0) {
			inventory.add(new ResourceItem(Resource.gunp, 3));
  		}
  		if (random.nextInt(4 / chance) == 0) {
			inventory.add(new ResourceItem(Resource.gunp, 3));
  		}
  		if (random.nextInt(4 / chance) == 0) {
			inventory.add(new ResourceItem(Resource.torch, 4));
  		}
  		if (random.nextInt(14 / chance) == 0) {
			inventory.add(new ResourceItem(Resource.swimpotion, 1));
  		}
  		if (random.nextInt(16 / chance) == 0) {
			inventory.add(new ResourceItem(Resource.hastepotion, 1));
  		}
  		if (random.nextInt(14 / chance) == 0) {
			inventory.add(new ResourceItem(Resource.lightpotion, 1));
  		}
  		if (random.nextInt(14 / chance) == 0) {
			inventory.add(new ResourceItem(Resource.speedpotion, 1));
  		}
  		if (random.nextInt(16 / chance) == 0) {
			inventory.add(new ResourceItem(Resource.iarmor, 1));
  		}
  		if (random.nextInt(5 / chance) == 0) {
			inventory.add(new ResourceItem(Resource.sbrick, 4));
  		}
  		if (random.nextInt(5 / chance) == 0) {
			inventory.add(new ResourceItem(Resource.sbrick, 6));
  		}
  		if (random.nextInt(4 / chance) == 0) {
			inventory.add(new ResourceItem(Resource.string, 3));
  		}
  		if (random.nextInt(4 / chance) == 0) {
			inventory.add(new ResourceItem(Resource.bone, 2));
  		}
  		if (random.nextInt(3 / chance) == 0) {
			inventory.add(new ResourceItem(Resource.bone, 1));
  		}
  		if (random.nextInt(6 / chance) == 0) {
			inventory.add(new ToolItem(ToolType.hatchet, 2));
  		}
  		if (random.nextInt(6 / chance) == 0) {
			inventory.add(new ToolItem(ToolType.pick, 2));
  		}
  		if (random.nextInt(6 / chance) == 0) {
			inventory.add(new ToolItem(ToolType.spade, 2));
  		}
  		if (random.nextInt(7 / chance) == 0) {
			inventory.add(new ToolItem(ToolType.claymore, 1));
  		}
  		if (random.nextInt(5 / chance) == 0) {
			inventory.add(new ResourceItem(Resource.torch, 3));
  		}
  		if (random.nextInt(6 / chance) == 0) {
			inventory.add(new ResourceItem(Resource.torch, 6));
  		}
  		if (random.nextInt(7 / chance) == 0) {
			inventory.add(new ResourceItem(Resource.steak, 3));
  		}
  		if (random.nextInt(9 / chance) == 0) {
			inventory.add(new ResourceItem(Resource.steak, 4));
  		}
  		if (random.nextInt(6 / chance) == 0) {
			inventory.add(new ResourceItem(Resource.torch, 6));
  		}
  		if (random.nextInt(7 / chance) == 0) {
			inventory.add(new ResourceItem(Resource.gem, 3));
  		}
  		if (random.nextInt(7 / chance) == 0) {
			inventory.add(new ResourceItem(Resource.gem, 5));
  		}
  		if (random.nextInt(7 / chance) == 0) {
			inventory.add(new ResourceItem(Resource.gem, 4));
  		}
  		if (random.nextInt(10 / chance) == 0) {
			inventory.add(new ResourceItem(Resource.yellowclothes, 1));
  		}
  		if (random.nextInt(10 / chance) == 0) {
			inventory.add(new ResourceItem(Resource.blackclothes, 1));
  		}
  		if (random.nextInt(12 / chance) == 0) {
			inventory.add(new ResourceItem(Resource.orangeclothes, 1));
  		}
  		if (random.nextInt(12 / chance) == 0) {
			inventory.add(new ResourceItem(Resource.cyanclothes, 1));
  		}
  		if (random.nextInt(12 / chance) == 0) {
			inventory.add(new ResourceItem(Resource.purpleclothes, 1));
  		}
  		if (random.nextInt(4 / chance) == 0) {
			inventory.add(new ResourceItem(Resource.arrow, 5));
  		}
  		if (inventory.items.size() < 1) {
			inventory.add(new ResourceItem(Resource.potion, 1));
			inventory.add(new ResourceItem(Resource.coal, 3));
			inventory.add(new ResourceItem(Resource.apple, 3));
			inventory.add(new ResourceItem(Resource.dirt, 7));
  		}
	}

	public void renderBackground(Screen screen, int xScroll, int yScroll) {
		int xo = xScroll >> 4;
		int yo = yScroll >> 4;
		int w = (screen.w + 15) >> 4;
		int h = (screen.h + 15) >> 4;
		screen.setOffset(xScroll, yScroll);
		for (int y = yo; y <= h + yo; y++) {
			for (int x = xo; x <= w + xo; x++) {
				getTile(x, y).render(screen, this, x, y);
			}
		}
		screen.setOffset(0, 0);
	}

	private List<Entity> rowSprites = new ArrayList<Entity>();

	public Player player;

	public void renderSprites(Screen screen, int xScroll, int yScroll) {
		int xo = xScroll >> 4;
		int yo = yScroll >> 4;
		int w = (screen.w + 15) >> 4;
		int h = (screen.h + 15) >> 4;

		screen.setOffset(xScroll, yScroll);
		for (int y = yo; y <= h + yo; y++) {
			for (int x = xo; x <= w + xo; x++) {
				if (x < 0 || y < 0 || x >= this.w || y >= this.h) continue;
				rowSprites.addAll(entitiesInTiles[x + y * this.w]);
			}
			if (rowSprites.size() > 0) {
				sortAndRender(screen, rowSprites);
			}
			rowSprites.clear();
		}
		screen.setOffset(0, 0);
	}

	public void renderLight(Screen screen, int xScroll, int yScroll) {
		int xo = xScroll >> 4;
		int yo = yScroll >> 4;
		int w = (screen.w + 15) >> 4;
		int h = (screen.h + 15) >> 4;

		screen.setOffset(xScroll, yScroll);
		int r = 4;
		for (int y = yo - r; y <= h + yo + r; y++) {
			for (int x = xo - r; x <= w + xo + r; x++) {
				if (x < 0 || y < 0 || x >= this.w || y >= this.h) continue;
				List<Entity> entities = entitiesInTiles[x + y * this.w];
				for (int i = 0; i < entities.size(); i++) {
					Entity e = entities.get(i);
					// e.render(screen);
					int lr = e.getLightRadius();
					if (lr > 0) screen.renderLight(e.x - 1, e.y - 4, lr * 8);
				}
				int lr = getTile(x, y).getLightRadius(this, x, y);
				if (lr > 0) screen.renderLight(x * 16 + 8, y * 16 + 8, lr * 8);
			}
		}
		screen.setOffset(0, 0);
	}
	
	private void sortAndRender(Screen screen, List<Entity> list) {
		Collections.sort(list, spriteSorter);
		for (int i = 0; i < list.size(); i++) {
			list.get(i).render(screen);
		}
	}
	
	public Tile getTile(int x, int y) {
		if (x < 0 || y < 0 || x >= w || y >= h) return Tile.rock;
		return Tile.tiles[tiles[x + y * w]];
	}

	public void setTile(int x, int y, Tile t, int dataVal) {
		if (x < 0 || y < 0 || x >= w || y >= h) return;
		tiles[x + y * w] = t.id;
		data[x + y * w] = (byte) dataVal;
	}

	public int getData(int x, int y) {
		if (x < 0 || y < 0 || x >= w || y >= h) return 0;
		return data[x + y * w] & 0xff;
	}

	public void setData(int x, int y, int val) {
		if (x < 0 || y < 0 || x >= w || y >= h) return;
		data[x + y * w] = (byte) val;
	}

	public void add(Entity entity) {
		if (entity instanceof Player) {
			player = (Player) entity;
		}
		entity.removed = false;
		entities.add(entity);
		entity.init(this);
		if (Game.debug) {
			String clazz = entity.getClass().getCanonicalName();
			if(clazz.contains("AirWizard")) {
				System.out.println("Adding Entity to level "+depth+" at x="+entity.x+" y="+entity.y+": " + clazz);
			}
		}
		
		insertEntity(entity.x >> 4, entity.y >> 4, entity);
	}

	public void adds(Entity entity, int xs, int ys) {
		if (entity instanceof Player) {
			player = (Player) entity;
		}
		entity.removed = false;
		entities.add(entity);
		entity.init(this);

		insertEntity(entity.x >> xs, entity.y >> ys, entity);
	}

	public void remove(Entity e) {
		entities.remove(e);
		int xto = e.x >> 4;
		int yto = e.y >> 4;
		removeEntity(xto, yto, e);
	}

	public void insertEntity(int x, int y, Entity e) {
		if (x < 0 || y < 0 || x >= w || y >= h) return;
		entitiesInTiles[x + y * w].add(e);
	}

	private void removeEntity(int x, int y, Entity e) {
		if (x < 0 || y < 0 || x >= w || y >= h) return;
		entitiesInTiles[x + y * w].remove(e);
	}

	public void trySpawn(int count) {
		for (int i = 0; i < count; i++) {
			Mob mob;

			int minLevel = 1;
			int maxLevel = 1;
			if (depth < 0) {
				maxLevel = (-depth) + 1;
			}
			if (depth > 0) {
				minLevel = maxLevel = 4;
			}

			int lvl = random.nextInt(maxLevel - minLevel + 1) + minLevel;
			int levels = depth;
			int rnd = random.nextInt(100);
			int tim = Game.time;

			if (levels == 0) {
				if (tim > 2) {
					if (rnd <= 40) mob = new Slime(lvl);
					else if (rnd <= 75) mob = new Zombie(lvl);
					else if (rnd >= 85) mob = new Skeleton(lvl);
					else mob = new Creeper(lvl);

					if (mob.findStartPos(this)) this.add(mob);
				}
			}

			if (levels == 0) {
				if (tim != 3) {
					if (rnd <= 22) mob = new Cow(lvl);
					else if (rnd >= 68) mob = new Pig(lvl);
					else mob = new Sheep(lvl);

					if (mob.findStartPosCow(this)) {
						this.add(mob);
					}
				}

				if (tim == 3) {
					if (rnd <= 33) mob = new Cow(lvl);
					else if (rnd >= 68) mob = new Pig(lvl);
					else mob = new Sheep(lvl);

					if (mob.findStartPosCowLight(this)) {
						this.add(mob);
					}
				}
			} else if (levels != 0 && levels != -4) {

				if (rnd <= 40) mob = new Slime(lvl);
				else if (rnd <= 75) mob = new Zombie(lvl);
				else if (rnd >= 85) mob = new Skeleton(lvl);
				else mob = new Creeper(lvl);

				if (mob.findStartPos(this)) {
					this.add(mob);
				}
			} else if (levels == -4) {

				if (rnd <= 40) mob = new Snake(lvl);
				else if (rnd <= 75) mob = new Knight(lvl);
				else if (rnd >= 85) mob = new Snake(lvl);
				else mob = new Knight(lvl);

				if (mob.findStartPosDungeon(this)) {
					this.add(mob);
				}
			}
		}
	}

	public void removeAllEnemies() {
		for (int i = 0; i < this.entities.size(); i++) {
			final String name =
					this.entities.get(i).getClass().getCanonicalName().replace("com.mojang.ld22.entity.", "");
			if (name.equals("Slime")
					|| name.equals("Zombie")
					|| name.equals("Skeleton")
					|| name.equals("Creeper")) {
				this.entities.get(i).remove();
			}
		}
	}

	public List<Entity> getEntities(int x0, int y0, int x1, int y1) {
		List<Entity> result = new ArrayList<Entity>();
		int xt0 = (x0 >> 4) - 1;
		int yt0 = (y0 >> 4) - 1;
		int xt1 = (x1 >> 4) + 1;
		int yt1 = (y1 >> 4) + 1;
		for (int y = yt0; y <= yt1; y++) {
			for (int x = xt0; x <= xt1; x++) {
				if (x < 0 || y < 0 || x >= w || y >= h) continue;
				List<Entity> entities = entitiesInTiles[x + y * w];
				for (int i = 0; i < entities.size(); i++) {
					Entity e = entities.get(i);
					if (e.intersects(x0, y0, x1, y1)) result.add(e);
				}
			}
		}
		return result;
	}
	
	public boolean noStairs(int x, int y) {
		return getTile(x, y) != Tile.stairsDown;
	}
}
