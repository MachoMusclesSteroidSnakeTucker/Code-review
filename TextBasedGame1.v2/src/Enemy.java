import java.util.Random;

public class Enemy extends Stvore {
	
	String lootType;
	
	Enemy() {
		
		String[] nameList = {"Rat","Eagle","Fish","Bear","Tiger","Rabbit","Wolf","Owl","Caterpillar","Forest Man"};
		Random rnr = new Random();
		name = nameList[rnr.nextInt(10)];
		
		switch(name) {
		
			case "Rat":
				
				maxHealth = 2;
				health = maxHealth;
				attack = 1;
				defense = 0;
				agility = 2;
				lootType = "Common";
				
				break;
			case "Eagle":
				
				maxHealth = 3;
				health = maxHealth;
				attack = 1;
				defense = 2;
				agility = 1;
				lootType = "Uncommon";
				
				break;
			case "Fish":
				
				maxHealth = 2;
				health = maxHealth;
				attack = 2;
				defense = 0;
				agility = 0;
				lootType = "Common";
				
				break;
			case "Bear":
				
				maxHealth = 7;
				health = maxHealth;
				attack = 2;
				defense = 2;
				agility = 1;
				lootType = "Epic";
				
				break;
			case "Tiger":
				
				maxHealth = 5;
				health = maxHealth;
				attack = 2;
				defense = 1;
				agility = 3;
				lootType = "Rare";
				
				break;
			case "Rabbit":
				
				maxHealth = 3;
				health = maxHealth;
				attack = 1;
				defense = 0;
				agility = 3;
				lootType = "Common";
				
				break;
			case "Wolf":
				
				maxHealth = 5;
				health = maxHealth;
				attack = 1;
				defense = 2;
				agility = 3;
				lootType = "Rare";
				
				break;
			case "Owl":
				
				maxHealth = 4;
				health = maxHealth;
				attack = 1;
				defense = 1;
				agility = 0;
				lootType = "Uncommon";
				
				break;
			case "Caterpillar":
				
				maxHealth = 1;
				health = maxHealth;
				attack = 1;
				defense = 0;
				agility = 0;
				lootType = "Common";
				
				break;
			case "Forest Man":
				
				maxHealth = 5;
				health = maxHealth;
				attack = 2;
				defense = 2;
				agility = 2;
				lootType = "Epic";
				
				break;
			default:
				
				System.out.println("Generated a non-valid enemy");
				
				break;
		
		}
		
	}

}
