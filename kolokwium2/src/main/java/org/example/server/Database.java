package org.example.server;

public class Database {
    
    /**
     * Authenticates a user with the provided login and password.
     * Currently returns true for any login/password combination.
     * This method will receive a more complex implementation in future steps.
     * 
     * @param login The user's login
     * @param password The user's password
     * @return true if authentication is successful, false otherwise
     */
    public boolean authenticate(String login, String password) {
        // For now, always return true as specified in Step 4
        // This will be enhanced in future implementations
        return true;
    }
}
