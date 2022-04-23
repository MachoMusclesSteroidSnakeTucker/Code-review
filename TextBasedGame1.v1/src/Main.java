import java.util.Scanner;
import java.util.Random;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
// USE \033[H\033[2J to clear console in UNIX

public class Main {
	
	//Keyboard input
	static Scanner sc = new Scanner(System.in);
	//You
	static Player me = new Player();
	//Money
	static int gold = 0;

	public static void main(String[] args) {
		
		//Input files
		File nCom = new File("nCom.txt");
		File l = new File("loot.txt");
		//General container used between functions
		String gContainer = "";
		//Defeated Enemy
		Enemy corpse = new Enemy();
		
		
		//Greet the player
		startAdventure(nCom, gContainer);
		
		//The game
		gameLoop(l, corpse, gContainer);
		
		//The end
		advance("<enter to exit>");
		sc.close();
		
	}
	
	private static void gameLoop(File l, Enemy corpse, String gContainer) {
		
		for(int i = 0; i < 5; i++) {
			
			gContainer = choosePath();
			
			if (gContainer.equals("Treasure room")) {
				
				treasureRoom();
				
			}
			
			else if (gContainer.equals("Waterfall")) {
				
				waterfall(l);
				
			}
			
			else if (gContainer.equals("Enemy encounter")) {
				
				corpse = enemyEncounter();
				
				if(me.health <= 0) {
					
					System.out.println("Game Over!");	
					break;
					
				}
				
				loot(corpse, l);
				
			}
			
			else if (gContainer.equals("Fountain")) {
				
				fountain();
				
			}
			
			else {
				
			}
			
		}
		
	}
	
	private static void startAdventure(File nCom, String gContainer) {
		
		System.out.println("Insert adventure text here.\n");
		
		advance();
		
		System.out.print("Pick a name for your warrior: ");
		
		me.name = sc.nextLine();
		gContainer = getRandomLine(nCom.getAbsolutePath());
		
		System.out.print("\n" + me.name);
		System.out.println(gContainer + "\n");
		
		advance();
		
	}
	
	private static Enemy enemyEncounter() {
		
		Enemy foe = new Enemy(), fled = new Enemy();
		fled.name = "fled";
		int tempHp, def = me.defense;
		System.out.println("Enemy encounter: " + foe.name);
		System.out.println("It glows with a " + rarityProperties(foe.lootType)[0] + " light.\n");
		while(foe.health > 0 && me.health > 0) {
			
			System.out.print(me.name + " stats: ");
			printStats(me, true);
			System.out.print(foe.name + " stats: ");
			printStats(foe, true);
			
			tempHp = foe.health;
			foe.health -= me.attack(me.attack, foe.defense, foe.name);
			
			if(tempHp == foe.health) {
				
				foe.defense--;
				
			}
			
			tempHp = me.health;
			me.health -= me.defend(me.defense, foe.attack);
			
			if(tempHp == me.health) {
				
				me.defense--;
				
			}
			
			if(me.agility > foe.agility) {
				
				System.out.println();
				System.out.println("<enter to advance>, f to attempt to flee");
				
				String coward = sc.nextLine();
				
				if(coward.equals("f")) {
					
					Random rnr = new Random();
					
					if(rnr.nextInt(4) > 1 ) {
						
						me.defense = def;
						
						return fled;						
						
					}
					
					else {
						
						System.out.println("You didn't manage to flee!");
						advance();
						
					}
					
				}
				
				clearScreen();
				
			}
			
			else {
				
				System.out.println();
				advance();
				
			}
			
		}
		
		me.defense = def;
		
		return foe;
		
	}
	
	private static String choosePath() {
		
		Random rnr = new Random();
		String[] paths = {"Treasure room", "Fountain", "Enemy encounter", "Enemy encounter", "Enemy encounter", "Enemy encounter", "Waterfall"};
		int temp1 = rnr.nextInt(paths.length), temp2 = rnr.nextInt(paths.length);
		String choice;
		
		System.out.println("Where would you like to go?\n");
		
		System.out.println(paths[temp1] + " (1)");
		System.out.println(paths[temp2] + " (2)\n");
		
		choice = sc.nextLine();
		while(true) {
			
			clearScreen();
			
			if (choice.equals("1")) {
				
				return paths[temp1];
				
			}
			
			else if (choice.equals("2")) {
				
				
				return paths[temp2];
				
			}
			
			else {
				
				System.out.println("This is not a valid choice!\n");
				
				System.out.println("Where would you like to go?\n");
				
				System.out.println(paths[temp1] + " (1)");
				System.out.println(paths[temp2] + " (2)\n");
				
				choice = sc.nextLine();
				
			}
			
		}
		
	}
	
	private static void treasureRoom() {
		
		System.out.println("You encountered a treasure room! All stats up for now!\n");
		me.maxHealth++;
		me.health++;
		me.attack++;
		me.defense++;
		me.agility++;
		
		System.out.print("New stats: ");
		
		printStats(me, true);
		
		advance();
		
	}
	
