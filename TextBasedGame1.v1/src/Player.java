
public class Player extends Stvore implements BasicCombatMethods {
	
	Player() {
		
		maxHealth = 10;
		health = maxHealth;
		attack = 1;
		defense = 0;
		agility = 0;
		name = "";
		
	}
	
	@Override
	public int attack(int attack, int eDefend, String name) {
		
		int damage = attack - eDefend;
		
		if(damage <= 0) {
			
			System.out.println("You didn't deal any damage");
			
			damage = 0;
			
		}
		else {
			
			System.out.println("You attacked the " + name + " for: " + attack + ".\nYou dealt " + damage + " damage.\n");

		}
		
		return damage;
		
	}
	
	@Override
	public int defend(int defense, int eAttack) {
		
		int damage = eAttack - defense;
		
		if(damage <= 0) {
			
			System.out.println("You didn't take any damage");
			
			damage = 0;
			
		}
		
		else {
			
			System.out.println("You defended for: " + defense + " and took " + damage + " damage.");
			
		}
		
		return damage;
		
	}

}
