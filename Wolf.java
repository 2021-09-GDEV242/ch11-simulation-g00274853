
import java.util.List;
import java.util.Iterator;
/**
 * Write a description of class Wolf here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Wolf extends Animal
{
    // instance variables - replace the example below with your own
    private static final int BREEDING_AGE = 14;
    private static final int MAX_AGE = 200;
    private static final double BREEDING_PROBABILITY = 0.10;
    private static final int MAX_LITTER_SIZE = 3;
    private static final int RABBIT_FOOD_VALUE = 6;
    private int foodLevel;
    

     /**
     * Constructor for objects of class Wolf
     */
    public Wolf(boolean randomAge, Field field, Location location)
    {
        super(randomAge, field, location);
        foodLevel = RABBIT_FOOD_VALUE;
    }

    /**
     * An example of a method - replace this comment with your own
     *
     * @param  y  a sample parameter for a method
     * @return    the sum of x and y
     */
    public void act(List<Animal> newLynxes)
    {
        incrementAge();
        incrementHunger();
        if(isAlive()) {
            giveBirth(newLynxes);            
            // Move towards a source of food if found.
            Location newLocation = findFood();
            if(newLocation == null) { 
                // No food found - try to move to a free location.
                newLocation = getField().freeAdjacentLocation(getLocation());
            }
            // See if it was possible to move.
            if(newLocation != null) {
                setLocation(newLocation);
            }
            else {
                // Overcrowding.
                setDead();
            }
        }
    }
    
    public int getAge() {
        return super.getAge();
    }
    
    public void setAge(int currentAge) {
        super.setAge(currentAge);
    }
    
    public int getMaxAge() {
        return MAX_AGE;
    }
    
    private void incrementHunger()
    {
        foodLevel--;
        if(foodLevel <= 0) {
            setDead();
        }
    }
    
    private Location findFood()
    {
        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while(it.hasNext()) {
            Location where = it.next();
            Object animal = field.getObjectAt(where);
            if(animal instanceof Rabbit) {
                Rabbit rabbit = (Rabbit) animal;
                if(rabbit.isAlive()) { 
                    rabbit.setDead();
                    foodLevel = RABBIT_FOOD_VALUE;
                    return where;
                }
            }
        }
        return null;
    }
    
    private void giveBirth(List<Animal> newLynxes)
    {
        // New foxes are born into adjacent locations.
        // Get a list of adjacent free locations.
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = super.breed();
        for(int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Wolf young = new Wolf(false, field, loc);
            newLynxes.add(young);
        }
    }
    
    public double getBreedingProbability() {
        return BREEDING_PROBABILITY;
    }
    
     public int getMaxLitterSize() {
        return MAX_LITTER_SIZE;
    }
    
    public int getBreedingAge() {
        return BREEDING_AGE;
    }
}
