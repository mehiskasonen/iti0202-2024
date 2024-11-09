package ee.taltech.iti0202.mysticorbs.storage;

import java.util.HashMap;
import java.util.Map;

public class ResourceStorage {

    private Map<String, Integer> resources = new HashMap<>();

    /**
     * Check if there is still resources. Return false if there is at least one resource that has amount more than 0,
     * Else returns true.
     * @return boolean.
     */
    public boolean isEmpty() {
        for (int amount : resources.values()) {
            if (amount > 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Add amount pieces into ResourceStorage. If resource already exists, the amount increases.
     * If resource name is empty or comprises from pieces, it will not be added.
     * @param resource string.
     * @param amount integer.
     */
    public void addResource(String resource, int amount) {
        if (resource != null && !resource.trim().isEmpty() && amount > 0) {
            if (resources.containsKey(resource.toLowerCase())) {
                resources.put(resource.toLowerCase(), resources.get(resource.toLowerCase()) + amount);
            } else {
                resources.put(resource.toLowerCase(), amount);
                System.out.println("Resource added: " + resource + ", amount: "
                        + amount + ", current amount: "
                        + resources.get(resource.toLowerCase()));
            }
        }
    }

    /**
     * Return the amount of resource. If such resource does not exist, or it has run out, return 0
     * @param resource string.
     * @return integer.
     */
    public int getResourceAmount(String resource) {
        if (!resources.containsKey(resource.toLowerCase()) || resources.get(resource.toLowerCase()) < 1) {
            return 0;
        } else {
            return resources.get(resource.toLowerCase());
        }
    }

    /**
     * Check if there is 'amount' of resources in ResourceStorage.
     * If amount is less than one, return false.
     * @param resource string.
     * @param amount integer.
     * @return boolean.
     */
    public boolean hasEnoughResource(String resource, int amount) {
        if (amount < 1) {
            return false;
        }
        Integer storedAmount = resources.get(resource.toLowerCase());
        return storedAmount != null && storedAmount >= amount;
    }

    /**
     * Method for taking resource from ResourceStorage. If the amount of resource can not be taken
     * return false. Else, returns true.
     * @param resource string.
     * @param amount integer.
     * @return boolean.
     */
    public boolean takeResource(String resource, int amount) {
        int existing = getResourceAmount(resource);
        if (existing >= amount) {
            resources.put(resource, resources.get(resource) - amount);
            return true;
        } else {
            return false;
        }
    }
}