	private static void waterfall(File l) {
		
		System.out.println("You encountered a beautiful waterfall! What can be found behind it?\n");
		
		String[] temp;
		
		temp = getRandomLine(l.getAbsolutePath(), "Uncommon").split(" ");
		
		addItem(temp);
		
		advance();
		
	}
	
	public static void addItem(String[] temp) {
		
		System.out.println("You got the " + temp[0] + "!\n");
		me.maxHealth += Integer.parseInt(temp[1]);
		me.attack += Integer.parseInt(temp[2]);
		me.defense += Integer.parseInt(temp[3]);
		me.agility += Integer.parseInt(temp[4]);
		
		System.out.println("New stats: ");
		
		printStats(me, true);		
		
	}
	
	private static void fountain() {
		
		System.out.println("You encountered refreshing fountain! Health restored!\n");
		me.health = me.maxHealth;
		System.out.println("Health: " + me.health + "\n");
		
		advance();
		
	}
	
	public static void loot(Enemy corpse, File l) {
		
		if(corpse.name.equals("fled")) {
			
			clearScreen();
			System.out.println("No loot for you!");
			return;
		
		}
		
		String[] temp1, temp2;
		String choice, rarity = corpse.lootType;
		
		temp1 = getRandomLine(l.getAbsolutePath(), rarity).split(" ");
		temp2 = getRandomLine(l.getAbsolutePath(), rarity).split(" ");
		
		while(temp2[0].equals(temp1[0])) {
			
			temp2 = getRandomLine(l.getAbsolutePath(), rarity).split(" ");
			
		}
		
		System.out.println("You loot the corpse, choose one!\n");
		
		System.out.println(temp1[0] + " (1)");
		System.out.println(temp2[0] + " (2)\n");
		
		choice = sc.nextLine();
		
		while(true) {
			
			clearScreen();
			
			if(choice.equals("1")) {
				
				addItem(temp1);
				
				break;
				
			}
			else if(choice.equals("2")) {
				
				addItem(temp2);
				
				break;
				
			}
			else {
				
				System.out.println("This is not a valid choice!");
				choice = sc.nextLine();
				
			}
			
		}
		
		advance();
		
	}
	
	public static void printStats(Stvore cr, boolean ln) {
		
		System.out.println("Health: " + cr.health + "/" + cr.maxHealth + ", Damage: " + cr.attack + ", Defense: " + cr.defense + ", Agility: " + cr.agility);
		if(cr.name == me.name) {System.out.println("Gold: " + gold);}
		if(ln) {System.out.println();}
		
	}
	
	public static void clearScreen() {  

		try {
			
			new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
			
		} 
		
		catch (Exception e) {
			
			e.printStackTrace();
			
		}   

	}
	
	private static void advance(){
		
		System.out.println("<enter to advance>");
		sc.nextLine();
		clearScreen();
		
	}
	
	private static void advance(String text){
		
		System.out.println(text);
		sc.nextLine();
		clearScreen();
		
	}
	
	private static String getRandomLine(String path) {
		
        List<String> lines;
        
        try {
        	
            lines = Files.readAllLines(Paths.get(path));
            
        } 
        
        catch (IOException e) {
        	
            e.printStackTrace();
            return null;
            
        }

        Random rnr = new Random();
        return lines.get(rnr.nextInt(lines.size()));
        
    }
	
	private static String getRandomLine(String path, String mod) {
		
        List<String> lines = new ArrayList<String>();
        int elements = 0;
        String content = "Rarity";
        
        try {
        	Scanner sc = new Scanner(Paths.get(path));
        	
        	while(!sc.nextLine().equals(mod));
        	while(!content.isEmpty()) {
        		
        		content = sc.nextLine();
        		
        		if(content.isEmpty()) {
        			
        			break;
        			
        		}
        		
        		elements++;
        		lines.add(content);
        		
        	}
        	
        } 
        
        catch (IOException e) {
        	
            e.printStackTrace();
            return null;
            
        }

        Random rnr = new Random();
        return lines.get(rnr.nextInt(elements));
        
    }
	
	private static String[] rarityProperties(String rarity) {
		
		//out[0] = glow, out[1] = value
		String[] out = new String[2];
		
		switch(rarity) {
		
		case "Common":
			
			out[0] = "gray";
			out[1] = "2";
			
			break;
			
		case "Uncommon":
			
			out[0] = "green";
			out[1] = "3";
			
			break;
			
		case "Rare":
			
			out[0] = "blue";
			out[1] = "5";
			
			break;
			
		case "Epic":
			
			out[0] = "purple";
			out[1] = "9";
			
			break;
			
		case "Legendary":
			
			out[0] = "orange";
			out[1] = "15";
			
			break;
			
		case "Mythic":
			
			out[0] = "red";
			out[1] = "50";
			
		default:
			
			break;
			
		}
		
		return out;
		
	}

}
